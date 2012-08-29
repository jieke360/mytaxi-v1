/**
 * 
 */
package com.great.mytaxi.fe.message;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import com.great.mytaxi.fe.common.util.StringUtils;
import com.great.mytaxi.fe.common.util.XmlUtil;



/**
 * 	客户端消息报文
 * 
 * @author xu.jianpu
 * 
 * 2012-8-8  下午12:44:35
 */
public class ClientMessage extends AbstractMessage{

	/**
	 * <mytaxi>
			<funcction>
				<tradeType>BindBankListGetRequest</tradeType>
			</funcction>
			<body>
				<SDId>5180000052D01213</SDId>
				<password>123456</password>
			</body>
		</mytaxi>
	 * 
	 * 
	 */
	private static final Log log = LogFactory.getLog(ClientMessage.class);
	
	/**
	 * 响应码，成功
	 */
	public static final String SUCCESS = "1";
	
	
	/**
	 * 响应码，成功
	 */
	public static final String FAIL = "1";
	
    private Element bodyElement;

    private Element functionElement;
    
    private Element rootElement;

    private String transType;

    private String responseCode;

    private String responseDescription;

    private boolean isPacked = false;
    
    /**
     * 获取function里面的子节点内容
     * @param key
     * @return
     */
    public String getBodyValueByKey(String key) {
        return XmlUtil.getValue(bodyElement, key);
    }
    
    /**
     * 添加function节点值
     * @param key
     * @param value
     */
    public void addBodyValue(String key, String value) {
        if(bodyElement == null){
        	bodyElement = new Element("body");
        }
        Element el = XmlUtil.makeNode(bodyElement, key);
        el.addContent(value);
    }
	
    /**
     * 重置function节点的值
     * @param key
     * @param value
     */
    public void resetBodyValue(String key, String value) {
        if(bodyElement == null){
        	bodyElement = new Element("body");
        }
        
        Element e1 = getNode(bodyElement, key);
        e1.setText(value);
    }
    
    /**
     * 获取子节点的值
     * @param element
     * @param path
     * @return
     */
    public  static  Element getNode(Element element, String path) {
        if (StringUtils.isEmpty(path))
            return  element;
        
        if (path.startsWith("/")) {
            return  null;
        }
        String  []paths = path.split("/");
        return  getNode(element, paths, paths.length);
   }
    
    /**
     * 获取子节点的值
     * @param element
     * @param paths
     * @param l
     * @return
     */
    private static  Element getNode(Element element, String paths[], int l) {
        StringBuffer    sb = new StringBuffer();
        Element base = element;

        for (int i= 0; i<l; i++) {
            if (StringUtils.isEmpty(paths[i])) {
                return  null;
            }
            sb.append(paths[i] + "/");
            
            Element child = base.getChild(paths[i]);
            if (child == null) {
                return null;
            }
            base = child;
        }
        return  base;
  }
    
    /**
     * 添加function子节点
     * @param child
     */
    public void addBodyChild(Element child) {
        if(bodyElement == null){
        	bodyElement = new Element("body");
        }
        bodyElement.addContent(child);
    }
    
    /**
     * 上次function子节点
     * @param key
     */
    public void removeBodyChild(String key) {
        if(bodyElement == null){
        	bodyElement = new Element("body");
        }
        bodyElement.removeChild(key);
    }
    
    @Override
	public boolean unpack(byte[] b, int off, int len) {

        SAXBuilder builder = new SAXBuilder();
        InputStream xml = new ByteArrayInputStream(b, off, len);
        Document doc = null;
        try {
            doc = builder.build(xml);
        }
        catch (JDOMException e) {
        	log.error("builder xml  file  error", e) ;
        	return false ;
        }
        catch (IOException e) {
        	log.error("read file  error", e) ;
        	return false ;
        }
        rootElement=doc.getRootElement();
        functionElement = doc.getRootElement().getChild("function");
        bodyElement = doc.getRootElement().getChild("body");
       
        
        transType = XmlUtil.getValue(functionElement, "tradeType");
        responseCode = XmlUtil.getValue(functionElement, "responseCode");
        responseDescription = XmlUtil.getValue(functionElement, "responseDescription");

        isPacked = true;
        return true;
    
	}

    @Override
	public byte[] pack() {
		 //RootElement
        if(rootElement==null){
             rootElement = new Element("mytaxi"); 
        }
        Document doc = rootElement.getDocument();
        if (doc == null)
        {
            doc = new Document(rootElement);
        }
        
        if(!isPacked){
            
        
            if(functionElement==null){
            	functionElement = new Element("function");
            }
//            if(bodyElement==null){
//                bodyElement = new Element("body");
//            }
//            
            if(!rootElement.isAncestor(functionElement)){
                rootElement.addContent(functionElement);
            }
//            if(!rootElement.isAncestor(bodyElement)){
//                rootElement.addContent(bodyElement);
//            }
            
            //headElement
            functionElement.addContent(new Element("tradeType").addContent(transType));
            functionElement.addContent(new Element("responseCode").addContent(responseCode));
            functionElement.addContent(new Element("responseDescription").addContent(responseDescription));
            
            //bodyElement
            if(bodyElement != null&&!bodyElement.isAncestor(rootElement)){
            	rootElement.addContent(bodyElement);
            }
        }
        
        isPacked = true;
        
        Format fmt = Format.getCompactFormat();
        fmt.setEncoding("UTF-8");
        fmt.setIndent("\t");
        XMLOutputter xmlOut = new XMLOutputter(fmt);
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            xmlOut.output(doc, bos);
            return bos.toByteArray();
        }
        catch (Exception e) {
           e.printStackTrace();
        }

        
        return null;
	}



	public Element getBodyElement() {
		return bodyElement;
	}


	public void setBodyElement(Element bodyElement) {
		this.bodyElement = bodyElement;
	}


	public Element getFunctionElement() {
		return functionElement;
	}


	public void setFunctionElement(Element functionElement) {
		this.functionElement = functionElement;
	}


	public Element getRootElement() {
		return rootElement;
	}


	public void setRootElement(Element rootElement) {
		this.rootElement = rootElement;
	}


	public String getTransType() {
		return transType;
	}


	public void setTransType(String transType) {
		this.transType = transType;
	}


	public String getResponseCode() {
		return responseCode;
	}


	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}


	public String getResponseDescription() {
		return responseDescription;
	}


	public void setResponseDescription(String responseDescription) {
		this.responseDescription = responseDescription;
	}


	public boolean isPacked() {
		return isPacked;
	}


	public void setPacked(boolean isPacked) {
		this.isPacked = isPacked;
	}

	
}
