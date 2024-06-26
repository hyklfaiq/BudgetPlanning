package com.biscuittaiger.budgetplanning;

abstract class BudgetCategory {
    private String name;
    private double budgetAmount;
    protected double spentAmount;
    private String period;

    public BudgetCategory(String name, double budgetAmount, String period) {
        this.name = name;
        this.budgetAmount = budgetAmount;
        this.spentAmount = 0;
        this.period = period;
    }

    public BudgetCategory(String name, double budgetAmount, double spentAmount, String period) {
        this.name = name;
        this.budgetAmount = budgetAmount;
        this.spentAmount = spentAmount;
        this.period = period;
    }

    public String getName() {
        return name;
    }

    public double getBudgetAmount() {
        return budgetAmount;
    }

    public double getSpentAmount() {
        return spentAmount;
    }

    public String getPeriod() {
        return period;
    }

    public void addExpense(double amount) {
        this.spentAmount += amount;
    }

    public boolean isWithinBudget() {
        return this.spentAmount <= this.budgetAmount;
    }

    public abstract void displayCategoryInfo();

    public String toFileString() {
        return name + "," + budgetAmount + "," + spentAmount + "," + period;
    }

    public static BudgetCategory fromFileString(String line) {
        String[] parts = line.split(",");
        String name = parts[0];
        double budgetAmount = Double.parseDouble(parts[1]);
        double spentAmount = Double.parseDouble(parts[2]);
        String period = parts[3];

        if (period.equals("weekly")) {
            return new WeeklyBudgetCategory(name, budgetAmount, spentAmount);
        } else {
            return new MonthlyBudgetCategory(name, budgetAmount, spentAmount);
        }
    }
}
