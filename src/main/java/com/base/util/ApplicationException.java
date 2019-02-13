/**
 * 
 */
package com.base.util;

/**
 * 业务异常类.
 * 
 * @author weidong
 * 
 */
@SuppressWarnings("serial")
public class ApplicationException extends RuntimeException {
	/**
	 * 构造方法.
	 */
	public ApplicationException() {
		super();
	}
	public ApplicationException(String message) {
		super(message);
	}
}
