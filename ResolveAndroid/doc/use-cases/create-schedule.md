# Creating User Schedule - Fully Dressed

## Scope:
- User Creates Initial Schedule
### Level:
- User Goal

## Primary Actor:
- User
## Stakeholders and Interests:
- User will need to initialize schedule so as to share with friends, add additional events in the future
- Weekly base schedule (Sunday-Saturday) so that students can add fixed events such as classes, work, meetings, practices, etc.
### Student Users:
- This base schedule will allow for further schedule functionality such as: Sharing schedule with friends/groups/orgs,
  sending an event to share with friends/groups, allowing orgs to add to user's schedules, etc.

## Preconditions
- Student has created an account with all proper information neccessary

## Postconditions
- Student's Schedule is added to the Schedule Database
- Student is able to view their current schedule through their profile page
- Friends/Groups/Orgs of the user must be also able to view the users schedule (per user's permissions)

## Main Success Scenario
1. Student creates their user account and initializes all necessary profile/account information
2. Student is prompted to create their basic weekly schedule (not a full calendar type just a weekly basis)
3. System prompts user to add events systematically by each day (Starting with Sunday, then Monday, Tuesday, etc.)
4. When prompted with the day, the user will add an event by entering event info (start time, end time, event name, etc.)
5. The system will take these events and add them to a List that will then be stored in a Map, the Map will have 7 keys for each day.
6. After user goes through each day and adds all events System will add the Map of their schedule to the Schedule Database
7. The Map will also be assigned to the User and their Profile so that the User can view a graphical interface of their schedule

## Extensions
    1a. User does not wish to input a standard schedule
1. The user should not be forced to enter a weekly schedule so system should ask user if it wishes to create the schedule

    1b. User has no events to add on a particular day
1. System should allow user to add no events to a particular day, so it should again ask user if it wants to add an event rather than automatically asking for event info

    2a. User Permissions of the event
1. The user should be able to set specific permissions as to the viewability of the event.
2. User should decide if event should be publically displayed, shared with friends, shared with groups, etc. 
3. Ideally user could also share that there is an event in a specific time slot without showing the event name, description, etc.

    3a. Event Conflicts
1. System should be able to deal with User inputting multiple events that are at the same time or possibly overlap.
2. Need to think of the best way for displaying these conflicts in the UI

    4a. User quits system during the middle of schedule creation
1. When user returns to application it should ideally save the memory, but this is unlikely so the entire process can simply be restarted

    4b. User exits app (app is still open)
1. The events/days that the user has enter should ideally be saved as a sort of draft, so user can return and finish schedule creation without restarting entire process

## Special Requirements
### Usability
- Ideally the process of going through each day and adding event should be seamless, no strict rules as to when events are scheduled, names of events, etc. Should work similarly to an application like google calander, adjusting times via scroll rather than input
- User should be able to adjust graphical settings like color, font, etc. 
### Performance
- Schedule should take relatively short period of time to access after creation, do not want user to have to wait a long time for schedule to load as this simplicity of this application is it's advantage
### Scalability 
- Ideally this simple design could be scaled to a full year calander but at the moment it will simply focus on a weekly schedule.
### Security
- Permissions have been mentioned a few different times, but it essential the users schedule is not publically available if they do not wish it to be. 
### Technology and Data
- System should be able to store each user's schedule and eventually (as describe in other use-cases) have the capability to share schedule and create new events between multiple users.
- Schedule should be maintained and able to be managed at any point.
### Frequency of Occurence
- Every time a new user is created