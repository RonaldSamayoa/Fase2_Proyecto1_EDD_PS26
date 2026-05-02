package com.mycompany.supermercadoedd.sistema;
import com.mycompany.supermercadoedd.estructuras.ListaEnlazada;
import com.mycompany.supermercadoedd.modelos.Producto;
/**
 *
 * @author ronald
 */
public class ComparadorOrdenamientos {
    public String compararOrdenamientos(Sucursal sucursal) {
        if (sucursal == null) {
            return "La sucursal no existe.";
        }

        long inicio;
        long fin;

        long tiempoNombre;
        long tiempoFecha;
        long tiempoCategoria;

        ListaEnlazada<Producto> resultado;

        //AVL → Orden por nombre
        inicio = System.nanoTime();

        resultado = sucursal.obtenerProductosOrdenadosPorNombre();

        fin = System.nanoTime();

        tiempoNombre = fin - inicio;

        //Árbol B → Orden por fecha
        inicio = System.nanoTime();

        resultado = sucursal.obtenerProductosOrdenadosPorFecha();

        fin = System.nanoTime();

        tiempoFecha = fin - inicio;

        // Árbol B+ → Orden por categoría
        inicio = System.nanoTime();
        resultado = sucursal.obtenerProductosOrdenadosPorCategoria();

        fin = System.nanoTime();

        tiempoCategoria = fin - inicio;

        // Construcción del reporte final
        String reporte = "";

        reporte += "\n===== COMPARACIÓN DE ORDENAMIENTOS =====\n";
        reporte += "Orden por Nombre (AVL): "
                + tiempoNombre + " ns\n";
        reporte += "Orden por Fecha (Árbol B): "
                + tiempoFecha + " ns\n";
        reporte += "Orden por Categoría (Árbol B+): "
                + tiempoCategoria + " ns\n";
        reporte += "========================================\n";

        return reporte;
    }
}
