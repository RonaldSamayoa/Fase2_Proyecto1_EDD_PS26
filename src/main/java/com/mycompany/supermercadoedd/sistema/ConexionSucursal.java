package com.mycompany.supermercadoedd.sistema;

/**
 *
 * @author ronald
 */
public class ConexionSucursal {
    // Sucursal destino de la conexión
    private Sucursal destino;

    // Peso de la conexión (tiempo/costo de traslado)
    private int peso;

    // Inicializa una nueva conexión
    public ConexionSucursal(Sucursal destino, int peso) {
        this.destino = destino;
        this.peso = peso;
    }

    // Retorna la sucursal destino
    public Sucursal getDestino() {
        return destino;
    }

    // Retorna el peso de la conexión
    public int getPeso() {
        return peso;
    }

    // Permite modificar el peso
    public void setPeso(int peso) {
        this.peso = peso;
    }

    // Representación en texto
    @Override
    public String toString() {
        return "ConexionSucursal{" +
                "destino=" + destino.getNombre() +
                ", peso=" + peso +
                '}';
    }
}
