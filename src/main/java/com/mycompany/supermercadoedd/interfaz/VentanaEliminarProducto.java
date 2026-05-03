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
public class VentanaEliminarProducto {
    private SistemaSupermercado sistema;

    public VentanaEliminarProducto(SistemaSupermercado sistema) {
        this.sistema = sistema;
    }

    public void mostrar(Stage stage) {
        Label titulo = new Label("Eliminar Producto");

        TextField txtIdSucursal = new TextField();
        txtIdSucursal.setPromptText("ID de sucursal");

        TextField txtCodigo = new TextField();
        txtCodigo.setPromptText("Código de barras");

        TextArea resultado = new TextArea();
        resultado.setEditable(false);
        resultado.setPrefHeight(200);

        Button btnEliminar = new Button("Eliminar");
        Button btnRegresar = new Button("Regresar");

        btnEliminar.setPrefWidth(250);
        btnRegresar.setPrefWidth(250);

        // Ejecuta la eliminación
        btnEliminar.setOnAction(e -> {
            try {
                int idSucursal =
                        Integer.parseInt(txtIdSucursal.getText());

                boolean eliminado =
                        sistema.eliminarProducto(
                                idSucursal,
                                txtCodigo.getText()
                        );

                if (eliminado) {
                    resultado.setText(
                            "Producto eliminado correctamente."
                    );
                } else {
                    resultado.setText(
                            "No fue posible eliminar el producto."
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
                txtCodigo,
                btnEliminar,
                resultado,
                btnRegresar
        );

        Scene scene = new Scene(root, 700, 500);

        stage.setTitle("Eliminar Producto");
        stage.setScene(scene);
        stage.show();
    }
}
