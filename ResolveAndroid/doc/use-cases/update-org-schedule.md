# Update Organization Schedule - Fully Dressed
## Scope
- Organization Event Planning
## Level
- User goal
## Primary Actor
- Organization leader
## Stakeholders and Interest
### Organizations
- Efficiently planning events and quickly updating an organization's schedule to reflect important changes is central to an organization's productivity and growth. Our scheduling app will allow organization leaders and members to effectively communicate about scheduling, and coupling the organization schedule to the planning conversation will lead to an increase in fully attended events, and less room for organization members to miss an event because they missed an email or a scheduling change on a different application. This efficiency will strengthen leadership skills and make an organization more attractive to new members, which is incredibly important for new organizations, or large organizations that are becomming large enough that their method of communication is no longer viable.  
### Students
- Students who are members of an organization using our application will be given the benefit of having every organization they are a part of in the same location, and they can easily switch between them to communicate with all of them, instead of switching between multiple applications. This should significantly reduce stress for students who are heavily involved on campus. An update to any of the organizations a student has joined will notify them of the change, and because we are combining the messaging between students and orgs. with the act of scheduling itself, the student would most likely have been aware of this change, and had been a part of planning it, before they were notified. Our application should increase organization and structure for students who are already very busy day-to-day.

## Preconditions
- Organization leader must have created personal account
- Organization leader must create an org. to become to leader
## Postconditions
- Update must be reflected on the organization's schedule UI, and be visible to all members
- Update must be added to organization's feed, allowing members to add to their personal schedules
## Main Success Scenario
1. Org. leader adds/removes/reschedules event on org. schedule
2. The system detects no conflicts
3. Org. leader saves new schedule
3. System updates org. schedule
4. System notifies org. members of update
5. System adds update to org. feed
## Extensions
2a. System detects conflict between previous org. event and new update
 - System prevents org. leader from saving schedule with conflict
 - Org. leader resolves conflicting events
 - Org. leader saves updated schedule

4a. System detects conflicts with update and members' schedules
 - Org. members are notified of conflicts individually
 - System adds update to org. feed, notes some members have conflicts.
## Special Requirements
### Usability
- Schedule update process must be intuitive, can't be clunky
- Org. members passively notified of updates/conflicts
### Performance
- System must be able to quickly detect scheduling conflicts
### Security
- System should maintain organization members' privacy and keep conflicts anonymous
- Only events with high visibility should be sent to org. leader when viewing conflicts
### Scalability
- System should be scalable to large organizations
### Technology and Data Variations
- Schedules saved to persistent storage on each org. member's device
- Push notifications through Android OS
- To detect conflicts, system must be able to send schedule information between users
### Frequency of Occurance
- High; Core feature

