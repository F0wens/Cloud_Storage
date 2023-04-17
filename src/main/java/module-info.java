module com.mycompany.cloudstorage {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;

    opens com.mycompany.cloudstorage to javafx.fxml;
    exports com.mycompany.cloudstorage;
}
