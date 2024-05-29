module com.biscuittaiger.budgetplanning {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.biscuittaiger.budgetplanning to javafx.fxml;
    exports com.biscuittaiger.budgetplanning;
}