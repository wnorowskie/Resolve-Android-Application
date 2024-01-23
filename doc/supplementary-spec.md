# Usability:
- An easy-to-make account by initiating username & password or connecting through Vassar system. 
- Simple and readable design with high contrast and basic colors, so it can be easy to read/interact with.
- Should take users 15-30 min to get familiar with the application, depending on familiarity with technology and social media applications. 
- Notifications regarding scheduling and conflicts, so the user is always up-to-date. 
- Intuitive navigation design will keep students and organizations organized and efficient.

# Reliability/Recoverability:
- If a user can not authenticate with Vassar Systems, give the option to connect through a basic username & password and the option to then re-connect with Vassar at a later time.
- User and profile memory: if a user logs out or deletes the application, the user can always log in again and profile information will be saved.
- If a user fails to remember password, be able to reset it through email.
- In the first iterations, the system will use hash maps to store data, but as the application scales, it will need a back-end server to store/retrieve data. 
# Performance: 
- Users want a quick system and an engaging application. Android Operating System
- Authentication through the Vassar system should be relatively fast (under 20 seconds to get to OneLogin, under 1 min to authenticate with the system). 
- Search tool should allow users to find other users quickly (goal should be in under 30 seconds 90% of the time)
# Operational Concerns:
- Authentication Failure
    - Log errors with users remembering their password. (warn users after 5 passwords, they may need to re-confirm with email)
    - Log authentication failures and where system failure was.
- Privacy
    - Users should be free to share/keep private any portion of their schedule
# Design Constraints
- Android OS
    - All development must be compatible with Android OS
- Data Transfer
    - At the current stage, all data transfer is serverless
- Ask Banner
    - Schedule integration must be compatible with Ask Banner

