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
    private GrafoSucursales grafo;

    // Inicializa el sistema principal
    public SistemaSupermercado() {
        // Se crea la lista principal de sucursales
        sucursales = new ListaEnlazada<>();
        
        // Se inicializa el grafo de conexiones
        grafo = new GrafoSucursales();
    }

    // Registra una nueva sucursal en el sistema
    public void agregarSucursal(Sucursal sucursal) {
        // Inserta la sucursal al final de la lista
        sucursales.insertarAlFinal(sucursal);
        grafo.agregarSucursal(sucursal); //agregar dentro del grafo
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
    public boolean registrarProducto(int idSucursal, Producto producto) {
        // Busca la sucursal destino
        Sucursal sucursal = buscarSucursalPorId(idSucursal);

        // Si existe, inserta el producto
        if (sucursal != null) {
            return sucursal.agregarProducto(producto);
        }
        return false;
    }

    // Muestra todas las sucursales registradas
    public void mostrarSucursales() {
        for (int i = 0; i < sucursales.obtenerTamanio(); i++) {
            System.out.println( sucursales.obtener(i));
        }
    }

    // Retorna la lista completa de sucursales
    public ListaEnlazada<Sucursal> getSucursales() {
        return sucursales;
    }
    
    // Traslada cierta cantidad de un producto
    // desde una sucursal origen hacia una sucursal destino
    public boolean trasladarProducto(int idOrigen, int idDestino,String codigoBarras, int cantidad) {

        // Busca ambas sucursales
        Sucursal origen = buscarSucursalPorId(idOrigen);
        Sucursal destino = buscarSucursalPorId(idDestino);

        // Si alguna no existe, falla
        if (origen == null || destino == null) {
            return false;
        }

        // Busca el producto en la sucursal origen
        Producto productoOrigen = origen.buscarProductoPorCodigo(codigoBarras);

        // Si no existe, falla
        if (productoOrigen == null) {
            return false;
        }

        // Verifica stock suficiente
        if (productoOrigen.getStock() < cantidad) {
            return false;
        }

        // Calcula la ruta mínima usando Dijkstra
        int distancia = grafo.dijkstra(idOrigen,idDestino);

        // Si no existe ruta posible, falla
        if (distancia == -1) {
            return false;
        }

        // Descuenta stock en origen
        boolean descuento = origen.disminuirStock(codigoBarras,cantidad);

        if (!descuento) {
            return false;
        }

        // Intenta aumentar en destino
        boolean aumento = destino.aumentarStock(codigoBarras,cantidad);

        // Si no existe el producto en destino, se crea una copia e inserta
        if (!aumento) {
            Producto nuevoProducto =
                    new Producto(
                            productoOrigen.getNombre(),productoOrigen.getCodigoBarras(), productoOrigen.getCategoria(),
                            productoOrigen.getFechaCaducidad(), productoOrigen.getMarca(), productoOrigen.getPrecio(),
                            cantidad);
            destino.agregarProducto(nuevoProducto);
        }

        System.out.println("Traslado realizado correctamente. " +
                "Tiempo mínimo estimado: " + distancia
        );
        return true;
    }
    
    // Elimina un producto de una sucursal específica usando su código de barras
    public boolean eliminarProducto(int idSucursal, String codigoBarras) {
        // Busca la sucursal correspondiente
        Sucursal sucursal = buscarSucursalPorId(idSucursal);

        // Si la sucursal no existe, falla
        if (sucursal == null) {
            return false;
        }
        // Delega la eliminación a la sucursal
        return sucursal.eliminarProducto(codigoBarras);
    }
    
    public Producto buscarProductoPorNombre(String nombre) {
        // Recorre todas las sucursales registradas
        for (int i = 0; i < sucursales.obtenerTamanio(); i++) {

            Sucursal sucursal = sucursales.obtener(i);

            // Busca dentro de esa sucursal
            Producto producto = sucursal.buscarProductoPorNombre(nombre);

            // Si se encuentra, se retorna inmediatamente
            if (producto != null) {
                return producto;
            }
        }
        // Si no existe en ninguna sucursal
        return null;
    }
    
    public ListaEnlazada<Producto> buscarProductosPorRangoFecha(int idSucursal, String fechaInicio,String fechaFin) {
        // Primero se localiza la sucursal
        Sucursal sucursal = buscarSucursalPorId(idSucursal);

        // Si no existe la sucursal, se retorna una lista vacía
        if (sucursal == null) {
            return new ListaEnlazada<>();
        }

        // Se delega la búsqueda a la sucursal
        return sucursal.buscarProductosPorRangoFecha(fechaInicio,fechaFin);
    }
    
    // Busca productos por categoría dentro de una sucursal específica
    public ListaEnlazada<Producto> buscarProductosPorCategoria(int idSucursal, String categoria) {
        // Se busca primero la sucursal correspondiente
        Sucursal sucursal =
                buscarSucursalPorId(idSucursal);

        // Si la sucursal no existe,
        // se retorna una lista vacía
        if (sucursal == null) {
            return new ListaEnlazada<>();
        }

        // Se delega la búsqueda a la sucursal
        return sucursal.buscarProductosPorCategoria(
                categoria
        );
    }

    // Lista todos los productos de una sucursal ordenados por nombre
    public ListaEnlazada<Producto> listarProductosOrdenadosPorNombre(int idSucursal) {
        Sucursal sucursal = buscarSucursalPorId(idSucursal);

        // Si no existe la sucursal, retorna lista vacía
        if (sucursal == null) {
            return new ListaEnlazada<>();
        }

        return sucursal.obtenerProductosOrdenadosPorNombre();
    }
    
    // Lista todos los productos de una sucursal ordenados por fecha de caducidad
    public ListaEnlazada<Producto> listarProductosOrdenadosPorFecha(int idSucursal) {
        Sucursal sucursal = buscarSucursalPorId(idSucursal);

        // Si no existe la sucursal, retorna vacío
        if (sucursal == null) {
            return new ListaEnlazada<>();
        }
        return sucursal.obtenerProductosOrdenadosPorFecha();
    }
}
