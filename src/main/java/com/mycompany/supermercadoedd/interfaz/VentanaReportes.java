package com.mycompany.supermercadoedd.interfaz;
import com.mycompany.supermercadoedd.sistema.SistemaSupermercado;
import com.mycompany.supermercadoedd.sistema.Sucursal;
import com.mycompany.supermercadoedd.sistema.GeneradorReportes;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
/**
 *
 * @author ronald
 */
public class VentanaReportes {
    private SistemaSupermercado sistema;

    public VentanaReportes(SistemaSupermercado sistema) {
        this.sistema = sistema;
    }

    public void mostrar(Stage stage) {

        Label titulo = new Label("Generación de Reportes");

        ComboBox<Sucursal> comboSucursales = new ComboBox<>();

        // Cargar sucursales
        for (int i = 0; i < sistema.getSucursales().obtenerTamanio(); i++) {
            comboSucursales.getItems().add(
                sistema.getSucursales().obtener(i)
            );
        }

        comboSucursales.setPromptText("Seleccione una sucursal");

        CheckBox chkImagen = new CheckBox("Generar imagen PNG");

        Button btnAVL = new Button("Reporte AVL");
        Button btnB = new Button("Reporte Árbol B");
        Button btnBPlus = new Button("Reporte Árbol B+");
        Button btnHash = new Button("Reporte Tabla Hash");
        Button btnGrafo = new Button("Reporte Grafo");
        Button btnRegresar = new Button("Regresar");

        btnAVL.setMaxWidth(Double.MAX_VALUE);
        btnB.setMaxWidth(Double.MAX_VALUE);
        btnBPlus.setMaxWidth(Double.MAX_VALUE);
        btnHash.setMaxWidth(Double.MAX_VALUE);
        btnGrafo.setMaxWidth(Double.MAX_VALUE);
        btnRegresar.setPrefWidth(250);

        // ================= EVENTOS =================
        btnAVL.setOnAction(e -> {
            Sucursal s = comboSucursales.getValue();
            if (s == null) return;

            sistema.generarReporteAVL(s, chkImagen.isSelected());
        });

        btnB.setOnAction(e -> {
            Sucursal s = comboSucursales.getValue();
            if (s == null) return;

            sistema.generarReporteArbolB(s, chkImagen.isSelected());
        });

        btnBPlus.setOnAction(e -> {
            Sucursal s = comboSucursales.getValue();
            if (s == null) return;

            sistema.generarReporteArbolBPlus(s, chkImagen.isSelected());
        });

        btnHash.setOnAction(e -> {
            Sucursal s = comboSucursales.getValue();
            if (s == null) return;

            sistema.generarReporteTablaHash(s, chkImagen.isSelected());
        });

        btnGrafo.setOnAction(e -> {
            sistema.generarReporteGrafo(chkImagen.isSelected());
        });
        
        btnRegresar.setOnAction(e -> {
            MainFrame main = new MainFrame(sistema);
            main.mostrar(stage);
        });

        // ================= LAYOUT =================

        VBox root = new VBox(12);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));

        root.getChildren().addAll(
            titulo,
            comboSucursales,
            chkImagen,
            btnAVL,
            btnB,
            btnBPlus,
            btnHash,
            btnGrafo,
            btnRegresar
        );

        Scene scene = new Scene(root, 400, 450);
        stage.setScene(scene);
        stage.setTitle("Reportes");
        stage.show();
    }
}
