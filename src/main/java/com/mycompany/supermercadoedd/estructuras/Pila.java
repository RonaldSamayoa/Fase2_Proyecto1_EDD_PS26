package com.mycompany.supermercadoedd.estructuras;

/**
 *
 * @author ronald
 */
// Implementa una pila genérica utilizando una lista enlazada
public class Pila<T> {
     // Declara la lista enlazada que almacenará los elementos
    private ListaEnlazada<T> lista;

    // Inicializa la pila
    public Pila() {
        lista = new ListaEnlazada<>();
    }

    // Inserta un elemento en la cima de la pila
    public void apilar(T dato) {
        lista.insertarAlInicio(dato);
    }

    // Elimina y retorna el elemento en la cima de la pila
    public T desapilar() {
        if (estaVacia()) {
            throw new IllegalStateException("La pila está vacía.");
        }
        return lista.eliminarAlInicio();
    }

    // Retorna el elemento en la cima sin eliminarlo
    public T cima() {
        if (estaVacia()) {
            throw new IllegalStateException("La pila está vacía.");
        }
        return lista.obtener(0);
    }

    // Verifica si la pila se encuentra vacía
    public boolean estaVacia() {
        return lista.estaVacia();
    }

    // Retorna el número de elementos en la pila
    public int tamanio() {
        return lista.obtenerTamanio();
    }

    // Elimina todos los elementos de la pila
    public void limpiar() {
        lista.limpiar();
    }

    // Retorna una representación en cadena de la pila
    @Override
    public String toString() {
        return lista.toString();
    }
}
