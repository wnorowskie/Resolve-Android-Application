package edu.vassar.cmpu203.resolveandroid.view;

import android.view.View;

public interface ISignUpViewMvc {

    public interface Listener{
        void onSignUpAttempt(String username, String password, ISignUpViewMvc signUpViewMvc);
        void onSignInAttempt(String username, String password, ISignUpViewMvc signUpViewMvc);
        void returningUser();
        void newUser();
    }

    void onUserAlreadyExists();
    void onInvalidCreds();
}
