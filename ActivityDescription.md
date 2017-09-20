# Todo-Together
SOFTWARE DESIGN PATTERN PROJECT

>> No details for xml file is provided. Because in android studio there is an option that directly takes to xml file for corresponding java class. Go there and check it out.

Main Activities:

1. LogInPage: this start at very beginning of the app. And used for authentication. See firebase documentation for authentication for further details in this page.

2. InterfaceActivity: this is interface and abstract class. All ohter activity extends it as per requirement.

3. HomePage: this is the heart of the app. Contains some tab, and each one contains some catagorized list of tasks. this thing is done using several java and xml files. First of all java class PostViewHolder is added that can hold task fragments as a list. Now, We need the fragments. Here, PostFragmentInterface implements all the things that are required to do for holding some fragments. and then MypostsFragment, MyTopPosts Fragmentt, RecentPostFragments each implements that class and holds corresponding catagorywise tasks. 

4. NavigationPage: a button from homepage leads to navigationpage. Create a new project and add a navigation drawer acitivity in it to see how it works and how things are decorated. 

5. CreateNewPostPage: a button from homepage goes here. this page adds a tasks. takes input from user and then just adds it to database.

6. TaskDetailsPage: this one shows details of a task. and also holds comments below. But no extra class is added. this comment section is handled in the class itself, and required more classes are added privately here.

7. CreateNewGroupPage: Same as CreateNewPostPage.

8. GroupDetailsPage: Same as TaskDetailsPage.

9. GroupListPage: Same as HomePage with less buttons and options. Instead of tasks, groups are listed.


MODELS:
Comment, Group, Task, User each model is a class that is used to create corresponding objects. 

