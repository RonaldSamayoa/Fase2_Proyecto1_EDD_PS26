package com.mycompany.supermercadoedd.estructuras;

/**
 *
 * @author ronald
 */
public class ArbolBPlus<T extends Comparable<T>> {
    // Define el grado fijo del árbol
    // Se utiliza grado 3 por facilidad académica
    private final int GRADO = 3;

    // Define la estructura interna del nodo
    private class NodoBPlus {

        T[] claves; // Almacena las claves
        NodoBPlus[] hijos; // Referencias a hijos
        int numeroClaves; // Cantidad actual de claves
        boolean esHoja; // Indica si es hoja

        NodoBPlus siguiente; // Enlace entre hojas

        // Inicializa un nodo del Árbol B+
        @SuppressWarnings("unchecked")
        public NodoBPlus(boolean esHoja) {

            // Reserva espacio para claves
            claves = (T[]) new Comparable[2 * GRADO - 1];

            // Reserva espacio para hijos
            hijos = new ArbolBPlus.NodoBPlus[2 * GRADO];

            // Inicialmente no hay claves
            numeroClaves = 0;

            // Define si será hoja
            this.esHoja = esHoja;

            // Inicialmente no apunta a otra hoja
            this.siguiente = null;
        }
    }

    private NodoBPlus raiz; // Representa la raíz

    // Inicializa el Árbol B+
    public ArbolBPlus() {

        // La raíz inicia como hoja vacía
        raiz = new NodoBPlus(true);
    }

    // Verifica si está vacío
    public boolean estaVacio() {
        return raiz.numeroClaves == 0;
    }

    // Retorna el grado fijo
    public int obtenerGrado() {
        return GRADO;
    }

    // Busca una clave dentro del árbol
    public boolean buscar(T clave) {

        NodoBPlus actual = raiz;

        // Siempre desciende hasta una hoja
        while (!actual.esHoja) {

            int i = 0;

            // Busca el hijo correcto
            while (i < actual.numeroClaves &&
                   clave.compareTo(actual.claves[i]) >= 0) {
                i++;
            }

            actual = actual.hijos[i];
        }

        // Ya en hoja, busca la clave
        for (int i = 0; i < actual.numeroClaves; i++) {
            if (actual.claves[i].compareTo(clave) == 0) {
                return true;
            }
        }

        return false;
    }

    // Muestra todas las hojas enlazadas
    public void mostrarHojas() {

        NodoBPlus actual = raiz;

        // Baja hasta la hoja más izquierda
        while (!actual.esHoja) {
            actual = actual.hijos[0];
        }

        // Recorre todas las hojas enlazadas
        while (actual != null) {

            System.out.print("[ ");

            for (int i = 0; i < actual.numeroClaves; i++) {
                System.out.print(actual.claves[i] + " ");
            }

            System.out.print("] → ");

            actual = actual.siguiente;
        }

        System.out.println("null");
    }
    
    public ListaEnlazada<T> obtenerElementosEnOrden() {
        ListaEnlazada<T> lista = new ListaEnlazada<>();

        NodoBPlus actual = raiz;

        // Baja hasta la hoja más izquierda
        while (!actual.esHoja) {
            actual = actual.hijos[0];
        }

        // Recorre todas las hojas enlazadas
        while (actual != null) {

            for (int i = 0; i < actual.numeroClaves; i++) {
                lista.insertarAlFinal(actual.claves[i]);
            }
            actual = actual.siguiente;
        }
        return lista;
    }
    
        // Inserta una nueva clave en el Árbol B+
    public void insertar(T clave) {

        NodoBPlus raizActual = raiz;

        // Si la raíz está llena, debe dividirse
        if (estaLleno(raizActual)) {

            // Se crea una nueva raíz interna
            NodoBPlus nuevaRaiz = new NodoBPlus(false);

            // La raíz anterior pasa a ser hijo
            nuevaRaiz.hijos[0] = raizActual;

            // Se divide la raíz anterior
            dividirHijo(nuevaRaiz, 0, raizActual);

            // Se actualiza la raíz
            raiz = nuevaRaiz;
        }

        // Continúa la inserción normal
        insertarNoLleno(raiz, clave);
    }
    
        // Verifica si un nodo alcanzó su capacidad máxima
    private boolean estaLleno(NodoBPlus nodo) {
        return nodo.numeroClaves == (2 * GRADO - 1);
    }
    
        // Inserta una clave en un nodo que no está lleno
    private void insertarNoLleno(NodoBPlus nodo, T clave) {

        int i = nodo.numeroClaves - 1;

        // Si es hoja, inserta directamente
        if (nodo.esHoja) {

            // Desplaza claves mayores
            while (i >= 0 &&
                   clave.compareTo(nodo.claves[i]) < 0) {

                nodo.claves[i + 1] = nodo.claves[i];
                i--;
            }

            // Inserta en orden
            nodo.claves[i + 1] = clave;
            nodo.numeroClaves++;

        } else {

            // Busca el hijo correcto
            while (i >= 0 &&
                   clave.compareTo(nodo.claves[i]) < 0) {
                i--;
            }

            i++;

            // Si el hijo está lleno, primero divide
            if (estaLleno(nodo.hijos[i])) {

                dividirHijo(nodo, i, nodo.hijos[i]);

                // Recalcula hacia dónde bajar
                if (clave.compareTo(nodo.claves[i]) >= 0) {
                    i++;
                }
            }

            insertarNoLleno(nodo.hijos[i], clave);
        }
    }
    
        // Divide un nodo lleno
    private void dividirHijo(NodoBPlus padre, int posicion, NodoBPlus nodoLleno) {

        NodoBPlus nuevoNodo = new NodoBPlus(nodoLleno.esHoja);

        // Caso especial: división de hoja
        if (nodoLleno.esHoja) {

            int mitad = GRADO;

            // Copia mitad derecha al nuevo nodo
            for (int i = 0; i < GRADO - 1; i++) {
                nuevoNodo.claves[i] = nodoLleno.claves[i + mitad];
            }

            nuevoNodo.numeroClaves = GRADO - 1;
            nodoLleno.numeroClaves = mitad;

            // Mantiene enlace entre hojas
            nuevoNodo.siguiente = nodoLleno.siguiente;
            nodoLleno.siguiente = nuevoNodo;

            // Desplaza hijos del padre
            for (int i = padre.numeroClaves; i >= posicion + 1; i--) {
                padre.hijos[i + 1] = padre.hijos[i];
            }

            padre.hijos[posicion + 1] = nuevoNodo;

            // Desplaza claves del padre
            for (int i = padre.numeroClaves - 1; i >= posicion; i--) {
                padre.claves[i + 1] = padre.claves[i];
            }

            // Promueve la primera clave del nuevo nodo
            padre.claves[posicion] = nuevoNodo.claves[0];
            padre.numeroClaves++;
        }

        // División de nodo interno
        else {

            int mitad = GRADO - 1;

            for (int i = 0; i < mitad; i++) {
                nuevoNodo.claves[i] = nodoLleno.claves[i + GRADO];
            }

            for (int i = 0; i < GRADO; i++) {
                nuevoNodo.hijos[i] = nodoLleno.hijos[i + GRADO];
            }

            nuevoNodo.numeroClaves = mitad;
            nodoLleno.numeroClaves = mitad;

            for (int i = padre.numeroClaves; i >= posicion + 1; i--) {
                padre.hijos[i + 1] = padre.hijos[i];
            }

            padre.hijos[posicion + 1] = nuevoNodo;

            for (int i = padre.numeroClaves - 1; i >= posicion; i--) {
                padre.claves[i + 1] = padre.claves[i];
            }

            padre.claves[posicion] = nodoLleno.claves[GRADO - 1];
            padre.numeroClaves++;
        }
    }
    
        // Elimina una clave del Árbol B+
    public void eliminar(T clave) {

        // Si está vacío, no hay nada que eliminar
        if (estaVacio()) {
            return;
        }

        eliminar(raiz, clave);

        // Si la raíz queda vacía y no es hoja,
        // se reemplaza por su primer hijo
        if (!raiz.esHoja && raiz.numeroClaves == 0) {
            raiz = raiz.hijos[0];
        }
    }
    
        // Realiza la eliminación recursiva
    private void eliminar(NodoBPlus nodo, T clave) {

        // Si es hoja, elimina directamente
        if (nodo.esHoja) {

            int indice = encontrarIndice(nodo, clave);

            if (indice < nodo.numeroClaves &&
                nodo.claves[indice].compareTo(clave) == 0) {

                eliminarDeHoja(nodo, indice);
            }

            return;
        }

        int indice = encontrarIndice(nodo, clave);

        // Antes de bajar, verifica si el hijo necesita refuerzo
        if (nodo.hijos[indice].numeroClaves < GRADO - 1) {
            llenar(nodo, indice);
        }

        eliminar(nodo.hijos[indice], clave);
    }
    
        // Encuentra la posición correcta de una clave
    private int encontrarIndice(NodoBPlus nodo, T clave) {

        int indice = 0;

        while (indice < nodo.numeroClaves &&
               nodo.claves[indice].compareTo(clave) < 0) {
            indice++;
        }

        return indice;
    }
    
        // Elimina una clave desde una hoja
    private void eliminarDeHoja(NodoBPlus nodo, int indice) {

        // Desplaza las claves hacia la izquierda
        for (int i = indice + 1; i < nodo.numeroClaves; i++) {
            nodo.claves[i - 1] = nodo.claves[i];
        }

        nodo.numeroClaves--;
    }
    
        // Garantiza suficientes claves antes de descender
    private void llenar(NodoBPlus nodo, int indice) {

        // Intenta préstamo desde la izquierda
        if (indice != 0 &&
            nodo.hijos[indice - 1].numeroClaves >= GRADO) {

            prestarDeAnterior(nodo, indice);
        }
        // Intenta préstamo desde la derecha
        else if (indice != nodo.numeroClaves &&
                 nodo.hijos[indice + 1].numeroClaves >= GRADO) {

            prestarDeSiguiente(nodo, indice);
        }
        // Si no se puede prestar, fusiona
        else {

            if (indice != nodo.numeroClaves) {
                fusionar(nodo, indice);
            } else {
                fusionar(nodo, indice - 1);
            }
        }
    }
    
        // Toma una clave prestada desde el hermano izquierdo
    private void prestarDeAnterior(NodoBPlus nodo, int indice) {

        NodoBPlus hijo = nodo.hijos[indice];
        NodoBPlus hermano = nodo.hijos[indice - 1];

        // Desplaza claves del hijo hacia la derecha
        for (int i = hijo.numeroClaves - 1; i >= 0; i--) {
            hijo.claves[i + 1] = hijo.claves[i];
        }

        // Si no es hoja, también desplaza hijos
        if (!hijo.esHoja) {
            for (int i = hijo.numeroClaves; i >= 0; i--) {
                hijo.hijos[i + 1] = hijo.hijos[i];
            }
        }

        // Baja la clave del padre
        hijo.claves[0] = nodo.claves[indice - 1];

        // Si no es hoja, mueve el último hijo del hermano
        if (!hijo.esHoja) {
            hijo.hijos[0] = hermano.hijos[hermano.numeroClaves];
        }

        // Actualiza la clave del padre
        nodo.claves[indice - 1] =
                hermano.claves[hermano.numeroClaves - 1];

        hijo.numeroClaves++;
        hermano.numeroClaves--;
    }
    
        // Toma una clave prestada desde el hermano derecho
    private void prestarDeSiguiente(NodoBPlus nodo, int indice) {

        NodoBPlus hijo = nodo.hijos[indice];
        NodoBPlus hermano = nodo.hijos[indice + 1];

        // Baja la clave del padre al final del hijo
        hijo.claves[hijo.numeroClaves] = nodo.claves[indice];

        // Si no es hoja, mueve el primer hijo del hermano
        if (!hijo.esHoja) {
            hijo.hijos[hijo.numeroClaves + 1] = hermano.hijos[0];
        }

        // Sube la primera clave del hermano al padre
        nodo.claves[indice] = hermano.claves[0];

        // Desplaza claves del hermano
        for (int i = 1; i < hermano.numeroClaves; i++) {
            hermano.claves[i - 1] = hermano.claves[i];
        }

        // Si no es hoja, desplaza hijos
        if (!hermano.esHoja) {
            for (int i = 1; i <= hermano.numeroClaves; i++) {
                hermano.hijos[i - 1] = hermano.hijos[i];
            }
        }

        hijo.numeroClaves++;
        hermano.numeroClaves--;
    }
    
        // Fusiona dos nodos hermanos
    private void fusionar(NodoBPlus nodo, int indice) {

        NodoBPlus hijo = nodo.hijos[indice];
        NodoBPlus hermano = nodo.hijos[indice + 1];

        // Caso especial: si son hojas
        if (hijo.esHoja) {

            // Copia todas las claves del hermano
            for (int i = 0; i < hermano.numeroClaves; i++) {
                hijo.claves[hijo.numeroClaves + i] =
                        hermano.claves[i];
            }

            hijo.numeroClaves += hermano.numeroClaves;

            // Mantiene correctamente el enlace entre hojas
            hijo.siguiente = hermano.siguiente;
        }
        // Si son nodos internos
        else {

            // Baja la clave del padre
            hijo.claves[hijo.numeroClaves] =
                    nodo.claves[indice];

            for (int i = 0; i < hermano.numeroClaves; i++) {
                hijo.claves[hijo.numeroClaves + 1 + i] =
                        hermano.claves[i];
            }

            for (int i = 0; i <= hermano.numeroClaves; i++) {
                hijo.hijos[hijo.numeroClaves + 1 + i] =
                        hermano.hijos[i];
            }

            hijo.numeroClaves += hermano.numeroClaves + 1;
        }

        // Desplaza claves del padre
        for (int i = indice + 1; i < nodo.numeroClaves; i++) {
            nodo.claves[i - 1] = nodo.claves[i];
        }

        // Desplaza hijos del padre
        for (int i = indice + 2; i <= nodo.numeroClaves; i++) {
            nodo.hijos[i - 1] = nodo.hijos[i];
        }

        nodo.numeroClaves--;
    }
}
