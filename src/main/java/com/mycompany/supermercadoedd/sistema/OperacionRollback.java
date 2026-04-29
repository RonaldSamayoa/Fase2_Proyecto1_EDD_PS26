package com.mycompany.supermercadoedd.sistema;
import com.mycompany.supermercadoedd.modelos.Producto;
/**
 *
 * @author ronald
 */
// Representa una operación realizada que puede revertirse
public class OperacionRollback {
    private int idSucursal;
    private Producto producto;
    private String estructura;

    // Inicializa una operación de rollback
    public OperacionRollback(int idSucursal,
                             Producto producto,
                             String estructura) {

        this.idSucursal = idSucursal;
        this.producto = producto;
        this.estructura = estructura;
    }

    // Retorna el id de la sucursal afectada
    public int getIdSucursal() {
        return idSucursal;
    }

    // Retorna el producto involucrado
    public Producto getProducto() {
        return producto;
    }

    // Retorna la estructura afectada
    public String getEstructura() {
        return estructura;
    }

    // Muestra la información de la operación
    @Override
    public String toString() {
        return "OperacionRollback{" +
                "idSucursal=" + idSucursal +
                ", producto=" + producto +
                ", estructura='" + estructura + '\'' +
                '}';
    }
}
