package com.mycompany.supermercadoedd.modelos;
/**
 *
 * @author ronald
 */
public class Producto {
    private String nombre;
    private String codigoBarras;
    private String categoria;
    private String fechaCaducidad;
    private String marca;
    private double precio;
    private int stock;

    // Constructor de la clase Producto.
    public Producto(String nombre, String codigoBarras, String categoria,
                    String fechaCaducidad, String marca,
                    double precio, int stock) {
        this.nombre = nombre;
        this.codigoBarras = codigoBarras;
        this.categoria = categoria;
        this.fechaCaducidad = fechaCaducidad;
        this.marca = marca;
        this.precio = precio;
        this.stock = stock;
    }

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getFechaCaducidad() {
        return fechaCaducidad;
    }

    public void setFechaCaducidad(String fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "nombre='" + nombre + '\'' +
                ", codigoBarras='" + codigoBarras + '\'' +
                ", categoria='" + categoria + '\'' +
                ", fechaCaducidad='" + fechaCaducidad + '\'' +
                ", marca='" + marca + '\'' +
                ", precio=" + precio +
                ", stock=" + stock +
                '}';
    }
}
