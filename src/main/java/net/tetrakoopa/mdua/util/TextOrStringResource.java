package net.tetrakoopa.mdua.util;

import android.content.Context;

public class TextOrStringResource {

	private final String text;
	private final int resource;

	public TextOrStringResource(String text) {
		this.text = text;
		this.resource = 0;
	}
	public TextOrStringResource(int resourceId) {
		this.text = null;
		this.resource = resourceId;
	}

	public String getText(Context context) {
		if (text != null) {
			return text;
		} else {
			return ResourcesUtil.getString(context, resource);
		}
	}
}
