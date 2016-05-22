package net.tetrakoopa.mdu.util;


import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FileUtilTest {

	@Test
	public void formattedSizeOfTheSizeOfCatPicture() {
		String formattedSize;
		long size;

		size = 2000000;
		formattedSize = FileUtil.fileSizeAsString(size);
		assertEquals("1,907 mb.", formattedSize);
	}

	@Test
	public void customFormattedSizeOfTheSizeOfASmallMovie() {
		String formattedSize;
		long size;

		size = 600000000;
		formattedSize = FileUtil.fileSizeAsString(size, "%1$.5f (%2$sb)");
		assertEquals("572,20459 (mb)", formattedSize);

	}

}
