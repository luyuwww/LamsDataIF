package com.bwzk.util;
/** 
 * @author  wwwly
 */
public class FtpDownloadFileException extends RuntimeException {
	public FtpDownloadFileException() {
		super();
	}

	public FtpDownloadFileException(String message) {
		super(message);
	}

	public FtpDownloadFileException(String message, Throwable cause) {
		super(message, cause);
	}

	public FtpDownloadFileException(Throwable cause) {
		super(cause);
	}

	protected FtpDownloadFileException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
