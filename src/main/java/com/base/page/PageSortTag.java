package com.base.page;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;



/**
 * 分页排序标签
 * 
 */
public class PageSortTag extends TagSupport {

	private static final long serialVersionUID = 1636024994132641178L;
	private String sortValue;//排序字段

	
	@Override
	public int doStartTag() throws JspException {
		HttpServletRequest request = (HttpServletRequest)pageContext.getRequest();
		PaginationSortOrderData page = (PaginationSortOrderData)request.getAttribute("page");
		page.setSortValue(sortValue);
		StringBuffer html = new StringBuffer();
		html.append("<a href=\"javascript:sortTable('"+sortValue+"',"+page.isHasAscing()+")\" style=\"color: #000000;text-decoration: none;\">");
		
		try {
			pageContext.getOut().write(html.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return EVAL_BODY_INCLUDE;
	}
	

	
	@Override
	public int doEndTag() throws JspException {
		HttpServletRequest request = (HttpServletRequest)pageContext.getRequest();
		PaginationSortOrderData page = (PaginationSortOrderData)request.getAttribute("page");
		
		StringBuffer html = new StringBuffer();
		html.append("</a>");
		if(page.getSortValue() != null && page.getSortValue().equals(sortValue)){
			if(page.isHasAscing()){
				html.append("<font face=\"Webdings\">5</font>");
			} else {
				html.append("<font face=\"Webdings\">6</font>");
			}
		}
		try {
			pageContext.getOut().write(html.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return EVAL_PAGE;
	}
	public String getSortValue() {
		return sortValue;
	}

	public void setSortValue(String sortValue) {
		this.sortValue = sortValue;
	}

}
