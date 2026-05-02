package com.mycompany.supermercadoedd.sistema;
import com.mycompany.supermercadoedd.modelos.Producto;
/**
 *
 * @author ronald
 */
// Maneja todo el sistema de colas y despacho entre sucursales
public class GestorDespacho {
    // Procesa el traslado completo entre dos sucursales
    public boolean trasladarProducto(Sucursal origen, Sucursal destino, String codigoBarras, int cantidad, int distancia) {
        if (origen == null || destino == null) {
            return false;
        }

        Producto productoOrigen = origen.buscarProductoPorCodigo(codigoBarras);

        if (productoOrigen == null) {
            return false;
        }

        if (productoOrigen.getStock() < cantidad) {
            return false;
        }

        boolean descuento = origen.disminuirStock(codigoBarras, cantidad);

        if (!descuento) {
            return false;
        }

        // Se crea copia del producto para traslado
        Producto productoTraslado = new Producto(
                        productoOrigen.getNombre(),
                        productoOrigen.getCodigoBarras(),
                        productoOrigen.getCategoria(),
                        productoOrigen.getFechaCaducidad(),
                        productoOrigen.getMarca(),
                        productoOrigen.getPrecio(),
                        cantidad
                );
        
        // Paso 1: origen → cola salida
        origen.getColaSalida().encolar(productoTraslado);

        // Paso 2: sale de origen
        origen.getColaSalida().desencolar();

        // Paso 3: llega a destino → cola ingreso
        destino.getColaIngreso().encolar(productoTraslado);

        // Paso 4: pasa a preparación
        Producto recibido = destino.getColaIngreso().desencolar();

        destino.getColaPreparacionTraspaso().encolar(recibido);

        // Paso 5: preparación terminada
        Producto preparado = destino.getColaPreparacionTraspaso().desencolar();

        // Paso 6: se intenta sumar stock
        boolean aumento = destino.aumentarStock(preparado.getCodigoBarras(),cantidad);

        // Si no existe, se agrega completo
        if (!aumento) {
            destino.agregarProducto(preparado);
        }

        System.out.println(
                "\n=== TRASLADO COMPLETADO ===\n"
                + "Origen: " + origen.getNombre() + "\n"
                + "Destino: " + destino.getNombre() + "\n"
                + "Producto: " + preparado.getNombre() + "\n"
                + "Cantidad: " + cantidad + "\n"
                + "Tiempo estimado: " + distancia + "\n"
        );

        return true;
    }

    // Muestra el estado actual de colas
    public String mostrarEstadoColas(Sucursal sucursal) {

        if (sucursal == null) {
            return "Sucursal no encontrada.";
        }

        String reporte = "";

        reporte += "\n=== ESTADO DE COLAS ===\n";
        reporte += "Sucursal: "
                + sucursal.getNombre() + "\n";

        reporte += "Cola ingreso: "
                + sucursal.getColaIngreso()
                + "\n";

        reporte += "Cola preparación: "
                + sucursal.getColaPreparacionTraspaso()
                + "\n";

        reporte += "Cola salida: "
                + sucursal.getColaSalida()
                + "\n";
        return reporte;
    }
}
