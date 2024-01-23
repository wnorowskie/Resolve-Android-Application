```plantuml
@startuml
hide circle
hide empty methods
title Domain Model

' classes
class User{
    username
    password
    profile
    friends list
}
class Organization{
organization leader
members list
admin list
organization name
profile
}

class Profile{
    name
    bio
    photo
    type
}

class Group{
    members list
    name
}

class Database{
    group list
    user list
    organization list
}
class userCollections{
collection name
collection memberlist
UUID
}
class Event{
start time
end time
title
notes
display color
}
class Schedule{
    Weekly Schedule
    Daily Schedule

}

' associations
User "1" - "1" Profile : \tDescribed-by\t\t
User "1" - "1" Schedule : \tCreates\t\t
Organization "1" - "1" Profile : \tDescribed-by\t\t
Organization "1..*" -down- "1..*" User : \tContains\t\t
Organization "*" -down- "1..*" User : \tIs-administered-by\t\t 
Organization "1" - "1" Schedule : \tCreates\t\t
Organization "1" - "1" userCollections: \tSends data to\t\t
Group "1" -right- "1..*" User : \tContains\t\t
Group "1" - "1" userCollections: \tSends data to\t\t
Database "1" -left- "1..*" Group : \tIs-contained-by\t\t
Database "1" -down- "1..*" User : \tContains\t\t
Database "1" -down- "1..*" Organization : \tContains\t\t
Schedule "1" - "1..*" Event : \tContains\t\t
@enduml
```

```plantuml
@startuml
title Searching for User
hide footbox 

actor User as user
participant ": TextUI" as textUI
participant ": Controller" as controller
participant ": Database" as database

user -> textUI: search(String username)
textUI -> controller: searchUsers(username)
controller -> database: result = getUser(List<User>)
database -> controller: return List<User>
controller -> user: print(profile.List<User>)
@enduml 
```
```plantuml
@startuml
title Send Friend Request
hide footbox

actor User1 as user1
participant ": TextUI" as textUI
participant ": Controller" as controller
actor User2 as user2

user1 -> textUI: searchUser(String "user2")
textUI -> controller : User = getUser(user2)
controller -> user2 : addRequestToQueue(user2)
controller -> textUI : requestSent(user2)
textUI -> user1: successfulRequest(String "request sent")

@enduml
```
```plantuml
@startuml

title Accepting Friend Request

hide footbox

actor user1 as user1
participant ": TextUI" as textUI
participant ": Controller" as controller
actor user2 as user2

user1 -> textUI: request = searchUser(String "user2")
textUI -> controller: User = getUser(user2)
controller -> user2: request  = sendRequest(user2)
user2 -> controller: acceptRequest(user1)
controller -> user1: addFriend(user1, user2)
controller -> user2: addFriend(user2, user1)

@enduml 
```
```plantuml
@startuml

title Denying Friend Request

hide footbox

actor user1 as user1
participant "TextUI" as textUI
participant ": Controller" as controller
actor user2 as user2

user1 -> textUI: request = searchUser(String "user2")
textUI -> controller: User = getUser(user2) 
controller -> user2: request = sendRequest(user2)
user2 -> controller: denyRequest(user1)
controller -> user1: requestDenied(String "Request Denied")


@enduml 
```


```plantuml
@startuml
hide footbox

title Create User

actor User as user
participant ": Controller" as controller
participant "user: User" as userclass
participant "userProfile: Profile" as prof
participant ": Database" as data

user -> controller : newUser()
controller -> user : info = getUserCredentials(user)
controller -->> userclass **: user = create(info)
controller -> user : info = getProfileInfo(userProfile)
controller -->> prof **: userProfile = create(info)
prof -> controller : getProfile()
controller -> data : store(user)
@enduml
```
```plantuml
@startuml
hide footbox
title Create Group
actor User as user
participant ": Controller" as controller
participant "group: Group" as group
participant ": Database" as database

user -> controller : newGroup()
controller -->> group **: group = create()
controller -> user: getMembers(group)
controller -> group: addMembers(List<User>)
controller -> database: store(group)
@enduml
```
```plantuml
@startuml
hide footbox
title Create Organization
actor User as user
participant ": Controller" as controller
participant "org: Organization" as org
participant "orgProfile: Profile" as prof
participant ": Database" as data

user -> controller : newOrg()
controller -->> org **: org = create(user)
controller -->> prof **: orgProfile = create()
controller -> user : getOrgInfo(org)
prof -> controller : getProfile()
controller -> data : store(org)
@enduml
```
```plantuml
skinparam classAttributeIconSize 0

class User{
-username : String
-password : String
-friendsList : List<User>
-orgList : List<UUID>
-groupList : List<UUID>
-numFriends : int
-incPendingRequests : List<User>
-outPendingRequests : List<User>
-profile: Profile
--
User(String username, String password) : User
+getUsername() : String
+getPassword() : String
+setProfile(Profile prof) : void
+getProfile() : void
+getIncPendingRequests() : List<User>
+getOutPendingRequests() : List<User>
+getFriendsList() : List<User>
+addPendingRequest(User user) : boolean
+removePendingRequest(User user) : boolean
+denyRequest(User user) : boolean
+acceptRequest(User user) : boolean
+addFriend(User user) : boolean
+removeFriend(User user) : boolean
+addOrg(Org org) : boolean
+leaveOrg(Org org) : boolean
+addGroup(Group group) : boolean
+leaveGroup(Group, group) : boolean
+getOrgList() : List<UUID>
+getGroupList() : List<UUID>
+getNumFriends() : boolean
+toString() : String
}

class Group{
--
+Group(String groupName, List<User> memberList) : Group
+ toString() : String
}

class Org{
-leader : User
-profile : Profile
-adminList : List<User>
--
Org(String name, User leader)
+ getProfile() : Profile
+ setProfile(Profile orgProfile) : Profile
+ getadminList() : List<User>
+ addAdmin(User user) : boolean
+ removeAdmin(User user) : boolean
+ isAdmin(User user) : boolean
+ isLeader(User user) : boolean
+ getLeader(User user) : User
+ toString() : String
}

class Profile{
-name : String
-bio : String
- maxBioChars : static final int 
--
Profile(String name)
+getName() : String
+getBio() : String
+setBio() : boolean
+toString() : boolean
+getMaxBioChars() : int
+toString() : String
}
class UserCollections{
-name : String
-numMembers : int
-memberList : ArrayList<User>
-uuid : UUID
--
+UserCollection()
+getName() : String
+getUUID() : UUID
+getMembers() : ArrayList<User>
+isMember() : boolean
+addMember() : boolean
+removeMember() : boolean
+toString() : String
}

class Event{
-title : String
-eventColor : String
-startTime : EventTime
-endTime : EventTime
-notes : List<String>
--
+Event()
+conflictsWith() : boolean
+setStartTime() : void
+setEndTime() : void
+setTitle() : void
+setNotes() : void
+getTitle() : String
+getStartTime() : EventTime
+getEndTime() : EventTime
+addNote() : void
+removeNote() : void
+getNotes() : List<String>
}
class EventTime{
-hour : int
-min : int
-format : String
--
+EventTime()
+getHour() : int
+getMinute() : int
+setHour() : void
+setMinute() : void
+setFormat() : void
+getTimeMins() : int
+getFormat() : String
}
class Weekday{
#id : int
#name : String
--
+Weekday()
+getName() : String
+getID() : int
}
class WeeklySchedule{
-days : DailySchedule[]
--
+WeeklySchedule()
+getDailySchedule() : DailySchedule
+addEvent() : void
+removeEvent() : boolean
+rescheduleEvent() : List<Integer>
}

class Database{
-userRepo : Map<String, User>
-groupRepo : Map<UUID, Group>
-orgRepo : Map<UUID, Org>
--
+getUserRepo() : Map<String, User>
+getGroupRepo() : Map<UUID, Group>
+getOrgRepo() : Map<UUID, Org>
+addUser(User user) : void
+addOrg(Org org) : void
+addGroup(Group group) : void
}

class Controller{
+createUser(String username, String password, String name, String bio) : User
+validateCreds(String username, String password) : User
+userExists(String username) : boolean
+orgExists(UUID) : boolean
+groupsExists(UUID) : boolean
+getOrg(UUID) : Org
+getUser(String username) : User
+startGroup(User user) : void
+startOrg(String name, User user) : boolean
+searchOrg(String orgName) : Org
}


User -> "1\nprofile" Profile
Org --> "1\nprofile"Profile
Database  -> "*\nuserList\n{HashMap}"User
Database  ---> "*\ngroupList\n{HashMap}"Group
Database  --> "*\norgList\n{HashMap}"Org
Controller -->"1\ncontrols" Database
UserCollections <|-- Group
UserCollections <|-- Org
User --> WeeklySchedule
WeeklySchedule --> Weekday
Event --> EventTime
WeeklySchedule --> Event
```