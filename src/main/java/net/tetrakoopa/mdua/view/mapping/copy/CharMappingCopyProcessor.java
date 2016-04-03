package net.tetrakoopa.mdua.view.mapping.copy;

import java.lang.reflect.Field;

import net.tetrakoopa.mdua.view.mapping.exception.BadValueException;

public class CharMappingCopyProcessor extends PrimitiveMappingCopyProcessor<Character> {

	@Override
	public <VO> void setIntoVOPrimitive(VO vo, Field field, Character value) throws IllegalArgumentException, IllegalAccessException, BadValueException {
		if (field.getType().isPrimitive())
			field.setChar(vo, value);
		else
			setIntoVORaw(vo, field, value);
	}

	@Override
	public <VO> Character getFromVOPrimitive(VO vo, Field field) throws IllegalArgumentException, IllegalAccessException, BadValueException {
		if (field.getType().isPrimitive())
			return field.getChar(vo);
		else
			return getFromVORaw(vo, field);
	}
}
