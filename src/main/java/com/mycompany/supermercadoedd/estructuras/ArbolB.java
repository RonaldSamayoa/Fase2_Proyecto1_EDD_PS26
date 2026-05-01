package com.mycompany.supermercadoedd.estructuras;

/**
 *
 * @author ronald
 */
// Implementa la estructura base de un Árbol B genérico
public class ArbolB<T extends Comparable<T>> {
    // Define el grado fijo del Árbol B
    // Se utiliza grado 3 por facilidad y mejor visualización
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
    
    // Elimina una clave del árbol
    public void eliminar(T clave) {
        // Si el árbol está vacío, no hay nada que eliminar
        if (estaVacio()) {
            return;
        }

        eliminar(raiz, clave);

        // Si la raíz queda vacía y no es hoja,
        // se reemplaza por su primer hijo
        if (raiz.numeroClaves == 0 && !raiz.esHoja) {
            raiz = raiz.hijos[0];
        }
    }
    
    // Realiza la eliminación recursiva
    private void eliminar(NodoB nodo, T clave) {
        int indice = encontrarIndice(nodo, clave);

        // Si la clave existe en este nodo
        if (indice < nodo.numeroClaves &&
            nodo.claves[indice].compareTo(clave) == 0) {

            // Si es hoja, se elimina directamente
            if (nodo.esHoja) {
                eliminarDeHoja(nodo, indice);
            } else {
                 eliminarDeInterno(nodo, indice);
            }

        } else {
            // Si es hoja y no existe, termina
            if (nodo.esHoja) {
                return;
            }

            // Continúa la búsqueda en el hijo correcto
            if (nodo.hijos[indice].numeroClaves < GRADO) {
                llenar(nodo, indice);
            }

            eliminar(nodo.hijos[indice], clave);
        }
    }
    
    // Encuentra la posición de una clave dentro del nodo
    private int encontrarIndice(NodoB nodo, T clave) {
        int indice = 0;

        while (indice < nodo.numeroClaves &&
               nodo.claves[indice].compareTo(clave) < 0) {
            indice++;
        }
        return indice;
    }
    
    // Elimina una clave desde un nodo hoja
    private void eliminarDeHoja(NodoB nodo, int indice) {
        // Desplaza las claves hacia la izquierda
        for (int i = indice + 1; i < nodo.numeroClaves; i++) {
            nodo.claves[i - 1] = nodo.claves[i];
        }

        // Reduce la cantidad de claves
        nodo.numeroClaves--;
    }
    
    // Elimina una clave ubicada en un nodo interno
    private void eliminarDeInterno(NodoB nodo, int indice) {
        T clave = nodo.claves[indice];

        // Si el hijo izquierdo tiene suficientes claves
        if (nodo.hijos[indice].numeroClaves >= GRADO) {
            T predecesor = obtenerPredecesor(nodo, indice);
            nodo.claves[indice] = predecesor;
            eliminar(nodo.hijos[indice], predecesor);

        }
        // Si el hijo derecho tiene suficientes claves
        else if (nodo.hijos[indice + 1].numeroClaves >= GRADO) {
            T sucesor = obtenerSucesor(nodo, indice);
            nodo.claves[indice] = sucesor;
            eliminar(nodo.hijos[indice + 1], sucesor);

        }
        // Si ambos hijos tienen pocas claves
        else {
            fusionar(nodo, indice);
            eliminar(nodo.hijos[indice], clave);
        }
    }
    
    // Obtiene el predecesor de una clave
    private T obtenerPredecesor(NodoB nodo, int indice) {
        NodoB actual = nodo.hijos[indice];

        // Baja hasta el nodo más a la derecha
        while (!actual.esHoja) {
            actual = actual.hijos[actual.numeroClaves];
        }
        return actual.claves[actual.numeroClaves - 1];
    }
    
    // Obtiene el sucesor de una clave
    private T obtenerSucesor(NodoB nodo, int indice) {
        NodoB actual = nodo.hijos[indice + 1];

        // Baja hasta el nodo más a la izquierda
        while (!actual.esHoja) {
            actual = actual.hijos[0];
        }
        return actual.claves[0];
    }
    
    // Fusiona dos hijos alrededor de una clave del padre
    private void fusionar(NodoB nodo, int indice) {
        NodoB hijo = nodo.hijos[indice];
        NodoB hermano = nodo.hijos[indice + 1];

        // Baja la clave del padre al hijo izquierdo
        hijo.claves[GRADO - 1] = nodo.claves[indice];

        // Copia las claves del hermano
        for (int i = 0; i < hermano.numeroClaves; i++) {
            hijo.claves[i + GRADO] = hermano.claves[i];
        }

        // Si no son hojas, copia también los hijos
        if (!hijo.esHoja) {
            for (int i = 0; i <= hermano.numeroClaves; i++) {
                hijo.hijos[i + GRADO] = hermano.hijos[i];
            }
        }

        // Desplaza claves del padre
        for (int i = indice + 1; i < nodo.numeroClaves; i++) {
            nodo.claves[i - 1] = nodo.claves[i];
        }

        // Desplaza hijos del padre
        for (int i = indice + 2; i <= nodo.numeroClaves; i++) {
            nodo.hijos[i - 1] = nodo.hijos[i];
        }

        // Actualiza contadores
        hijo.numeroClaves += hermano.numeroClaves + 1;
        nodo.numeroClaves--;
    }
    
        // Garantiza que el hijo tenga suficientes claves antes de descender
    private void llenar(NodoB nodo, int indice) {
        // Intenta préstamo desde el hermano izquierdo
        if (indice != 0 &&
            nodo.hijos[indice - 1].numeroClaves >= GRADO) {

            prestarDeAnterior(nodo, indice);
        }
        // Intenta préstamo desde el hermano derecho
        else if (indice != nodo.numeroClaves &&
                 nodo.hijos[indice + 1].numeroClaves >= GRADO) {

            prestarDeSiguiente(nodo, indice);
        }
        // Si no puede prestar, fusiona
        else {
            if (indice != nodo.numeroClaves) {
                fusionar(nodo, indice);
            } else {
                fusionar(nodo, indice - 1);
            }
        }
    }
    
    // Toma una clave prestada del hermano izquierdo
    private void prestarDeAnterior(NodoB nodo, int indice) {
        NodoB hijo = nodo.hijos[indice];
        NodoB hermano = nodo.hijos[indice - 1];

        // Desplaza claves del hijo hacia la derecha
        for (int i = hijo.numeroClaves - 1; i >= 0; i--) {
            hijo.claves[i + 1] = hijo.claves[i];
        }

        // Si no es hoja, desplaza también los hijos
        if (!hijo.esHoja) {
            for (int i = hijo.numeroClaves; i >= 0; i--) {
                hijo.hijos[i + 1] = hijo.hijos[i];
            }
        }

        // Baja la clave del padre al hijo
        hijo.claves[0] = nodo.claves[indice - 1];

        // Si no es hoja, mueve también el último hijo del hermano
        if (!hijo.esHoja) {
            hijo.hijos[0] = hermano.hijos[hermano.numeroClaves];
        }

        // Sube la última clave del hermano al padre
        nodo.claves[indice - 1] = hermano.claves[hermano.numeroClaves - 1];

        hijo.numeroClaves++;
        hermano.numeroClaves--;
    }
    
    // Toma una clave prestada del hermano derecho
    private void prestarDeSiguiente(NodoB nodo, int indice) {
        NodoB hijo = nodo.hijos[indice];
        NodoB hermano = nodo.hijos[indice + 1];

        // Baja la clave del padre al final del hijo
        hijo.claves[hijo.numeroClaves] = nodo.claves[indice];

        // Si no es hoja, mueve también el primer hijo del hermano
        if (!hijo.esHoja) {
            hijo.hijos[hijo.numeroClaves + 1] = hermano.hijos[0];
        }

        // Sube la primera clave del hermano al padre
        nodo.claves[indice] = hermano.claves[0];

        // Desplaza claves del hermano hacia la izquierda
        for (int i = 1; i < hermano.numeroClaves; i++) {
            hermano.claves[i - 1] = hermano.claves[i];
        }

        // Si no es hoja, desplaza también los hijos
        if (!hermano.esHoja) {
            for (int i = 1; i <= hermano.numeroClaves; i++) {
                hermano.hijos[i - 1] = hermano.hijos[i];
            }
        }

        hijo.numeroClaves++;
        hermano.numeroClaves--;
    }
    
    public ListaEnlazada<T> buscarPorRango(T inicio, T fin) {
        // Lista donde se guardarán los resultados encontrados
        ListaEnlazada<T> resultados = new ListaEnlazada<>();

        // Se inicia el recorrido recursivo desde la raíz
        buscarPorRango( raiz, inicio, fin, resultados );
        return resultados;
    }
    
    // Recorre el árbol y agrega todas las claves que estén dentro del rango solicitado
    private void buscarPorRango(NodoB nodo, T inicio, T fin, ListaEnlazada<T> resultados) {
        // Si el nodo no existe, termina
        if (nodo == null) {
            return;
        }

        int i;

        // Recorre todas las claves del nodo actual
        for (i = 0; i < nodo.numeroClaves; i++) {
            // Primero recorre el hijo izquierdo
            if (!nodo.esHoja) {
                buscarPorRango(nodo.hijos[i],inicio, fin,resultados);
            }

            T actual = nodo.claves[i];

            // Verifica si la clave está dentro del rango
            if (actual.compareTo(inicio) >= 0 && actual.compareTo(fin) <= 0) {
                resultados.insertarAlFinal(actual);
            }
        }

        // Finalmente recorre el último hijo
        if (!nodo.esHoja) {
            buscarPorRango(nodo.hijos[i],inicio, fin,resultados);
        }
    }
}
