package com.mycompany.supermercadoedd.aplicacion;

import com.mycompany.supermercadoedd.interfaz.MainFrame;
import com.mycompany.supermercadoedd.sistema.SistemaSupermercado;
import javafx.application.Application;
import javafx.stage.Stage;

public class Principal extends Application {
    // Instancia única del sistema
    private SistemaSupermercado sistema;

    @Override
    public void start(Stage stage) {
        // Inicializa el sistema principal
        sistema = new SistemaSupermercado();

        // Carga la ventana principal
        MainFrame mainFrame = new MainFrame(sistema);
        mainFrame.mostrar(stage);
    }
}