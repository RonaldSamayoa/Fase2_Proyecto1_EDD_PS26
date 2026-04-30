package com.mycompany.supermercadoedd.sistema;
import com.mycompany.supermercadoedd.estructuras.ListaEnlazada;
/**
 *
 * @author ronald
 */
// Administra toda la red de conexiones entre sucursales
// Esta clase representa el grafo completo del sistema
public class GrafoSucursales {

    // Guarda todas las sucursales registradas dentro del grafo.
    // Cada sucursal representa un nodo
    private ListaEnlazada<Sucursal> sucursales;

    // Arreglo de listas de adyacencia.
    // Cada posición representa las conexiones salientes de una sucursal específica.
    private ListaEnlazada<ConexionSucursal>[] conexiones;
    // Capacidad máxima inicial del arreglo. Si se supera, se expande dinámicamente.
    private int capacidad;

    // Lleva el control de cuántas sucursales existen realmente.
    private int cantidadSucursales;

    // Constructor principal del grafo
    @SuppressWarnings("unchecked")
    public GrafoSucursales() {

        // Se define una capacidad inicial razonable
        capacidad = 20;

        // Se inicializa la lista principal de sucursales
        sucursales = new ListaEnlazada<>();

        // Se crea el arreglo de listas de conexiones
        conexiones = new ListaEnlazada[capacidad];

        // Inicialmente no hay sucursales registradas
        cantidadSucursales = 0;

        // Cada posición del arreglo debe inicializarse
        // con su propia lista enlazada
        for (int i = 0; i < capacidad; i++) {
            conexiones[i] = new ListaEnlazada<>();
        }
    }

    // Agrega una nueva sucursal al grafo
    public void agregarSucursal(Sucursal sucursal) {

        // Evita registrar duplicados
        if (buscarIndiceSucursal(sucursal.getId()) != -1) {
            return;
        }

        // Si ya no hay espacio suficiente,
        // se expande la capacidad del arreglo
        if (cantidadSucursales >= capacidad) {
            expandirCapacidad();
        }

        // Se inserta la sucursal como nuevo nodo
        sucursales.insertarAlFinal(sucursal);

        // Se actualiza el contador
        cantidadSucursales++;
    }

    // Crea una conexión entre dos sucursales
    public void agregarConexion(int idOrigen,
                                int idDestino,
                                int peso) {

        // Se obtiene la posición real de ambas sucursales
        int indiceOrigen = buscarIndiceSucursal(idOrigen);
        int indiceDestino = buscarIndiceSucursal(idDestino);

        // Si alguna no existe, no se puede crear conexión
        if (indiceOrigen == -1 || indiceDestino == -1) {
            return;
        }

        // Se obtiene la sucursal destino
        Sucursal destino = sucursales.obtener(indiceDestino);
        Sucursal origen = sucursales.obtener(indiceOrigen);

            // conexión origen → destino
            conexiones[indiceOrigen].insertarAlFinal(
                    new ConexionSucursal(destino, peso)
            );

            // conexión destino → origen
            conexiones[indiceDestino].insertarAlFinal(
                    new ConexionSucursal(origen, peso)
            );
    }

    // Busca la posición real de una sucursal usando su ID
    private int buscarIndiceSucursal(int idSucursal) {

        // Recorre toda la lista principal
        for (int i = 0; i < sucursales.obtenerTamanio(); i++) {

            // Si encuentra coincidencia, retorna posición
            if (sucursales.obtener(i).getId() == idSucursal) {
                return i;
            }
        }

        // Si no existe, retorna -1
        return -1;
    }

    // Expande el arreglo cuando se llena
    @SuppressWarnings("unchecked")
    private void expandirCapacidad() {

        // Se duplica la capacidad actual
        int nuevaCapacidad = capacidad * 2;

        // Se crea nuevo arreglo más grande
        ListaEnlazada<ConexionSucursal>[] nuevo =
                new ListaEnlazada[nuevaCapacidad];

        // Se inicializan todas las nuevas posiciones
        for (int i = 0; i < nuevaCapacidad; i++) {
            nuevo[i] = new ListaEnlazada<>();
        }

        // Se copian todas las conexiones anteriores
        for (int i = 0; i < capacidad; i++) {
            nuevo[i] = conexiones[i];
        }

        // Se reemplaza el arreglo viejo
        conexiones = nuevo;

        // Se actualiza la nueva capacidad
        capacidad = nuevaCapacidad;
    }

    // Muestra visualmente todo el grafo
    public void mostrarGrafo() {

        // Recorre todas las sucursales registradas
        for (int i = 0; i < cantidadSucursales; i++) {

            Sucursal origen = sucursales.obtener(i);

            System.out.print(
                    origen.getNombre() + " -> "
            );

            // Obtiene las conexiones de esa sucursal
            ListaEnlazada<ConexionSucursal> lista =
                    conexiones[i];

            // Recorre todas las conexiones existentes
            for (int j = 0; j < lista.obtenerTamanio(); j++) {

                ConexionSucursal conexion =
                        lista.obtener(j);

                System.out.print(
                        "[" +
                        conexion.getDestino().getNombre() +
                        ", peso=" +
                        conexion.getPeso() +
                        "] "
                );
            }

            System.out.println();
        }
    }

    // Retorna todas las sucursales registradas
    public ListaEnlazada<Sucursal> getSucursales() {
        return sucursales;
    }
    
    // Calcula la distancia mínima entre dos sucursales
    public int dijkstra(int idOrigen, int idDestino) {
        // Se buscan los índices reales dentro del grafo
        int origen = buscarIndiceSucursal(idOrigen);
        int destino = buscarIndiceSucursal(idDestino);

        // Si alguna sucursal no existe, no puede calcularse
        if (origen == -1 || destino == -1) {
            return -1;
        }

        // Arreglo de distancias mínimas acumuladas
        int[] distancias = new int[cantidadSucursales];

        // Controla qué nodos ya fueron procesados
        boolean[] visitado = new boolean[cantidadSucursales];

        // Inicializa todas las distancias en infinito
        for (int i = 0; i < cantidadSucursales; i++) {
            distancias[i] = Integer.MAX_VALUE;
            visitado[i] = false;
        }

        // La distancia desde origen hacia sí mismo es 0
        distancias[origen] = 0;

        // Se repite para cada sucursal
        for (int i = 0; i < cantidadSucursales; i++) {

            // Busca el nodo no visitado con menor distancia
            int actual = obtenerMenorDistancia(
                    distancias,
                    visitado
            );

            // Si no hay más nodos alcanzables, termina
            if (actual == -1) {
                break;
            }

            // Marca como visitado
            visitado[actual] = true;

            // Recorre todas las conexiones del nodo actual
            ListaEnlazada<ConexionSucursal> lista =
                    conexiones[actual];

            for (int j = 0; j < lista.obtenerTamanio(); j++) {

                ConexionSucursal conexion =
                        lista.obtener(j);

                int vecino = buscarIndiceSucursal(
                        conexion.getDestino().getId()
                );

                int peso = conexion.getPeso();

                // Relajación clásica de Dijkstra
                if (!visitado[vecino] &&
                    distancias[actual] != Integer.MAX_VALUE &&
                    distancias[actual] + peso < distancias[vecino]) {

                    distancias[vecino] =
                            distancias[actual] + peso;
                }
            }
        }

        // Si sigue infinito, no existe ruta
        if (distancias[destino] == Integer.MAX_VALUE) {
            return -1;
        }

        return distancias[destino];
    }

    // Busca el nodo no visitado con menor distancia acumulada
    private int obtenerMenorDistancia(
            int[] distancias,
            boolean[] visitado) {

        int minimo = Integer.MAX_VALUE;
        int indice = -1;

        for (int i = 0; i < cantidadSucursales; i++) {

            if (!visitado[i] &&
                distancias[i] < minimo) {

                minimo = distancias[i];
                indice = i;
            }
        }

        return indice;
    }
}
