package seedu.duke.model.entries;

import seedu.duke.utility.MintException;

//@@author pos0414

/**
 * Enumeration for available intervals of recurring entries (either month or year)
 */
public enum Interval {
    MONTH("MONTH"),
    YEAR("YEAR");

    public final String label;

    Interval(String label) {
        this.label = label;
    }

    public static Interval determineInterval(String interval) throws MintException {
        switch (interval.toUpperCase()) {
        case "MONTH":
            return MONTH;
        case "YEAR":
            return YEAR;
        default:
            throw new MintException(MintException.ERROR_INVALID_INTERVAL);
        }
    }
}

