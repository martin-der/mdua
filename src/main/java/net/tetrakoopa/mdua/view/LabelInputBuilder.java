package net.tetrakoopa.mdua.view;

import android.content.Context;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import net.tetrakoopa.mdu.mapping.MappingHelper;
import net.tetrakoopa.mdua.view.mapping.annotation.Label;
import net.tetrakoopa.mdua.view.mapping.annotation.UIElement;

import java.lang.reflect.Field;

public class LabelInputBuilder<BEAN> extends ViewMappingHelper<BEAN> {

	public LabelInputBuilder(Class<BEAN> beanClass) {
		super(beanClass);
	}

	public enum Validation {
		END_OF_LINE, UNDER_FIELD, HINT
	}

	public void populate(TableLayout table, Object bean, Validation validation, String... attributes ) {
		final Context context = table.getContext();
		final TableLayout.LayoutParams tableParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT);
		table.setLayoutParams(tableParams);
		final TableRow.LayoutParams rowParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
		for (String attribute :  attributes) {
			final AttributeMapping mapping = mappings.get(attribute);
			final Field field = mapping.readerWriter;

			TableRow tableRow = new TableRow(context);
			tableRow.setLayoutParams(tableParams);

			final String label;
			final Label labelHint = field.getAnnotation(Label.class);
			if (labelHint != null) {
				label = context.getResources().getString(labelHint.value());
			} else {
				label = field.getName();
			}

			TextView textView = new TextView(context);
			textView.setText(label);
			textView.setLayoutParams(rowParams);

			tableRow.addView(textView);
		}
	}

}
