package edu.vassar.cmpu203.resolveandroid.view;

import android.view.View;

import androidx.fragment.app.Fragment;

public interface IMainView {

    public View getRootView();
    public void displayFragment(Fragment fragment);
    public void addFragment(Fragment fragment);

    void addFragmentWithAnim(Fragment fragment, int enterAnim, int leaveAnim);
    void addFragmentNoBackStack(Fragment fragment);

    public void back();
}
