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
    // Referencia al sistema principal
    private SistemaSupermercado sistema;

    // Constructor principal
    public VentanaProductos(SistemaSupermercado sistema) {
        this.sistema = sistema;
    }

    // Muestra la ventana
    public void mostrar(Stage stage) {
        Label titulo = new Label("Gestión de Productos");

        Button btnRegistrar = new Button("Registrar Producto");
        Button btnEliminar = new Button("Eliminar Producto");
        Button btnDevoluciones = new Button("Control de Devoluciones");
        Button btnInventario = new Button("Mostrar Inventario");
        Button btnRegresar = new Button("Regresar");

        btnRegistrar.setPrefWidth(250);
        btnEliminar.setPrefWidth(250);
        btnDevoluciones.setPrefWidth(250);
        btnInventario.setPrefWidth(250);
        btnRegresar.setPrefWidth(250);

        // Temporales por ahora
        btnRegistrar.setOnAction(e -> {
            VentanaRegistrarProducto ventana =
                    new VentanaRegistrarProducto(sistema);

            ventana.mostrar(stage);
        });

        btnEliminar.setOnAction(e -> {
            System.out.println("Eliminar producto");
        });

        btnDevoluciones.setOnAction(e -> {
            System.out.println("Control de devoluciones");
        });

        btnInventario.setOnAction(e -> {
            System.out.println("Mostrar inventario");
        });

        // Regresa al menú principal
        btnRegresar.setOnAction(e -> {
            MainFrame main = new MainFrame(sistema);
            main.mostrar(stage);
        });

        VBox root = new VBox(15);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(30));

        root.getChildren().addAll(
                titulo,
                btnRegistrar,
                btnEliminar,
                btnDevoluciones,
                btnInventario,
                btnRegresar
        );

        Scene scene = new Scene(root, 900, 600);

        stage.setTitle("Gestión de Productos");
        stage.setScene(scene);
        stage.show();
    }
}
