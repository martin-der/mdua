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
	private boolean result;
	private final TextOrStringResource text;

	private final boolean negatedView;

	public DontShowAgainLinkedToPreference(boolean defaultValue, String name, String key) {
		this(defaultValue, name, key, false, null, Context.MODE_PRIVATE);
	}
	public DontShowAgainLinkedToPreference(boolean defaultValue, String name, String key, boolean negatedView) {
		this(defaultValue, name, key, negatedView, null, Context.MODE_PRIVATE);
	}
	public DontShowAgainLinkedToPreference(boolean defaultValue, String name, String key, int mode) {
		this(defaultValue, name, key, false, null, mode);
	}
	public DontShowAgainLinkedToPreference(boolean defaultValue, String name, String key, boolean negatedView, int mode) {
		this(defaultValue, name, key, negatedView, null, mode);
	}
	public DontShowAgainLinkedToPreference(boolean defaultValue, String name, String key, TextOrStringResource text) {
		this(defaultValue, name, key, false, text, Context.MODE_PRIVATE);
	}
	public DontShowAgainLinkedToPreference(boolean defaultValue, String name, String key, boolean negatedView, TextOrStringResource text) {
		this(defaultValue, name, key, negatedView, text, Context.MODE_PRIVATE);
	}
	public DontShowAgainLinkedToPreference(boolean defaultValue, String name, String key, TextOrStringResource text, int mode) {
		this(defaultValue, name, key, false, text, Context.MODE_PRIVATE);
	}
	public DontShowAgainLinkedToPreference(boolean defaultValue, String name, String key, boolean negatedView, TextOrStringResource text, int mode) {
		this.defaultValue = defaultValue; this.name = name; this.key = key;
		this.mode = mode;
		this.text = text;
		this.negatedView = negatedView;
		this.result = defaultValue;
	}

	public SharedPreferences getPreferences(Context context) {
		return context.getSharedPreferences(name, mode);
	}

	public boolean getResult() {
		return result;
	}

	public final boolean getViewValue(boolean value) {
		return negatedView ^ value;
	}
	public final boolean getValue(Context context) {
		return getPreferences(context).getBoolean(key, defaultValue);
	}
	public final boolean setValue(Context context, boolean value) {
		result = value;
		return getPreferences(context).edit().putBoolean(key, value).commit();
	}
	public final String getText(Context context) {
		return text.getText(context);
	}


	// -- Specialisations --

	public static class Negated extends DontShowAgainLinkedToPreference {
		public Negated(boolean defaultValue, String name, String key) {
			this(defaultValue, name, key, null, Context.MODE_PRIVATE);
		}

		public Negated(boolean defaultValue, String name, String key, int mode) {
			this(defaultValue, name, key, null, mode);
		}

		public Negated(boolean defaultValue, String name, String key, TextOrStringResource text) {
			this(defaultValue, name, key, text, Context.MODE_PRIVATE);
		}

		public Negated(boolean defaultValue, String name, String key, TextOrStringResource text, int mode) {
			super(defaultValue, name, key, true, text, mode);
		}
	}

	public static class DefaultNegated extends Default {

		public DefaultNegated(boolean defaultValue, String key) {
			this(defaultValue, key, null);
		}
		public DefaultNegated(boolean defaultValue, String key, TextOrStringResource text) {
			super(defaultValue, key, true, text);
		}
	}
	public static class Default extends DontShowAgainLinkedToPreference {

		public Default(boolean defaultValue, String key) {
			this(defaultValue, key, false, null);
		}
		public Default(boolean defaultValue, String key, boolean negated) {
			this(defaultValue, key, negated, null);
		}
		public Default(boolean defaultValue, String key, TextOrStringResource text) {
			this(defaultValue, key, false, text);
		}
		public Default(boolean defaultValue, String key, boolean negated, TextOrStringResource text) {
			super(defaultValue, null, key, negated, text, Context.MODE_PRIVATE);
		}

		public SharedPreferences getPreferences(Context context) {
			return PreferenceManager.getDefaultSharedPreferences(context);
		}

	}

}
