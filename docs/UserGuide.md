# User Guide v2.0

## Introduction

Mint is your smart money manager that help you track your daily expenses, set budgets
and long term financial goals(coming soon). 

It is optimized for use via a Command Line Interface (CLI). 

Using this guide, you will be able to navigate the app and use all of its functionalities through step-by-step-instructions.

##Table of Contents
- [Quick start](#quickStart)
    - [Setting Up](#settingUp)
    - [Running the Programme](#runningTheProgramme)
    - [[For users new to CLI] Changing the Directory](#changingTheDirectory)
- [Features](#features)
    - [Viewing help](#help)
    - [Adding entries](#add) 
    - [Adding recurring expenses](#addR) 
    - [Viewing entries](#view)
    - [Deleting entries](#delete)
    - [Editing entries](#edit)
    - [Viewing categories](#cat)
    - [Exiting the program](#exit)
- [Available date formats](#dateFormat)
- [List of categories](#categoryList)
- [Command Summary](#command-summary)

## <a name="quickStart"></a>Quick Start
> Before you get started, ensure that you have Java 11 or above installed in your Computer. Once that is done, follow
> the steps below!

### <a name="settingUp"></a>Setting Up

1. Download the latest version of tp.jar from [here](https://github.com/AY2122S1-CS2113T-W11-2/tp/releases/tag/v1.0).

2. Copy the tp.jar file to the folder you desire.

### <a name="runningTheProgramme"></a>Running the Programme
1. Open your desired Command Line Interface and ensure that you are in the directory where you saved the folder.
   If you are new to git, click [here](#changingTheDirectory) to see how you can change the directory.
2. Once you ensured you are in the correct directory, run the programme using the command `java -jar tp.jar`. 
3. To test if the programme is working, type a command and press Enter to execute it. 
   e.g., typing `help` and pressing Enter will display the list of commands to help you use our application. 

**Some example commands you can try:**

1. Add an expense to your list: `add a/13 d/2021-12-03 n/Movie ticket c/1` 
   > This command adds a Movie ticket that you have purchased for 13 dollars on December 3rd 2021 under the 
   > Entertainment category
   
2. Exit the programme: `exit`
   > This command terminates the program.

Refer to the [Features Section](#features) below for details of each comm

### <a name="[For users new to CLI] changingTheDirectory"></a>Changing the Directory to your tp.jar file
1. Right-click on your tp.jar file and select Properties. There would be a pop up with all the information.
2. Look for the Location and copy the entire string.
3. Go back to your Command Line Interface and enter the command `cd [paste what you copied here]`
4. Mint is now at your service!

## <a name="features"></a>Features

---

Notes about the following list of commands:

- Items in square brackets are optional.</br>
  e.g `n/NAME [d/DATE]` can be used as `n/burger d/2021-10-20` or as `n/burger`
- Parameters with tags or optional modifiers can be in any order.</br>
  e.g. if the command specifies `n/NAME` `a/AMOUNT`, `a/AMOUNT` `n/NAME` is also acceptable.
- If a parameter is expected only once in the command but if you specify it multiple times, only the last occurrence of
  the parameter will be taken.</br>
  e.g. if you specify `a/10 a/15`, only `a/10` will be taken.
- Extraneous parameters for commands that do not take in parameters (such as `help`, `budget` and `exit`)
  will be ignored.</br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

---

## <a name="dateFormat"></a>Acceptable date formats

Example: 5th Jaunary 2020

|Format | Example of input |
|--------|----------|
| yyyy-MM-dd | 2020-01-05|
| yyyy-M-dd  | 2020-1-05 | 
| yyyy-MM-d  | 2020-01-5 |
| yyyy-M-d | 2020-1-5 |
| dd-MM-yyyy | 05-01-2020 | 
| d-MM-yyyy | 5-01-2020 |
| d-M-yyyy | 5-1-2020 |
| dd-M-yyyy |05-1-2020 |
| dd MMM yyyy (for MMM, capitalise only </br> the first letter, e.g. Jan) | 05 Jan 2020 |
| d MMM yyyy (for MMM, capitalise only the first letter, e.g. Jan) | 5 Jan 2020 |

## <a name="categoryList"></a>Available categories

|Category tag | Category name |
|--------|----------|
| c/0 | Food |
| c/1 | Entertainment
| c/2| Transportation
| c/3 | Household
| c/4 | Apparel
| c/5 | Beauty
| c/6 | Gift
| c/7 | Others

## <a name="help"></a>Viewing help: `help`

Shows a list of possible user commands

Format: `help`

## <a name="add"></a>Adding entries: `add`

Adds an expense or income to your tracker

Format: `add [income] n/NAME a/AMOUNT [d/DATE] [c/CATEGORY_NUMBER]`

- Adds an entry of the specified `NAME`, `DATE`, `AMOUNT` and `CATEGORY_NUMBER`
- If `income` is included after `add`, entry will be an income entry, else it will be an expense entry.
- `NAME` can be any string of characters
- `AMOUNT` is any number up to 2 decimal points.
- `DATE(optional)` can be any of the [acceptable date formats](#dateFormat). </br>
  If the date is not specified, the default date set would be the date of expense entry.
- `CATEGORY_NUMBER(optional)` - Please refer to the [available categories](#categoryList). </br>
  If the `CATEGORY_NUMBER` is not specified, the default `CATEGORY_NUMBER` would be C/7 which is `others`.
  </br>
  
Examples:
- Adding a textbook that costs $15: `add n/textbook a/15`
- Adding a cheeseburger that costs $4.20 that I had on 20th April 2021 and categorize it under "Food": `add n/Cheese Burger a/4.2 d/2021-04-20 c/0`
- Adding the income I made from sales, amounting to $34 `add income n/Sales a/34 d/2021-02-19 c/1`

Examples and expected Output

```
add n/Textbook a/15
I've added: Expense |      OTHERS      | 2021-10-25 |     textbook     |-$15.00
add n/Cheese burger a/4.2 d/2021-04-20 c/0
I've added: Expense |       FOOD       | 2021-04-20 |    cheeseurger   |-$4.20
add income n/Sales a/34 d/2021-02-19 c/1
I've added :Income  |  ENTERTAINMENT   | 2021-02-19 |      Sales       | $34.00
```

## <a name="addR"></a>Adding Recurring Entries: `addR`

Adds an expense or income to your tracker

Format: `addR [income] n/NAME a/AMOUNT [d/DATE] [c/CATEGORY_NUMBER] i/INTERVAL [e/END_DATE]`

- Adds an entry of the specified `NAME`, `DATE`, `AMOUNT`, `INTERVAL`, `END_DATE` and `CATEGORY_NUMBER`
- If `income` is included after `add`, entry will be an income entry, else it will be an expense entry.
- `NAME` can be any string of characters
- `AMOUNT` is any number up to 2 decimal points.
- `DATE(optional)` can be any of the [acceptable date formats](#dateFormat). </br>
  If the date is not specified, the default date set would be the date of expense entry.
- `CATEGORY_NUMBER(optional)` - Please refer to the [available categories](#categoryList). </br>
  If the `CATEGORY_NUMBER` is not specified, the default `CATEGORY_NUMBER` would be C/7 which is `others`.
  </br>
- `INTERVAL` can be either `MONTH` or `YEAR` depending on how often one receives the `INCOME` or has to pay for the
    expenditure.
- `END_DATE` can be any of the [acceptable date formats](#dateFormat). </br>
   If the date is not specified, the default date set would be forever.
   
Examples:
- `addR a/90 d/2021-12-03 n/phone bills c/3 i/MONTH`
- `addR a/5 n/phone bills c/4 i/MONTH`
- `addR a/5 n/phone bills d/2021-10-10 i/MONTH`

Examples and expected Output

```
addR a/90 d/2021-12-03 n/phone bills c/3 i/MONTH
I've added :Expense |    HOUSEHOLD     | 2021-12-03 |   phone bills    |-$90.00 | MONTH | 2200-12-31
addR a/5 n/phone bills c/4 i/MONTH
I've added :Expense |     APPAREL      | 2021-10-26 |      shirt       |-$300.00 | MONTH | 2200-12-31
addR a/5 n/phone bills d/2021-10-10 i/MONTH
I've added :Expense |      OTHERS      | 2021-10-10 |   phone bills    |-$5.00 | MONTH | 2200-12-31
```

## <a name="view"></a>Viewing all entries: `view`

Shows a list of all the expenses, each with the associated `NAME`, `DATE`, and `AMOUNT`. 

Format: `view [income] [expense] [by SORTTYPE] [month MONTH] [year YEAR] [from STARTDATE [ENDDATE]] [up/ascending]`

- Views all entries with the specified `MONTH`, `YEAR`, from `STARTDATE` to `ENDDATE`, sorted by `SORTTYPE`.
- `[income](optional)` and `[expense](optional)` if appended, only shows the entries of the corresponding type.
- `SORTTYPE` can be any of the following types: `name`, `date`, `amount`, `category`
- `MONTH(optional)` can be any number from 1 to 12.
- If `MONTH` is not specified, the default will be the current month.
- `YEAR(optional)` can be any 4-digit number.
- If `YEAR` is not specified, the default will be the current year.
- `STARTDATE(optional)` and `ENDDATE(optional)` can be any of the [acceptable date formats](#dateFormat). </br>
- If `STARTDATE` is specified but `ENDDATE` is not specified, the default `ENDDATE` set would be the current date.
- `up(optional)` or `ascending(optional)` if appended with sort, will sort the list in ascending 
  , else the default will sort the list in descending order.
  
Examples:
- `view`
- `view income`
- `view month 4 year 2021`
- `view from 2021-03-25 2022-01-02 by amount ascending`

Examples and expected Output:

```
view
Here is the list of your entries:
  Type  |     Category     |    Date    |       Name       | Amount
Expense |      OTHERS      | 2021-10-25 |     Textbook     |-$15.00
Expense |       FOOD       | 2021-04-20 |  Cheese Burger   |-$4.20
Income  |  ENTERTAINMENT   | 2021-02-19 |      Sales       | $34.00
                                                Net Total: | $14.80
```
```
view income
Here is the list of your entries:
  Type  |     Category     |    Date    |       Name       | Amount
Income  |  ENTERTAINMENT   | 2021-02-19 |      Sales       | $34.00
                                                Net Total: | $34.00
```
```
view month 4 year 2021
For the year 2021:
For the month of APRIL:
Here is the list of your entries:
  Type  |     Category     |    Date    |       Name       | Amount
Expense |       FOOD       | 2021-04-20 |  Cheese Burger   |-$4.20
                                                Net Total: |-$4.20
```
```
view from 2021-03-25 2022-01-02 by amount ascending
Here is the list of your entries:
Since 2021-03-25 to 2022-01-02:
  Type  |     Category     |    Date    |       Name       | Amount
Expense |       FOOD       | 2021-04-20 |  Cheese Burger   |-$4.20
Expense |      OTHERS      | 2021-10-25 |     Textbook     |-$15.00
                                                Net Total: |-$19.20
```

## <a name="delete"></a>Deleting an entry: `delete`

Deletes an existing entry. </br>
Format: `delete [n/NAME] [d/DATE] [a/AMOUNT] [c/CATEGORY_NUMBER]`

- At least one of the optional fields must be provided.
- Our program searches the entry that matches the fields provided by the user.
    - If there is more than 1 `Expense` or `Income` matching the query, the program will </br>
      return a list for the user to choose from. The user would then have to </br>
      confirm the deletion of the entry.
    - If there is 1  `Expense` or `Income` matching the query, the program will prompt the </br>
      user to confirm the deletion of that  `Expense` or `Income` .
- Deletes an entry of the specified `NAME`, `DATE`, `AMOUNT`, or `CATEGORY_NUMBER`
- `NAME` can be any string of characters
- `DATE` can be any of the [acceptable date formats](#dateFormat).
- `AMOUNT` is in dollars. Numbers after the decimal point are in cents. Eg. 4.5 is $4.50
- `CATEGORY_NUMBER` is any integer from 0 to 7. Please refer to the [available categories](#categoryList).

Examples:

- `delete n/Textbook d/2012-09-21 a/15`
- `delete n/Cheese Burger d/2020-04-20 a/4.2`

Examples and expected output:

- If user query only matches 1 `Expense` or `Income` in the expense list

```
delete n/Textbook d/2012-09-21
Is this what you want to delete?
    Expense |      OTHERS      | 2012-09-21 |     Textbook     |-$15.00
Type "y" if yes. Type "n" if not.
y
I have deleted: Expense |      OTHERS      | 2012-09-21 |     Textbook     |-$15.00
```

- If user query matches more than 1 `Expense` or `Income` in the list

```
delete n/Cheese Burger d/2020-04-20 a/4.2
Here is the list of items containing the keyword.
    1  Income  |      OTHERS      | 2020-04-20 |  Cheese Burger   | $4.20
    2  Expense |  TRANSPORTATION  | 2020-04-20 |  Cheese Burger   |-$4.20
Enter the index of the item you want to delete. To cancel, type "cancel"
1
I have deleted: Income  |      OTHERS      | 2020-04-20 |  Cheese Burger   | $4.20
```

## <a name="edit"></a>Editing an entry: `edit`

Edits an existing entry </br>
Format: `edit [n/NAME] [a/AMOUNT] [d/DATE] [c/CATEGORY_NUMBER]`

- At least one of the optional fields must be provided
- When editing fields, existing fields of the `Expense` or `Income` indicated by the user will be </br>
  replaced.
- Our program searches the entry that matches the fields provided by the user.
    - If there is 1 `Expense` or `Income` matching the query, the program will prompt the </br>
      user to confirm if they wish to edit that entry.
    - If there is more than 1 `Expense` or `Income` matching the query, the program will return </br>
      a list for the user to choose from. The user would then have to confirm if </br>
      they wish to edit the entry.
- `NAME` can be any string of characters
- `DATE` can be any of the [acceptable date formats](#dateFormat).
- `AMOUNT` is in dollars. Numbers after the decimal point are in cents. Eg. 4.5 is $4.50
- `CATEGORY_NUMBER` is any integer from 0 to 7. Please refer to the [available categories](#categoryList). </br>

Examples:

- `edit n/Textbook d/2012-09-21 a/15`
- `edit n/Cheese Burger d/2020-04-20 a/4.2`

Examples and expected output:

- If user query only matches 1 `Expense` or `Income` in the expense list

```
edit n/Textbook d/2012-09-21 a/15
Is this what you want to edit?
    Expense |      OTHERS      | 2012-09-21 |     Textbook     |-$15.00
Type "y" if yes. Type "n" if not.
y
What would you like to edit?
a/14
Got it! I will update the fields accordingly!
```

- If user query matches more than 1 `Expense` or `Income` in the list

```
edit n/Cheese Burger d/2020-04-20 a/4.2
Here is the list of items containing the keyword.
    1  Expense |  TRANSPORTATION  | 2020-04-20 |  Cheese Burger   |-$4.20
    2  Income  |      OTHERS      | 2020-04-20 |  Cheese Burger   | $4.20
Enter the index of the item you want to edit. To cancel, type "cancel"
1
What would you like to edit?
c/0
Got it! I will update the fields accordingly!

```

## <a name="cat"></a>View available categories: `cat`

Shows a list of all available categories and its corresponding tag number</br>
Format: `cat`
Expected Output:

```
{INSERT cat expected output}
```

## <a name="exit"></a>Exit the program: `exit`

Exits the Mint program.

Format: `exit`

Expected Output:
- `Bye! Thanks for using Mint. See you soon :D`


## <a name="Commannd Summary"></a>Command Summary
| Command | Format,Examples |
| -----| -----|
|Add |`add` `[income]` `n/NAME` `a/AMOUNT` `[d/DATE]` `[c/CATEGORY_NUMBER]` </br>e.g. `add n/burger a/5 d/2021-10-20 c/0`|
|AddR | `addR` `[income]` `n/NAME a/AMOUNT` `[d/DATE]` `[c/CATEGORY_NUMBER]` `i/INTERVAL` `[e/END_DATE]` </br> e.g. `addR a/90 d/2021-12-03 n/phone bills c/3 i/MONTH`
|Edit |`edit` `[n/NAME]` `[a/AMOUNT]` `[d/DATE]` `[c/CATEGORY_NUMBER]` </br> e.g. `edit n/burger a/5 d/2021-10-20 c/0`|
|Delete | `delete``[n/NAME]` `[a/AMOUNT]` `[d/DATE]` `[c/CATEGORY_NUMBER]` </br> e.g. `delete n/Cheese Burger d/20-10-2021 a/4.2` |
|View |`view` `[income]` `[expense]` `[by SORTTYPE]` `[month MONTH]` `[year YEAR]` `[from STARTDATE [ENDDATE]]` `[up/ascending]` </br> e.g. `view from 2021-03-25 2022-01-02 by amount ascending`|
|View categories | `cat` |
|Help | `help` |
|Exit | `exit` |
