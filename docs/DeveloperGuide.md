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

{list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the original source as well}

## <a name="design"></a>Design

### <a name="sys-arch"></a>System Architecture

{add image}

The above **System Architecture** diagram shows the high-level design of Mint.

On launch, the `Main` class initialises the app components in the correct sequence and links them up
with each other, in the correct sequence.

ConTech comprises four main components, namely:
- `Ui`: Command Line User Interface of Mint.
- `Parser`: Parser to parser user inputs from `Ui` for `ExpenseList`.
- `ExpenseList`: Data structure to store `Expense`s while running Mint.
- `DataManager`: Reads from and writes to [`LocalStorage`](#local-storage).

The four main components interact with each other, as shown in the sequence diagram below.
{ NEED SEQUENCE DIAGRAM }

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

{NOT DONE}

|Version| As a ... | I want to ... | So that I can ...|
|--------|----------|---------------|------------------|
|v1.0|new user|see usage instructions|refer to them when I forget how to use the application|
|v2.0|user|find a to-do item by name|locate a to-do without having to go through the entire list|

## <a name="nf-req"></a>Non-Functional Requirements

- Should work on any [*mainstream Operating Systems*](#os) as long as Java `11` or higher has been installed on it
  {Give non-functional requirements}

## <a name="glossary"></a>Glossary

* <a name="os"></a>**Mainstream Operating Systems** - Windows, macOS, *NIX
* <a name="local-storage"></a>**LocalStorage** - Refers to user's hard disk storage

## <a name="manual-test"></a>Instructions for manual testing

{Give instructions on how to do a manual product testing e.g., how to load sample data to be used for testing}