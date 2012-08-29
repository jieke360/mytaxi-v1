package com.great.mytaxi.fe.common;

import java.util.ArrayList;
import java.util.List;

public class Page<T>
{
    /** count property ��¼����*/
    private long count = 0; 

    /** pageSize property ÿҳ��ʾ��¼��*/
    private int pageSize = 15;

    /** pageCount property ��ҳ��*/
    private int pageCount = 0; 

    /** page property ��ǰҳ��*/
    private int targetPage = 1;
    
    /**��ҳʵ��Page��������*/
    private String pageName="";
    
    private Integer formNO = 0;
    
    public Page(){};
    
    public Page(String pageName)
    {
        this.pageName = pageName+".";
    }  
    
    private List<T> resultList = new ArrayList<T>(); 

    
    public Page(int pageSize)
    {
        this.pageSize = pageSize;
    }
    
    public long getCount()
    {
        return count;
    }

    /**
     * ���ܼ�¼����


     * @param count
     * @author nixin
     */
    public void setCount(long count)
    {
        if (pageSize != 0)
        {
            pageCount = (int)count / pageSize;
            if (count % pageSize != 0)
            {
                pageCount++;
            }
        }
        this.count = count;
        pageHandle();
    }
    
    
    /**
     * ��ʾ��׼�ķ�ҳ�˵�

     */
    public String getPageMenu()
    {
        StringBuffer str = new StringBuffer();
        int prev, next;
        String pageFirst = "��ҳ";
        String pagePrevious = "��һҳ";
        String pageNext = "��һҳ";
        String pageLast = "ĩҳ";
        String pageTotal = "��";
        String pageRecord = "����¼";
        String pagePageNum = "ҳ��";
        
        String targetPageField =  pageName + "targetPage";
        String pageSizeField =  pageName + "pageSize";
        
        @SuppressWarnings("unused")
		String pageCountField = pageName + "pageCount";
        
        //ÿҳ��¼����
        @SuppressWarnings("unused")
		String[] pageSizeAry = {"10","30","60","100"};
        
        //���ɷ�ҳ������£�����Ϊ��ɫ������ɫ
        String fontColorStyle1 = "<font color='#666666'>";
        String fontColorStyle2 = "</font>";
        prev = this.getTargetPage() - 1;
        next = this.getTargetPage() + 1;
        if (this.getTargetPage() > 1)
        {
            str.append("<a href=\"#\" onclick='document.all(\""+targetPageField+"\").value=1;document.forms["+formNO+"].submit();'>"+pageFirst+"</a>&nbsp;&nbsp;");
            str.append("<a href=\"#\" onclick='document.all(\""+targetPageField+"\").value=" + prev
                    + ";try{doBeforeSubmit();}catch(e){}finally{document.forms["+formNO+"].submit();}'>"+pagePrevious+"</a>&nbsp;");
        }
        else
        {
            pageFirst = fontColorStyle1 + pageFirst + fontColorStyle2;
            pagePrevious = fontColorStyle1 + pagePrevious + fontColorStyle2;
            str.append("<a>"+pageFirst+"</a>&nbsp;&nbsp;");
            str.append("<a>"+pagePrevious+"</a>&nbsp;&nbsp;");
        }

        if (this.getTargetPage() < this.getPageCount())
        {
            str.append("<a href=\"#\" onclick='document.all(\""+targetPageField+"\").value=" + next
                    + ";try{doBeforeSubmit();}catch(e){}finally{document.forms["+formNO+"].submit();}'>"+pageNext+"</a>&nbsp;&nbsp;");
        }
        else
        {
            pageNext = fontColorStyle1 + pageNext + fontColorStyle2;
            str.append("<a>"+pageNext+"</a>&nbsp;&nbsp;");
        }

        if (this.getPageCount() > 1 && this.getTargetPage() != this.getPageCount())
        {
            str.append("<a href=\"#\"  onclick='document.all(\""+targetPageField+"\").value=" + this.getPageCount()
                    + ";try{doBeforeSubmit();}catch(e){}finally{document.forms["+formNO+"].submit();}'>"+pageLast+"</a>&nbsp;&nbsp;");
        }
        else
        {
            pageLast = fontColorStyle1 + pageLast + fontColorStyle2;
            str.append("<a>"+pageLast+"</a>&nbsp;&nbsp;&nbsp;");
        }

        
        //ҳ��ÿһҳ�ļ�¼����
        //str.append("<SELECT name='"+pageCountField+"' onchange='document.all(\""+pageSizeField+"\").value=this.value;document.forms["+formNO+"].submit();'>");
//        for(String s : pageSizeAry)
//        {
//            if(this.getPageSize() == Integer.parseInt(s))
//            {
//                str.append("<OPTION value=" + s + " selected>" + s +"</OPTION>");
//            }
//            else
//            {
//                str.append("<OPTION value=" + s + ">" + s +"</OPTION>");
//            }
//        }       
//        String s ;
//        s = String.valueOf(this.getPageSize());
//        str.append("<OPTION value=" + s + " selected>" + s +"</OPTION>");
        //str.append("</SELECT>");       
        str.append(" "+pageTotal + this.getCount()+" "+ pageRecord);

        str.append("&nbsp;&nbsp;&nbsp;&nbsp;"+pagePageNum + " ");
        str
                .append("<SELECT size=1 name=Pagelist onchange='document.all(\""+targetPageField+"\").value=this.value;try{doBeforeSubmit();}catch(e){}finally{document.forms["+formNO+"].submit();}'>");
        int coun = this.getPageCount();
        if (coun > 0)
        {
            for (int i = 1; i < this.getPageCount() + 1; i++)
            {
                if (i == this.getTargetPage())
                {
                    str.append("<OPTION value=" + i + " selected>" + i + " / " + coun + "</OPTION>");
                }
                else
                {
                    str.append("<OPTION value=" + i + ">" + i + " / " + coun + "</OPTION>");
                }
            }
        }
        else
        {
            str.append("<OPTION value=\"1\">1 / 1</OPTION>");
        }
        str.append("</SELECT>");
        str.append("<INPUT type=hidden  value=" + this.getTargetPage() + " name=\""+targetPageField+"\" > ");
        str.append("<INPUT type=hidden  value=" + this.getPageSize() + " name=\""+pageSizeField+"\"> ");
        return str.toString();
    }
    /**
     * ��ʾ��׼�ķ�ҳ�˵�

     */
    public String getPageMenu1()
    {
        StringBuffer str = new StringBuffer();
        
        @SuppressWarnings("unused")
		int prev, next;
        
        String pageFirst = "��ҳ";
        String pagePrevious = "��һҳ";
        String pageNext = "��һҳ";
        String pageLast = "ĩҳ";
        String pageTotal = "��";
        String pageRecord = "����¼";
        String pagePageNum = "ҳ��";
        
        String targetPageField =  pageName + "targetPage";
        String pageSizeField =  pageName + "pageSize";
        
        @SuppressWarnings("unused")
		String pageCountField = pageName + "pageCount";
        
        //ÿҳ��¼����
        @SuppressWarnings("unused")
		String[] pageSizeAry = {"10","30","60","100"};
        
        //���ɷ�ҳ������£�����Ϊ��ɫ������ɫ
        String fontColorStyle1 = "<font color='#666666'>";
        String fontColorStyle2 = "</font>";
        prev = this.getTargetPage() - 1;
        next = this.getTargetPage() + 1;
        pageFirst = fontColorStyle1 + pageFirst + fontColorStyle2;
        pagePrevious = fontColorStyle1 + pagePrevious + fontColorStyle2;
        str.append("<a>"+pageFirst+"</a>&nbsp;&nbsp;");
        str.append("<a>"+pagePrevious+"</a>&nbsp;&nbsp;");
        pageNext = fontColorStyle1 + pageNext + fontColorStyle2;
        str.append("<a>"+pageNext+"</a>&nbsp;&nbsp;");
        pageLast = fontColorStyle1 + pageLast + fontColorStyle2;
        str.append("<a>"+pageLast+"</a>&nbsp;&nbsp;&nbsp;");
        str.append(" "+pageTotal + 0+" "+ pageRecord);

        str.append("&nbsp;&nbsp;&nbsp;&nbsp;"+pagePageNum + " ");
        str.append("<SELECT size=1 name=Pagelist onchange='document.all(\""+targetPageField+"\").value=this.value;try{doBeforeSubmit();}catch(e){}finally{document.forms["+formNO+"].submit();}'>");
       
        str.append("<OPTION value=\"1\">1 / 1</OPTION>");
        str.append("</SELECT>");
        str.append("<INPUT type=hidden  value=" + this.getTargetPage() + " name=\""+targetPageField+"\" > ");
        str.append("<INPUT type=hidden  value=" + this.getPageSize() + " name=\""+pageSizeField+"\"> ");
        return str.toString();
    }
    /**
     * ��ҳ����
     * @author chenshaoqiang
     */
    private void pageHandle()
    {
        if (count == 0)
        {
            targetPage = 1;
            pageCount = 1;
            return;
        }

        //��ǰҳ������ҳ������Ϊ��ǰҳΪ��ҳ��

        if (targetPage > pageCount)
        {
            targetPage = pageCount;
        }       
    }

    public int getTargetPage()
    {
        return targetPage;
    }

    public void setTargetPage(int targetPage)
    {
        this.targetPage = targetPage;
    }

    public int getPageCount()
    {
        return pageCount;
    }

    public void setPageCount(int pageCount)
    {
        this.pageCount = pageCount;
    }

    public int getPageSize()
    {
        return pageSize;
    }

    public void setPageSize(int pageSize)
    {
        this.pageSize = pageSize;
    }

    public List<T> getResultList()
    {
        return resultList;
    }

    public void setResultList(List<T> resultList)
    {
        this.resultList = resultList;
    }

    public Integer getFormNO() {
        return formNO;
    }

    public void setFormNO(Integer formNO) {
        this.formNO = formNO;
    }
}
