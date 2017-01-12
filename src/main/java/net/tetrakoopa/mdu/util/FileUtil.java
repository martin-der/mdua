package net.tetrakoopa.mdu.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;

public class FileUtil {

	public static final String DEFAULT_ENCODING = "utf-8";

	public static String filename(String path) {
		final String separator = System.getProperty("path.separator");
		final int pos = path.indexOf(separator);
		return pos < 0 ? path : path.substring(pos+separator.length());
	}

	public static String getAsString(Reader reader) throws IOException {
		final char buffer[] = new char[200];
		int l;
		final StringBuffer stringBufferSource = new StringBuffer();
		while ((l=reader.read(buffer))>0) { stringBufferSource.append(buffer, 0, l); }
		return stringBufferSource.toString();
	}

	public static void copy(InputStream input, OutputStream output) throws IOException {
		copy(input, output, new byte[4*1024]);
	}
	public static void copy(InputStream input, OutputStream output, final byte buffer[]) throws IOException {
		int l;
		while ((l = input.read(buffer)) > 0) {
			output.write(buffer, 0, l);
		}
	}
	public static CharSequence readCharSequence(InputStream inputStream) throws IOException {
		return readCharSequence(inputStream, DEFAULT_ENCODING);
	}
	public static CharSequence readCharSequence(InputStream inputStream, String encoding) throws IOException {
		final BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, encoding));
		try {
			String line;
			StringBuilder buffer = new StringBuilder();
			while ((line = in.readLine()) != null)
				buffer.append(line).append('\n');
			return buffer;
		} finally {
			in.close();
		}
	}

	public static byte[] readContent(InputStream input) throws IOException {
		final ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		copy(input, buffer, new byte[4*1024]);
		return buffer.toByteArray();
	}

	/**
	 * @Deprecated
	 * @see FormatterUtil.fileSizeAsString(long);
	 */
	@Deprecated
	public static String fileSizeAsString(long size) {
		return FormatterUtil.fileSizeAsString(size);
	}


	/**
	 * @Deprecated
	 * @see FormatterUtil.fileSizeAsString(size, format);
	 */
	@Deprecated
	public static String fileSizeAsString(long size, String format) {
		return FormatterUtil.fileSizeAsString(size, format);
	}

}
