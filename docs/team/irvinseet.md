# irvinseet's Project Portfolio Page

## Overview

**Mint** is an all-in-one money managing app that helps you track your daily expense and set budgets. It is optimized
for use via a Command Line Interface (CLI).

## Summary of Contributions

### Code Contributed

The code written by me (`irvinseet`) can be
found [here](https://nus-cs2113-ay2122s1.github.io/tp-dashboard/?search=irvinseet&sort=groupTitle&sortWithin=title&since=2021-09-25&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=false)

### Enhancements Implemented

1. Implemented base classes

- Implemented `Entry` class, which is then inherited by other classes in later part of the code.
- Implemented `Expense` class.

2. Implementation of enumerations

- `ExpenseCategory` and `IncomeCategory` which allows the user to assign a category to their entries to easily query
  their data or set budgets.
- Printing of category list when user key in `cat`

3. Implemented budget setting -`Budget`: base class which allows user to set spending limit and fetch spending related
   to respective budgets.

- `BudgetManager` which handles all `Budget`-related actions.

4. Ui

- `help` message
- `Budget`-related messages
- check for unsafe user input using regex.

5. Parser

- User friendliness improvements
    - initialisation of fields when user do not specify some of the fields. e.g, `date` initialised to `LocalDate.now()`
      and `category` initialised to `OTHERS`
    - `dateFormatter` which takes
      in [10 different formats](https://github.com/AY2122S1-CS2113T-W11-2/tp/blob/master/docs/UserGuide.md#acceptable-date-formats)
    - `parseInputByTags` which parse user inputs by tags regardless of order using regex.
- setting categories of entries

### Contributions to the UG

- Converted UG into markdown
- Introduction of the app
- Added table of "Acceptable date formats"
- Added table of "Available categories"
- Updated documentation formatting and overall readability

### Contributions to the DG

- Added diagram of System Architecture
- Added documentation for `Budget` Component
- Added some user stories

### Contributions to team-based tasks

- Hosted meetings and set general direction during ideation phases (V1.0, V2.0, V2.1)
- Set up the Github team organisation and repository
- Updated User Guide (UG) and Developer Guide (DG) documentation that were not specific to any feature:
    - Reviewed and updated overall User Guide and fixed inconsistencies between UG and error messages in application


### Review/mentoring contributions

- Reviewed PRs and ensured overall code quality/formatting was up to standard

### Community
Reported bugs during PE Dry Run.