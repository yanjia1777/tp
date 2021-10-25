package seedu.duke.storage;

import seedu.duke.entries.*;
import seedu.duke.exception.MintException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class RecurringListDataManager extends DataManagerActions{

    public RecurringListDataManager(String path) {
        super(path);
    }

    public void appendToMintRecurringListTextFile(String filePath, Entry entry) {
        // Format of MintRecurring.txt file: Expense|7|2021-10-25|SALARY|10000.0|MONTH
        FileWriter fileWriter = null;
        try {
            RecurringEntry recurringEntry = (RecurringEntry) entry;
            fileWriter = new FileWriter(filePath, true);
            fileWriter.write(entry.getType().toString() + TEXT_DELIMITER + entry.getCategory().ordinal()
                    + TEXT_DELIMITER + entry.getDate() + TEXT_DELIMITER + entry.getName() + TEXT_DELIMITER
                    + entry.getAmount() + TEXT_DELIMITER + recurringEntry.getInterval() + TEXT_DELIMITER
                    + recurringEntry.getEndDate() + System.lineSeparator());
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadEntryListContents(ArrayList<Entry> entryList) throws FileNotFoundException {
        File mintEntryList = new File(RECURRING_FILE_PATH); // create a File for the given file path
        Scanner scanner = new Scanner(mintEntryList); // create a Scanner using the File as the source
        while (scanner.hasNext()) {
            String fieldsInTextFile = scanner.nextLine();
            String[] params = fieldsInTextFile.split("\\|");
            String type = params[0];
            String catNum = params[1];
            String date = params[2];
            String name = params[3];
            String amount = params[4];
            String interval = params[5];
            String endDate = params[6];
            loadEntry(type, name, date, amount, catNum, interval, endDate, entryList);
        }
    }


    public void loadEntry(String type, String name, String dateStr, String amountStr,
                          String catNumStr, String intervalStr, String endDateStr, ArrayList<Entry> recurringList) {
        //should check type before loading
        RecurringEntry recurringEntry;
        LocalDate date = LocalDate.parse(dateStr);
        LocalDate endDate = LocalDate.parse(endDateStr);
        double amount = Double.parseDouble(amountStr);
        Interval interval = null;
        try {
            interval = Interval.determineInterval(intervalStr);
        } catch (MintException e) {
            e.printStackTrace();
        }
        int index = Integer.parseInt(catNumStr);
        if (Objects.equals(type.toLowerCase(), "expense")) {
            ExpenseCategory category = ExpenseCategory.values()[index];
            recurringEntry = new RecurringExpense(name, date, amount, category, interval, endDate);
        } else {
            IncomeCategory category = IncomeCategory.values()[index];
            recurringEntry = new RecurringIncome(name, date, amount, category, interval, endDate);
        }
        recurringList.add(recurringEntry);
    }
}
