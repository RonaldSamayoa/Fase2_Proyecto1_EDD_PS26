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

                // Detecta encabezado
                if (linea.toLowerCase().contains("origen") &&
                    linea.toLowerCase().contains("destino")) {
                    continue;
                }

                try {
                    String[] datos = parsearLineaCSV(linea);

                    if (datos.length != 4) {
                        resultado.agregarFallido("Línea " + numeroLinea + ": formato incorrecto (4 columnas requeridas)");
                        continue;
                    }

                    String origenStr = limpiarCampo(datos[0]);
                    String destinoStr = limpiarCampo(datos[1]);
                    String tiempoStr = limpiarCampo(datos[2]);
                    String costoStr = limpiarCampo(datos[3]);

                    if (origenStr.isEmpty() || destinoStr.isEmpty() ||
                        tiempoStr.isEmpty() || costoStr.isEmpty()) {

                        resultado.agregarFallido("Línea " + numeroLinea + ": campos vacíos");
                        continue;
                    }

                    int origen = Integer.parseInt(origenStr);
                    int destino = Integer.parseInt(destinoStr);
                    int tiempo = Integer.parseInt(tiempoStr);
                    int costo = Integer.parseInt(costoStr);

                    if (tiempo <= 0 || costo <= 0) {
                        resultado.agregarFallido("Línea " + numeroLinea + ": tiempo/costo deben ser > 0");
                        continue;
                    }

                    if (sistema.buscarSucursalPorId(origen) == null ||
                        sistema.buscarSucursalPorId(destino) == null) {

                        resultado.agregarFallido("Línea " + numeroLinea + ": sucursal inexistente");
                        continue;
                    }

                    sistema.getGrafo().agregarConexion(origen, destino, tiempo, costo);
                    resultado.agregarExitoso();

                } catch (Exception e) {
                    resultado.agregarFallido("Línea " + numeroLinea + ": error -> " + e.getMessage());
                }
            }

        } catch (IOException e) {
            resultado.agregarFallido("Error archivo: " + e.getMessage());
        }

        return resultado;
    }

    private String limpiarCampo(String campo) {
        campo = campo.trim();
        if (campo.startsWith("\"") && campo.endsWith("\"")) {
            campo = campo.substring(1, campo.length() - 1);
        }
        return campo.trim();
    }

    private String[] parsearLineaCSV(String linea) {
        String[] temp = new String[50];
        int count = 0;
        String actual = "";
        boolean enComillas = false;

        for (char c : linea.toCharArray()) {
            if (c == '"') {
                enComillas = !enComillas;
                continue;
            }

            if (c == ',' && !enComillas) {
                temp[count++] = actual;
                actual = "";
            } else {
                actual += c;
            }
        }

        temp[count++] = actual;

        String[] resultado = new String[count];
        System.arraycopy(temp, 0, resultado, 0, count);
        return resultado;
    }
}
