<%-- 
    Document   : buscarProductoCategoria
    Created on : 08-06-2021, 09:06:38 PM
    Author     : Melissa Ibañez Lopez
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <%! String salidaTabla = "";%>
    </head>
    <body>
        <h1>Hello World!</h1>

        <jsp:useBean id="categoriaBean" scope="session" class="com.test.bean.CategoriaBean"/>
        <jsp:useBean id="productoBean" scope="session" class="com.test.bean.ProductoBean"/>
        <%
            if (request.getParameter("buscar") != null) {
                String codCat = request.getParameter("codCategoria");
                //llamando al metodo de busqueda de productos de una determinada categoria
                salidaTabla = productoBean.listarProductoCategoria(codCat);
            }
        %>
        <form method="POST">
            <table border="1">
                <thead>
                    <tr>
                        <th colspan="2">CATEGORIA</th>

                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td> <select name="codCategoria">
                                <%=categoriaBean.listarCategoriaSelect()%>
                            </select>
                        </td>
                        <td>
                            <input type="submit" value="BUSCAR" name="buscar" /> 
                        </td>

                    </tr>                    
                </tbody>                
            </table>
            <table border="1">
                <thead>
                    <tr>
                        <th>PRODUCTO</th>
                        <th>DESCRIPCION</th>
                        <th>COSTO UNIT. COMPRA</th>
                        <th>COSTO UNIT. VENTA</th>
                        <th>STOCK</th>
                        <th>PROVEEDOR</th>
                    </tr>
                </thead>
                <tbody>
                    <%=salidaTabla%>
                </tbody>
            </table>


        </form>
    </body>
</html>
