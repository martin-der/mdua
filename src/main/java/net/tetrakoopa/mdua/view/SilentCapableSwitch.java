package net.tetrakoopa.mdua.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.CompoundButton;
import android.widget.Switch;

public class SilentCapableSwitch extends Switch implements SilentCapableCompoundButton {

	private final Helper<Switch> helper;

	public SilentCapableSwitch(Context context) {
		super(context);
		this.helper = new Helper<>(this);
	}

	public SilentCapableSwitch(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.helper = new Helper<>(this);
	}

	public SilentCapableSwitch(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		this.helper = new Helper<>(this);
	}

	//@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	/*public SilentCapableSwitch(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
	}*/

	@Override
	public void setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener listener) {
		super.setOnCheckedChangeListener(listener);
		helper.setOnCheckedChangeListener(listener);
	}

	@Override
	public void setCheckedSilently(boolean checked) {
		helper.setCheckedSilently(checked);
	}
}
