# Creating an Organization - Fully Dressed

## Scope: 
- Schedule App Function
## Level: 
- User goal
## Primary Actor:
- Organization Leader
## Stakeholders and Interests:
### College Organization Leader: 
Wants to create a club page for student members to join. Wants to post organization events on their organization feed for members to see. Wants to easily create events that work with other members’ schedules in order to maximize the number of people who can come to an event. Wants to send notifications to members when a new event is created. Wants to message organization members. Master schedule allows organization leader to create events that all members can view and compare to their own schedules.


### College Organization Co-Leader: 
Wants to post organization events. Wants to easily create events that work with other members’ schedules in order to maximize the number of people who can come to an event. Wants to send notifications to members when a new event is created. Wants to message organization members.


### College Organization Member: 
Wants to be able to join an organization page for an organization on their campus. Wants to easily view organization events and be notified when new events are created. Wants to RSVP to event invitations.
## Preconditions: 
- Organization leader has created a personal account.
## Postconditions: 
- Organization page is created and saved in the system. 
- Users can search for organization. 
- Users can request to join the organization. 
- Users can view organization profile. 

## Main Success Scenario
1. User navigates to profile
2. User clicks on “create organization”
3. System prompts user to create organization profile
4. Organization profile automatically set as public
5. User automatically granted organization leader permissions
6. User customizes organization account with club name, profile photo, bio

## Extensions
2a. User clicks on "create organization" and nothing pops up:
1. User refreshes/closes out application
2. User reopens application

*a. At any time, application crashes:

1. User refreshes/closes out application
2. User reopens application

## Special Requirements

### Usability
- Easy-to-follow, step-by-step organization page process
### Performance
- System will make organization page visible to others quickly after creation
### Security
- User must successfully log in to their account before creating an organization page
- Only the user who created the organization page has org. leader permissions (e.g. making other org. members co-leaders)
### Scalability
- System should be scalable to large organizations
