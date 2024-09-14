package com.jeremyarevalo.webapp.biblioteca.controller.FXController;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jeremyarevalo.webapp.biblioteca.System.Main;
import com.jeremyarevalo.webapp.biblioteca.model.Categoria;
import com.jeremyarevalo.webapp.biblioteca.model.Libro;
import com.jeremyarevalo.webapp.biblioteca.service.CategoriaService;
import com.jeremyarevalo.webapp.biblioteca.service.LibroService;

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
public class LibroControllerV implements Initializable{

    @Autowired
    CategoriaService categoriaService;
    @Autowired
    LibroService libroService;

    @FXML
    TextField tfId, tfIsbn, tfNombre, tfSipnosis, tfAutor, tfEditorial, tfEstanteria, tfCluster, tfBuscar;
    @FXML
    ComboBox <String> cmbDisponibilidad;
    @FXML
    ComboBox <Categoria> cmbCategoria;
    @FXML
    Button btnGuardar, btnVaciar, btnBuscar, btnEliminar, btnCancelar;
    @FXML
    TableView tblLibros;
    @FXML
    TableColumn colId, colIsbn, colNombre, colSipnosis, colAutor, colEditorial, colDisponibilidad, colEstanteria, colCluster, colCategoria;


    @Setter
    private Main stage;
    
    @Override
    public void initialize(URL url, ResourceBundle resources) {
        cargarDatos();
        cmbDisponibilidad.setItems(FXCollections.observableArrayList("Disponible", "No Disponible"));
        cmbCategoria.setItems(listarCategorias());
    }

    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnGuardar){
            if(tfId.getText().isBlank()){
                agregarLibro();
            }else {
                editarLibro();
            }
        }else if(event.getSource() == btnVaciar){
            limpiarFomrEditar();
        }else if(event.getSource() == btnEliminar){
            eliminarLibro();
        }else if(event.getSource() == btnBuscar) {
            tblLibros.getItems().clear();
            if (tfBuscar.getText().isBlank()) {
                cargarDatos();
            } else {
                tblLibros.getItems().add(buscarLibro());
                colId.setCellValueFactory(new PropertyValueFactory<Libro, Long>("id"));
                colIsbn.setCellValueFactory(new PropertyValueFactory<Libro, String>("isbn"));
                colNombre.setCellValueFactory(new PropertyValueFactory<Libro, String>("nombre"));
                colSipnosis.setCellValueFactory(new PropertyValueFactory<Libro, String>("sinopsis"));
                colAutor.setCellValueFactory(new PropertyValueFactory<Libro, String>("autor"));
                colEditorial.setCellValueFactory(new PropertyValueFactory<Libro, String>("editorial"));
                colDisponibilidad.setCellValueFactory(new PropertyValueFactory<Libro, Boolean>("disponibilidad"));
                colEstanteria.setCellValueFactory(new PropertyValueFactory<Libro, String>("numeroEstanteria"));
                colCluster.setCellValueFactory(new PropertyValueFactory<Libro, String>("cluster"));
                colCategoria.setCellValueFactory(new PropertyValueFactory<Libro, Categoria>("categoria"));
            }
        }else if(event.getSource() == btnCancelar){
            stage.indexView();
        }
    }

    public void cargarDatos(){
        tblLibros.setItems(listarLibros());
        colId.setCellValueFactory(new PropertyValueFactory<Libro, Long>("id"));
        colIsbn.setCellValueFactory(new PropertyValueFactory<Libro, String>("isbn"));
        colNombre.setCellValueFactory(new PropertyValueFactory<Libro, String>("nombre"));
        colSipnosis.setCellValueFactory(new PropertyValueFactory<Libro, String>("sinopsis"));
        colAutor.setCellValueFactory(new PropertyValueFactory<Libro, String>("autor"));
        colEditorial.setCellValueFactory(new PropertyValueFactory<Libro, String>("editorial"));
        colDisponibilidad.setCellValueFactory(new PropertyValueFactory<Libro, Boolean>("disponibilidad"));
        colEstanteria.setCellValueFactory(new PropertyValueFactory<Libro, String>("numeroEstanteria"));
        colCluster.setCellValueFactory(new PropertyValueFactory<Libro, String>("cluster"));
        colCategoria.setCellValueFactory(new PropertyValueFactory<Libro, Categoria>("categoria"));
    }

    public void cargarFormEditar(){
        Libro libro = (Libro)tblLibros.getSelectionModel().getSelectedItem();
        if(libro != null){
            tfId.setText(Long.toString(libro.getId()));
            tfIsbn.setText(libro.getIsbn());
            tfNombre.setText(libro.getNombre());
            tfSipnosis.setText(libro.getSinopsis());
            tfAutor.setText(libro.getAutor());
            tfEditorial.setText(libro.getEditorial());

            if (libro.getDisponibilidad() != null) {
                if (libro.getDisponibilidad()) {
                    cmbDisponibilidad.getSelectionModel().select("Disponible");
                } else {
                    cmbDisponibilidad.getSelectionModel().select("No Disponible");
                }
            }
            
            tfEstanteria.setText(libro.getEditorial());
            tfCluster.setText(libro.getEditorial());
            cmbCategoria.getSelectionModel().select(libro.getCategoria());

        }
    }

    public int obtenerIndexCategoria() {
        Categoria categoriaSeleccionada = (Categoria) tblLibros.getSelectionModel().getSelectedItem();
        return cmbCategoria.getItems().indexOf(categoriaSeleccionada);
    }

    public ObservableList<Categoria> listarCategorias(){
        return FXCollections.observableList(categoriaService.listarCategoria());
    }

    public void limpiarFomrEditar(){
        tfBuscar.clear();
        tfId.clear();
        tfIsbn.clear();
        tfNombre.clear();
        tfSipnosis.clear();
        tfAutor.clear();
        tfEditorial.clear();
        cmbDisponibilidad.getSelectionModel().clearSelection();
        tfEstanteria.clear();
        tfCluster.clear();
        cmbCategoria.getSelectionModel().clearSelection();
    }

    public ObservableList<Libro> listarLibros(){
        return FXCollections.observableList(libroService.listarLibros());
    }

    public void agregarLibro(){
        Libro libro = new Libro();
        libro.setIsbn(tfIsbn.getText());
        libro.setNombre(tfNombre.getText());
        libro.setSinopsis(tfSipnosis.getText());
        libro.setAutor(tfAutor.getText());
        libro.setEditorial(tfEditorial.getText());

        String disponibilidadSeleccionada = cmbDisponibilidad.getSelectionModel().getSelectedItem();
        if (disponibilidadSeleccionada.equalsIgnoreCase("Disponible")) {
            libro.setDisponibilidad(true);
        } else if (disponibilidadSeleccionada.equalsIgnoreCase("No Disponible")) {
            libro.setDisponibilidad(false);
        }

        libro.setNumeroEstanteria(tfEstanteria.getText());
        libro.setCluster(tfCluster.getText());

        Categoria categoriaSeleccionada = cmbCategoria.getSelectionModel().getSelectedItem();
        if (categoriaSeleccionada != null) {
            libro.setCategoria(categoriaSeleccionada);
        }

        libroService.guardarLibro(libro);
        cargarDatos();
    }

    public void editarLibro(){
        Libro libro = libroService.buscarLibro(Long.parseLong(tfId.getText()));
        libro.setIsbn(tfIsbn.getText());
        libro.setNombre(tfNombre.getText());
        libro.setSinopsis(tfSipnosis.getText());
        libro.setAutor(tfAutor.getText());
        libro.setEditorial(tfEditorial.getText());

        String disponibilidadSeleccionada = cmbDisponibilidad.getSelectionModel().getSelectedItem();
        if (disponibilidadSeleccionada.equalsIgnoreCase("Disponible")) {
            libro.setDisponibilidad(true);
        } else if (disponibilidadSeleccionada.equalsIgnoreCase("No Disponible")) {
            libro.setDisponibilidad(false);
        }

        libro.setNumeroEstanteria(tfEstanteria.getText());
        libro.setCluster(tfCluster.getText());

        Categoria categoriaSeleccionada = cmbCategoria.getSelectionModel().getSelectedItem();
        if (categoriaSeleccionada != null) {
            libro.setCategoria(categoriaSeleccionada);
        }

        libroService.guardarLibro(libro);
        cargarDatos();
    }

    public void eliminarLibro(){
        Libro libro = libroService.buscarLibro(Long.parseLong(tfId.getText()));
        libroService.eliminarLibro(libro);
        cargarDatos();
    }

    public Libro buscarLibro(){
        return libroService.buscarLibro(Long.parseLong(tfBuscar.getText()));
    }
}
