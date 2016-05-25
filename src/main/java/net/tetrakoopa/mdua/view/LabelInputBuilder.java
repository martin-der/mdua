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

import net.tetrakoopa.mdu.mapping.MappingHelper;
import net.tetrakoopa.mdua.R;
import net.tetrakoopa.mdua.util.ResourcesUtil;
import net.tetrakoopa.mdua.view.mapping.annotation.Label;
import net.tetrakoopa.mdua.view.mapping.annotation.UIElement;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class LabelInputBuilder<BEAN> extends ViewMappingHelper<BEAN> {

	public LabelInputBuilder(Class<BEAN> beanClass) {
		super(beanClass);
	}

	public static class LabelProvider {
		private Map<String, String> labels;
		public String tranform(String label) { return label; }

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
		final TableRow.LayoutParams rowParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
		for (String attribute :  attributes) {
			final AttributeMapping mapping = mappings.get(attribute);
			final Field field = mapping.readerWriter;

			TableRow tableRow = new TableRow(context);
			tableRow.setLayoutParams(tableParams);

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
				label = labelProvider.tranform(label);


			final TextView textView = new TextView(context);
			textView.setText(label);
			textView.setLayoutParams(rowParams);


			final EditText editText = new EditText(context);

			tableRow.addView(textView);
		}
	}

	public void show(Context context, Object bean, String... attributes) {
		final LayoutInflater inflater = LayoutInflater.from(context);
		final View view = inflater.inflate(R.layout.dialog_edit_bean, null);

		final TableLayout table = (TableLayout)view.findViewById(R.id.table);

		populate(table, bean, null, null, attributes);

		final AlertDialog.Builder builder = new AlertDialog.Builder(context);

		String titre = "titre";
		int iconId = 0;
		final DialogInterface.OnClickListener onClickListener = null;

		builder.setCancelable(true)
				.setPositiveButton("action_modify", new DialogInterface.OnClickListener() {
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

		builder.show();

	}

}
