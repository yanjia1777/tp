package seedu.duke;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class DataManager {
    private final String path;
    public static final String FILE_PATH = "data" + File.separator + "Mint.txt";


    public DataManager(String path) {
        this.path = path;
    }

    public static void appendToFileLive(String filePath, Expense expense) throws IOException {
        FileWriter fileWriter = new FileWriter(filePath, true);
        // Format of Mint.txt file: 0|2021-12-03|Textbook|15.0
        fileWriter.write(expense.getCatNum() + "|" + expense.getDate() + "|" + expense.getName()
                    + "|" + expense.getAmount() + System.lineSeparator());
        fileWriter.close();
    }

    public static void editFileLive(String originalString, String newString) {
        ArrayList<String> fileContent;
        try {
            fileContent = new ArrayList<>(Files.readAllLines(Path.of(FILE_PATH), StandardCharsets.UTF_8));
            for (int i = 0; i < fileContent.size(); i++) {
                if (fileContent.get(i).equals(originalString)) {
                    fileContent.set(i, newString);
                    break;
                }
            }
            Files.write(Path.of(FILE_PATH), fileContent, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deleteFileLive(String originalString) {
        ArrayList<String> fileContent;
        try {
            fileContent = new ArrayList<>(Files.readAllLines(Path.of(FILE_PATH), StandardCharsets.UTF_8));
            for (int i = 0; i < fileContent.size(); i++) {
                if (fileContent.get(i).equals(originalString)) {
                    fileContent.remove(i);
                    break;
                }
            }
            Files.write(Path.of(FILE_PATH), fileContent, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readFileContents(String filePath, ExpenseList expenseList) throws FileNotFoundException {
        File dukeExpenseList = new File(filePath); // create a File for the given file path
        Scanner scanner = new Scanner(dukeExpenseList); // create a Scanner using the File as the source
        while (scanner.hasNext()) {
            String fieldsInTextFile = scanner.nextLine();
            String[] params = fieldsInTextFile.split("\\|");
            String catNum = params[0];
            String date = params[1];
            String name = params[2];
            String amount = params[3];
            addExpense(name, date, amount, catNum, expenseList);
        }
    }

    public static void addExpense(String name, String date, String amount, String catNum, ExpenseList expenseList) {
        ArrayList<Expense> loadedExpenseList = expenseList.getExpenseList();
        Expense expense = new Expense(name, date, amount, catNum);
        loadedExpenseList.add(expense);
    }

    public void printPreviousFileContents(String filePath, ExpenseList expenseList) {
        try {
            DataManager.readFileContents(filePath, expenseList);
        } catch (FileNotFoundException e) {
            System.out.println("No data detected! I see you are a new user...Starting afresh!");
            createDirectory(filePath, expenseList);
        }
    }

    public void createDirectory(String filePath, ExpenseList expenseList) {
        Path path = Paths.get(this.path);
        try {
            Files.createDirectories(path.getParent()); //make directory
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
