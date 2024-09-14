package com.jeremyarevalo.webapp.biblioteca.controller.FXController;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jeremyarevalo.webapp.biblioteca.System.Main;
import com.jeremyarevalo.webapp.biblioteca.model.Cliente;
import com.jeremyarevalo.webapp.biblioteca.model.Empleado;
import com.jeremyarevalo.webapp.biblioteca.service.EmpleadoService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.Setter;

@Component
public class EmpleadoControllerV implements Initializable{

    @FXML
    TextField tfId, tfNombre, tfApellido, tfTelefono, tfDireccion, tfDpi, tfBuscar;
    @FXML
    Button btnGuardar, btnVaciar, btnBuscar, btnEliminar, btnCancelar;
    @FXML
    TableView tblEmpleados;
    @FXML
    TableColumn colId, colNombre, colApellido, colTelefono, colDireccion, colDpi;

    @Setter
    private Main stage;

    @Autowired
    EmpleadoService empleadoService;
    
    @Override
    public void initialize(URL url, ResourceBundle resources) {
        cargarDatos();
    }

    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnGuardar){
            if(tfId.getText().isBlank()){
                agregarEmpleado();
            }else{
                editarEmpleado();
            }
        }else if(event.getSource() == btnVaciar){
            limpiarFomrEditar();
        }else if(event.getSource() == btnEliminar){
            eliminarEmpleado();
        }else if(event.getSource() == btnBuscar) {
            tblEmpleados.getItems().clear();
            if (tfBuscar.getText().isBlank()) {
                cargarDatos();
            } else {
                tblEmpleados.getItems().add(buscarEmpleado());
                colId.setCellValueFactory(new PropertyValueFactory<Empleado, Long>("id"));
                colNombre.setCellValueFactory(new PropertyValueFactory<Empleado, String>("nombre"));
                colApellido.setCellValueFactory(new PropertyValueFactory<Empleado, String>("apellido"));
                colTelefono.setCellValueFactory(new PropertyValueFactory<Empleado, String>("telefono"));
                colDireccion.setCellValueFactory(new PropertyValueFactory<Empleado, String>("direccion"));
                colDpi.setCellValueFactory(new PropertyValueFactory<Empleado, String>("dpi"));
            }
        }else if(event.getSource() == btnCancelar){
            stage.indexView();
        }
    }

    public void cargarDatos(){
        tblEmpleados.setItems(listarEmpleado());
        colId.setCellValueFactory(new PropertyValueFactory<Empleado, Long>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<Empleado, String>("nombre"));
        colApellido.setCellValueFactory(new PropertyValueFactory<Empleado, String>("apellido"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<Empleado, String>("telefono"));
        colDireccion.setCellValueFactory(new PropertyValueFactory<Empleado, String>("direccion"));
        colDpi.setCellValueFactory(new PropertyValueFactory<Empleado, String>("dpi"));
    }

    public void cargarFormEditar(){
        Empleado empleado = (Empleado)tblEmpleados.getSelectionModel().getSelectedItem();
        if(empleado != null){
            tfId.setText(Long.toString(empleado.getId()));
            tfNombre.setText(empleado.getNombre());
            tfApellido.setText(empleado.getApellido());
            tfTelefono.setText(empleado.getTelefono());
            tfDireccion.setText(empleado.getDireccion());
            tfDpi.setText(empleado.getDpi());
        }
    }

    public void limpiarFomrEditar(){
        tfBuscar.clear();
        tfId.clear();
        tfNombre.clear();
        tfApellido.clear();
        tfTelefono.clear();
        tfDireccion.clear();
        tfDpi.clear();
    }

    public ObservableList<Empleado> listarEmpleado(){
        return FXCollections.observableList(empleadoService.listaEmpleados());
    }

    public void agregarEmpleado(){
        Empleado empleado = new Empleado();
        empleado.setNombre(tfNombre.getText());
        empleado.setApellido(tfApellido.getText());
        empleado.setTelefono(tfTelefono.getText());
        empleado.setDireccion(tfDireccion.getText());
        empleado.setDpi(tfDpi.getText());
        empleadoService.GuardarEmpleado(empleado);
        cargarDatos();
    }

    public void editarEmpleado(){
        Empleado empleado = empleadoService.buscarEmpleadoPorId(Long.parseLong(tfId.getText()));
        empleado.setNombre(tfNombre.getText());
        empleado.setApellido(tfApellido.getText());
        empleado.setTelefono(tfTelefono.getText());
        empleado.setDireccion(tfDireccion.getText());
        empleado.setDpi(tfDpi.getText());
        empleadoService.GuardarEmpleado(empleado);
        cargarDatos();
    }

    public void eliminarEmpleado(){
        Empleado empleado = empleadoService.buscarEmpleadoPorId(Long.parseLong(tfId.getText()));
        empleadoService.eliminarEmpleado(empleado);
        cargarDatos();
    }

    public Empleado buscarEmpleado(){
        return empleadoService.buscarEmpleadoPorId(Long.parseLong(tfBuscar.getText()));
    }
}
