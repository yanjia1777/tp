package seedu.duke.storage;

import seedu.duke.CategoryList;
import seedu.duke.Expense;
import seedu.duke.ExpenseList;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

public class ExpenseListDataManager extends DataManagerActions {

    public static final String TEXT_DELIMITER = "|";
    public static final String FILE_PATH = "data" + File.separator + "Mint.txt";

    public ExpenseListDataManager(String path) {
        super(path);
    }

    public static void appendToExpenseListTextFile(String filePath, Expense expense) throws IOException {
        FileWriter fileWriter = new FileWriter(filePath, true);
        // Format of Mint.txt file: 0|2021-12-03|Textbook|15.0
        fileWriter.write(expense.getCatNum() + TEXT_DELIMITER + expense.getDate()
                + TEXT_DELIMITER + expense.getName() + TEXT_DELIMITER + expense.getAmount() + System.lineSeparator());
        fileWriter.close();
    }

    public static void editExpenseListTextFile(String originalString, String newString) {
        ArrayList<String> fileContent;
        try {
            fileContent = new ArrayList<>(Files.readAllLines(Path.of(FILE_PATH), StandardCharsets.UTF_8));
            setContentToBeChanged(originalString, newString, fileContent);
            editTextFile(fileContent, FILE_PATH);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void setContentToBeChanged(String originalString, String newString, ArrayList<String> fileContent) {
        for (int i = 0; i < fileContent.size(); i++) {
            if (fileContent.get(i).equals(originalString)) {
                fileContent.set(i, newString);
                break;
            }
        }
    }

    public static void deleteLineInTextFile(String originalString) {
        ArrayList<String> fileContent;
        try {
            fileContent = new ArrayList<>(Files.readAllLines(Path.of(FILE_PATH), StandardCharsets.UTF_8));
            lineRemoval(originalString, fileContent);
            editTextFile(fileContent, FILE_PATH);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void lineRemoval(String originalString, ArrayList<String> fileContent) {
        for (int i = 0; i < fileContent.size(); i++) {
            if (fileContent.get(i).equals(originalString)) {
                fileContent.remove(i);
                break;
            }
        }
    }

    public static void loadExpenseListContents(ExpenseList expenseList) throws FileNotFoundException {
        File mintExpenseList = new File(FILE_PATH); // create a File for the given file path
        Scanner scanner = new Scanner(mintExpenseList); // create a Scanner using the File as the source
        while (scanner.hasNext()) {
            String fieldsInTextFile = scanner.nextLine();
            String[] params = fieldsInTextFile.split("\\|");
            String catNum = params[0];
            String date = params[1];
            String name = params[2];
            String amount = params[3];
            loadExpense(name, date, amount, catNum, expenseList);
        }
    }

    public static void loadExpense(String name, String date, String amount, String catNum, ExpenseList expenseList) {
        ArrayList<Expense> loadedExpenseList = expenseList.getExpenseList();
        Expense expense = new Expense(name, date, amount, catNum);
        CategoryList.addSpending(catNum, amount);
        loadedExpenseList.add(expense);
    }

}
