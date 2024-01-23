package edu.vassar.cmpu203.resolveandroid.controller;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import edu.vassar.cmpu203.resolveandroid.R;
import edu.vassar.cmpu203.resolveandroid.databinding.FragmentScheduleBinding;
import edu.vassar.cmpu203.resolveandroid.model.Controller;
import edu.vassar.cmpu203.resolveandroid.model.Database;
import edu.vassar.cmpu203.resolveandroid.model.Event;
import edu.vassar.cmpu203.resolveandroid.model.EventTime;
import edu.vassar.cmpu203.resolveandroid.model.Group;
import edu.vassar.cmpu203.resolveandroid.model.Org;
import edu.vassar.cmpu203.resolveandroid.model.Profile;
import edu.vassar.cmpu203.resolveandroid.model.User;
import edu.vassar.cmpu203.resolveandroid.model.Weekday;
import edu.vassar.cmpu203.resolveandroid.persistence.FirestoreFacade;
import edu.vassar.cmpu203.resolveandroid.persistence.IPersistenceFacade;
import edu.vassar.cmpu203.resolveandroid.view.AddEventFragment;
import edu.vassar.cmpu203.resolveandroid.view.EventDescriptionFragment;
import edu.vassar.cmpu203.resolveandroid.view.FriendListFragment;
import edu.vassar.cmpu203.resolveandroid.view.FriendRequestFragment;
import edu.vassar.cmpu203.resolveandroid.view.GroupViewFragment;
import edu.vassar.cmpu203.resolveandroid.view.IAddEventViewMvc;
import edu.vassar.cmpu203.resolveandroid.view.IAuthViewMvc;
import edu.vassar.cmpu203.resolveandroid.view.IEventDescriptionFragmentMvc;
import edu.vassar.cmpu203.resolveandroid.view.IFriendRequestViewMvc;
import edu.vassar.cmpu203.resolveandroid.view.IFriendsListViewMvc;
import edu.vassar.cmpu203.resolveandroid.view.IGroupViewViewMvc;
import edu.vassar.cmpu203.resolveandroid.view.IMainView;
import edu.vassar.cmpu203.resolveandroid.view.IOrgListViewMvc;
import edu.vassar.cmpu203.resolveandroid.view.IScheduleViewMvc;
import edu.vassar.cmpu203.resolveandroid.view.ISignUpViewMvc;
import edu.vassar.cmpu203.resolveandroid.view.ISocialManagerViewMvc;
import edu.vassar.cmpu203.resolveandroid.view.MainView;
import edu.vassar.cmpu203.resolveandroid.view.OrgListFragment;
import edu.vassar.cmpu203.resolveandroid.view.ScheduleFragment;
import edu.vassar.cmpu203.resolveandroid.view.SignInFragment;
import edu.vassar.cmpu203.resolveandroid.view.SignUpFragment;
import edu.vassar.cmpu203.resolveandroid.view.SocialManagerFragment;
import edu.vassar.cmpu203.resolveandroid.view.TitleScreenFragment;

public class ControllerActivity extends AppCompatActivity
        implements ISignUpViewMvc.Listener, IAddEventViewMvc.Listener, ISocialManagerViewMvc.Listener, IFriendRequestViewMvc.Listener,
        IGroupViewViewMvc.Listener, IFriendsListViewMvc.Listener, IOrgListViewMvc.Listener, IAuthViewMvc.Listener, IScheduleViewMvc.Listener, IEventDescriptionFragmentMvc.Listener {
    private User user;
    private Weekday curDay;
    private IMainView mainView;

    private final IPersistenceFacade persistenceFacade = new FirestoreFacade();

    private static final String CUR_USER = "curUser";


    @Override
    protected void onCreate(Bundle savedInstanceState){

        getSupportFragmentManager().
                setFragmentFactory(new ResolveFragFactory(this));

        super.onCreate(savedInstanceState);

        if(savedInstanceState != null)
            this.user = (User) savedInstanceState.getSerializable(CUR_USER);

        Database.init();
        this.mainView = new MainView(this);
        setContentView(this.mainView.getRootView());

        if(savedInstanceState == null) {
            //TODO add title screen fragment

            this.mainView.displayFragment(new TitleScreenFragment());
            this.mainView.addFragment(new SignUpFragment(this));
        }
    }

    @Override
    public void onBackPressed() {
        this.mainView.back();
    }

    @Override
    public void onSignInAttempt(String username, String password, ISignUpViewMvc view){
        User u = new User(username, password);
        this.persistenceFacade.retrieveUserFromUsername(username, new IPersistenceFacade.DataListener<User>() {
            @Override
            public void onDataReceived(@NonNull User data) {
                if(u.validatePassword(password)){
                    ControllerActivity.this.user = data;
                    ControllerActivity.this.mainView.displayFragment(new SocialManagerFragment(ControllerActivity.this));
                } else view.onInvalidCreds();
            }

            @Override
            public void onNoDataFound() {
                view.onInvalidCreds();
            }
        });
        /*User u = Controller.validateCreds(username, password);
        if(u != null) {
            this.user = u;
            this.mainView.displayFragment(new SocialManagerFragment(this));
            return true;
        }
        return false;

         */
    }

    @Override
    public void onSignUpAttempt(String username, String password, ISignUpViewMvc signUpViewMvc){

        User u = new User(username, password);

        this.persistenceFacade.createUserIfNotExists(u, new IPersistenceFacade.BinaryResultListener() {
            @Override
            public void onYesResult() {

                String name = "", bio = "";
                Profile p = new Profile(name);

                if (p.setBio(bio)) {
                    // set profile
                    u.setProfile(p);

                    ControllerActivity.this.user = u;
                    // add user to database
                    // Database.addUser(u);
                    //Adding some default users here to test functionality
                    User temp = new User("tester1", "password");
                    //temp.addPendingRequest(u);
                    User temp1 = new User("tester2", "password");
                    //temp1.addPendingRequest(u);

                    ControllerActivity.this.createUserIfNotExists(temp);
                    ControllerActivity.this.createUserIfNotExists(temp1);

                    Org tempOrg = new Org("CMPU 203 club", user);
                    Org tempOrg2 = new Org("Lavagirl fan club", user);

                    addPendingRequestToUser("tester2");
                    addPendingRequestToUser("tester1");

                    user.addOrg(tempOrg);
                    user.addOrg(tempOrg2);

                    ControllerActivity.this.createOrgIfNotExists(tempOrg);
                    //Database.addOrg(tempOrg);
                    //Database.addOrg(tempOrg2);

                    ArrayList<User> memberList = new ArrayList();
                    memberList.add(temp);
                    memberList.add(temp1);
                    memberList.add(user);

                    Group tempGroup = new Group("Team 1c", memberList);

                    user.addGroup(tempGroup);
                    ControllerActivity.this.createGroupIfNotExists(tempGroup);
                    //Database.addGroup(tempGroup);

                    //persistenceFacade.saveUser(u);

                    ControllerActivity.this.updateUser();
                    ControllerActivity.this.mainView.displayFragment(new SocialManagerFragment(ControllerActivity.this));
                }
            }

            @Override
            public void onNoResult() {
                signUpViewMvc.onUserAlreadyExists();
            }
        });
    }

    private void addPendingRequestToUser(String username) {
        ControllerActivity.this.persistenceFacade.retrieveUserFromUsername(username, new IPersistenceFacade.DataListener<User>() {
            @Override
            public void onDataReceived(@NonNull User data) {
                data.addPendingRequest(user);
                updateUser();
            }

            @Override
            public void onNoDataFound() {
                // error message
            }
        });
    }

    @Override
    public void onClickGroups(){
                ControllerActivity.this.mainView.displayFragment(new GroupViewFragment(ControllerActivity.this));
    }

    public void createUserIfNotExists(User u){
        this.persistenceFacade.createUserIfNotExists(u, new IPersistenceFacade.BinaryResultListener() {
            @Override
            public void onYesResult() {
                // nothing
            }

            @Override
            public void onNoResult() {
                //TODO: error
            }
        });
    }

    @Override
    public void onClickSchedule(){
        ScheduleFragment f = new ScheduleFragment(this);
        AddEventFragment button = new AddEventFragment(this);
        f.setAddEventButtonFragment(button);
        this.mainView.displayFragment(f);
        this.mainView.addFragmentNoBackStack(button);
    }

    @Override
    public void onClickFriends() {
        this.mainView.displayFragment(new FriendListFragment(this, this));
    }

    @Override
    public void returningUser(){
        this.mainView.displayFragment(new TitleScreenFragment());
        this.mainView.addFragment(new SignInFragment(this));
    }

    @Override
    public void newUser(){
        this.mainView.displayFragment(new TitleScreenFragment());
        this.mainView.addFragment(new SignUpFragment(this));
    }

    public void onAddEvent(String title, EventTime startTime, EventTime endTime, Weekday day, View view) {
        Event e = new Event(startTime, endTime);
        e.setTitle(title);

        for(Event event : user.getSchedule().getDailySchedule(day).getEvents()){
            if(event.equals(e)){
                Snackbar.make(view, "Duplicate Event", Snackbar.LENGTH_LONG).show();
                Log.d("Add Event:", "Duplicate");
                return;
            }
        }
        //TODO make this better
        List<Weekday> w = new ArrayList<>();
        w.add(day);
        user.getSchedule().addEvent(e, w);

        this.updateUser();

    }


    @Override
    public void onRequestFriend(User u) {
        user.addPendingRequest(u);
        //TODO: update database to reflect this
        updateUser();
        updateOtherUser(u);
    }

    public void retrieveUserFriendRequests(UUID uuid, IFriendRequestViewMvc view){

        this.persistenceFacade.retrieveUser(uuid, new IPersistenceFacade.DataListener<User>() {
            @Override
            public void onDataReceived(@NonNull User data) {
                view.updateDisplay(data);
            }
            @Override
            public void onNoDataFound() {
                //TODO: error
            }
        });
    }

    public void retrieveUserFriendsList(UUID uuid, IFriendsListViewMvc view){
        this.persistenceFacade.retrieveUser(uuid, new IPersistenceFacade.DataListener<User>() {
            @Override
            public void onDataReceived(@NonNull User data) {
                view.updateDisplay(data, false);
            }
            @Override
            public void onNoDataFound() {
                //TODO: error
            }
        });
    }

    public void retrieveOrg(UUID uuid, IOrgListViewMvc view){
        this.persistenceFacade.retrieveOrg(uuid, new IPersistenceFacade.DataListener<Org>() {
            @Override
            public void onDataReceived(@NonNull Org data) {
                view.updateDisplay(data);
            }

            @Override
            public void onNoDataFound() {
                //TODO: error
            }
        });
    }

    public void retrieveGroup(UUID uuid, IGroupViewViewMvc view){
        this.persistenceFacade.retrieveGroup(uuid, new IPersistenceFacade.DataListener<Group>() {
            @Override
            public void onDataReceived(@NonNull Group data) {
                view.updateDisplay(data);
            }

            @Override
            public void onNoDataFound() {
                // TODO : error
            }
        });
    }

    public void createOrgIfNotExists(Org org){
        this.persistenceFacade.createOrgIfNotExists(org, new IPersistenceFacade.BinaryResultListener() {
            @Override
            public void onYesResult() {
                // nothing
            }

            @Override
            public void onNoResult() {
                // org already existed
            }
        });
    }

    public void createGroupIfNotExists(Group group){
        this.persistenceFacade.createGroupIfNotExists(group, new IPersistenceFacade.BinaryResultListener() {
            @Override
            public void onYesResult() {
                // nothing
            }

            @Override
            public void onNoResult() {
                // group already existed
            }
        });
    }

    private void updateOtherUser(User u) {
        this.persistenceFacade.updateUser(u, new IPersistenceFacade.BinaryResultListener() {
            @Override
            public void onYesResult() {
                // pending request received
            }

            @Override
            public void onNoResult() {
                //TODO: error
            }
        });
    }

    private void updateUser() {
        this.persistenceFacade.updateUser(this.user, new IPersistenceFacade.BinaryResultListener() {
            @Override
            public void onYesResult() {
                // pending request sent
            }

            @Override
            public void onNoResult() {
                //TODO: error message, could not add pending request
            }
        });

        /**
         * this.persistenceFacade.retrieveUser(u.getUsername(), new IPersistenceFacade.DataListener<u>{
         *             @Override
         *             public void onDataReceived(@NonNull T data){
         *
         *             }
         *             @Override
         *             public void onNoDataFound(){
         *
         *         }
         *                 //}
         */


    }

    @Override
    public void onCreateGroup(String groupname, ArrayList<User> memberList, ISocialManagerViewMvc socialManagerViewMvc) {
        Group group = new Group(groupname, memberList);
        Database.addGroup(group);
        for(User u : memberList){
            u.addGroup(group);
        }
        //TODO: check if group is already in database before adding it

        /**
        this.persistenceFacade.retrieveGroup(group.getMembers(), new IPersistenceFacade().DataListener<group> {
            @Override
            public void onDataReceived(@NonNull T data){

            }
            @Override
            public void onNoDataFound(){

            }
        });
         **/

    }

    @Override
    public void onLeaveGroup(Group group, ISocialManagerViewMvc socialManagerViewMvc) {
        user.leaveGroup(group);
        //TODO: update database

        /**
         this.persistenceFacade.retrieveGroup(group.getMembers(), new IPersistenceFacade().DataListener<group> {
        @Override
        public void onDataReceived(@NonNull T data){

        }
        @Override
        public void onNoDataFound(){

        }
        });
         **/

        /**
         this.persistenceFacade.retrieveUser(user.getGroups(), new IPersistenceFacade().DataListener<group> {
        @Override
        public void onDataReceived(@NonNull T data){

        }
        @Override
        public void onNoDataFound(){

        }
        });
         **/
    }

    @Override
    public List<UUID> getGroupList(){ return this.user.getGroupList();}

    @Override
    public void onAddOrg(Org org, ISocialManagerViewMvc socialManagerViewMvc) {
        user.addOrg(org);
        //TODO: update database

        /**
         this.persistenceFacade.retrieveOrg(org.getName, new IPersistenceFacade().DataListener<group> {
        @Override
        public void onDataReceived(@NonNull T data){

        }
        @Override
        public void onNoDataFound(){

        }
        });
         **/
    }

    @Override
    public void onLeaveOrg(Org org, ISocialManagerViewMvc socialManagerViewMvc) {
        user.leaveOrg(org);
        //TODO: implement method/change parameters if needed
    }

    @Override
    public List<UUID> getUserOrgList(){ return this.user.getOrgList();}

    @Override
    public List<UUID> getIncRequests() {
        return this.user.getIncPendingRequests();
    }

    @Override
    public void onAcceptRequest(User newFriend) {
        //This will only be false if newFriend wasn't in the request list
        this.user.acceptRequest(newFriend);

        updateUser();
        updateOtherUser(newFriend);

        /**
         this.persistenceFacade.retrieveUser(newFriend.getUsername(), new IPersistenceFacade().DataListener<group> {
        @Override
        public void onDataReceived(@NonNull T data){

        }
        @Override
        public void onNoDataFound(){

        }
        });
         **/

        /**
         this.persistenceFacade.retrieveUser(this.getUsername(), new IPersistenceFacade().DataListener<group> {
        @Override
        public void onDataReceived(@NonNull T data){

        }
        @Override
        public void onNoDataFound(){

        }
        });
         **/

    }

    @Override
    public void onDenyRequest(User deniedFriend) {
        //This will only be false if deniedFriend wasn't in the request list
        this.user.denyRequest(deniedFriend);

        updateUser();
        updateOtherUser(deniedFriend);
    }

    @Override
    public List<UUID> getFriends() {
        return this.user.getFriendsList();
    }

    @Override
    public void onReturntoManager() {
        this.mainView.displayFragment(new SocialManagerFragment(this));
    }

    @Override
    public void onClickViewPending() {
        this.mainView.displayFragment(new FriendRequestFragment(this));
    }

    @Override
    public void onReturnToFriendList() {
        this.mainView.displayFragment(new FriendListFragment(this, this));
    }

    @Override
    public String getCurUsername() {
        return this.user.getUsername();
    }

    @Override
    public void onClickOrganizations() {
        this.mainView.displayFragment(new OrgListFragment(this));
    }

    @Override
    public void onSearchForUser(String query, IFriendsListViewMvc view) {
        this.persistenceFacade.retrieveUserFromUsername(query, new IPersistenceFacade.DataListener<User>(){
            @Override
            public void onDataReceived(@NonNull User user) {
                if(!(user.getUuid().equals(ControllerActivity.this.user.getUuid()) || ControllerActivity.this.user.getFriendsList().contains(user.getUuid()) ||
                ControllerActivity.this.user.getOutPendingRequests().contains(user.getUuid()) || ControllerActivity.this.user.getIncPendingRequests().contains(user.getUuid()))) {

                    view.updateDisplay(user, true);
                }
            }
            @Override
            public void onNoDataFound() { }
        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putSerializable(CUR_USER, this.user);
    }

    public User getUser(){return this.user;}

    public IMainView getMainView() {
        return mainView;
    }

    public IPersistenceFacade getPersistenceFacade() {
        return persistenceFacade;
    }

    public Weekday getCurDay() {
        return curDay;
    }

    @Override
    public void onRegister(String username, String password, IAuthViewMvc authView) {
        User user = new User(username, password); // our tentative user
        this.persistenceFacade.createUserIfNotExists(user,
                new IPersistenceFacade.BinaryResultListener() {
                    @Override
                    public void onYesResult() { authView.onRegisterSuccess(); }
                    @Override
                    public void onNoResult() { authView.onUserAlreadyExists(); }
                });
    }

    @Override
    public void onSigninAttempt(String username, String password, IAuthViewMvc authView) {
        this.persistenceFacade.retrieveUserFromUsername(username, new IPersistenceFacade.DataListener<User>(){
                    @Override
                    public void onDataReceived(@NonNull User user) {
                        if (user.validatePassword(password)){
                            ControllerActivity.this.user = user; // we have a new user
                            // ControllerActivity.this.mainView.displayFragment(
                                    // new LedgerFragment(ControllerActivity.this));
                        } else authView.onInvalidCredentials(); // let view know validation failed
                    }
                    @Override
                    public void onNoDataFound() {
                        authView.onInvalidCredentials();
                    }
                });
    }

    @Override
    public List<Event> loadEventsForDay(int day, User u) {
        if(u == null){
            return user.getSchedule().getDailySchedule(Weekday.values().get(day)).getEvents();
        } else {
            return u.getSchedule().getDailySchedule(Weekday.values().get(day)).getEvents();
        }
    }

    @Override
    public Fragment onClickEvent(Event e, View creator, FragmentScheduleBinding binding, boolean canEdit) {
        EventDescriptionFragment f = new EventDescriptionFragment(this, e, creator, binding, canEdit);
        this.mainView.addFragmentWithAnim(f, R.anim.event_info_in, R.anim.event_info_out);
        return f;
    }

    @Override
    public void onScrollEventList(Fragment f) {
        this.mainView.back();
    }

    @Override
    public void closeEventDescription(Fragment f) {
        this.mainView.back();
    }

    @Override
    public void onViewFriendSchedule(User u) {
        Fragment friendScheduleFragment = new ScheduleFragment(this, u);
        this.mainView.displayFragment(friendScheduleFragment);
    }

    @Override
    public void deleteEventFromSchedule(Event event) {
        this.user.getSchedule().removeEvent(event);

        this.persistenceFacade.updateUser(user, new IPersistenceFacade.BinaryResultListener() {
            @Override
            public void onYesResult() {
                // nothing needs to happen, event is updated in database
            }

            @Override
            public void onNoResult() {
                //TODO: error message, could not add event
            }
        });
    }
}
