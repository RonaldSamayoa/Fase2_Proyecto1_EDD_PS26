package com.mycompany.supermercadoedd.interfaz;
import com.mycompany.supermercadoedd.sistema.SistemaSupermercado;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
/**
 *
 * @author ronald
 */
public class VentanaCompararRutas {
    // Referencia al sistema principal
    private SistemaSupermercado sistema;

    // Constructor principal
    public VentanaCompararRutas(SistemaSupermercado sistema) {
        this.sistema = sistema;
    }

    // Muestra la ventana principal
    public void mostrar(Stage stage) {
        Label titulo = new Label("Comparar Rutas entre Sucursales");

        TextField txtIdOrigen = new TextField();
        txtIdOrigen.setPromptText("ID sucursal origen");

        TextField txtIdDestino = new TextField();
        txtIdDestino.setPromptText("ID sucursal destino");

        TextArea resultado = new TextArea();
        resultado.setEditable(false);
        resultado.setPrefHeight(250);

        Button btnComparar = new Button("Comparar");
        Button btnTransferencia = new Button("Ir a Transferencias");
        Button btnRegresar = new Button("Regresar");

        btnComparar.setPrefWidth(250);
        btnTransferencia.setPrefWidth(250);
        btnRegresar.setPrefWidth(250);

        // Calcula la comparación entre tiempo y costo
        btnComparar.setOnAction(e -> {
            try {
                int idOrigen = Integer.parseInt(txtIdOrigen.getText());
                int idDestino = Integer.parseInt(txtIdDestino.getText());

                String reporte = sistema.compararTransferencia(idOrigen, idDestino);

                resultado.setText(reporte);

            } catch (Exception ex) {
                resultado.setText("Error en los datos ingresados.");
            }
        });

        // Abre la ventana de transferencias
        btnTransferencia.setOnAction(e -> {
            VentanaTransferencias ventana =
                    new VentanaTransferencias(sistema);

            ventana.mostrar(stage);
        });

        // Regresa al menú principal
        btnRegresar.setOnAction(e -> {
            MainFrame main = new MainFrame(sistema);
            main.mostrar(stage);
        });

        VBox root = new VBox(12);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));

        root.getChildren().addAll(
                titulo,
                txtIdOrigen,
                txtIdDestino,
                btnComparar,
                resultado,
                btnTransferencia,
                btnRegresar
        );

        Scene scene = new Scene(root, 900, 650);

        stage.setTitle("Comparación de Rutas");
        stage.setScene(scene);
        stage.show();
    }
}
