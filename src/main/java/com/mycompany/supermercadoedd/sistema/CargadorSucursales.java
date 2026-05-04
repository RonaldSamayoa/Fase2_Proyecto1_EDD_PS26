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

                // Detecta encabezado en cualquier línea
                if (linea.toLowerCase().contains("id") &&
                    linea.toLowerCase().contains("nombre")) {
                    continue;
                }

                try {
                    String[] datos = parsearLineaCSV(linea);

                    // EXACTAMENTE 6 columnas
                    if (datos.length != 6) {
                        resultado.agregarFallido("Línea " + numeroLinea + ": formato incorrecto (se esperaban 6 columnas)");
                        continue;
                    }

                    String idStr = limpiarCampo(datos[0]);
                    String nombre = limpiarCampo(datos[1]);
                    String ubicacion = limpiarCampo(datos[2]);
                    String tIngresoStr = limpiarCampo(datos[3]);
                    String tTraspasoStr = limpiarCampo(datos[4]);
                    String tDespachoStr = limpiarCampo(datos[5]);

                    // Campos vacíos
                    if (idStr.isEmpty() || nombre.isEmpty() || ubicacion.isEmpty() ||
                        tIngresoStr.isEmpty() || tTraspasoStr.isEmpty() || tDespachoStr.isEmpty()) {

                        resultado.agregarFallido("Línea " + numeroLinea + ": campos vacíos");
                        continue;
                    }

                    int id = Integer.parseInt(idStr);
                    int tIngreso = Integer.parseInt(tIngresoStr);
                    int tTraspaso = Integer.parseInt(tTraspasoStr);
                    int tDespacho = Integer.parseInt(tDespachoStr);

                    // Validaciones
                    if (id <= 0 || tIngreso <= 0 || tTraspaso <= 0 || tDespacho <= 0) {
                        resultado.agregarFallido("Línea " + numeroLinea + ": valores deben ser > 0");
                        continue;
                    }

                    if (sistema.existeSucursal(id)) {
                        resultado.agregarFallido("Línea " + numeroLinea + ": ID de sucursal duplicado");
                        continue;
                    }

                    Sucursal sucursal = new Sucursal(id, nombre, ubicacion, tIngreso, tTraspaso, tDespacho);
                    sistema.agregarSucursal(sucursal);

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
