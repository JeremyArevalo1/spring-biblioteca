package com.jeremyarevalo.webapp.biblioteca.controller.FXController;

import java.net.URL;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jeremyarevalo.webapp.biblioteca.System.Main;
import com.jeremyarevalo.webapp.biblioteca.model.Categoria;
import com.jeremyarevalo.webapp.biblioteca.model.Cliente;
import com.jeremyarevalo.webapp.biblioteca.model.Empleado;
import com.jeremyarevalo.webapp.biblioteca.model.Libro;
import com.jeremyarevalo.webapp.biblioteca.model.Prestamo;
import com.jeremyarevalo.webapp.biblioteca.service.ClienteService;
import com.jeremyarevalo.webapp.biblioteca.service.EmpleadoService;
import com.jeremyarevalo.webapp.biblioteca.service.LibroService;
import com.jeremyarevalo.webapp.biblioteca.service.PrestamoService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.Setter;

@Component
public class PrestamoControllerV implements Initializable{

    @Autowired
    PrestamoService prestamoService;
    @Autowired
    LibroService libroService;
    @Autowired
    ClienteService clienteService;
    @Autowired
    EmpleadoService empleadoService;

    @FXML
    TextField tfId, tfFecPrestamo, tfFecDevolucion, tfBuscar;
    @FXML
    ComboBox <String> cmbVigencia;
    @FXML
    ComboBox <Empleado> cmbEmpleado;
    @FXML
    ComboBox <Cliente> cmbCliente;
    //@FXML
    //ComboBox <Libro> cmbLibro;
    @FXML
    Button btnGuardar, btnVaciar, btnBuscar, btnEliminar, btnCancelar;
    @FXML
    TableView tblPrestamo;
    @FXML
    TableColumn colId, colFecPrestamo, colFecDevolucion, colVigencia, colEmpleado, colCliente, colLibro;

    @Setter
    private Main stage;

    @Override
    public void initialize(URL url, ResourceBundle resources) {
        cargarDatos();
        cmbVigencia.setItems(FXCollections.observableArrayList("Activa", "Caducada"));
        cmbEmpleado.setItems(listarEmpleado());
        cmbCliente.setItems(listarCliente());
        //cmbLibro.setItems(listarLibro());
    }

    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnGuardar){
            if(tfId.getText().isBlank()){
                agregarPrestamo();
            }
        }else if(event.getSource() == btnCancelar){
            stage.indexView();
        }
    }

    public void cargarDatos(){
        tblPrestamo.setItems(listarPrestamo());
        colId.setCellValueFactory(new PropertyValueFactory<Libro, Long>("id"));
        colFecPrestamo.setCellValueFactory(new PropertyValueFactory<Libro, Date>("fechaDePrestamo"));
        colFecDevolucion.setCellValueFactory(new PropertyValueFactory<Libro, Date>("fechaDeDevolucion"));
        colVigencia.setCellValueFactory(new PropertyValueFactory<Libro, Boolean>("vigencia"));
        colEmpleado.setCellValueFactory(new PropertyValueFactory<Libro, Empleado>("empleado"));
        colCliente.setCellValueFactory(new PropertyValueFactory<Libro, Cliente>("cliente"));
        //colLibro.setCellValueFactory(new PropertyValueFactory<Libro, List<Libro>>("libro"));
    }

    public void cargarFormEditar(){
        Prestamo prestamo = (Prestamo)tblPrestamo.getSelectionModel().getSelectedItem();
        if(prestamo != null){
            tfId.setText(Long.toString(prestamo.getId()));

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            if (prestamo.getFechaDePrestamo() != null) {
                tfFecPrestamo.setText(sdf.format(prestamo.getFechaDePrestamo()));
            }
            if (prestamo.getFechaDeDevolucion() != null) {
                tfFecDevolucion.setText(sdf.format(prestamo.getFechaDeDevolucion()));
            }


            if (prestamo.getVigencia() != null) {
                if (prestamo.getVigencia()) {
                    cmbVigencia.getSelectionModel().select("Activa");
                } else {
                    cmbVigencia.getSelectionModel().select("Caducada");
                }
            }
            
            cmbEmpleado.getSelectionModel().select(prestamo.getEmpleado());
            cmbCliente.getSelectionModel().select(prestamo.getCliente());
           // List<Libro> libros = prestamo.getLibro();
            /*if (libros != null && !libros.isEmpty()) {
                Libro libroSeleccionado = libros.get(0);
                cmbLibro.getSelectionModel().select(libroSeleccionado);
            }*/

        }
    }

    public int obtenerIndexEmpleado() {
        Empleado empleadoSeleccionado = (Empleado) tblPrestamo.getSelectionModel().getSelectedItem();
        return cmbEmpleado.getItems().indexOf(empleadoSeleccionado);
    }

    public ObservableList<Empleado> listarEmpleado(){
        return FXCollections.observableList(empleadoService.listaEmpleados());
    }

    public int obtenerIndexCliente() {
        Cliente clienteSeleccionado = (Cliente) tblPrestamo.getSelectionModel().getSelectedItem();
        return cmbCliente.getItems().indexOf(clienteSeleccionado);
    }

    public ObservableList<Cliente> listarCliente(){
        return FXCollections.observableList(clienteService.listarCliente());
    }

   /*public int obtenerIndexLibro() {
        Libro libroSeleccionado = (Libro) tblPrestamo.getSelectionModel().getSelectedItem();
        return cmbLibro.getItems().indexOf(libroSeleccionado);
    }

    public ObservableList<Libro> listarLibro(){
        return FXCollections.observableList(libroService.listarLibros());
    }*/

    public void limpiarFomrEditar(){
        tfBuscar.clear();
        tfId.clear();
        tfFecPrestamo.clear();
        tfFecDevolucion.clear();
        cmbVigencia.getSelectionModel().clearSelection();
        cmbEmpleado.getSelectionModel().clearSelection();
        cmbCliente.getSelectionModel().clearSelection();
        //cmbLibro.getSelectionModel().clearSelection();
    }

    public ObservableList<Prestamo> listarPrestamo(){
        return FXCollections.observableList(prestamoService.listarPrestamo());
    }

    public void agregarPrestamo(){
        Prestamo prestamo = new Prestamo();
        prestamo.setFechaDePrestamo(Date.valueOf(tfFecPrestamo.getText()));
        prestamo.setFechaDeDevolucion(Date.valueOf(tfFecDevolucion.getText()));

        String VigenciaSeleccionada = cmbVigencia.getSelectionModel().getSelectedItem();
        if (VigenciaSeleccionada.equalsIgnoreCase("Activa")) {
            prestamo.setVigencia(true);
        } else if (VigenciaSeleccionada.equalsIgnoreCase("Caducada")) {
            prestamo.setVigencia(false);
        }

        Empleado empleadoSeleccionado = cmbEmpleado.getSelectionModel().getSelectedItem();
        if (empleadoSeleccionado != null) {
            prestamo.setEmpleado(empleadoSeleccionado);
        }

        Cliente clienteSeleccionado = cmbCliente.getSelectionModel().getSelectedItem();
        if (clienteSeleccionado != null) {
            prestamo.setCliente(clienteSeleccionado);
        }

        /*Libro libroSeleccionado = cmbLibro.getSelectionModel().getSelectedItem();
        if (libroSeleccionado != null) {
            prestamo.setLibro(Collections.singletonList(libroSeleccionado));
        }*/

        prestamoService.guardarPrestamo(prestamo);
        cargarDatos();
    }
}
