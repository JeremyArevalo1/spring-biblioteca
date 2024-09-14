package com.jeremyarevalo.webapp.biblioteca.controller.FXController;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jeremyarevalo.webapp.biblioteca.System.Main;
import com.jeremyarevalo.webapp.biblioteca.model.Categoria;
import com.jeremyarevalo.webapp.biblioteca.model.Cliente;
import com.jeremyarevalo.webapp.biblioteca.service.CategoriaService;

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
public class CategoriaControllerV implements Initializable{

    @FXML
    TextField tfId, tfNombre, tfBuscar;
    @FXML
    Button btnGuardar, btnVaciar, btnBuscar, btnEliminar, btnCancelar;
    @FXML
    TableView tblCategotias;
    @FXML
    TableColumn colId, colNombre;

    @Setter
    private Main stage;

    @Autowired
    CategoriaService categoriaService;
    
    @Override
    public void initialize(URL url, ResourceBundle resources) {
        cargarDatos();

    }

    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnGuardar){
            if(tfId.getText().isBlank()){
                agregarCategoria();
            }else{
                editarCategoria();
            }
        }else if(event.getSource() == btnVaciar){
            limpiarFomrEditar();
        }else if(event.getSource() == btnEliminar){
            eliminarCategoria();
        }else if(event.getSource() == btnBuscar) {
            tblCategotias.getItems().clear();
            if (tfBuscar.getText().isBlank()) {
                cargarDatos();
            } else {
                tblCategotias.getItems().add(buscarCategoria());
                colId.setCellValueFactory(new PropertyValueFactory<Categoria, Long>("id"));
                colNombre.setCellValueFactory(new PropertyValueFactory<Categoria, String>("nombreCategoria"));
            }
        }else if(event.getSource() == btnCancelar){
            stage.indexView();
        }
    }

    public void cargarDatos(){
        tblCategotias.setItems(listarCategorias());
        colId.setCellValueFactory(new PropertyValueFactory<Categoria, Long>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<Categoria, String>("nombreCategoria"));
    }

    public void cargarFormEditar(){
        Categoria categoria = (Categoria)tblCategotias.getSelectionModel().getSelectedItem();
        if(categoria != null){
            tfId.setText(Long.toString(categoria.getId()));
            tfNombre.setText(categoria.getNombreCategoria());
        }
    }

    public void limpiarFomrEditar(){
        tfBuscar.clear();
        tfId.clear();
        tfNombre.clear();
    }

    public ObservableList<Categoria> listarCategorias(){
        return FXCollections.observableList(categoriaService.listarCategoria());
    }

    public void agregarCategoria(){
        Categoria categoria = new Categoria();
        categoria.setNombreCategoria(tfNombre.getText());
        categoriaService.guardarCategoria(categoria);
        cargarDatos();
    }

    public void editarCategoria(){
        Categoria categoria = categoriaService.buscarCategoriaPorId(Long.parseLong(tfId.getText()));
        categoria.setNombreCategoria(tfNombre.getText());
        categoriaService.guardarCategoria(categoria);
        cargarDatos();
    }

    public void eliminarCategoria(){
        Categoria categoria = categoriaService.buscarCategoriaPorId(Long.parseLong(tfId.getText()));
        categoriaService.eliminarCategoria(categoria);
        cargarDatos();
    }

    public Categoria buscarCategoria(){
        return categoriaService.buscarCategoriaPorId(Long.parseLong(tfBuscar.getText()));
    }

}
