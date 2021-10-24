package seedu.duke.commands;

import seedu.duke.Entry;
import seedu.duke.EntryList;
import seedu.duke.MintException;
import seedu.duke.storage.EntryListDataManager;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class DeleteCommand extends Command {

    public void deleteByKeywords(ArrayList<String> tags, Entry entry, EntryList entryList) throws MintException {
        try {
            Entry finalEntry = entryList.chooseEntryByKeywords(tags, true, entry);
            if (finalEntry != null) {
                delete(finalEntry, entryList);
            }
        } catch (MintException e) {
            throw new MintException(e.getMessage());
        }
    }

    public void delete(Entry entry, EntryList entryList) throws MintException {
            //logger.log(Level.INFO, "User deleted expense: " + entry);
            System.out.println("I have deleted: " + entry);
            entryList.deleteEntry(entry);
            String stringToDelete = EntryList.overWriteString(entry);
            EntryListDataManager.deleteLineInTextFile(stringToDelete);
    }

    public void deleteAll(ArrayList<Entry> entryList) {
        if (Objects.equals(deleteConfirmations(), "yes")) {
            entryList.clear();
            EntryListDataManager.removeAll();
            System.out.println("Successfully deleted all entries.");
        } else {
            System.out.println("Delete cancelled.");
        }
    }

    private String deleteConfirmations() {
        String choice;
        Scanner scan = new Scanner(System.in);
        System.out.println("Are you sure you want to delete all entries?");
        choice = scan.nextLine();
        return choice;
    }
}
