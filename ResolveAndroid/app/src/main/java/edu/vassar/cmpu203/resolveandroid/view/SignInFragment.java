package edu.vassar.cmpu203.resolveandroid.view;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.vassar.cmpu203.resolveandroid.databinding.FragmentSignInBinding;

public class SignInFragment extends Fragment implements ISignUpViewMvc {
    private FragmentSignInBinding binding;
    private Listener listener;

    public SignInFragment(Listener listener){ this.listener = listener; }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.binding = FragmentSignInBinding.inflate(inflater);
        return this.binding.getRoot();
    }

    public void clearFailMessage(){
        this.binding.failMessage.setText("");
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        TextWatcher clearFailMessage = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                clearFailMessage();
            }

            @Override
            public void afterTextChanged(Editable s) { }
        };

        this.binding.usernameEditText.addTextChangedListener(clearFailMessage);
        this.binding.passwordEditText.addTextChangedListener(clearFailMessage);

        this.binding.signInButton.setOnClickListener(v -> {
            // get username
            String username = binding.usernameEditText.getText().toString();
            binding.usernameEditText.getText().clear();

            String password = binding.passwordEditText.getText().toString();
            binding.passwordEditText.getText().clear();

            /*if(!listener.onSignInAttempt(username, password, SignInFragment.this)){
                binding.failMessage.setText("Invalid username or password");
                binding.failMessage.setTextColor(Color.RED);
                binding.failMessage.setGravity(Gravity.CENTER);
            }
             */
            listener.onSignInAttempt(username, password, SignInFragment.this);
        });

        this.binding.signUpPageButton.setOnClickListener(v -> {
            listener.newUser();
        });
    }

    @Override
    public void onInvalidCreds(){
        setFailMessage("Login failed. Invalid credentials.");
    }

    @Override
    public void onUserAlreadyExists() {

    }

    public void setFailMessage(String text){
        binding.failMessage.setText(text);
        binding.failMessage.setTextColor(Color.RED);
        binding.failMessage.setGravity(Gravity.CENTER);
    }
}
