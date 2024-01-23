package edu.vassar.cmpu203.resolveandroid.persistence;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import edu.vassar.cmpu203.resolveandroid.model.DailySchedule;
import edu.vassar.cmpu203.resolveandroid.model.Event;
import edu.vassar.cmpu203.resolveandroid.model.Group;
import edu.vassar.cmpu203.resolveandroid.model.Org;
import edu.vassar.cmpu203.resolveandroid.model.User;


public class FirestoreFacade implements IPersistenceFacade{
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private static final String EVENTS = "events";
    private static final String USERS = "users";
    private static final String USERNAMES = "usernames";
    private static final String UUID_STRING = "uuidstring";
    private static final String GROUPS = "groups";
    private static final String ORGS = "orgs";


    @Override
    public void createUserIfNotExists(@NonNull User user, @NonNull BinaryResultListener listener) {
        this.retrieveUserFromUsername(user.getUsername(), new DataListener<User>() {
            @Override
            public void onDataReceived(User data) { listener.onNoResult(); } // user already exists
            @Override
            public void onNoDataFound() { // there's no such user, so we can add it
                FirestoreFacade.this.setUser(user, listener);
            }
        });
    }

    private void setUser(@NonNull User user, @NonNull BinaryResultListener listener){
        this.db.collection(USERS)
                .document(user.getUuid().toString())
                .set(user.toMap())
                .addOnSuccessListener(avoid -> listener.onYesResult())
                .addOnFailureListener(e ->
                        Log.w("NextGenPos", "Error setting user in database",e));

        Map<String, Object> uuidMap = new HashMap<>();
        uuidMap.put(UUID_STRING, user.getUuid().toString());
        this.db.collection(USERNAMES)
                .document(user.getUsername())
                .set(uuidMap)
                .addOnFailureListener(e ->
                        Log.w("NextGenPos", "Error setting uuid in database",e));
    }

    public void retrieveUser(@NonNull UUID uuid, @NonNull DataListener<User> listener) {
        this.db.collection(USERS)
                .document(uuid.toString())
                .get()
                .addOnSuccessListener(dsnap -> {
                    if (dsnap.exists()) { // got some data back
                        User user = User.fromMap(dsnap.getData());
                        listener.onDataReceived(user);
                    } else listener.onNoDataFound(); // no data retrieved, means no username match
                })
                .addOnFailureListener(e ->
                            Log.w("NextGenPos", "Error retrieving user from database", e));
        }
    @Override
    // unnecessary?
    public void saveUser(User u) {
        db.collection(USERS).add(u);
    }

    public void updateUser(@NonNull User user, @NonNull BinaryResultListener listener){
        this.retrieveUser(user.getUuid(), new DataListener<User>() {
            @Override
            public void onDataReceived(@NonNull User data) {
                FirestoreFacade.this.setUser(user, listener);
            }

            @Override
            public void onNoDataFound() {
                listener.onNoResult();
            }
        });
    }

    public void retrieveUserFromUsername(@NonNull String username, @NonNull DataListener<User> listener){
        this.db.collection(USERNAMES)
                .document(username)
                .get()
                .addOnSuccessListener(dsnap -> {
                    if (dsnap.exists()) {
                        UUID uuid = UUID.fromString((String) dsnap.get(UUID_STRING));
                        this.retrieveUser(uuid, listener);
                    } else listener.onNoDataFound();
                })
                .addOnFailureListener(e ->
                Log.w("NextGenPos", "Error retrieving uuid from database", e));
    }

    @Override
    public void saveEvent(Event e) {
        db.collection(EVENTS).add(e);
    }

    @Override
    public void retrieveSchedule(DataListener<DailySchedule> listener) {

        db.collection(EVENTS).
                get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot qsnap) {
                DailySchedule schedule = new DailySchedule();
                for (DocumentSnapshot dsnap : qsnap){
                    Event e = dsnap.toObject(Event.class);
                    schedule.addEvent(e);
                }
                listener.onDataReceived(schedule);
            }
        });
    }

    @Override
    public void createOrgIfNotExists(@NonNull Org org, @NonNull BinaryResultListener listener) {
        this.retrieveOrg(org.getUUID(), new DataListener<Org>() {
            @Override
            public void onDataReceived(Org data) { listener.onNoResult(); } // user already exists
            @Override
            public void onNoDataFound() { // there's no such user, so we can add it
                FirestoreFacade.this.setOrg(org, listener);
            }
        });
    }

    @Override
    public void retrieveOrg(@NonNull UUID uuid, @NonNull DataListener<Org> listener){
        this.db.collection(ORGS)
                .document(uuid.toString())
                .get()
                .addOnSuccessListener(dsnap -> {
                    if (dsnap.exists()) { // got some data back
                        Org org = Org.fromMap(dsnap.getData());
                        listener.onDataReceived(org);
                    } else listener.onNoDataFound(); // no data retrieved means no uuid match
                })
                .addOnFailureListener(e ->
                        Log.w("NextGenPos", "Error retrieving org from database", e));
    }

    private void setOrg(@NonNull Org org, @NonNull BinaryResultListener listener){
        this.db.collection(ORGS)
                .document(org.getUUID().toString())
                .set(org.toMap())
                .addOnSuccessListener(avoid -> listener.onYesResult())
                .addOnFailureListener(e ->
                        Log.w("NextGenPos", "Error setting org in database",e));
    }

    @Override
    public void createGroupIfNotExists(@NonNull Group group, @NonNull BinaryResultListener listener) {
        this.retrieveGroup(group.getUUID(), new DataListener<Group>() {
            @Override
            public void onDataReceived(Group data) { listener.onNoResult(); } // user already exists
            @Override
            public void onNoDataFound() { // there's no such user, so we can add it
                FirestoreFacade.this.setGroup(group, listener);
            }
        });
    }

    @Override
    public void retrieveGroup(@NonNull UUID uuid, @NonNull DataListener<Group> listener){
        this.db.collection(GROUPS)
                .document(uuid.toString())
                .get()
                .addOnSuccessListener(dsnap -> {
                    if (dsnap.exists()) { // got some data back
                        Group group = Group.fromMap(dsnap.getData());
                        listener.onDataReceived(group);
                    } else listener.onNoDataFound(); // no data retrieved means no uuid match
                })
                .addOnFailureListener(e ->
                        Log.w("NextGenPos", "Error retrieving group from database", e));
    }

    private void setGroup(@NonNull Group group, @NonNull BinaryResultListener listener){
        this.db.collection(GROUPS)
                .document(group.getUUID().toString())
                .set(group.toMap())
                .addOnSuccessListener(avoid -> listener.onYesResult())
                .addOnFailureListener(e ->
                        Log.w("NextGenPos", "Error setting group in database",e));
    }
}
