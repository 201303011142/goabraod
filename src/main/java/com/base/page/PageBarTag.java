package com.base.page;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;



/**
 * 分页下拉框标签
 * 
 */
public class PageBarTag extends TagSupport{

	private static final long serialVersionUID = 4707064945294847682L;

	@Override
	public int doEndTag() throws JspException {
		HttpServletRequest reuqest = (HttpServletRequest)pageContext.getRequest();
		PaginationSortOrderData page = (PaginationSortOrderData)reuqest.getAttribute("frontPage");
		
		StringBuffer html = new StringBuffer();
		html.append("<input type=\"hidden\" name=\"frontPage.curPage\" value=\""+page.getCurPage()+"\"/>\n");
		html.append("<input type=\"hidden\" name=\"frontPage.sortValue\" value=\""+page.getSortValue()+"\"/>\n");
		html.append("<input type=\"hidden\" name=\"frontPage.hasAscing\" value=\""+page.isHasAscing()+"\"/>\n");
		html.append(page.getHtmlString());
		try {
			pageContext.getOut().write(html.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return EVAL_PAGE;
	}
}
