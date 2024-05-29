package com.biscuittaiger.budgetplanning;

class MonthlyBudgetCategory extends BudgetCategory {
    public MonthlyBudgetCategory(String name, double budgetAmount) {
        super(name, budgetAmount, "monthly");
    }


    @Override
    public void addExpense(double amount) {
        super.addExpense(amount);
        System.out.println("Expense added to monthly category: " + getName());
    }
}
