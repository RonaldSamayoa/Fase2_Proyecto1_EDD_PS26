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
public class VentanaBusquedas {
    private SistemaSupermercado sistema;

    public VentanaBusquedas(SistemaSupermercado sistema) {
        this.sistema = sistema;
    }

    public void mostrar(Stage stage) {

        Label titulo = new Label("Módulo de Búsquedas");

        // ===== DATOS BASE =====
        TextField txtIdSucursal = new TextField();
        txtIdSucursal.setPromptText("ID de sucursal");

        // ===== CAMPOS =====
        TextField txtNombre = new TextField();
        txtNombre.setPromptText("Nombre");

        TextField txtCodigo = new TextField();
        txtCodigo.setPromptText("Código");

        TextField txtCategoria = new TextField();
        txtCategoria.setPromptText("Categoría");

        TextField txtFechaInicio = new TextField();
        txtFechaInicio.setPromptText("Fecha inicio");

        TextField txtFechaFin = new TextField();
        txtFechaFin.setPromptText("Fecha fin");

        TextField txtCoincidencia = new TextField();
        txtCoincidencia.setPromptText("Texto parcial");

        // ===== RESULTADO =====
        TextArea resultado = new TextArea();
        resultado.setEditable(false);
        resultado.setPrefHeight(300);

        // ===== BOTONES =====
        Button btnCompararNombre = new Button("Comparar Nombre (Lista vs AVL)");
        Button btnCompararCodigo = new Button("Comparar Código (Lista vs Hash)");
        Button btnCategoria = new Button("Buscar por Categoría");
        Button btnFecha = new Button("Buscar por Fechas");
        Button btnCoincidencia = new Button("Coincidencias Parciales");
        Button btnRegresar = new Button("Regresar");

        // ===== EVENTOS =====

        btnCompararNombre.setOnAction(e -> {
            try {
                int id = Integer.parseInt(txtIdSucursal.getText());
                resultado.setText(
                        sistema.compararBusquedaPorNombre(id, txtNombre.getText())
                );
            } catch (Exception ex) {
                resultado.setText("Error en búsqueda por nombre.");
            }
        });

        btnCompararCodigo.setOnAction(e -> {
            try {
                int id = Integer.parseInt(txtIdSucursal.getText());
                resultado.setText(
                        sistema.compararBusquedaPorCodigo(id, txtCodigo.getText())
                );
            } catch (Exception ex) {
                resultado.setText("Error en búsqueda por código.");
            }
        });

        btnCategoria.setOnAction(e -> {
            try {
                int id = Integer.parseInt(txtIdSucursal.getText());

                ListaEnlazada<Producto> lista =
                        sistema.buscarProductosPorCategoria(id, txtCategoria.getText());

                resultado.setText(lista.toString());

            } catch (Exception ex) {
                resultado.setText("Error en categoría.");
            }
        });

        btnFecha.setOnAction(e -> {
            try {
                int id = Integer.parseInt(txtIdSucursal.getText());

                ListaEnlazada<Producto> lista =
                        sistema.buscarProductosPorRangoFecha(
                                id,
                                txtFechaInicio.getText(),
                                txtFechaFin.getText()
                        );

                resultado.setText(lista.toString());

            } catch (Exception ex) {
                resultado.setText("Error en fechas.");
            }
        });

        btnCoincidencia.setOnAction(e -> {
            try {
                int id = Integer.parseInt(txtIdSucursal.getText());

                ListaEnlazada<Producto> lista =
                        sistema.buscarCoincidenciasParciales(id, txtCoincidencia.getText());

                resultado.setText(lista.toString());

            } catch (Exception ex) {
                resultado.setText("Error en coincidencias.");
            }
        });

        btnRegresar.setOnAction(e -> {
            MainFrame main = new MainFrame(sistema);
            main.mostrar(stage);
        });

        // ===== LAYOUT =====
        VBox root = new VBox(10);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);

        root.getChildren().addAll(
                titulo,
                txtIdSucursal,

                new Label("Comparaciones"),
                txtNombre, btnCompararNombre,
                txtCodigo, btnCompararCodigo,

                new Label("Búsquedas"),
                txtCategoria, btnCategoria,
                txtFechaInicio, txtFechaFin, btnFecha,
                txtCoincidencia, btnCoincidencia,

                new Label("Resultado"),
                resultado,

                btnRegresar
        );

        Scene scene = new Scene(root, 900, 700);

        stage.setTitle("Búsquedas");
        stage.setScene(scene);
        stage.show();
    }
}
