package com.mycompany.supermercadoedd.estructuras;

/**
 *
 * @author ronald
 */
// Implementa una cola genérica utilizando una lista enlazada
public class Cola<T> {
    // Declara la lista enlazada que almacenará los elementos
    private ListaEnlazada<T> lista;

    // Inicializa la cola
    public Cola() {
        lista = new ListaEnlazada<>();
    }

    // Inserta un elemento al final de la cola
    public void encolar(T dato) {
        lista.insertarAlFinal(dato);
    }

    // Elimina y retorna el primer elemento de la cola
    public T desencolar() {
        if (estaVacia()) {
            throw new IllegalStateException("La cola está vacía.");
        }
        return lista.eliminarAlInicio();
    }

    // Retorna el primer elemento sin eliminarlo
    public T frente() {
        if (estaVacia()) {
            throw new IllegalStateException("La cola está vacía.");
        }
        return lista.obtener(0);
    }

    // Verifica si la cola se encuentra vacía
    public boolean estaVacia() {
        return lista.estaVacia();
    }

    // Retorna el número de elementos en la cola
    public int tamanio() {
        return lista.obtenerTamanio();
    }

    // Elimina todos los elementos de la cola
    public void limpiar() {
        lista.limpiar();
    }

    // Retorna una representación en cadena de la cola
    @Override
    public String toString() {
        return lista.toString();
    }
}
