package net.tetrakoopa.mdua.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class ActivityDelegator {

	public static void shareText(Context context, int title, String subject, String content) {
		shareText(context, context.getResources().getString(title), subject, content);
	}
	public static void shareText(Context context, String title, String subject, String content) {
		final Intent sharingIntent = new Intent(Intent.ACTION_SEND);
		sharingIntent.setType("text/plain");
		if (subject!=null)
			sharingIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
		sharingIntent.putExtra(Intent.EXTRA_TEXT, content);
		context.startActivity(Intent.createChooser(sharingIntent, title));
	}



	public interface OnPickContentActivityReturnHandler {
		void onFileChoosed(Uri uri);
		void onFilesChoosed(Uri uris[]);
		void onBadTypeChoosed(Uri uris[]);
		void onPickerDismissed();

		abstract class SimpleOneFileHandler implements OnPickContentActivityReturnHandler {

			public final void onFilesChoosed(Uri uris[]) {
				onFileChoosed(uris[0]);
			}
			@Override
			public void onBadTypeChoosed(Uri uris[]) {

			}
			@Override
			public void onPickerDismissed() {

			}

			public String getFilename(Context context, Uri uri) {
				return ResourcesUtil.getNameFromUri(context, uri);
			}
			public String getPath(Context context, Uri uri) {
				return ResourcesUtil.getPathFromUri(context, uri);
			}

		}
	}

	/**
	 * Try to get a file path using <code>android.intent.action.GET_CONTENT</code> activity
	 * @param type : can be <code>null</code>, defaults to '* /*'
	 * @param startUri : can be <code>null</code>, a file URI for suggested file name or starting directory
	 * @param title : can be <code>null</code>, message shown by activity
	 * @param button : can be <code>null</code>, message shown be activity
	 */
	public static void getContent(Activity activity, String type, boolean multiple, int activityCode, Uri startUri, String title, String button) {
		final Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		// message(R.string.title_pick_file_to_share);
		intent.setType(type == null ? "*/*" : type);
		activity.startActivityForResult(intent, activityCode);
	}

	/**
	 * Try to get a file path using <code>org.openintents.action.PICK_FILE</code> activity
	 * @param type : can be <code>null</code>, defaults to '* /*'
	 * @param startUri : can be <code>null</code>, a file URI for suggested file name or starting directory
	 * @param title : can be <code>null</code>, message shown by activity
	 * @param button : can be <code>null</code>, message shown be activity
	 */
	public static void pickContent(Activity activity, String type, boolean multiple, int activityCode, Uri startUri, String title, String button) {
		Intent intent = new Intent("org.openintents.action.PICK_FILE");
		if (startUri != null)
			intent.setData(startUri);
		if (title != null)
			intent.putExtra("org.openintents.extra.TITLE", title); // String
		if (button != null)
			intent.putExtra("org.openintents.extra.BUTTON_TEXT", button); // String
		activity.startActivityForResult(intent, activityCode);
	}
	public static void pickContent(Activity activity, String type, boolean multiple, int activityCode, Uri startUri, /*@LayoutRes*/ int title, /*@LayoutRes*/ int button) {
		pickContent(activity, type, multiple, activityCode, startUri, activity.getResources().getString(title), activity.getResources().getString(button));
	}

	public static void handlePickActivityResponse(int resultCode, Intent data, OnPickContentActivityReturnHandler handler) {
		if (resultCode != Activity.RESULT_OK || data == null) {
			handler.onPickerDismissed();
			return;
		}
		final Uri uri = data.getData();
		handler.onFileChoosed(uri);
	}

}
