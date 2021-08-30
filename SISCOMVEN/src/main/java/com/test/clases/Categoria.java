/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.test.clases;

import java.io.Serializable;

/**
 * POJO de la tabla categoria para modificar los campos de un determinado registro
 * @author Miriam lopez
 */
public class Categoria implements Serializable{
    //atributos
    private int codCategoria;
    private String nomCategoria;
    private String desCategoria;
    //constructor
    public Categoria() {
    }
    //getter y setter

    public int getCodCategoria() {
        return codCategoria;
    }

    public void setCodCategoria(int codCategoria) {
        this.codCategoria = codCategoria;
    }

    public String getNomCategoria() {
        return nomCategoria;
    }

    public void setNomCategoria(String nomCategoria) {
        this.nomCategoria = nomCategoria;
    }

    public String getDesCategoria() {
        return desCategoria;
    }

    public void setDesCategoria(String desCategoria) {
        this.desCategoria = desCategoria;
    }

    
    
}
