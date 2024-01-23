package edu.vassar.cmpu203.resolveandroid.view;

import java.util.List;
import java.util.UUID;

import edu.vassar.cmpu203.resolveandroid.model.Org;

public interface IOrgListViewMvc {

    public interface Listener{

        public List<UUID> getUserOrgList();
        public void retrieveOrg(UUID uuid, IOrgListViewMvc view);
    }

    public void updateDisplay(Org org);
}
