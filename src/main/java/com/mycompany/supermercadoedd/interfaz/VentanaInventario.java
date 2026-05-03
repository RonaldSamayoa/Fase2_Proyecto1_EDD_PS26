package com.mycompany.supermercadoedd.interfaz;
import com.mycompany.supermercadoedd.estructuras.ListaEnlazada;
import com.mycompany.supermercadoedd.modelos.Producto;
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
public class VentanaInventario {
    private SistemaSupermercado sistema;

    // Constructor principal
    public VentanaInventario(SistemaSupermercado sistema) {
        this.sistema = sistema;
    }

    // Muestra la ventana principal
    public void mostrar(Stage stage) {
        Label titulo = new Label("Inventario y Ordenamientos");

        TextField txtIdSucursal = new TextField();
        txtIdSucursal.setPromptText("ID de sucursal");

        TextArea resultado = new TextArea();
        resultado.setEditable(false);
        resultado.setPrefHeight(350);

        Button btnOrdenNombre = new Button("Listar por Nombre");
        Button btnOrdenCategoria = new Button("Listar por Categoría");
        Button btnOrdenFecha = new Button("Listar por Fecha");
        Button btnComparar = new Button("Comparar Ordenamientos");
        Button btnRegresar = new Button("Regresar");

        btnOrdenNombre.setPrefWidth(300);
        btnOrdenCategoria.setPrefWidth(300);
        btnOrdenFecha.setPrefWidth(300);
        btnComparar.setPrefWidth(300);
        btnRegresar.setPrefWidth(300);

        // Lista productos ordenados por nombre
        btnOrdenNombre.setOnAction(e -> {
            try {
                int idSucursal = Integer.parseInt(txtIdSucursal.getText());

                ListaEnlazada<Producto> lista =
                        sistema.listarProductosOrdenadosPorNombre(idSucursal);

                resultado.setText(lista.toString());

            } catch (Exception ex) {
                resultado.setText("Error al listar por nombre.");
            }
        });

        // Lista productos ordenados por categoría
        btnOrdenCategoria.setOnAction(e -> {
            try {
                int idSucursal = Integer.parseInt(txtIdSucursal.getText());

                ListaEnlazada<Producto> lista =
                        sistema.listarProductosOrdenadosPorCategoria(idSucursal);

                resultado.setText(lista.toString());

            } catch (Exception ex) {
                resultado.setText("Error al listar por categoría.");
            }
        });

        // Lista productos ordenados por fecha
        btnOrdenFecha.setOnAction(e -> {
            try {
                int idSucursal = Integer.parseInt(txtIdSucursal.getText());

                ListaEnlazada<Producto> lista =
                        sistema.listarProductosOrdenadosPorFecha(idSucursal);

                resultado.setText(lista.toString());

            } catch (Exception ex) {
                resultado.setText("Error al listar por fecha.");
            }
        });

        // Compara tiempos de ordenamiento
        btnComparar.setOnAction(e -> {
            try {
                int idSucursal = Integer.parseInt(txtIdSucursal.getText());

                String reporte =
                        sistema.compararOrdenamientosSucursal(idSucursal);

                resultado.setText(reporte);

            } catch (Exception ex) {
                resultado.setText("Error al comparar ordenamientos.");
            }
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
                txtIdSucursal,
                btnOrdenNombre,
                btnOrdenCategoria,
                btnOrdenFecha,
                btnComparar,
                resultado,
                btnRegresar
        );

        Scene scene = new Scene(root, 950, 700);

        stage.setTitle("Inventario");
        stage.setScene(scene);
        stage.show();
    }
}
