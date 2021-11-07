# Developer Guide

### Table of Content

- [Acknowledgements](#acknowledgements)
- [Design](#design)
    - [System Architecture](#sys-arch)
    - [Ui Component](#text-ui)
    - [Logic Component](#logic)
    - [Model Component](#model)
        - [Finance Component](#finance)
        - [Budget Component](#budget)
    - [Storage Component](#storage)
- [Implementation](#implementation)
- [Product Scope](#scope)
    - [Target user profile](#target)
    - [Value proposition](#value)
- [User Stories](#stories)
- [Non-Function Requirements](#nf-req)
- [Glossary](#glossary)
- [Instructions for manual testing](#manual-test)
    - [Adding an Entry](#Adding)
    - [Deleting an Entry](#delete)
    - [Editing an Entry](#edit)
    - [Adding a Recurring Entry](#Add-recurring-entry)
    - [Deleting a Recurring Entry](#Delete-recurring-entry)
    - [Editing a recurring Entry](#Edit-recurring-entry)
    - [Setting Budget](#Set-budget)
    - [Viewing Budget](#View-budget)

## <a name="acknowledgements"></a>Acknowledgements

- Inspiration for App Idea and OOP Structure:
- Inspiration for User Guide and Developer Guide: AddressBook (Level 2) <br />
  https://se-education.org/addressbook-level3/DeveloperGuide.html <br/>
  https://se-education.org/addressbook-level3/UserGuide.html

{list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the
original source as well}

## <a name="design"></a>Design

### <a name="sys-arch"></a>System Architecture

![](images/ArchitectureDiagram.png)

The above **System Architecture** diagram shows the high-level design of Mint.

On launch, the `Main` class initialises the app components in the correct sequence and links them up with each other, in
the correct sequence.

Apart from `Main`, Mint comprises six main components, namely:

- `Ui`: The UI of the App
- `Logic`: Make sense of user input and execute command
- `Model`: Holds the data of the App and performs actions on the data.
- `Storage`: Reads from and writes to [`LocalStorage`](#local-storage).

The four components interact with each other.

### <a name="text-ui"></a>Ui Component

![](images/Ui.png)

The UI consists of a `Ui` class that represents the interface that the user interacts with.

The Ui component,

* takes in user inputs.
* pass them either to the `Logic` component or `Storage` component.
* outputs entries requested by the user.

### <a name="logic"></a>Logic Component

Here is a (partial) class diagram of the `Logic` component.

![](images/Logic.png)

How the `Logic` component works:

1. When `Logic` is called upon to parse a command, it uses `Parser` class to parse the user command.
2. The `Parser` prepares to return a `Command` object (more precisely, an object of one of its </br >
   subclasses e.g.,`AddRecurringCommand`) by parsing the arguments and verifying through `ValidityChecker` class.
3. `Parser` encapsulates the details of the query as an `Entry` object from `Model`.
4. `Parser` returns a `Command` object, which is executed by `Main`.
5. The `Command` can communicate with the `Model` when it is executed </br>
6. The `Command` saves the resulting data by using the `Storage`.
7. The result is printed to the user by the `Ui`.
   (e.g. to add a recurring entry)

The Seuquence Diagram below illustrates the interactions within the `Logic` component for the </br>
`parseCommand("add n/movie a/12")` API call.

![](images/LogicSequenceDiagram.png)

### <a name="model"></a>Model Component

The `Model` package consists of three sub-components: `FinanceManager`, `Entry`, and `Budget`.

##### <a name="finance"></a>Finance and Entry Components

![](images/Finance.png)

The `FinanceManager` component,

- stores all the entry data i.e., all `Entry` objects.
    - `NormalFinanceManager` object stores all the `Income` and `Expense` objects
    - `RecurringFinanceManager` stores all the `RecurringIncome` and `RecurringExpense` objects
- performs action on the list of `Entry` objects (e.g., add, delete, etc.)
- depends on the `Ui` component as some action needs confirmation from the user (e.g. For edit, after the user chooses
  the entry to edit, the user needs to provide the fields to be edited.)

The `Entry` component,

- stores information about the individual `Entry` object.
- does not depend on any of the other three components.

#### <a name="budget"></a>Budget Component

![](images/Budget.png)

The `Budget` package consists of a `BudgetManager` and the `Budget`'s each of the seven `ExpenseCategory`'s.

- `Budget` is an abstract class
- `XYZBudget`(`XYZ` is a placeholder for the specified budget e.g., `FoodBudget`), inherits `Budget` and its attributes.
- `BudgetManager` stores a list of the seven `ExpenseCategory`'s.
- Currently, `BudgetManager` only interacts in 2 ways
    - when user want to set budget for a specific category
    - when other parts of the app requires the list of budgets e.g., `Ui` needs the list to print to the user, or
      `FinanceManager` needs to know if the user is nearing their spending limit to notify them.

### <a name="storage"></a>Storage Component

**How the `Storage` component works:**

**1. Loading the lists from the stored files:**

![](images/StorageLoadFiles.png)
> For ease of visualization, since the logic for all the different lists are the same, we have broken it down to show
> one example using the diagram

1. When the program is executed, all the objects including the `BudgetDataManager`, `DataManagerActions`,
   `NormalListDataManager` and `RecurringListDataManager` would be created.
2. The programme would then load all the stored content from the lists which records the user's previously recorded
   normal expenditure, recurring expenditure, and the spending limits they set for each category.
3. Upon detection, missing text files, and the required directory would be created.

**2. General logic for each command:**

![](images/Storage.png)

1. After the command is extracted from the `Parser`, `Duke` would call the respective command class. In the case of the
   add function, the `AddCommand` class would be called.
2. In the `AddCommand` class, after the add command has been successfully performed, a method call to
   `appendToEntryListTextFile` would activate the `NormalListDataManager` class for which the added entry would be
   appended to an external text file.

**The `Storage` component:**

1. Can save all the `recurringEntryList`, `budgetList` and the `entryList` in a text file. It is also able to read the
   data from the respective text files and read them back into the corresponding objects.
2. Has four different classes. `DataManagerActions` comprises the common components used by the other two classes,
   `BudgetListDataManager`, `RecurringListDataManager` and `NormalListDataManager`. All of these classes inherit from
   the `DataManagerActions` class.

## <a name="implementation"></a>Implementation

### BudgetManager

Below is a sequence diagram of how the `BudgetManager` interacts with other classes to check if user exceeds their
budget.

![](images/BudgetWarning.png)

Note: `XYZBudget` (`XYZ` is a placeholder for the specified budget e.g., `FoodBudget`)

When `checkExceedBudget(entry,...)` is called, assuming `entry` is of type `Expense`,

1) `BudgetManager` will fetch both `Entries` and `RecurringEntries` from their respective `FinanceManager`.
    - For readability purposes, we will denote all entries as `entries` and group both `NormalFinanceManager`
      and `RecurringFinanceManager` as `FinanceManager`.

2) `BudgetManager` will then retrieve the specific `XYZBudget` corresponding to `entry`'s category.

- `BudgetManager` now have reference to the specific `XYZBudget`.

3) Using the same instance of `XYZBudget`, `BudgetManager` will
    1) get monthly spending corresponding to the `entry`'s category.
    2) get limit/budget set for `XYZBudget`, which is of `entry`'s category.

4) Lastly, `BudgetManager` will check if user has exceeded their threshold for that specific category and prints a
   warning message to the user if they have done so.

#### Design

Aspect: how to check whether user exceeded budget

- Option 1 (current choice): iterate through the list of entries each time `checkExceedBudget(entry,...)` is called and
  return the `amountSpent` and `spendingLimit` corresponding to the current `Entry`'s category. Only happens when
  user `add` entries.
    - Pros: Easy to implement. Less coupling of components.
    - Cons: More LOC and slower runtime.

- Option 2 2: instantly updates the budget's monthly spending when user `add`, `delete` or `edit` (recurring)
  entries.
    - Pros: Faster runtime and more responsive warning messages (such as when user `edit` the `Entry` to an overspent
      budget.)
    - Cons: massive coupling of components.

We picked option 1 as it was in line with our goal of making our code OOP with separate components. Initially, we
pursued option 2 and found it hard to add functionalities and do unit testing due to the coupling available. Option 2
also meant to make the live updating work, we had to deal with 7 commands: `add`, `addR`, `delete`, `deleteR`
, `deleteAll`, `edit` and `editR`. Hence, we went with Option 1 due to scalability.

{NOT DONE}

## <a name="scope"></a>Product scope

### <a name="target"></a>Target user profile

- has a need to track their expenses and savings
- prefer desktop apps over other types
- can type fast
- prefers typing to mouse interactions
- is reasonably comfortable using CLI apps

### <a name="value"></a>Value proposition

A smart and simple way to keep track of your expenses

## <a name="stories"></a>User Stories

|Version| As a ... | I want to ... | So that I can ...|
|--------|----------|---------------|------------------|
|v1.0|new user|see usage instructions|refer to them when I forget how to use the application|
|v1.0|user|add expense|
|v1.0|user|delete expense|remove entries that I no longer need|
|v1.0|user|view past expenses|keep track of my spending|
|v1.0|user|edit past expenses|avoid deleting the entire expense if I only made a small error while keying it in|
|v2.0|user|categorize expenses|see which areas I am overspending|
|v2.0|user|set spending limit|cut down on unnecessary spending|
|v2.0|user|add income|better estimate my spending capacity|
|v2.0|user|sort spending by amount|see which expenditure is taking up the budget|
|v2.0|user|find a particular entry by name, amount or any detail|locate an Entry without having to go through the entire list|
|v2.0|user|add subscriptions/incomes|keep track of them and automatically take them into account every month or year|
|v2.0|user|view total money I have |see if I have saved enough for the future.|
|v2.0|user|filter entries by date or type|see cash flow in a particular period|

## <a name="nf-req"></a>Non-Functional Requirements

- Should work on any [*mainstream Operating Systems*](#os) as long as Java `11` or higher has been installed on it.
- Each command (Add, delete, edit, view) should be executed within 1 second of the user input.
- Users can use the application throughout the week at any time during the day. In the case of unexpected downtime, all
  features will be available again within 24 hours.
- If the commands have errors, they can be fixed within 24 hours.
- The application’s interface has to be intuitive and easy to use.

## <a name="glossary"></a>Glossary

* <a name="os"></a>**Mainstream Operating Systems** - Windows, macOS, *NIX
* <a name="local-storage"></a>**LocalStorage** - Refers to user's hard disk storage

## <a name="manual-test"></a>Instructions for manual testing

> We define an `Entry` as an expense/income. </br>
> Tags in square brackets are optional
> e.g., `n/NAME [d/DATE]` can be used as `n/burger d/2021-10-20` or as `n/burger`

### <a name="Adding"></a>Adding an Entry

**Prerequisites**

- The list must have been initialized.

**Test case 1: Adding an `Entry` with all fields specified.**

**Usage:**

- Add an Expense: `add a/AMOUNT n/DESCRIPTION [d/DATE] [c/CATEGORY]`
- Add an Income: `add income a/AMOUNT n/DESCRIPTION [d/DATE] [c/CATEGORY]`
- Some fields such as `n/DESCRIPTION` and `a/AMOUNT` must be specified. If the user prefers, additional fields can be
  added for greater specificity. The fields can be specified in any order.

**Expected**

- Program would print a message to notify the user that the `Entry` has been added.
- An `Entry` would then be added to the list.
- Optional fields that are missing would be set to the default pre-determined by the programme.

**[EXPENSE] Example of usage and expected output:**

```
add a/15 d/2021-12-03 n/Textbook c/7
I've added: Expense  | OTHERS | 2021-12-03 | Textbook | $15.00
```

**[INCOME] Example of usage and expected output:**

```
add income a/15 d/2021-12-03 n/Selling Textbooks c/7
I've added: Income  | OTHERS | 2021-12-03 | Selling Textbooks | $15.00
```

**Test case 2: Adding an `Entry` with some fields specified.**

**Usage:**

- Add an Expense: `add a/AMOUNT n/DESCRIPTION [d/DATE] [c/CATEGORY]`
- Add an Income: `add income a/AMOUNT n/DESCRIPTION [d/DATE] [c/CATEGORY]`
- Some fields such as `n/DESCRIPTION` and ` a/AMOUNT` must be specified. If the user prefers, additional fields can be
  added for greater specificity. The fields can be specified in any order.

**Expected**

- Program would print a message to notify the user that the `Entry` has been added.
- An `Entry` would then be added to the list.
- Optional fields that are missing would be set to the default pre-determined by the programme.

**[EXPENSE] Example of usage and expected output:**

```
add a/15 d/2021-12-03 n/Textbook
I've added: Expense  | OTHERS | 2021-12-03 | Textbook | $15.00
```

```
add a/5 n/Chicken Rice c/0
I've added: Expense  | FOOD | 2021-11-07 | Chicken Rice | $5.00
```

```
add n/Cheese Burger a/23.5
I've added: Expense  | OTHERS | 2021-11-07 | Cheese Burger | $23.50
```

**[INCOME] Example of usage and expected output:**

```
add income a/15 d/2021-12-03 n/Selling Textbooks
I've added: Income  | OTHERS | 2021-12-03 | Selling Textbooks | $15.00
```

```
add income a/5 n/Selling Textbooks c/0
I've added: Income  | ALLOWANCE | 2021-11-07 | Selling Textbooks | $5.00
```

```
add income n/Selling Textbooks a/23.5
I've added: Income  | OTHERS | 2021-11-07 | Selling Textbooks | $23.50
```

### <a name="delete"></a>Deleting an Entry

**Prerequisites**

- The list must have entries that have already been added.

**Test case 1: Deleting an existing `Entry` with some fields specified.**

**Usage:**

- Delete an Expense or Income: `delete [n/NAME] [a/AMOUNT] [d/DATE] [c/CATEGORY]`
- At least one field must be specified. If the user prefers, additional tags can be added for greater specificity. The
  fields can be specified in any order.
- Dummy strings between `delete` and the first tag will not affect the program.
- We can delete both the expense and income using the same command `delete`

**Expected**

- If there is one `Entry` that matches the query, it asks user if the user wants to delete the found Entry.
    - When user inputs `y`, it would delete the `Entry`.
- If there are multiple entries that match the query, it asks the user to choose the index of Entry that the user wants
  to delete from a given list.
    - When user inputs a valid index, it would delete the `Entry`.

**[EXPENSE and INCOME] Example of usage and expected output:**

```
delete n/Movie c/1
Is this what you want to delete?
    Expense  | ENTERTAINMENT | 2021-12-03 | Movie | $20.00
Type "y" if yes. Type "n" if not.
y
I have deleted: Expense  | ENTERTAINMENT | 2021-12-03 | Movie | $20.00
```

```
delete hahaha n/mas
Here is the list of items containing the keyword.
 Index |   Type  | Category |    Date    |        Name         | Amount  | Every |   Until
   1   | Income  |   GIFT   | 2021-12-25 | Christmas allowance | $200.00 |       |
   2   | Expense |  OTHERS  | 2021-11-07 |       Massage       |-$50.00  |       |
Enter the index of the item you want to delete. To cancel, type "cancel"
2
I have deleted: Expense  | OTHERS | 2021-11-07 | Massage | $50.00
```

**Test case 2: Choosing not to delete an existing `Entry` after entering the delete command.**

**Usage:**

- Delete an Expense or Income: `delete [n/NAME] [a/AMOUNT] [d/DATE] [c/CATEGORY]`
- At least one field must be specified. If the user prefers, additional tags can be added for greater specificity. The
  fields can be specified in any order.
- Dummy strings between `delete` and the first tag will not affect the program.

**Expected**

- If there is one `Entry` that matches the query, it asks user if the user wants to delete the found `Entry`.
    - When user inputs `n`, it exits the delete process.
- If there are multiple entries that match the query, it asks the user to choose the index of `Entry` that the user
  wants to delete from a given list.
    - When user inputs `cancel`, it exits the delete process.

**[EXPENSE and INCOME] Example of usage and expected output:**

```
delete n/taxi d/2021-11-04
Is this what you want to delete?
    Expense  | TRANSPORTATION | 2021-11-04 | Taxi | $6.99
Type "y" if yes. Type "n" if not.
n
Ok. I have cancelled the process.
```

```
delete d/2021-11-04
Here is the list of items containing the keyword.
 Index |   Type  |  Category  |    Date    |  Name  | Amount | Every |   Until
   1   | Income  | INVESTMENT | 2021-11-04 |  Taxi  | $6.99  |       |
   2   | Expense |    FOOD    | 2021-11-04 | Burger |-$7.12  |       |
Enter the index of the item you want to delete. To cancel, type "cancel"
cancel
Ok. I have cancelled the process.
```

**Test case 3: Trying to delete an `Entry` that does not exist.**

**Usage:**

- Query tags such that there are no entries that match all the specified fields.
- Format: `deleteR [n/NAME] [a/AMOUNT] [d/DATE] [c/CATEGORY]`
- At least one field must be specified. If the user prefers, additional tags can be added for greater specificity. The
  fields can be specified in any order.
- Dummy strings between `delete` and the first tag will not affect the program.
- For the delete function, we can delete both the expense and income using the same command `delete`

**Expected**

- It tells that the `Entry` is not in list.

**[EXPENSE and INCOME] Example of usage and expected output:**

```
delete n/dryer a/382 c/1
Hmm.. That item is not in the list.
```

```
delete n/yoghurt
Hmm.. That item is not in the list.
```

### <a name="edit"></a>Editing an Entry

**Prerequisites**

- The list must have entries that have already been added.
- At least one field must be specified. If the user prefers, additional tags can be added for greater specificity. The
  fields can be specified in any order.

**Test case 1: Editing all fields.**

**Usage:**

- Editing an Expense or Income: `edit [n/NAME] [a/AMOUNT] [d/DATE] [c/CATEGORY]`
- At least one field must be specified. If the user prefers, additional tags can be added for greater specificity. The
  fields can be specified in any order.

**CAUTION**

- Do not edit the same field multiple times in one command.

**Expected**

- The user would be prompted to choose their `Entry` to edit if there are multiple entries or confirm their edit.
- The input fields of the selected `Entry` are updated and there would be a message printed to notify the users that the
  changes have been made.

**[EXPENSE] Example of usage and expected output:**

```
edit a/20 d/2021-12-03 n/Movie c/1
Is this what you want to edit?
    Expense  | ENTERTAINMENT | 2021-12-03 | Movie | $20.00
Type "y" if yes. Type "n" if not.
y
What would you like to edit? Type the tag and what you want to change e.g. a/10
a/8 n/Chicken Rice c/0 d/2000-09-22
Got it! I will update the fields accordingly!
```

**[INCOME] Example of usage and expected output:**

```
edit income a/20 d/2021-12-03 n/Full-time job c/1
Is this what you want to edit?
    Income  | WAGES | 2021-12-03 | Full-time job | $20.00
Type "y" if yes. Type "n" if not.
y
What would you like to edit? Type the tag and what you want to change e.g. a/10
n/part-time job
Got it! I will update the fields accordingly!
```

**Test case 2: Editing some fields.**

**Usage:**

- Editing an Expense or Income: `edit [n/NAME] [a/AMOUNT] [d/DATE] [c/CATEGORY]`
- At least one field must be specified. If the user prefers, additional tags can be added for greater specificity. The
  fields can be specified in any order.

**CAUTION**

- Do not edit the same field multiple times in one command.

**Expected**

- The user would be prompted to choose their `Entry` to edit if there are multiple entries or confirm their edit.
- The input fields of the selected `Entry` are updated and there would be a message printed to notify the users that the
  changes have been made.

**[EXPENSE] Example of usage and expected output:**

```
edit a/20 d/2021-12-03 n/Movie c/2
Is this what you want to edit?
    Expense  | TRANSPORTATION | 2021-12-03 | Movie | $20.00
Type "y" if yes. Type "n" if not.
y
What would you like to edit? Type the tag and what you want to change e.g. a/10
a/8 c/0
Got it! I will update the fields accordingly!
```

**[INCOME] Example of usage and expected output:**

```
edit income n/Full-time job
Is this what you want to edit?
    Income  | OTHERS | 2021-11-07 | Full-time job | $100.00
Type "y" if yes. Type "n" if not.
y
What would you like to edit? Type the tag and what you want to change e.g. a/10
n/Part-time job
Got it! I will update the fields accordingly!
```

### <a name="Add-recurring-entry"></a>Adding a Recurring Entry

**Prerequisites**

- The list must have been initialized.

**Test case 1: Adding a recurring `Entry` with all fields specified.**

**Usage:**

- Adding a Recurring Expense:`addR n/NAME a/AMOUNT i/INTERVAL [d/DATE] [e/END_DATE] [c/CATEGORY]`
- Adding a Recurring Income:`addR income n/NAME a/AMOUNT i/INTERVAL [d/DATE] [e/END_DATE] [c/CATEGORY]`

**Expected**

- Program would print a message to notify the user that the `Entry` has been added.
- An `Entry` would then be added to the list.

**[EXPENSE] Example of usage and expected output:**

```
addR a/90 d/2021-12-03 n/phone bills c/3 i/yeAR e/2023-04-15
I've added: Expense | HOUSEHOLD | 2021-12-03 | phone bills |-$90.00 | YEAR | 2023-04-15
```

**[INCOME] Example of usage and expected output:**

```
addR income a/20 d/2021-12-03 n/Full-time job c/1 i/MONTH e/2022-04-15
I've added: Income  | WAGES | 2021-12-03 | Full-time job | $20.00 | MONTH | 2022-04-15
```

**Test case 2: Adding a recurring `Entry` with some fields specified.**

**Usage:**

- Adding a Recurring Expense:`addR n/NAME a/AMOUNT i/INTERVAL [d/DATE] [e/END_DATE] [c/CATEGORY]`
- Adding a Recurring Income:`addR income n/NAME a/AMOUNT i/INTERVAL [d/DATE] [e/END_DATE] [c/CATEGORY]`
- Some fields such as `n/NAME`, `a/AMOUNT` and `i/INTERVAL` must be specified. If the user prefers, additional fields
  can be added for greater specificity. The fields can be specified in any order.

**Expected**

- Program would print a message to notify the user that the `Entry` has been added.
- An `Entry` would then be added to the list.
- Optional fields that are missing would be set to the default pre-determined by the program.

For these examples, assume today's date is `2021-11-07` </br>
**[EXPENSE] Example of usage and expected output:**

```
addR a/5 n/phone bills i/MOnth
I've added: Expense | OTHERS | 2021-11-07 | phone bills |-$5.00 | MONTH | Forever :D
```

**[INCOME] Example of usage and expected output:**

```
addR income a/90 n/Full-time job i/MONTH e/2023-12-23
I've added: Income  | OTHERS | 2021-11-07 | Full-time job | $90.00 | MONTH | 2023-12-23
```

### <a name="Delete-recurring-entry"></a>Deleting a Recurring Entry

**Test case 1: Deleting an existing recurring `Entry` with some fields specified.**

**Usage:**

- Delete a Recurring Expense or Recurring
  Income: `deleteR [n/NAME] [a/AMOUNT] [d/DATE] [c/CATEGORY] [e/END_DATE] [i/INTERVAL] `
- At least one field must be specified. If the user prefers, additional tags can be added for greater specificity. The
  fields can be specified in any order.
- Dummy strings between `deleteR` and the first tag will not affect the program.
- We can delete both the recurring expense and recurring income using the same command `deleteR`

**Expected**

- If there is one `Entry` that matches the query, it asks user if the user wants to delete the found Entry.
    - When user inputs `y`, it would delete the `Entry`.
- If there are multiple entries that match the query, it asks the user to choose the index of Entry that the user wants
  to delete from a given list.
    - When user inputs a valid index, it would delete the `Entry`.

**[EXPENSE and INCOME] Example of usage and expected output:**

```
deleteR n/netflix a/26
Is this what you want to delete?
    Expense | ENTERTAINMENT | 2021-12-03 | Netflix |-$26.00 | MONTH | 2023-04-15
Type "y" if yes. Type "n" if not.
y
I have deleted: Expense | ENTERTAINMENT | 2021-12-03 | Netflix |-$26.00 | MONTH | 2023-04-15

```

```
deleteR d/2021-12-03
Here is the list of items containing the keyword.
 Index |   Type  |   Category    |    Date    |    Name     | Amount | Every |   Until
   1   | Expense |   HOUSEHOLD   | 2021-12-03 | phone bills |-$90.00 | MONTH | 2023-04-15
   2   | Expense | ENTERTAINMENT | 2021-12-03 |   Netflix   |-$26.00 | MONTH | 2023-04-15
Enter the index of the item you want to delete. To cancel, type "cancel"
1
I have deleted: Expense | HOUSEHOLD | 2021-12-03 | phone bills |-$90.00 | MONTH | 2023-04-15
```

**Test case 2: Choosing not to delete an existing recurring `Entry` after entering the deleteR command.**

**Usage:**

- Delete a Recurring Expense or Recurring
  Income: `deleteR [n/NAME] [a/AMOUNT] [d/DATE] [c/CATEGORY] [e/END_DATE] [i/INTERVAL] `
- At least one field must be specified. If the user prefers, additional tags can be added for greater specificity. The
  fields can be specified in any order.
- Dummy strings between `deleteR` and the first tag will not affect the program.
- For the delete function, we can delete both the expense and income using the same command `deleteR`

**Expected**

- If there is one `Entry` that matches the query, it asks user if the user wants to delete the found `Entry`.
    - When user inputs `n`, it exits the delete process.
- If there are multiple entries that match the query, it asks the user to choose the index of `Entry` that the user
  wants to delete from a given list.
    - When user inputs `cancel`, it exits the delete process.

**[EXPENSE and INCOME] Example of usage and expected output:**

```
deleteR n/net
Is this what you want to delete?
    Income | ENTERTAINMENT | 2021-12-03 | Netflix refund | $26.00 | MONTH | 2023-04-15
Type "y" if yes. Type "n" if not.
n
Ok. I have cancelled the process.
```

```
deleteR c/3
Here is the list of items containing the keyword.
 Index |   Type  | Category  |    Date    |    Name     | Amount | Every |   Until
   1   | Expense | HOUSEHOLD | 2021-12-03 | phone bills |-$90.00 | MONTH | 2023-04-15
   2   | Expense | HOUSEHOLD | 2021-11-03 |    dryer    |-$30.00 | MONTH | 2023-04-15
Enter the index of the item you want to delete. To cancel, type "cancel"
cancel
Ok. I have cancelled the process.
```

**Test case 3: Trying to delete a recurring `Entry` that does not exist.**

**Usage:**

- Query tags such that there are no entries that match all the specified fields.
- Format: `deleteR [n/NAME] [a/AMOUNT] [d/DATE] [c/CATEGORY] [e/END_DATE] [i/INTERVAL]`
- At least one field must be specified. If the user prefers, additional tags can be added for greater specificity. The
  fields can be specified in any order.
- Dummy strings between `deleteR` and the first tag will not affect the program.
- For the delete function, we can delete both the expense and income using the same command `deleteR`

**Expected**

- It tells that the `Entry` is not in list.

**[EXPENSE and INCOME] Example of usage and expected output:**

```
deleteR n/dryer a/382 c/1
Hmm.. That item is not in the list.
```

```
deleteR n/yoghurt i/month
Hmm.. That item is not in the list.
```

### <a name="Edit-recurring-entry"></a>Editing a recurring Entry

**Prerequisites**

- The list must have expenses that have already been added.
- At least one field must be specified. If the user prefers, additional tags can be added for greater specificity. The
  fields can be specified in any order.

**Test case 1: Editing an existing recurring `Entry` with all fields specified.**

**Usage:**

- Editing a Recurring Expense or Recurring
  Income: `editR [n/NAME] [a/AMOUNT] [d/DATE] [c/CATEGORY] [e/END_DATE] [i/INTERVAL]`
- At least one field must be specified. If the user prefers, additional tags can be added for greater specificity. The
  fields can be specified in any order.
- For the edit function, we can edit both the expense and income using the same command `editR`

**Expected**

- The user would be prompted to choose their `Entry` to edit if there are multiple entries or confirm their edit.
- The input fields of the selected `Entry` are updated and there would be a message printed to notify the users that the
  changes have been made.

**[EXPENSE and INCOME] Example of usage and expected output:**

```
editR a/15 n/phone bills d/2021-12-03 c/0 i/MONTH e/2023-10-10
Is this what you want to edit?
    Expense | FOOD | 2021-12-03 | phone bills |-$15.00 | MONTH | 2023-10-10
Type "y" if yes. Type "n" if not.
y
What would you like to edit? Type the tag and what you want to change e.g. a/10
a/40
Got it! I will update the fields accordingly!
```

```
editR a/20 d/2021-12-03 n/Full-time job c/1 i/MONTH e/2023-10-10
Here is the list of items containing the keyword.
 Index |   Type  |   Category    |    Date    |     Name      | Amount | Every |   Until
   1   | Expense | ENTERTAINMENT | 2021-12-03 | Full-time job |-$20.00 | MONTH | 2023-10-10
   2   | Income  |     WAGES     | 2021-12-03 | Full-time job | $20.00 | MONTH | 2023-10-10
Enter the index of the item you want to edit. To cancel, type "cancel"
1
What would you like to edit? Type the tag and what you want to change e.g. a/10
a/100
Got it! I will update the fields accordingly!
```

**Test case 2: Editing an existing recurring `Entry` with some fields specified.**

**Usage:**

- Editing a Recurring Expense or Recurring
  Income:`editR [n/NAME] [a/AMOUNT] [d/DATE] [c/CATEGORY] [e/END_DATE] [i/INTERVAL]`
- At least one field must be specified. If the user prefers, additional tags can be added for greater. specificity. The
  fields can be specified in any order.
- For the edit function, we can edit both the expense and income using the same command `editR`

**Expected**

- The user would be prompted to choose their `Entry` to edit if there are multiple entries or confirm their edit.
- The input fields of the selected entries are updated and there would be a message printed to notify the users that the
  changes have been made.

**[EXPENSE and INCOME] Example of usage and expected output:**

```
editR a/40 n/Netflix Subscription c/1 
Is this what you want to edit?
    Expense | ENTERTAINMENT | 2021-11-07 | Netflix Subscription |-$40.00 | MONTH | Forever :D
Type "y" if yes. Type "n" if not.
y
What would you like to edit? Type the tag and what you want to change e.g. a/10
a/20
Got it! I will update the fields accordingly!
```

```
editR n/Full-time job
Here is the list of items containing the keyword.
 Index |   Type  |   Category    |    Date    |     Name      | Amount  | Every |   Until
   1   | Income  |     WAGES     | 2021-12-03 | Full-time job | $20.00  | MONTH | 2022-04-15
   2   | Income  |    OTHERS     | 2021-11-07 | Full-time job | $90.00  | MONTH | 2023-12-23
   3   | Expense | ENTERTAINMENT | 2021-12-03 | Full-time job |-$100.00 | MONTH | 2023-10-10
   4   | Income  |     WAGES     | 2021-12-03 | Full-time job | $20.00  | MONTH | 2023-10-10
Enter the index of the item you want to edit. To cancel, type "cancel"
1
What would you like to edit? Type the tag and what you want to change e.g. a/10
n/part-time job
Got it! I will update the fields accordingly!
```

### <a name="View"></a>Viewing entries

**Prerequisites**

- The list must have been initialized.

**Test case 1: View with no fields specified.**

**Usage:**

- `view`
- Invalid inputs that are not any of the below modifiers will not affect the program.
    - `by`
    - `month`
    - `year`
    - `from`
    - `up/ascending`

**Expected**

- Program would print a message to notify the user the viewing options that have been selected.
- A table of entries that fulfill the viewing options will be printed out.

**Example of usage and expected output:**

```
view
Here is the list of your entries:
  Type  |  Category  |    Date    |  Name   | Amount | Every |   Until
Income  | INVESTMENT | 2021-10-27 |  Sales  | $32.00 |       |
Expense |   BEAUTY   | 2021-06-04 | Massage |-$15.00 | MONTH | 2021-07-02
Expense |   BEAUTY   | 2021-05-04 | Massage |-$15.00 | MONTH | 2021-07-02
Expense |   BEAUTY   | 2021-04-04 | Massage |-$15.00 | MONTH | 2021-07-02
Expense |    FOOD    | 2020-01-06 | Burger  |-$4.20  |       |
                                 Net Total: |-$17.20
Here is the list of all recurring entries, where some were added to the above list:
Expense |   BEAUTY   | 2021-04-04 | Massage |-$15.00 | MONTH | 2021-07-02
```

**Test case 2: View with some fields specified.**

**Usage:**

- `view income/expense by [Sort Type] month [month] year [year] from [Start Date] [End Date] up/ascending`
- Any number of fields can be specified. If the user prefers, additional modifiers can be added for greater specificity.
  The fields can be specified in any order other than `income/expense`, which has to be right after `view`.

**Expected**

- Program would print a message to notify the user the viewing options that have been selected.
- A table of entries that fulfill the viewing options will be printed out.
- Optional fields that are missing would be set to the default pre-determined by the programme.

**Example of usage and expected output:**

```
view income
Here is the list of your entries:
  Type  |  Category  |    Date    | Name  | Amount | Every |   Until
Income  | INVESTMENT | 2021-10-27 | Sales | $32.00 |       |
                               Net Total: | $32.00
Here is the list of applicable recurring entries, where some were added to the above list:
```

```
view month 4 year 2021
For the year 2021:
For the month of APRIL:
Here is the list of your entries:
  Type  | Category |    Date    |  Name   | Amount | Every |   Until
Expense |  BEAUTY  | 2021-04-04 | Massage |-$15.00 | MONTH | 2021-07-02
                               Net Total: |-$15.00
Here is the list of recurring entries added to the above list:
Expense |  BEAUTY  | 2021-04-04 | Massage |-$15.00 | MONTH | 2021-07-02
```

```
view from 2021-03-25 2021-11-02 by amount ascending
Here is the list of your entries:
Since 2021-03-25 to 2021-11-02:
  Type  |  Category  |    Date    |  Name   | Amount | Every |   Until
Expense |   BEAUTY   | 2021-04-04 | Massage |-$15.00 | MONTH | 2021-07-02
Expense |   BEAUTY   | 2021-05-04 | Massage |-$15.00 | MONTH | 2021-07-02
Expense |   BEAUTY   | 2021-06-04 | Massage |-$15.00 | MONTH | 2021-07-02
Income  | INVESTMENT | 2021-10-27 |  Sales  | $32.00 |       |
                                 Net Total: |-$13.00
Here is the list of recurring entries added to the above list:
Expense |   BEAUTY   | 2021-04-04 | Massage |-$15.00 | MONTH | 2021-07-02
```

### <a name="delete"></a>Deleting all Entries

**Prerequisites**

- The list must have items that have already been added.

**Test case 1: Deleting all existing entries.**

**Usage:**

- Delete all entries: `deleteAll`
- Invalid inputs that are not `n` , `r`, `normal` or `recurring` will not affect the program.

**Expected**

- It will ask user if the user wants to delete all entries in the list.
    - When user inputs `y`, it would delete all entries in the list.
    - When user inputs `n`, it will abort the deletion.

** Example of usage and expected output:**

```
deleteAll
Are you sure you want to delete all entries?
Type "y" if yes. Type "n" if not.
y
All entries successfully deleted.
```

**Test case 2:  Only deleting all normal entries or all recurring entries.**

**Usage:**

- Delete all expenses or all incomes: `deleteAll [normal] [recurring]`
- `normal` and `recurring` can be substituted with `n` and `r` respectively.
- Invalid inputs that are not `n` , `r`, `normal` or `recurring` will not affect the program.

**Expected**

- It will ask user if the user wants to delete all entries in the particular list.
    - When user inputs `y`, it would delete all entries in the list.
    - When user inputs `n`, it will abort the deletion.

**Example of usage and expected output:**

```
deleteAll normal
Are you sure you want to delete all entries?
Type "y" if yes. Type "n" if not.
y
All entries successfully deleted.
```

```
deleteAll r
Are you sure you want to delete all entries?
Type "y" if yes. Type "n" if not.
n
Delete aborted.
```

### <a name="Set-budget"></a>Setting budget

1. Setting a budget with valid fields
    - Test case: `set c/0 a/100`
    - Expected: A message to show that specified budget is successfully set to amount specified.

```
Budget for FOOD set to $100.00
```

2. Setting a budget with invalid category number
    - Test case: `set c/-1 a/100`.
    - Expected: An error message to remind users that category number ranges from `0` to `7`.

```
Please enter a valid category number! c/0 to c/7
```

3. Setting a budget and adding an expense that exceeds 80% of the budget set
    - Prerequisite: List of entries must be empty. You may use the `deleteAll` function.
    - Test case (2 steps process):
        - First, key in `set c/0 a/100`.
        - Then, key in `add n/haidilao c/0 a/80.01`
    - Expected: A message warning user to slow down their spending.

```
Budget for FOOD set to $100.00
```

```
I've added: Expense  | FOOD | 2021-11-07 | haidilao | $80.01
Slow down, you've set aside $100.00 for FOOD, but you already spent $80.01.
```

### <a name="View-budget"></a>Viewing budget

1. Set a budget and view the list of budgets
    - Prerequisite: List of entries must be empty. You may use the `deleteAll` function. Assume no budget set yet.
    - Test case (2 steps process):
        - Key in `set c/0 a/100`.
        - Then, key in `budget`.
    - Expected: A list of budgets will be printed.

```
Budget for FOOD set to $100.00
```

```
Here is the budget for NOVEMBER 2021
   Category    | Amount | Budget  | Percentage
     FOOD      |  $0.00 / $100.00 | 
ENTERTAINMENT  |  $0.00 / Not set | 
TRANSPORTATION |  $0.00 / Not set | 
  HOUSEHOLD    |  $0.00 / Not set | 
   APPAREL     |  $0.00 / Not set | 
    BEAUTY     |  $0.00 / Not set | 
     GIFT      |  $0.00 / Not set | 
    OTHERS     | $80.01 / Not set | 
```

2. Test to see whether budget only includes current month's expenditure
    - Prerequisites:
        - List of entries must be empty. You may use the `deleteAll` function.
        - Budget of "FOOD" is set to $100. (see previous test case).
    - Test case (3 steps process):
        - Key in `add n/current expense a/100`.
        - Then, key in `add n/old expense d/2020-01-01 a/200`.
        - Then, key in `budget` to view current month's spending and budget.
    - Expected: Only entries that took place in current month will be calculated in the monthly spending. "current
      expense" will be added into the caculations while "old expense" is not.

```
I've added: Expense | OTHERS | 2021-11-07 | current expense | $100.00
```

```
I've added: Expense | OTHERS | 2020-01-01 | old expense | $200.00
```

```
Here is the budget for NOVEMBER 2021
   Category    |  Amount | Budget  | Percentage
     FOOD      |   $0.00 / $100.00 | 
ENTERTAINMENT  |   $0.00 / Not set | 
TRANSPORTATION |   $0.00 / Not set | 
  HOUSEHOLD    |   $0.00 / Not set | 
   APPAREL     |   $0.00 / Not set | 
    BEAUTY     |   $0.00 / Not set | 
     GIFT      |   $0.00 / Not set | 
    OTHERS     | $100.00 / Not set | 
```
