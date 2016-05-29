package net.tetrakoopa.mdua.util;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

public class ResourcesUtil {

	
	public static String getString(Context context, int stringId) {
		return context.getResources().getString(stringId);
	}
	public static String getStringf(Context context, int stringId, Object... objects) {
		final String fmt = getString(context, stringId);
		if (fmt == null)
			return null;
		return String.format(fmt, objects);
	}

	public static String getNamefromId(Context context, int id) {
		return "android.resource://"+context.getPackageName() +"/"+id;
	}

	/**
	 * @param context can be null is <code>uri.scheme</code> is 'file'
	 */
	public static String getFilenamefromUri(Context context, Uri uri) {
		final String scheme = uri.getScheme();
		if (scheme.equals("file")) {
			return uri.getLastPathSegment();
		}
		if (scheme.equals("content")) {
			final String[] proj = { MediaStore.Images.Media.TITLE };
			final Cursor cursor = context.getContentResolver().query(uri, proj, null, null, null);
			try {
				if (cursor != null && cursor.getCount() != 0) {
					int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.TITLE);
					cursor.moveToFirst();
					return cursor.getString(columnIndex);
				}
			}
			finally {
				if (cursor != null) {
					cursor.close();
				}
			}
		}
		throw new IllegalStateException("Don't know how to retrieve filename from Uri whose scheme is '"+scheme+"'");
	}
}
