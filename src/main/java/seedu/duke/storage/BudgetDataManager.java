package seedu.duke.storage;

import seedu.duke.budget.Budget;
import seedu.duke.budget.BudgetManager;
import seedu.duke.entries.*;
import seedu.duke.utility.Ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class BudgetDataManager extends DataManagerActions {

    public static final String BUDGET_FILE_PATH = "data" + File.separator + "MintBudget.txt.txt";
    public static final String CATEGORY_TAG = "c/";

    public BudgetDataManager(String path) {
        super(path);
    }

    public void writeToBudgetTextFile(ArrayList<Budget> budgetList) {
        FileWriter fileWriter = null;
        try {
            for (Budget budget: budgetList) {
                fileWriter = new FileWriter(BUDGET_FILE_PATH, true);
                fileWriter.write(budget.getCategory().ordinal() + TEXT_DELIMITER
                        + budget.getLimit() + System.lineSeparator());
                fileWriter.flush();
                fileWriter.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadBudgetListContents(ArrayList<Budget> budgetList) throws FileNotFoundException {
        File mintBudgetList = new File(BUDGET_FILE_PATH); // create a File for the given file path
        Scanner scanner = new Scanner(mintBudgetList); // create a Scanner using the File as the source
        while (scanner.hasNext()) {
            String fieldsInTextFile = scanner.nextLine();
            String[] params = fieldsInTextFile.split("\\|");
            String catNumStr = params[0];
            String amountStr = params[1];
            int catNum = Integer.parseInt(catNumStr);
            double amount = Double.parseDouble(amountStr);
            loadBudget(catNum, amount, budgetList);

        }
    }
    public void loadBudget(int catNum, double amount, ArrayList<Budget> budgetList) {
        Budget budget = budgetList.get(catNum);
        budget.setLimit(amount);
    }
}
