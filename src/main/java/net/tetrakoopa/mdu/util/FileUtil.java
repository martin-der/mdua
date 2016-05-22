package net.tetrakoopa.mdu.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;

public class FileUtil {

	public static final String DEFAULT_ENCODING = "utf-8";

	public static String getAsString(Reader reader) throws IOException {
		final char buffer[] = new char[200];
		int l;
		final StringBuffer stringBufferSource = new StringBuffer();
		while ((l=reader.read(buffer))>0) { stringBufferSource.append(buffer, 0, l); }
		return stringBufferSource.toString();
	}

	public static void copy(InputStream input, OutputStream output) throws IOException {
		copy(input, output, new byte[200]);
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

	/**
	 * Equivalent to <code>fileSizeAsString(size, "%1$.3f %2$sb.")</code>
	 */
	public static String fileSizeAsString(long size) {
		return fileSizeAsString(size, "%1$.3f %2$sb.");
	}

	/**
	 * @param format format used to display result, accept two place holders
	 *            <table>
	 *                <tr><th>Place Holder</th><th>Position</th></th><th>Type</th><th>example</th></tr>
	 *                <tr><td>size</td><td>1</td></td><td>Float or Double</td><td><code>%1$.1</code></td></tr>
	 *                <tr><td>dimension</td><td>2</td><td>String</td><td><code>%2$s</code></td></tr>
	 *            </table>
	 *
	 * example <code>fileSizeAsString(3000000, "%1$.3f %2$sb.")</code>
	 */
	public static String fileSizeAsString(long size, String format) {
		final double bytes = size;
		final double kilobytes = (bytes / 1024);
		if (kilobytes <= 1)
			return String.format(format, bytes, "");
		final double megabytes = (kilobytes / 1024);
		if (megabytes <= 1)
			return String.format(format, kilobytes, "k");
		final double gigabytes = (megabytes / 1024);
		if (gigabytes <= 1)
			return String.format(format, megabytes, "m");
		final double terabytes = (gigabytes / 1024);
		if (terabytes <= 1)
			return String.format(format, gigabytes, "g");
		final double petabytes = (terabytes / 1024);
		if (petabytes <= 1)
			return String.format(format, terabytes, "t");
		final double exabytes = (petabytes / 1024);
		if (exabytes <= 1)
			return String.format(format, petabytes, "p");
		final double zettabytes = (exabytes / 1024);
		if (zettabytes <= 1)
			return String.format(format, exabytes, "e");
		final double yottabytes = (zettabytes / 1024);
		if (yottabytes <= 1)
			return String.format(format, zettabytes, "z");
		return String.format(format, yottabytes, "y");
	}

}
