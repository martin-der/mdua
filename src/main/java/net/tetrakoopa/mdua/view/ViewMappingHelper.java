package net.tetrakoopa.mdua.view;

import android.view.View;

import net.tetrakoopa.mdu.mapping.MappingHelper;
import net.tetrakoopa.mdua.view.mapping.annotation.UIElement;

public class ViewMappingHelper<BEAN> extends MappingHelper<BEAN> {

	public ViewMappingHelper(Class<BEAN> beanClass) {
		super(beanClass);
	}

	public void copyDomainToView(BEAN bean, View view, String attribute) {
		final MappingHelper.AttributeMapping mapping = mappings.get(attribute);
		final UIElement uiElement = mapping.readerWriter.getAnnotation(UIElement.class);
		if (uiElement == null) {
			throw buildNoInformationException(attribute);
		}
		for (int id : uiElement.value()) {
			final View element = view.findViewById(id);
			if (element != null) {
				final Object value = get(mapping, bean);
				return;
			}
		}
		throw new MappingException("TODO");
	}
	public void copyViewToDomain(View view, BEAN bean, String attribute) {
		final AttributeMapping mapping = mappings.get(attribute);
		final UIElement uiElement = mapping.readerWriter.getAnnotation(UIElement.class);
		if (uiElement == null) {
			throw buildNoInformationException(attribute);
		}
		for (int id : uiElement.value()) {
			final View element = view.findViewById(id);
			if (element != null) {
				final Object value = null;
				set(mapping, bean, value);
				return;
			}
		}
		throw new MappingException("TODO");
	}

}
