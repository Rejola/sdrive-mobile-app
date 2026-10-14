package com.creatah.automobile.dealer.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.framework.utils.DeviceManager;
import com.android.framework.utils.Utils;
import com.android.network.listener.ServiceResponseListener;
import com.android.theme.activity.BaseActivity;
import com.android.theme.utils.Constants;
import com.creatah.automobile.dealer.R;
import com.creatah.automobile.dealer.manager.ServiceManager;
import com.creatah.automobile.dealer.modal.ForgotPasswordResponse;
import com.creatah.automobile.dealer.modal.LoginResponse;
import com.creatah.automobile.dealer.utils.AppUtils;
import com.loopj.android.http.RequestParams;
import com.sdrive.workshop.app.dev.DispatchDelivery;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by karthikeyan on 10-02-2017.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private EditText userNameEditText;
    private EditText passwordEditText;
    private TextView forgotTextView;
    private Button loginButton;
    private ImageView passEyeIcon;
    private Context context;
    private boolean isService = false;
    private boolean isFirstClicked;
    private String password;
    private  String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = this;
        hideToolbar();
        initializeViews();

        loginButton.setOnClickListener(this);
        forgotTextView.setOnClickListener(this);

        passEyeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFirstClicked) {

                    isFirstClicked = false;
                    passwordEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    passEyeIcon.setImageResource(R.drawable.ic_hide_password);
                    passwordEditText.setSelection(passwordEditText.getText().length());

                }
                else {

                    isFirstClicked = true;
                    passwordEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    passEyeIcon.setImageResource(R.drawable.ic_show__password);
                    passwordEditText.setSelection(passwordEditText.getText().length());

                }

            }
        });

        passwordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                password=passwordEditText.getText().toString();

                if(password.length()>0)
                {
                    passEyeIcon.setVisibility(View.VISIBLE);
                    passEyeIcon.setImageResource(R.drawable.ic_hide_password);

                }
                else {

                    passEyeIcon.setVisibility(View.GONE);

                }

            }
        });

        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {

                    userName=userNameEditText.getText().toString();
                    password=passwordEditText.getText().toString();

                    if (TextUtils.isEmpty(userName)) {
                        Toast.makeText(context, getString(R.string.enterEmail), Toast.LENGTH_SHORT).show();
                    } else if (!AppUtils.isValidEmail(userName)) {
                        Toast.makeText(context, getString(R.string.invalidEmail), Toast.LENGTH_SHORT).show();

                    } else if (TextUtils.isEmpty(password)) {
                        Toast.makeText(context, getString(R.string.enterPassword), Toast.LENGTH_SHORT).show();
                    } else {

                        doLogin();
                    }
                }
                return false;
            }
        });
    }

    private void initializeViews() {
        userNameEditText = (EditText) findViewById(R.id.userNameEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        loginButton = (Button) findViewById(R.id.loginButton);
        forgotTextView = (TextView) findViewById(R.id.forgotPassword);
        passEyeIcon = (ImageView) findViewById(R.id.passEyeIcon);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.loginButton:
                validateData();
                break;

            case R.id.forgotPassword:
                userName = userNameEditText.getText().toString();
                if (TextUtils.isEmpty(userName)) {
                    Toast.makeText(context, getString(R.string.enterEmail), Toast.LENGTH_SHORT).show();
                } else if (!AppUtils.isValidEmail(userName)) {
                    Toast.makeText(context, getString(R.string.invalidEmail), Toast.LENGTH_SHORT).show();
                } else {
                    forgotPassword(userName);
                }
                break;
        }
    }

    private void validateData() {

         userName = userNameEditText.getText().toString();
         password = passwordEditText.getText().toString();

        if (TextUtils.isEmpty(userName)) {
            Toast.makeText(context, getString(R.string.enterEmail), Toast.LENGTH_SHORT).show();
        } else if (!AppUtils.isValidEmail(userName)) {
            Toast.makeText(context, getString(R.string.invalidEmail), Toast.LENGTH_SHORT).show();

        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(context, getString(R.string.enterPassword), Toast.LENGTH_SHORT).show();
        } else {

            doLogin();
        }

    }

    private void doLogin() {

        if (DeviceManager.isDeviceOffline(context)) {

            showAlert(context, getResources().getString(R.string.title_of_data_connection), getResources().getString(R.string.title_of_data_message),
                    getResources().getString(R.string.ok), null, null, null);
            return;
        }

      final   RequestParams requestParams = new RequestParams();
        requestParams.put("email_id", userName);
        requestParams.put("password", password);

        if (!isService) {
            mainCategoryHandler.sendEmptyMessage(Constants.SHOW_PROGRESS_DIALOG);
            isService = true;
            ServiceManager.doLogin(context, requestParams, new ServiceResponseListener<LoginResponse>() {
                        @Override
                        public void onSuccess(LoginResponse response) {
                            Log.d("STATERR","hello");
                            isService = false;
                            mainCategoryHandler.sendEmptyMessage(Constants.DISMISS_PROGRESS_DIALOG);

                            if (response != null && !response.isError()) {
                                String message = response.getMessage();
                                String cname ="workshop";

                                if(message.equals(cname)){

                                    AppUtils.setWorkshopInfo(response.getWorkshopInfo(), getApplication());
                                    startActivity(new Intent(context, HomeActivity.class));
                                    AppUtils.registerNotification(getApplicationContext());
                                    finish();

                                }
                                else{
                                   // Utils.displayToast(context, message, Toast.LENGTH_SHORT);
                                    AppUtils.setWorkshopInfo(response.getWorkshopInfo(), getApplication());
                                    startActivity(new Intent(context, DispatchDelivery.class));

                                    //AppUtils.registerNotification(getApplicationContext());
                                    finish();




                                }
                            } else if (response != null) {
                                String message = response.getMessage();
                                Utils.displayToast(context, message, Toast.LENGTH_SHORT);
                            }
                        }

                        @Override
                        public void onFailure(Throwable throwable, String errorResponse) {
                            isService = false;
                            mainCategoryHandler.sendEmptyMessage(Constants.DISMISS_PROGRESS_DIALOG);
                            showAlert(context, getResources().getString(R.string.server_failed), getResources().getString(R.string.server_unavailable),
                                    getResources().getString(R.string.ok), null, null, null);
                        }
                    }

            );
        }

    }


    private void forgotPassword(String userName) {
        RequestParams requestParams = new RequestParams();
        requestParams.put("email_id", userName);
        if (DeviceManager.isDeviceOffline(context)) {

            showAlert(context, getResources().getString(R.string.title_of_data_connection), getResources().getString(R.string.title_of_data_message),
                    getResources().getString(R.string.ok), null, null, null);
            return;
        }

        if (!isService) {
            mainCategoryHandler.sendEmptyMessage(Constants.SHOW_PROGRESS_DIALOG);
            isService = true;
            ServiceManager.forgotPassword(context, requestParams, new ServiceResponseListener<ForgotPasswordResponse>() {
                        @Override
                        public void onSuccess(ForgotPasswordResponse response) {
                            isService = false;
                            mainCategoryHandler.sendEmptyMessage(Constants.DISMISS_PROGRESS_DIALOG);

                            if (response != null && !response.isError()) {
                                String message = response.getMessage();
                                Utils.displayToast(context, message, Toast.LENGTH_SHORT);
                            } else if (response != null) {
                                String message = response.getMessage();
                                Utils.displayToast(context, message, Toast.LENGTH_SHORT);
                            }
                        }

                        @Override
                        public void onFailure(Throwable throwable, String errorResponse) {
                            isService = false;
                            mainCategoryHandler.sendEmptyMessage(Constants.DISMISS_PROGRESS_DIALOG);
                            showAlert(context, getResources().getString(R.string.server_failed), getResources().getString(R.string.server_unavailable),
                                    getResources().getString(R.string.ok), null, null, null);
                        }
                    }

            );
        }


    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
