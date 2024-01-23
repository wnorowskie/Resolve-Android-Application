package edu.vassar.cs.cs203.resolve;
import java.util.*;

public class TextUI {

    private static Controller controller = new Controller();
    private static User user = new User("", "");
    private static Scanner userInput = new Scanner(System.in);

    public static void main(String[] args)
    {
        Database.init();
        startApp();

        test();

        User test1 = controller.createUser("user1", "password", "user1", "Test Bio");
        User test2 = controller.createUser("user2", "password", "user2", "Test Bio");

        user = newOrExistingUser();

        test1.addPendingRequest(user);


        while(true) {
            userAction();
        }
    }

    public static String getUserInput(){
        //Get one line of input from the user and always check for the quit command
        String line = userInput.nextLine();
        if(line.equalsIgnoreCase("quit")){
            userQuit();
        }
        return line;
    }

    public static void userSearch(){
        // ask user for username of the person they want to search for
        System.out.println("What username would you like to search for?");
        String searchUser = getUserInput();
        searchUser = searchUser.toLowerCase();
        // Currently only able to search for a user by their exact username
        User userS1 = controller.getUser(searchUser);
        // if inputted username does not exactly match a username in the user repo, print "user does not exist"
        if(userS1 == null){
            System.out.println("User does not exist.");
        } else{
            // otherwise, print message and desired user
            System.out.println("User " + searchUser + " found!");
            System.out.println(userS1);
        }
    }

    public static void startApp(){
        // print at start of application
        System.out.println(
                "/*** Welcome to Resolve! ***/" + "\n" +
                        "An all in one scheduling application to connect you with friends!" + "\n" +
                        "This application features the ability to add friends, create friend groups, and start organization page\n " +
                        "To terminate the application enter 'quit' at any point");
    }
    public static User newOrExistingUser(){
        // ask user to sign up or log in
        while(true) {
            System.out.println("Enter 'SignUp' if you are a new user or 'Login' if you are an existing user or 'Quit' if you would like to close the application");
            String NoE = getUserInput();
            NoE = NoE.toLowerCase();

            switch (NoE) {
                case "signup":
                    return newUser();
                case "login":
                    return userLogin();
                default:
                    System.out.println("Invalid Command");
            }
        }
    }

    public static void userActionTypesDisplay(){
        // print available actions
        System.out.println(
                "Thank you for using Resolve! \n Here are the following action types available to you" +
                        " and the input needed to call those actions formatted as 'Action Type (Actions of action type) : Input"
                        + "\n" + "Display Actions (Profile, Friends, Org Membership, Group Membership) : Display"
                        + "\n" + "Request Actions (Send, Remove, Accept, Deny) : Request" + "\n" +
                        "Creation Actions (Organization, Group) : Create");

        String actionType = getUserInput();
        String actionTypeL = actionType.toLowerCase();

        switch(actionTypeL) {
            case "display":
                actionTypeDisplay();
                break;
            case "request":
                actionTypeRequest();
                break;
            case "create":
                actionTypeCreate();
                break;
            default:
                System.out.println("Invalid Command");
        }
    }
    public static void actionTypeDisplay(){
        // show available display actions
        System.out.println(
                "Here are the following actions available to you" +
                        " and the input needed to call those actions formatted as 'Action : Input" + "\n" + "\n" +

        "Display Your Profile : profile" + "\n" +
                "Display Your Friends : friends" + "\n" +
                "Display Orgs You Are A Member Of : orgs" + "\n" +
                "Display Groups You Are A Member Of : groups");

        System.out.println("Enter the action you would like to perform!");
        // get user input
        String displayAction = getUserInput();
        String displayActionL = displayAction.toLowerCase().replaceAll("\\s", "");
        userActionDisplay(displayActionL);
    }
    public static void actionTypeRequest(){
        // show available request actions
        System.out.println(
                "Here are the following actions available to you" +
                        " and the input needed to call those actions formatted as 'Action : Input" + "\n" + "\n" +
        "Send Friend Request : SendRequest" + "\n" +
                "Remove Friend Request : RemoveRequest" + "\n" +
                "Accept Friend Request : AcceptRequest" + "\n" +
                "Deny Friend Request : DenyRequest");
        // get user input
        String requestAction = getUserInput();
        String requestActionL = requestAction.toLowerCase().replaceAll("\\s", "");;
        userActionRequest(requestActionL);

    }
    public static void actionTypeCreate(){
        // show available create actions
        System.out.println(
                "Here are the following actions available to you" +
                        " and the input needed to call those actions formatted as 'Action : Input" + "\n" + "\n" +
                "Start an Organization : StartOrg" + "\n" +
                "Start a Group : StartGroup" + "\n");
        String createAction = getUserInput();
        String createActionL = createAction.toLowerCase().replaceAll("\\s", "");;
        userActionCreate(createActionL);
    }

    public static void displayFriends(){
        // check if friends list is empty
        if (user.getFriendsList().isEmpty()){
            System.out.println("You don't have any friends yet!");
        }
        else {
            // otherwise, print out each friend
            for (User u : user.getFriendsList()) {
                System.out.println(u);
            }
        }
    }

    public static void displayOrgs(){
        // check if org list is empty
        if (user.getOrgList().isEmpty()){
            System.out.println("You are not in any organizations yet!");
        } else {
            // otherwise, print every org user is a member of
            for (UUID id : user.getOrgList()) {
                Org org = controller.getOrg(id);
                if(org != null){
                    System.out.println(org);
                }
            }
        }
    }

    public static void displayGroups(){
        // if group list is empty, tell user they are not in any groups yet
        if (user.getGroupList().isEmpty()) {
            System.out.println("You are not in any groups yet!");
        } else {
            // otherwise, print each group that user is a member of
            for (UUID id : user.getGroupList()) {
                if (Database.getGroupRepo().containsKey(id))
                    System.out.print(Database.getGroupRepo().get(id));
            }
        }
    }

    // different display actions
    public static void userActionDisplay(String userActionDisplay){
        switch (userActionDisplay){
            case "profile":
                System.out.print(user.getProfile());
                break;
            case "friends":
                displayFriends();
                break;
            case "orgs":
                displayOrgs();
                break;
            case "groups":
                displayGroups();
                break;
            default:
                System.out.println("Invalid Command");
        }
    }

    public static void sendFriendRequest(){
        // get user input for the user they want to send a request to
        System.out.println("What is the username of the friend you would like to request?(input 'username')");
        String addUser = getUserInput();
        addUser = addUser.toLowerCase().replaceAll("\\s", "");;

        if(controller.userExists(addUser)){
            User userS2 = controller.getUser(addUser);
            if(user.addPendingRequest(userS2)){
                System.out.println("Your friend request has been sent!");
            } else {
                System.out.println("You have already sent a friend request to this user.");
            }
        } else {
            System.out.println("User does not exist");
        }
    }

    public static void removeFriendRequest(){
        // ask user for the username of the person that they want to remove the request
        System.out.println("Which user would you like to remove a request for? (input 'username')");
        String removeUserRequest = getUserInput().replaceAll("\\s", "");;
        String removeUser = removeUserRequest.toLowerCase();

        // check if user exists
        if(controller.userExists(removeUser)){
            // gets user from database
            User userR1 = controller.getUser(removeUser);
            // check if user has a pending request to specified user
            if(user.removePendingRequest(userR1)){
                System.out.println("This pending friend request has been removed.");
            } else {
                System.out.println("You have not sent a friend request to this user.");
            }
        } else {
            // if username does not match a user in the user repo, print "user does not exist"
            System.out.println("User does not exist.");
        }
    }

    public static void sendRequest(){
        // ask if user wants to search for a user or directly send a request to specific user
        System.out.println("Would you like to search users to request (input '1')? or" +
                " input the username for a friend you would like to request? (input '2') ");

        String srequestd = getUserInput().replaceAll("\\s", "");;
        switch (srequestd) {
            case "1":
                userSearch();
                break;
            case "2":
                sendFriendRequest();
                break;
            default:
                System.out.println("Invalid Command");
        }
    }

    public static void removeRequest(){
        // check if there are any outgoing pending requests
        if(user.getOutPendingRequests().isEmpty()){
            System.out.println("You have not sent any friend requests.");
            return;
        }

        System.out.println("Would you like to see the friend requests you currently have pending? (input '1') or input the username of the friend request you would like to remove? (input '2')");

        String rrequestd = getUserInput().replaceAll("\\s", "");;
        // if user wants to see all of their current, pending, outgoing friend requests
        switch (rrequestd) {
            case "1":
                // print all outgoing pending requests
                System.out.println("Outgoing Requests:");
                for (User u : user.getOutPendingRequests()) {
                    System.out.println(u.getUsername());
                }
                break;
            case "2":
                removeFriendRequest();
                break;
            default:
                System.out.println("Invalid Command");
        }
    }

    public static void acceptFriendRequest(){
        // ask for username of person that user wants to send a request to
        System.out.println("What is the username of the request you would like to accept? (input 'username') ");
        String acceptUserR = getUserInput().replaceAll("\\s", "");;
        acceptUserR = acceptUserR.toLowerCase();
        User userA2 = controller.getUser(acceptUserR);
        /// if inputted username does not exactly match a user in the user repo, print "user does not exist"
        if(userA2 == null){
            System.out.println("User does not exist.");
            // accept the request
        } else if(user.acceptRequest(userA2)){
            // confirm that friend request has been accepted
            System.out.println("This friend request has been accepted!");
        } else{
            // print message if user does not have a pending friend request from specified user
            System.out.println("This user has not sent you a friend request.");
        }
    }

    public static void acceptRequest(){
        // check if user has any incoming, pending friend requests
        if(user.getIncPendingRequests().isEmpty()){
            System.out.println("You do not have any incoming friend requests.");
            return;
        }
        System.out.println("Would you like to see the friend requests you currently have pending? (input '1') or input the username of the following request you would like to accept? (input '2')");

        String arequestd = getUserInput().replaceAll("\\s", "");;
        // if user chooses to see their pending friend requests
        if(arequestd.equals("1")){
            // print out all current, ingoing, pending friend requests
            System.out.println("Here are the friend requests you currently have pending:");
            for (User u : user.getIncPendingRequests()){
                System.out.println(u);
            }
        }
        // if user chooses to accept a request
        else if(arequestd.equals("2")){
            acceptFriendRequest();
        }
        else{
            System.out.println("Invalid Command");
        }
    }

    public static void denyFriendRequest(){
        // ask for username of the user they would like to deny the request from
        System.out.println("What is the username of the request you would like to deny?");
        String denyUserR = getUserInput();
        denyUserR = denyUserR.toLowerCase().replaceAll("\\s", "");;
        User userD2 = controller.getUser(denyUserR);
        // if username does not exactly match a user in user repo, print "user does not exist"
        if(userD2 == null){
            System.out.println("User does not exist.");
            // deny request from specified user
        } else if(user.denyRequest(userD2)){
            System.out.println("This friend request has been denied.");
        } else{
            // print message if user does not have a pending friend request from specified user
            System.out.println("This user has not sent you a friend request.");
        }
    }

    public static void denyRequest(){
        // check if user has any incoming, pending friend requests
        if(user.getIncPendingRequests().isEmpty()){
            System.out.println("You do not have any incoming friend requests.");
            return;
        }

        System.out.println("Would you like to see the friend requests you currently have pending? (input '1') or" +
                " input the username of the friend request you would like to deny? (input '2')");
        String drequestd = getUserInput().replaceAll("\\s", "");;

        // if user chooses to see their current friend requests
        if(drequestd.equals("1")){
            // print all of user's pending friend requests
            for (User u : user.getIncPendingRequests()){
                System.out.println(u);
            }
        }
        // if user chooses to deny a request
        else if(drequestd.equals("2")){
            denyFriendRequest();
        }
        else{
            System.out.println("Invalid Command");
        }
    }

    // user chooses "request" as their action
    public static void userActionRequest(String userActionRequest){
        switch (userActionRequest){
            case "sendrequest":
                sendRequest();
                break;
            case "removerequest":
                removeRequest();
                break;
            case "acceptrequest":
                acceptRequest();
                break;
            case "denyrequest":
                denyRequest();
                break;
            default:
                System.out.println("Invalid Command");

        }
    }
    // user chooses "create" as their action
    public static void userActionCreate(String userActionCreate){
        // user chooses to create an organization
        switch (userActionCreate) {
            case "startorg":
                System.out.println("Enter the name of the Organization you would like to start!");
                String orgName = getUserInput();
                // create organization
                if (controller.startOrg(orgName, user)) {
                    System.out.println("Your organization has been created!");
                } else {
                    // if name already in use, print message
                    System.out.println("An organization with that name already exists.");
                }
                break;
            case "startgroup":
                // if user chooses to start a group with no name
                System.out.println("Enter the name of the Group you would like to start!");
                String groupName = getUserInput();
                // create group
                controller.startGroup(user, groupName);
                break;
            default:
                System.out.println("Invalid command");
        }
    }
    public static void userAction(){
        userActionTypesDisplay();
    }

    public static void userQuit(){
        //Exit the program when the user enters the quit command
        System.out.println("User Quit");
        System.exit(0);
    }

    // creates new user
    public static User newUser(){
        while(true){
            String nusername = "";
            String npassword = "";
            String nname = "";
            String nbio = "";

            // ask for username
            System.out.println("Please enter your desired username:");
            nusername = getUserInput();
            // usernames are not case sensitive, passwords are
            nusername = nusername.toLowerCase().replaceAll("\\s", "");;

            // ask for password
            System.out.println("Please enter your desired password:");
            npassword = getUserInput();

            // ask for name
            System.out.println("Enter your full name:");
            nname = getUserInput();

            // ask for bio
            System.out.println(String.format("Please enter your bio. %d characters max:", Profile.getMaxBioChars()));
            nbio = getUserInput();

            if(controller.userExists(nusername)){
                System.out.println("That user already exists");
            }
            User nUser = controller.createUser(nusername, npassword, nname, nbio);
            if(nUser != null){
                System.out.println("Signup successful!");
                return nUser;
            } else {
                System.out.println("Bio too long, try again");
            }
        }
    }

    // logs in existing user
    public static User userLogin(){
        User eUser;
        while(true) {
            // ask for username
            System.out.println("Please enter your username:\n");
            String eusername = getUserInput();
            eusername = eusername.toLowerCase().replaceAll("\\s", "");;
            // ask for password
            System.out.println("Please enter your password:\n");
            String epassword = getUserInput();

            eUser = controller.validateCreds(eusername, epassword);

            if(eUser != null){
                return eUser;
            } else {
                System.out.println("Invalid Login");
            }
        }
    }

    public static void test() {
        User t1 = controller.createUser("aaaa", "1234", "a", "a bio");
        User t2 = controller.createUser("bbbb", "2345", "b", "b bio");
        User t3 = controller.createUser("cccc", "3456", "c", "c bio");
        User t4 = controller.createUser("dddd", "4567", "d", "d bio");

        // create new group, add first 3 users to group, add group to group list of those members
        ArrayList<User> aListUser = new ArrayList<User>();
        Group group = new Group("group1", aListUser);
        Database.addGroup(group);
        group.addMember(t1);
        group.addMember(t2);
        group.addMember(t3);
        t1.addGroup(group);
        t2.addGroup(group);
        t3.addGroup(group);

        // create org, add 4 users to org, add org to org list of those members (+ 1 admin)
        Org org = new Org("org1", t1);
        Database.addOrg(org);
        Profile profile = new Profile("organization1");
        profile.setBio("The best organization on campus!");
        org.setProfile(profile);
        org.addMember(t1);
        org.addMember(t2);
        org.addMember(t3);
        org.addMember(t4);
        t1.addOrg(org);
        t2.addOrg(org);
        t3.addOrg(org);
        t4.addOrg(org);
    }
}
