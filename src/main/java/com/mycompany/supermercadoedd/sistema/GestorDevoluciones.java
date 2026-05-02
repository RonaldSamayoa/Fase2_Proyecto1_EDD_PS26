package com.mycompany.supermercadoedd.sistema;
import com.mycompany.supermercadoedd.modelos.Producto;
/**
 *
 * @author ronald
 */
// Maneja el proceso de devolución de productos utilizando la pila de control de cambios
public class GestorDevoluciones {
    // Procesa la devolución de un producto
    public boolean devolverProducto(Sucursal sucursal, String codigoBarras, int cantidad) {
        // Verifica que la sucursal exista
        if (sucursal == null) {
            return false;
        }

        // Busca el producto dentro de la sucursal
        Producto producto = sucursal.buscarProductoPorCodigo(codigoBarras);

        // Si el producto no existe, no puede devolverse
        if (producto == null) {
            return false;
        }

        // Aumenta nuevamente el stock por devolución
        boolean aumento = sucursal.aumentarStock(codigoBarras, cantidad);

        if (!aumento) {
            return false;
        }

        // Registra visualmente la devolución usando el historial de cambios ya existente
        System.out.println("\n=== DEVOLUCIÓN REGISTRADA ===\n"
                + "Sucursal: " + sucursal.getNombre() + "\n"
                + "Producto: " + producto.getNombre() + "\n"
                + "Cantidad devuelta: " + cantidad + "\n"
                + "Nuevo stock: " + producto.getStock() + "\n"
        );

        return true;
    }
}
