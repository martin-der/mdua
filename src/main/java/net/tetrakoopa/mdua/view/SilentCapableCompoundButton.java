package net.tetrakoopa.mdua.view;

import android.widget.CompoundButton;

public interface SilentCapableCompoundButton {

	class Helper<C extends CompoundButton> {

		private CompoundButton.OnCheckedChangeListener listener;

		private final C button;

		public <CE extends C> Helper(CE button) {
			if (button == null)
				throw new NullPointerException();
			this.button = button;
		}

		public void setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener listener) {
			this.listener = listener;
		}

		public void setCheckedSilently(boolean checked) {
			final CompoundButton.OnCheckedChangeListener listener = this.listener;
			button.setOnCheckedChangeListener(null);
			button.setChecked(checked);
			button.setOnCheckedChangeListener(listener);
			this.listener = listener;
		}

	}

	void setCheckedSilently(boolean checked);
}