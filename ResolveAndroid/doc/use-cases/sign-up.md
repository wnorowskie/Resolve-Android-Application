# User Creates Account (Sign-Up) - Fully Dressed

## Scope:
- New user creates an account
### Level:
- User Goal

## Primary Actor:
- User

## Stakeholders and Interests:
- First step in process to beginning the application. If user does not have a current account they must create a new one
- At first just includes basic username and password for account creation can add additional info (bio, photo, schedule, etc.)

## Preconditions
- Downloading the application and opening it.

## Postconditions
- User now has an account with username, password, bio, etc. and has functionality to then participate in additional features (creating schedule, creating groups, following friends/orgs, etc.)
- Eventually user should be able to then logout and login, but not until data persistance is incorporated.

## Main Success Scenario
1. User downloads the application.
2. User opens the application.
3. User is prompted to sign up with by inputing a username and password.
4. After information is entered the user will have an account created and the application will display the account profile.

## Extensions
    1a. User already has an account
1. User opens application but already has an account
2. Below the fragment asking user to input username and password, there will be a button to go to login screen
3. User clicks that button and is brought to the login page.
    
    2a. Username already exists
1. User enters an username that already exists in the system
2. User is prompted with a message that the username is taken already
3. Screen reloads and user can try a different username.

    3a. User quits application
1. User quits the application before signing up
2. Application restarts
2. When user opens it will be the sign up page again.

    3b. User Quits after signing up
1. User quits the application after signing up and being brought to their profile page.
2. Application shuts down.
3. When user opens application again he will be brought to login page or automatically signed in. (At this point no data persistence is expected so user would be forgotten)

## Special Requirements
### Usability
- Ideally this is extrememly easy and functional, it is the first step to using the application so it should run efficiently and be easy to use.
- All we are requiring at this point is a username and password, the simplest info needed (do not want to lose potential users due to this step of the process/application)
### Performance
- As mentioned above this should be an efficient and quick process, the only check that is needed is to see if the username is used by another account. Using an efficient Map for now will due, eventually would ideally want a database.
### Scalability
- System should be prepared to store a sufficient amount of user data.
### Security
- Password is senstive information, at this point there is no need for too much concern because data persistence is not required. Eventually when database is initiated, will be important to ensure password security.
### Technology and Data
- As mentioned before the system should be able to handle sufficient data that numerous users can create accounts. Users should have control of data and account after creation.
### Frequency of Occurence
- Every time a new user wants to create an account.