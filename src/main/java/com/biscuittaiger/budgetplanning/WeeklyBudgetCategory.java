package com.biscuittaiger.budgetplanning;

class WeeklyBudgetCategory extends BudgetCategory {
    public WeeklyBudgetCategory(String name, double budgetAmount) {
        super(name, budgetAmount, "weekly");
    }


    @Override
    public void addExpense(double amount) {
        super.addExpense(amount);
        System.out.println("Expense added to weekly category: " + getName());
    }

}