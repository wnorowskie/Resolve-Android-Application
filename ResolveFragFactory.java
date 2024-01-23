package edu.vassar.cmpu203.resolveandroid.controller;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentFactory;

import edu.vassar.cmpu203.resolveandroid.view.FriendListFragment;
import edu.vassar.cmpu203.resolveandroid.view.FriendRequestFragment;
import edu.vassar.cmpu203.resolveandroid.view.GroupViewFragment;
import edu.vassar.cmpu203.resolveandroid.view.OrgListFragment;
import edu.vassar.cmpu203.resolveandroid.view.ScheduleFragment;
import edu.vassar.cmpu203.resolveandroid.view.SignInFragment;
import edu.vassar.cmpu203.resolveandroid.view.SignUpFragment;
import edu.vassar.cmpu203.resolveandroid.view.SocialManagerFragment;

public class ResolveFragFactory extends FragmentFactory {

    private ControllerActivity controller;

    ResolveFragFactory(ControllerActivity controller){
        this.controller = controller;
    }

    @NonNull
    @Override
    public Fragment instantiate(@NonNull ClassLoader classLoader, @NonNull String className) {

        Class<? extends Fragment> fragmentClass = loadFragmentClass(classLoader, className);

        Fragment fragment;
        //if (fragmentClass == TitleScreenFragment.class)
          //  fragment = new TitleScreenFragment(controller);
        //else
        if (fragmentClass == SocialManagerFragment.class)
            fragment = new SocialManagerFragment(controller);
        else if (fragmentClass == SignUpFragment.class)
            fragment = new SignUpFragment(controller);
        else if (fragmentClass == SignInFragment.class)
            fragment = new SignInFragment(controller);
        else if (fragmentClass == OrgListFragment.class)
            fragment = new OrgListFragment(controller);
        else if (fragmentClass == GroupViewFragment.class)
            fragment = new GroupViewFragment(controller);
        else if (fragmentClass == FriendRequestFragment.class)
            fragment = new FriendRequestFragment(controller);
        else if (fragmentClass == FriendListFragment.class)
            fragment = new FriendListFragment(controller, controller);
        else if(fragmentClass == ScheduleFragment.class)
            fragment = new ScheduleFragment(controller);
        else fragment = super.instantiate(classLoader, className);

        return fragment;
    }
}
