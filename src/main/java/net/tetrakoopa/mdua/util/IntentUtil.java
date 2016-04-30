package net.tetrakoopa.mdua.util;

import android.content.Context;
import android.content.Intent;

import android.os.Parcelable;

public class IntentUtil {

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

	/**
	 * Copy action and type from <code>source</code> intent to <code>destination</code> intent.<br/>
	 * Also copy all extra parameters listed in <code>extras</code>
	 */
	public static void mimicIntent(Intent source, Intent destination, String... extras) {
		final String action = source.getAction();
		final String type = source.getType();
		if (action!=null)
			destination.setAction(action);
		if (type!=null)
			destination.setType(type);

		for (String extra : extras) {
			final Parcelable extraValue = source.getParcelableExtra(extra);
			if (extraValue!=null)
				destination.putExtra(extra, extraValue);
		}

	}

}
