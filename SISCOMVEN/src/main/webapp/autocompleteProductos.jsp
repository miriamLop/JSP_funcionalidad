<%-- 
    Document   : autocompleteProductos
    Created on : 17-08-2021, 05:52:05 PM
    Author     : Melissa Ibañez Lopez
--%>

<%@page import="com.test.bean.ProductoBean"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    System.out.println("Ingrese a listar");
    ProductoBean productoBean = (ProductoBean) session.getAttribute("productoBean");
    //se esta obteniendo el parametro enviado por autocomplete, el nombre del parametro q no debe ser cambiado
    String producto = request.getParameter("q");

    List<String> lista = productoBean.getProductos(producto);

    System.out.println("Lista: " + (lista == null ? "NULL" : lista.size()));

    if (lista != null && lista.size() > 0) {
        for (String nomProducto : lista) {
            out.println(nomProducto);
        }
    }
    System.out.println("Despues de listar");
%>
