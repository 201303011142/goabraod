package com.base.page;
import java.util.List;

/**
 * 分页Bean
 * 
 */
public class PaginationSortOrderData {

	private int pageSize = 10;// 默认每页显示数据条目(可修改)

	// private String perPageSelectData = "10,15,20,25,30";//分页工具条下拉框显示数据,','相隔(可修改)

	private int rowCount = 0;// 数据总量

	private int pageCount = 0;// 页面总量

	private int curPage = 1;// 当前页

	private String sortValue = "";// 排序

	private boolean hasAscing = true;// 排序类型(升，降)

	private String htmlString = "";// pageBar的HTML代码

	private int cutPageOrNot = 1;// 是否分页; 1为分页，0为不分页

	@SuppressWarnings("rawtypes")
	private List dataList;// 数据列表

	public void setRowCount(int rowCount) {
		if (cutPageOrNot == 0) {// 使用是否分页
			this.rowCount = rowCount;
			pageCount = 1;
			curPage = 1;
			pageSize = rowCount;
		} else {
			this.rowCount = rowCount;
			pageCount = rowCount % pageSize == 0 ? rowCount / pageSize
					: (rowCount / pageSize + 1);
			if (curPage < 1) {
				this.curPage = 1;
			} else if (curPage > pageCount && pageCount == 0) {
				this.curPage = 1;
			} else if (curPage > pageCount && pageCount != 0) {
				this.curPage = pageCount;
			}
		}
		if (curPage > pageCount || pageCount == 1) {
			htmlString = "";
		} else {
			StringBuffer html = new StringBuffer();
			html.append("<table align=\"center\" style=\"border:0;\" cellspacing=\"1\" cellpadding=\"1\" width=\"100%\">\n");
			html.append("<tr style=\"font-size:12px\"><td style=\"border:0;\" align=\"center\">\n");
			if (curPage == 1) {
				html.append("\u9996\u9875" + "&nbsp;&nbsp;"
						+ "\u4e0a\u4e00\u9875" + "&nbsp;&nbsp;");
			}

			if (curPage > 1) {
				html.append("<a href=\"javascript:gotoPage(1)\" style=\"text-decoration: none;color: #1c89e3;\">"
						+ "\u9996\u9875" + "</a>&nbsp;&nbsp;");
				html.append("<a href=\"javascript:gotoPage("
						+ (curPage - 1)
						+ ")\" style=\"text-decoration: none;color: #1c89e3;\">"
						+ "\u4e0a\u4e00\u9875" + "</a>&nbsp;&nbsp;");
			}

			if (curPage >= pageCount) {
				html.append("\u4e0b\u4e00\u9875" + "&nbsp;&nbsp;"
						+ "\u672b\u9875" + "&nbsp;&nbsp;");
			}

			if (curPage < pageCount) {
				html.append("<a href=\"javascript:gotoPage("
						+ (curPage + 1)
						+ ")\" style=\"text-decoration: none;color: #1c89e3;\">"
						+ "\u4e0b\u4e00\u9875" + "</a>&nbsp;&nbsp;");
				html.append("<a href=\"javascript:gotoPage("
						+ pageCount
						+ ")\" style=\"text-decoration: none;color: #1c89e3;\">"
						+ "\u672b\u9875" + "</a>&nbsp;&nbsp;");
			}

			html.append("\u7b2c" + "<strong>" + curPage + "</strong>"
					+ "\u9875" + "&nbsp;&nbsp;");

			html.append("\u5171" + "<strong>" + pageCount + "</strong>"
					+ "\u9875" + "&nbsp;&nbsp;");

			html.append("\u5230\u7b2c"
					+ "<input id=\"textFooter\" type=\"text\" size=\"1\"/>"
					+ "\u9875" + "&nbsp;&nbsp;");

			html.append("<a href=\"javascript:gotoPage(document.getElementById('textFooter').value)\" ");
			html.append("style=\"text-decoration: none;color: #1c89e3;\">");
			html.append("\u8df3\u8f6c");
			html.append("</a>&nbsp;&nbsp;&nbsp;\n");

			/*
			 * html.append("\u6bcf\u9875\u663e\u793a"); String[]
			 * perPageSelectDatas = perPageSelectData.split(","); html.append(
			 * "<select name=\"page.pageSize\" onchange=\"javascript:gotoPage(1)\">"
			 * ); for(int i=0;i<perPageSelectDatas.length;i++){
			 * html.append("<option value='"+perPageSelectDatas[i]+"' " +
			 * (pageSize == Integer.parseInt(perPageSelectDatas[i]) ? "selected"
			 * : "") + ">"+perPageSelectDatas[i]+"</option>"); }
			 * html.append("</select>"); html.append("\u6761\u6570\u636e");
			 */

			html.append("</td></tr>\n");
			html.append("</table>\n");
			htmlString = html.toString();
		}
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public int getCurPage() {
		return curPage;
	}

	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}

	public int getRowCount() {
		return rowCount;
	}

	public String getHtmlString() {
		return htmlString;
	}

	public void setHtmlString(String htmlString) {
		this.htmlString = htmlString;
	}

	@SuppressWarnings("rawtypes")
	public List getDataList() {
		return dataList;
	}

	@SuppressWarnings("rawtypes")
	public void setDataList(List dataList) {
		this.dataList = dataList;
	}

	public String getSortValue() {
		return sortValue;
	}

	public void setSortValue(String sortValue) {
		this.sortValue = sortValue;
	}

	public boolean isHasAscing() {
		return hasAscing;
	}

	public void setHasAscing(boolean hasAscing) {
		this.hasAscing = hasAscing;
	}

	public int getCutPageOrNot() {
		return cutPageOrNot;
	}

	public void setCutPageOrNot(int cutPageOrNot) {
		this.cutPageOrNot = cutPageOrNot;
	}

}
