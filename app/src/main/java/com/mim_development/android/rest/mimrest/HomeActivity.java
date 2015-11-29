package com.mim_development.android.rest.mimrest;

import android.app.ProgressDialog;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.mim_development.android.rest.mimrest.exception.OperationException;
import com.mim_development.android.rest.mimrest.model.services.user.AuthenticationInvocationCallback;
import com.mim_development.android.rest.mimrest.model.services.user.GetProfileInvocationCallback;
import com.mim_development.android.rest.mimrest.model.services.user.UserService;
import com.mim_development.android.rest.mimrest.model.services.user.responses.AuthenticationResponse;
import com.mim_development.android.rest.mimrest.model.services.user.responses.GetProfileResponse;

import java.util.UUID;

public class HomeActivity extends AppCompatActivity {

    private EditText userNameEditText;
    private EditText passwordEditText;

    private ProgressDialog loginProgressDialog;
    private ProgressDialog getProfileProgressDialog;

    private UUID authenticationRequestIdentifier;
    private UUID getProfileRequestIdentifier;

    private Handler handler = new Handler();

    private class AuthenticationCallback implements AuthenticationInvocationCallback {

        @Override
        public void success(final UUID operationIdentity, final AuthenticationResponse response) {

            handler.post(new Runnable() {
                @Override
                public void run() {
                    processAuthenticationSuccess(operationIdentity, response);
                }
            });
        }

        @Override
        public void error(final UUID operationIdentity, final OperationException exception) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    processAuthenticationError(operationIdentity, exception);
                }
            });
        }
    }

    private void processAuthenticationSuccess(UUID operationIdentity, AuthenticationResponse response) {

        if (authenticationRequestIdentifier != null &&
                authenticationRequestIdentifier.equals(operationIdentity)) {

            authenticationRequestIdentifier = null;

            if (loginProgressDialog != null) {
                loginProgressDialog.dismiss();
            }

            Toast.makeText(
                    HomeActivity.this,
                    R.string.authentication_activity_authentication_succeeded_toast_text,
                    Toast.LENGTH_LONG).show();
        }
    }

    private void processAuthenticationError(UUID operationIdentity, OperationException exception) {

        if (authenticationRequestIdentifier != null &&
                authenticationRequestIdentifier.equals(operationIdentity)) {

            authenticationRequestIdentifier = null;

            if (loginProgressDialog != null) {
                loginProgressDialog.dismiss();
            }

            Toast.makeText(
                    HomeActivity.this,
                    R.string.authentication_activity_authentication_failed_toast_text,
                    Toast.LENGTH_LONG).show();
        }
    }

    private class GetProfileCallback implements GetProfileInvocationCallback {

        @Override
        public void success(final UUID operationIdentity, final GetProfileResponse response) {

            handler.post(new Runnable() {
                @Override
                public void run() {
                    processGetProfileSuccess(operationIdentity, response);
                }
            });
        }

        @Override
        public void error(final UUID operationIdentity, final OperationException exception) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    processGetProfileError(operationIdentity, exception);
                }
            });
        }
    }

    private void processGetProfileSuccess(UUID operationIdentity, GetProfileResponse response) {

        if (getProfileRequestIdentifier != null &&
                getProfileRequestIdentifier.equals(operationIdentity)) {

            getProfileRequestIdentifier = null;

            if (getProfileProgressDialog != null) {
                getProfileProgressDialog.dismiss();
            }

            Toast.makeText(
                    HomeActivity.this,
                    R.string.authentication_activity_get_profile_succeeded_toast_text,
                    Toast.LENGTH_LONG).show();
        }
    }

    private void processGetProfileError(UUID operationIdentity, OperationException exception) {

        if (getProfileRequestIdentifier != null &&
                getProfileRequestIdentifier.equals(operationIdentity)) {

            getProfileRequestIdentifier = null;

            if (getProfileProgressDialog != null) {
                getProfileProgressDialog.dismiss();
            }

            Toast.makeText(
                    HomeActivity.this,
                    R.string.authentication_activity_get_profile_failed_toast_text,
                    Toast.LENGTH_LONG).show();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        userNameEditText = (EditText) findViewById(R.id.authentication_activity_username_edittext);
        passwordEditText = (EditText) findViewById(R.id.authentication_activity_password_edittext);
    }

    public void authenticateActivityAuthenticateButton_onClick(View v) {

        String userName = userNameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        authenticationRequestIdentifier = UserService.getInstance().authenticate(userName, password, new AuthenticationCallback());

        if (authenticationRequestIdentifier.equals(Globals.EMPTY_UUID)) {
            authenticationRequestIdentifier = null;
            Toast.makeText(
                    HomeActivity.this,
                    R.string.authentication_activity_authentication_failed_toast_text,
                    Toast.LENGTH_LONG).show();
        } else {
            loginProgressDialog = ProgressDialog.show(
                    this,
                    getResources().getString(R.string.authentication_activity_user_service_dialog_title),
                    getResources().getString(R.string.authentication_activity_authentication_progress_dialog_label));
        }
    }

    public void authenticateActivityGetProfileButton_onClick(View v){

        getProfileRequestIdentifier = UserService.getInstance().loadProfile("12345", new GetProfileCallback());

        if (getProfileRequestIdentifier.equals(Globals.EMPTY_UUID)) {
            getProfileRequestIdentifier = null;
            Toast.makeText(
                    HomeActivity.this,
                    R.string.authentication_activity_get_profile_failed_toast_text,
                    Toast.LENGTH_LONG).show();
        } else {
            getProfileProgressDialog = ProgressDialog.show(
                    this,
                    getResources().getString(R.string.authentication_activity_user_service_dialog_title),
                    getResources().getString(R.string.authentication_activity_get_profile_progress_dialog_label));
        }

    }
}