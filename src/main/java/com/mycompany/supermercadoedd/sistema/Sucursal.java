package com.mycompany.supermercadoedd.sistema;
import com.mycompany.supermercadoedd.estructuras.ArbolAVL;
import com.mycompany.supermercadoedd.estructuras.ArbolB;
import com.mycompany.supermercadoedd.estructuras.ArbolBPlus;
import com.mycompany.supermercadoedd.estructuras.ListaEnlazada;
import com.mycompany.supermercadoedd.estructuras.Pila;
import com.mycompany.supermercadoedd.estructuras.TablaHash;
import com.mycompany.supermercadoedd.modelos.Producto;
/**
 *
 * @author ronald
 */
// Representa una sucursal del supermercado
public class Sucursal {
    private int id;
    private String nombre;
    private String ubicacion;

    private int tiempoIngreso;
    private int tiempoTraspaso;
    private int tiempoDespacho;

    // Estructuras de datos requeridas
    private ListaEnlazada<Producto> inventario;
    private ArbolAVL<Producto> arbolAVL;
    private TablaHash<String, Producto> tablaHash;
    private ArbolB<Producto> arbolB;
    private ArbolBPlus<Producto> arbolBPlus;
    
    // Guarda el historial de operaciones para deshacer cambios posteriores
    private Pila<OperacionRollback> historialCambios;

    // Inicializa una nueva sucursal
    public Sucursal(int id, String nombre, String ubicacion,
                     int tiempoIngreso, int tiempoTraspaso,
                     int tiempoDespacho) {

        this.id = id;
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.tiempoIngreso = tiempoIngreso;
        this.tiempoTraspaso = tiempoTraspaso;
        this.tiempoDespacho = tiempoDespacho;

        // Inicializa todas las estructuras
        inventario = new ListaEnlazada<>();
        arbolAVL = new ArbolAVL<>();
        tablaHash = new TablaHash<>();
        arbolB = new ArbolB<>();
        arbolBPlus = new ArbolBPlus<>();
        historialCambios = new Pila<>(); // Inicializa la pila de historial de cambios
    }

    // Inserta un producto en todas las estructuras
    public boolean agregarProducto(Producto producto) {
        // Se crea la pila para rollback
        Pila<OperacionRollback> pilaRollback = new Pila<>();

        try {

            // Inserta en lista enlazada
            inventario.insertarAlFinal(producto);

            pilaRollback.apilar(
                    new OperacionRollback(
                            id,
                            producto,
                            "LISTA"
                    )
            );

            // Inserta en AVL
            arbolAVL.insertar(producto);

            pilaRollback.apilar(
                    new OperacionRollback(
                            id,
                            producto,
                            "AVL"
                    )
            );

            // Inserta en tabla hash
            tablaHash.insertar(
                    producto.getCodigoBarras(),
                    producto
            );

            pilaRollback.apilar(
                    new OperacionRollback(
                            id,
                            producto,
                            "HASH"
                    )
            );

            // Inserta en Árbol B
            arbolB.insertar(producto);

            pilaRollback.apilar(
                    new OperacionRollback(
                            id,
                            producto,
                            "ARBOL_B"
                    )
            );

            // Inserta en Árbol B+
            arbolBPlus.insertar(producto);

            pilaRollback.apilar(
                    new OperacionRollback(
                            id,
                            producto,
                            "ARBOL_B_PLUS"
                    )
            );
            
            // Registra la operación para permitir deshacer posteriormente
            historialCambios.apilar(
                    new OperacionRollback(
                            id,
                            producto,
                            "AGREGAR_PRODUCTO"
                    )
            );

            return true; // Si todo salió bien

        } catch (Exception e) {

            realizarRollback(pilaRollback); // Si algo falla, revierte todo
            return false;
        }   
    }
    
    // Revierte todas las operaciones realizadas
    private void realizarRollback(
            Pila<OperacionRollback> pilaRollback) {

        while (!pilaRollback.estaVacia()) {

            OperacionRollback operacion =
                    pilaRollback.desapilar();

            String estructura =
                    operacion.getEstructura();

            Producto producto =
                    operacion.getProducto();

            // Se deshace según la estructura afectada
            switch (estructura) {

                case "LISTA":
                    inventario.eliminar(producto);
                    break;

                case "AVL":
                    arbolAVL.eliminar(producto);
                    break;

                case "HASH":
                    tablaHash.eliminar(
                            producto.getCodigoBarras()
                    );
                    break;

                case "ARBOL_B":
                    arbolB.eliminar(producto);
                    break;

                case "ARBOL_B_PLUS":
                    arbolBPlus.eliminar(producto);
                    break;
            }
        }
    }
    
    // Deshace la última operación registrada
    public boolean deshacerUltimaOperacion() {

        // Verifica si no existen operaciones registradas
        if (historialCambios.estaVacia()) {
            return false;
        }

        OperacionRollback operacion =
                historialCambios.desapilar();

        Producto producto =
                operacion.getProducto();

        String tipoOperacion =
                operacion.getEstructura();

        switch (tipoOperacion) {

            case "AGREGAR_PRODUCTO":

                // Elimina el producto de todas las estructuras
                inventario.eliminar(producto);
                arbolAVL.eliminar(producto);
                tablaHash.eliminar(
                        producto.getCodigoBarras()
                );
                arbolB.eliminar(producto);
                arbolBPlus.eliminar(producto);

                return true;
        }

        return false;
    }

    // Retorna el id de la sucursal
    public int getId() {
        return id;
    }

    // Retorna el nombre
    public String getNombre() {
        return nombre;
    }

    // Retorna la ubicación
    public String getUbicacion() {
        return ubicacion;
    }

    // Retorna el inventario
    public ListaEnlazada<Producto> getInventario() {
        return inventario;
    }

    // Muestra información general de la sucursal
    @Override
    public String toString() {
        return "Sucursal{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", ubicacion='" + ubicacion + '\'' +
                ", tiempoIngreso=" + tiempoIngreso +
                ", tiempoTraspaso=" + tiempoTraspaso +
                ", tiempoDespacho=" + tiempoDespacho +
                '}';
    }
    
    // Busca un producto usando su código de barras.
    // Se aprovecha la tabla hash porque la búsqueda es más rápida.
    public Producto buscarProductoPorCodigo(String codigoBarras) {
        return tablaHash.obtener(codigoBarras);
    }

    // Disminuye el stock de un producto si existe suficiente cantidad.
    // Retorna true si la operación fue exitosa.
    public boolean disminuirStock(String codigoBarras, int cantidad) {

        Producto producto =
                buscarProductoPorCodigo(codigoBarras);

        // Si no existe el producto, falla
        if (producto == null) {
            return false;
        }

        // Si no hay suficiente stock, falla
        if (producto.getStock() < cantidad) {
            return false;
        }

        // Se descuenta la cantidad solicitada
        producto.setStock(
                producto.getStock() - cantidad
        );

        return true;
    }

    // Aumenta el stock de un producto existente.
    // Si no existe, retorna false.
    public boolean aumentarStock(String codigoBarras, int cantidad) {

        Producto producto =
                buscarProductoPorCodigo(codigoBarras);

        // Si no existe, no puede aumentarse
        if (producto == null) {
            return false;
        }

        // Se suma la nueva cantidad
        producto.setStock(
                producto.getStock() + cantidad
        );

        return true;
    }
}
