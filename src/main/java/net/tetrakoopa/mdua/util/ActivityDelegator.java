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
				return ResourcesUtil.getFilenamefromUri(context, uri);
			}

		}
	}

	public static void pickContent(Activity activity, String type, boolean multiple, int activityCode, int title) {
		pickContent(activity, type, multiple, activityCode, activity.getResources().getString(title));
	}
	/**
	 * @param type : can be null, defaults to '* /*'
	 */
	public static void pickContent(Activity activity, String type, boolean multiple, int activityCode, String title) {
		final Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		// message(R.string.title_pick_file_to_share);
		intent.setType(type == null ? "*/*" : type);
		activity.startActivityForResult(intent, activityCode);
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
