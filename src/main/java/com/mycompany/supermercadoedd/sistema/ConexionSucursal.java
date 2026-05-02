package com.mycompany.supermercadoedd.sistema;

/**
 *
 * @author ronald
 */
public class ConexionSucursal {
     // sucursal destino
    private Sucursal destino;

    // tiempo de traslado
    private int tiempo;

    // costo de traslado
    private int costo;

    public ConexionSucursal( Sucursal destino,int tiempo,int costo) {
        this.destino = destino;
        this.tiempo = tiempo;
        this.costo = costo;
    }

    public Sucursal getDestino() {
        return destino;
    }

    public int getTiempo() {
        return tiempo;
    }

    public int getCosto() {
        return costo;
    }

    public void setTiempo(int tiempo) {
        this.tiempo = tiempo;
    }

    public void setCosto(int costo) {
        this.costo = costo;
    }

    @Override
    public String toString() {
        return "ConexionSucursal{" +
                "destino=" + destino.getNombre() +
                ", tiempo=" + tiempo +
                ", costo=" + costo +
                '}';
    }
}
