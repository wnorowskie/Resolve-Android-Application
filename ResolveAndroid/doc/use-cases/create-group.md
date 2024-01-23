# Creating a Group - Fully Dressed
## Scope
- Informal coordination between individuals

## Level
- User goal

## Primary Actor
- Individual (User)

## Stakeholders and Interests
###Individuals:
Wants to create a group with other followers/members. Wants to coordinate meet-ups and events with group members. Wants to view members' schedules. Wants to message other group members. 


## Preconditions
- User who creates group has successfully logged in beforehand
- User is following all group members 

## Postconditions
- All group members have been notified that they have been added to a new group
- All group members can message, add group events, and view schedules of other members

## Main Success Scenario
1. User navigates to profile
2. User clicks on “create group”
3. User can add group name
4. User adds desired group members 
5. System notifies group members that they were added to a group

## Extensions
5a. User attempts to add someone that they're not following:
1. System displays error message
2. System prevents user from adding them to the group

## Special Requirements
### Usability
- Making a group should be an intuitive, step-by-step process

### Security
- Group is private (cannot join without an invitation and group contents are only visible to group members)
- Group will not appear in search results from non-members

### Scalability 
- Groups should be able to handle a large amount of members 

## Technology and data variations
- Notifications through Android OS push notifications

