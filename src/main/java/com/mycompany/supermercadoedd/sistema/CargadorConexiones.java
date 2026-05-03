package com.mycompany.supermercadoedd.sistema;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
/**
 *
 * @author ronald
 */
public class CargadorConexiones {
    public ResultadoCarga cargarConexiones(String rutaArchivo, SistemaSupermercado sistema) {

        ResultadoCarga resultado = new ResultadoCarga();

        try (BufferedReader lector = new BufferedReader(new FileReader(rutaArchivo))) {

            String linea;
            int numeroLinea = 0;

            while ((linea = lector.readLine()) != null) {

                numeroLinea++;

                if (linea.trim().isEmpty()) continue;

                if (numeroLinea == 1 && linea.toLowerCase().contains("origen")) continue;

                try {
                    String[] datos = linea.split(",");

                    if (datos.length < 4) {
                        resultado.agregarFallido("Línea " + numeroLinea + ": columnas insuficientes");
                        continue;
                    }

                    int origen = Integer.parseInt(datos[0].trim());
                    int destino = Integer.parseInt(datos[1].trim());
                    int tiempo = Integer.parseInt(datos[2].trim());
                    int costo = Integer.parseInt(datos[3].trim());

                    sistema.getGrafo().agregarConexion(origen, destino, tiempo, costo);

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
