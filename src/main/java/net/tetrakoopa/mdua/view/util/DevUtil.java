package net.tetrakoopa.mdua.view.util;

import java.lang.Thread.UncaughtExceptionHandler;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;

public class DevUtil {

	public static class PromptThenDelegateUncaughtExceptionHandler implements UncaughtExceptionHandler {

		private final Activity activity;
		private final UncaughtExceptionHandler delegatedHandler;

		public PromptThenDelegateUncaughtExceptionHandler(Activity activity, UncaughtExceptionHandler delegatedHandler) {
			this.activity = activity;
			this.delegatedHandler = delegatedHandler;
		}

		@Override
		public void uncaughtException(Thread thread, Throwable throwable) {
			DevUtil.showException(activity, throwable);
		}

	}

	public static void addDevHandler(Activity activity) {
		Thread.currentThread().setUncaughtExceptionHandler(new PromptThenDelegateUncaughtExceptionHandler(activity, Thread.currentThread().getUncaughtExceptionHandler()));
	}

	public static void showException(final Context context, Throwable ex) {
		SystemUIUtil.showOKDialog(context, "Oops!", ex.getMessage(), android.R.drawable.ic_dialog_alert, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				((Activity) context).finish();
			}
		});
	}
}
