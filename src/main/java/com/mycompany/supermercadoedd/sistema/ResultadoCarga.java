package com.mycompany.supermercadoedd.sistema;

/**
 *
 * @author ronald
 */
//almacena el resultado final de la carga desde csv
public class ResultadoCarga {
    private int registrosExitosos;
    private int registrosFallidos;
    private String detalleErrores;

    // Inicializa el resultado de carga
    public ResultadoCarga() {

        // Inicialmente no existen registros procesados
        registrosExitosos = 0;
        registrosFallidos = 0;
        detalleErrores = "";
    }

    // Aumenta en uno los registros cargados correctamente
    public void agregarExitoso() {
        registrosExitosos++;
    }

    // Aumenta en uno los registros fallidos y guarda el error
    public void agregarFallido(String error) {

        registrosFallidos++;

        // Se acumula el detalle del error para reportes posteriores
        detalleErrores += error + "\n";
    }

    // Retorna la cantidad de registros exitosos
    public int getRegistrosExitosos() {
        return registrosExitosos;
    }

    // Retorna la cantidad de registros fallidos
    public int getRegistrosFallidos() {
        return registrosFallidos;
    }

    // Retorna el detalle completo de errores
    public String getDetalleErrores() {
        return detalleErrores;
    }

    // Muestra un resumen del proceso de carga
    @Override
    public String toString() {
        return "ResultadoCarga{" +
                "registrosExitosos=" + registrosExitosos +
                ", registrosFallidos=" + registrosFallidos +
                ", detalleErrores=\n" + detalleErrores +
                '}';
    }
}
