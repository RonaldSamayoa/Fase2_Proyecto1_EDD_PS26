package com.mycompany.supermercadoedd.modelos;
/**
 *
 * @author ronald
 */
// Representa un producto dentro del sistema
public class Producto implements Comparable<Producto> {

    private String nombre;
    private String codigoBarras;
    private String categoria;
    private String fechaCaducidad;
    private String marca;
    private double precio;
    private int stock;

    // Inicializa un nuevo producto con todos sus datos
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

    public String getNombre() {
        return nombre;
    }

    // Modifica el nombre del producto
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    // Modifica el código de barras
    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public String getCategoria() {
        return categoria;
    }

    // Modifica la categoría
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getFechaCaducidad() {
        return fechaCaducidad;
    }

    // Modifica la fecha de caducidad
    public void setFechaCaducidad(String fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    public String getMarca() {
        return marca;
    }

    // Modifica la marca
    public void setMarca(String marca) {
        this.marca = marca;
    }

    public double getPrecio() {
        return precio;
    }

    // Modifica el precio
    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    // Modifica el stock
    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public int compareTo(Producto otro) {
        return this.nombre.compareToIgnoreCase(otro.nombre);
    }

    // Convierte el objeto a texto para mostrar información
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