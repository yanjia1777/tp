package seedu.duke.storage;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class DataManagerActions {
    public static final int NUMBER_OF_CATEGORIES = 8;
    public static final String REGEX_TO_REPLACE = "[\\p{Alpha}, [\\p{Punct}&&[^.]]+]";
    public static final String TEXT_DELIMITER = "|";
    public static final String NORMAL_FILE_PATH = "data" + File.separator + "Mint.txt";
    public static final String RECURRING_FILE_PATH = "data" + File.separator + "MintRecurring.txt";
    public static final String BUDGET_FILE_PATH = "data" + File.separator + "MintBudget.txt";

    public void createDirectory() {
        Path path = Paths.get("data" + File.separator + "Mint.txt");
        try {
            Files.createDirectories(path.getParent());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createFiles() {
        try {
            String[] filesToCreate = {RECURRING_FILE_PATH, NORMAL_FILE_PATH, BUDGET_FILE_PATH};
            createNewFiles(filesToCreate);
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private void createNewFiles(String[] filesToCreate) throws IOException {
        for (String file: filesToCreate) {
            assert(file != null);
            File myObj = new File(file);
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
        }
    }
}
