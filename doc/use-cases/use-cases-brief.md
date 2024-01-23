# Brief Use Cases


## Individual Users
- Create Account
    1. User downloads the application
    2. User is asked to select a username and password
    3. User is authenticated, gets a welcome screen
    4. User inputs their classes into a schedule page
    5. User can choose to receive notifications before their classes

- Add Friend
    1. After user inputs their classes into their weekly schedule, they want to add someone who has done the same as a friend
    2. User finds this individual using a profile search feature (call “search for profile” use case)
    3. User sends a friend request to this individual
    4. Individual gets a notification, can choose to accept or reject request
    5. Individual accepts, user and individual are now friends
    6. User can see individual’s class schedule and vice versa

- Add Event
    1. User wants to add an event of some specified type (default is dinner)
    2. User goes to their own schedule and chooses to overlay their friends (>1 friend) schedules on top of their own
    3. App automatically shows gaps in the schedules where user and all friends are free
    4. User taps on one of these gaps and a popup appears where user taps on one of the default event titles
    5. User selects to notify/invite friends to this event
    6. An event is created in all schedules at that time
    7. Friends receive a notification, can choose to accept or reject the invitation
    8. Friends accept, and notifications with that information are sent to user

- Remove Event
    1. User realizes that they can no longer attend an event that they had previously accepted an invitation for
    2. User taps on that event, a popup appears that shows that they have accepted the invitation to this event
    3. User clicks an option to say that they will no longer be going to the event
    4. Event is removed from user’s schedule
    5. The person who created the event is notified that user will no longer be attending


## Groups/Organizations

-  Update Organization Schedule
    1. Org. leader navigates to: My Orgs -> \[org-name\] -> Edit Schedule
    2. Org. leader adds/modifies/removes an event
    3. System detects conflicts with org. members
    4. System notifies each org. member of change, conflicts
    5. System updates org. feed with schedule change
    6. Org. leader returns to home page



- Group Member Creates Group Event
    1. Group member navigates to: Groups -> \[group-name\] -> Add Group Event
    2. Group member enters event details
    3. System notifies group members of new group event
    4. System adds group event to group feed

- Creating a Group 
    1. User navigates to profile 
    2. User clicks on “create group”
    3. User can add group name 
    4. User adds desired group members 
    5. System notifies group members that they were added to a group

- Group Member Adds Event to Personal Schedule
    1. Group member navigates to group feed
    2. Group member views new group event
    3. Group member selects "Add to Calendar"
    4. System adds event to group member's calendar
    5. System saves updated schedule

- Group Member Creates Group Event
    1. System detects new group event
    2. System notifies group members
    3. System adds event to group feed


- Creating an Organization
    1. User navigates to profile
    2. User clicks on “create organization”
    3. System prompts user to create organization profile
    4. Organization profile automatically set as public
    5. User automatically granted organization leader permissions
    6. User customizes organization profile with organization name, profile photo, bio


- Inviting a Member to Group
    1. User navigates to specific group
    2. User clicks “invite” button
    3. User enter the username of user they want to invite
    4. User clicks on username
    5. User confirms that they want to invite selected user
    6. System sends a notification to invited user
    7. Invited user accepts or rejects invitation

## Subfunctions 
[comment]: # (internal/external conflicts should go in glossary)

- Handle Internal Conflict
    1. User adds/removes/reschedules event
    2. System detects other events conflict with change
    3. System prevents user from saving schedule with conflicts
    4. User resolves conflicts
    5. User saves schedule
    6. System updates/saves user's schedule

- Handle External Conflict
    1. User adds/removes/reschedules group/org. event 
    2. System detects conflicts with group/org. members' schedules
    3. System notifies members with conflicts.
    4. User saves schedule
    5. System updates/saves new schedule

- Search for Profile
    1. User wants to search for a profile (can be individual user or club/organization)
    2. User goes to search section of the app through the interface
    3. User is prompted to type into a search bar
    4. User clicks enter and the profiles that match their search are displayed and can be clicked on to see information set to visible