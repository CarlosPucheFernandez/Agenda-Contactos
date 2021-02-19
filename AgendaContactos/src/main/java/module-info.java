module com.mycompany.agendacontactos {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.persistence;
    requires java.base;

    opens com.mycompany.agendacontactos to javafx.fxml, java.sql;
    opens modelos;
    exports com.mycompany.agendacontactos;

}
