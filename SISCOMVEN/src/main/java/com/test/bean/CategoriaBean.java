/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.test.bean;

import com.test.clases.Categoria;
import com.test.conexion.VariablesConexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.annotation.PreDestroy;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Miriam Lopez
 */
public class CategoriaBean {

    //atributos
    private Connection connection;
    private PreparedStatement insertCategoria;
    private VariablesConexion variable;
    //objeto que rescatara los datos para ser modificados
    private Categoria categoriaModificar;
    //objeto para poder modificar el registro
    private PreparedStatement updateCategoria;
    //objeto para poder eliminar un registro
    private PreparedStatement deleteCategoria;
    

    //constructores
    public CategoriaBean() throws SQLException {
        //instanciando
        variable = new VariablesConexion();
        variable.inicioConexion();
        //obteniendo la conexion
        connection = variable.getConnection();
        System.out.println("Iniciando la conexion");
    }

    //metodos
    @PreDestroy
    public void cerrarConexion() {
        variable.cerrarConexion();
    }

    public String registrarCategoria(HttpServletRequest request) {
        String mensaje = "";
        if (request == null) {
            return "";
        }
        if (connection != null) {
            try {
                //definiendo la consulta
                StringBuilder query = new StringBuilder();
                query.append(" insert into categoria ");
                query.append(" values (nextval('sec_cat'),?,?)");
                //enviando la consulta
                if (insertCategoria == null) {
                    insertCategoria = connection.prepareStatement(query.toString());
                }
                //rescatando los parametros del formulario jsp registrarCategoria
                String nombre = request.getParameter("nomCat");
                String descripcion = request.getParameter("desCat");
                //pasando los datos a los parametros de la consulta
                insertCategoria.setString(1, nombre);
                insertCategoria.setString(2, descripcion);
                //ejecutando la consulta
                int registro = insertCategoria.executeUpdate();
                if (registro == 1) {
                    mensaje = "Registro realizado con exito";
                } else {
                    mensaje = "Error al insertar el registro";
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return mensaje;
    }

    //Realizando el listado de todas las categorias que se tienen registrados
    public String listarCategoria() {
        StringBuilder salidaTabla = new StringBuilder();
        StringBuilder query = new StringBuilder();
        query.append(" select c.cod_cat,c.nom_cat,c.des_cat ");
        query.append(" from categoria c ");
        try {
            PreparedStatement pst = connection.prepareStatement(query.toString());
            ResultSet resultado = pst.executeQuery();
            while (resultado.next()) {
                salidaTabla.append("<tr>");
                salidaTabla.append("<td>");
                salidaTabla.append(resultado.getInt(1));
                salidaTabla.append("</td>");
                salidaTabla.append("<td>");
                salidaTabla.append(resultado.getString(2));
                salidaTabla.append("</td>");
                salidaTabla.append("<td>");
                salidaTabla.append(resultado.getString(3));
                salidaTabla.append("</td>");
                //adicionando enlace para modificar ese registro
                salidaTabla.append("<td>");
                salidaTabla.append("<a href=modificarCategoria.jsp?cod=").append(resultado.getInt(1)).append(">Modificar</a>");
                salidaTabla.append("</td>");
                salidaTabla.append("<td>");
                salidaTabla.append("<a href='listaCategoria.jsp?cod=").append(resultado.getInt(1)).append("' onclick='return confirmarEliminacion();'>Eliminar</a>");
                salidaTabla.append("</td>");

                salidaTabla.append("</tr>");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error de conexion");
        }
        return salidaTabla.toString();
    }

    //metodo que lista las categorias en un select
    public String listarCategoriaSelect() {
        StringBuilder salidaTabla = new StringBuilder();
        StringBuilder query = new StringBuilder();
        query.append(" select c.cod_cat,c.nom_cat ");
        query.append(" from categoria c ");
        try {
            PreparedStatement pst = connection.prepareStatement(query.toString());
            ResultSet resultado = pst.executeQuery();
            while (resultado.next()) {
                salidaTabla.append("<option value='");
                salidaTabla.append(resultado.getInt(1));
                salidaTabla.append("'>");
                salidaTabla.append(resultado.getString(2));
                salidaTabla.append("</option>");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return salidaTabla.toString();
    }
    // metodo que permite buscar una categoria por su codigo o llave primaria

    public void buscarCategoria(String codCategoria) {
        categoriaModificar = new Categoria();
        //StringBuilder salidaTabla = new StringBuilder();
        StringBuilder query = new StringBuilder();
        query.append(" select cod_cat, nom_cat, des_cat ");
        query.append(" from categoria ");
        query.append(" where cod_cat =? ");
        try {
            PreparedStatement pst = connection.prepareStatement(query.toString());
            pst.setInt(1, Integer.parseInt(codCategoria));
            ResultSet resultado = pst.executeQuery();
            //utilizamos una condicion porque la busqueda nos devuelve 1 registro
            if (resultado.next()) {
                //cargando la informacion a nuestro objeto categoriaModificar de tipo Categoria
                categoriaModificar.setCodCategoria(resultado.getInt(1));
                categoriaModificar.setNomCategoria(resultado.getString(2));
                categoriaModificar.setDesCategoria(resultado.getString(3));
            }

        } catch (SQLException e) {
            System.out.println("Error de conexion");
            e.printStackTrace();
        }
    }

    public String modificarCategoria(HttpServletRequest request, String codCategoria) {
        String salida = "";
        if (request == null) {
            return "";
        }
        if (connection != null) {
            try {
                StringBuilder query = new StringBuilder();
                query.append(" update categoria ");
                query.append(" set nom_cat=?, des_cat=? ");
                query.append(" where cod_cat=? ");
                if (updateCategoria == null) {
                    updateCategoria = connection.prepareStatement(query.toString());
                }
                //rescatando los datos que fueron modificados por el usuario
                String nombre = request.getParameter("nom_cat");
                String des = request.getParameter("des_cat");
                //actualizando los atributos en el objeto categoriamodificar
                categoriaModificar.setNomCategoria(nombre);
                categoriaModificar.setDesCategoria(des);
                //pasando los parametros a la consulta
                updateCategoria.setString(1, nombre);
                updateCategoria.setString(2, des);
                updateCategoria.setInt(3, Integer.parseInt(codCategoria == null ? "0" : codCategoria));
                int registros = updateCategoria.executeUpdate();
                if (registros == 1) {
                    salida = "Modificacion correcta";
                } else {
                    salida = " Error al ejecutar el update";
                }
            } catch (SQLException e) {
                System.out.println("Error al ejecutar el update");
                e.printStackTrace();
            }
        }
        return salida;
    }
    //metodo que permite eliminar un registro de la tabla categoria
    public String eliminarCategoria(HttpServletRequest request,String codCategoria){
        String salida="";
        if(request==null){
            return "";
        }
        if(connection!=null && codCategoria!=null && codCategoria.length()>0){
            try {
                StringBuilder query=new StringBuilder();
                query.append(" delete from categoria");
                query.append(" where cod_cat=? ");
                deleteCategoria=connection.prepareStatement(query.toString());
                //pasando el parametro
                deleteCategoria.setInt(1, Integer.parseInt(codCategoria));
                //ejecutando la consulta
                int nroRegistros=deleteCategoria.executeUpdate();
                if(nroRegistros==1){
                    salida="Registro eliminado de forma correcta";
                }else{
                    salida="Existio un error al tratar de eliminar el registro";
                }
            } catch (SQLException e) {
                System.out.println("Error en el proceso");
                e.printStackTrace();
            }
        }
        return salida;
    }
    
    //getter y setter

    public Categoria getCategoriaModificar() {
        return categoriaModificar;
    }

    public void setCategoriaModificar(Categoria categoriaModificar) {
        this.categoriaModificar = categoriaModificar;
    }

}
