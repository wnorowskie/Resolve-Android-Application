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
import edu.vassar.cmpu203.resolveandroid.databinding.FragmentGroupViewBinding;
import edu.vassar.cmpu203.resolveandroid.databinding.FragmentOrgListBinding;
import edu.vassar.cmpu203.resolveandroid.databinding.FragmentSocialManagerBinding;
import edu.vassar.cmpu203.resolveandroid.model.Database;
import edu.vassar.cmpu203.resolveandroid.model.Group;

public class GroupViewFragment extends Fragment implements IGroupViewViewMvc {
    private FragmentGroupViewBinding binding;
    private IGroupViewViewMvc.Listener listener;

    public GroupViewFragment(IGroupViewViewMvc.Listener listener){ this.listener = listener; }

    private void populateGroupList(){
        this.binding.groupList.removeView(this.binding.groupsEntry);
        List<UUID> groups = this.listener.getGroupList();
        Log.d("List", "getting orgs");
        for(UUID uuid: groups){
            this.listener.retrieveGroup(uuid, this);
            //createGroupEntry(uuid.toString());
            // Log.d("Group", Database.getGroupRepo().get(g).getName());
        }
    }

    public void updateDisplay(Group g){ createGroupEntry(g.getName()); }

    private View createGroupEntry(String g){
        LayoutInflater inflater = LayoutInflater.from(this.getContext());
        inflater = inflater.cloneInContext(this.getContext());

        FragmentGroupViewBinding newBinding = FragmentGroupViewBinding.inflate(inflater);
        newBinding.groupList.removeView(newBinding.groupsEntry);
        newBinding.groupName.setText(g);
        this.binding.groupList.addView(newBinding.groupsEntry);

        return newBinding.groupsEntry;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.binding = FragmentGroupViewBinding.inflate(inflater);
        return this.binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        populateGroupList();
    }
}