package com.jeremyarevalo.webapp.biblioteca.System;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import com.jeremyarevalo.webapp.biblioteca.BibliotecaApplication;
import com.jeremyarevalo.webapp.biblioteca.controller.FXController.CategoriaControllerV;
import com.jeremyarevalo.webapp.biblioteca.controller.FXController.ClienteControllerV;
import com.jeremyarevalo.webapp.biblioteca.controller.FXController.EmpleadoControllerV;
import com.jeremyarevalo.webapp.biblioteca.controller.FXController.IndexController;
import com.jeremyarevalo.webapp.biblioteca.controller.FXController.LibroControllerV;
import com.jeremyarevalo.webapp.biblioteca.controller.FXController.PrestamoControllerV;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application{
    private ConfigurableApplicationContext applicationContext;
    private Stage stage;
    private Scene scene;

    @Override
    public void init(){
        this.applicationContext = new SpringApplicationBuilder(BibliotecaApplication.class).run();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.stage = primaryStage;
        stage.setTitle("Biblioteca Spring");
        // vista inicial
        indexView();
        stage.show();
    }

    public Initializable switchScene(String fxmlName, int width, int height) throws IOException{
        Initializable resultado = null;
        FXMLLoader loader = new FXMLLoader();

        loader.setControllerFactory(applicationContext::getBean);
        InputStream archivo = Main.class.getResourceAsStream("/templates/" + fxmlName);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(Main.class.getResource("/templates/" + fxmlName));

        scene = new Scene((AnchorPane) loader.load(archivo), width, height);
        stage.setScene(scene);
        stage.sizeToScene();

        resultado = (Initializable)loader.getController();

        return resultado;
    }

    public void indexView(){
        try {
            IndexController indexView = (IndexController)switchScene("index.fxml", 1000, 600);
            indexView.setStage(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    public void categoriaView(){
        try {
            CategoriaControllerV categoriaView = (CategoriaControllerV)switchScene("categoriaView.fxml", 1000, 600);
            categoriaView.setStage(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clienteView(){
        try {
            ClienteControllerV clienteView = (ClienteControllerV)switchScene("clienteView.fxml", 1000, 600);
            clienteView.setStage(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void empleadoView(){
        try {
            EmpleadoControllerV empleadoView = (EmpleadoControllerV)switchScene("empleadoView.fxml", 1000, 600);
            empleadoView.setStage(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void libroView(){
        try {
            LibroControllerV libroView = (LibroControllerV)switchScene("libroView.fxml", 1000, 600);
            libroView.setStage(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void prestamoView(){
        try {
            PrestamoControllerV prestamoView = (PrestamoControllerV)switchScene("prestamoView.fxml", 1000, 600);
            prestamoView.setStage(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
