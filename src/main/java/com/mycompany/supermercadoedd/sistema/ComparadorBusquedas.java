package com.mycompany.supermercadoedd.sistema;
import com.mycompany.supermercadoedd.modelos.Producto;
/**
 *
 * @author ronald
 */
public class ComparadorBusquedas {
    public String compararBusquedaPorNombre( Sucursal sucursal,  String nombreProducto) {

        if (sucursal == null) {
            return "La sucursal no existe.";
        }

        long inicio;
        long fin;

        long tiempoLista;
        long tiempoAVL;

        Producto encontradoLista;
        Producto encontradoAVL;

        // Búsqueda secuencial
        inicio = System.nanoTime();

        encontradoLista = sucursal.buscarProductoSecuencialPorNombre(nombreProducto);

        fin = System.nanoTime();
        tiempoLista = fin - inicio;

        // Búsqueda AVL
        inicio = System.nanoTime();

        encontradoAVL = sucursal.buscarProductoPorNombre(nombreProducto);

        fin = System.nanoTime();
        tiempoAVL = fin - inicio;

        String reporte = "";

        reporte += "\n=== COMPARACIÓN POR NOMBRE ===\n";

        reporte += "Lista Enlazada: "
                + tiempoLista + " ns\n";

        reporte += "AVL: "
                + tiempoAVL + " ns\n";

        reporte += "Producto encontrado: "
                + (encontradoAVL != null
                ? encontradoAVL.toString()
                : "No encontrado")
                + "\n";

        return reporte;
    }

    //Compara búsqueda por código:  Lista enlazada vs Hash
    public String compararBusquedaPorCodigo(Sucursal sucursal, String codigoBarras) {

        if (sucursal == null) {
            return "La sucursal no existe.";
        }

        long inicio;
        long fin;

        long tiempoLista;
        long tiempoHash;

        Producto encontradoLista;
        Producto encontradoHash;

        // Búsqueda secuencial
        inicio = System.nanoTime();

        encontradoLista = sucursal.buscarProductoSecuencialPorCodigo(codigoBarras);

        fin = System.nanoTime();
        tiempoLista = fin - inicio;

        // Búsqueda Hash
        inicio = System.nanoTime();

        encontradoHash = sucursal.buscarProductoPorCodigo(codigoBarras);

        fin = System.nanoTime();
        tiempoHash = fin - inicio;

        String reporte = "";

        reporte += "\n=== COMPARACIÓN POR CÓDIGO ===\n";

        reporte += "Lista Enlazada: "
                + tiempoLista + " ns\n";

        reporte += "Hash: "
                + tiempoHash + " ns\n";

        reporte += "Producto encontrado: "
                + (encontradoHash != null
                ? encontradoHash.toString()
                : "No encontrado")
                + "\n";

        return reporte;
    }
}
