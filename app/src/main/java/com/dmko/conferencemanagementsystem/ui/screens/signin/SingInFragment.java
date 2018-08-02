package com.dmko.conferencemanagementsystem.ui.screens.signin;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dmko.conferencemanagementsystem.R;
import com.dmko.conferencemanagementsystem.data.user.entities.SignInForm;
import com.dmko.conferencemanagementsystem.ui.base.mvp.impl.BaseFragment;
import com.dmko.conferencemanagementsystem.ui.screens.main.MainActivity;
import com.dmko.conferencemanagementsystem.ui.screens.signup.SignUpActivity;
import com.dmko.conferencemanagementsystem.utils.Patterns;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import butterknife.Unbinder;

public class SingInFragment extends BaseFragment implements SignInContract.View {

    @BindView(R.id.input_layout_email) TextInputLayout inputLayoutEmail;
    @BindView(R.id.input_layout_password) TextInputLayout inputLayoutPassword;
    @BindView(R.id.button_signin) Button buttonSignIn;
    @BindView(R.id.text_signup) TextView textSignUp;
    @BindView(R.id.progress_loading) ProgressBar progressBar;
    @BindView(R.id.layout_content) ConstraintLayout layoutContent;
    @BindString(R.string.error_empty_email) String errorEmptyEmail;
    @BindString(R.string.error_incorrect_email) String errorIncorrectEmail;
    @BindString(R.string.error_empty_password) String errorEmptyPassword;

    @Inject SignInContract.Presenter presenter;

    private Unbinder unbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getControllerComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signin, container, false);
        unbinder = ButterKnife.bind(this, view);
        presenter.attachView(this);

        inputLayoutEmail.getEditText().setText("creator@google.com");
        inputLayoutPassword.getEditText().setText("passworD1@");

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        presenter.detachView();
    }

    @OnClick(R.id.button_signin)
    public void onButtonSignInClicked() {
        SignInForm signInForm = validate();
        if (signInForm != null) {
            presenter.signIn(signInForm);
        }
    }

    @OnEditorAction(R.id.input_password)
    public boolean onEditorAction(int actionId) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            onButtonSignInClicked();
        }
        return true;
    }

    @OnClick(R.id.text_signup)
    public void onTextSignUpClicked() {
        Intent intent = new Intent(getContext(), SignUpActivity.class);
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
        getContext().startActivity(intent);
    }

    @Override
    public void showIncorrectEmailOrPasswordError() {
        inputLayoutEmail.setErrorEnabled(true);
        inputLayoutEmail.setError(getString(R.string.error_incorrect_email_or_password));
    }

    private SignInForm validate() {
        String email = inputLayoutEmail.getEditText().getText().toString();
        String password = inputLayoutPassword.getEditText().getText().toString();
        SignInForm signInForm = new SignInForm(email, password);

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

        if (inputLayoutEmail.isErrorEnabled()) {
            inputLayoutEmail.getEditText().requestFocus();
            return null;
        } else if (inputLayoutPassword.isErrorEnabled()) {
            inputLayoutPassword.getEditText().requestFocus();
            return null;
        } else {
            return signInForm;
        }
    }
}
