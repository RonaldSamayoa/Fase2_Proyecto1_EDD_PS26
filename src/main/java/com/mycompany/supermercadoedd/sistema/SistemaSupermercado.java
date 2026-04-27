package com.mycompany.supermercadoedd.sistema;

import com.mycompany.supermercadoedd.estructuras.ListaEnlazada;
import com.mycompany.supermercadoedd.modelos.Producto;

/**
 *
 * @author ronald
 */
// Administra todas las sucursales del sistema
public class SistemaSupermercado {
    // Almacena todas las sucursales registradas
    private ListaEnlazada<Sucursal> sucursales;

    // Inicializa el sistema principal
    public SistemaSupermercado() {

        // Se crea la lista principal de sucursales
        sucursales = new ListaEnlazada<>();
    }

    // Registra una nueva sucursal en el sistema
    public void agregarSucursal(Sucursal sucursal) {

        // Inserta la sucursal al final de la lista
        sucursales.insertarAlFinal(sucursal);
    }

    // Busca una sucursal usando su id
    public Sucursal buscarSucursalPorId(int id) {

        // Recorre toda la lista de sucursales
        for (int i = 0; i < sucursales.obtenerTamanio(); i++) {

            Sucursal actual = sucursales.obtener(i);

            // Verifica si coincide el id
            if (actual.getId() == id) {
                return actual;
            }
        }

        // Si no existe, retorna null
        return null;
    }

    // Registra un producto dentro de su sucursal correspondiente
    public void registrarProducto(int idSucursal, Producto producto) {

        // Busca la sucursal destino
        Sucursal sucursal = buscarSucursalPorId(idSucursal);

        // Si existe, inserta el producto
        if (sucursal != null) {
            sucursal.agregarProducto(producto);
        }
    }

    // Muestra todas las sucursales registradas
    public void mostrarSucursales() {

        for (int i = 0; i < sucursales.obtenerTamanio(); i++) {
            System.out.println(
                    sucursales.obtener(i)
            );
        }
    }

    // Retorna la lista completa de sucursales
    public ListaEnlazada<Sucursal> getSucursales() {
        return sucursales;
    }
}
