package com.example.administrator.calendartest;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BottomDialog extends Dialog{
	public BottomDialog(Context context) {
		super(context);
	}

	public BottomDialog(Context context, int theme) {
		super(context, theme);
	}

	public static class Builder {
		private Context context;
		private String title;
		private String message;
		private String confirm_btnText;
		private String cancel_btnText;
		private View contentView;
		
		private OnClickListener confirm_btnClickListener;
		private OnClickListener cancel_btnClickListener;

		public Builder(Context context) {
			this.context = context;
		}

	
		public Builder setMessage(String message) {
			this.message = message;
			return this;
		}

		
		public Builder setMessage(int message) {
			this.message = (String) context.getText(message);
			return this;
		}

		
		public Builder setTitle(int title) {
			this.title = (String) context.getText(title);
			return this;
		}

		
		public Builder setTitle(String title) {
			this.title = title;
			return this;
		}

		
		public Builder setContentView(View v) {
			this.contentView = v;
			return this;
		}

		
		public Builder setPositiveButton(int confirm_btnText,
				OnClickListener listener) {
			this.confirm_btnText = (String) context
					.getText(confirm_btnText);
			this.confirm_btnClickListener = listener;
			return this;
		}

		
		public Builder setPositiveButton(String confirm_btnText,
				OnClickListener listener) {
			this.confirm_btnText = confirm_btnText;
			this.confirm_btnClickListener = listener;
			return this;
		}

		
		public Builder setNegativeButton(int cancel_btnText,
				OnClickListener listener) {
			this.cancel_btnText = (String) context
					.getText(cancel_btnText);
			this.cancel_btnClickListener = listener;
			return this;
		}

		
		public Builder setNegativeButton(String cancel_btnText,
				OnClickListener listener) {
			this.cancel_btnText = cancel_btnText;
			this.cancel_btnClickListener = listener;
			return this;
		}

		public BottomDialog create() {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			
			final BottomDialog dialog = new BottomDialog(context, R.style.bottom_dialog_style);
			View layout = inflater.inflate(R.layout.share_dialog, null);
			
			dialog.addContentView(layout, new LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			
			dialog.setCanceledOnTouchOutside(true);
			((TextView) layout.findViewById(R.id.title)).setText(title);
			((TextView) layout.findViewById(R.id.title)).getPaint().setFakeBoldText(true);

			if (confirm_btnText != null) {
				((Button) layout.findViewById(R.id.confirm_btn))
						.setText(confirm_btnText);
				if (confirm_btnClickListener != null) {
					layout.findViewById(R.id.confirm_btn)
							.setOnClickListener(new View.OnClickListener() {
								public void onClick(View v) {
									confirm_btnClickListener.onClick(dialog,
											DialogInterface.BUTTON_POSITIVE);
								}
							});
				}
			} else {
				
				layout.findViewById(R.id.confirm_btn).setVisibility(
						View.GONE);
			}
			
			if (cancel_btnText != null) {
				((Button) layout.findViewById(R.id.cancel_btn))
						.setText(cancel_btnText);
				if (cancel_btnClickListener != null) {
					layout.findViewById(R.id.cancel_btn)
							.setOnClickListener(new View.OnClickListener() {
								public void onClick(View v) {
									cancel_btnClickListener.onClick(dialog,
											DialogInterface.BUTTON_NEGATIVE);
								}
							});
				}
			} else {
				
				layout.findViewById(R.id.cancel_btn).setVisibility(View.GONE);
			}
			if (message != null) {
				((TextView) layout.findViewById(R.id.message)).setText(message);
			} else if (contentView != null) {
				((LinearLayout) layout.findViewById(R.id.l_message)).removeAllViews();
				((LinearLayout) layout.findViewById(R.id.l_message)).addView(
						contentView, new LayoutParams(
								LayoutParams.MATCH_PARENT,
								LayoutParams.WRAP_CONTENT));
			layout.findViewById(R.id.iv_middle_line).setVisibility(View.GONE);
			layout.findViewById(R.id.title).setVisibility(View.GONE);
			}
			dialog.setContentView(layout);
			return dialog;
		}

	}

}
