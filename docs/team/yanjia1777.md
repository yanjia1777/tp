# Peng Yanjia's Project Portfolio Page

## Overview

**Mint** is an all-in-one money managing app that helps you track your daily expense and set budgets. It is optimized
for use via a Command Line Interface (CLI).

## Summary of Contributions

- **New Feature**: Implemented `view` function, allowing users to print out all their entries that are in the list.

  - What it does: allow users to view all their current entries and filter and sort them based on their preference.
    The user is able to input the modifiers they wish to include in their viewing of the entries and all entries that match the requirements specified will be shown.
    The user is also able to input how they want the list to be sorted based on either name, amount, date or category, and also whether to sort the list in ascending or descending order.
  - Justification: This feature is essential to the product as it allows the user to know what entries they have in their application and allows them to visualize it as they want via sorting and filtering of these entries.
  - Highlights: This feature is crucial to the program as it is the feature that will definitely be the most used among the other features. It was challenging to implement as they were many user concerns to take not of as it is the most important function in the application. There factors include:
    - The default sorting and filtering if fields are omitted.
    - The table is listed out in a clear and concise manner.
    - Net total is calculated at the bottom.

- **New Feature**: Implemented `deleteAll` function, allowing users to delete all entries from both normal and recurring entry lists or either one of them.
  - What it does: deletes all entries in both lists or a particular list.
  - Justification: This feature improves the product as users may find the need to delete all their entries in one command such as if they want to reset the application.
  - Highlights: It was difficult to implement as there were considerations such as if the user typed it accidentally, thus the inclusion of a confirmation message.
  
- **New Feature**: Implemented base classes `Entry` and `Income`
  - What it does: `Entry` class is the base class for all other classes. `Income` class inherits from the `Entry` class. Each instance
    stores information about the entry or income respectively.

- **Enhancement**: Implemented variable indentation for printing of entries.
  - What it does: indents the entries based on the longest entry of the current list.
  - Justification: This feature improves the product significantly as it allows for the label of all entries to line up with each other, allowing users to view the entries in a much more efficient and clear manner.
  - Highlights: It was challenging to implement as the indentations vary based on the longest entry in the entire list. The indentations could be left indent, middle indent or right indent based on the context of the label to improve the user-experience. The indentation are also done automatically and had to be calculated beforehand before printing of the entries.

- **Enhancement**: Implemented validity checking of parsed entries' fields for some commands.
  - What it does: checks if parsed name, amount, date, end date, interval of the entries are valid and checks
    if mandatory fields are specified and valid.
  
- **Code contributed**: [here](https://nus-cs2113-ay2122s1.github.io/tp-dashboard/?search=&sort=totalCommits&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2021-09-25&tabOpen=true&tabType=authorship&tabAuthor=yanjia1777&tabRepo=AY2122S1-CS2113T-W11-2%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code~other&authorshipIsBinaryFileTypeChecked=false)

- **Community**:
  - PRs reviewed: [#38](https://github.com/nus-cs2113-AY2122S1/tp/pull/38),
    [#23](https://github.com/nus-cs2113-AY2122S1/tp/pull/23),

  - Reported bugs and suggestions for other teams: [#1](https://github.com/yanjia1777/ped/issues),

- **Contributions to the UG**
  - Created UG structure
  - Added `view` command guide
  - Added `deleteAll` guide
  - Updated documentation formatting and overall readability

- **Contributions to the DG**
  - Added documentation for the Ui component under the Design section.
    - Added brief explanation of the Ui component.
    - Added the sequence diagrams for the Ui component.
  - Added manual testing portion for view function.
  - Added manual testing potion for deleteAll function.