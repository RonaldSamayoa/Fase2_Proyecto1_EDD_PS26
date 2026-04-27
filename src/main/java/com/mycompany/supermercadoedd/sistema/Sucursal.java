package com.mycompany.supermercadoedd.sistema;
import com.mycompany.supermercadoedd.estructuras.ArbolAVL;
import com.mycompany.supermercadoedd.estructuras.ArbolB;
import com.mycompany.supermercadoedd.estructuras.ArbolBPlus;
import com.mycompany.supermercadoedd.estructuras.ListaEnlazada;
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
    }

    // Inserta un producto en todas las estructuras
    public void agregarProducto(Producto producto) {

        inventario.insertarAlFinal(producto);

        arbolAVL.insertar(producto);

        tablaHash.insertar(
                producto.getCodigoBarras(),
                producto
        );

        arbolB.insertar(producto);

        arbolBPlus.insertar(producto);
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
}
