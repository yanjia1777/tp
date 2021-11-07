# Seet Ting Yang Irvin's Project Portfolio Page

## Overview

**Mint** is an all-in-one money managing app that helps you track your daily expense and set budgets. It is optimized
for use via a Command Line Interface (CLI).

## Summary of Contributions

- **New Feature**: Implemented budget setting
    - What it does: `Budget`: base class which allows user to set spending limit and fetch spending related to
      respective budgets. `BudgetManager` which handles all `Budget`-related actions such as fetching monthly spending
      and limit.
    - Justification: This feature improves the product significantly for the users as they can set budget for individual
      categories as well as track the amount of money they spent in respective categories and make informed decisions.

- **New Feature**: Implemented base class for expenses
    - What it does: Basic building block of our app. Each instance stores information about the expense.

- **New Feature**: Implemented categories' enumeration: `ExpenseCategory` and `IncomeCategory`
    - What it does: Assigned category to entries.
    - Justification: Allow user to easily assign categories to their entry for housekeeping purposes.
    - Highlights: It also allowed the team to expand on our features such as allowing user to view their entries by
      category or set budgets for specific categories.

- **New Feature**: Implemented skeleton and formatting of help message in Ui
    - What it does: Prints a summary of all commands available.
    - Justification: Help users old and new navigate the app efficiently.

- **Enhancement**: Handled unsafe user input
    - What it does: 2 layered defense system. First, check for any invalid characters. Then, check if valid characters
      are used in correct manner.
    - Justification: This feature improves the product significantly as there are customized messages to tell the user
      which part of their input went wrong. It is not uncommon for users to mess up the inputs. We want to ensure that
      user's carelessness will not reduce their enjoyment while using our app.
    - Highlights: It was not easy to find the correct regex pattern to match as they are many factors to consider, such
      as optional tags.


- **Enhancement**: Allow users to key in entries without specify `date` or `category`
    - What it does: initialisation of fields when user do not specify some fields. e.g, `date` initialised
      to `LocalDate.now()` and `category` initialised to `OTHERS`
    - Justification: It may be tedious for user to type out the entirety of the entry. Users using expense tracking app
      mostly key in their expenses on the same day. Also, they may want to key in quickly and then edit the relevant
      fields in their own time.

- **Enhancement**: Allow users to key in entries
  in [10 different date formats](https://github.com/AY2122S1-CS2113T-W11-2/tp/blob/master/docs/UserGuide.md#acceptable-date-formats)
    - What it does: User can type in date in any of the 10 given formats and our program can identify it correctly
    - Justification: Different users have different preference in terms of keying in date, we want to ensure everyone
      can enjoy the app.

- **Enhancement**: Implemented a parsing fuction that reads input correctly despite jumbled up tags
    - What it does: `parseInputByTags` method, a one-size-fits-all which parse user inputs by tags regardless of order
      using regex. As tags are essential to our app, it is for many functions.
    - Justification: Users do not have to worry about following a "strict" format. Improves user experience

- **Code
  contributed**: [here](https://nus-cs2113-ay2122s1.github.io/tp-dashboard/?search=irvinseet&sort=groupTitle&sortWithin=title&since=2021-09-25&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=false)

- **Project management**:
    - Set up iterations `v1.0` - `v2.1`on GitHub.
    - Set up Issue Trackers on GitHub.

- **Contributions to team-based tasks**
    - Hosted meetings and set general direction during ideation phases (V1.0, V2.0, V2.1).
    - Set up the Github team organisation and repository.
    - Updated User Guide (UG) and Developer Guide (DG) documentation that were not specific to any feature:
        - Reviewed and updated overall User Guide and fixed inconsistencies between UG and error messages in
          application.
    - Constantly pushed to enhance user-friendliness of our app.

- **Community**:
    - PR reviewed: [#42](https://github.com/nus-cs2113-AY2122S1/tp/pull/42),

    - Reported bugs and suggestions for other teams in the class: [Here](https://github.com/irvinseet/ped/issues)

- **Contributions to the UG**
    - Converted UG from Google Docs draft into markdown.
    - Added Introduction of the app.
    - Added table of "Acceptable date formats".
    - Added table of "Available categories".
    - Updated documentation formatting and overall readability.

- **Contributions to the DG**
    - Added diagram of System Architecture.
    - Added documentation for `Budget` Component.
    - Added implementation and design consideration of `BudgetManager`.
    - Added some user stories.
    - Added manual testing for `set` function.
    - Added manual testing for `budget` function.

