# Developer Guide

### Table of Content

- [Acknowledgements](#acknowledgements)
- [Design](#design)
    - [System Architecture](#sys-arch)
    - [Ui](#text-ui)
    - [Parser](#main-parser)
    - [Command](#command)
    - [ContactList](#contact-list)
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

{add image}

The above **System Architecture** diagram shows the high-level design of Mint.

On launch, the `Main` class initialises the app components in the correct sequence and links them up with each other, in
the correct sequence.

Mint comprises five main components, namely:

- `Ui`: Command Line User Interface of Mint.
- `Parser`: Parser to parser user inputs from `Ui` for `ExpenseList`.
- `ExpenseList`: Data structure to store `Expense`s while running Mint.
- `CategoryList`: Data structure to store `Category`s while running Mint.
- `DataManager`: Reads from and writes to [`LocalStorage`](#local-storage).

The four main components interact with each other, as shown in the sequence diagram below. { NEED SEQUENCE DIAGRAM }

### <a name="text-ui"></a>Ui

### <a name="main-parser"></a>Parser

### <a name="contact-list"></a>ExpenseList

### <a name="storage"></a>DataManager

## <a name="implementation"></a>Implementation

{Describe the design and implementation of the product. Use UML diagrams and short code snippets where applicable.}

{NOT DONE}

## <a name="scope"></a>Product scope

### <a name="target"></a>Target user profile

- sampletext

### <a name="value"></a>Value proposition

Sampletext

## <a name="stories"></a>User Stories

{UNCOMPLETED}

|Version| As a ... | I want to ... | So that I can ...|
|--------|----------|---------------|------------------|
|v1.0|new user|see usage instructions|refer to them when I forget how to use the application|
|v1.0|user|add expense
|v1.0|user|delete expense|remove entries that I no longer need
|v1.0|user|view past expenses|keep track of my spending|
|v2.0|user|set spending limit|cut down on unnecessary spending|
|v2.0|user|sort spending by amount|see which expenditure is taking up the budget
|v2.0|user|find a to-do item by name|locate a to-do without having to go through the entire list|

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

### :writing_hand: <a name="edit"></a>Editing an expense

**Prerequisites**

- The list must have expenses that have already been added.

**Test case 1: Editing all fields**

**Usage:**

- `edit [include all fields of expense you would like to edit]`
- `a/[amount] n/[description] c/[catNum] d/[validDate]`

**Expected**

- The input fields of the selected entry are updated

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
**Test case 2: Editing some fields**

**Usage:**

- `edit [include all fields of expense you would like to edit]`
- Include the tags and things you would like to change in any order `tag/[input]`

:bomb: **CAUTION**
- Do not edit the same field multiple times in one command.

**Expected**

- The input fields of the selected entry are updated

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



### :x: <a name="delete"></a>Deleting an expense

**Prerequisites**

- The list must have expenses that have already been added.

**Test case 1: Deleting an existing expense with some fields specified. Only one expense
exists that matches all the fields specified.**

**Usage:**

- `delete [include some fields of the expense you would like to delete]`

**Expected**

- It asks user if the user wants to delete the found expense.
- If user inputs y, it deletes. If user inputs n, it exits the delete process.

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