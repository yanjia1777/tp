package seedu.duke;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    /**
     * Main entry-point for the java.duke.Duke application.
     */
    public static void main(String[] args) {
        new Main().run();
    }

    public void run() {
        ArrayList<Expense> expenseList = new ArrayList<>();
        Scanner in = new Scanner(System.in);
        while (true) {
            String userInput = in.nextLine();
            if(userInput.equals("bye")) {
                break;
            } else {
                addExpense(expenseList, userInput);
            }
        }
    }

    private void addExpense(ArrayList<Expense> expenseList, String userInput) {
        //      Expected user input: 0 Water $10
        String[] splitUserInput = userInput.split(" ", 2);
        int catNum = Integer.parseInt(splitUserInput[0]);
        String[] expenseDescriptionAndAmount = splitUserInput[1].split("\\$");
        Double amount = Double.parseDouble(expenseDescriptionAndAmount[1]);
        String description = expenseDescriptionAndAmount[0];

        expenseList.add(new Expense(catNum, description, amount));
//        System.out.println(expenseList);
    }
}
