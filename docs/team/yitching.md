# Seetoh Yit Ching's Project Portfolio Page

## Overview

**Mint** is an all-in-one money managing app that helps you track your daily expense and set budgets. It is optimized
for use via a Command Line Interface (CLI).

**Contribution**: The code written by me (`Yitching`) can be
found [here](https://nus-cs2113-ay2122s1.github.io/tp-dashboard/?search=&sort=totalCommits&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2021-09-25&tabOpen=true&tabType=authorship&zFR=false&tabAuthor=Yitching&tabRepo=AY2122S1-CS2113T-W11-2%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code~other&authorshipIsBinaryFileTypeChecked=false)

### Enhancements Implemented

- **New Feature**: Implemented edit function for both recurring and normal expense

  - What it does: The `edit` function allows users to edit any expense. The user would be able to input the fields they wish to edit in any order, and the changes would be reflected if the fields they entered are valid.
  - Justification: This feature improves the product because if they accidentally make a small mistake, they would not have to delete the entry and can simply edit the entry to correct the mistake.
    The fact that they can specify the fields they wish to edit in any order also makes it very user-friendly.
  - Highlights: Initially, implementing the edit function was a challenge as there was a need to take in another user input.
    The additional feature of allowing users to specify the tags in any order also made the code more challenging as somehow
    it was difficult to find an efficient way to implement the solution. Taking in another user input also meant that more validity
    checks needed to be done and therefore initially it was quite bug prone until we implemented a more solid validity checker class.

- **Enhancement**: Parser for `edit` function
  - What it does: This enhancement was implemented so that the parser would be the one parsing the required information to the `edit` function.
    In addition, to enhance the efficiency, a HashMap data structure was used to store the attributes of the new expense to overwrite the old expense.
  - Justification: This is in an attempt to make our code more OOP as well as to make our program more efficient.
  - Highlights: Implementing this part of the code was fun especially since it was an opportunity to try out a new data structure that I
    have not really tried before.

- **New Feature**: Implemented the `storage` function for recurring, budget and normal expense list
  - What it does: The storage function saves the data immediately after an action is performed.
  - Justification: This function is important as our application is an expense tracker. Given the nature of our program, it would
    only make sense to save the expenses or incomes set by the user so that they would not have to key in the entry and restart
    everytime they terminate the program. In this sense, they would be able to more effectively use our application as it can help
    them properly keep track of their expenses.

- **Enhancement**: Added additional validity checks to the text files and directory created to prevent erroneous entries which may jeopardize the functionality of our program.
  - What it does: The additional validity checks prevents users from creating invalid file types, and it would also monitor the text file
    such that only valid entries are loaded into the program when it is used.
  - Highlights: This enhancement was a little difficult to implement initially as it was difficult figuring out how to defend an external file effectively. Some
    of the bugs encountered were also unexpected which did catch me off guard initially.

- **Enhancement**: Ui
  - Justification: Added `edit` function related messages in the Ui class for more OOP.

- **Contributions to the UG**:
  - Added the QuickStart portion of the User Guide.
  - Added documentation for the edit function in the User Guide.

- **Contributions to the DG**:
  - Added documentation for the storage component under the Design section.
    - Added brief explanation of the storage component.
    - Added the sequence diagrams for the storage component.
  - Added manual testing skeleton and specific details for add and edit functions.
  - Added some user stories.
- **Contributions to team-based tasks**:
  - Gave inputs on the general direction during ideation phases (V1.0, V2.0, V2.1).
  - Identified some bugs to be fixed.
  - Updated User Guide (UG) and Developer Guide (DG) documentation that were not specific to any feature:
    - Reviewed and updated overall User Guide and fixed inconsistencies between UG and error messages in application.

- **Review/mentoring contributions**:
  - Reviewed PRs and ensured overall code quality/formatting was up to standard.
- **Community**
  - PR reviewed: [Here](https://github.com/nus-cs2113-AY2122S1/tp/pull/25/files/969ac6a3a4b737bbf9839bb634ca90680d4ee988)
  - Reported bugs and suggestions for other teams in the class: [Here](https://github.com/Yitching/ped/issues)
