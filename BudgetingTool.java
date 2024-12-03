package com.example;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class BudgetingTool {
    public static void main(String[] args){
        // setting up the window
        JFrame frame = new JFrame("Budgeting Tool");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Display window
        frame.setSize(400,500);
        frame.setLayout(new GridLayout(4, 1)); // 4 sections: income, expense, result, button

        // panels for income, expense and the buttons 
        JPanel incomePanel = new JPanel(new GridLayout(4, 2));
        JPanel expensePanel = new JPanel(new GridLayout(4, 2));
        JPanel resultPanel = new JPanel(new GridLayout(4, 1));
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));


        // Income inputs 
        JLabel wagesLabel = new JLabel("Wages:");
        JTextField wagesField = new JTextField();
        JLabel loanLabel = new JLabel("Loan:");
        JTextField loanField = new JTextField();
        JLabel rentalIncomeLabel = new JLabel("Rental Income:");
        JTextField rentalIncomeField = new JTextField();

        // Time period selection for income
        String[] timePeriods = {"Weekly", "Monthly", "Yearly"};
        JComboBox wagesTimePeriod = new JComboBox(timePeriods);
        JComboBox loanTimePeriod = new JComboBox(timePeriods);
        JComboBox rentalIncomeTimePeriod = new JComboBox(timePeriods);

        // undo button 
        JButton incomeUndo = new JButton("Undo");

        // Add income features to panel
        incomePanel.setBorder(BorderFactory.createTitledBorder("Income"));
        incomePanel.add(wagesLabel);
        incomePanel.add(wagesField);
        incomePanel.add(wagesTimePeriod);
        incomePanel.add(loanLabel);
        incomePanel.add(loanField);
        incomePanel.add(loanTimePeriod);
        incomePanel.add(rentalIncomeLabel);
        incomePanel.add(rentalIncomeField);
        incomePanel.add(rentalIncomeTimePeriod);
        incomePanel.add(incomeUndo);



        // Expense inputs
        JLabel foodLabel = new JLabel("Food:");
        JTextField foodField = new JTextField();
        JLabel transportLabel = new JLabel("Transport:");
        JTextField transportField = new JTextField();
        JLabel utilityBillLabel = new JLabel("Utility Bill:");
        JTextField utilityBillField = new JTextField();

        // Time period selection for expenses
        JComboBox foodTimePeriod = new JComboBox(timePeriods);
        JComboBox transportTimePeriod = new JComboBox(timePeriods);
        JComboBox utilityBillTimePeriod = new JComboBox(timePeriods);

        //undo button
        JButton expenseUndo = new JButton("Undo");

        // Add expense features to panel
        expensePanel.setBorder(BorderFactory.createTitledBorder("Expense"));
        expensePanel.add(foodLabel);
        expensePanel.add(foodField);
        expensePanel.add(foodTimePeriod);
        expensePanel.add(transportLabel);
        expensePanel.add(transportField);
        expensePanel.add(transportTimePeriod);
        expensePanel.add(utilityBillLabel);
        expensePanel.add(utilityBillField);
        expensePanel.add(utilityBillTimePeriod);
        expensePanel.add(expenseUndo);


        // Result labels
        JLabel totalIncomeLabel = new JLabel("Total Income: $0.00");
        JLabel totalExpenseLabel = new JLabel("Total Expenses: $0.00");
        JLabel surplusLabel = new JLabel("Surplus/Deficit: $0.00");
        JButton resultUndo = new JButton("Undo");

        resultPanel.add(totalIncomeLabel);
        resultPanel.add(totalExpenseLabel);
        resultPanel.add(surplusLabel);
        resultPanel.add(resultUndo);


        // Buttons
        JButton calculateButton = new JButton("Calculate");
        JButton undoButton = new JButton("Undo");

        // Add buttons to the panel
        buttonPanel.add(calculateButton);
        buttonPanel.add(undoButton);

        // Add action listener for calculate button
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Validate and parse income fields
                double wages = validateAndParseInput(wagesField.getText());
                double loan = validateAndParseInput(loanField.getText());
                double rentalIncome = validateAndParseInput(rentalIncomeField.getText());
            
                if (wages == -1 || loan == -1 || rentalIncome == -1) {
                    JOptionPane.showMessageDialog(frame, "Please enter valid numbers for all income fields", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return; // Exit method if input is invalid
                }
            
                // Adjust based on selected time period
                wages = adjustBasedOnTimePeriod(wages, wagesTimePeriod.getSelectedItem().toString());
                loan = adjustBasedOnTimePeriod(loan, loanTimePeriod.getSelectedItem().toString());
                rentalIncome = adjustBasedOnTimePeriod(rentalIncome, rentalIncomeTimePeriod.getSelectedItem().toString());
            
                // Validate and parse expense fields
                double food = validateAndParseInput(foodField.getText());
                double transport = validateAndParseInput(transportField.getText());
                double utilityBill = validateAndParseInput(utilityBillField.getText());
            
                if (food == -1 || transport == -1 || utilityBill == -1) {
                    JOptionPane.showMessageDialog(frame, "Please enter valid numbers for all expense fields", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return; // Exit method if input is invalid
                }
            
                // Adjust based on selected time period
                food = adjustBasedOnTimePeriod(food, foodTimePeriod.getSelectedItem().toString());
                transport = adjustBasedOnTimePeriod(transport, transportTimePeriod.getSelectedItem().toString());
                utilityBill = adjustBasedOnTimePeriod(utilityBill, utilityBillTimePeriod.getSelectedItem().toString());
            
                // Calculate totals
                double totalIncome = wages + loan + rentalIncome;
                double totalExpenses = food + transport + utilityBill;
                double surplus = totalIncome - totalExpenses;
            
                // Update labels
                totalIncomeLabel.setText(String.format("Total Income: $%.2f", totalIncome));
                totalExpenseLabel.setText(String.format("Total Expenses: $%.2f", totalExpenses));
            
                // Update surplus label with color
                surplusLabel.setText(String.format("Surplus/Deficit: $%.2f", surplus));
                if (surplus < 0) {
                    surplusLabel.setForeground(java.awt.Color.RED);
                } else {
                    surplusLabel.setForeground(java.awt.Color.BLACK);
                }
            }
        });

        //undo button actionlistener
        undoButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                // income fields goes blank
                wagesField.setText(" ");
                loanField.setText(" ");
                rentalIncomeField.setText(" ");

                //expense field goes blank
                foodField.setText(" ");
                transportField.setText(" ");
                utilityBillField.setText(" ");

                 // labels go back to normal
                totalIncomeLabel.setText("Total Income: $0.00");
                totalExpenseLabel.setText("Total Expenses: $0.00");
                surplusLabel.setText("Surplus/Deficit: $0.00");
            }
        });

        incomeUndo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                // income fields goes blank
                wagesField.setText(" ");
                loanField.setText(" ");
                rentalIncomeField.setText(" ");
                
            }
        });

        expenseUndo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                //expense field goes blank
                foodField.setText(" ");
                transportField.setText(" ");
                utilityBillField.setText(" "); 
                
            }
        });

        resultUndo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                /// labels go back to normal
                totalIncomeLabel.setText("Total Income: $0.00");
                totalExpenseLabel.setText("Total Expenses: $0.00");
                surplusLabel.setText("Surplus/Deficit: $0.00");
            }
        });

        // Add components to the frame
        frame.add(incomePanel);
        frame.add(expensePanel);
        frame.add(resultPanel);
        frame.add(buttonPanel);

        // Make the frame visible
        frame.setVisible(true);
    }

    private static double adjustBasedOnTimePeriod(double amount, String timePeriod) {
        switch (timePeriod) {
            case "Weekly":
                return amount * 52; 
            case "Monthly":
                return amount * 12; 
            case "Yearly":
                return amount; 
            default:
                return amount;
        }
    }

    private static double validateAndParseInput(String input) {
        try {
            return Double.parseDouble(input);
        } catch (NumberFormatException e) {
            return -1; // Return -1 if input is invalid
        }
    }
}
