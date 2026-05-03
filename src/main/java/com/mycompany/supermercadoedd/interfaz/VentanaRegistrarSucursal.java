package com.mycompany.supermercadoedd.interfaz;
import com.mycompany.supermercadoedd.sistema.SistemaSupermercado;
import com.mycompany.supermercadoedd.sistema.Sucursal;
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
public class VentanaRegistrarSucursal {
    private SistemaSupermercado sistema;

    // Constructor principal
    public VentanaRegistrarSucursal(SistemaSupermercado sistema) {
        this.sistema = sistema;
    }

    // Muestra la ventana de registro
    public void mostrar(Stage stage) {
        Label titulo = new Label("Registrar Sucursal");

        TextField txtId = new TextField();
        txtId.setPromptText("ID");

        TextField txtNombre = new TextField();
        txtNombre.setPromptText("Nombre");

        TextField txtUbicacion = new TextField();
        txtUbicacion.setPromptText("Ubicación");

        TextField txtTiempoIngreso = new TextField();
        txtTiempoIngreso.setPromptText("Tiempo de ingreso");

        TextField txtTiempoPreparacion = new TextField();
        txtTiempoPreparacion.setPromptText("Tiempo de preparación");

        TextField txtTiempoDespacho = new TextField();
        txtTiempoDespacho.setPromptText("Intervalo de despacho");

        Label resultado = new Label("");

        Button btnRegistrar = new Button("Registrar");
        Button btnRegresar = new Button("Regresar");

        btnRegistrar.setPrefWidth(250);
        btnRegresar.setPrefWidth(250);

        // Registra la sucursal en el sistema
        btnRegistrar.setOnAction(e -> {
            try {
                int id = Integer.parseInt(txtId.getText());
                String nombre = txtNombre.getText();
                String ubicacion = txtUbicacion.getText();
                int tiempoIngreso = Integer.parseInt(txtTiempoIngreso.getText());
                int tiempoPreparacion = Integer.parseInt(txtTiempoPreparacion.getText());
                int tiempoDespacho = Integer.parseInt(txtTiempoDespacho.getText());

                Sucursal nuevaSucursal = new Sucursal(
                        id,
                        nombre,
                        ubicacion,
                        tiempoIngreso,
                        tiempoPreparacion,
                        tiempoDespacho
                );

                sistema.agregarSucursal(nuevaSucursal);

                resultado.setText("Sucursal registrada correctamente.");

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
                txtId,
                txtNombre,
                txtUbicacion,
                txtTiempoIngreso,
                txtTiempoPreparacion,
                txtTiempoDespacho,
                btnRegistrar,
                resultado,
                btnRegresar
        );

        Scene scene = new Scene(root, 900, 650);

        stage.setTitle("Registrar Sucursal");
        stage.setScene(scene);
        stage.show();
    }
}
