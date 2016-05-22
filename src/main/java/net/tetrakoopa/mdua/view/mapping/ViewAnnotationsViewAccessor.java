package net.tetrakoopa.mdua.view.mapping;

import java.lang.reflect.Field;

import net.tetrakoopa.mdua.view.mapping.annotation.UIElement;
import net.tetrakoopa.mdua.view.mapping.exception.accessor.IllegalViewMappingException;

public interface ViewAnnotationsViewAccessor<VIEW, VIEW_ELEMENT> {

	VIEW_ELEMENT findElement(VIEW view, UIElement uiElement) throws IllegalViewMappingException;

	<TYPE> TYPE getViewElementValue(Field field, VIEW_ELEMENT element, Class<TYPE> type) throws IllegalViewMappingException;

	<TYPE> void setViewElementValue(Field field, VIEW_ELEMENT element, Class<TYPE> type, TYPE value) throws IllegalViewMappingException;
}
