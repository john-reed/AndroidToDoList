# Requirements Document – Team 22

## 1 Introduction
This product, named TODO On the Go, is an android application that will provide an interactive to do list supporting multiple users.


## 2 User Requirements

### 2.1 Software Interfaces
The system is an Android application and will therefore interact with the Android environment and make use of Android tools and SDK and the available interfaces contained in the Android platform.

### 2.2 User Interfaces
<ol>
<li>The user will interface with the system on an Android device using a touch screen.</li>
<li>The user should be able to access all portions of the system using the touch screen and touch screen keyboard.</li>
<li>The user shall be able to enter in a name to differentiate their list from other users.</li>
<li>The user shall be able to enter in tasks in a to do list and delete tasks from that list from the interface.</li>
<li>The user shall be able to enter an task with a date and priority.</li>
<li>The user shall be able to check tasks on the to do list</li>
<li>The user shall be able to hide or show checked items in the list.</li>
<li>The user shall have the ability to login using a username and password.</li>
<li>The user shall have the ability to add or delete users from the UI</li>
<li>Optional:The user shall be able to create and delete complete lists based on a topic, such as work, home, etc.</li>
</ol>

### 2.3 User Characteristics
The user may be any person who has an Android phone and may have any level of technical expertise or educational background. The only assumption made about the user is that they have the capacity and ability to use an Android phone and access an application on that phone.

### 2.4 Assumptions and Dependencies
The team having to concurrently learn Android programming along with the prototyping process may lead to some delays or requirements not being met.

## 3 System Requirements

### 3.1 Functional Requirements
<ol>
<li>The application shall consist of a list of tasks.</li>
<li>The application shall allow the user to add, delete, and edit tasks in the list.</li>
<li>Each task on the list shall have properties for name, priority, due date and checked state that shall be editable by the user.</li>
<li>The properties of priority and due date shall be selected by using a drop down menu</li>
<li>The date shall have a default value of one day after the entered date.</li>
<li>The priority field of the task shall have a default value of High. </li>
<li>The list for the user shall be saved immediately after editing a field without input from the user.</li>
<li>Tasks in the list shall have a checkbox to signify the checked state representing completed</li>
<li>The items in the list without a check mark will always be displayed</li>
<li>The items in the list with a check mark shall be displayed by the system when the user selects the option to do so.</li>
<li>The system will support multiple users based on an entered name by the user and each user will have their own lists</li>
<li>The system will support user login and list security by having using a simple authentication.</li>
<li>The system shall have the ability to add and delete users</li>
<li>Optional: The system shall allow multiple lists for one user based on user defined topics and the system should allow the user to create and delete these lists</li>
</ol>
### 3.2 Non-Functional Requirements
<li>The User Interface must be intuitive and responsive.</li>
#### 3.2.1 Software Quality Attributes
<ol>
<li>Reliability- The app should not crash and should run as intended for the user and the user names and lists should be saved and not lost when the system is closed and the application is killed.</li>
<li>Efficiency- The application should run smoothly on the system and with out interruption or delays.</li>
<li>Security- The application should have the lists for each user be secure and only viewable by the specific user.</li>
</ol>
