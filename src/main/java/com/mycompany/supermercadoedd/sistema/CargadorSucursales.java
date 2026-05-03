package com.mycompany.supermercadoedd.sistema;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
/**
 *
 * @author ronald
 */
public class CargadorSucursales {
    public ResultadoCarga cargarSucursales(String rutaArchivo, SistemaSupermercado sistema) {

        ResultadoCarga resultado = new ResultadoCarga();

        try (BufferedReader lector = new BufferedReader(new FileReader(rutaArchivo))) {

            String linea;
            int numeroLinea = 0;

            while ((linea = lector.readLine()) != null) {

                numeroLinea++;

                if (linea.trim().isEmpty()) continue;

                // Salta encabezado
                if (numeroLinea == 1 && linea.toLowerCase().contains("id")) continue;

                try {
                    String[] datos = linea.split(",");

                    if (datos.length < 6) {
                        resultado.agregarFallido("Línea " + numeroLinea + ": columnas insuficientes");
                        continue;
                    }

                    int id = Integer.parseInt(datos[0].trim());
                    String nombre = datos[1].trim();
                    String ubicacion = datos[2].trim();

                    int tIngreso = Integer.parseInt(datos[3].trim());
                    int tTraspaso = Integer.parseInt(datos[4].trim());
                    int tDespacho = Integer.parseInt(datos[5].trim());

                    Sucursal sucursal = new Sucursal(id, nombre, ubicacion, tIngreso, tTraspaso, tDespacho);

                    sistema.agregarSucursal(sucursal);

                    resultado.agregarExitoso();

                } catch (Exception e) {
                    resultado.agregarFallido("Línea " + numeroLinea + ": " + e.getMessage());
                }
            }

        } catch (IOException e) {
            resultado.agregarFallido("Error archivo: " + e.getMessage());
        }

        return resultado;
    }
}
