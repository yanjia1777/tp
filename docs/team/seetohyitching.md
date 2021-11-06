# Seetoh Yit Ching's Project Portfolio Page

## Overview

**Mint** is an all-in-one money managing app that helps you track your daily expense and set budgets. It is optimized
for use via a Command Line Interface (CLI).

## Summary of Contributions

### Code Contributed

The code written by me (`seetohyitching`) can be
found [here](https://nus-cs2113-ay2122s1.github.io/tp-dashboard/?search=&sort=totalCommits&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2021-09-25&tabOpen=true&tabType=authorship&zFR=false&tabAuthor=Yitching&tabRepo=AY2122S1-CS2113T-W11-2%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code~other&authorshipIsBinaryFileTypeChecked=false)

### Enhancements Implemented

1. Implemented edit function for both recurring and normal expense

- Implemented the `edit` function that would allow users to edit any expense.
- The user would be able to input the fields they wish to edit in any order, and the changes would be reflected if the fields they entered are valid.

2. Parser
- Extracted some methods from the `edit` function such that the parser would be the one parsing the required information to the `edit` function.
- Implemented a HashMap data structure to store the attributes of the new expense to overwrite the old expense for efficiency.

3. Implemented the `storage` function for recurring and normal expense list
- Implemented the `storage` function such that the data would be saved immediately after an action is performed. 
- Added additional validity checks to the text files and directory created to prevent erroneous entries which may jeopardize the functionality of our program.

4. Implemented `storage` function for budget list
- Implemented the `storage` function such that the spending limit for each category would be stored.
- Added additional validity checks to the text files and directory created to prevent erroneous entries which may jeopardize the functionality of our program.

5. Ui
- Added `edit` function related messages. 
### Contributions to the UG

- Added the QuickStart portion of the User Guide.
- Added documentation for the edit function in the User Guide.

### Contributions to the DG

- Added documentation for the storage component under the Design section. 
  - Added brief explanation of the storage component.
  - Added the sequence diagrams for the storage component.
- Added manual testing portion for add, delete and edit functions.
- Added some user stories.

### Contributions to team-based tasks

- Gave inputs on the general direction during ideation phases (V1.0, V2.0, V2.1).
- Updated User Guide (UG) and Developer Guide (DG) documentation that were not specific to any feature:
    - Reviewed and updated overall User Guide and fixed inconsistencies between UG and error messages in application.


### Review/mentoring contributions

- Reviewed PRs and ensured overall code quality/formatting was up to standard.

### Community
Reported bugs during PE Dry Run.