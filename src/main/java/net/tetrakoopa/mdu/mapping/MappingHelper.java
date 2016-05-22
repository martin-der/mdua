package net.tetrakoopa.mdu.mapping;

import net.tetrakoopa.mdu.util.StringUtil;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

public class MappingHelper<BEAN> {

	protected static class AttributeMapping {

		private Field readerWriter;
		private Method getter;
		private Method setter;

	}

	public static class MappingException extends RuntimeException {
		public MappingException(String message) {
			super(message);
		}
	}

	protected final Class<BEAN> beanClass;
	protected final Map<String, AttributeMapping> mappings = new HashMap<>();

	public MappingHelper(Class<BEAN> beanClass) {
		this.beanClass = (Class<BEAN>) beanClass;
	}

	public void addAttribute(String name) {
		addAttribute(name, true);
	}
	public void addAttribute(String name, boolean writeEnabled) {
		for (Field field : beanClass.getDeclaredFields()) {
			if (field.getName().equals(name)) {

				final Class<?> attributeType = field.getType();

				final int modifiers = field.getModifiers();
				final AttributeMapping mapping = new AttributeMapping();
				if (Modifier.isPublic(modifiers)) {
					mapping.readerWriter = field;
					if (writeEnabled) {
						if (Modifier.isFinal(modifiers)) {
							throw new MappingException(faultingAttribute(name)+" cannot be set since its public field is final");
						}
					}
					mappings.put(name, mapping);
					return;
				}

				final boolean isPrimitiveBoolean = boolean.class.equals(attributeType);
				final String gettterName = getterName(name, isPrimitiveBoolean);
				final String settterName = writeEnabled ? setterName(name) : null;

				for (Method method : beanClass.getDeclaredMethods()) {
					final int methodModifiers = method.getModifiers();
					if (method.getName().equals(gettterName)
							&& (method.getParameterTypes().length==0)
							&& method.getReturnType().equals(attributeType))
					{
						if (!Modifier.isPublic(methodModifiers)) {
							throw new MappingException(faultingAttribute(name)+" have a getter ( '"+gettterName+"' ) but is not public");
						}
						mapping.getter = method;
					}

					if (settterName != null) {
						if (method.getName().equals(settterName)
								&& (method.getParameterTypes().length==1)
								&& (method.getParameterTypes()[0].equals(attributeType))
								&& method.getReturnType().equals(Void.TYPE))
						{
							if (!Modifier.isPublic(methodModifiers)) {
								throw new MappingException(faultingAttribute(name)+" have a setter ( '"+settterName+"' ) but is not public");
							}
							mapping.setter = method;
						}
					}
				}

				if (mapping.getter != null && (mapping.setter != null || !writeEnabled)) {
					mappings.put(name, mapping);
					return;
				}

				if (writeEnabled)
					throw new MappingException(faultingAttribute(name)+" does not have getter and setter ( '"+gettterName+"' and '"+settterName+"' )");
				else
					throw new MappingException(faultingAttribute(name)+" does not have getter ( '"+gettterName+"' )");
			}
		}

		throw new MappingException(faultingAttribute(name)+" does not exist");
	}

	public void set(String name, BEAN bean, Object value) throws InvocationTargetException, IllegalAccessException {
		final AttributeMapping mapping = mappings.get(name);
		if (mapping == null)
			throw buildNoSuchAttributeException(name);
		set(mapping, bean, value);
	}
	protected void set(AttributeMapping mapping, BEAN bean, Object value) throws InvocationTargetException, IllegalAccessException {
		if (mapping.setter != null) {
			mapping.setter.invoke(bean, value);
		} else {
			mapping.readerWriter.set(bean, value);
		}
	}
	public Object get(String name, BEAN bean) throws InvocationTargetException, IllegalAccessException {
		final AttributeMapping mapping = mappings.get(name);
		if (mapping == null)
			throw buildNoSuchAttributeException(name);
		return get(mapping, bean);
	}
	protected Object get(AttributeMapping mapping, BEAN bean) throws InvocationTargetException, IllegalAccessException {
		if (mapping.getter != null) {
			return mapping.getter.invoke(bean);
		} else {
			return mapping.readerWriter.get(bean);
		}
	}


	private static String setterName(String attribute) {
		return "set"+ StringUtil.firstCharToUpperCase(attribute);
	}
	private static String getterName(String attribute, boolean forBoolean) {
		return (forBoolean ? "is" : "get")+ StringUtil.firstCharToUpperCase(attribute);
	}

	private final MappingException buildNoSuchAttributeException(String name) {
		return new MappingException("No such mapped attribute '"+name+"' in "+beanClass.getName());
	}
	private final String faultingAttribute(String name) {
		return "Attribute '"+name+"' in "+beanClass.getName();
	}

}
