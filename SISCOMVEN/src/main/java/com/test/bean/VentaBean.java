/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.test.bean;

import com.test.clases.ProductoCompra;
import com.test.conexion.VariablesConexion;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;

/**
 *
 * @author Miriam Lopez Surco
 */
@Stateless
public class VentaBean {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    private Connection connection;
    private PreparedStatement consulta;

    public VentaBean() throws SQLException {
        try {

            System.out.println("Creando conexion.");
            Class.forName(VariablesConexion.DRIVER_BBDD);
            connection = DriverManager.getConnection(VariablesConexion.URL_BBDD, VariablesConexion.USUARIO_BBDD, VariablesConexion.PASSWORD_BBDD);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param listaProductos //el detalle de los productos añadidos para la
     * venta
     * @param nit //nit del cliente
     * @param nombre //nombre del cliente para generar la Factura
     * @param codUsuario //quien realizo la venta
     * @return
     */
    public Long registrarCompra(List<ProductoCompra> listaProductos, String nit, String nombre, Long codUsuario) {
        Long idFactura = 0L;
        if (listaProductos != null && listaProductos.size() > 0) {

            try {
                // empezando bloque transaccional
                connection.setAutoCommit(false);
                Long idCliente = 0L, idVenta = 0L;
                ResultSet resultado = null;
                // obteniendo secuencia para Cliente registrado anteriormente
                StringBuilder querySecuencia = new StringBuilder();
                querySecuencia.append("select nextval('sec_cli')");
                consulta = connection.prepareStatement(querySecuencia.toString());
                resultado = consulta.executeQuery();
                // obteniendo id para cliente
                if (resultado != null && resultado.next()) {
                    idCliente = resultado.getLong(1);
                    System.out.println("ID CLIENTE: " + idCliente);
                }
                // obteniendo secuencia para venta
                querySecuencia = new StringBuilder();
                querySecuencia.append("select nextval('sec_ven')");
                consulta = connection.prepareStatement(querySecuencia.toString());
                resultado = consulta.executeQuery();

                // obteniendo id para cliente
                if (resultado != null && resultado.next()) {
                    idVenta = resultado.getLong(1);
                    System.out.println("ID VENTA: " + idVenta);
                }
                //////////////////////////

                StringBuilder queryVenta = new StringBuilder();
                queryVenta.append("INSERT INTO venta(cod_ven, cod_cli, cod_usu, fecha) VALUES (?, ?, ?, ?)");

                StringBuilder queryDetalleProducto = new StringBuilder();
                queryDetalleProducto.append("INSERT INTO detalle( cod_ven, cod_pro, cantidad, cos_tot) VALUES (?, ?, ?, ?);");

                StringBuilder queryCliente = new StringBuilder();
                queryCliente.append("INSERT INTO cliente(cod_cli, nombre, nit) VALUES (?, ?, ?);");

                // insert clientes
                consulta = connection.prepareStatement(queryCliente.toString());

                consulta.setLong(1, idCliente);
                consulta.setString(2, nombre);
                consulta.setInt(3, Integer.parseInt(nit));
                int res = consulta.executeUpdate();
                System.out.println("Cliente Registrado: " + res);

                // INSERT VENTA
                consulta = connection.prepareStatement(queryVenta.toString());

                consulta.setLong(1, idVenta);
                consulta.setLong(2, idCliente);
                //codUsuario
                consulta.setLong(3, codUsuario);
                consulta.setDate(4, new java.sql.Date(new Date().getTime()));

                res = consulta.executeUpdate();
                System.out.println("venta Registrado: " + res);

                // INSERT detalle
                consulta = connection.prepareStatement(queryDetalleProducto.toString());

                for (ProductoCompra productoCompra : listaProductos) {
                    //( cod_det, cod_ven, cod_pro, cantidad, cos_tot)
                    //consulta.setLong(1, x);
                    consulta.setLong(1, idVenta);
                    consulta.setLong(2, Long.parseLong(productoCompra.getCodigoProducto()));
                    consulta.setInt(3, productoCompra.getCantidad());
                    consulta.setFloat(4, productoCompra.getPrecioFinal());

                    res = consulta.executeUpdate();
                    System.out.println("Insert detalle: " + res);
                }
                idFactura = idVenta;
                // comiteando transaccion
                connection.commit();

            } catch (SQLException ex) {
                Logger.getLogger(VentaBean.class.getName()).log(Level.SEVERE, null, ex);
                try {
                    // rollbackeando
                    connection.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(VentaBean.class.getName()).log(Level.SEVERE, null, ex1);
                }
            }

        }
        return idFactura;

    }

    @PreDestroy
    public void cerrarConexion() {
        System.out.println("Antes de cerrar conexion");
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

}
