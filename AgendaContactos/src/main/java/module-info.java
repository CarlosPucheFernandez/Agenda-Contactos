module com.mycompany.agendacontactos {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.mycompany.agendacontactos to javafx.fxml, java.sql;
    exports com.mycompany.agendacontactos;
    requires java.persistence;
}
