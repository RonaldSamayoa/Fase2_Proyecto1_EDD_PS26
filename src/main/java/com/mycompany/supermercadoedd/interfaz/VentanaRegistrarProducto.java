package com.mycompany.supermercadoedd.interfaz;
import com.mycompany.supermercadoedd.modelos.Producto;
import com.mycompany.supermercadoedd.sistema.SistemaSupermercado;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
/**
 *
 * @author ronald
 */
public class VentanaRegistrarProducto {
    private SistemaSupermercado sistema;

    // Constructor principal
    public VentanaRegistrarProducto(SistemaSupermercado sistema) {
        this.sistema = sistema;
    }

    // Muestra la ventana de registro
    public void mostrar(Stage stage) {
        Label titulo = new Label("Registrar Producto");

        TextField txtIdSucursal = new TextField();
        txtIdSucursal.setPromptText("ID de sucursal");

        TextField txtNombre = new TextField();
        txtNombre.setPromptText("Nombre");

        TextField txtCodigo = new TextField();
        txtCodigo.setPromptText("Código de barras");

        TextField txtCategoria = new TextField();
        txtCategoria.setPromptText("Categoría");

        TextField txtFecha = new TextField();
        txtFecha.setPromptText("Fecha de caducidad");

        TextField txtMarca = new TextField();
        txtMarca.setPromptText("Marca");

        TextField txtPrecio = new TextField();
        txtPrecio.setPromptText("Precio");

        TextField txtStock = new TextField();
        txtStock.setPromptText("Stock");

        Label resultado = new Label("");

        Button btnRegistrar = new Button("Registrar");
        Button btnRegresar = new Button("Regresar");

        btnRegistrar.setPrefWidth(250);
        btnRegresar.setPrefWidth(250);

        // Registra el producto dentro del sistema
        btnRegistrar.setOnAction(e -> {
            try {
                int idSucursal = Integer.parseInt(txtIdSucursal.getText());
                String nombre = txtNombre.getText();
                String codigo = txtCodigo.getText();
                String categoria = txtCategoria.getText();
                String fecha = txtFecha.getText();
                String marca = txtMarca.getText();
                double precio = Double.parseDouble(txtPrecio.getText());
                int stock = Integer.parseInt(txtStock.getText());

                Producto producto = new Producto(
                        nombre,
                        codigo,
                        categoria,
                        fecha,
                        marca,
                        precio,
                        stock
                );

                boolean registrado = sistema.registrarProducto(
                        idSucursal,
                        producto
                );

                if (registrado) {
                    resultado.setText("Producto registrado correctamente.");
                } else {
                    resultado.setText("No fue posible registrar el producto.");
                }

            } catch (Exception ex) {
                resultado.setText("Error en los datos ingresados.");
            }
        });

        // Regresa al módulo anterior
        btnRegresar.setOnAction(e -> {
            VentanaProductos ventana = new VentanaProductos(sistema);
            ventana.mostrar(stage);
        });

        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));

        root.getChildren().addAll(
                titulo,
                txtIdSucursal,
                txtNombre,
                txtCodigo,
                txtCategoria,
                txtFecha,
                txtMarca,
                txtPrecio,
                txtStock,
                btnRegistrar,
                resultado,
                btnRegresar
        );

        Scene scene = new Scene(root, 900, 650);

        stage.setTitle("Registrar Producto");
        stage.setScene(scene);
        stage.show();
    }
}
