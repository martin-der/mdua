package net.tetrakoopa.mdua.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TextView;

import net.tetrakoopa.mdua.R;
import net.tetrakoopa.mdua.util.ResourcesUtil;

public class BeanEditionDialogBuilder<BEAN> extends AlertDialog.Builder {

	private final LabelInputBuilder<BEAN> labelInputBuilder;
	private final View view;

	private String upperText;
	private String lowerText;

	private String actionLabel;

	private DialogInterface.OnClickListener onClickListener;
	private LabelInputBuilder.LabelProvider labelProvider;

	private String attributes[];
	private BEAN bean;


	public BeanEditionDialogBuilder(Context context, Class<BEAN> beanClass) {
		super(context);
		labelInputBuilder = new LabelInputBuilder(context, beanClass);
		view = labelInputBuilder.getView();
		setView(view);
	}

	public AlertDialog.Builder setUpperText(int stringId, String... formatArgs) {
		setUpperText(getContext().getResources().getString(stringId, (Object[])formatArgs));
		return this;
	}
	public AlertDialog.Builder setUpperText(int stringId) {
		setUpperText(getContext().getResources().getString(stringId));
		return this;
	}
	public AlertDialog.Builder setUpperText(String text) {
		this.upperText = text;
		return this;
	}

	public AlertDialog.Builder setLowerText(int stringId, String... formatArgs) {
		setLowerText(getContext().getResources().getString(stringId, (Object[])formatArgs));
		return this;
	}
	public AlertDialog.Builder setLowerText(int stringId) {
		setLowerText(getContext().getResources().getString(stringId));
		return this;
	}
	public AlertDialog.Builder setLowerText(String text) {
		this.lowerText = text;
		return this;
	}

	public AlertDialog.Builder setActionLabel(int stringId, String... formatArgs) {
		setActionLabel(getContext().getResources().getString(stringId, (Object[])formatArgs));
		return this;
	}
	public AlertDialog.Builder setActionLabel(int stringId) {
		setActionLabel(getContext().getResources().getString(stringId));
		return this;
	}
	public AlertDialog.Builder setActionLabel(String text) {
		this.actionLabel = text;
		return this;
	}

	public AlertDialog.Builder setClickListener(DialogInterface.OnClickListener onClickListener) {
		this.onClickListener = onClickListener;
		return this;
	}
	public AlertDialog.Builder setLabelProvider(LabelInputBuilder.LabelProvider labelProvider) {
		this.labelProvider = labelProvider;
		return this;
	}

	public AlertDialog.Builder setContent(final BEAN bean, final String... attributes) {
		this.attributes = attributes;
		this.bean = bean;
		return this;
	}

	public AlertDialog show() {

		final TableLayout table = (TableLayout)view.findViewById(R.id.table);

		labelInputBuilder.populate(table, bean, null, labelProvider, attributes);

		final String actionLabel = this.actionLabel != null ? this.actionLabel : getContext().getString(android.R.string.ok) ;

		this.setCancelable(true)
				.setPositiveButton(actionLabel, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
						labelInputBuilder.copyViewToBean(bean, attributes);
						if (onClickListener != null) {
							onClickListener.onClick(dialog, id);
						}
					}
				})
				.setNegativeButton(ResourcesUtil.getString(getContext(), android.R.string.cancel), new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
						if (onClickListener != null) {
							onClickListener.onClick(dialog, id);
						}
					}
				});
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
			this.setOnDismissListener(new DialogInterface.OnDismissListener() {
				@Override
				public void onDismiss(DialogInterface dialog) {
					if (onClickListener != null) {
						onClickListener.onClick(dialog, DialogInterface.BUTTON_NEUTRAL);
					}
				}
			});
		}

		final TextView upperView = ((TextView) view.findViewById(R.id.upper_text));
		if (upperText!=null) {
			upperView.setText(upperText);
		} else {
			upperView.setVisibility(View.GONE);
		}
		final TextView lowerView = ((TextView) view.findViewById(R.id.lower_text));
		if (lowerText!=null) {
			lowerView.setText(lowerText);
		} else {
			lowerView.setVisibility(View.GONE);
		}


		return super.show();
	}


}
