package com.mycompany.supermercadoedd.estructuras;

/**
 *
 * @author ronald
 */
public class ListaEnlazada<T> {
    // Referencia al primer nodo de la lista
    private Nodo<T> cabeza;

    // Contador de elementos almacenados en la lista
    private int tamanio;

    // Representa un nodo de la lista enlazada. Contiene el dato y la referencia al siguiente nodo.
    private static class Nodo<T> {

        // Almacena el dato del nodo
        private T dato;

        // Referencia al siguiente nodo en la lista
        private Nodo<T> siguiente;

        //@param dato Elemento que se almacenará en el nodo.
        public Nodo(T dato) {
            this.dato = dato;
            this.siguiente = null;
        }
    }

    public ListaEnlazada() {
        this.cabeza = null;
        this.tamanio = 0;
    }

    //Inserta un elemento al inicio de la lista.
    public void insertarAlInicio(T dato) {
        Nodo<T> nuevo = new Nodo<>(dato);
        nuevo.siguiente = cabeza;
        cabeza = nuevo;
        tamanio++;
    }

    //Inserta un elemento al final de la lista.
    public void insertarAlFinal(T dato) {
        Nodo<T> nuevo = new Nodo<>(dato);

        // Verifica si la lista está vacía
        if (cabeza == null) {
            cabeza = nuevo;
        } else {
            Nodo<T> actual = cabeza;

            // Recorre la lista hasta llegar al último nodo
            while (actual.siguiente != null) {
                actual = actual.siguiente;
            }

            // Enlaza el nuevo nodo al final
            actual.siguiente = nuevo;
        }
        tamanio++;
    }

    //Inserta un elemento en una posición específica.
    public void insertarEnPosicion(T dato, int indice) {
        if (indice < 0 || indice > tamanio) {
            throw new IndexOutOfBoundsException("Índice fuera de rango.");
        }

        if (indice == 0) {
            insertarAlInicio(dato);
            return;
        }

        Nodo<T> nuevo = new Nodo<>(dato);
        Nodo<T> actual = cabeza;

        // Recorre la lista hasta la posición anterior al índice
        for (int i = 0; i < indice - 1; i++) {
            actual = actual.siguiente;
        }

        nuevo.siguiente = actual.siguiente;
        actual.siguiente = nuevo;
        tamanio++;
    }

    // Elimina el primer elemento de la lista.
    public T eliminarAlInicio() {
        if (estaVacia()) {
            throw new IllegalStateException("La lista está vacía.");
        }

        T dato = cabeza.dato;
        cabeza = cabeza.siguiente;
        tamanio--;
        return dato;
    }

    //Elimina el último elemento de la lista
    public T eliminarAlFinal() {
        if (estaVacia()) {
            throw new IllegalStateException("La lista está vacía.");
        }

        // Caso en el que solo existe un elemento
        if (cabeza.siguiente == null) {
            T dato = cabeza.dato;
            cabeza = null;
            tamanio--;
            return dato;
        }

        Nodo<T> actual = cabeza;

        // Recorre la lista hasta el penúltimo nodo
        while (actual.siguiente.siguiente != null) {
            actual = actual.siguiente;
        }

        T dato = actual.siguiente.dato;
        actual.siguiente = null;
        tamanio--;
        return dato;
    }

    // Elimina un elemento en una posición específica.
    public T eliminarEnPosicion(int indice) {
        if (indice < 0 || indice >= tamanio) {
            throw new IndexOutOfBoundsException("Índice fuera de rango.");
        }

        if (indice == 0) {
            return eliminarAlInicio();
        }

        Nodo<T> actual = cabeza;

        // Recorre la lista hasta la posición anterior
        for (int i = 0; i < indice - 1; i++) {
            actual = actual.siguiente;
        }

        T dato = actual.siguiente.dato;
        actual.siguiente = actual.siguiente.siguiente;
        tamanio--;
        return dato;
    }
    
    // Elimina la primera aparición de un dato específico
    public boolean eliminar(T dato) {

        // Verifica si la lista está vacía
        if (estaVacia()) {
            return false;
        }

        // Caso especial: el dato está en la cabeza
        if (cabeza.dato.equals(dato)) {
            cabeza = cabeza.siguiente;
            tamanio--;
            return true;
        }

        Nodo<T> actual = cabeza;

        // Busca el nodo anterior al que se desea eliminar
        while (actual.siguiente != null) {

            if (actual.siguiente.dato.equals(dato)) {

                actual.siguiente = actual.siguiente.siguiente;
                tamanio--;
                return true;
            }

            actual = actual.siguiente;
        }

        // Si no se encontró el dato
        return false;
    }

    //Obtiene un elemento según su posición.
    public T obtener(int indice) {
        if (indice < 0 || indice >= tamanio) {
            throw new IndexOutOfBoundsException("Índice fuera de rango.");
        }

        Nodo<T> actual = cabeza;

        // Recorre la lista hasta la posición indicada
        for (int i = 0; i < indice; i++) {
            actual = actual.siguiente;
        }

        return actual.dato;
    }

    //Busca un elemento dentro de la lista.
    public boolean contiene(T dato) {
        Nodo<T> actual = cabeza;

        // Recorre la lista en busca del elemento
        while (actual != null) {
            if (actual.dato.equals(dato)) {
                return true;
            }
            actual = actual.siguiente;
        }

        return false;
    }

    //Obtiene el tamaño de la lista.
    public int obtenerTamanio() {
        return tamanio;
    }

    //Verifica si la lista se encuentra vacía.
    public boolean estaVacia() {
        return cabeza == null;
    }

    //Elimina todos los elementos de la lista.
    public void limpiar() {
        cabeza = null;
        tamanio = 0;
    }

    //Genera una representación en cadena de la lista
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Nodo<T> actual = cabeza;

        while (actual != null) {
            sb.append(actual.dato).append(" -> ");
            actual = actual.siguiente;
        }

        sb.append("null");
        return sb.toString();
    }
}
