package com.android.theme.fragment;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.theme.R;
import com.android.theme.utils.Constants;
import com.android.theme.widget.CustomAlertDialog;
import com.android.theme.widget.CustomProgressDialog;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class BaseFragment extends Fragment {

    private CustomProgressDialog progressDialog;


    public BaseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return null;
    }

    @SuppressLint("HandlerLeak")
    protected Handler mainCategoryHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case Constants.SHOW_PROGRESS_DIALOG:
                    showProgressDialog();
                    break;
                case Constants.DISMISS_PROGRESS_DIALOG:
                    dismissProgressDialog();
                    break;

                default:
                    break;
            }
        };
    };

    protected void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new CustomProgressDialog(getActivity(),
                    getString(R.string.please_wait),
                    getString(R.string.loading), false, false);
            if (!getActivity().isFinishing()) {
                progressDialog.show();
            }
            return;
        }

        if (!progressDialog.isShowing()) {
            if (!getActivity().isFinishing()) {
                progressDialog.show();
            }
        }
    }


    private void dismissProgressDialog() {

        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    public static void showAlert(Context context, String title, String message,
                                 String possitiveBtnName,
                                 DialogInterface.OnClickListener possitiveBtnOnClickListener,
                                 String negativeBtnName,
                                 DialogInterface.OnClickListener negativeBtnOnClickListener) {
        CustomAlertDialog.CustomAlertDialogDetails dialogDetails = new CustomAlertDialog.CustomAlertDialogDetails();
        if (title != null) {
            dialogDetails.setDialogTitle(title);
        }
        if (message != null) {
            dialogDetails.setDialogMessage(message);
        }
        ArrayList<CustomAlertDialog.DDAlertButton> buttonList = new ArrayList<CustomAlertDialog.DDAlertButton>();
        if (possitiveBtnName != null) {
            CustomAlertDialog.DDAlertButton button = new CustomAlertDialog.DDAlertButton(possitiveBtnName);
            button.setOnClickListener(possitiveBtnOnClickListener);
            buttonList.add(button);
        }
        if (negativeBtnName != null) {
            CustomAlertDialog.DDAlertButton button = new CustomAlertDialog.DDAlertButton(negativeBtnName, CustomAlertDialog.DDAlertButton.RIGHT);
            button.setOnClickListener(negativeBtnOnClickListener);
            buttonList.add(button);
        }
        dialogDetails.setDialogButton(buttonList);
        CustomAlertDialog jumptuitDialog = new CustomAlertDialog(context,
                dialogDetails);
        jumptuitDialog.setCancelable(false);
        jumptuitDialog.show();
    }

}
