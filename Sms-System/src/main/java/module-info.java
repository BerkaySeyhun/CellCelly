module CellCelly.CellCelly {
    requires javafx.controls;
    requires javafx.fxml;

    opens tr.com.cellcelly to javafx.fxml;
    exports tr.com.cellcelly;
}
