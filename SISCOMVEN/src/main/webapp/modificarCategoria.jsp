<%-- 
    Document   : modificarCategoria
    Created on : 28-07-2021, 07:24:40 PM
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
        <h1>MODIFICAR CATEGORIA</h1>
        <jsp:useBean id="categoria" scope="session" class="com.test.bean.CategoriaBean"/>
        <%
            // rescatar el parametro del codigo de categoria
            String codCategoria=request.getParameter("cod");
            //llamado al metodo de busqueda de categoria
            categoria.buscarCategoria(codCategoria);
            //verificando si se acciono el boton de modificar
            if(request.getParameter("modificar")!=null){
                String salida=categoria.modificarCategoria(request, codCategoria);
                out.print(salida);
            }
        %>
        <form method="POST">
            <table border="1">
                <thead>
                    <tr>
                        <th colspan="2"> DATOS DEL REGISTRO CATEGORIA</th>
                       
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td> NOMBRE:</td>
                        <td><input type="text" name="nom_cat" value="<%=categoria.getCategoriaModificar().getNomCategoria()%>" /></td>
                    </tr>
                    <tr>
                        <td>DESCRIPCION</td>
                        <td><input type="text" name="des_cat" value="<%=categoria.getCategoriaModificar().getDesCategoria()%>" /></td>
                    </tr>
                    <tr>
                        <td colspan="2"><input type="submit" value="Modificar" name="modificar" /> </td>
                       
                    </tr>
                </tbody>
            </table>

        </form>
                    <a href="index.jsp"> MENU INICIO</a>
    </body>
</html>
