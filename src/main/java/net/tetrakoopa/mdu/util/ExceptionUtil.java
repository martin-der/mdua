package net.tetrakoopa.mdu.util;


public class ExceptionUtil {

	/**
	 * @param throwable : not null
	 * @return throwable messages separated by ' : '
	 */
	public static String getMessages(Throwable throwable) {
		return getMessages(throwable, false, null);
	}

	/**
	 * @param throwable : not null
	 * @param withExceptionName : if <code>true</code> prints exception class name, otherwise only prints exception message
	 * @return throwable messages separated by ' : '
	 */
	public static String getMessages(Throwable throwable, boolean withExceptionName) {
		return getMessages(throwable, withExceptionName, null);
	}

	/**
	 * @param throwable : not null
	 * @param withExceptionName : if <code>true</code> prints exception class name, otherwise only prints exception message
	 * @param separator : if null, defaults to ' : '
	 * @return throwable messages separated by <code>separator</code>
	 */
	public static String getMessages(Throwable throwable, boolean withExceptionName, String separator) {
		if (separator==null)
			separator = " : ";

		boolean first = true;
		final StringBuffer messages = new StringBuffer();

		do {
			if (first)
				first = false;
			else
				messages.append(separator);

			messages.append(withExceptionName ? throwable.toString() : throwable.getMessage());

		} while ((throwable = throwable.getCause())!=null);

		return messages.toString();
	}

}
