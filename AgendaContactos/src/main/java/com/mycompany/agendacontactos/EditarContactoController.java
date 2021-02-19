/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.agendacontactos;

import gestorBD.gestorBD;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import modelos.Contacto;

/**
 * FXML Controller class
 *
 * @author carlo
 */
public class EditarContactoController implements Initializable {

    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tfApellidos;
    @FXML
    private TextField tfEmpresa;
    @FXML
    private ScrollPane spEmail;
    @FXML
    private VBox contenedorEmail;
    @FXML
    private TextField email0;
    @FXML
    private ScrollPane scTelefono;
    @FXML
    private VBox contenedorTelefono;
    @FXML
    private TextField telefono0;
    @FXML
    private Label lblError;
    @FXML
    private Button btnAgregarEmail;
    @FXML
    private Button btnAgregarTelefono;
    @FXML
    private Button guardar;

    private boolean todoOk = false;
    private int contadorEmails = 1;
    private int contadorTelefonos = 1;
    private gestorBD gbd = new gestorBD();
    private ArrayList<TextField> tfemails = new ArrayList<TextField>();
    private ArrayList<TextField> tftelefonos = new ArrayList<TextField>();
    Contacto contactoEditar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        spEmail.setStyle("-fx-background-color:transparent;");
        scTelefono.setStyle("-fx-background-color:transparent;");
        lblError.setVisible(false);
        tfemails.add(email0);
        tftelefonos.add(telefono0);
    }

    @FXML
    private void comprobarEmail(KeyEvent event) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        if (!email0.getText().matches(regex)) {
            lblError.setVisible(true);
            lblError.setText("El email no es correcto");
            todoOk = false;
        } else {
            lblError.setVisible(false);
            todoOk = true;
        }
    }

    @FXML
    private void comprobarTelefono(KeyEvent event) {

        if (telefono0.getText().length() != 9) {
            lblError.setVisible(true);
            lblError.setText("El telefono no es correcto");
            todoOk = false;
        } else {
            lblError.setVisible(false);
            todoOk = true;
        }
    }

    @FXML
    private void AgregarEmail(ActionEvent event) {
        crearNuevoTextField(contenedorEmail, "email", contadorEmails);
        contadorEmails++;
    }

    @FXML
    private void AgregarTelefono(ActionEvent event) {
        crearNuevoTextField(contenedorTelefono, "telefono", contadorTelefonos);
        contadorTelefonos++;
    }

    @FXML
    private void guardar(ActionEvent event) {
        comprobarRequeridos();
        if (todoOk) {
            gbd.editar(contactoEditar, tfNombre.getText(), tfApellidos.getText(), tfEmpresa.getText(), leerEmail(), leerTelefono());

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Guardar");
            alert.setHeaderText("");
            alert.setContentText("Contacto agregado correctamente");
            alert.showAndWait();
        }
    }

    private void crearNuevoTextField(VBox contenedor, String nombre, int contador) {
        TextField nuevo = new TextField();
        HBox hb = new HBox();
        Label lbl = new Label();

        lbl.setMaxWidth(76);
        lbl.setPrefSize(76, 25);
        nuevo.setId(nombre + "" + contador);
        nuevo.setPrefSize(401, 25);
        nuevo.setMaxWidth(401);

        if (nombre.equals("email")) {
            tfemails.add(nuevo);
        } else {
            tftelefonos.add(nuevo);
        }

        hb.getChildren().add(lbl);
        hb.getChildren().add(nuevo);
        contenedor.getChildren().add(hb);
    }

    private void crearNuevoTextFieldInit(VBox contenedor, String nombre, int contador, String valor) {
        TextField nuevo = new TextField();
        HBox hb = new HBox();
        Label lbl = new Label();

        lbl.setMaxWidth(63);
        lbl.setPrefSize(63, 25);
        nuevo.setId(nombre + "" + contador);
        nuevo.setPrefSize(401, 25);
        nuevo.setMaxWidth(401);
        nuevo.setText(valor);

        if (nombre.equals("email")) {
            tfemails.add(nuevo);
        } else {
            tftelefonos.add(nuevo);
        }

        hb.getChildren().add(lbl);
        hb.getChildren().add(nuevo);
        contenedor.getChildren().add(hb);
    }

    public ArrayList<String> leerEmail() {
        ArrayList<String> emails = new ArrayList();
        for (int i = 0; i < contadorEmails; i++) {
            emails.add(tfemails.get(i).getText());
        }
        return emails;
    }

    public ArrayList<String> leerTelefono() {
        ArrayList<String> telefonos = new ArrayList();
        for (int i = 0; i < contadorTelefonos; i++) {
            telefonos.add(tftelefonos.get(i).getText());
        }
        return telefonos;
    }

    private void comprobarRequeridos() {
        if (tfNombre.getText().isBlank() || tfApellidos.getText().isBlank() || tfEmpresa.getText().isBlank() || email0.getText().isBlank() || telefono0.getText().isBlank()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setTitle("Cuidado");
            alert.setContentText("rellene correctamente todos los parametros obligatorios");
            alert.showAndWait();
            todoOk = false;
        }
    }

    public void initComponentes(Contacto c) {
        contactoEditar = c;
        
        tfNombre.setText(c.getNombre());
        tfApellidos.setText(c.getApellidos());
        tfEmpresa.setText(c.getEmpresa());
        
        if (c.getEmails().size() == 1) {
            email0.setText(c.getEmails().get(0));
        } else {
            email0.setText(c.getEmails().get(0));
            for (int i = 1; i < c.getEmails().size(); i++) {
                crearNuevoTextFieldInit(contenedorEmail, "email", contadorEmails, c.getEmails().get(i));
            }
        }
        
        if (c.getTelefonos().size() == 1) {
            telefono0.setText(c.getTelefonos().get(0));
        } else {
            telefono0.setText(c.getTelefonos().get(0));
            for (int i = 1; i < c.getTelefonos().size(); i++) {
                crearNuevoTextFieldInit(contenedorTelefono, "telefono", contadorTelefonos, c.getTelefonos().get(1));
            }
        }

    }
}
