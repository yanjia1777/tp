# Peng Yanjia's Project Portfolio Page

## Overview

**Mint** is an all-in-one money managing app that helps you track your daily expense and set budgets. It is optimized
for use via a Command Line Interface (CLI).

## Summary of Contributions

### Code Contributed

The code written by me (`yanjia1777`) can be
found [here](https://nus-cs2113-ay2122s1.github.io/tp-dashboard/?search=&sort=totalCommits&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2021-09-25&tabOpen=true&tabType=authorship&tabAuthor=yanjia1777&tabRepo=AY2122S1-CS2113T-W11-2%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code~other&authorshipIsBinaryFileTypeChecked=false)

### Enhancements Implemented

1. Implemented base classes

- Implemented `Entry` class, which is then inherited by other classes in later part of the code.
- Implemented `Income` class.

2. Implementation of view function

- Implemented `view` command, allowing users to print out all their entries that are in the list.
  - Implemented modifier to view a certain month.
  - Implemented modifier to view a certain year.
  - Implemented modifier to view from a specific date period.
- Implemented sorting in view by name, amount, date and category.
  - Implemented sorting in ascending/descending.

3. Implemented deleteAll function

- Implemented the `deleteAll` function.
  - Implemented modifiers to just delete all normal entries or all recurring entries.

4. Duke

- Created the Main class structure.

5. Ui

- Printing of entries
- Implemented variable indentation of entries whenever it is required to be printed in `Ui`.
  - Three different indentation methods; left indent, middle indent and right indent.
  - Indentations automatically fits the size of the longest entry in the list.


6. Parser

- User friendliness improvements.
    - Multiple alternative inputs accepted such as `deleteall` and `deleteAll`.
    - Setting defaults if additional fields are omitted.
- Parsing input via arguments.

### Contributions to the UG

- Created UG structure
- Added view command guide
- Added deleteAll guide
- Updated documentation formatting and overall readability

### Contributions to the DG

- Added documentation for the Ui component under the Design section.
    - Added brief explanation of the Ui component.
    - Added the sequence diagrams for the Ui component.
- Added manual testing portion for view function.
- Added manual testing potion for deleteAll function.

### Contributions to team-based tasks

- Gave inputs on the general direction during ideation phases (V1.0, V2.0, V2.1)
- Updated User Guide (UG) and Developer Guide (DG) documentation that were not specific to any feature:
- Reviewed and updated overall User Guide and fixed inconsistencies between UG and error messages in application
- Helped to troubleshoot for solution to bugs found.


### Review/mentoring contributions

- Reviewed PRs and ensured overall code quality/formatting was up to standard

### Community
Reported bugs during PE Dry Run.
Reviewed other's PR with non-trivial comments.