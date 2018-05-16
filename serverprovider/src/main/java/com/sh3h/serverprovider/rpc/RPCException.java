/**
 * @author qiweiwei 2013-7-3 下午2:07:21
 *
 */
package com.sh3h.serverprovider.rpc;

/**
 * ApiException, 自定义异常
 */
public class RPCException extends Exception {

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 8024911776557836710L;

	/**
	 * 构造函数
	 *
	 * @param error 错误内容
	 */
	public RPCException(Object error)
	{
		super(error.toString());
	}

	/**
	 * 构造函数
	 *
	 * @param message 错误描述
	 * @param innerException 内部异常
	 */
	public RPCException(String message, Throwable innerException)
	{
		super(message, innerException);
	}

}
