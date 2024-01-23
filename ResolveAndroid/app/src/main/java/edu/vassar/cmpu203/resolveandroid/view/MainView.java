package edu.vassar.cmpu203.resolveandroid.view;

import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import java.util.Stack;

import edu.vassar.cmpu203.resolveandroid.databinding.MainBinding;

public class MainView implements IMainView {

    private MainBinding binding;
    private FragmentActivity activity;
    private Fragment curFragment;

    public MainView(FragmentActivity activity){
        this.binding = MainBinding.inflate(activity.getLayoutInflater());
        this.activity = activity;
    }

    @Override
    public View getRootView() {
        return this.binding.getRoot();
    }

    @Override
    public void displayFragment(Fragment fragment) {
        FragmentTransaction ft = this.activity.getSupportFragmentManager().beginTransaction();
        ft.replace(this.binding.fragmentContainerView.getId(), fragment);
        this.curFragment = fragment;
        //Only allow user to use the back button if they aren't on the signin/signup screen
        if(!(this.curFragment instanceof TitleScreenFragment) && !(this.curFragment instanceof SignUpFragment) && !(this.curFragment instanceof SignInFragment))
            ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void addFragment(Fragment fragment){
        FragmentTransaction ft = this.activity.getSupportFragmentManager().beginTransaction();
        ft.add(this.binding.fragmentContainerView.getId(), fragment);
        this.curFragment = fragment;
        //Only allow user to use the back button if they aren't on the signin/signup screen
        if(!(this.curFragment instanceof TitleScreenFragment) && !(this.curFragment instanceof SignUpFragment) && !(this.curFragment instanceof SignInFragment))
            ft.addToBackStack(null);
        ft.commit();
    }

    public void addFragmentNoBackStack(Fragment fragment){
        FragmentTransaction ft = this.activity.getSupportFragmentManager().beginTransaction();
        ft.add(this.binding.fragmentContainerView.getId(), fragment);
        ft.commit();
    }

    @Override
    public void addFragmentWithAnim(Fragment fragment, int enterAnim, int leaveAnim){
        FragmentTransaction ft = this.activity.getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(enterAnim, leaveAnim);
        ft.add(this.binding.fragmentContainerView.getId(), fragment);
        this.curFragment = fragment;
        if(!(this.curFragment instanceof TitleScreenFragment) && !(this.curFragment instanceof SignUpFragment) && !(this.curFragment instanceof SignInFragment))
            ft.addToBackStack(null);
        ft.commit();
        
    }

    public void removeFragment(Fragment fragment){
        FragmentTransaction ft = this.activity.getSupportFragmentManager().beginTransaction();
        ft.remove(fragment);
        ft.commit();
    }

    @Override
    public void back() {
        this.activity.getSupportFragmentManager().popBackStackImmediate();
    }
}
