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
    private ArbolB<String> arbolB;
    private ArbolBPlus<String> arbolBPlus;
    
    // Guarda el historial de operaciones para deshacer cambios posteriores
    private Pila<OperacionRollback> historialCambios;

    // Inicializa una nueva sucursal
    public Sucursal(int id, String nombre, String ubicacion,int tiempoIngreso, int tiempoTraspaso,int tiempoDespacho) {
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
                    new OperacionRollback(id,producto, "LISTA"));

            // Inserta en AVL
            arbolAVL.insertar(producto);

            pilaRollback.apilar(new OperacionRollback(id, producto,"AVL"));

            // Inserta en tabla hash
            tablaHash.insertar(producto.getCodigoBarras(), producto);

            pilaRollback.apilar(
                    new OperacionRollback(id,producto,"HASH"));

            // Inserta en Árbol B
            arbolB.insertar(producto.getFechaCaducidad());

            pilaRollback.apilar(
                    new OperacionRollback(id, producto,"ARBOL_B"));

            // Inserta en Árbol B+
            arbolBPlus.insertar(producto.getCategoria());

            pilaRollback.apilar( new OperacionRollback(id, producto,"ARBOL_B_PLUS") );
            
            // Registra la operación para permitir deshacer posteriormente
            historialCambios.apilar(new OperacionRollback(id, producto,"AGREGAR_PRODUCTO"));

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
            OperacionRollback operacion = pilaRollback.desapilar();
            String estructura = operacion.getEstructura();
            Producto producto = operacion.getProducto();

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
                    arbolB.eliminar(producto.getFechaCaducidad());
                    break;

                case "ARBOL_B_PLUS":
                    arbolBPlus.eliminar(producto.getCategoria());
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

        OperacionRollback operacion = historialCambios.desapilar();
        Producto producto = operacion.getProducto();
        String tipoOperacion = operacion.getEstructura();

        switch (tipoOperacion) {

            case "AGREGAR_PRODUCTO":
                // Elimina el producto de todas las estructuras
                inventario.eliminar(producto);
                arbolAVL.eliminar(producto);
                tablaHash.eliminar(producto.getCodigoBarras());
                arbolB.eliminar(producto.getFechaCaducidad());
                arbolBPlus.eliminar(producto.getCategoria());

                return true;
                
            case "ELIMINAR_PRODUCTO":
                // Si se deshace una eliminación, se vuelve a insertar en todas las estructuras
                agregarProducto(producto);

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
        Producto producto = buscarProductoPorCodigo(codigoBarras);

        // Si no existe el producto, falla
        if (producto == null) {
            return false;
        }

        // Si no hay suficiente stock, falla
        if (producto.getStock() < cantidad) {
            return false;
        }

        // Se descuenta la cantidad solicitada
        producto.setStock(producto.getStock() - cantidad );
        return true;
    }

    // Aumenta el stock de un producto existente. Si no existe, retorna false.
    public boolean aumentarStock(String codigoBarras, int cantidad) {
        Producto producto = buscarProductoPorCodigo(codigoBarras);

        // Si no existe, no puede aumentarse
        if (producto == null) {
            return false;
        }

        // Se suma la nueva cantidad
        producto.setStock(producto.getStock() + cantidad);
        return true;
    }
    
    // Elimina un producto usando su código de barras
    // La eliminación debe propagarse a todas las estructuras
    public boolean eliminarProducto(String codigoBarras) {
        // Primero se busca el producto usando la tabla hash porque es la estructura más rápida para localizarlo
        Producto producto = buscarProductoPorCodigo(codigoBarras);

        // Si no existe, no puede eliminarse
        if (producto == null) {
            return false;
        }

        try {

            // Se elimina de la lista enlazada principal
            inventario.eliminar(producto);

            // Se elimina del árbol AVL
            arbolAVL.eliminar(producto);

            // Se elimina de la tabla hash
            tablaHash.eliminar( producto.getCodigoBarras());

            // Se elimina del Árbol B
            arbolB.eliminar(producto.getFechaCaducidad());

            // Se elimina del Árbol B+
            arbolBPlus.eliminar(producto.getCategoria());

            // Se registra en historial
            historialCambios.apilar(new OperacionRollback(id,producto,"ELIMINAR_PRODUCTO" ));
            return true;

        } catch (Exception e) {
            // Si ocurre algún error inesperado, se reporta como fallo
            return false;
        }
    }
    
    // Busca un producto por nombre usando el AVL como validación principal, se usa después la lista enlazada para recuperar el objeto completo.
    public Producto buscarProductoPorNombre(String nombre) {
        // Se crea un producto temporal únicamente para realizar la comparación dentro del AVL.
        Producto temporal = new Producto( nombre,"","","", "", 0, 0);

        // Si el AVL indica que no existe, se termina inmediatamente
        if (!arbolAVL.contiene(temporal)) {
            return null;
        }

        // Si existe en AVL, se recorre inventario para obtener el objeto real completo
        for (int i = 0; i < inventario.obtenerTamanio(); i++) {
            Producto actual = inventario.obtener(i);

            if (actual.getNombre().equalsIgnoreCase(nombre)) {
                return actual;
            }
        }
        return null;
    }
    
    // Busca un producto de forma secuencial recorriendo únicamente la lista enlazada
    public Producto buscarProductoSecuencialPorNombre(String nombre) {
        for (int i = 0; i < inventario.obtenerTamanio(); i++) {

            Producto actual =  inventario.obtener(i);

            if (actual.getNombre().equalsIgnoreCase(nombre)) {
                return actual;
            }
        }

        return null;
    }
    
    public Producto buscarProductoSecuencialPorCodigo(String codigoBarras) {
        for (int i = 0; i < inventario.obtenerTamanio(); i++) {

            Producto actual = inventario.obtener(i);

            if (actual.getCodigoBarras().equalsIgnoreCase(codigoBarras)) {
                return actual;
            }
        }
        return null;
    }

    // Busca todos los productos cuya fecha de caducidad esté dentro del rango indicado.
    public ListaEnlazada<Producto> buscarProductosPorRangoFecha(String fechaInicio, String fechaFin) {
        // Lista final con los productos encontrados
        ListaEnlazada<Producto> resultados = new ListaEnlazada<>();

        // El Árbol B devuelve todas las fechas que están dentro del rango solicitado
        ListaEnlazada<String> fechasValidas = arbolB.buscarPorRango(fechaInicio,fechaFin);

        // Si no hay fechas encontradas, retorna vacío
        if (fechasValidas.obtenerTamanio() == 0) {
            return resultados;
        }

        // Recorre todo el inventario real
        for (int i = 0; i < inventario.obtenerTamanio(); i++) {
            Producto producto = inventario.obtener(i);

            String fechaProducto = producto.getFechaCaducidad();

            // Verifica si la fecha del producto coincide con alguna fecha encontrada
            for (int j = 0; j < fechasValidas.obtenerTamanio(); j++) {
                String fechaValida = fechasValidas.obtener(j);

                if (fechaProducto.equals(fechaValida)) {
                    resultados.insertarAlFinal(producto);
                    break;
                }
            }
        }
        return resultados;
    }
    
    public ListaEnlazada<Producto> buscarProductosPorCategoria(String categoria) {
        // Lista final de resultados
        ListaEnlazada<Producto> resultados =
                new ListaEnlazada<>();

        // Primero se consulta el Árbol B+
        // Si la categoría no existe, no se sigue buscando
        if (!arbolBPlus.buscar(categoria)) {
            return resultados;
        }

        // Si existe, se recorre el inventario real
        // para recuperar los productos completos
        for (int i = 0; i < inventario.obtenerTamanio(); i++) {

            Producto producto =
                    inventario.obtener(i);

            // Se compara la categoría exacta
            if (producto.getCategoria()
                    .equalsIgnoreCase(categoria)) {

                resultados.insertarAlFinal(producto);
            }
        }

        return resultados;
    }
    
    // Retorna todos los productos ordenados por nombre usando AVL + recorrido InOrder
    public ListaEnlazada<Producto> obtenerProductosOrdenadosPorNombre() {
        return arbolAVL.obtenerElementosEnOrden();
    }
    
    // Retorna todos los productos ordenados por fecha de caducidad usando Árbol B
    public ListaEnlazada<Producto> obtenerProductosOrdenadosPorFecha() {
        // Lista final con productos completos
        ListaEnlazada<Producto> resultados = new ListaEnlazada<>();

        // El Árbol B devuelve las fechas ya ordenadas
        ListaEnlazada<String> fechasOrdenadas = arbolB.obtenerElementosEnOrden();

        // Si no hay fechas, retorna vacío
        if (fechasOrdenadas.obtenerTamanio() == 0) {
            return resultados;
        }

        // Recorre cada fecha en orden
        for (int i = 0; i < fechasOrdenadas.obtenerTamanio(); i++) {

            String fechaActual = fechasOrdenadas.obtener(i);

            // Busca en inventario los productos con esa fecha
            for (int j = 0; j < inventario.obtenerTamanio(); j++) {
                Producto producto = inventario.obtener(j);

                if (producto.getFechaCaducidad().equals(fechaActual)) {
                    resultados.insertarAlFinal(producto);
                }
            }
        }
        return resultados;
    }
    // Retorna todos los productos ordenados por categoría usando Árbol B+
    public ListaEnlazada<Producto> obtenerProductosOrdenadosPorCategoria() {
        ListaEnlazada<Producto> resultados = new ListaEnlazada<>();

        // El Árbol B+ devuelve categorías ordenadas
        ListaEnlazada<String> categoriasOrdenadas =  arbolBPlus.obtenerElementosEnOrden();

        // Si no hay categorías, retorna vacío
        if (categoriasOrdenadas.obtenerTamanio() == 0) {
            return resultados;
        }

        // Recorre cada categoría en orden
        for (int i = 0; i < categoriasOrdenadas.obtenerTamanio(); i++) {

            String categoriaActual = categoriasOrdenadas.obtener(i);

            // Busca productos reales en inventario
            for (int j = 0; j < inventario.obtenerTamanio(); j++) {

                Producto producto = inventario.obtener(j);

                if (producto.getCategoria().equalsIgnoreCase(categoriaActual)) {
                    resultados.insertarAlFinal(producto);
                }
            }
        }
        return resultados;
    }
    
    // Busca productos por coincidencia parcial en el nombre
    public ListaEnlazada<Producto> buscarCoincidenciasParciales(String texto) {
        ListaEnlazada<Producto> resultados = new ListaEnlazada<>();

        // Normaliza texto a minúsculas
        String textoBusqueda = texto.toLowerCase();
        for (int i = 0; i < inventario.obtenerTamanio(); i++) {

            Producto producto = inventario.obtener(i);
            String nombreProducto = producto.getNombre().toLowerCase();

            // Verifica coincidencia parcial
            if (nombreProducto.contains(textoBusqueda)) {
                resultados.insertarAlFinal(producto);
            }
        }
        return resultados;
    }
}
