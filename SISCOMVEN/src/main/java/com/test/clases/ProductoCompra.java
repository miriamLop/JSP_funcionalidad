/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.test.clases;

import java.io.Serializable;

/**
 *
 * @author Miriam Lopez
 */
public class ProductoCompra implements Serializable {

    //atributos de producto
    private String codigoProducto;
    private String nombreProducto;
    //este atributo no es relevante para la accion de venta, ya que no se mostrara el contenido
    private String descripcionProducto;

    private float precio;
    private int stock;
    //atributos para determinar el costo final
    private int cantidad;
    private float precioFinal = 0;

    public ProductoCompra() {
    }

    public ProductoCompra(String codigoProducto, String nombreProducto, String descripcionProducto) {
        this.codigoProducto = codigoProducto;
        this.nombreProducto = nombreProducto;
        this.descripcionProducto = descripcionProducto;
    }

    //getter y setter

    public String getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(String codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getDescripcionProducto() {
        return descripcionProducto;
    }

    public void setDescripcionProducto(String descripcionProducto) {
        this.descripcionProducto = descripcionProducto;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public float getPrecioFinal() {
        return precioFinal;
    }

    public void setPrecioFinal(float precioFinal) {
        this.precioFinal = precioFinal;
    }
    
}
