package com.biscuittaiger.budgetplanning;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;

public class BudgetPlanning extends Application {
    private ArrayList<BudgetCategory> categories;
    private static final String FILE_NAME = "budget_data.txt";
    private static final String[] FIXED_CATEGORIES = {"Utilities", "Groceries", "Insurance", "Transportation", "Other Expenses"};
    private static final String[] PERIOD_OPTIONS = {"Weekly", "Monthly"};

    public BudgetPlanning() {
        categories = new ArrayList<>();
        loadCategories();
        initializeFixedCategories();
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Budget Planning");

        // Main layout
        VBox mainLayout = new VBox(10);
        mainLayout.setPadding(new Insets(10));

        // Budget setup section
        Label setBudgetLabel = new Label("Set Budget for Categories");
        ComboBox<String> categoryComboBox = new ComboBox<>();
        categoryComboBox.getItems().addAll(FIXED_CATEGORIES);
        TextField budgetAmountField = new TextField();
        budgetAmountField.setPromptText("Enter budget amount");
        ComboBox<String> periodComboBox = new ComboBox<>();
        periodComboBox.getItems().addAll(PERIOD_OPTIONS);
        periodComboBox.setValue(PERIOD_OPTIONS[1]); // Default to Monthly
        Button setBudgetButton = new Button("Set Budget");

        setBudgetButton.setOnAction(e -> {
            String selectedCategory = categoryComboBox.getValue();
            double amount = Double.parseDouble(budgetAmountField.getText());
            String selectedPeriod = periodComboBox.getValue();
            BudgetCategory category = findCategoryByName(selectedCategory);
            if (category != null) {
                category.resetSpentAmount();
                if (selectedPeriod.equals(PERIOD_OPTIONS[0])) { // Weekly
                    categories.set(categoryComboBox.getSelectionModel().getSelectedIndex(), new WeeklyBudgetCategory(category.getName(), amount));
                } else { // Monthly
                    categories.set(categoryComboBox.getSelectionModel().getSelectedIndex(), new MonthlyBudgetCategory(category.getName(), amount));
                }
                saveCategories();
                budgetAmountField.clear();
            }
        });

        GridPane budgetSetupGrid = new GridPane();
        budgetSetupGrid.setHgap(10);
        budgetSetupGrid.setVgap(10);
        budgetSetupGrid.add(setBudgetLabel, 0, 0, 3, 1);
        budgetSetupGrid.add(new Label("Category:"), 0, 1);
        budgetSetupGrid.add(categoryComboBox, 1, 1);
        budgetSetupGrid.add(new Label("Budget Amount:"), 0, 2);
        budgetSetupGrid.add(budgetAmountField, 1, 2);
        budgetSetupGrid.add(new Label("Period:"), 0, 3);
        budgetSetupGrid.add(periodComboBox, 1, 3);
        budgetSetupGrid.add(setBudgetButton, 1, 4);

        // Expense addition section
        Label addExpenseLabel = new Label("Add Expense");
        ComboBox<String> expenseCategoryComboBox = new ComboBox<>();
        expenseCategoryComboBox.getItems().addAll(FIXED_CATEGORIES);
        TextField expenseAmountField = new TextField();
        expenseAmountField.setPromptText("Enter expense amount");
        Button addExpenseButton = new Button("Add Expense");

        addExpenseButton.setOnAction(e -> {
            String selectedCategory = expenseCategoryComboBox.getValue();
            double amount = Double.parseDouble(expenseAmountField.getText());
            BudgetCategory category = findCategoryByName(selectedCategory);
            if (category != null) {
                category.addExpense(amount);
                saveCategories();
                expenseAmountField.clear();
            }
        });

        GridPane expenseGrid = new GridPane();
        expenseGrid.setHgap(10);
        expenseGrid.setVgap(10);
        expenseGrid.add(addExpenseLabel, 0, 0, 2, 1);
        expenseGrid.add(new Label("Category:"), 0, 1);
        expenseGrid.add(expenseCategoryComboBox, 1, 1);
        expenseGrid.add(new Label("Expense Amount:"), 0, 2);
        expenseGrid.add(expenseAmountField, 1, 2);
        expenseGrid.add(addExpenseButton, 1, 3);

        // Show budget status section
        Button showBudgetStatusButton = new Button("Show Budget Status");
        TextArea budgetStatusArea = new TextArea();
        budgetStatusArea.setEditable(false);

        showBudgetStatusButton.setOnAction(e -> {
            StringBuilder status = new StringBuilder();
            for (BudgetCategory category : categories) {
                status.append("Category: ").append(category.getName()).append("\n")
                        .append("Budget: ").append(category.getBudgetAmount()).append("\n")
                        .append("Spent: ").append(category.getSpentAmount()).append("\n")
                        .append("Period: ").append(category.getPeriod()).append("\n")
                        .append("Status: ").append(category.isWithinBudget() ? "Within Budget" : "Over Budget").append("\n\n");
            }
            budgetStatusArea.setText(status.toString());
        });

        // Layout setup
        mainLayout.getChildren().addAll(budgetSetupGrid, expenseGrid, showBudgetStatusButton, budgetStatusArea);

        primaryStage.setScene(new Scene(mainLayout, 400, 500));
        primaryStage.show();
    }

    private void initializeFixedCategories() {
        for (String categoryName : FIXED_CATEGORIES) {
            if (findCategoryByName(categoryName) == null) {
                categories.add(new MonthlyBudgetCategory(categoryName, 0));
            }
        }
    }

    private BudgetCategory findCategoryByName(String name) {
        for (BudgetCategory category : categories) {
            if (category.getName().equalsIgnoreCase(name)) {
                return category;
            }
        }
        return null;
    }

    private void saveCategories() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (BudgetCategory category : categories) {
                writer.println(category.toFileString());
            }
        } catch (IOException e) {
            System.out.println("Error saving categories: " + e.getMessage());
        }
    }

    private void loadCategories() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                categories.add(BudgetCategory.fromFileString(line));
            }
        } catch (IOException e) {
            System.out.println("No existing budget data found. Starting fresh.");
        }
    }
}

