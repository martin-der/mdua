package net.tetrakoopa.mdua.view.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import net.tetrakoopa.mdua.util.TextOrStringResource;

public class DontShowAgainLinkedToPreference {

	private final boolean defaultValue;
	private final String name;
	public final String key;
	private final int mode;
	public boolean result;
	private final TextOrStringResource text;

	public DontShowAgainLinkedToPreference(boolean defaultValue, String name, String key) {
		this(defaultValue, name, key, null, Context.MODE_PRIVATE);
	}
	public DontShowAgainLinkedToPreference(boolean defaultValue, String name, String key, int mode) {
		this(defaultValue, name, key, null, mode);
	}
	public DontShowAgainLinkedToPreference(boolean defaultValue, String name, String key, TextOrStringResource text) {
		this(defaultValue, name, key, text, Context.MODE_PRIVATE);
	}
	public DontShowAgainLinkedToPreference(boolean defaultValue, String name, String key, TextOrStringResource text, int mode) {
		this.defaultValue = defaultValue; this.name = name; this.key = key;
		this.mode = mode;
		this.text = text;
	}

	public SharedPreferences getPreferences(Context context) {
		return context.getSharedPreferences(name, mode);
	}
	public final boolean getValue(Context context) {
		return getPreferences(context).getBoolean(key, defaultValue);
	}
	public final boolean setValue(Context context, boolean value) {
		return getPreferences(context).edit().putBoolean(key, value).commit();
	}
	public final String getText(Context context) {
		return text.getText(context);
	}

	public static class Default extends DontShowAgainLinkedToPreference {

		public Default(boolean defaultValue, String key) {
			this(defaultValue, key, null);
		}
		public Default(boolean defaultValue, String key, TextOrStringResource text) {
			super(defaultValue, null, key, text, Context.MODE_PRIVATE);
		}

		public SharedPreferences getPreferences(Context context) {
			return PreferenceManager.getDefaultSharedPreferences(context);
		}

	}

}
