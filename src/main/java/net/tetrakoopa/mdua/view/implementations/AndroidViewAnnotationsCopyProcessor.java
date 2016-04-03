package net.tetrakoopa.mdua.view.implementations;

import net.tetrakoopa.mdua.view.mapping.ViewAnnotationsCopyProcessor;
import android.view.View;

public class AndroidViewAnnotationsCopyProcessor extends ViewAnnotationsCopyProcessor<View, View> {

	public AndroidViewAnnotationsCopyProcessor() {
		super(new AndroidViewAnnotationsViewAccessor());
	}


}
