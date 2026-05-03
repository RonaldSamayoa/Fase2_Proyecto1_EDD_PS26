package com.mycompany.supermercadoedd.interfaz;
import com.mycompany.supermercadoedd.sistema.CargadorCSV;
import com.mycompany.supermercadoedd.sistema.CargadorConexiones;
import com.mycompany.supermercadoedd.sistema.CargadorSucursales;
import com.mycompany.supermercadoedd.sistema.ResultadoCarga;
import com.mycompany.supermercadoedd.sistema.SistemaSupermercado;
import java.io.File;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
/**
 *
 * @author ronald
 */
public class VentanaCargaCSV {
    private SistemaSupermercado sistema;

    public VentanaCargaCSV(SistemaSupermercado sistema) {
        this.sistema = sistema;
    }

    public void mostrar(Stage stage) {

        Label titulo = new Label("Carga Masiva CSV");

        ComboBox<String> tipoCarga = new ComboBox<>();
        tipoCarga.getItems().addAll(
                "Sucursales",
                "Conexiones",
                "Productos");
        tipoCarga.setPromptText("Seleccione tipo de carga");

        Button btnSeleccionar = new Button("Seleccionar Archivo CSV");
        Button btnRegresar = new Button("Regresar");

        TextArea areaResultado = new TextArea();
        areaResultado.setEditable(false);
        areaResultado.setPrefHeight(350);

        btnSeleccionar.setOnAction(e -> {

            if (tipoCarga.getValue() == null) {
                areaResultado.setText("Seleccione un tipo de carga.");
                return;
            }

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Seleccionar archivo CSV");
            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("CSV", "*.csv")
            );

            File archivo = fileChooser.showOpenDialog(stage);

            if (archivo != null) {

                ResultadoCarga resultado = null;

                String ruta = archivo.getAbsolutePath();

                switch (tipoCarga.getValue()) {

                    case "Sucursales":
                        resultado = new CargadorSucursales().cargarSucursales(ruta, sistema);
                        break;

                    case "Conexiones":
                        resultado = new CargadorConexiones().cargarConexiones(ruta, sistema);
                        break;

                    case "Productos":
                        resultado = new CargadorCSV()
                                .cargarProductos(ruta, sistema);
                        break;
                }

                areaResultado.setText(
                        "=== RESULTADO ===\n\n"
                        + "Exitosos: " + resultado.getRegistrosExitosos() + "\n"
                        + "Fallidos: " + resultado.getRegistrosFallidos() + "\n\n"
                        + "Errores:\n" + resultado.getDetalleErrores()
                );
            }
        });

        btnRegresar.setOnAction(e -> {
            MainFrame main = new MainFrame(sistema);
            main.mostrar(stage);
        });

        VBox root = new VBox(15);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(30));

        root.getChildren().addAll(
                titulo,
                tipoCarga,
                btnSeleccionar,
                areaResultado,
                btnRegresar
        );

        Scene scene = new Scene(root, 900, 600);

        stage.setTitle("Carga CSV");
        stage.setScene(scene);
        stage.show();
    }
}
