package com.biscuittaiger.budgetplanning;

class WeeklyBudgetCategory extends BudgetCategory {
    public WeeklyBudgetCategory(String name, double budgetAmount) {
        super(name, budgetAmount, "weekly");
    }

    public WeeklyBudgetCategory(String name, double budgetAmount, double spentAmount) {
        super(name, budgetAmount, spentAmount, "weekly");
    }

    @Override
    public void displayCategoryInfo() {
        System.out.println("Category: " + getName());
        System.out.println("Budget: " + getBudgetAmount());
        System.out.println("Spent: " + getSpentAmount());
        System.out.println("Period: " + getPeriod());
        if (isWithinBudget()) {
            System.out.println("Status: Within Budget");
        } else {
            System.out.println("Status: Over Budget");
        }
    }
}
