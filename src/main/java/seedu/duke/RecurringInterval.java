package seedu.duke;

public enum RecurringInterval {
    WEEK("WEEK"),
    MONTH("MONTH"),
    YEAR("YEAR");

    public final String label;

    private RecurringInterval(String label) {
        this.label = label;
    }
}
