package edu.vassar.cmpu203.resolveandroid.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.UUID;



import edu.vassar.cmpu203.resolveandroid.R;

import edu.vassar.cmpu203.resolveandroid.databinding.FragmentFriendListBinding;
import edu.vassar.cmpu203.resolveandroid.databinding.FragmentOrgListBinding;
import edu.vassar.cmpu203.resolveandroid.databinding.FragmentSocialManagerBinding;
import edu.vassar.cmpu203.resolveandroid.model.Database;
import edu.vassar.cmpu203.resolveandroid.model.User;
import edu.vassar.cmpu203.resolveandroid.model.Org;
import edu.vassar.cmpu203.resolveandroid.model.User;


public class OrgListFragment extends Fragment implements IOrgListViewMvc {

    private FragmentOrgListBinding binding;
    private IOrgListViewMvc.Listener listener;


    public OrgListFragment(IOrgListViewMvc.Listener listener){this.listener = listener; }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.binding = FragmentOrgListBinding.inflate(inflater);
        return this.binding.getRoot();
    }

    private void populateOrgList(){
        this.binding.orgsList.removeView(this.binding.orgEntry);
        List<UUID> orgs = this.listener.getUserOrgList();
        Log.d("List", "getting orgs");
        for(UUID uuid: orgs){
            this.listener.retrieveOrg(uuid, this);
            // Log.d("Org", Database.getOrgRepo().get(o).getName());
        }
    }

    public void updateDisplay(Org o) {createOrgEntry(o.getName()); }

    private View createOrgEntry(String o){
        LayoutInflater inflater = LayoutInflater.from(this.getContext());
        inflater = inflater.cloneInContext(this.getContext());

        FragmentOrgListBinding newBinding = FragmentOrgListBinding.inflate(inflater);
        newBinding.orgsList.removeView(newBinding.orgEntry);
        newBinding.orgName.setText(o);
        this.binding.orgsList.addView(newBinding.orgEntry);

        return newBinding.orgEntry;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        populateOrgList();

    }

    }