package com.mycompany.supermercadoedd.sistema;
import com.mycompany.supermercadoedd.modelos.Producto;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
/**
 *
 * @author ronald
 */
public class CargadorCSV {
    // Carga productos desde un archivo CSV hacia el sistema
    public ResultadoCarga cargarProductos(String rutaArchivo,
                                          SistemaSupermercado sistema) {

        ResultadoCarga resultado = new ResultadoCarga();

        try (BufferedReader lector = new BufferedReader(
                new FileReader(rutaArchivo))) {

            String linea;
            int numeroLinea = 0;

            // Lee el archivo línea por línea
            while ((linea = lector.readLine()) != null) {

                numeroLinea++;

                // Ignora líneas vacías
                if (linea.trim().isEmpty()) {
                    continue;
                }

                // Detecta y omite encabezado automáticamente
                if (numeroLinea == 1 &&
                        linea.toLowerCase().contains("sucursal")) {
                    continue;
                }

                try {

                    // Se parsea la línea respetando comillas
                    String[] datos = parsearLineaCSV(linea);

                    // Valida la cantidad esperada de columnas
                    if (datos.length < 8) {
                        resultado.agregarFallido(
                                "Línea " + numeroLinea +
                                ": columnas insuficientes"
                        );
                        continue;
                    }

                    int idSucursal = Integer.parseInt(
                            limpiarCampo(datos[0])
                    );

                    String nombre = limpiarCampo(datos[1]);
                    String codigoBarras = limpiarCampo(datos[2]);
                    String categoria = limpiarCampo(datos[3]);
                    String fechaCaducidad = limpiarCampo(datos[4]);
                    String marca = limpiarCampo(datos[5]);

                    double precio = Double.parseDouble(
                            limpiarCampo(datos[6])
                    );

                    int stock = Integer.parseInt(
                            limpiarCampo(datos[7])
                    );

                    Producto producto = new Producto(
                            nombre,
                            codigoBarras,
                            categoria,
                            fechaCaducidad,
                            marca,
                            precio,
                            stock
                    );

                    // Verifica que la sucursal exista
                    if (sistema.buscarSucursalPorId(idSucursal) == null) {

                        resultado.agregarFallido(
                                "Línea " + numeroLinea +
                                ": sucursal no encontrada -> " +
                                idSucursal
                        );

                        continue;
                    }

                    // Inserta el producto en el sistema
                    sistema.registrarProducto(
                            idSucursal,
                            producto
                    );

                    resultado.agregarExitoso();

                } catch (Exception e) {

                    // Si algo falla, no detiene el sistema
                    resultado.agregarFallido(
                            "Línea " + numeroLinea +
                            ": error -> " + e.getMessage()
                    );
                }
            }

        } catch (IOException e) {

            resultado.agregarFallido(
                    "Error al abrir archivo: " +
                    e.getMessage()
            );
        }

        return resultado;
    }

    // Limpia espacios y comillas externas
    private String limpiarCampo(String campo) {

        campo = campo.trim();

        if (campo.startsWith("\"") &&
                campo.endsWith("\"")) {

            campo = campo.substring(
                    1,
                    campo.length() - 1
            );
        }

        return campo.trim();
    }

    // Parsea una línea CSV respetando comillas
    private String[] parsearLineaCSV(String linea) {

        String[] temporal = new String[50];
        int contador = 0;

        String actual = "";
        boolean dentroComillas = false;

        for (int i = 0; i < linea.length(); i++) {

            char caracter = linea.charAt(i);

            // Cambia el estado de comillas
            if (caracter == '"') {
                dentroComillas = !dentroComillas;
                continue;
            }

            // Solo separa por coma si no está dentro de comillas
            if (caracter == ',' && !dentroComillas) {

                temporal[contador] = actual;
                contador++;
                actual = "";

            } else {
                actual += caracter;
            }
        }

        // Guarda el último campo
        temporal[contador] = actual;
        contador++;

        // Se crea el arreglo final exacto
        String[] resultado = new String[contador];

        for (int i = 0; i < contador; i++) {
            resultado[i] = temporal[i];
        }

        return resultado;
    }
}
