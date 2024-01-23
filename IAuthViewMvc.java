package edu.vassar.cmpu203.resolveandroid.view;

public interface IAuthViewMvc {
    interface Listener {
        void onRegister(String username, String password, IAuthViewMvc authView);
        void onSigninAttempt(String username, String password, IAuthViewMvc authView);
    }
    void onRegisterSuccess();
    void onInvalidCredentials();
    void onUserAlreadyExists();
}
