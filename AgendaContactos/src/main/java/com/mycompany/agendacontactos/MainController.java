package com.mycompany.agendacontactos;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import gestorBD.gestorBD;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import modelos.Contacto;

/**
 * FXML Controller class
 *
 * @author carlo
 */
public class MainController implements Initializable {

    @FXML
    private VBox contenedorEmail;
    @FXML
    private VBox contenedorTelefono;
    @FXML
    private ScrollPane spEmail;
    @FXML
    private Button btnAgregarEmail;
    @FXML
    private Button btnAgregarTelefono;
    @FXML
    private Button guardar;
    @FXML
    private TextField email0;
    @FXML
    private TextField telefono0;
    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tfApellidos;
    @FXML
    private TextField tfEmpresa;
    @FXML
    private TableColumn<Contacto, String> colNombre;
    @FXML
    private TableColumn<Contacto, String> colApellidos;
    @FXML
    private TableColumn<Contacto, String> colEmpresa;
    @FXML
    private TableColumn<Contacto, ArrayList> colEmails;
    @FXML
    private TableColumn<Contacto, ArrayList> colTelefonos;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnEliminar;
    @FXML
    private TableView<Contacto> tblContactos;
    @FXML
    private ScrollPane scTelefono;
    @FXML
    private Label lblError;

    private boolean todoOk = false;
    private int contadorEmails = 1;
    private int contadorTelefonos = 1;
    private gestorBD gbd = new gestorBD();
    private ArrayList<TextField> tfemails = new ArrayList<TextField>();
    private ArrayList<TextField> tftelefonos = new ArrayList<TextField>();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        spEmail.setStyle("-fx-background-color:transparent;");
        scTelefono.setStyle("-fx-background-color:transparent;");
        /*
        * comprobar editar
        * actualizar tabla tras accion
         */
        lblError.setVisible(false);
        tfemails.add(email0);
        tftelefonos.add(telefono0);

        this.colNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        this.colApellidos.setCellValueFactory(new PropertyValueFactory("apellidos"));
        this.colEmpresa.setCellValueFactory(new PropertyValueFactory("empresa"));
        this.colEmails.setCellValueFactory(new PropertyValueFactory("emailFormateado"));
        this.colTelefonos.setCellValueFactory(new PropertyValueFactory("telefonoFormateado"));
        colNombre.prefWidthProperty().bind(tblContactos.widthProperty().divide(8)); // w * 1/10
        colApellidos.prefWidthProperty().bind(tblContactos.widthProperty().divide(4)); // w * 1/3
        colEmpresa.prefWidthProperty().bind(tblContactos.widthProperty().divide(7)); // w * 1/10
        colEmails.prefWidthProperty().bind(tblContactos.widthProperty().divide(4)); // w * 1/4
        colTelefonos.prefWidthProperty().bind(tblContactos.widthProperty().divide(4)); // w * 1/4

        tblContactos.setItems(gbd.mostrarDatos());
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
            Contacto c = new Contacto(tfNombre.getText(), tfApellidos.getText(), tfEmpresa.getText(), leerTelefono(), leerEmail());
            System.out.println(c.toString());
            gbd.guardar(c);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Guardar");
            alert.setHeaderText("");
            alert.setContentText("Contacto agregado correctamente");
            alert.showAndWait();
            actualizarTabla();
            vaciarTf();
        }
    }

    @FXML
    private void EditarElemento(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("editarContacto.fxml"));
            Parent root;
            root = fxmlLoader.load();
            EditarContactoController controlador = fxmlLoader.getController();
            controlador.initComponentes(tblContactos.getSelectionModel().getSelectedItem());
            Stage stage = new Stage();
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initStyle(StageStyle.DECORATED);
            stage.setTitle("Editar Contacto");
            stage.setScene(new Scene(root));
            stage.showAndWait();
            actualizarTabla();
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void EliminarElemento(ActionEvent event) {
        gbd.eliminar(tblContactos.getSelectionModel().getSelectedItem());
        actualizarTabla();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Eliminar");
        alert.setHeaderText("");
        alert.setContentText("Contacto eliminado correctamente");
        alert.showAndWait();
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

    private void actualizarTabla() {
        tblContactos.getItems().clear();
        tblContactos.setItems(gbd.mostrarDatos());
    }

    private void vaciarTf() {
        tfNombre.setText("");
        tfApellidos.setText("");
        tfEmpresa.setText("");
        email0.setText("");
        telefono0.setText("");
        
        for (int i = 1; i < tfemails.size(); i++) {
          contenedorEmail.getChildren().remove(i);
        }
        
        for (int i = 1; i < tftelefonos.size(); i++) {
          contenedorTelefono.getChildren().remove(i);
        }
    }
}
