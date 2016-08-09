package com.wei.bigshow.ui.fragment;

import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.wei.bigshow.R;
import com.wei.bigshow.rx.RxBus;
import com.wei.bigshow.rx.event.EditItemEvent;
import com.wei.bigshow.ui.vandor.keyevent.KeyEventUtil;


public class EditItemDialog extends DialogFragment {

	public static final String TAG = EditItemDialog.class.getSimpleName();
	public static final String ARG_TITLE = "title";
	public static final String ARG_ITEM_POSITION = "position";

	public interface OnEditItemListener {
		void onTitleModified(int position, String newTitle);
	}

	private String mTitle;
	private int mPosition;

	public EditItemDialog() {
	}

	public static EditItemDialog newInstance(String title, int position) {
		return newInstance(title, position, null);
	}

	public static EditItemDialog newInstance(String title, int position, Fragment fragment) {
		EditItemDialog dialog = new EditItemDialog();
		Bundle args = new Bundle();
		args.putString(ARG_TITLE, title);
		args.putInt(ARG_ITEM_POSITION, position);
		dialog.setArguments(args);
		dialog.setArguments(args);
		dialog.setTargetFragment(fragment, 0);
		return dialog;
	}

	private OnEditItemListener getListener() {
		OnEditItemListener listener = (OnEditItemListener) getTargetFragment();
		if (listener == null) {
			listener = (OnEditItemListener) getActivity();
		}
		return listener;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putString(ARG_TITLE, mTitle);
		super.onSaveInstanceState(outState);
	}

	@Override
	public void onStart() {
		super.onStart();
		getDialog().getWindow().setWindowAnimations(R.style.animation_slide_from_right);
	}

	@SuppressLint({"InflateParams", "HandlerLeak"})
	@Override
	public AlertDialog onCreateDialog(Bundle savedInstanceState) {
		//Pick up bundle parameters
		Bundle bundle;
		if (savedInstanceState == null) {
			bundle = getArguments();
		} else {
			bundle = savedInstanceState;
		}

		mTitle = bundle.getString(ARG_TITLE);
		mPosition = bundle.getInt(ARG_ITEM_POSITION);

		//Inflate custom view
		View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_edit_item, null);

		final EditText editText = (EditText) dialogView.findViewById(R.id.text_edit_title);

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());//, R.style.AppTheme_AlertDialog);
		builder.setTitle(R.string.dialog_edit_title)
				.setView(dialogView)
				.setNegativeButton(R.string.CANCEL, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						KeyEventUtil.hideKeyboard(getActivity(), editText);
						dialog.dismiss();
					}
				})
				.setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						RxBus.getDefault().post(new EditItemEvent(mPosition, editText.getText().toString()));
//						getListener().onTitleModified(mPosition,
//								editText.getText().toString().trim());
						KeyEventUtil.hideKeyboard(getActivity(), editText);
						dialog.dismiss();
					}
				});

		final AlertDialog editDialog = builder.create();

		if (mTitle != null) {
			editText.setText(mTitle);
			editText.selectAll();
		}
		editText.requestFocus();

		editDialog.getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

		return editDialog;
	}


}