package net.tetrakoopa.mdu.util;

public class StringUtil {

	public static String firstCharToUpperCase(String string) {
		if (string == null) {
			return null;
		}
		if (string.length()>0) {
			final StringBuffer majusculeString = new StringBuffer();
			majusculeString.append(Character.toUpperCase(string.charAt(0)));
			majusculeString.append(string.substring(1));
			return majusculeString.toString();
		} else {
			return string;
		}
	}
    public static char[] escapeRegex(char string[]) {
        final StringBuffer escaped = new StringBuffer();
        final int len = string.length;
        for (int i =0; i<len; i++) {
            final char c = string[i];
            if (".*?()[]{}^S|+".contains(String.valueOf(c))) {
                escaped.append('\\');
            }
            if (c=='\\' && i<len-1) {
                i++;
                continue;
            }
            escaped.append(c);
        }
        return escaped.toString().toCharArray();
    }

	public static String byte2Hex(byte[] bytes) {
		return byte2Hex(bytes, " ");
	}
	public static String byte2Hex(byte[] bytes, String twoBytesSeparator) {
		final StringBuilder sb = new StringBuilder();
		boolean first = true;
		for (int i = 0; i < bytes.length; i++) {
			if (first) {
				first = false;
			} else if (twoBytesSeparator != null) {
				sb.append(twoBytesSeparator);
			}
			int b = bytes[i] & 0xff;
			if (b < 0x10)
				sb.append('0');
			sb.append(Integer.toHexString(b));
		}
		return sb.toString();
	}

}
