package com.jeremyarevalo.webapp.biblioteca.controller.FXController;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.stereotype.Component;

import com.jeremyarevalo.webapp.biblioteca.System.Main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import lombok.Setter;

@Component
public class IndexController implements Initializable{

    @FXML
    MenuItem btnCategoria, btnCliente, btnEmpleado, btnLibro, btnPrestamo;

    @Setter
    private Main stage;

    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnCategoria){
            stage.categoriaView();
        }else if(event.getSource() == btnCliente){
            stage.clienteView();
        }else if(event.getSource() == btnEmpleado){
            stage.empleadoView();
        }else if(event.getSource() == btnLibro){
            stage.libroView();
        }else if(event.getSource() == btnPrestamo){
            stage.prestamoView();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resources) {

    }

}
