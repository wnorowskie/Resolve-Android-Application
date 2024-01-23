# Basic Test Report

### Implementation

---
Our prototype fully implements the following features:

- Creating an account (username and password)
- Customizing your profile with a name and bio
- Creating a group
- Creating an organization
- Customizing an organization profile with a name and bio
- Adding/removing a friend

### Test of Basic Features

---
All features are working and succesful.

This quick test includes a new user signing up for the application, displaying the user profile, creating a new group, displaying the users group memberships, creating a new organization, displaying the users org memberships.

---

The first screenshot includes:
 - The applications introduction
 - Asks user if they would like to login or signup
 - User enters "SignUp"
 - System prompts user to enter desired username
 - User enters username ("ericwnorowski" in this case)
 - System prompts user to enter desired password
 - User enters password ("samplepassword" in this case)
 - System prompts user to enter full name
 - User enters full name ("Eric Wnorowski" in this case)
 - System prompts user to enter bio of maximum 150 characters
 - User enters bio "Vassar '23. Computer Science Major. Vassar Men's Soccer"
 - System alerts user that the signup was successful
![](/doc/Test-Report-Photos/SignUp.png)

---
the second screenshot includes:
 - System prompts user to enter a command type
 - User enters "Display"
 - System displays the possible command types and prompts user to enter one
 - User enters "profile"
 - System display the user's name and bio
![](/doc/Test-Report-Photos/Display-Profile.png)

---
the third screenshot includes:
- System prompts user to enter a command type
 - User enters "Create"
 - System displays the possible command types and prompts user to enter one
 - User enters "group"
 - System prompts user to enter group name
 - User enters group name ("Team-1C CMPU-203" in this case)
 - System alerts user that the creation was succesful
 ![](/doc/Test-Report-Photos/Create-Group.png)

 ---
 the fourth screenshot includes:
 - System prompts user to enter a command type
 - User enters "Display"
 - System displays the possible command types and prompts user to enter one
 - User enters "groups"
 - System display the user's group memberships and the members of the group
 ![](/doc/Test-Report-Photos/Display-Group.png)

 ---
 the fifth screenshot includes:
- System prompts user to enter a command type
 - User enters "Create"
 - System displays the possible command types and prompts user to enter one
 - User enters "org"
 - System prompts user to enter org name
 - User enters group name ("Vassar Men's Soccer" in this case)
 - System alerts user that the creation was succesful
 ![](/doc/Test-Report-Photos/Create-Org.png)

 ---
 the sixth screenshot includes:
 - System prompts user to enter a command type
 - User enters "Display"
 - System displays the possible command types and prompts user to enter one
 - User enters "orgs"
 - System display the user's org memberships and the org bio (generic for now, further implementation required)
 ![](/doc/Test-Report-Photos/Display-Org.png)
 
