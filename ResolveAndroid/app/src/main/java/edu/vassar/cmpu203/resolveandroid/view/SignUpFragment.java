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
import edu.vassar.cmpu203.resolveandroid.databinding.FragmentSignUpBinding;
import edu.vassar.cmpu203.resolveandroid.model.Database;

public class SignUpFragment extends Fragment implements ISignUpViewMvc {
    private FragmentSignUpBinding binding;
    private Listener listener;

    public SignUpFragment(Listener listener){ this.listener = listener; }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.binding = FragmentSignUpBinding.inflate(inflater);
        return this.binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        //Create onTextChanged function
        TextWatcher clearFailMessage = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setFailMessage("");
            }

            @Override
            public void afterTextChanged(Editable s) { }
        };

        //Clear the failure message when user types in either username or password
        this.binding.usernameEditText.addTextChangedListener(clearFailMessage);
        this.binding.passwordEditText.addTextChangedListener(clearFailMessage);

        this.binding.signUpButton.setOnClickListener(v -> {
            trySignUp();
        });

        this.binding.signInPageButton.setOnClickListener(v -> {
            listener.returningUser();
        });
    }

    public void trySignUp(){
        // get username
        String username = binding.usernameEditText.getText().toString();
        binding.usernameEditText.getText().clear();
        // get password
        String password = binding.passwordEditText.getText().toString();
        binding.passwordEditText.getText().clear();
        //if either field is empty, show error message
        if(username.isEmpty() || password.isEmpty()){
            //TODO define this constant in values
            setFailMessage("Fields cannot be blank.");

        }
        // SignUpFragment.this.listener
          //      .onSignUpAttempt(username, password, SignUpFragment.this);
        else if(Database.getUserRepo().containsKey(username)){
            //TODO define this constant in values
            setFailMessage("User already exists.");
        } else
         {
           listener.onSignUpAttempt(username, password, SignUpFragment.this);
       }
    }

    @Override
    public void onUserAlreadyExists(){
        setFailMessage("Sign up attempt failed. User already exists.");
    }

    @Override
    public void onInvalidCreds(){}

    public void setFailMessage(String text){
        binding.failureMessage.setText(text);
        binding.failureMessage.setTextColor(Color.RED);
        binding.failureMessage.setGravity(Gravity.CENTER);
    }
}