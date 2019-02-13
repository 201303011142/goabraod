package com.base.page;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;



/**
 * 分页初始化标签
 */
public class PageDefaultTag  extends TagSupport {
	private static final long serialVersionUID = 376525266406596696L;
	private String urlPath = "" ;//分页提交URL路径
	

	@Override
	public int doEndTag() throws JspException {

		HttpServletRequest request = (HttpServletRequest)pageContext.getRequest();
		PaginationSortOrderData page = (PaginationSortOrderData)request.getAttribute("frontPage");

		
		StringBuffer html = new StringBuffer();
		html.append("<script type=\"text/javascript\">\n");
		html.append("function gotoPage(page){\n");
		html.append("var strP=/^\\+?[1-9][0-9]*$/;\n");
		html.append("var curPage=1;\n");
		html.append("if(!strP.test(page)){\n");
		html.append("curPage=1;\n");
		html.append("} else {\n");
		html.append("curPage=page;\n");
		html.append("}\n");
		html.append("document.pageForm.elements[\"frontPage.curPage\"].value = curPage;\n");
		html.append("document.pageForm.action = \"" + urlPath + "\";\n");
		html.append("document.pageForm.submit();\n");
		html.append("}\n");

		html.append("function sortTable(sortValue,hasAscing){\n");
		html.append("document.pageForm.elements[\"frontPage.curPage\"].value = 1;\n");
		html.append("document.pageForm.elements[\"frontPage.sortValue\"].value = sortValue;\n");
		html.append("if('"+page.getSortValue()+"' == sortValue){\n");
		html.append("document.pageForm.elements[\"frontPage.hasAscing\"].value = !hasAscing;\n");
		html.append("} else {\n");
		html.append("document.pageForm.elements[\"frontPage.hasAscing\"].value = hasAscing;\n");
		html.append("}\n");
		html.append("document.pageForm.action = \"" + urlPath + "\";\n");
		html.append("document.pageForm.submit();\n");
		html.append("}\n");

		html.append("</script>\n");
		try {
			pageContext.getOut().write(html.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return EVAL_PAGE;
	}

	public String getUrlPath() {
		return urlPath;
	}

	public void setUrlPath(String urlPath) {
		this.urlPath = urlPath;
	}

}
