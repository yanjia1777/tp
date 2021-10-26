# Developer Guide

### Table of Content

- [Acknowledgements](#acknowledgements)
- [Design](#design)
    - [System Architecture](#sys-arch)
    - [Ui](#text-ui)
    - [Logic](#logic)
      - [Command](#command)
      - [Parser](#parser)
    - [Model](#model)
      - [Finance](#finance)
      - [Budget](#budget)
    - [Storage](#storage)
- [Implementation](#implementation)
- [Product Scope](#scope)
    - [Target user profile](#target)
    - [Value proposition](#value)
- [User Stories](#stories)
- [Non-Function Requirements](#nf-req)
- [Glossary](#glossary)
- [Instructions for manual testing](#manual-test)

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
- `Model`: Holds the data of the App
- `DataManager`: Reads from and writes to [`LocalStorage`](#local-storage).

The components interact with each other, as shown in the sequence diagram below.

![](images/ArchitectureSequenceDiagram.png)

### <a name="text-ui"></a>Ui

### <a name="logic"></a>Logic
![](images/Logic.png)
### <a name="model"></a>Model
##### <a name="finance"></a>Finance
![](images/Finance.png)
#### <a name="budget"></a>Budget
![](images/Budget.png)

The `Budget` package consists of a `BudgetManager` and the `Budget`'s each of the seven `ExpenseCategory`'s.
- `Budget` is an abstract class
- `XYZBudget`(`XYZ` is a placeholder for the specified budget e.g., `FoodBudget`), inherits `Budget` and its attributes.
- `BudgetManager` stores a list of the seven `ExpenseCategory`'s.
- Currently, `BudgetManager` only interacts in 2 ways
    - when user want to set budget for a specific category
    - when other parts of the app requires the list of budgets e.g., `Ui` needs the list to print to the user, or `FinanceManager` needs to know if the user is nearing their spending limit to notify them.

### <a name="contact-list"></a>ExpenseList

### <a name="storage"></a>DataManager

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

1. After the command is extracted from the `Parser`, `Duke` would call the respective command class. In the case of
   the add function, the `AddCommand` class would be called.
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

{Describe the design and implementation of the product. Use UML diagrams and short code snippets where applicable.}

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
|v2.0|user|set spending limit|cut down on unnecessary spending|
|v2.0|user|sort spending by amount|see which expenditure is taking up the budget|
|v2.0|user|find a particular Expense by name|locate an Expense without having to go through the entire list|
|v2.0|user|add recurring expenses|keep track of them and take them into account every month|
|v2.0|user|add income|better estimate my spending capacity|

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

### :heavy_plus_sign: <a name="Adding"></a>Adding an expense

**Prerequisites**

- The list must have been initialized.
  <br/>
  
**Test case 1: Adding an expense with all fields specified.**

**Usage:**

- `add a/[amount] n/[description] d/[date] c/[categoryNumber]` 
- Some fields such as `n/[description] a/[amount]` must be specified. If the user prefers,
  additional fields can be added for greater specificity. The fields can be specified in any order.

**Expected**

- Program would print a message to notify the user that the item has been added.
- An expense would then be added to the list.
- Optional fields that are missing would be set to the default pre-determined by the program.

**Example of usage and expected output:**

```
add a/15 d/2021-12-03 n/Textbook c/7
--------------------------------------------------------------------
I've added: Expense  | OTHERS | 2021-12-03 | Textbook | $15.00
```
<br/>

**Test case 2: Adding an expense with some fields specified.**

**Usage:**

- `add [include some fields of the expense you would like to add]`
- Some fields such as `n/[description] a/[amount]` must be specified. If the user prefers,
  additional fields can be added for greater specificity. The fields can be specified in any order.

**Expected**

- Program would print a message to notify the user that the item has been added.
- An expense would then be added to the list.
- Optional fields that are missing would be set to the default pre-determined by the programme.

**Example of usage and expected output:**

```
add a/15 d/2021-12-03 n/Textbook
--------------------------------------------------------------------
I've added: Expense  | OTHERS | 2021-12-03 | Textbook | $15.00
```
```
add a/5 n/Chicken Rice c/0
--------------------------------------------------------------------
I've added: Expense  | FOOD | 2021-10-27 | Chicken Rice | $5.00
```
```
add n/Cheese Burger a/23.5
--------------------------------------------------------------------
I've added: Expense  | OTHERS | 2021-10-27 | Cheese Burger | $23.50
```

### :x: <a name="delete"></a>Deleting an expense

**Prerequisites**

- The list must have expenses that have already been added.

**Test case 1: Deleting an existing expense with some fields specified. Only one expense
exists that matches all the fields specified.**

**Usage:**

- `delete [include some fields of the expense you would like to delete]`
- At least one field must be specified. If the user prefers, additional tags can be added for greater
  specificity. The fields can be specified in any order.

**Expected**

- It asks user if the user wants to delete the found expense.
- When user inputs y, it would delete the item.

**Example of usage and expected output:**

```
delete n/Movie c/7
--------------------------------------------------------------------
Is this what you want to delete?
    Others | 2021-10-19 | Movie | $12.00
Type "y" if yes. Type "n" if not.
--------------------------------------------------------------------
y
--------------------------------------------------------------------
I have deleted: Others | 2021-10-19 | Movie | $12.00
```
**Test case 2: Choosing not to delete an existing expense after entering the delete command.**

**Usage:**

- `delete [include some fields of the expense you would like to delete]` 
- At least one field must be specified. If the user prefers, additional tags can be added for greater
  specificity. The fields can be specified in any order.

**Expected**

- It asks user if the user wants to delete the found expense.
- When user inputs n, it exits the delete process.

**Example of usage and expected output:**

```
delete a/90 d/2021-12-03 n/phone bills c/3 
--------------------------------------------------------------------
Is this what you want to delete?
    Expense |    HOUSEHOLD     | 2021-12-03 |   phone bills    |-$90.00
Type "y" if yes. Type "n" if not.
--------------------------------------------------------------------
n
--------------------------------------------------------------------
Ok. I have cancelled the process.
--------------------------------------------------------------------
n
--------------------------------------------------------------------
Ok. I have cancelled the process.
```

### :writing_hand: <a name="edit"></a>Editing an expense

**Prerequisites**

- The list must have expenses that have already been added.
- At least the `n/[description]` must be specified. If the user prefers, additional tags can be added for greater 
  specificity. The fields can be specified in any order.

**Test case 1: Editing all fields.**

**Usage:**

- `edit [include all fields of expense you would like to edit]`
- At least one field must be specified. If the user prefers, additional tags can be added for greater
  specificity. The fields can be specified in any order.
  
:bomb: **CAUTION**
- Do not edit the same field multiple times in one command.

**Expected**

- The user would be prompted to choose their entry to edit if there are multiple entries or confirm their edit.
- The input fields of the selected entry are updated and there would be a message printed to notify the users that
  the changes have been made.

**Example of usage and expected output:**

```
edit a/20 d/2021-12-03 n/Movie c/2
--------------------------------------------------------------------
What would you like to edit?
--------------------------------------------------------------------
a/8 n/Chicken Rice c/0 d/2000-09-22
--------------------------------------------------------------------
Got it! I will update the fields accordingly!
```
**Test case 2: Editing some fields.**

**Usage:**

- `edit [include all fields of expense you would like to edit]`
- At least one field must be specified. If the user prefers, additional tags can be added for greater
  specificity. The fields can be specified in any order.

:bomb: **CAUTION**
- Do not edit the same field multiple times in one command.

**Expected**

- The user would be prompted to choose their entry to edit if there are multiple entries or confirm their edit.
- The input fields of the selected entry are updated and there would be a message printed to notify the users that
  the changes have been made.

**Example of usage and expected output:**

```
edit a/20 d/2021-12-03 n/Movie c/2
--------------------------------------------------------------------
What would you like to edit?
--------------------------------------------------------------------
a/8 c/0 
--------------------------------------------------------------------
Got it! I will update the fields accordingly!
```

### :alarm_clock: :heavy_plus_sign: <a name="Add recurring expense"></a>Adding a Recurring Expense

**Prerequisites**

- The list must have been initialized.

**Test case 1: Adding an existing recurring expense with all fields specified.**

**Usage:**

- `addR a/[amount] n/[description] c/[category] i/[interval] e/[end date] d/[start date]`
- Some fields such as `n/[description] a/[amount] i/[interval]` must be specified. If the user prefers,
  additional fields can be added for greater specificity. The fields can be specified in any order.

**Expected**

- Program would print a message to notify the user that the item has been added.
- An expense would then be added to the list.
- Optional fields that are missing would be set to the default pre-determined by the programme.

**Example of usage and expected output:**

```
addR a/90 d/2021-12-03 n/phone bills c/3 i/MONTH e/2023-04-15
--------------------------------------------------------------------
I've added: Expense | HOUSEHOLD | 2021-12-03 | phone bills |-$90.00 | MONTH | 2023-04-15
```
**Test case 2: Adding an existing recurring expense with some fields specified.**

**Usage:**

- `addR [include some fields of the expense you would like to add as recurring expense]`

**Expected**

- Program would print a message to notify the user that the item has been added.
- An expense would then be added to the list.
- Optional fields that are missing would be set to the default pre-determined by the programme.

**Example of usage and expected output:**

```
addR a/90 d/2021-12-03 n/phone bills c/3 i/MONTH
--------------------------------------------------------------------
I've added: Expense | HOUSEHOLD | 2021-12-03 | phone bills |-$90.00 | MONTH | Forever :D
```
```
addR a/5 n/phone bills c/3 i/MONTH
--------------------------------------------------------------------
I've added: Expense | HOUSEHOLD| 2021-10-27 | phone bills |-$5.00 | MONTH | Forever :D
```
```
addR a/5 n/phone bills d/2021-10-10 i/MONTH
--------------------------------------------------------------------
I've added: Expense | OTHERS | 2021-10-10 | phone bills |-$5.00 | MONTH | Forever :D
```
### :alarm_clock: :x: <a name="Delete recurring expense"></a>Deleting a Recurring Expense

**Prerequisites**

- The list must have been initialized.

**Test case 1: Deleting an existing recurring expense with all fields specified.**

**Usage:**

- `deleteR a/[amount] n/[description] d/[date] c/[categoryNumber] i/[interval] e/[endDate]`
- At least one field must be specified. If the user prefers, additional tags can be added for greater.
  specificity. The fields can be specified in any order.
  
**Expected**

- It asks user if the user wants to delete the found expense.
- When user inputs y, it deletes the expense.

**Example of usage and expected output:**

```
deleteR a/90 d/2021-12-03 n/phone bills c/3 i/MONTH e/2023-04-15
--------------------------------------------------------------------
Is this what you want to delete?
    Expense | HOUSEHOLD | 2021-12-03 | phone bills |-$90.00 | MONTH | 2023-04-15
Type "y" if yes. Type "n" if not.
--------------------------------------------------------------------
y
--------------------------------------------------------------------
I have deleted: Expense | APPAREL | 2021-12-03 | phone bills |-$90.00 | MONTH | 2023-04-15
```
**Test case 2: Deleting an existing recurring expense with some fields specified.**

**Usage:**

- `deleteR [include some fields of the recurring expense you would like to delete]`
- At least one field must be specified. If the user prefers, additional tags can be added for greater
  specificity. The fields can be specified in any order.

**Expected**

- It asks user if the user wants to delete the found expense.
- When user inputs y, it deletes the expense.

**Example of usage and expected output:**

```
deleteR a/90 d/2021-12-03 n/phone bills c/3 i/MONTH
--------------------------------------------------------------------
Is this what you want to delete?
    Expense |    HOUSEHOLD     | 2021-12-03 |   phone bills    |-$90.00 | MONTH | Forever :D
Type "y" if yes. Type "n" if not.
--------------------------------------------------------------------
y
--------------------------------------------------------------------
I have deleted: Expense | HOUSEHOLD | 2021-12-03 | phone bills |-$90.00 | MONTH | Forever :D
```
```
deleteR a/5 n/phone bills c/4 i/MONTH
--------------------------------------------------------------------
Is this what you want to delete?
    Expense |     APPAREL      | 2021-10-26 |   phone bills    |-$5.00 | MONTH | Forever :D
Type "y" if yes. Type "n" if not.
--------------------------------------------------------------------
y
--------------------------------------------------------------------
I have deleted: Expense |     APPAREL      | 2021-10-26 |   phone bills    |-$5.00 | MONTH | Forever :D
```
```
deleteR a/5 n/phone bills d/2021-10-10 i/MONTH
--------------------------------------------------------------------
Is this what you want to delete?
    Expense |      OTHERS      | 2021-10-10 |   phone bills    |-$5.00 | MONTH | Forever :D
Type "y" if yes. Type "n" if not.
--------------------------------------------------------------------
y
--------------------------------------------------------------------
I have deleted: Expense |      OTHERS      | 2021-10-10 |   phone bills    |-$5.00 | MONTH | Forever :D
```
**Test case 3: Choosing not to delete an existing expense after entering the delete command.**

**Usage:**

- `deleteR [include some fields of the recurring expense you would like to delete]`
- At least one field must be specified. If the user prefers, additional tags can be added for greater
  specificity. The fields can be specified in any order.

**Expected**

- It asks user if the user wants to delete the found expense.
- When user inputs n, it exits the delete process.

**Example of usage and expected output:**

```
deleteR a/90 d/2021-12-03 n/phone bills c/3 i/MONTH
--------------------------------------------------------------------
Is this what you want to delete?
    Expense |    HOUSEHOLD     | 2021-12-03 |   phone bills    |-$90.00 | MONTH | Forever :D
Type "y" if yes. Type "n" if not.
--------------------------------------------------------------------
n
--------------------------------------------------------------------
Ok. I have cancelled the process.
```
### :alarm_clock: :writing_hand: <a name="Adding"></a>Editing a recurring expense

**Prerequisites**

- The list must have expenses that have already been added.
- At least one field must be specified. If the user prefers, additional tags can be added for greater
  specificity. The fields can be specified in any order.
  
**Test case 1: Editing an existing recurring expense with all fields specified.**

**Usage:**

- `editR a/[amount] n/[description] d/[date] c/[categoryNumber] i/[Interval] e/[end date]`
- At least one field must be specified. If the user prefers, additional tags can be added for greater
  specificity. The fields can be specified in any order.

**Expected**

- The user would be prompted to choose their entry to edit if there are multiple entries or confirm their edit.
- The input fields of the selected entry are updated and there would be a message printed to notify the users that
  the changes have been made.

**Example of usage and expected output:**

```
editR a/15 n/phone bills d/2021-12-03 c/0 i/MONTH e/2023-10-10
--------------------------------------------------------------------
Is this what you want to edit?
    Expense |       FOOD       | 2021-12-03 |   phone bills    |-$15.00 | MONTH | 2023-10-10
Type "y" if yes. Type "n" if not.
--------------------------------------------------------------------
y
--------------------------------------------------------------------
What would you like to edit?
--------------------------------------------------------------------
a/40
--------------------------------------------------------------------
Got it! I will update the fields accordingly!
```
**Test case 2: Editing an existing recurring expense with some fields specified.**

**Usage:**

- `editR [include some fields of the recurring expense you would like to edit]`
- At least one field must be specified. If the user prefers, additional tags can be added for greater.
  specificity. The fields can be specified in any order.

**Expected**

- The user would be prompted to choose their entry to edit if there are multiple entries or confirm their edit. 
- The input fields of the selected entry are updated and there would be a message printed to notify the users that
  the changes have been made.

**Example of usage and expected output:**

```
editR n/phone bills
--------------------------------------------------------------------
Here is the list of items containing the keyword.
    1  Expense |    HOUSEHOLD     | 2021-12-03 |   phone bills    |-$90.00 | MONTH | Forever :D
    2  Expense |       FOOD       | 2021-12-03 |   phone bills    |-$40.00 | MONTH | 2023-10-10
Enter the index of the item you want to edit. To cancel, type "cancel"
--------------------------------------------------------------------
2
--------------------------------------------------------------------
What would you like to edit?
--------------------------------------------------------------------
c/4
--------------------------------------------------------------------
Got it! I will update the fields accordingly!
```
```
editR a/40 n/Netflix Subscription c/1
--------------------------------------------------------------------
Is this what you want to edit?
    Expense |  ENTERTAINMENT   | 2021-10-26 | Netflix Subscription |-$40.00 | MONTH | 2023-04-10
Type "y" if yes. Type "n" if not.
--------------------------------------------------------------------
y
--------------------------------------------------------------------
What would you like to edit?
--------------------------------------------------------------------
a/20
--------------------------------------------------------------------
Got it! I will update the fields accordingly!
```