package seedu.duke;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class DataManager {
    public static final int NUMBER_OF_CATEGORIES = 8;
    public static final String REGEX_TO_REPLACE = "[\\p{Alpha}, [\\p{Punct}&&[^.]]+]";
    public static final String CATEGORY_TAG = "c/";
    public static final String TEXT_DELIMITER = "|";
    private final String path;
    public static final String FILE_PATH = "data" + File.separator + "Mint.txt";
    public static final String CATEGORY_FILE_PATH = "data" + File.separator + "MintCategory.txt";


    public DataManager(String path) {
        this.path = path;
    }

    public static void appendToFileLive(String filePath, Expense expense) throws IOException {
        FileWriter fileWriter = new FileWriter(filePath, true);
        // Format of Mint.txt file: 0|2021-12-03|Textbook|15.0
        fileWriter.write(expense.getCatNum() + TEXT_DELIMITER + expense.getDate()
                + TEXT_DELIMITER + expense.getName() + TEXT_DELIMITER + expense.getAmount() + System.lineSeparator());
        fileWriter.close();
    }

    public void editCategoryFileLive(int catNum) {
        ArrayList<String> fileContent;
        try {
            String categoryToBeChanged = CATEGORY_TAG + catNum;
            fileContent = loadStoredFileContent();
            buildContentCatList(catNum, fileContent, categoryToBeChanged);
            editCategoryFile(fileContent);
        } catch (FileNotFoundException e) {
            createDirectory();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void editCategoryFile(ArrayList<String> fileContent) throws IOException {
        if (!fileContent.isEmpty()) {
            amendFile(fileContent, CATEGORY_FILE_PATH);
        }
    }

    private void buildContentCatList(int catNum, ArrayList<String> fileContent, String categoryToBeChanged) {
        for (int i = 0; i < fileContent.size(); i++) {
            if (fileContent.get(i).contains(categoryToBeChanged)) {
                fileContent.set(i, CATEGORY_TAG + catNum + TEXT_DELIMITER + CategoryList.getLimitIndented(catNum));
                break;
            }
        }
    }

    private ArrayList<String> loadStoredFileContent() throws IOException {
        ArrayList<String> fileContent;
        fileContent = new ArrayList<>(Files.readAllLines(Path.of(CATEGORY_FILE_PATH), StandardCharsets.UTF_8));
        if (fileContent.isEmpty()) {
            loadCategoryTextFile();
        }
        return fileContent;
    }

    public static void editFileLive(String originalString, String newString) {
        ArrayList<String> fileContent;
        try {
            fileContent = new ArrayList<>(Files.readAllLines(Path.of(FILE_PATH), StandardCharsets.UTF_8));
            buildContent(originalString, newString, fileContent);
            amendFile(fileContent, FILE_PATH);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void buildContent(String originalString, String newString, ArrayList<String> fileContent) {
        for (int i = 0; i < fileContent.size(); i++) {
            if (fileContent.get(i).equals(originalString)) {
                fileContent.set(i, newString);
                break;
            }
        }
    }

    public static void deleteFileLive(String originalString) {
        ArrayList<String> fileContent;
        try {
            fileContent = new ArrayList<>(Files.readAllLines(Path.of(FILE_PATH), StandardCharsets.UTF_8));
            lineRemoval(originalString, fileContent);
            amendFile(fileContent, FILE_PATH);
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

    public static void readFileContents(String filePath, ExpenseList expenseList) throws FileNotFoundException {
        File mintExpenseList = new File(filePath); // create a File for the given file path
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

    private static boolean isCurrentMonthExpense(Expense expense) {
        return expense.date.getMonthValue() == LocalDate.now().getMonthValue()
                && expense.date.getYear() == LocalDate.now().getYear();
    }

    public static void loadExpense(String name, String date, String amount, String catNum, ExpenseList expenseList) {
        ArrayList<Expense> loadedExpenseList = expenseList.getExpenseList();
        Expense expense = new Expense(name, date, amount, catNum);
        if (isCurrentMonthExpense(expense)) {
            CategoryList.addSpending(expense);
        }
        loadedExpenseList.add(expense);
    }

    public static void loadCategoryTextFile() throws IOException {
        FileWriter fileWriter = new FileWriter(CATEGORY_FILE_PATH, true);
        for (int catNum = 0; catNum < NUMBER_OF_CATEGORIES; catNum++) {
            fileWriter.write(CATEGORY_TAG + catNum + TEXT_DELIMITER
                    + CategoryList.getLimitIndented(catNum) + System.lineSeparator());
            fileWriter.flush();
        }
        fileWriter.close();
    }

    public static void readCategoryFileContents() throws FileNotFoundException {
        File categoryList = new File(CATEGORY_FILE_PATH); // create a File for the given file path
        Scanner scanner = new Scanner(categoryList); // create a Scanner using the File as the source
        while (scanner.hasNext()) {
            //c/0| Not Set
            String fieldsInTextFile = scanner.nextLine();
            String[] params = fieldsInTextFile.split("\\|");
            String catNum = params[0].replaceAll(REGEX_TO_REPLACE,"").trim();
            String limit = params[1].replaceAll(REGEX_TO_REPLACE,"").trim();
            try {
                if (!limit.equals("")) {
                    CategoryList.setLimit(catNum, limit);
                }
            } catch (MintException e) {
                e.printStackTrace();
            }
        }
    }

    private static void amendFile(ArrayList<String> fileContent, String filepath) throws IOException {
        Files.write(Path.of(filepath), fileContent, StandardCharsets.UTF_8);
    }

    public void printPreviousFileContents(String filePath, ExpenseList expenseList) {
        try {
            DataManager.readFileContents(filePath, expenseList);
            DataManager.readCategoryFileContents();
        } catch (FileNotFoundException e) {
            Ui.printMissingFileMessage();
            createDirectory();
            createFiles();
        }
    }

    public void createDirectory() {
        Path path = Paths.get(this.path);
        try {
            Files.createDirectories(path.getParent()); //make directory
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createFiles() {
        try {
            String[] filesToCreate = {CATEGORY_FILE_PATH, FILE_PATH};
            createNewFiles(filesToCreate);
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private static void createNewFiles(String[] filesToCreate) throws IOException {
        for (String file: filesToCreate) {
            File myObj = new File(file);
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
        }
    }
}
