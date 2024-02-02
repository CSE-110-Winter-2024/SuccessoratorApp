
# <a name="_xwb8um4xqj9w"></a>Risk Analysis:

[ ](https://www.when2meet.com/?23477466-2DA4m)[https://www.when2meet.com/?23477466-2DA4m**](https://www.when2meet.com/?23477466-2DA4m)

**Risk:** Team Risk [Conflict of Schedule]

**Description:** Team not being able to meet at the same time due to conflicts of schedule. The majority of people might be able to make it one day, however one or two others might have a class or work during that time frame.

**Severity:** High

**Resolution:** stand-up meetings for check-ins 5 min before lecture MWF, Wednesday evening online meeting from 7-8pm to sort out major issues (group coding/structural issues)

**Status:** Resolved

**Risk:** Team Risk [Learning Android Development]

**Description:** Team is not familiar with Android development so there will be difficulties implementing everything properly

**Severity:** Med

**Resolution:** We are starting with a low velocity to account for having to refer to stack overflow and documentation. [*maybe even ChatGPT*]  REMEMBER TO CITE SOURCES

**Status:** Resolved

**Risk:** Schedule Business varies due to other classes

**Description:** We’re in Midterms so schedules and time availability will vary. 

**Severity:** Med

**Resolution:** Maintain strong communication via frequent check-ins and mentions of any time issues via online-chat.

**Status**: Resolved

**Iteration Length:** 1 week (30 hrs (5 hrs/ person))

**Initial Velocity:** 0.4 as the team is unfamiliar with each other and our workspeeds in a project setting; will adjust accordingly after seeing results from the first iteration 

30 \* 0.5 = 15 hrs of actual work 






# <a name="_leczm6s9r00r"></a>Planning Poker Results:


Increments (In hours): 1, 2, 3, 4, 8, 16 

|**User Story**|**Name**|**Hand**|**Assumptions Revealed**|
| :- | :- | :- | :-: |
|1|Add Goal|4 4 8 8 16 8|<p>- Develop UI</p><p>- developing backend structure will take a while</p><p>- Would require cache (no experience in lab)</p><p>- Assuming very first task completed</p>|
|1|Add Goal|8 8 8 8 8 8|(None)|
|2|Complete Goal|3 3 2 4 4 3|<p>- Requirements: add a true/false flag for complete</p><p>- On click listener to implement</p><p>- Rearranging the list and the UI stuff will probably be the hardest part (strikethrough in addition)</p><p>- Much of the time consuming stuff is already implemented</p>|
|2|Complete Goal|3 3 3 3 3 3|(None)|
|3|Display Goal|3 2 3 2 2 4|- Similar implementation to the list in lab 4 (would just require the on-click for story 2 but can be handled during that time)|
|3|Display Goal|2 2 2 2 2 2 |(None) |
|4|Un-Complete Goal|4 2 2 2 3 2|<p>- Similar to User Story (2)</p><p>- Only difficult part being proper implementation/testing</p>|
|4|Un-Complete Goal|2 2 2 2 2 2|(None)|
|5|Display Date|2 2 3 1 1 2|<p>- Just UI and dealing with API</p><p>- Challenge is making sure date updates and proper format</p><p>- <https://stackoverflow.com/questions/5369682/how-to-get-current-time-and-date-in-android></p><p>- Strftime strptime implementation (or similar)</p>|
|5|Display Date|1 2 1 2 1 1 |<p>- Dates can be finicky</p><p>- Mac and windows have differences regarding time</p><p>&emsp;- Should not be a problem for us as we are using an emulator</p>|
|5|Display Date|1 1 1 1 2 1|<p>- We may encounter problems </p><p>&emsp;- (velocity should account for it)</p>|
|5|Display Date|1 1 1 1 1 1|(None)|
|6|Rollover Goal|3 3 3 3 3 4|<p>- Keep correct and delete correct</p><p>- Once again time stuff</p><p>- Continuous time check?</p><p>- Does the app need to auto refresh time or just on restart</p><p>&emsp;</p><p>&emsp;- Will require some sort of alarm to look into</p>|
|6|Rollover Goal|3 4 4 4 4 3|- Testing might take time (how to change the system date for it?)	|
|6|Rollover Goal|4 4 4 4 4 4|(None)|

# <a name="_xjcycognofb9"></a>User Stories:
- (1.) Add Goal 
  - As a user, I want to be able to add a goal so I can keep track of what I want to accomplish today.
  - Priority: High
  - Dependency: None
  - Time Estimate: 8
  - Task:
    - Goal Repository (some sort of backend)
    - Goal Object/Class
    - Implement “Add” button, when clicked, display pop-up/fragment for goal creation
      - Design UI for display 
      - Submit and cancel button
      - Fragment/pop-up for goal creation
    - Implement keyboard w/ voice dictation
    - Testing
  - Scenarios:
    - Adding First Goal: Given that there are no goals in the list, when Jessica presses the “+” on the upper right corner of the app, then a display pops up for Jessica to type “Prepare for midterm”, when Jessica presses “Submit” on the display, then the goal “Prepare for midterm” shows up on the app.
    - Canceling First Goal: Given that there are no goals in the list, when Jessica presses the “+” on the upper right corner of the app, then a display pops up for Jessica to type “Prepare for midterm”, when Jessica presses “Cancel” on the display, then the display disappears and Jessica sees a blank app with the message "No goals for the Day. Click the + at the upper right to enter your Most Important Thing.".
    - Adding to a List of Goals: Given that there is one goal in the list “Prepare for midterm”, when Jessica presses the “+” on the upper right corner of the app, then a display pops up for Jessica to type “Grocery shopping”, when Jessica presses “Submit” on the display, then the goal “Grocery shopping” shows up below the goal “Grocery shopping”.
    - Given Jessica wants to add a new goal but has a completed goal “Prepare for the midterm” and an unfinished goal “Grocery Shopping”. The newly added goal “Text Maria” would be inserted in between the two.
    - Voice Dictation: Given that the user is adding a task, when the user taps on the microphone at the upper right of the keyboard, then the user can say a phrase, when the user says a phrase, then the app displays the phrase said, when the user clicks the check mark at the bottom right, the task is added

- (2.) Complete Goal
  - As a user, I want to be able to mark a goal as completed so that I do not have to worry about completing or repeating a goal
  - Priority: High
  - Dependencies: User Story #3
  - Time Estimate: 3
  - Task:
    - Onclick event for goal
    - Shift completed goal to top of finished goals list
    - Update goal repository
    - Add strikethrough on goal text
    - Testing
  - Scenarios: 
    - Only One Goal: Given that there is one goal “Prepare for midterm”, when Jessica taps on “Prepare for midterm”, then the “Prepare for midterm text will be crossed out in strikethrough.
    - Multiple Goals: Given that there are two goals “Prepare for midterm” and “Grocery shopping”, when Jessica taps on “Prepare for midterm” to mark the goal as complete, then the “Prepare for midterm” text will be crossed out in strikethrough and moved to below “Grocery shopping”. 
    - Already Completed Goals: Given that there is one unfinished goal “Prepare for midterm” and one finished goal “Grocery shopping”, when Jessica taps on “Prepare for midterm”, then the “Prepare for midterm” text will be crossed out in strikethrough and put above “Grocery shopping”.

- (3.) Display Goals
  - As a user, I want to be able to see the goals I have completed and not completed so that I know what *needs* to be done today. 
  - Priority: High
  - Dependency: None
  - Time Estimate: 2
  - Task: 
    - UI
      - Using a list style of page
    - Fetch goals (2 lists, “active goals” and “completed goals”)
      - Observers for the goals
    - Testing
  - Scenarios:
    - No Goals: Given that there are no goals in the list, when Jessica opens the app, then the message "No goals for the Day.  Click the + at the upper right to enter your Most Important Thing." is displayed on screen in gray text.
    - Already populated goals 
      - Completed Goals: Given that the goal “Prepare for midterm” has been marked as completed, when Jessica opens the app, then the goal “Prepare for midterm” will be striked through.
      - Completed and Uncompleted Goals: Given that the goal “Prepare for midterm” has been marked as completed and the goal “Grocery shopping” has not been completed, when Jessica opens the app, then the goal “Grocery shopping” will not be struck though and will appear above the goal “Prepare for midterm” which will be striked through.

- (4.) Un-Complete Goal
  - As a user, I want to be able to unmark a goal as incomplete so that I know it still needs to be done. 
  - Priority: Med
  - Dependency: User Story #2
  - Time Estimate: 2
  - Task:
    - Add “on-click” on a completed goal to mark as uncomplete
    - Update goal repository 
    - Move goal to top of list
    - Remove strikethrough from goal
    - Testing
  - Scenario: 
    - Only One Completed Goal: Given that the only goal “Prepare for midterm” has been marked as completed, when Jessica taps on “Prepare for midterm” then the strikethrough on “Prepare for midterm” is removed.
    - Completed and Uncompleted Goals: Given that the goal “Prepare for midterm” has been marked as completed and the goal “Grocery shopping” has not been marked as completed, when Jessica taps on “Prepare for midterm” then the strikethrough on “Prepare for midterm” is removed and “Prepare for midterm” is moved above “Grocery shopping”.
    - Only Uncompleted Goals: Given that the goal “Prepare for midterm” has been marked as completed and the goal “Grocery shopping” has been marked as completed, when Jessica taps on “Prepare for midterm” then the strikethrough on “Grocery shopping” is removed and “Grocery shopping” is moved above “Grocery shopping”.



- (5). Display Date 
  - Priority: Low
  - As a user, when I launch the app, I want to see today’s date at the top of the screen so I know what day I am creating goals for.
  - Dependency: none
  - Time Estimate: 1
  - Task:
    - Format: Day of Week x/xx
    - UI Design for date panel
    - Create panel for date 
    - Display date
    - Update date at 2am
    - Testing
  - Scenario:
    - Display Today’s Date with no Goals: Given that there are no events added and the date is Wednesday, January 31, when Jessica opens that app then the app should display “Wednesday 1/31” at the top of the screen
    - Display Today’s Date with Goals: Given that there is the goal “Prepare for midterm” added and the date is Wednesday, January 31, when Jessica opens that app then the app should display “Wednesday 1/31” at the top of the screen and the goal “Prepare for midterm” should be displayed beneath the date
- (6) Rollover Goals
  - Priority: Med
  - Dependency: User Story #1, #2, #3, #5
  - Time Estimate: 4
  - As a user, I want my goals to rollover to the next day if I don’t complete them so I know that I still have to finish them.
  - Task:
    - Roll over at 2am & retrieve unfinished goals from previous day 
    - Delete any completed goals @ rollover time 
    - Testing
  - Scenario:
    - Uncompleted Goals: Given that the goal “Prepare for midterm” is not marked as completed and the goal “text Maria” is marked as completed and the date is Wednesday, 1/31, when it is 2am, then the goal “Prepare for midterm” will be displayed at the top marked as uncompleted with the date Thursday, 2/1.
    - All Goals Completed: Given that the goals “Prepare for midterm” and goal “Grocery shopping” are marked as completed and there are no other goals, when it turns 2am comes, then no goals will be displayed.

# <a name="_6zm5xqotkze"></a>Tasks

**Iteration 1:**

Loose Tasks:

-set up Android environment **Time Estimate:** 15 min

-set up basis for the app (design basic structure, Java classes, etc) **Time Estimate:** 15 min

-learn how to add keyboard function **Time Estimate:** 15 min

-set up GitHub repository & Github Projects **Time Estimate:** 30 min

User Story #1: Add GOAL (8 hours) (8.5 hours after estimating tasks)

- Task:
  - Goal Repository (some sort of backend) **Time Estimate:** 3 hours
  - Goal Object/Class **Time Estimate:** 1 hour
  - Implement “Add” button, when clicked, display pop-up/fragment for goal creation 
    - Design UI for display **Time Estimate:** 30 min
    - Submit and cancel button **Time Estimate:** 30 min
    - Fragment/pop-up for goal creation **Time Estimate:** 1 hour
  - Implement keyboard w/ voice dictation (voice dictation is low priority) **Time Estimate:** 30 min
  - Testing **Time Estimate:** 2 hours

User Story #3: Display Goal (2 hours)

- Task: 
  - UI **Time Estimate:** 30 min
    - Using a list style of page
  - Fetch goals (2 lists, “active goals” and “completed goals”) 

**Time Estimate:** 1 hour

- Observers for the goals
- Testing **Time Estimate:** 30 min

User Story #5: Display Date (1 hours)

- Task:
  - Format: Day of Week x/xx **Time Estimate:** 30min
  - UI Design for date panel **Time Estimate:** 15min
    - Create panel for date
    - Display date** 
  - Testing **Time Estimate:** 15min

# <a name="_94fogoyb8xj"></a>Iterations/Milestone
First Iteration (Feb 4 - Feb 10):

User Story #1 – Add Goal

User Story #3 – Display Goal

User Story #5 – Display Date


Second Iteration (Feb 11 - Feb 19):

User Story #2 – Complete Goal

User Story #4 – Un-Complete Goal

1st Milestone:

-try to have a working app! 

-MVP: be able to add, display and complete a goal 

# <a name="_fpdeysepnvwp"></a>Scenario-Based Milestone Tests

1\. Start the app by tapping the app icon. You should see the date Wednesday 1/31 in the center at the top. You should also see a blank white screen beneath it that says "No goals for the Day.  Click the + at the upper right to enter your Most Important Thing." in gray text in the middle. You should also see a + in the upper right corner of the app. 

2\. Tap the + in the upper right corner. A small box with a text input field pops up and a “Cancel” button on the bottom left half of the box and a “Submit” button on the bottom right half of the box should appear. A keyboard should appear at the bottom of the app.

3\.Type in “Prepare for midterm” in the text field, using the keyboard, then tap “Cancel.” The box pop-up should disappear and you should still see the date Wednesday 1/31 in the center at the top and a blank white screen with the message "No goals for the Day.  Click the + at the upper right to enter your Most Important Thing.", along with a + in the upper right corner.

4\. Tap the + again. Type in “Prepare for midterm” in the text field,” hit “Submit”. The box pop-up should disappear. Below the date, “Prepare for midterm” shows up.

5\. Tap the + again. Type in “Grocery shopping” in the text field, hit “Submit”. The box pop-up should disappear, and “Grocery shopping” should appear below “Prepare for midterm”.

6\. Tap on “Prepare for midterm.” The text for “Prepare for midterm” should become ~~“Prepare for midterm”.~~ This should appear below “Grocery shopping”.

7\. Tap on “Grocery shopping”. The text for “Grocery shopping” should become ~~“Grocery shopping”~~. This should appear above ~~“Prepare for midterm”.~~

8\. Tap on ~~“Prepare for midterm”.~~ The text for it should become “Prepare for midterm”. This should appear above ~~“Grocery shopping”.~~

9\. Close the app. Reopen the app. The date Wednesday 1/31 should still be at the top. Below it is, in order, “Prepare for midterm”, ~~“Grocery shopping~~”. 

10\. Tap the + again. Tap the text field to manifest the keyboard. Tap the microphone button on the upper right of the keyboard. Speak into the phone “text Maria”. Tap “Submit”. “Prepare for midterm” should appear above “text Maria” which appears above ~~“Grocery shopping~~”.

11\. When it is 2am, the date at the top should change to Thursday 2/01. Below it should be “Prepare for midterm”, which is above “text Maria”.

12\. Close the app.
# <a name="_qag1e56l2oz7"></a>Github Project

Project: <https://github.com/orgs/CSE-110-Winter-2024/projects/182/views/1> 

Repository: <https://github.com/CSE-110-Winter-2024/project-team-18>
