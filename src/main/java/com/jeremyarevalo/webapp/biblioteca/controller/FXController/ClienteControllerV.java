package com.jeremyarevalo.webapp.biblioteca.controller.FXController;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jeremyarevalo.webapp.biblioteca.System.Main;
import com.jeremyarevalo.webapp.biblioteca.model.Cliente;
import com.jeremyarevalo.webapp.biblioteca.service.ClienteService;

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
public class ClienteControllerV implements Initializable{

    @FXML
    TextField tfDpi, tfNombre, tfApellido, tfTelefono, tfBuscar;
    @FXML
    Button btnGuardar, btnVaciar, btnBuscar, btnEliminar, btnCancelar;
    @FXML
    TableView tblClientes;
    @FXML
    TableColumn colDpi, colNombre, colApellido, colTelefono;

    @Setter
    private Main stage;

    @Autowired
    ClienteService clienteService;
    
    @Override
    public void initialize(URL url, ResourceBundle resources) {
        cargarDatos();
    }

    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnGuardar){
            if(tfDpi.getText().isBlank()){
                agregarCliente();
            }else{
                editarCliente();
            }
        }else if(event.getSource() == btnVaciar){
            limpiarFomrEditar();
        }else if(event.getSource() == btnEliminar){
            eliminarCliente();
        }else if(event.getSource() == btnBuscar) {
            tblClientes.getItems().clear();
            if (tfBuscar.getText().isBlank()) {
                cargarDatos();
            } else {
                tblClientes.getItems().add(buscarCliente());
                colDpi.setCellValueFactory(new PropertyValueFactory<Cliente, Long>("dpi"));
                colNombre.setCellValueFactory(new PropertyValueFactory<Cliente, String>("nombre"));
                colApellido.setCellValueFactory(new PropertyValueFactory<Cliente, String>("apellido"));
                colTelefono.setCellValueFactory(new PropertyValueFactory<Cliente, String>("numero"));
            }
        }else if(event.getSource() == btnCancelar){
            stage.indexView();
        }
    }

    public void cargarDatos(){
        tblClientes.setItems(listarClientes());
        colDpi.setCellValueFactory(new PropertyValueFactory<Cliente, Long>("dpi"));
        colNombre.setCellValueFactory(new PropertyValueFactory<Cliente, String>("nombre"));
        colApellido.setCellValueFactory(new PropertyValueFactory<Cliente, String>("apellido"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<Cliente, String>("numero"));
    }

    public void cargarFormEditar(){
        Cliente cliente = (Cliente)tblClientes.getSelectionModel().getSelectedItem();
        if(cliente != null){
            tfDpi.setText(Long.toString(cliente.getDpi()));
            tfNombre.setText(cliente.getNombre());
            tfApellido.setText(cliente.getApellido());
            tfTelefono.setText(cliente.getNumero());
        }
    }

    public void limpiarFomrEditar(){
        tfBuscar.clear();
        tfDpi.clear();
        tfNombre.clear();
        tfApellido.clear();
        tfTelefono.clear();
    }

    public ObservableList<Cliente> listarClientes(){
        return FXCollections.observableList(clienteService.listarCliente());
    }

    public void agregarCliente(){
        Cliente cliente = new Cliente();
        cliente.setNombre(tfNombre.getText());
        cliente.setApellido(tfApellido.getText());
        cliente.setNumero(tfTelefono.getText());
        clienteService.GuardarCliente(cliente);
        cargarDatos();
    }

    public void editarCliente(){
        Cliente cliente = clienteService.buscarClientePorId(Long.parseLong(tfDpi.getText()));
        cliente.setNombre(tfNombre.getText());
        cliente.setApellido(tfApellido.getText());
        cliente.setNumero(tfTelefono.getText());
        clienteService.GuardarCliente(cliente);
        cargarDatos();
    }

    public void eliminarCliente(){
        Cliente cliente = clienteService.buscarClientePorId(Long.parseLong(tfDpi.getText()));
        clienteService.eliminarCliente(cliente);
        cargarDatos();
    }

    public Cliente buscarCliente() {
        return clienteService.buscarClientePorId(Long.parseLong(tfBuscar.getText()));
    }

    
}
