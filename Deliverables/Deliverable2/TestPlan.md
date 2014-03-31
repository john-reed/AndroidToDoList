# Test Plan Document – Team 22
## 1 Introduction
This product, named TODO On the Go, is an android application that will provide an interactive to do list supporting multiple users.

## 2 Quality Control

### 2.1 Test Plan Quality
We will use Unit Testing, System Testing and User Acceptance Testing to ensure that our final product meets all the requirements of our client. Unit Tests will be developed and written to correspond to all non-UI functionalities of our product. Development of these tests will be done concurrently with the main development tasks, and will be added as new functionalities become available to ensure that changes during iteration do not affect the functional quality of our product. System Tests will be developed at the beginning of the project lifecycle to ensure that the final product will meet all of our defined requirements. These tests may also mature as we move through the iterative development cycle. Finally, after development has finished, we will use a modified version of User Acceptance Testing to ensure that the product performs as anticipated and meets all requirements. We are modifying this pattern to be performed by all the members of the team, as we do not have direct access to the client.

### 2.2 Adequacy Criterion
Testing will be complete and considered successful when the following conditions are met:
* All Unit Tests pass
* All System Tests pass
* User Acceptance Testing is complete and passes (specifically, all team members have tested the application and have signed off on its submission)

### 2.3 Bug Tracking
We will track all bugs and enhancement requests through the GitHub Issue Tracker. Enhancement requests will be handled by the Requirements Analyst and bug reports will be handled by the Tester.

## 3 Test Strategy

### 3.1 Testing Process
Unit Tests will be developed in parallel with the main application's development in order to ensure that back end functionalities added to support the main requirements do not regress during iteration. These unit tests will be run regularly to ensure that product quality is maintained through the development process.

System Tests will be designed at the beginning of the process to ensure that the final product will meet all requirements. These tests will be performed at each release point (Alpha, Beta, Final) to ensure that all requirements have been met. Alpha and Beta releases are not required to pass all tests, but the Final release must pass before submission.

User Acceptance Testing will be performed prior to final submission to ensure that all requirements have been met. 

The Tester will design all Unit and System Tests, and will implement all Unit Tests. The Tester will ensure that Unit Tests will continue to pass, but all persons involved with the development process should ensure that unit tests are passing before pushing code to the repository. The Tester will be responsible for performing System Tests. All members of the group will perform User Acceptance Testing.

### 3.2 Technology
We will use the JUnit framework for our Unit Tests.

## 4 Test Cases

### 4.1 Unit Test Cases
**Note:** These test cases are preliminary and will be updated during the development process to ensure that iteration does not cause regressions in code quality.

<table>
	<thead>
		<tr>
			<th><b>Test Name</b></th>
			<th><b>Purpose</b></th>
			<th><b>Steps</b></th>
			<th><b>Expected Result</b></th>
			<th><b>Actual Result</b></th>
			<th><b>Pass/Fail</b></th>
			<th><b>Comments</b></th>
		</tr>
	</thead>
	<tbody>
	  <tr>
	    <td>Add User</td>
	    <td>Add a user to the database.</td>
	    <td>Execute the AddUser unit test.</td>
	    <td>User Count increases by 1.</td>
	    <td></td>
	    <td></td>
	    <td></td>
	  </tr>
	  <tr>
	    <td>Delete User</td>
	    <td>Remove a user from the database.</td>
	    <td>Execute the DeleteUser unit test.</td>
	    <td>User Count decreases by 1.</td>
	    <td></td>
	    <td></td>
	    <td></td>
	  </tr>
	  <tr>
	    <td>Add List</td>
	    <td>Add a todo list to the database.</td>
	    <td>Execute the AddList unit test.</td>
	    <td>List Count increases by 1.</td>
	    <td></td>
	    <td></td>
	    <td></td>
	  </tr>
	  <tr>
	    <td>Delete List</td>
	    <td>Delete a todo list from the database.</td>
	    <td>Execute the DeleteList unit test.</td>
	    <td>List Count decreases by 1.</td>
	    <td></td>
	    <td></td>
	    <td></td>
	  </tr>
	  <tr>
	    <td>Add Task</td>
	    <td>Add a task to the database.</td>
	    <td>Execute the AddTask unit test.</td>
	    <td>Task Count increases by 1.</td>
	    <td></td>
	    <td></td>
	    <td></td>
	  </tr>
	  <tr>
	    <td>Edit Task</td>
	    <td>Edit the values of a task the database.</td>
	    <td>Execute the EditTask unit test.</td>
	    <td>Task values are accordingly edited.</td>
	    <td></td>
	    <td></td>
	    <td></td>
	  </tr>
	  <tr>
	    <td>Delete Task</td>
	    <td>Delete a task from the database.</td>
	    <td>Execute the DeleteTask unit test.</td>
	    <td>Task Count decreases by 1.</td>
	    <td></td>
	    <td></td>
	    <td></td>
	  </tr>
	  <tr>
	    <td>Mark Task Complete</td>
	    <td>Marks a task as complete.</td>
	    <td>Execute the MarkTaskComplete unit test.</td>
	    <td>Task is marked complete.</td>
	    <td></td>
	    <td></td>
	    <td></td>
	  </tr>
	  <tr>
	    <td>Mark Task Incomplete</td>
	    <td>Marks a task as incomplete.</td>
	    <td>Execute the MarkTaskIncomplete unit test.</td>
	    <td>Task is marked incomplete.</td>
	    <td></td>
	    <td></td>
	    <td></td>
	  </tr>
	</tbody>
</table>

### 4.2 System Test Cases

<table>
	<thead>
		<tr>
			<th><b>Test Name</b></th>
			<th><b>Purpose</b></th>
			<th><b>Steps</b></th>
			<th><b>Expected Result</b></th>
			<th><b>Actual Result</b></th>
			<th><b>Pass/Fail</b></th>
			<th><b>Comments</b></th>
		</tr>
	</thead>
	<tbody>
		<tr>
			<td>Add User</td>
			<td>A user can be added to the application.</td>
			<td>Press the add user button, enter a username and password, press Add.</td>
			<td>User is added and appears in the user list. User can sign in to application.</td>
			<td></td>
			<td></td>
			<td></td>
		</tr>
		<tr>
			<td>Delete User</td>
			<td>A user can be deleted from the application.</td>
			<td>Press the delete user button, choose a user from the list, enter the user's password and press confirm.</td>
			<td>User is deleted and no longer appears in the list. User can no longer sign in.</td>
			<td></td>
			<td></td>
			<td></td>
		</tr>
		<tr>
			<td>Sign In</td>
			<td>A user can sign in to the application. Precondition: the user has been added.</td>
			<td>Select the user from the list. Enter the user's password and press Sign In.</td>
			<td>User is signed in and sees his todo lists.</td>
			<td></td>
			<td></td>
			<td></td>
		</tr>
		<tr>
			<td>Sign Out</td>
			<td>A user can sign out of the application. Precondition: the user is signed in.</td>
			<td>Press the Menu button. Press sign out.</td>
			<td>User is signed out and the list of users is displayed.</td>
			<td></td>
			<td></td>
			<td></td>
		</tr>
		<tr>
			<td>Add List</td>
			<td>A user can add a list to the application. Precondition: the user is signed in.</td>
			<td>Press the add list button, enter a name for the list, press Add.</td>
			<td>List is added and appears in the list of todo lists. User can open the list.</td>
			<td></td>
			<td></td>
			<td></td>
		</tr>
		<tr>
			<td>Delete List</td>
			<td>A user can delete a list from the application. Precondition: the user is signed in.</td>
			<td>Press the delete list button, select a list from the list of lists, press Confirm.</td>
			<td>List is deleted and no longer appears in the list of todo lists. User can no longer open the list. Tasks previously within the lists are also deleted.</td>
			<td></td>
			<td></td>
			<td></td>
		</tr>
		<tr>
			<td>Open List</td>
			<td>A user can open a list in the application. Precondition: the user is signed in and a list has been added.</td>
			<td>Select a list from the list of todo lists.</td>
			<td>List of all tasks in the todo list is displayed.</td>
			<td></td>
			<td></td>
			<td></td>
		</tr>
		<tr>
			<td>Add then Save Task</td>
			<td>A user can add a task to the application. Precondition: the user is signed in and has opened a list.</td>
			<td>Press the add list button. Enter a task name, due date, priority and (optionally) details. Press the save button.</td>
			<td>Task is added and is visible in the todo list.</td>
			<td></td>
			<td></td>
			<td></td>
		</tr>
		<tr>
			<td>Add then Cancel Task</td>
			<td>A user can cancel the process of adding a task. Precondition: the user is signed in and has opened a list.</td>
			<td>Press the add list button. Optionally enter a task name, due date, priority and details. Press the cancel button. Press confirm.</td>
			<td>Task is not added and is not visible in the todo list.</td>
			<td></td>
			<td></td>
			<td></td>
		</tr>
		<tr>
			<td>View a Task</td>
			<td>A user can view a task in a list. Precondition: the user is signed in and has opened a list, and that list has at least one task.</td>
			<td>Choose a task from the list.</td>
			<td>Task is information is displayed in detail.</td>
			<td></td>
			<td></td>
			<td></td>
		</tr>
		<tr>
			<td>Edit a Task</td>
			<td>A user can edit a task in a list. Precondition: the user is signed in and has opened a task.</td>
			<td>Press the edit task button. Change one or more properties of the task. Press confirm.</td>
			<td>Task is edited and new values are displayed to the user.</td>
			<td></td>
			<td></td>
			<td></td>
		</tr>
		<tr>
			<td>Delete a Task</td>
			<td>A user can delete a task from a list. Precondition: the user is signed in and has opened a list, and that list has at least one task.</td>
			<td>Press the delete task button. Choose a task from the list. Press confirm.</td>
			<td>Task is deleted and is no longer visible in the todo list.</td>
			<td></td>
			<td></td>
			<td></td>
		</tr>
		<tr>
			<td>Mark a Task as Complete</td>
			<td>A user can mark a task as complete. Precondition: the user is signed in and has opened a list, and that list has at least one incomplete task.</td>
			<td>Press the empty checkbox next to an incomplete task.</td>
			<td>The task is marked as complete, as indicated by a checked checkbox.</td>
			<td></td>
			<td></td>
			<td></td>
		</tr>
		<tr>
			<td>Filter out Completed Tasks</td>
			<td>A user can choose to view all tasks or only incomplete tasks. Precondition: the user is signed in and has opened a list, and that list has both complete and incomplete tasks.</td>
			<td>Press the menu button. Press Show Incomplete Tasks or Show All Tasks.</td>
			<td>If Show Incomplete Tasks is selected, the application only displays incomplete tasks. Else, show all tasks.</td>
			<td></td>
			<td></td>
			<td></td>
		</tr>
	</tbody>
</table>
