package net.tetrakoopa.mdua.view.implementations;

import net.tetrakoopa.mdua.view.mapping.ViewAnnotationsValidator;
import android.view.View;

public class AndroidViewAnnotationsValidator extends ViewAnnotationsValidator<View, View> {

	public AndroidViewAnnotationsValidator() {
		super(new AndroidViewAnnotationsViewAccessor());
	}

}