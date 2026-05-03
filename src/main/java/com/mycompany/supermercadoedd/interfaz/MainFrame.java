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
public class MainFrame {
    // Instancia principal del sistema
    private SistemaSupermercado sistema;

    // Constructor principal
    public MainFrame(SistemaSupermercado sistema) {
        this.sistema = sistema;
    }

    // Muestra la ventana principal
    public void mostrar(Stage stage) {
        // Título principal
        Label titulo = new Label("Sistema de Gestión de Supermercados");

        // Botones principales
        Button btnProductos = new Button("Gestión de Productos");
        Button btnBusquedas = new Button("Búsquedas");
        Button btnTransferencias = new Button("Transferencias");
        Button btnOrdenamientos = new Button("Ordenamientos");
        Button btnSucursales = new Button("Registrar Sucursal");
        Button btnCompararRutas = new Button("Comparar Rutas");
        Button btnInventario = new Button("Inventario");
        Button btnSalir = new Button("Salir");

        // Tamaño uniforme
        btnProductos.setPrefWidth(250);
        btnBusquedas.setPrefWidth(250);
        btnTransferencias.setPrefWidth(250);
        btnOrdenamientos.setPrefWidth(250);
        btnSucursales.setPrefWidth(250);
        btnCompararRutas.setPrefWidth(250);
        btnInventario.setPrefWidth(250);
        btnSalir.setPrefWidth(250);

        // Abre módulo de productos
        btnProductos.setOnAction(e -> {
            VentanaProductos ventana = new VentanaProductos(sistema);
            ventana.mostrar(stage);
        });

        btnBusquedas.setOnAction(e -> {
            VentanaBusquedas ventana =
                    new VentanaBusquedas(sistema);

            ventana.mostrar(stage);
        });

        btnTransferencias.setOnAction(e -> {
            VentanaTransferencias ventana =
                    new VentanaTransferencias(sistema);

            ventana.mostrar(stage);
        });

        btnOrdenamientos.setOnAction(e -> {
            System.out.println("Abrir módulo ordenamientos");
        });
        
        btnSucursales.setOnAction(e -> {
            VentanaRegistrarSucursal ventana =
                    new VentanaRegistrarSucursal(sistema);

            ventana.mostrar(stage);
        });
        
        btnCompararRutas.setOnAction(e -> {
            VentanaCompararRutas ventana =
                    new VentanaCompararRutas(sistema);

            ventana.mostrar(stage);
        });
        
        btnInventario.setOnAction(e -> {
            VentanaInventario ventana =
                    new VentanaInventario(sistema);

            ventana.mostrar(stage);
        });

        // Cierra sistema
        btnSalir.setOnAction(e -> {
            stage.close();
        });

        VBox root = new VBox(15);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(30));

        root.getChildren().addAll(
                titulo,
                btnSucursales,
                btnProductos,
                btnBusquedas,
                btnCompararRutas,
                btnTransferencias,
                btnOrdenamientos,
                btnInventario,
                btnSalir
        );

        Scene scene = new Scene(root, 900, 600);

        stage.setTitle("Supermercado EDD");
        stage.setScene(scene);
        stage.show();
    }
}
