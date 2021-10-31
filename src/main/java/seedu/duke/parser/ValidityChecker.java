package seedu.duke.parser;

import seedu.duke.entries.Interval;
import seedu.duke.entries.Type;
import seedu.duke.exception.MintException;
import seedu.duke.utility.Ui;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class ValidityChecker {
    public static final int MIN_CATNUM = 0;
    public static final int MAX_CATNUM = 7;
    private static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    public static final String FILE_PATH = "data" + File.separator + "Mint.txt";
    public static final String ERROR_INVALID_NUMBER = "Invalid number entered! Unable to edit expense.";
    public static final Pattern doublePattern = Pattern.compile("\\d+(\\.\\d+)?");

    public static DateTimeFormatter dateFormatter
            = DateTimeFormatter.ofPattern("[yyyy-MM-dd][yyyy-M-dd][yyyy-MM-d][yyyy-M-d]"
            + "[dd-MM-yyyy][d-MM-yyyy][d-M-yyyy][dd-M-yyyy]"
            + "[dd MMM yyyy][d MMM yyyy][dd MMM yy][d MMM yy]");

    static void checkEmptyName(Parser parser) throws MintException {
        boolean hasEmptyName = parser.name.equals(Parser.STRING_EMPTY);
        if (hasEmptyName) {
            logger.log(Level.INFO, "User entered empty name");
            throw new MintException(MintException.ERROR_NO_NAME);
        }
    }

    static void checkInvalidAmount(Parser parser) throws MintException {
        boolean isDoubleWithoutLetters = doublePattern.matcher(parser.amountStr).matches();
        boolean isEmpty = parser.amountStr == null;
        if ( isEmpty || !isDoubleWithoutLetters) {
            throw new MintException(MintException.ERROR_INVALID_AMOUNT);
        }
    }

    static void checkInvalidDate(Parser parser) throws MintException {
        try {
            LocalDate.parse(parser.dateStr, dateFormatter);
        } catch (DateTimeParseException e) {
            logger.log(Level.INFO, "User entered invalid date");
            throw new MintException(MintException.ERROR_INVALID_DATE);
        }
    }


    static void checkInvalidEndDate(Parser parser) throws MintException {
        try {
            LocalDate parsedEndDate = LocalDate.parse(parser.endDateStr, dateFormatter);
            LocalDate parsedDate = LocalDate.parse(parser.dateStr, dateFormatter);
            if (parsedEndDate.isBefore(parsedDate)) {
                throw new MintException("End date must be after start date.");
            }
        } catch (DateTimeParseException e) {
            logger.log(Level.INFO, "User entered invalid date");
            throw new MintException(MintException.ERROR_INVALID_DATE);
        } catch (MintException e) {
            throw new MintException(e.getMessage());
        }
    }

    public static void checkInvalidCatNum(Parser parser) throws MintException {
        try {
            int catNumInt = Integer.parseInt(parser.catNumStr);
            if (!(catNumInt >= MIN_CATNUM && catNumInt <= MAX_CATNUM)) {
                throw new MintException(MintException.ERROR_INVALID_CATNUM);
            }
        } catch (NumberFormatException e) {
            logger.log(Level.INFO, "User entered invalid category number");
            throw new MintException(MintException.ERROR_INVALID_CATNUM);
        }

    }

    private static void checkInvalidInterval(Parser parser) throws MintException {
        try {
            Interval.valueOf(parser.intervalStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            logger.log(Level.INFO, "User entered invalid interval");
            throw new MintException("Please enter valid interval: MONTH, YEAR");
        }
    }

    static ArrayList<String> identifyValidTags(Parser parser, String userInput,
                                               String[] mandatoryTags) throws MintException {
        ArrayList<String> validTags = new ArrayList<>();
        ArrayList<String> invalidTags = new ArrayList<>();
        String[] tags = parser.isRecurring ? new String[]{"n/", "d/", "a/", "c/", "i/", "e/"}
                : new String[]{"n/", "d/", "a/", "c/"};
        List<String> mandatoryTagsToBeChecked = Arrays.asList(mandatoryTags);

        for (String tag : tags) {
            try {
                if (userInput.contains(tag.trim())) {
                    switch (tag.trim()) {
                    case "n/":
                        checkEmptyName(parser);
                        break;
                    case "d/":
                        checkInvalidDate(parser);
                        break;
                    case "a/":
                        checkInvalidAmount(parser);
                        break;
                    case "c/":
                        checkInvalidCatNum(parser);
                        break;
                    case "i/":
                        checkInvalidInterval(parser);
                        break;
                    case "e/":
                        checkInvalidEndDate(parser);
                        break;
                    default:
                        throw new MintException(MintException.ERROR_INVALID_TAG_ERROR);
                    }
                    validTags.add(tag);
                } else if (mandatoryTagsToBeChecked.contains(tag)) {
                    invalidTags.add(tag);
                }
            } catch (MintException e) {
                invalidTags.add(tag);
            }
        }

        if (invalidTags.size() > 0) {
            Ui.constructErrorMessage(invalidTags);
            throw new MintException(Ui.constructErrorMessage(invalidTags).toString());
        } else if (validTags.size() == 0) {
            throw new MintException("Please enter at least one tag.");
        }
        return validTags;
    }

    static ArrayList<String> checkExistenceAndValidityOfTags(Parser parser, String userInput) throws MintException {
        try {
            String[] mandatoryTags;
            switch (parser.command) {
            case "add":
                mandatoryTags = new String[]{"n/", "a/"};
                break;
            case "addR":
                mandatoryTags = new String[]{"n/", "a/", "i/"};
                break;
            case "set":
                mandatoryTags = new String[]{"c/", "a/"};
                break;
            default:
                mandatoryTags = new String[]{};
            }
            return identifyValidTags(parser, userInput, mandatoryTags);
        } catch (MintException e) {
            throw new MintException(e.getMessage());
        }
    }

    public static void checkValidCatNum(int catNum) throws MintException {
        if (!((catNum > -1) && (catNum < 8))) {
            throw new MintException(ERROR_INVALID_NUMBER);
        }
    }

    public static void checkValidityOfFieldsInNormalListTxt(String type, String name, String date, String amount,
                                                  String catNum) throws MintException {
        if (!((type.equalsIgnoreCase("Income") || type.equalsIgnoreCase("Expense")))) {
            throw new MintException("Unable to load text file! Invalid type detected! "
                    + "Did u accidentally edit the file?");
        }
        if (name.equals("")) {
            throw new MintException("Unable to load text file! Empty description detected! "
                    + "Did u accidentally edit the file?");
        }
        try {
            LocalDate.parse(date, dateFormatter);
            Double.parseDouble(amount);
            checkValidCatNum(Integer.parseInt(catNum));
        } catch (DateTimeParseException e) {
            logger.log(Level.INFO, "User entered invalid date");
            throw new MintException("Unable to load text file! Invalid date detected! "
                    + "Did u accidentally edit the file?");
        } catch (NumberFormatException e) {
            logger.log(Level.INFO, "User entered invalid amount!");
            throw new MintException("Unable to load text file! Invalid amount detected! "
                    + "Did u accidentally edit the file?");
        }
    }

    public static void checkValidityOfFieldsInRecurringListTxt(String interval, String endDate) throws MintException {
        try {
            LocalDate parsedEndDate = LocalDate.parse(endDate, dateFormatter);
            LocalDate parsedDate = LocalDate.parse(endDate, dateFormatter);
            if (parsedEndDate.isBefore(parsedDate)) {
                throw new MintException("Unable to load text file! Invalid date detected! "
                        + "Did u accidentally edit the file?");
            }
            Interval.valueOf(interval.toUpperCase());
        } catch (DateTimeParseException e) {
            logger.log(Level.INFO, "User entered invalid date");
            throw new MintException("Unable to load text file! Invalid date detected! "
                    + "Did u accidentally edit the file?");
        } catch (MintException e) {
            throw new MintException(e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.log(Level.INFO, "User entered invalid interval");
            throw new MintException("Unable to load text file! Invalid interval detected! "
                    + "Did u accidentally edit the file?");
        }
    }

    public static void checkValidityOfFieldsInBudgetListTxt(String catNum, String amount) throws MintException {
        try {
            Double.parseDouble(amount);
            checkValidCatNum(Integer.parseInt(catNum));
        } catch (NumberFormatException e) {
            logger.log(Level.INFO, "User entered invalid amount!");
            throw new MintException("Unable to load text file! Invalid number detected! "
                    + "Did u accidentally edit the file?");
        }
    }
}
