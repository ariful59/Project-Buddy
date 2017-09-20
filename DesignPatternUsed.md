# Todo-Together
SOFTWARE DESIGN PATTERN PROJECT


Design Pattern:

1. Observer pattern: When an user posts something, all other users get that message instantly. This is done using observer pattern.The database acts as the subject, and users are observer. whenever a new data is added or modified, all the observers get the update by observing the database. 

2. Decorator Pattern: Here, Both in HomePage, where all the tasks are listed, and also in grouplist page, we used decorator pattern. All  the fragments are loaded in listview layout. and that layout is wrapped with tabview layout. so that finally we get both of it in display. So that user can now slide to change to different tabs, and each tab contains a list of different tasks. without decorator pattern, it wouldn't be possible and even if possible, it would be extremely inefficient and hard to code.

3. Simple Factory Pattern: Most of the activity contains some commmon information and methods. instead of writing them everytime manually, we added factory pattern. So that, all basic methods are in interfaceactivity. and all other acitivity similar to it implements this method. So that, all the methods contains some 'loading' option, toasting options, which are used easily if necessary.

4. Abstract Factory pattern: In listview of tasks, that list can contains all the tasks, or just tasks of specific user, or top rated tasks etc.in grouplistview, things are pretty much same work while loading the fragments, just change the fragments to groups instead of tasks. Thisthings was done using abstract factory pattern. at first we made interface for a fragment holder that can take fragments. then created abstract class, one for tasks another for groups. later for each catagory of tasks or groups we implemented it the way we need. 

5. Template Pattern: In login acitivity, there can be two options, login or signup. If we look closely both are quite same. first, user inputs email and password. second, the email password is checked if inputed correctly. third, those data are sent to database for authentication. fourth, if success, user is directed to homepage, else failure, user stays here and tries again. so, all things are quite same except for third stage, when already signed up user logins to his previous account and new users logins to new account. So, keeping the algorithm same, we applied template pattern here.

6. Composite Pattern: Each user is member of some group and that group contains some tasks. So, we added reference of groups as a list in user. so that, whenever an user needs to add some tasks to a group, or modifiy something, or needs the list of other users of that group he can do it from his grouplist.

