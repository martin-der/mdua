package net.tetrakoopa.mdua.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import net.tetrakoopa.mdua.R;
import net.tetrakoopa.mdua.util.ResourcesUtil;
import net.tetrakoopa.mdua.view.mapping.annotation.Label;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class LabelInputBuilder<BEAN> extends AlertDialog.Builder {

	private final ViewMappingHelper<BEAN> viewMappingHelper;

	private final View rootView;

	private String upperText;
	private String lowerText;

	public LabelInputBuilder(Context context, Class<BEAN> beanClass) {
		super(context);
		viewMappingHelper = new ViewMappingHelper(beanClass);
		final LayoutInflater inflater = LayoutInflater.from(getContext());
		rootView = inflater.inflate(R.layout.dialog_edit_bean, null);
		setView(rootView);
	}

	public static class LabelProvider {
		private Map<String, String> labels;
		public String transform(String label) { return label; }

		public boolean containsLabel(String name) {	
			return labels != null && labels.containsKey(name);
		}
		public String getLabel(String name) {
			if ( labels==null) return null;
			return labels.get(name);
		}
		private String putLabel(String name, String label) {
			if (labels == null)
				labels = new HashMap<>();
			return labels.put(name, label);
		}
	}

	public enum ValidationLocation {
		END_OF_LINE, UNDER_FIELD, HINT
	}

	public void populate ( TableLayout table, Object bean, ValidationLocation validation, LabelProvider labelProvider, String... attributes ) {
		final Context context = table.getContext();
		final TableLayout.LayoutParams tableParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT);
		table.setLayoutParams(tableParams);
		final TableRow.LayoutParams labelRowParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
		final TableRow.LayoutParams editRowParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
		for (String attribute :  attributes) {
			final Field field = viewMappingHelper.getField(attribute);

			final TableRow row = new TableRow(context);
			//tableRow.setLayoutParams(tableParams);
			row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

			String label = null;
			if ( labelProvider != null ) {
				if ( labelProvider.containsLabel(attribute) )
					label = labelProvider.getLabel(attribute);
			}
			if ( label == null ) {
				final Label labelHint = field.getAnnotation(Label.class);
				if (labelHint != null) {
					label = context.getResources().getString(labelHint.value());
				} 
			}
			if ( label == null ) {
				label = field.getName();
			}
			if ( labelProvider != null )
				label = labelProvider.transform(label);


			final TextView textView = new TextView(context);
			textView.setText(label);
			textView.setLayoutParams(labelRowParams);
			row.addView(textView);

			final EditText editText = new EditText(context);
			textView.setLayoutParams(editRowParams);
			row.addView(editText);

			table.addView(row);
		}

	}

	public AlertDialog.Builder setUpperText(int stringId, String... formatArgs) {
		setUpperText(getContext().getResources().getString(stringId, formatArgs));
		return this;
	}
	public AlertDialog.Builder setUpperText(int stringId) {
		setUpperText(getContext().getResources().getString(stringId));
		return this;
	}
	public AlertDialog.Builder setUpperText(String text) {
		this.upperText = text;
		return this;
	}
	public AlertDialog.Builder setLowerText(int stringId, String... formatArgs) {
		setLowerText(getContext().getResources().getString(stringId, formatArgs));
		return this;
	}
	public AlertDialog.Builder setLowerText(int stringId) {
		setLowerText(getContext().getResources().getString(stringId));
		return this;
	}
	public AlertDialog.Builder setLowerText(String text) {
		this.lowerText = text;
		return this;
	}
	public AlertDialog.Builder setAll(Object bean, String action, final LabelProvider labelProvider, final DialogInterface.OnClickListener onClickListener, String... attributes) {

		final TableLayout table = (TableLayout)rootView.findViewById(R.id.table);

		populate(table, bean, null, labelProvider, attributes);

		this.setCancelable(true)
			.setPositiveButton(action, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
				if (onClickListener != null) {
					onClickListener.onClick(dialog, id);
				}
				}
			})
			.setNegativeButton(ResourcesUtil.getString(getContext(), android.R.string.cancel), new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
			dialog.cancel();
				if (onClickListener != null) {
					onClickListener.onClick(dialog, id);
				}
				}
			});
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
			this.setOnDismissListener(new DialogInterface.OnDismissListener() {
					@Override
					public void onDismiss(DialogInterface dialog) {
				if (onClickListener != null) {
					onClickListener.onClick(dialog, DialogInterface.BUTTON_NEUTRAL);
				}
				}
			});
		}

		return this;
	}

	public AlertDialog show() {
		if (upperText!=null)
			((TextView)rootView.findViewById(R.id.upper_text)).setText(upperText);
		if (lowerText!=null)
			((TextView)rootView.findViewById(R.id.lower_text)).setText(lowerText);
		return super.show();
	}


}
