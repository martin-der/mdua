package net.tetrakoopa.mdu.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StringUtilTest {

	@Test
	public void makeByte2Hex() {
		byte bytes[] = {(byte)0xff, (byte)0x49, (byte)0xb1,  (byte)0x5e};
		String bytesString = StringUtil.byte2Hex(bytes);

		assertEquals("ff 49 b1 5e", bytesString);

	}
}