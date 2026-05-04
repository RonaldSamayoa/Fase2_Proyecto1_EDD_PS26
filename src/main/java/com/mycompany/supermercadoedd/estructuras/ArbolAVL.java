package com.mycompany.supermercadoedd.estructuras;

/**
 *
 * @author ronald
 */
// Implementa un Árbol AVL genérico
public class ArbolAVL <T extends Comparable<T>> {
    // Define la estructura del nodo del árbol
    private class Nodo {
        T dato; // Almacena el valor del nodo
        Nodo izquierdo; // Referencia al hijo izquierdo
        Nodo derecho; // Referencia al hijo derecho
        int altura; // Almacena la altura del nodo

        // Inicializa el nodo con el dato proporcionado
        Nodo(T dato) {
            this.dato = dato;
            this.altura = 1; // Se establece la altura inicial en 1
        }
    }

    // Declara la raíz del árbol
    private Nodo raiz;

    // Inserta un nuevo elemento en el árbol
    public void insertar(T dato) {
        raiz = insertar(raiz, dato);
    }

    // Realiza la inserción recursiva en el árbol
    private Nodo insertar(Nodo nodo, T dato) {
        // Inserta el nodo si la posición actual está vacía
        if (nodo == null) {
            return new Nodo(dato);
        }

        // Compara el dato con el contenido del nodo actual
        if (dato.compareTo(nodo.dato) < 0) {
            nodo.izquierdo = insertar(nodo.izquierdo, dato);
        } else if (dato.compareTo(nodo.dato) > 0) {
            nodo.derecho = insertar(nodo.derecho, dato);
        } else {
            return nodo; // Evita duplicados
        }

        // Actualiza la altura del nodo
        nodo.altura = 1 + Math.max(altura(nodo.izquierdo), altura(nodo.derecho));

        // Calcula el factor de balanceo
        int balance = obtenerBalance(nodo);

        // Realiza rotación simple a la derecha (Caso Izquierda-Izquierda)
        if (balance > 1 && dato.compareTo(nodo.izquierdo.dato) < 0) {
            return rotarDerecha(nodo);
        }

        // Realiza rotación simple a la izquierda (Caso Derecha-Derecha)
        if (balance < -1 && dato.compareTo(nodo.derecho.dato) > 0) {
            return rotarIzquierda(nodo);
        }

        // Realiza rotación doble izquierda-derecha (Caso Izquierda-Derecha)
        if (balance > 1 && dato.compareTo(nodo.izquierdo.dato) > 0) {
            nodo.izquierdo = rotarIzquierda(nodo.izquierdo);
            return rotarDerecha(nodo);
        }

        // Realiza rotación doble derecha-izquierda (Caso Derecha-Izquierda)
        if (balance < -1 && dato.compareTo(nodo.derecho.dato) < 0) {
            nodo.derecho = rotarDerecha(nodo.derecho);
            return rotarIzquierda(nodo);
        }

        // Retorna el nodo sin modificaciones si está balanceado
        return nodo;
    }

    // Elimina un elemento del árbol
    public void eliminar(T dato) {
        raiz = eliminar(raiz, dato);
    }

    // Realiza la eliminación recursiva en el árbol
    private Nodo eliminar(Nodo nodo, T dato) {
        // Retorna el nodo si no se encuentra el dato
        if (nodo == null) {
            return nodo;
        }

        // Busca el nodo a eliminar
        if (dato.compareTo(nodo.dato) < 0) {
            nodo.izquierdo = eliminar(nodo.izquierdo, dato);
        } else if (dato.compareTo(nodo.dato) > 0) {
            nodo.derecho = eliminar(nodo.derecho, dato);
        } else {
            // Maneja el caso de un solo hijo o sin hijos
            if (nodo.izquierdo == null || nodo.derecho == null) {
                Nodo temp = (nodo.izquierdo != null) ? nodo.izquierdo : nodo.derecho;

                // Elimina el nodo sin hijos
                if (temp == null) {
                    nodo = null;
                } else {
                    nodo = temp;
                }
            } else {
                // Obtiene el sucesor inorden
                Nodo sucesor = obtenerMinimo(nodo.derecho);
                nodo.dato = sucesor.dato;
                nodo.derecho = eliminar(nodo.derecho, sucesor.dato);
            }
        }

        // Retorna si el árbol queda vacío
        if (nodo == null) {
            return nodo;
        }

        // Actualiza la altura del nodo
        nodo.altura = 1 + Math.max(altura(nodo.izquierdo), altura(nodo.derecho));

        // Calcula el factor de balanceo
        int balance = obtenerBalance(nodo);

        // Realiza rotación derecha
        if (balance > 1 && obtenerBalance(nodo.izquierdo) >= 0) {
            return rotarDerecha(nodo);
        }

        // Realiza rotación izquierda
        if (balance < -1 && obtenerBalance(nodo.derecho) <= 0) {
            return rotarIzquierda(nodo);
        }

        // Realiza rotación izquierda-derecha
        if (balance > 1 && obtenerBalance(nodo.izquierdo) < 0) {
            nodo.izquierdo = rotarIzquierda(nodo.izquierdo);
            return rotarDerecha(nodo);
        }

        // Realiza rotación derecha-izquierda
        if (balance < -1 && obtenerBalance(nodo.derecho) > 0) {
            nodo.derecho = rotarDerecha(nodo.derecho);
            return rotarIzquierda(nodo);
        }

        // Retorna el nodo balanceado
        return nodo;
    }

    // Obtiene la altura de un nodo
    private int altura(Nodo nodo) {
        return (nodo == null) ? 0 : nodo.altura;
    }

    // Calcula el factor de balanceo de un nodo
    private int obtenerBalance(Nodo nodo) {
        return (nodo == null) ? 0 : altura(nodo.izquierdo) - altura(nodo.derecho);
    }

    // Realiza una rotación simple hacia la derecha
    private Nodo rotarDerecha(Nodo y) {
        Nodo x = y.izquierdo;
        Nodo T2 = x.derecho;

        // Realiza la rotación
        x.derecho = y;
        y.izquierdo = T2;

        // Actualiza las alturas
        y.altura = 1 + Math.max(altura(y.izquierdo), altura(y.derecho));
        x.altura = 1 + Math.max(altura(x.izquierdo), altura(x.derecho));

        // Retorna la nueva raíz
        return x;
    }

    // Realiza una rotación simple hacia la izquierda
    private Nodo rotarIzquierda(Nodo x) {
        Nodo y = x.derecho;
        Nodo T2 = y.izquierdo;

        // Realiza la rotación
        y.izquierdo = x;
        x.derecho = T2;

        // Actualiza las alturas
        x.altura = 1 + Math.max(altura(x.izquierdo), altura(x.derecho));
        y.altura = 1 + Math.max(altura(y.izquierdo), altura(y.derecho));

        // Retorna la nueva raíz
        return y;
    }

    // Obtiene el nodo con el valor mínimo
    private Nodo obtenerMinimo(Nodo nodo) {
        Nodo actual = nodo;

        // Recorre el subárbol izquierdo hasta encontrar el mínimo
        while (actual.izquierdo != null) {
            actual = actual.izquierdo;
        }

        return actual;
    }

    // Verifica si el árbol contiene un elemento
    public boolean contiene(T dato) {
        return buscar(raiz, dato);
    }

    // Realiza la búsqueda recursiva en el árbol
    private boolean buscar(Nodo nodo, T dato) {
        if (nodo == null) {
            return false;
        }

        if (dato.compareTo(nodo.dato) < 0) {
            return buscar(nodo.izquierdo, dato);
        } else if (dato.compareTo(nodo.dato) > 0) {
            return buscar(nodo.derecho, dato);
        }

        return true;
    }

    // Realiza un recorrido en orden
    public void recorridoInOrden() {
        inOrden(raiz);
        System.out.println();
    }

    // Implementa el recorrido en orden de forma recursiva
    private void inOrden(Nodo nodo) {
        if (nodo != null) {
            inOrden(nodo.izquierdo);
            System.out.print(nodo.dato + " ");
            inOrden(nodo.derecho);
        }
    }

    // Realiza un recorrido en preorden
    public void recorridoPreOrden() {
        preOrden(raiz);
        System.out.println();
    }

    // Implementa el recorrido en preorden
    private void preOrden(Nodo nodo) {
        if (nodo != null) {
            System.out.print(nodo.dato + " ");
            preOrden(nodo.izquierdo);
            preOrden(nodo.derecho);
        }
    }

    // Realiza un recorrido en postorden
    public void recorridoPostOrden() {
        postOrden(raiz);
        System.out.println();
    }

    // Implementa el recorrido en postorden
    private void postOrden(Nodo nodo) {
        if (nodo != null) {
            postOrden(nodo.izquierdo);
            postOrden(nodo.derecho);
            System.out.print(nodo.dato + " ");
        }
    }

    // Obtiene la altura del árbol
    public int altura() {
        return altura(raiz);
    }

    // Verifica si el árbol está vacío
    public boolean estaVacio() {
        return raiz == null;
    }
    
    // Retorna todos los elementos en orden (alfabético en este caso)
    public ListaEnlazada<T> obtenerElementosEnOrden() {
        ListaEnlazada<T> lista = new ListaEnlazada<>();
        llenarInOrden(raiz, lista);
        return lista;
    }

    // Recorre el árbol en inorden y guarda los elementos en la lista
    private void llenarInOrden(Nodo nodo, ListaEnlazada<T> lista) {
        if (nodo != null) {
            llenarInOrden(nodo.izquierdo, lista);

            lista.insertarAlFinal(nodo.dato);

            llenarInOrden(nodo.derecho, lista);
        }
    }
    
    // Genera el DOT completo del árbol AVL
    public String generarDot() {
        StringBuilder sb = new StringBuilder();
        sb.append("digraph AVL {\n");

        if (raiz != null) {
            generarDotRecursivo(raiz, sb);
        }

        sb.append("}\n");
        return sb.toString();
    }

    // Recorre el árbol y genera conexiones
    private void generarDotRecursivo(Nodo nodo, StringBuilder sb) {
        if (nodo != null) {

            if (nodo.izquierdo != null) {
                sb.append("\"")
                  .append(nodo.dato)
                  .append("\" -> \"")
                  .append(nodo.izquierdo.dato)
                  .append("\";\n");
            }

            if (nodo.derecho != null) {
                sb.append("\"")
                  .append(nodo.dato)
                  .append("\" -> \"")
                  .append(nodo.derecho.dato)
                  .append("\";\n");
            }

            generarDotRecursivo(nodo.izquierdo, sb);
            generarDotRecursivo(nodo.derecho, sb);
        }
    }
}
