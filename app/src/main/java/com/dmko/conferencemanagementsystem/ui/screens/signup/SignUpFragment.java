package com.dmko.conferencemanagementsystem.ui.screens.signup;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dmko.conferencemanagementsystem.R;
import com.dmko.conferencemanagementsystem.data.user.entities.SignUpForm;
import com.dmko.conferencemanagementsystem.ui.base.mvp.impl.BaseFragment;
import com.dmko.conferencemanagementsystem.ui.screens.main.MainActivity;
import com.dmko.conferencemanagementsystem.ui.screens.signin.SignInActivity;
import com.dmko.conferencemanagementsystem.utils.Patterns;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import butterknife.Unbinder;

public class SignUpFragment extends BaseFragment implements SignUpContract.View {

    @BindView(R.id.input_layout_first_name) TextInputLayout inputLayoutFirstName;
    @BindView(R.id.input_layout_last_name) TextInputLayout inputLayoutLastName;
    @BindView(R.id.input_layout_email) TextInputLayout inputLayoutEmail;
    @BindView(R.id.input_layout_password) TextInputLayout inputLayoutPassword;
    @BindView(R.id.input_layout_password_confirm) TextInputLayout inputLayoutPasswordConfirm;
    @BindView(R.id.button_signup) Button buttonSignUp;
    @BindView(R.id.text_login) TextView textLogin;
    @BindView(R.id.progress_loading) ProgressBar progressBar;

    @BindString(R.string.error_empty_first_name) String errorEmptyFirstName;
    @BindString(R.string.error_empty_last_name) String errorEmptyLastName;
    @BindString(R.string.error_empty_email) String errorEmptyEmail;
    @BindString(R.string.error_incorrect_email) String errorIncorrectEmail;
    @BindString(R.string.error_empty_password) String errorEmptyPassword;
    @BindString(R.string.error_empty_password_confirm) String errorEmptyPasswordConfirm;
    @BindString(R.string.error_passwords_do_not_match) String errorPasswordsDoNotMatch;

    @Inject SignUpContract.Presenter presenter;

    private Unbinder unbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getControllerComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup, container, false);
        unbinder = ButterKnife.bind(this, view);
        presenter.attachView(this);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        presenter.detachView();
    }

    @OnClick(R.id.button_signup)
    public void onButtonSignUpClicked() {
        SignUpForm signUpForm = validate();
        if (signUpForm != null) {
            presenter.signUp(signUpForm);
        }
    }

    @OnEditorAction(R.id.input_password)
    public boolean onEditorAction(int actionId) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            onButtonSignUpClicked();
        }
        return true;
    }

    @OnClick(R.id.text_login)
    public void onTextLoginClicked() {
        Intent intent = new Intent(getContext(), SignInActivity.class);
        getContext().startActivity(intent);
    }

    @Override
    public void showLoading(boolean isLoading) {
        progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showMainActivity() {
        Intent intent = new Intent(getContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    private SignUpForm validate() {
        String firstName = inputLayoutFirstName.getEditText().getText().toString();
        String lastName = inputLayoutLastName.getEditText().getText().toString();
        String email = inputLayoutEmail.getEditText().getText().toString();
        String password = inputLayoutPassword.getEditText().getText().toString();
        String passwordConfirm = inputLayoutPasswordConfirm.getEditText().getText().toString();
        SignUpForm signUpForm = new SignUpForm(firstName, lastName, email, password);

        if (firstName.isEmpty()) {
            inputLayoutFirstName.setErrorEnabled(true);
            inputLayoutFirstName.setError(errorEmptyFirstName);
        } else {
            inputLayoutFirstName.setErrorEnabled(false);
        }

        if (lastName.isEmpty()) {
            inputLayoutLastName.setErrorEnabled(true);
            inputLayoutLastName.setError(errorEmptyLastName);
        } else {
            inputLayoutLastName.setErrorEnabled(false);
        }

        if (email.isEmpty()) {
            inputLayoutEmail.setErrorEnabled(true);
            inputLayoutEmail.setError(errorEmptyEmail);
        } else if (!email.matches(Patterns.EMAIL_REGEX)) {
            inputLayoutEmail.setErrorEnabled(true);
            inputLayoutEmail.setError(errorIncorrectEmail);
        } else {
            inputLayoutEmail.setErrorEnabled(false);
        }

        if (password.isEmpty()) {
            inputLayoutPassword.setErrorEnabled(true);
            inputLayoutPassword.setError(errorEmptyPassword);
        } else {
            inputLayoutPassword.setErrorEnabled(false);
        }

        if (passwordConfirm.isEmpty()) {
            inputLayoutPasswordConfirm.setErrorEnabled(true);
            inputLayoutPasswordConfirm.setError(errorEmptyPasswordConfirm);
        } else if (!password.equals(passwordConfirm)) {
            inputLayoutPasswordConfirm.setErrorEnabled(true);
            inputLayoutPasswordConfirm.setError(errorPasswordsDoNotMatch);
        } else {
            inputLayoutPasswordConfirm.setErrorEnabled(false);
        }

        if (inputLayoutFirstName.isErrorEnabled()) {
            inputLayoutFirstName.requestFocus();
            return null;
        } else if (inputLayoutLastName.isErrorEnabled()) {
            inputLayoutLastName.requestFocus();
            return null;
        } else if (inputLayoutEmail.isErrorEnabled()) {
            inputLayoutEmail.requestFocus();
            return null;
        } else if (inputLayoutPassword.isErrorEnabled()) {
            inputLayoutPassword.requestFocus();
            return null;
        } else if (inputLayoutPasswordConfirm.isErrorEnabled()) {
            inputLayoutPasswordConfirm.requestFocus();
            return null;
        } else {
            return signUpForm;
        }
    }
}
