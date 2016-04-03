package net.tetrakoopa.mdu.util;


import net.tetrakoopa.mdu.util.FileUtil;

import java.lang.System;

public class FileUtilTest {

    public String fileSizeAsString(long size, String byteSuffix) {
        return fileSizeAsString(size, 0, byteSuffix);
    }

	public static void main (String args[]) {
		String formatedSize;
		long size;

		size = 2000000;
		formatedSize = FileUtil.fileSizeAsString(size);

		System.out.println("formatedSize for "+size+" = '"+formatedSize+"'");
	}

}
