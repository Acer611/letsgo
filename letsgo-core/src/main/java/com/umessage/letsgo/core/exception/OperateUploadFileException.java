package com.umessage.letsgo.core.exception;

/**  
* @version V1.0  
*/
public class OperateUploadFileException extends RuntimeException {

	/** 
	* @Fields serialVersionUID : -1712721075788285095L
	*/ 
	private static final long serialVersionUID = -1712721075788285095L;

	public OperateUploadFileException() {
		super();
	}

	public OperateUploadFileException(String message) {
		super(message);
	}

	public OperateUploadFileException(Throwable cause) {
		super(cause);
	}

}
