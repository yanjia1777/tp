# User Guide v2.0

## Introduction

Mint is an all-in-one desktop app for young adults to manage your savings and expenses and set long term financial
goals. It is optimized for use via a Command Line Interface (CLI).

- [Features](#features)
    - [Viewing help](#help)
    - [Adding entries](#add)
    - [Viewing entries](#view)
    - [Deleting entries](#delete)
    - [Editing entries](#edit)
    - [Viewing categories](#cat)
    - [Exiting the program](#exit)
- [Available date formats](#dateFormat)
- [List of categories](#categoryList)
- [Command Summary](#command-summary)

## <a name="features"></a>Features

---

Notes about the following list of commands:

- Items in square brackets are optional.</br>
  e.g n/NAME [d/DATE] can be used as n/burger d/2021-10-20 or as n/burger
- Parameters with tags or optional modifiers can be in any order.</br>
  e.g. if the command specifies n/NAME a/AMOUNT, a/AMOUNT n/NAME is also acceptable.
- If a parameter is expected only once in the command but if you specify it multiple times, only the last occurrence of
  the parameter will be taken.</br>
  e.g. if you specify a/10 a/15, only a/10 will be taken.
- Extraneous parameters for commands that do not take in parameters (such as help, list, exit and clear)
  will be ignored.</br>
  e.g. if the command specifies help 123, it will be interpreted as help.

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
- `add n/Textbook a/15`
- `add n/Cheese Burger a/4.2 d/2021-04-20 c/0`
- `add income n/Sales a/34 d/2021-02-19 c/1`

Examples and expected Output

```
add n/Textbook a/15
I've added: Expense |      OTHERS      | 2021-10-25 |     Textbook     |-$15.00
add n/Cheese burger a/4.2 d/2021-04-20 c/0
I've added: Expense |       FOOD       | 2021-04-20 |  Cheese Burger   |-$4.20
add income n/Sales a/34 d/2021-02-19 c/1
I've added :Income  |  ENTERTAINMENT   | 2021-02-19 |      Sales       | $34.00
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
- `up(optional)` or `ascending(optional)` if appended with sort, will sort the list in ascending order, else the default will sort the list in descending order.

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
    - If there is more than 1 `Expense` matching the query, the program will </br>
      return a list for the user to choose from. The user would then have to </br>
      confirm the deletion of the entry.
    - If there is 1  `Expense` matching the query, the program will prompt the </br>
      user to confirm the deletion of that  `Expense`.
- Deletes an expense of the specified `NAME`, `DATE`, `AMOUNT`, or `CATEGORY_NUMBER`
- `NAME` can be any string of characters
- `DATE` can be any of the [acceptable date formats](#dateFormat).
- `AMOUNT` is in dollars. Numbers after the decimal point are in cents. Eg. 4.5 is $4.50
- `CATEGORY_NUMBER` is any integer from 0 to 7. Please refer to the [available categories](#categoryList).

Examples:

- `delete n/Textbook d/120921 a/15`
- `delete n/Cheese Burger d/010420 a/4.2`

Examples and expected output:

- If user query only matches 1 `Expense` in the expense list

```
{INSERT delete 1 expense}
```

- If user query matches more than 1 `Expense` in the list

```
{INSERT delete more than 1 expense}
```

## <a name="edit"></a>Editing an entry: `edit`

Edits an existing entry </br>
Format: `edit [n/NAME] [a/AMOUNT] [d/DATE] [c/CATEGORY_NUMBER]`

- At least one of the optional fields must be provided
- When editing fields, existing fields of the `Expense` indicated by the user will be </br>
  replaced.
- Our program searches the entry that matches the fields provided by the user.
    - If there is 1  `Expense` matching the query, the program will prompt the </br>
      user to confirm if they wish to edit that Expense.
    - If there is more than 1 Expense matching the query, the program will return </br>
      a list for the user to choose from. The user would then have to confirm if </br>
      they wish to edit the entry.
- `NAME` can be any string of characters
- `DATE` can be any of the [acceptable date formats](#dateFormat).
- `AMOUNT` is in dollars. Numbers after the decimal point are in cents. Eg. 4.5 is $4.50
- `CATEGORY_NUMBER` is any integer from 0 to 7. Please refer to the [available categories](#categoryList). </br>

Examples and expected output:

- If user query only matches 1 `Expense` in the expense list

```
{INSERT edit 1 expense}
```

- If user query matches more than 1 `Expense` in the list

```
{INSERT edit more than 1 expense}
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
| action | Format,Examples |
| -----| -----|
|Add |add `n/NAME` `a/AMOUNT` `[d/DATE]` `[c/CATEGORY_NUMBER]` </br>e.g., `add n/burger a/5 d/2021-10-20 c/0` |
|Edit |edit `[n/NAME]` `[a/AMOUNT]` `[d/DATE]` `[c/CATEGORY_NUMBER]` </br> e.g., `edit n/burger a/5 d/2021-10-20 c/0`|
|Delete | delete `[n/NAME]` `[a/AMOUNT]` `[d/DATE]` `[c/CATEGORY_NUMBER]` </br> `e.g., delete n/Cheese Burger d/20-10-2021 a/4.2` |
|View | {to be confirmed} |
|View categories | `cat` |
|Help | `help` |
|Exit | `exit` |
