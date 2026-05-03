package com.mycompany.supermercadoedd.interfaz;
import com.mycompany.supermercadoedd.sistema.SistemaSupermercado;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
/**
 *
 * @author ronald
 */
public class VentanaProductos {
    private SistemaSupermercado sistema;

    // Constructor principal
    public VentanaProductos(SistemaSupermercado sistema) {
        this.sistema = sistema;
    }

    // Muestra la ventana principal
    public void mostrar(Stage stage) {
        Label titulo = new Label("Gestión de Productos");

        Button btnCargaCSV = new Button("Carga Masiva CSV");
        Button btnRegistrar = new Button("Registrar Producto");
        Button btnEliminar = new Button("Eliminar Producto");
        Button btnRollback = new Button("Deshacer Última Operación");
        Button btnInventario = new Button("Inventario y Ordenamientos");
        Button btnRegresar = new Button("Regresar");

        btnCargaCSV.setPrefWidth(250);
        btnRegistrar.setPrefWidth(280);
        btnEliminar.setPrefWidth(280);
        btnRollback.setPrefWidth(280);
        btnInventario.setPrefWidth(280);
        btnRegresar.setPrefWidth(280);

        btnCargaCSV.setOnAction(e -> {
            VentanaCargaCSV ventana =
                    new VentanaCargaCSV(sistema);

            ventana.mostrar(stage);
        });

        // Abre ventana de registro
        btnRegistrar.setOnAction(e -> {
            VentanaRegistrarProducto ventana =
                    new VentanaRegistrarProducto(sistema);

            ventana.mostrar(stage);
        });

        // Abre ventana de eliminación
        btnEliminar.setOnAction(e -> {
            VentanaEliminarProducto ventana =
                    new VentanaEliminarProducto(sistema);

            ventana.mostrar(stage);
        });

        // Abre ventana de rollback
        btnRollback.setOnAction(e -> {
            VentanaRollback ventana =
                    new VentanaRollback(sistema);

            ventana.mostrar(stage);
        });

        // Abre inventario
        btnInventario.setOnAction(e -> {
            VentanaInventario ventana =
                    new VentanaInventario(sistema);

            ventana.mostrar(stage);
        });

        // Regresa al menú principal
        btnRegresar.setOnAction(e -> {
            MainFrame main =
                    new MainFrame(sistema);

            main.mostrar(stage);
        });

        VBox root = new VBox(15);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(30));

        root.getChildren().addAll(
                titulo,
                btnCargaCSV,
                btnRegistrar,
                btnEliminar,
                btnRollback,
                btnInventario,
                btnRegresar
        );

        Scene scene = new Scene(root, 900, 650);

        stage.setTitle("Gestión de Productos");
        stage.setScene(scene);
        stage.show();
    }
}
