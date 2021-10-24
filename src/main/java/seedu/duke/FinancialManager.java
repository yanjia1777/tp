package seedu.duke;

import java.util.ArrayList;

public class FinancialManager {
    public ArrayList<Entry> entryList;

    public FinancialManager() {
        this.entryList = new ArrayList<>();
    }

    public static ArrayList<Entry> filterEntryByKeywords(ArrayList<String> tags, Entry entry,
                                                      ArrayList<Entry> entryList) throws MintException {
        ArrayList<Entry> filteredList = new ArrayList<>(entryList);
        for (String tag : tags) {
            switch (tag) {
            case "n/":
                filteredList = Filter.filterEntryByName(entry.getName(), filteredList);
                break;
            case "d/":
                filteredList = Filter.filterEntryByDate(entry.getDate(), filteredList);
                break;
            case "a/":
                filteredList = Filter.filterEntryByAmount(entry.getAmount(), filteredList);
                break;
            case "c/":
                filteredList = Filter.filterEntryByCategory(entry.getCategory(), filteredList);

                break;
            default:
                throw new MintException("Unable to locate tag");
            }
        }
        return filteredList;
    }

    // Common method
    public static Entry chooseEntryByKeywords(ArrayList<String> tags, boolean isDelete, Entry query,
                                           ArrayList<Entry> entryList) throws MintException {
        ArrayList<Entry> filteredList = filterEntryByKeywords(tags, query, entryList);
        Entry entry = null;
        if (filteredList.size() == 0) {
            throw new MintException(MintException.ERROR_EXPENSE_NOT_IN_LIST);
        } else if (filteredList.size() == 1) {
            Entry onlyEntry = filteredList.get(0);
            if (Ui.isConfirmedToDeleteOrEdit(onlyEntry, isDelete)) {
                entry = onlyEntry;
            }
            return entry;
        }

        Ui.viewGivenList(filteredList);
        try {
            int index = Ui.chooseItemToDeleteOrEdit(filteredList, isDelete);
            if (index >= 0) {
                entry = filteredList.get(index);
            }
        } catch (MintException e) {
            throw new MintException(e.getMessage());
        }
        return entry;
    }

    public double calculateTotalEntry(ArrayList<Entry> entryList) {
        double total = 0;
        for (Entry entry : entryList) {
            total += entry.getAmount();
        }
        return total;
    }

    // common method
    public static String overWriteString(Entry entry) {
        return entry.getCategory() + "|" + entry.getDate() + "|" + entry.getName()
                + "|" + entry.getAmount();
    }

}
