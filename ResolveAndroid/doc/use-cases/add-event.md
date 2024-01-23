# Add an Event
## Scope
- Schedule Functionality
## Level
- User goal
## Primary Actor
- Individual (User)
## Stakeholders and Interests
- User: Wants fast and responsive event adding functionality, likely wants many options in terms of notifying/inviting friends, wants schedules to be accurate for decision making
- Friends of User: Want to be notified appropriately of new events, want schedules to be accurate
## Preconditions
- User and any friends are authenticated and have entered their class schedules
## Postconditions
- Event is created and saved in the schedule database for user and all invited friends.
- Notifications/invitations are sent to involved friends, and any accept/reject notifications are sent back to the original user
## Main Success Scenario
  1. User wants to add an event to a schedule, where the number of schedules the event will be added to depends on what page the user initiates the action
  2. User goes to the schedule that they wish to add the event to, and the app displays gaps where everyone whose schedule is displayed is free
  3. User taps on a gap and a popup appears where default event options such as “dinner” and “study,” along with an area to type your own name for an event
  4. User can click on “add event” after they choose between various settings (or leave as default) depending on what schedule they are on
  5. Anyone set to be notified gets a notification
## Extensions
  1a. If the user initiates the action on their own page:
1. The event can be added to the schedules of all of their friends (if they choose to, discussed in 4a)

1b. If the user initiates the action on a friend’s page:
1. The event will be added to only that friend and the user’s schedules

1c. If the user initiates the action on a group’s page (not a club/org, that will be addressed in a separate use case):
1. The event will be added to the schedules of everyone in that group

2a. If on a group’s page:
1. User can click checkboxes corresponding to other users’ profiles to toggle their schedule being displayed on whichever page the user is on -- if a profile is checked off, that user will not be notified of the event automatically

3a. If time user taps on is not free:
1. User will get a message saying the time is not free, and they will be told to choose a new time or update their schedule
2. If on a group’s page, message will also mention that individuals can be checked off (from part 2a) in case that is causing the time to not display as free

4a. If on their own page:
1. User can choose whether or not the event is visible to friends 
2. If so, user can choose whether or not friends can join
3. If both previous options are chosen as yes, user can choose to notify friends of the event

4b. If on a friend’s page:
1. User can only choose whether or not to notify the friend

4c. If on a group’s page:
1. User can only choose whether or not to notify members of the group
2. If any individual profiles in the group were checked off in part 2a, those individuals would have to be manually added when selecting to notify group members if the user wants them to receive a notification

4d. For all pages that the user could be on (individual, friend, or group for the purposes of this use case):
1. Default settings for notifications, etc. can be decided by the user in a user preferences menu

5a. For notifications:
1. Users can have it set so that their phone displays a banner notification that there is a new event, otherwise it just goes in a notifications sections

5b. If others were invited:
1. The original user who invited them will receive a notification whenever someone accepts

*a. System crashes:
1. If the crash happens before the event is added, data for the event in progress is not saved (can be easily remade)
2. If it happens after the event is added, the data should be saved in the schedule already

*b. User goes out of the app (app is still open):
1. Data for the event in progress should be saved as some kind of draft
## Special Requirements
- Default colors should not be those commonly confused by individuals with colorblindness.
- Colors should be able to be set by the user in user preferences.
## Technology and data variations list
- Schedules saved to persistent storage.

