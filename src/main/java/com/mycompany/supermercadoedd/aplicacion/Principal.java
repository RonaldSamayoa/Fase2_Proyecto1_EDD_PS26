package com.mycompany.supermercadoedd.aplicacion;

import com.mycompany.supermercadoedd.modelos.Producto;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Principal extends Application {

    @Override
    public void start(Stage stage) {
        Label label = new Label("Sistema de Gestión de Supermercados");
        Scene scene = new Scene(new StackPane(label), 900, 600);

        stage.setTitle("Supermercado EDD");
        stage.setScene(scene);
        stage.show();
    }

}