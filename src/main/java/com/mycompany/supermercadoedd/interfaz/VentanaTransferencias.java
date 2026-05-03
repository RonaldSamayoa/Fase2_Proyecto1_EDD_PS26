package com.mycompany.supermercadoedd.interfaz;
import com.mycompany.supermercadoedd.sistema.SistemaSupermercado;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
/**
 *
 * @author ronald
 */
public class VentanaTransferencias {
    private SistemaSupermercado sistema;

    // Constructor principal
    public VentanaTransferencias(SistemaSupermercado sistema) {
        this.sistema = sistema;
    }

    // Muestra la ventana principal de transferencias
    public void mostrar(Stage stage) {
        Label titulo = new Label("Transferencia de Productos");

        TextField txtIdOrigen = new TextField();
        txtIdOrigen.setPromptText("ID sucursal origen");

        TextField txtIdDestino = new TextField();
        txtIdDestino.setPromptText("ID sucursal destino");

        TextField txtCodigo = new TextField();
        txtCodigo.setPromptText("Código de barras");

        TextField txtCantidad = new TextField();
        txtCantidad.setPromptText("Cantidad");

        ComboBox<String> comboCriterio = new ComboBox<>();
        comboCriterio.getItems().addAll("TIEMPO", "COSTO");
        comboCriterio.setPromptText("Criterio de traslado");

        Label resultado = new Label("");

        Button btnTransferir = new Button("Transferir");
        Button btnRegresar = new Button("Regresar");

        btnTransferir.setPrefWidth(250);
        btnRegresar.setPrefWidth(250);

        // Ejecuta la transferencia entre sucursales
        btnTransferir.setOnAction(e -> {
            try {
                int idOrigen = Integer.parseInt(txtIdOrigen.getText());
                int idDestino = Integer.parseInt(txtIdDestino.getText());
                String codigo = txtCodigo.getText();
                int cantidad = Integer.parseInt(txtCantidad.getText());
                String criterio = comboCriterio.getValue();

                if (criterio == null) {
                    resultado.setText("Debe seleccionar un criterio.");
                    return;
                }

                boolean exito = sistema.trasladarProducto(
                        idOrigen,
                        idDestino,
                        codigo,
                        cantidad,
                        criterio
                );

                if (exito) {
                    resultado.setText("Transferencia realizada correctamente.");
                } else {
                    resultado.setText("No fue posible realizar la transferencia.");
                }

            } catch (Exception ex) {
                resultado.setText("Error en los datos ingresados.");
            }
        });

        // Regresa al menú principal
        btnRegresar.setOnAction(e -> {
            MainFrame main = new MainFrame(sistema);
            main.mostrar(stage);
        });

        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));

        root.getChildren().addAll(
                titulo,
                txtIdOrigen,
                txtIdDestino,
                txtCodigo,
                txtCantidad,
                comboCriterio,
                btnTransferir,
                resultado,
                btnRegresar
        );

        Scene scene = new Scene(root, 900, 650);

        stage.setTitle("Transferencias");
        stage.setScene(scene);
        stage.show();
    }
}
