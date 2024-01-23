package edu.vassar.cmpu203.resolveandroid.persistence;


import androidx.annotation.BinderThread;
import androidx.annotation.NonNull;

import java.util.UUID;

import edu.vassar.cmpu203.resolveandroid.model.DailySchedule;
import edu.vassar.cmpu203.resolveandroid.model.Event;
import edu.vassar.cmpu203.resolveandroid.model.Group;
import edu.vassar.cmpu203.resolveandroid.model.Org;
import edu.vassar.cmpu203.resolveandroid.model.User;
import edu.vassar.cmpu203.resolveandroid.model.WeeklySchedule;

public interface IPersistenceFacade {

    interface DataListener<T> {
            void onDataReceived(@NonNull T data);
            void onNoDataFound();
    }

    interface BinaryResultListener {
        void onYesResult();
        void onNoResult();
    }
    void createUserIfNotExists(@NonNull User user, @NonNull BinaryResultListener listener);
    void updateUser(@NonNull User user, @NonNull BinaryResultListener listener);
    void retrieveUser(@NonNull UUID uuid, @NonNull DataListener<User> listener);
    void retrieveUserFromUsername(@NonNull String username, @NonNull DataListener<User> listener);
    void saveUser(User u);
    void saveEvent(Event e);
    void retrieveSchedule(DataListener<DailySchedule> listener);

    void retrieveOrg(@NonNull UUID uuid, @NonNull DataListener<Org> listener);
    void createOrgIfNotExists(@NonNull Org org, @NonNull BinaryResultListener listener);

    void retrieveGroup(@NonNull UUID uuid, @NonNull DataListener<Group> listener);
    void createGroupIfNotExists(@NonNull Group group, @NonNull BinaryResultListener listener);
}
