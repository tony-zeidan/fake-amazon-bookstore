# Fake Amazon Bookstore #

[![Continuous Integration](https://github.com/tony-zeidan/fake-amazon-bookstore/actions/workflows/ci-testing.yml/badge.svg?branch=master)](https://github.com/tony-zeidan/fake-amazon-bookstore/actions/workflows/ci-testing.yml)

## Project Description ##
Bookstore Owner can upload and edit Book information (ISBN, picture, description, author, publisher,...) and inventory. User can search for, and browse through, the books in the bookstore, sort/filter them based on the above information. User can then decide to purchase one or many books by putting them in the Shopping Cart and proceeding to Checkout. The purchase itself will obviously be simulated, but purchases cannot exceed the inventory. User can also view Book Recommendations based on past purchases. This is done by looking for users whose purchases are most similar (using Jaccard distance: Google it!), and then recommending books purchased by those similar users but that the current User hasn't yet purchased.


## Database schema ##
![img.png](img.png)

## Current State of Project ##
### Functionality
Currently, the bookstore admin page has the following abilities:
 - Access the administrative view, the bookstore view, and the user cart page
 - Upload a new book, with a specified name, description, ISBN, and quantity in stock
 - Modify the stock of a particular book
 - Remove books entirely from the bookstore
 - View all the books stored in the bookstore


The bookstore user page also has the following abilities:
 - Access the bookstore view and the user cart page
 - Add items to their cart
 - View cart items
 - Change quantities of specific items in cart
 - Remove items completely from cart


Guests finally have the following abilities:
 - Access the homepage
 - Register for a new unique account
 - Login with user account credentials
 - Logout from account

## Milestones ##
Milestone 1: Early prototype. Give a 10-15 minute demo during the lab on March 8h.
For this milestone we are looking to see enough functionality to get a feel for the system and how it will
work. One important use case should be operational. It should collect data from the back end, do
something with it and display the result. The display doesn't need to be fancy. There should be a GitHub
repo, integrated with CI Cloning the repo and running the pom.xml should provide us with a ready-to-run JAR file.
We will also inspect the README file, the Issues, the Kanban, the code reviews, the tests, and we will
verify that everybody is participating in all aspects of the project (if that is not the case, different team
members will end up with different grades).

Milestone 2: Alpha Release. Give a 10-15 minute demo during the lab on March 22th.
For the alpha release your system should be somewhat usable, although not feature-complete. This
means that a user should be able to use several related features of the app and do something reasonably
useful. The README on GitHub must be updated with a plan for the next sprint.

Milestone 3 - Final demo. Project complete. Give a 10-15 minute demo during the lab on April 5th.
For the final sprint of your project you must decide on the final scope of the product: a set of features
that can be implemented within the given timeline and makes the product usable and useful. The user
interface should not have any dangling links to non-implemented features.

## Project Guidelines ##
### Timeline ###
There will be three 2-week "sprints" where the project will be incrementally built. After each sprint
each group must meet with the TA during the lab hours for a short demo (less than 15 min) describing
their progress.
Requirements

### Project Setup ###
Your project should have a repository on Github, enabled with CI from the start of the project.
In the root folder of your project there should be a pom.xml file. The TA should be able to clone the
project, and use the pom.xml to compile, test, package and run the application.

### Development and release process ###
The project must have a main branch, and each contributor should create a new branch for each of the
features they implement. Once development is complete, the team member should open a pull request
and request code reviews by at least one other team member. After the reviewer(s) approve the code, it
can be merged into master and re-tested and built with CI. The TAs are instructed to check for this!

### Scrums ###
Each team member is required to communicate weekly updates following the principles of the daily
scrum meetings. The purpose of daily scrums is to communicate within the project team. This
communication should also be visible to the TA, and therefore you will use GitHub Issues1:
Each week a group member should open a new issue called Weekly scrum – [date], then every team
member should add comments with their own contribution, i.e. their answers to the questions:
- what have I done this week? (with link(s) to the relevant GitHub Issue(s))
- what will I do next week? (with link(s) to the relevant GitHub Issue(s))
- what is holding me back? (if applicable)
  Expected length: 1-2 sentences per item.

### Product backlog ###
  Use GitHub Projects (“Projects” tab in your GitHub repo) to create a “Kanban” style view of your
  Issues, with columns dedicated to “backlog”, “in progress”, “completed”. The README.md file on
  Github should summarize the current state of the project as per the Kanban, and include a plan for the
  next sprint. It should also include the up-to-date schema of the database2.
  1 To enable issues in your GitHub repo: go to the “settings” tab of your repo (top right), then check the “issues”
  checkbox under “Features”.
  2 IntelliJ can do this for you, once you have an actual persistent database set up. Otherwise you may have to draw it by
  hand.
  Page 2

### Project report: No project report is required. ###
  The information on the GitHub README should provide sufficient information to understand what the
  project is and how much is implemented. Include a UML class diagram of your Models (and only of the
  Models!), as well as the corresponding database schema created by the ORM (observe what patterns are
  being used by the ORM behind the scenes! You need to know these as preparation for the final exam
  too...). Your diagrams must be in sync with the code that you have produced, so just put these diagrams
  in version control, and grow/update them along with your code!
  Evaluation
  Each milestone counts for 33% of the overall project grade and will be judged as follows:
  Process, agile practices (60%)
- Proper use of version control, continuous integration, code reviews.
- Tests: unit tests, integration tests
- Deployment frequency: at minimum, 2-3 deploys per week. Every team member should be deploying.
- Information on GitHub README.
- Kanban, Issues, Weekly scrum contributions.
  Features (40%)
  Status of the project at each milestone, and quality of what has been deployed. The TA will evaluate
  whether the project makes reasonable progress. Since every project is different, we cannot specify a list
  of expected features to be implemented, or lines of code to be written.
