module ua.lpnu.lab7 {
    requires javafx.controls;
    requires javafx.fxml;


    opens ua.lpnu.lab7 to javafx.fxml;
    exports ua.lpnu.lab7;
}