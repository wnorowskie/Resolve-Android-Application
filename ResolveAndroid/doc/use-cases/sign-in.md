# User Signs In (Login) - Fully Dressed

## Scope:
- Prexisting user logs in.
### Level:
- User Goal

## Primary Actor:
- User

## Stakeholders and Interests:
- After a user creates an account they should be able to log back in to the account they created.
- At current iterations data persistence is not required. However, we have created the set up for the log-in screen so that when it does become relevant we can provide functionality.
- Being able to return to the application while mainting the profile is essential to usability.

## Preconditions
- User has already created an account.

## Postconditions
- Once user signs in they will be brought to their profile page
- Will be able to see all their friends, schedules, etc. from the last time they were logged in (data persistence required)

## Main Success Scenario
1. User opens the application.
2. User is prompted with the create account page.
3. User clicks on button to go to the log-in page.
4. At log-in page user is prompted to enter username and password of account.
5. User enters proper credentials and is brought to their profile page.
6. User has ability to access other features once logged in.

## Extensions
    1a. User enters improper credentials: Username
1. User enters a username that is not recognized by the system
2. User is prompted with a message that their username is not recognized.
3. User can re-enter username and password to try again.
    
    1b. User enters improper credentials: Password
1. User enters a username that is recognized by the system but the password is not correct.
2. User is prompted with a message that they have entered the incorrect password.
3. user can re-enter username and password to try again.

    2a.  User quits application
1. User quits the application before logging in
2. Application restarts
2. When user opens it will be the sign up page again.

    2b. User Quits after signing up
1. User quits the application after logging in and being brought to their profile page.
2. Application shuts down.
3. When user opens application again he will be brought to login page or automatically signed in. (At this point no data persistence is expected so user would be forgotten)

## Special Requirements
### Usability
- This signing up hould be an extremly efficient part of the application, do not want to lose potential users because of log in issues.
- Only require simple information like username and login, and allow users to retry their usernames or passwords.

### Performance
- As mentioned above the system should be able to process this exchange quickly. Want users to be able to re-enter application and use it without problems or delays.
### Scalability
- User data should already be stored because user is logging back in so, no realy scalability issues.
### Security
- For now retrys for usernames or passwords will be allowed. At some point should likely consider putting a cap on the amount of tries one can get on getting the password correct for the same username.
- In future should consider finding a way to allow users to change their password, via email most likely or perhaps a set of security questions.
### Technology and Data
- No particular data issues in this case, because no additional data is being stored, just need to make sure accessing data like the username list is efficient.
- Potential technology to include in future is a set of security principle surronding password, password reset, etc.
### Frequency of Occurence
- Whenever a user logs out of their account and returns to use the application. User will log back in, so frequency depends on how often the user logs out. 