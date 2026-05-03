package com.mycompany.supermercadoedd.interfaz;
import com.mycompany.supermercadoedd.sistema.SistemaSupermercado;
import com.mycompany.supermercadoedd.sistema.Sucursal;
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
public class VentanaRollback {
    private SistemaSupermercado sistema;

    public VentanaRollback(SistemaSupermercado sistema) {
        this.sistema = sistema;
    }

    public void mostrar(Stage stage) {
        Label titulo = new Label("Deshacer Última Operación");

        TextField txtIdSucursal = new TextField();
        txtIdSucursal.setPromptText("ID de sucursal");

        TextArea resultado = new TextArea();
        resultado.setEditable(false);
        resultado.setPrefHeight(220);

        Button btnDeshacer = new Button("Deshacer");
        Button btnRegresar = new Button("Regresar");

        btnDeshacer.setPrefWidth(250);
        btnRegresar.setPrefWidth(250);

        // Ejecuta rollback
        btnDeshacer.setOnAction(e -> {
            try {
                int idSucursal =
                        Integer.parseInt(txtIdSucursal.getText());

                Sucursal sucursal =
                        sistema.buscarSucursalPorId(idSucursal);

                if (sucursal == null) {
                    resultado.setText("Sucursal no encontrada.");
                    return;
                }

                boolean exito =
                        sucursal.deshacerUltimaOperacion();

                if (exito) {
                    resultado.setText(
                            "Rollback realizado correctamente.\n" +
                            "La pila de cambios fue utilizada."
                    );
                } else {
                    resultado.setText(
                            "No existen operaciones para deshacer."
                    );
                }

            } catch (Exception ex) {
                resultado.setText(
                        "Error en los datos ingresados."
                );
            }
        });

        btnRegresar.setOnAction(e -> {
            VentanaProductos ventana =
                    new VentanaProductos(sistema);

            ventana.mostrar(stage);
        });

        VBox root = new VBox(12);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));

        root.getChildren().addAll(
                titulo,
                txtIdSucursal,
                btnDeshacer,
                resultado,
                btnRegresar
        );

        Scene scene = new Scene(root, 700, 500);

        stage.setTitle("Rollback");
        stage.setScene(scene);
        stage.show();
    }
}
