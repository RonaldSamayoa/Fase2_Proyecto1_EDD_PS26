package com.mycompany.supermercadoedd.estructuras;

/**
 *
 * @author ronald
 */
// Implementa una tabla hash genérica utilizando encadenamiento separado
public class TablaHash<K,V> {
    // Define la estructura de cada entrada de la tabla hash
    private static class Entrada<K, V> {
        K clave; // Almacena la clave
        V valor; // Almacena el valor asociado

        // Inicializa una nueva entrada con su clave y valor
        public Entrada(K clave, V valor) {
            this.clave = clave;
            this.valor = valor;
        }

        // Retorna una representación en cadena de la entrada
        @Override
        public String toString() {
            return clave + " : " + valor;
        }
    }

    // Declara el arreglo de listas enlazadas que conforman la tabla hash
    private ListaEnlazada<Entrada<K, V>>[] tabla;

    // Almacena la capacidad total de la tabla
    private int capacidad;

    // Lleva el conteo de elementos almacenados
    private int tamanio;

    // Define el factor de carga máximo permitido
    private static final double FACTOR_CARGA = 0.75;

    // Inicializa la tabla hash con una capacidad por defecto
    public TablaHash() {
        this(11); // Se utiliza un número primo para mejorar la distribución
    }

    // Inicializa la tabla hash con una capacidad específica
    @SuppressWarnings("unchecked")
    public TablaHash(int capacidad) {
        this.capacidad = capacidad;
        this.tabla = new ListaEnlazada[capacidad];
        this.tamanio = 0;

        // Inicializa cada posición del arreglo con una lista enlazada
        for (int i = 0; i < capacidad; i++) {
            tabla[i] = new ListaEnlazada<>();
        }
    }

    // Calcula el índice correspondiente a una clave
    private int funcionHash(K clave) {
        return Math.abs(clave.hashCode()) % capacidad;
    }

    // Inserta un par clave-valor en la tabla hash
    public void insertar(K clave, V valor) {
        // Verifica si la clave es nula
        if (clave == null) {
            throw new IllegalArgumentException("La clave no puede ser nula.");
        }

        // Calcula el índice correspondiente
        int indice = funcionHash(clave);

        // Obtiene la lista enlazada en la posición calculada
        ListaEnlazada<Entrada<K, V>> lista = tabla[indice];

        // Recorre la lista para verificar si la clave ya existe
        for (int i = 0; i < lista.obtenerTamanio(); i++) {
            Entrada<K, V> entrada = lista.obtener(i);

            // Actualiza el valor si la clave ya existe
            if (entrada.clave.equals(clave)) {
                entrada.valor = valor;
                return;
            }
        }

        // Inserta la nueva entrada en la lista
        lista.insertarAlFinal(new Entrada<>(clave, valor));
        tamanio++;

        // Verifica si es necesario redimensionar la tabla
        if ((double) tamanio / capacidad >= FACTOR_CARGA) {
            redimensionar();
        }
    }

    // Busca el valor asociado a una clave
    public V obtener(K clave) {
        // Verifica si la clave es nula
        if (clave == null) {
            throw new IllegalArgumentException("La clave no puede ser nula.");
        }

        // Calcula el índice correspondiente
        int indice = funcionHash(clave);

        // Obtiene la lista enlazada en la posición calculada
        ListaEnlazada<Entrada<K, V>> lista = tabla[indice];

        // Recorre la lista en busca de la clave
        for (int i = 0; i < lista.obtenerTamanio(); i++) {
            Entrada<K, V> entrada = lista.obtener(i);

            // Retorna el valor si la clave es encontrada
            if (entrada.clave.equals(clave)) {
                return entrada.valor;
            }
        }

        // Retorna nulo si la clave no existe
        return null;
    }

    // Elimina una entrada de la tabla hash
    public V eliminar(K clave) {
        // Verifica si la clave es nula
        if (clave == null) {
            throw new IllegalArgumentException("La clave no puede ser nula.");
        }

        // Calcula el índice correspondiente
        int indice = funcionHash(clave);

        // Obtiene la lista enlazada en la posición calculada
        ListaEnlazada<Entrada<K, V>> lista = tabla[indice];

        // Recorre la lista para localizar la clave
        for (int i = 0; i < lista.obtenerTamanio(); i++) {
            Entrada<K, V> entrada = lista.obtener(i);

            // Elimina la entrada si la clave coincide
            if (entrada.clave.equals(clave)) {
                V valor = entrada.valor;
                lista.eliminarEnPosicion(i);
                tamanio--;
                return valor;
            }
        }

        // Retorna nulo si la clave no existe
        return null;
    }

    // Verifica si la tabla contiene una clave específica
    public boolean contieneClave(K clave) {
        return obtener(clave) != null;
    }

    // Retorna el número de elementos almacenados
    public int tamanio() {
        return tamanio;
    }

    // Verifica si la tabla está vacía
    public boolean estaVacia() {
        return tamanio == 0;
    }

    // Elimina todos los elementos de la tabla
    public void limpiar() {
        for (int i = 0; i < capacidad; i++) {
            tabla[i].limpiar();
        }
        tamanio = 0;
    }

    // Redimensiona la tabla cuando se supera el factor de carga
    @SuppressWarnings("unchecked")
    private void redimensionar() {
        ListaEnlazada<Entrada<K, V>>[] tablaAnterior = tabla;
        capacidad = capacidad * 2 + 1;
        tabla = new ListaEnlazada[capacidad];
        tamanio = 0;

        // Inicializa la nueva tabla
        for (int i = 0; i < capacidad; i++) {
            tabla[i] = new ListaEnlazada<>();
        }

        // Reubica los elementos en la nueva tabla
        for (ListaEnlazada<Entrada<K, V>> lista : tablaAnterior) {
            for (int i = 0; i < lista.obtenerTamanio(); i++) {
                Entrada<K, V> entrada = lista.obtener(i);
                insertar(entrada.clave, entrada.valor);
            }
        }
    }

    // Retorna una representación en cadena de la tabla hash
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < capacidad; i++) {
            sb.append("Índice ").append(i).append(": ");

            ListaEnlazada<Entrada<K, V>> lista = tabla[i];

            if (lista.estaVacia()) {
                sb.append("null");
            } else {
                for (int j = 0; j < lista.obtenerTamanio(); j++) {
                    sb.append(lista.obtener(j)).append(" -> ");
                }
                sb.append("null");
            }

            sb.append("\n");
        }

        return sb.toString();
    }
}
