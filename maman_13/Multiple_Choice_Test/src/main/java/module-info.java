module com.example.q1 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.q1 to javafx.fxml;
    exports com.example.q1;
}