package edu.vassar.cmpu203.resolveandroid.view;

import java.util.List;
import java.util.UUID;

import edu.vassar.cmpu203.resolveandroid.model.Group;

public interface IGroupViewViewMvc {

    public interface Listener{
        public List<UUID> getGroupList();
        public void retrieveGroup(UUID uuid, IGroupViewViewMvc view);
    }

    public void updateDisplay(Group g);
}
