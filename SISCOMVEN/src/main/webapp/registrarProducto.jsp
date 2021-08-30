<%-- 
    Document   : registrarProducto
    Created on : 10-08-2021, 08:26:27 PM
    Author     : Melissa Ibañez Lopez
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <jsp:useBean id="categoriaBean" scope="session" class="com.test.bean.CategoriaBean"/>
        <jsp:useBean  id="proveedorBean" scope="session" class="com.test.bean.ProveedorBean"/>
        <jsp:useBean id="productoBean" scope="session" class="com.test.bean.ProductoBean"/>
        <h1>REGISTRAR PORODUCTO</h1>
        <%
            if (request.getParameter("guardar") != null) {
                String salida = productoBean.registrarProducto(request);
                out.print(salida);
            }
        %>
        <form method="POST">
            <table border="1">
                <thead>
                    <tr>
                        <th colspan="2"> REGISTRO PROVEEDOR</th>

                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>NOMBRE PRODUCTO: </td>
                        <td><input type="text" name="nombreProd"  /></td>
                    </tr>
                    <tr>
                        <td>DESCRIPCION DEL PRODUCTO</td>
                        <td><textarea name="descripcionProd" rows="4" cols="20">
                            </textarea></td>
                    </tr>
                    <tr>
                        <td>COSTO UNIT. COMPRA</td>
                        <td><input type="text" name="costoUC"  /></td>
                    </tr>
                    <tr>
                        <td>COSTO UNIT. VENTA: </td>
                        <td><input type="text" name="costoUV" value="" /></td>
                    </tr>
                    <tr>
                        <td>STOCK</td>
                        <td><input type="text" name="stock"  /></td>
                    </tr>
                    <tr>
                        <td>PROVEEDOR: </td>
                        <td>
                            <select name="codP">
                                <%=proveedorBean.listarProveedorSelect()%>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td>CATEGORIA:</td>
                        <td><select name="codC">
                                <%=categoriaBean.listarCategoriaSelect()%>
                            </select></td>
                    </tr>
                    <tr>
                        <td colspan="2"><input type="submit" value="Guardar" name="guardar" /> </td>

                    </tr>
                </tbody>
            </table>

        </form>
        <a href="index.jsp"> Menu Inicio</a>
    </body>
</html>
