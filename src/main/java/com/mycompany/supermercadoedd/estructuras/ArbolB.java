package com.mycompany.supermercadoedd.estructuras;

/**
 *
 * @author ronald
 */
// Implementa la estructura base de un Árbol B genérico
public class ArbolB<T extends Comparable<T>> {

    // Define el grado fijo del Árbol B
    // Se utiliza grado 3 por facilidad académica y mejor visualización
    private final int GRADO = 3;

    // Define la estructura interna de cada nodo
    private class NodoB {

        T[] claves; // Almacena las claves del nodo
        NodoB[] hijos; // Almacena las referencias a los hijos
        int numeroClaves; // Indica cuántas claves tiene actualmente
        boolean esHoja; // Indica si el nodo es hoja o no

        // Inicializa un nuevo nodo del Árbol B
        @SuppressWarnings("unchecked")
        public NodoB(boolean esHoja) {

            // Se reserva espacio para el máximo de claves
            // Fórmula: 2 * t - 1
            claves = (T[]) new Comparable[2 * GRADO - 1];

            // Se reserva espacio para el máximo de hijos
            // Fórmula: 2 * t
            hijos = new ArbolB.NodoB[2 * GRADO];

            // Inicialmente no existen claves almacenadas
            numeroClaves = 0;

            // Se define si será hoja o nodo interno
            this.esHoja = esHoja;
        }
    }

    private NodoB raiz; // Representa la raíz del árbol

    // Inicializa el Árbol B
    public ArbolB() {

        // La raíz inicia como una hoja vacía
        raiz = new NodoB(true);
    }

    // Verifica si el árbol está vacío
    public boolean estaVacio() {
        return raiz.numeroClaves == 0;
    }

    // Retorna el grado fijo del árbol
    public int obtenerGrado() {
        return GRADO;
    }

    // Busca una clave dentro del árbol
    public boolean buscar(T clave) {
        return buscar(raiz, clave);
    }

    // Realiza la búsqueda recursiva
    private boolean buscar(NodoB nodo, T clave) {

        int i = 0;

        // Busca la posición correcta dentro del nodo
        while (i < nodo.numeroClaves && clave.compareTo(nodo.claves[i]) > 0) {
            i++;
        }

        // Verifica si la clave fue encontrada
        if (i < nodo.numeroClaves && clave.compareTo(nodo.claves[i]) == 0) {
            return true;
        }

        // Si es hoja y no se encontró, entonces no existe
        if (nodo.esHoja) {
            return false;
        }

        // Continúa la búsqueda en el hijo correspondiente
        return buscar(nodo.hijos[i], clave);
    }

    // Realiza el recorrido en orden del árbol
    public void recorrido() {
        recorrido(raiz);
        System.out.println();
    }

    // Implementa el recorrido recursivo
    private void recorrido(NodoB nodo) {

        int i;

        // Recorre todas las claves y sus hijos
        for (i = 0; i < nodo.numeroClaves; i++) {

            // Si no es hoja, primero recorre el hijo izquierdo
            if (!nodo.esHoja) {
                recorrido(nodo.hijos[i]);
            }

            // Muestra la clave actual
            System.out.print(nodo.claves[i] + " ");
        }

        // Recorre el último hijo
        if (!nodo.esHoja) {
            recorrido(nodo.hijos[i]);
        }
    }

    // Muestra visualmente la estructura del árbol
    public void mostrarEstructura() {
        mostrarEstructura(raiz, 0);
    }

    // Muestra el árbol por niveles
    private void mostrarEstructura(NodoB nodo, int nivel) {

        // Genera sangría según el nivel
        for (int i = 0; i < nivel; i++) {
            System.out.print("   ");
        }

        // Muestra las claves del nodo actual
        System.out.print("[ ");

        for (int i = 0; i < nodo.numeroClaves; i++) {
            System.out.print(nodo.claves[i] + " ");
        }

        System.out.println("]");

        // Si no es hoja, recorre los hijos
        if (!nodo.esHoja) {
            for (int i = 0; i <= nodo.numeroClaves; i++) {
                if (nodo.hijos[i] != null) {
                    mostrarEstructura(nodo.hijos[i], nivel + 1);
                }
            }
        }
    }
    
        // Inserta una nueva clave en el árbol
    public void insertar(T clave) {

        NodoB raizActual = raiz;

        // Si la raíz está llena, debe dividirse
        if (estaLleno(raizActual)) {

            // Se crea una nueva raíz
            NodoB nuevaRaiz = new NodoB(false);

            // La raíz anterior pasa a ser hijo
            nuevaRaiz.hijos[0] = raizActual;

            // Se divide la raíz anterior
            dividirHijo(nuevaRaiz, 0, raizActual);

            // Se actualiza la raíz del árbol
            raiz = nuevaRaiz;
        }

        // Se inserta en un nodo que no esté lleno
        insertarNoLleno(raiz, clave);
    }
    
        // Verifica si un nodo alcanzó su máximo de claves
    private boolean estaLleno(NodoB nodo) {
        return nodo.numeroClaves == (2 * GRADO - 1);
    }
    
        // Inserta una clave en un nodo que no está lleno
    private void insertarNoLleno(NodoB nodo, T clave) {

        int i = nodo.numeroClaves - 1;

        // Si el nodo es hoja, se inserta directamente
        if (nodo.esHoja) {

            // Desplaza las claves mayores hacia la derecha
            while (i >= 0 && clave.compareTo(nodo.claves[i]) < 0) {
                nodo.claves[i + 1] = nodo.claves[i];
                i--;
            }

            // Inserta la nueva clave en la posición correcta
            nodo.claves[i + 1] = clave;
            nodo.numeroClaves++;

        } else {

            // Busca el hijo correcto
            while (i >= 0 && clave.compareTo(nodo.claves[i]) < 0) {
                i--;
            }

            i++;

            // Si el hijo está lleno, primero debe dividirse
            if (estaLleno(nodo.hijos[i])) {

                dividirHijo(nodo, i, nodo.hijos[i]);

                // Determina si debe avanzar al siguiente hijo
                if (clave.compareTo(nodo.claves[i]) > 0) {
                    i++;
                }
            }

            // Continúa la inserción recursiva
            insertarNoLleno(nodo.hijos[i], clave);
        }
    }
    
        // Divide un hijo lleno en dos nodos
    private void dividirHijo(NodoB padre, int posicion, NodoB nodoLleno) {

        // Se crea el nuevo nodo para la mitad derecha
        NodoB nuevoNodo = new NodoB(nodoLleno.esHoja);

        // El nuevo nodo tendrá t - 1 claves
        nuevoNodo.numeroClaves = GRADO - 1;

        // Copia las últimas claves al nuevo nodo
        for (int j = 0; j < GRADO - 1; j++) {
            nuevoNodo.claves[j] = nodoLleno.claves[j + GRADO];
        }

        // Si no es hoja, también copia los hijos
        if (!nodoLleno.esHoja) {
            for (int j = 0; j < GRADO; j++) {
                nuevoNodo.hijos[j] = nodoLleno.hijos[j + GRADO];
            }
        }

        // Reduce las claves del nodo original
        nodoLleno.numeroClaves = GRADO - 1;

        // Desplaza los hijos del padre
        for (int j = padre.numeroClaves; j >= posicion + 1; j--) {
            padre.hijos[j + 1] = padre.hijos[j];
        }

        // Conecta el nuevo nodo al padre
        padre.hijos[posicion + 1] = nuevoNodo;

        // Desplaza las claves del padre
        for (int j = padre.numeroClaves - 1; j >= posicion; j--) {
            padre.claves[j + 1] = padre.claves[j];
        }

        // Promueve la clave central al padre
        padre.claves[posicion] = nodoLleno.claves[GRADO - 1];

        // Aumenta el número de claves del padre
        padre.numeroClaves++;
    }
}
