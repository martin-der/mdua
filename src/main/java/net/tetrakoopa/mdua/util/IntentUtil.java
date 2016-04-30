package net.tetrakoopa.mdua.util;

import android.content.Context;
import android.content.Intent;

import android.os.Bundle;
import android.os.Parcelable;

import java.io.Serializable;

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

		final Bundle sourceExtras = source.getExtras();
		for (String extra : extras) {
			final Object extraValue = sourceExtras.get(extra);
			if (extraValue!=null) {
				if (extraValue instanceof Parcelable)
					destination.putExtra(extra, (Parcelable)extraValue);

				else if (extraValue instanceof String)
					destination.putExtra(extra, (String)extraValue);

				else if (extraValue instanceof Serializable)
					destination.putExtra(extra, (Serializable)extraValue);

				else
					throw new IllegalStateException("Don't know how to handle Intent extra '"+extra+"' of type "+extra.getClass().getName());
			}
		}

	}

}
