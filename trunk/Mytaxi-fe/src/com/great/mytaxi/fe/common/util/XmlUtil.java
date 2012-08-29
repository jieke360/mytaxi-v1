/**
 * 
 */
package com.great.mytaxi.fe.common.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

/**
 * @author xu.jianpu
 * 
 * 2012-8-8  下午12:51:34
 */
public class XmlUtil {

	private static final Log log = LogFactory.getLog(XmlUtil.class);
	
	public static Element verifySchemas(InputStream xml, String xsd) {
		try {
			if (xsd==null) {
				SAXBuilder builder = new SAXBuilder();
				Document document = builder.build(xml);
				return	document.getRootElement();
			}

			//
			File xsdFile = new File(xsd);
			if (!xsdFile.canRead()) {
				log.error( "Can't read xsdFile<"+xsdFile.getName()+">");
				//log.showError("Can't read xsdFile<"+xsdFile.getName()+">");
				return	null;
			}
			
			SAXBuilder builder = new SAXBuilder(
				"org.apache.xerces.parsers.SAXParser", true);
			
			builder.setFeature("http://apache.org/xml/features/validation/schema", true);
			
			builder.setProperty(
				"http://apache.org/xml/properties/schema"
				+ "/external-noNamespaceSchemaLocation",
				xsdFile.toURI().toURL().toString());
			
			Document doc = builder.build(xml);
			return	doc.getRootElement();
		} catch (Exception e) {
			log.debug("", e);
			//log.printStackTrace(e);
		}
		return	null;
	}


	/**
	 *
	 * @param prefix  node to find, must starts with "<"
	 */
	public	static	byte[]	getXmlText(byte[] req, int off, int len, 
					   byte[] prefix) {
			if (prefix[0]!='<') {
				log.error( "getXmlText: prefix("+new String(prefix)+") not starts with '<'");
				//log.showError("getXmlText: prefix("+new String(prefix)+") not starts with '<'");
				return	null;
			}
			
			// try to find prefix
			boolean	found = false;
			for (found = false; len>prefix.length; off++, len--) {
				if (req[off]!='<')
					continue;
				if (!StringUtils.isSame(req, off, prefix, 0, prefix.length))
					continue;
				found = true;
				break;
			}
			if (!found) {
				log.error("getXmlText: can't found '"+new String(prefix)+"'");
				//log.showError("getXmlText: can't found '"+new String(prefix)+"'");
				return	null;
			}
			
			// try to find '>'
			int	end = off + prefix.length-1;
			int	left = len - prefix.length-1;
			for (found = false; left>0; end++, left--) {
				if (req[end]!='>')
					continue;
				found = true;
				break;
			}
			if (!found) {
				log.error("getXmlText: can't found '"+new String(prefix)+"'");
				//log.showError("getXmlText: can't found '"+new String(prefix)+"'");
				return	null;
			}

			// <prefix...>	-> </prefix...>
			byte[]	suffix = new byte[end-off+1+1]; //����һ��'/'��λ��
			System.arraycopy(req, off+1, suffix, 2, suffix.length-2);
			suffix[0] = '<';
			suffix[1] = '/';
			
			log.debug( "getXmlText: suffix = " + new String(suffix));
			//log.showDebug("getXmlText: suffix = " + new String(suffix));
			//try to find </prefix...> (from right)
			for (found = false; len>suffix.length; len--) {
				if (req[off+len-1]!='>')
					continue;
				if (!StringUtils.isSame(req, off+len-suffix.length, suffix, 0, suffix.length))
					continue;
				found = true;
				break;
			}
			if (!found) {
				log.error( "getXmlText: can't found '"+new String(suffix)+"'");
				//log.showError("getXmlText: can't found '"+new String(suffix)+"'");
				return	null;
			}
			log.debug("getXmlText: Ok, off=" + off+ ", end=" + (off+len-1) + ", len="+len);
			//log.showDebug("getXmlText: Ok, off=" + off+ ", end=" + (off+len-1) + ", len="+len);		
			byte[]	text = new byte[len];
			System.arraycopy(req, off, text, 0, len);
			return	text;
	}

	// </base/node[i]/child/> or <node[i]/child/>
	public	static	String[] getChildrenNames(Element element, String name) {
		Element node = getNode(element, name);
		if (node == null)
			return	null;
		List	elements = node.getChildren();
		if (elements.size()==0) {
			log.error( "Node<"+node.getName()+"> is leaf");
			//log.showError("Node<"+node.getName()+"> is leaf");
			return	null;
		}
		String[] ss = new String[elements.size()];
		for (int i=0; i<elements.size(); i++) {
			ss[i] = ((Element)elements.get(i)).getName();
		}

		return	ss;
	}

	public	static	String	getValue(Element element, String name) {
		Element node = getNode(element, name);
		if (node == null)
			return	null;

		//����Ƿ���Ҷ�ڵ�
		if (node.getChildren().size()!=0) {
			log.info( "Node<"+node.getName()+"> not leaf, size="+ node.getChildren().size());
//			log.showError("Node<"+node.getName()
//				+"> not leaf, size="+ node.getChildren().size());
			return	null;
		}
/*
		log.showDebug("Node<"+node.getName()
			+">=<"+ node.getText()+">");
*/
		return	node.getText();
	}
	
	//
	//xpathx = /base/node[i]/child/  or node[i]/child 
	public	static	Element	getNode(Element element, String path) {
		if (StringUtils.isEmpty(path))
			return	element;
		
		boolean bRoot = path.startsWith("/");

		// "/root/node" split -> "" "root" "node"
		
		String	[]paths = path.split("/");
		return	getNode(element, bRoot, paths, paths.length);
        }


	//
	private	static	Element	getNode(Element element, boolean bRoot, String paths[], int l) {
		StringBuffer	sb = new StringBuffer();
		int	off = bRoot?1:0;
		Element	base = element;
		if (bRoot) {// start with '/'
			String	root = element.getName();
			if (l>off) { 
				if (!root.equals(paths[off])) {
					log.error("Base Node is <"+base.getName()+">, !=" + paths[off]);
					//log.showError("Base Node is <"+base.getName()+">, !=" + paths[off]);
					return	null;
				}
	
				sb.append(paths[off] + "/");
			}
			off ++; //skip root
		}

		//
		for (int i= off; i<l; i++) {
			if (StringUtils.isEmpty(paths[i])) {
//				continue;
				log.error( "Empty Node<"+sb+"/> name!");
				//log.showError("Empty Node<"+sb+"/> name!");
				return	null;
			}
			sb.append(paths[i] + "/");
			base = getChild(base, paths[i]);
			if (base == null) {
				log.warn( "Node<"+sb+"> not found!");
				//log.showWarning("Node<"+sb+"> not found!");
				return	null;
			}
		}

//		log.showDebug("getNode: Node = <"+base.getName()+">, child#="+ base.getChildren().size());

		return	base;
        }

        // name[n], empty name means all children
	private	static	Element	getChild(Element base, String name) {
		if (!name.endsWith("]"))
			return	base.getChild(name);
		
		int	k = name.indexOf('[');
		if (k<0) //
			return	base.getChild(name);
			
		//split "name[n]" into <name, n>
		String	x = name.substring(0, k);
		String	y = name.substring(k+1, name.length()-1).trim();
		int	n = Integer.parseInt(y);
//		log.showDebug("Node="+x+", sub="+y);

		List	elements = (k==0)?base.getChildren():base.getChildren(x);
		if (n>=elements.size())
			return	null;
		return	(Element)elements.get(n);
	}


	//xpathx = node/child 
	public	static	Element	makeNode(Element element, String path) {
		if (StringUtils.isEmpty(path))
			return	element;
		
		if (path.startsWith("/")) {
			log.error( "Path startsWith \"/\"!");
			//log.showError("Path startsWith \"/\"!");
			return	null;
		}

		// "/root/node" split -> "" "root" "node"
		
		String	[]paths = path.split("/");
		return	makeNode(element, paths, paths.length);
        }

	//
	private	static	Element	makeNode(Element element, String paths[], int l) {
		StringBuffer	sb = new StringBuffer();
		Element	base = element;

		//
		for (int i= 0; i<l; i++) {
			if (StringUtils.isEmpty(paths[i])) {
//				continue;
				log.error( "Empty Node<"+sb+"/> name!");
				//log.showError("Empty Node<"+sb+"/> name!");
				return	null;
			}
			sb.append(paths[i] + "/");
			
			Element child = base.getChild(paths[i]);
			if (child == null) {
				child = new Element(paths[i]);
				base.addContent(child);
			}
			base = child;
		}

//		log.showDebug("getNode: Node = <"+base.getName()+">, child#="+ base.getChildren().size());

		return	base;
        }

	// call o.m(element, x)
	public static	void	walkthru(Element top, Object o, Method m, Object[] x) {
		List	elements = top.getChildren();
		if (elements==null) {
			return;
		}
		Iterator it = elements.iterator();
		if (!it.hasNext()) {
			return;
		}
		for ( ; it.hasNext(); ) {
			Element element = (Element)it.next();
			try {
				m.invoke(o, element, x);
				walkthru(element, o, m, x);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}


	///////////////////////////////////////////////////////////////////

	//test
	public static void main(String[] args) 
		throws	Exception {
		FileInputStream	f = new FileInputStream("001.pkt");
		byte[]	req = new byte[1100];
		f.read(req);
		f.close();
		
		int	len = 900;
		//skip lead space
		int	off = 200;
		while ((len>0) && (req[off]!='<')) {
			off++;
			len--;
		}
		while ((len>0) && (req[off+len-1]!='>')) {
			len--;
		}
		
//		String	s = new String(req, 200, 900);
		String	s = new String(req, off, len);
		System.out.println("XML=["+s+"]");
		
		ByteArrayInputStream bi = new ByteArrayInputStream(req, off, len);
		XmlUtil.verifySchemas(bi, "001.xsd");
	}
}
