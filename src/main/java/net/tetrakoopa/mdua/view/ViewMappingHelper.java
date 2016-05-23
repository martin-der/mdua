package net.tetrakoopa.mdua.view;

import net.tetrakoopa.mdu.mapping.MappingHelper;

public abstract class ViewMappingHelper<BEAN> extends MappingHelper<BEAN> {

	public ViewMappingHelper(Class<BEAN> beanClass) {
		super(beanClass);
	}

	private RuntimeException buildNoInformationException(String attribute) {
		throw new RuntimeException("Attribute '"+attribute+"' in "+beanClass+" has no mapping configuration");
	}

}
