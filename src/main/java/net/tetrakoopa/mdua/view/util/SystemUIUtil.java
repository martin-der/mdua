package net.tetrakoopa.mdua.view.util;

import net.tetrakoopa.mdua.R;
import net.tetrakoopa.mdua.util.ResourcesUtil;
import net.tetrakoopa.mdua.util.TextOrStringResource;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;

import java.util.concurrent.Executor;

public class SystemUIUtil {

	public static class Values {
		public static class Strings {
			public int dont_show_again = R.string.mdua_dont_show_again;
			public int dont_ask_again = R.string.mdua_dont_ask_again;
		}

		public final Strings strings = new Strings();
	}
	public static final Values values_R = new Values();

	public static class DontShowAgainLinkedToPreference {
		public boolean defaultValue;
		public String name;
		public String key;
		public int mode;
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

		public boolean getValue(Context context) {
			final SharedPreferences settings = context.getSharedPreferences(name, mode);
			return settings.getBoolean(key, defaultValue);
		}
		public String getText(Context context) {
			return text.getText(context);
		};
	}


	private SystemUIUtil() {
	}

	public static void showOkDialog(Context context, String titre, String message) {
		showOkDialog(context, titre, message, null, null, 0);
	}
	public static void showOkHtmlDialog(Context context, String titre, String message) {
		showOkHtmlDialog(context, titre, message, null, null, 0);
	}

	public static void showOkDialog(Context context, String titre, String message, DontShowAgainLinkedToPreference dontShowAgain) {
		showOkDialog(context, titre, message, dontShowAgain, null, 0);
	}
	public static void showOkHtmlDialog(Context context, String titre, String message, DontShowAgainLinkedToPreference dontShowAgain) {
		showOkHtmlDialog(context, titre, message, dontShowAgain, null, 0);
	}

	public static void showOkDialog(Context context, String titre, String message, int iconId) {
		showOkDialog(context, titre, message, null, null, iconId);
	}
	public static void showOkHtmlDialog(Context context, String titre, String message, int iconId) {
		showOkHtmlDialog(context, titre, message, null, null, iconId);
	}

	public static void showOkDialog(Context context, String titre, String message, DontShowAgainLinkedToPreference dontShowAgain, int iconId) {
		showOkDialog(context, titre, message, dontShowAgain, null, iconId);
	}
	public static void showOKHtmlDialog(Context context, String titre, String message, DontShowAgainLinkedToPreference dontShowAgain, int iconId) {
		showOkHtmlDialog(context, titre, message, dontShowAgain, null, iconId);
	}

	public static void showOkDialog(Context context, String titre, String message, DialogInterface.OnClickListener onClickListener) {
		showOkDialog(context, titre, message, null, onClickListener, 0);
	}
	public static void showOKHtmlDialog(Context context, String titre, String message, DialogInterface.OnClickListener onClickListener) {
		showOkHtmlDialog(context, titre, message, null, onClickListener, 0);
	}

	public static void showOkDialog(Context context, String titre, String message, int iconId, DialogInterface.OnClickListener onClickListener) {
		showOkDialog(context, titre, message, null, onClickListener, iconId);
	}
	public static void showOkHtmlDialog(Context context, String titre, String message, int iconId, DialogInterface.OnClickListener onClickListener) {
		showOkHtmlDialog(context, titre, message, null, onClickListener, iconId);
	}

	public static void showOkDialog(Context context, String titre, String message, DontShowAgainLinkedToPreference dontShowAgain, final DialogInterface.OnClickListener onClickListener) {
		showOkDialog(context, titre, message, dontShowAgain, onClickListener, 0);
	}
	public static void showOKHtmlDialog(Context context, String titre, String message, DontShowAgainLinkedToPreference dontShowAgain, final DialogInterface.OnClickListener onClickListener) {
		showOkHtmlDialog(context, titre, message, dontShowAgain, onClickListener, 0);
	}

	public static void showOkDialog(final Context context, String titre, String message, DontShowAgainLinkedToPreference dontShowAgain, final DialogInterface.OnClickListener onClickListener, int iconId) {
		final AlertDialog.Builder builder = createOkDialogBuilderWithoutMessage(context, titre, dontShowAgain, onClickListener, iconId);
		builder.setMessage(message).show();
	}
	public static void showOkHtmlDialog(final Context context, String titre, String message, DontShowAgainLinkedToPreference dontShowAgain, final DialogInterface.OnClickListener onClickListener, int iconId) {
		final AlertDialog.Builder builder = createOkDialogBuilderWithoutMessage(context, titre, dontShowAgain, onClickListener, iconId);
		builder.setMessage(Html.fromHtml(message)).show();
	}

	private static AlertDialog.Builder createOkDialogBuilderWithoutMessage(final Context context, String titre, final DontShowAgainLinkedToPreference dontShowAgain, final DialogInterface.OnClickListener onClickListener, int iconId) {

		final AlertDialog.Builder builder = new AlertDialog.Builder(context);

		final CheckBox dontShowAgainCheckBox;

		if (dontShowAgain != null) {
			final LayoutInflater inflater = LayoutInflater.from(context);
			final View view = inflater.inflate(R.layout.dialog_ok_dontshow_checkbox, null);
			dontShowAgainCheckBox = (CheckBox) view.findViewById(R.id.dont_show);
			dontShowAgain.result = context.getSharedPreferences(dontShowAgain.name, dontShowAgain.mode).getBoolean(dontShowAgain.key, dontShowAgain.defaultValue);
			dontShowAgainCheckBox.setChecked(dontShowAgain.result);
			final String text = dontShowAgain.getText(context);
			if (text != null)
				dontShowAgainCheckBox.setText(text);
			else
				dontShowAgainCheckBox.setText(values_R.strings.dont_show_again);
			builder.setView(view);
		} else {
			dontShowAgainCheckBox = null;
		}

		builder.setCancelable(true)
				.setPositiveButton(ResourcesUtil.getString(context, android.R.string.ok), new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						if (dontShowAgain != null) {
							dontShowAgain.result = dontShowAgainCheckBox.isChecked();
							if (dontShowAgain.name != null && dontShowAgain.key != null) {
								final SharedPreferences settings = context.getSharedPreferences(dontShowAgain.name, dontShowAgain.mode);
								settings.edit().putBoolean(dontShowAgain.key, dontShowAgain.result).commit();
							}
						}
						if (onClickListener != null) {
							onClickListener.onClick(dialog, id);
						}
						dialog.cancel();
					}
				});


		builder.setTitle(titre);
		if (iconId != 0)
			builder.setIcon(iconId);

		return builder;
	}

	public static void showActionCancelDialog(final Context context, String titre, String action, String message, final DialogInterface.OnClickListener onClickListener) {
		final AlertDialog.Builder builder = createActionCancelDialogBuilderWithoutMessage(context, titre, action, onClickListener, 0);
		builder.setMessage(message).show();
	}
	public static void showActionCancelHtmlDialog(final Context context, String titre, String action, String message, final DialogInterface.OnClickListener onClickListener) {
		final AlertDialog.Builder builder = createActionCancelDialogBuilderWithoutMessage(context, titre, action, onClickListener, 0);
		builder.setMessage(Html.fromHtml(message)).show();
	}

	public static void showActionCancelDialog(final Context context, String titre, String action, String message, final DialogInterface.OnClickListener onClickListener, int iconId) {
		final AlertDialog.Builder builder = createActionCancelDialogBuilderWithoutMessage(context, titre, action, onClickListener, iconId);
		builder.setMessage(message).show();
	}
	public static void showActionCancelHtmlDialog(final Context context, String titre, String action, String message, final DialogInterface.OnClickListener onClickListener, int iconId) {
		final AlertDialog.Builder builder = createActionCancelDialogBuilderWithoutMessage(context, titre, action, onClickListener, iconId);
		builder.setMessage(Html.fromHtml(message)).show();
	}

	private static AlertDialog.Builder createActionCancelDialogBuilderWithoutMessage(final Context context, String titre, String action, final DialogInterface.OnClickListener onClickListener, int iconId) {

		final AlertDialog.Builder builder = new AlertDialog.Builder(context);

		builder.setCancelable(true)
			.setPositiveButton(action, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					dialog.cancel();
					if (onClickListener != null) {
						onClickListener.onClick(dialog, id);
					}
				}
			})
			.setNegativeButton(ResourcesUtil.getString(context, android.R.string.cancel), new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					dialog.cancel();
					if (onClickListener != null) {
						onClickListener.onClick(dialog, id);
					}
				}
			});
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
			builder
				.setOnDismissListener(new DialogInterface.OnDismissListener() {
					@Override
					public void onDismiss(DialogInterface dialog) {
						if (onClickListener != null) {
							onClickListener.onClick(dialog, DialogInterface.BUTTON_NEUTRAL);
						}
					}
				});
			}

		builder.setTitle(titre);
		if (iconId != 0)
			builder.setIcon(iconId);

		return builder;
	}

	private static AlertDialog.Builder createInputDialogBuilderWithoutMessage(final Context context, String titre, String action, final DialogInterface.OnClickListener onClickListener, int iconId, Object bean, String... attributes) {

		final AlertDialog.Builder builder = new AlertDialog.Builder(context);

		builder.setCancelable(true)
			.setPositiveButton(action, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					dialog.cancel();
					if (onClickListener != null) {
						onClickListener.onClick(dialog, id);
					}
				}
			})
			.setNegativeButton(ResourcesUtil.getString(context, android.R.string.cancel), new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
				if (onClickListener != null) {
					onClickListener.onClick(dialog, id);
				}
				}
			});
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
			builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
						@Override
						public void onDismiss(DialogInterface dialog) {
				if (onClickListener != null) {
					onClickListener.onClick(dialog, DialogInterface.BUTTON_NEUTRAL);
				}
				}
			});
		}

		builder.setTitle(titre);
		if (iconId != 0)
			builder.setIcon(iconId);

		return builder;
	}

	public static ProgressDialog showProgress(Context context, final AsyncTask task, String titre, String message, final DialogInterface.OnCancelListener listener) {

		final ProgressDialog dialog = ProgressDialog.show(context, titre, message, true, true, new OnCancelListener() {

			@Override
			public void onCancel(DialogInterface dialog) {
				if (listener != null)
					listener.onCancel(dialog);
			}

		});
		task.executeOnExecutor(new Executor() {
			@Override
			public void execute(Runnable command) {

			}
		});
		return dialog;
	}



}
