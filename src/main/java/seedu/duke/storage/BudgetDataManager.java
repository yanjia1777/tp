package seedu.duke.storage;

import seedu.duke.budget.Budget;
import seedu.duke.exception.MintException;
import seedu.duke.parser.ValidityChecker;
import seedu.duke.utility.Ui;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

//@@author Yitching
public class BudgetDataManager extends DataManagerActions {

    public static final String BUDGET_FILE_PATH = "data" + File.separator + "MintBudget.txt";

    /**
     * Saves the spending limits for each category to the MintBudget.txt file
     *
     * @param budgetList Budget type arrayList that stores the spending limits for each category.
     */
    public void writeToBudgetTextFile(ArrayList<Budget> budgetList) {
        FileWriter fileWriter = null;
        boolean cleared = false;
        try {
            fileWriter = new FileWriter(BUDGET_FILE_PATH, false);
            for (Budget budget: budgetList) {
                fileWriter.write(budget.getCategory().ordinal() + TEXT_DELIMITER
                        + budget.getLimit() + System.lineSeparator());
            }
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the spending limits for each category to the budgetList arrayList.
     *
     * @param budgetList Budget type arrayList that stores the spending limits for each category.
     */
    public void loadBudgetListContents(ArrayList<Budget> budgetList) throws FileNotFoundException, MintException,
            ArrayIndexOutOfBoundsException {
        File mintBudgetList = new File(BUDGET_FILE_PATH); // create a File for the given file path
        Scanner scanner = new Scanner(mintBudgetList); // create a Scanner using the File as the source
        while (scanner.hasNext()) {
            String fieldsInTextFile = scanner.nextLine();
            if (fieldsInTextFile.length() == 0) {
                continue;
            }
            try {
                String[] params = fieldsInTextFile.split("\\|");
                assert (params[0] != null);
                assert (params[1] != null);
                String catNumStr = params[0];
                String amountStr = params[1];
                ValidityChecker.checkValidityOfFieldsInBudgetListTxt(catNumStr, amountStr);
                int catNum = Integer.parseInt(catNumStr);
                double amount = Double.parseDouble(amountStr);
                loadBudget(catNum, amount, budgetList);
            } catch (MintException e) {
                reload(budgetList, fieldsInTextFile);
                throw new MintException(e.getMessage() + " Invalid line deleted. We have reloaded the list!");
            } catch (ArrayIndexOutOfBoundsException e) {
                reload(budgetList, fieldsInTextFile);
                throw new ArrayIndexOutOfBoundsException();
            }
        }
    }

    /**
     * Deletes an invalid line from the text file if found.
     *
     * @param originalString is the invalid string to be removed.
     */
    public void deleteLineInTextFile(String originalString) {
        ArrayList<String> fileContent;
        try {
            fileContent = new ArrayList<>(Files.readAllLines(Path.of(BUDGET_FILE_PATH), StandardCharsets.UTF_8));
            lineRemoval(originalString, fileContent);
            editTextFile(fileContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Iterates through the content of the MintBudget.txt file to find and remove the invalid line.
     *
     * @param originalString is the invalid string to be removed.
     * @param fileContent holds the content of the MintBudget.txt file
     */
    protected void lineRemoval(String originalString, ArrayList<String> fileContent) {
        for (int i = 0; i < fileContent.size(); i++) {
            if (fileContent.get(i).equals(originalString)) {
                fileContent.remove(i);
                break;
            }
        }
    }

    /**
     * Writes any changes to the text file.
     *
     * @param fileContent holds the content of the MintBudget.txt file
     */
    protected void editTextFile(ArrayList<String> fileContent) throws IOException {
        Files.write(Path.of(BUDGET_FILE_PATH), fileContent, StandardCharsets.UTF_8);
    }

    /**
     * If any invalid lines or errors are found, this function deletes the invalid lines and reloads the contents from
     * the MintBudget.txt file to the budgetList.
     *
     * @param budgetList Budget type arrayList that stores the spending limits for each category.
     * @param fieldsInTextFile different fields from text file to be loaded into different attributes of the budgetList.
     */
    public void reload(ArrayList<Budget> budgetList, String fieldsInTextFile) throws FileNotFoundException,
            MintException {
        deleteLineInTextFile(fieldsInTextFile);
        loadBudgetListContents(budgetList);
    }

    /**
     * Load the spending limits set for each category from the MintBudget.txt file to the budgetList
     *
     * @param catNum category number representing different categories
     * @param amount spending limit set for each category
     * @param budgetList Budget type arrayList that stores the spending limits for each category.
     */
    public void loadBudget(int catNum, double amount, ArrayList<Budget> budgetList) {
        Budget budget = budgetList.get(catNum);
        budget.setLimit(amount);
    }

    /**
     * At the start of the program, load the contents of the MintBudget.txt file to the budgetList. If there is any
     * missing or erroneous text files or directory, this function would create all the necessary files and directory.
     *
     * @param budgetList Budget type arrayList that stores the spending limits for each category.
     */
    public void loadFromTextFile(ArrayList<Budget> budgetList) {
        BudgetDataManager budgetDataManager = new BudgetDataManager();
        try {
            budgetDataManager.loadBudgetListContents(budgetList);
        } catch (FileNotFoundException e) {
            Ui.printMissingFileMessage();
            createDirectory();
            createFiles();
        } catch (MintException e) {
            System.out.println(e.getMessage());
        } catch (ArrayIndexOutOfBoundsException e) {
            Ui.printFieldsErrorMessage();
        }
    }
}
//@@author Yitching