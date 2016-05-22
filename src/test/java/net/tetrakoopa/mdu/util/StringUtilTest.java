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

	@Test
	public void majuscule() {
		String string = "hello";
		String majusculeString = StringUtil.firstCharToUpperCase(string);

		assertEquals("Hello", majusculeString);
	}

	@Test
	public void majusculeEmpty() {
		String string = "";
		String majusculeString = StringUtil.firstCharToUpperCase(string);

		assertEquals("", majusculeString);
	}

	@Test
	public void majusculeSingleChar() {
		String string = "a";
		String majusculeString = StringUtil.firstCharToUpperCase(string);

		assertEquals("A", majusculeString);
	}

	@Test
	public void convertSomeCamel2_SOME_CAMEL() {
		String string = "MushroomCookingHelper";
		String UPPER_STRING = StringUtil.camelCase2UpperCaseUnderscoreSeparated(string);

		assertEquals("MUSHROOM_COOKING_HELPER", UPPER_STRING);
	}

}