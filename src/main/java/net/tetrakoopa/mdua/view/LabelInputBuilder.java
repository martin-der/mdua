package net.tetrakoopa.mdua.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import net.tetrakoopa.mdua.R;
import net.tetrakoopa.mdua.view.mapping.annotation.Label;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class LabelInputBuilder<BEAN>  {

	private final ViewMappingHelper<BEAN> viewMappingHelper;

	private final View view;

	public LabelInputBuilder(Context context, Class<BEAN> beanClass) {
		this(context, beanClass, null);
	}
	public LabelInputBuilder(Context context, Class<BEAN> beanClass, final View view) {
		viewMappingHelper = new ViewMappingHelper(beanClass);
		this.view = view == null ? LayoutInflater.from(context).inflate(R.layout.dialog_edit_bean, null) : view;
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

	public View getView() {
		return view;
	}

	public enum ValidationLocation {
		END_OF_LINE, UNDER_FIELD, HINT
	}

	public void populate ( TableLayout table, BEAN bean, LabelProvider labelProvider, String... attributes ) {
		populate(table, bean, null, attributes);
	}
	public void populate ( TableLayout table, BEAN bean, ValidationLocation validation, LabelProvider labelProvider, String... attributes ) {
		final Context context = table.getContext();
		final TableLayout.LayoutParams tableLayoutParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
		table.setLayoutParams(tableLayoutParams);
		final TableRow.LayoutParams rowLayoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
		final TableRow.LayoutParams labelLayoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
		final TableRow.LayoutParams editLayoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);

		for (String attribute :  attributes) {
			final Field field = viewMappingHelper.getField(attribute);

			final TableRow row = new TableRow(context);
			row.setLayoutParams(rowLayoutParams);

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
			textView.setLayoutParams(labelLayoutParams);
			row.addView(textView);

			final EditText editText = new EditText(context);
			textView.setLayoutParams(editLayoutParams);
			row.addView(editText);

			final Object value = viewMappingHelper.get(attribute, bean);
			if (value != null)
				editText.setText(value.toString());

			viewMappingHelper.setView(attribute, editText);

			table.addView(row);
		}

	}

	protected void copyViewToBean(BEAN bean, String... attributes) {
		for (String  attribute : attributes) {
			final View view = viewMappingHelper.getView(attribute);
			viewMappingHelper.set(attribute, bean, ((EditText)view).getText().toString());
		}
	}


}
