package net.tetrakoopa.mdua.view.util;

import net.tetrakoopa.mdua.R;
import net.tetrakoopa.mdua.SystemValues;
import net.tetrakoopa.mdua.util.ResourcesUtil;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.AsyncTask;
import android.os.Build;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;

import java.util.concurrent.Executor;

public class SystemUIUtil {

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

		final CheckBox dontShowAgainCheckBox = prepareBuilderLayout(builder, dontShowAgain);

		builder.setCancelable(true)
				.setPositiveButton(ResourcesUtil.getString(context, android.R.string.ok), new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						if (dontShowAgain != null) {
							final boolean result = dontShowAgain.getViewValue(dontShowAgainCheckBox.isChecked());
							if (dontShowAgain.key != null) {
								dontShowAgain.setValue(context, result);
							}
						}
						if (onClickListener != null) {
							onClickListener.onClick(dialog, id);
						}
						dialog.cancel();
					}
				});

		redirectDismissClickIfNeeded(builder, onClickListener);

		builder.setTitle(titre);
		if (iconId != 0)
			builder.setIcon(iconId);

		return builder;
	}

	public static void showActionCancelDialog(final Context context, String titre, String action, String message, final DialogInterface.OnClickListener onClickListener) {
		final AlertDialog.Builder builder = createActionCancelDialogBuilderWithoutMessage(context, titre, action, null, onClickListener, 0);
		builder.setMessage(message).show();
	}
	public static void showActionCancelHtmlDialog(final Context context, String titre, String action, String message, final DialogInterface.OnClickListener onClickListener) {
		final AlertDialog.Builder builder = createActionCancelDialogBuilderWithoutMessage(context, titre, action, null, onClickListener, 0);
		builder.setMessage(Html.fromHtml(message)).show();
	}

	public static void showActionCancelDialog(final Context context, String titre, String action, String message, final DialogInterface.OnClickListener onClickListener, int iconId) {
		final AlertDialog.Builder builder = createActionCancelDialogBuilderWithoutMessage(context, titre, action, null, onClickListener, iconId);
		builder.setMessage(message).show();
	}
	public static void showActionCancelHtmlDialog(final Context context, String titre, String action, String message, final DialogInterface.OnClickListener onClickListener, int iconId) {
		final AlertDialog.Builder builder = createActionCancelDialogBuilderWithoutMessage(context, titre, action, null, onClickListener, iconId);
		builder.setMessage(Html.fromHtml(message)).show();
	}

	public static void showActionCancelDialog(final Context context, String titre, String action, String message, final DontShowAgainLinkedToPreference dontShowAgain, final DialogInterface.OnClickListener onClickListener) {
		final AlertDialog.Builder builder = createActionCancelDialogBuilderWithoutMessage(context, titre, action, dontShowAgain, onClickListener, 0);
		builder.setMessage(message).show();
	}
	public static void showActionCancelHtmlDialog(final Context context, String titre, String action, String message, final DontShowAgainLinkedToPreference dontShowAgain, final DialogInterface.OnClickListener onClickListener) {
		final AlertDialog.Builder builder = createActionCancelDialogBuilderWithoutMessage(context, titre, action, dontShowAgain, onClickListener, 0);
		builder.setMessage(Html.fromHtml(message)).show();
	}

	public static void showActionCancelDialog(final Context context, String titre, String action, String message, final DontShowAgainLinkedToPreference dontShowAgain, final DialogInterface.OnClickListener onClickListener, int iconId) {
		final AlertDialog.Builder builder = createActionCancelDialogBuilderWithoutMessage(context, titre, action, dontShowAgain, onClickListener, iconId);
		builder.setMessage(message).show();
	}
	public static void showActionCancelHtmlDialog(final Context context, String titre, String action, String message, final DontShowAgainLinkedToPreference dontShowAgain, final DialogInterface.OnClickListener onClickListener, int iconId) {
		final AlertDialog.Builder builder = createActionCancelDialogBuilderWithoutMessage(context, titre, action, dontShowAgain, onClickListener, iconId);
		builder.setMessage(Html.fromHtml(message)).show();
	}

	private static AlertDialog.Builder createActionCancelDialogBuilderWithoutMessage(final Context context, String titre, String action, final DontShowAgainLinkedToPreference dontShowAgain, final DialogInterface.OnClickListener onClickListener, int iconId) {

		final AlertDialog.Builder builder = new AlertDialog.Builder(context);

		final CheckBox dontShowAgainCheckBox = prepareBuilderLayout(builder, dontShowAgain);

		final boolean cancelButtonIsNegative = false;

		final DialogInterface.OnClickListener clickListener = new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				if (dontShowAgain != null) {
					if (id==DialogInterface.BUTTON_POSITIVE || id==DialogInterface.BUTTON_NEGATIVE) {
						final boolean result = dontShowAgain.getViewValue(dontShowAgainCheckBox.isChecked());
						if (dontShowAgain.key != null) {
							dontShowAgain.setValue(context, result);
						}
					}
				}
				if (onClickListener != null) {
					onClickListener.onClick(dialog, id);
				}
				dialog.cancel();
			}
		};

		builder.setCancelable(true)
			.setPositiveButton(action, onClickListener);
		if (cancelButtonIsNegative) {
			builder.setNegativeButton(ResourcesUtil.getString(context, android.R.string.cancel), clickListener);
		} else {
			builder.setNeutralButton(ResourcesUtil.getString(context, android.R.string.cancel), clickListener);
		}

		redirectDismissClickIfNeeded(builder, onClickListener);

		builder.setTitle(titre);
		if (iconId != 0)
			builder.setIcon(iconId);

		return builder;
	}

	public static void showActionAndOppositeDialog(final Context context, String titre, String action, String oppositeAction, String message, final DialogInterface.OnClickListener onClickListener) {
		final AlertDialog.Builder builder = createActionAndOppositeDialogBuilderWithoutMessage(context, titre, action, oppositeAction, null, onClickListener, 0);
		builder.setMessage(message).show();
	}
	public static void showActionAndOppositeHtmlDialog(final Context context, String titre, String action, String oppositeAction, String message, final DialogInterface.OnClickListener onClickListener) {
		final AlertDialog.Builder builder = createActionAndOppositeDialogBuilderWithoutMessage(context, titre, action, oppositeAction, null, onClickListener, 0);
		builder.setMessage(Html.fromHtml(message)).show();
	}

	public static void showActionAndOppositeDialog(final Context context, String titre, String action, String oppositeAction, String message, final DialogInterface.OnClickListener onClickListener, int iconId) {
		final AlertDialog.Builder builder = createActionAndOppositeDialogBuilderWithoutMessage(context, titre, action, oppositeAction, null, onClickListener, iconId);
		builder.setMessage(message).show();
	}
	public static void showActionAndOppositeHtmlDialog(final Context context, String titre, String action, String oppositeAction, String message, final DialogInterface.OnClickListener onClickListener, int iconId) {
		final AlertDialog.Builder builder = createActionAndOppositeDialogBuilderWithoutMessage(context, titre, action, oppositeAction, null, onClickListener, iconId);
		builder.setMessage(Html.fromHtml(message)).show();
	}

	public static void showActionAndOppositeDialog(final Context context, String titre, String action, String oppositeAction, String message, final DontShowAgainLinkedToPreference dontShowAgain, final DialogInterface.OnClickListener onClickListener) {
		final AlertDialog.Builder builder = createActionAndOppositeDialogBuilderWithoutMessage(context, titre, action, oppositeAction, dontShowAgain, onClickListener, 0);
		builder.setMessage(message).show();
	}
	public static void showActionAndOppositeHtmlDialog(final Context context, String titre, String action, String oppositeAction, String message, final DontShowAgainLinkedToPreference dontShowAgain, final DialogInterface.OnClickListener onClickListener) {
		final AlertDialog.Builder builder = createActionAndOppositeDialogBuilderWithoutMessage(context, titre, action, oppositeAction, dontShowAgain, onClickListener, 0);
		builder.setMessage(Html.fromHtml(message)).show();
	}

	public static void showActionAndOppositeDialog(final Context context, String titre, String action, String oppositeAction, String message, final DontShowAgainLinkedToPreference dontShowAgain, final DialogInterface.OnClickListener onClickListener, int iconId) {
		final AlertDialog.Builder builder = createActionAndOppositeDialogBuilderWithoutMessage(context, titre, action, oppositeAction, dontShowAgain, onClickListener, iconId);
		builder.setMessage(message).show();
	}
	public static void showActionAndOppositeHtmlDialog(final Context context, String titre, String action, String oppositeAction, String message, final DontShowAgainLinkedToPreference dontShowAgain, final DialogInterface.OnClickListener onClickListener, int iconId) {
		final AlertDialog.Builder builder = createActionAndOppositeDialogBuilderWithoutMessage(context, titre, action, oppositeAction, dontShowAgain, onClickListener, iconId);
		builder.setMessage(Html.fromHtml(message)).show();
	}

	private static AlertDialog.Builder createActionAndOppositeDialogBuilderWithoutMessage(final Context context, String titre, String action, String oppositeAction, final DontShowAgainLinkedToPreference dontShowAgain, final DialogInterface.OnClickListener onClickListener, int iconId) {

		final AlertDialog.Builder builder = new AlertDialog.Builder(context);

		final CheckBox dontShowAgainCheckBox = prepareBuilderLayout(builder, dontShowAgain);

		final DialogInterface.OnClickListener clickListener = new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				if (dontShowAgain != null) {
					if (id==DialogInterface.BUTTON_POSITIVE || id==DialogInterface.BUTTON_NEGATIVE) {
						final boolean result = dontShowAgain.getViewValue(dontShowAgainCheckBox.isChecked());
						if (dontShowAgain.key != null) {
							dontShowAgain.setValue(context, result);
						}
					}
				}
				if (onClickListener != null) {
					onClickListener.onClick(dialog, id);
				}
				dialog.cancel();
			}
		};

		builder.setCancelable(true)
				.setPositiveButton(action, clickListener)
				.setNegativeButton(oppositeAction, clickListener);

		redirectDismissClickIfNeeded(builder, onClickListener);

		builder.setTitle(titre);
		if (iconId != 0)
			builder.setIcon(iconId);

		return builder;
	}

	/**
	 * @param builder
	 * @param onClickListener maybe null
	 */
	private static void redirectDismissClickIfNeeded(final AlertDialog.Builder builder, final DialogInterface.OnClickListener onClickListener) {
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

	}

	/**
	 * Set the layout of the builder according to the presence of a "Don't show again" question<br/>
	 * @param builder @notnull
	 * @param dontShowAgain may be null
	 * @return the "don't show again checkbox' or null if <code>dontShowAgain</code> was null
	 */
	private static CheckBox prepareBuilderLayout(AlertDialog.Builder builder, DontShowAgainLinkedToPreference dontShowAgain) {
		final Context context = builder.getContext();
		final CheckBox dontShowAgainCheckBox;
		if (dontShowAgain != null) {
			final LayoutInflater inflater = LayoutInflater.from(context);
			final View view = inflater.inflate(R.layout.dialog_with_dontshow_checkbox, null);
			dontShowAgainCheckBox = (CheckBox) view.findViewById(R.id.dont_show);
			final boolean actualValue = dontShowAgain.getValue(context);
			dontShowAgainCheckBox.setChecked(dontShowAgain.getViewValue(actualValue));
			final String text = dontShowAgain.getText(context);
			if (text != null)
				dontShowAgainCheckBox.setText(text);
			else
				dontShowAgainCheckBox.setText(SystemValues.R.string.dont_ask_again);
			builder.setView(view);
		} else {
			dontShowAgainCheckBox = null;
		}
		return dontShowAgainCheckBox;
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
