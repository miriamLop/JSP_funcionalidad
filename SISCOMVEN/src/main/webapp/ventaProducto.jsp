<%-- 
    Document   : ventaProducto
    Created on : 17-08-2021, 05:29:34 PM
    Author     : Miriam Lopez
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="com.test.clases.ProductoCompra"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Venta de Producto</title>
        <link rel="stylesheet" type="text/css" href="resources/css/jquery.autocomplete.css" />
        <script src="resources/js/jsapi_google.js"></script> 
        <script>
            google.load("jquery", "1");

            var costoTotal = 0;
            //funcion para calcular el subtotal de cada producto y la suma total
            function modificar(codProducto) {

                var cantidad = document.getElementById('cant' + codProducto).value;
                var precioUnitario = document.getElementById('precioU' + codProducto).value;
                var stock = document.getElementById('stock' + codProducto).value;
                var precio = document.getElementById('pre' + codProducto).value;
                //mostrando por consola los datos
                console.log('Codigo: ' + codProducto);
                console.log('Cantidad: ' + cantidad);
                console.log('Precio Unitario: ' + precioUnitario);
                console.log('Stock: ' + stock);
                
                
                precio = precioUnitario * cantidad;
                console.log('SubTotal: '+precio)
                costoTotal = (parseFloat(costoTotal) + parseFloat(precio));
                console.log('Costo Total: ' + costoTotal);
                document.getElementById('pre' + codProducto).value = precio;
                document.getElementById('resultado').value = costoTotal;
                

            }
        </script> 
        <%!
            List<ProductoCompra> lista;
            ProductoCompra productoCompra;
        %>
    </head>
    <body>
        <jsp:useBean id="productoBean" scope="session" class="com.test.bean.ProductoBean"/>
        <jsp:useBean id="ventaBean" scope="session"class="com.test.bean.VentaBean"/>
        <%
            if (session.getAttribute("listaProductos") != null) {

                if (request.getParameter("agregar") != null) {
                    // recupero valor del input
                    String valor = request.getParameter("producto");
                    // separando codigo de descripcion
                    //out.print("valor:" + valor);
                    if (valor != null && valor.length() > 0) {
                        String codigo = valor.substring(0, valor.indexOf(" "));
                        String nombre = valor.substring(valor.indexOf(" "));

                        System.out.print("codigo : " + codigo);
                        System.out.print("producto: " + nombre);

                        // TODO: llamar a BBDD pára recuperar datos del formulario
                        productoCompra = productoBean.buscarProducto(codigo);

                        // recupernado objeto de session
                        lista = (List<ProductoCompra>) session.getAttribute("listaProductos");
                        // agregando objeto producto a la lista de producto recuperado de session                     
                        lista.add(productoCompra);
                        // actualizando objeto en sesion
                        session.setAttribute("listaProductos", lista);
                    }
                }

                // verificando si se digito el boton de elimnacion de un producto
                if (request.getParameter("codMod") != null) {

                    String codigoProducto = request.getParameter("codMod");
                    // recupernado objeto de session
                    List<ProductoCompra> lista = (List<ProductoCompra>) session.getAttribute("listaProductos");
                    List<ProductoCompra> listaTemporal = new ArrayList<ProductoCompra>();

                    for (ProductoCompra productoCompra : lista) {
                        if (!productoCompra.getCodigoProducto().equals(codigoProducto)) {
                            listaTemporal.add(productoCompra);
                        }
                    }
                    // actualizar lista en sesion
                    session.setAttribute("listaProductos", listaTemporal);
                }
                //verificando si se realizo clic en el boton registrarProducto
                if (request.getParameter("registrarProducto") != null) {

                    // recupernado objeto de session
                    List<ProductoCompra> lista = (List<ProductoCompra>) session.getAttribute("listaProductos");
                    String cantidad, precio;

                    for (ProductoCompra productoCompra : lista) {
                        //rescatando los datos de cantidad y precio calculado del producto
                        cantidad = request.getParameter("cant" + productoCompra.getCodigoProducto());
                        precio = request.getParameter("pre" + productoCompra.getCodigoProducto());
                        System.out.print("cantidad: " + cantidad);
                        System.out.print("precio: " + precio);
                        if (cantidad != null) {
                            try {
                                productoCompra.setCantidad(Integer.parseInt(cantidad));
                            } catch (NumberFormatException e) {
                                e.printStackTrace();
                            }
                        }
                        if (precio != null) {
                            try {
                                productoCompra.setPrecioFinal(Float.parseFloat(precio));
                            } catch (NumberFormatException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    // actualizando lista de la sesion
                    session.setAttribute("listaProductos", lista);
                }
               

            } else {
                //primera vez
                //instanciando la lista de tipo Productocompra
                //subiendo a session la lista de productos
                lista = new ArrayList<ProductoCompra>();
                session.setAttribute("listaProductos", lista);

            }

        %>
        <form method="post" action="#" >
            <table border="1">
                <thead>
                    <tr>
                        <th colspan="2" >VENTA DE PRODUCTOS  </th>

                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>Nombre Producto: </td>
                        <td><input type="text" name="producto" id="producto" /></td>
                    </tr>
                    <tr>

                        <td><input type="submit" value="Agregar" id="agregar" name="agregar" />   </td>
                        <td><input type="reset" value="Limpiar" /></td>


                    </tr>
                </tbody>
            </table>
            <table border="1">
                <thead>
                    <tr>
                        <td>Codigo Producto</td>
                        <td>Nombre Producto</td>
                        <td>Stock</td>
                        <td>Precio Unitario</td>
                        <td>Cantidad</td>
                        <td>Precio</td>
                        <td>Operacion</td>
                    </tr>
                </thead>
                <tbody>
                    <%                        // recupernado objeto de session
                        List<ProductoCompra> lista = (List<ProductoCompra>) session.getAttribute("listaProductos");
                        float sumaTotal = 0f;
                        if (lista != null) {
                            for (ProductoCompra productoCompra : lista) {
                                out.print("<tr>");
                                out.print("<td>");
                                out.print(productoCompra.getCodigoProducto());
                                out.print("</td>");
                                out.print("<td>");
                                out.print(productoCompra.getNombreProducto());
                                out.print("</td>");
                                out.print("<td>");
                                out.print("<input type='text' id='stock" + productoCompra.getCodigoProducto() + "' value='" + productoCompra.getStock() + "' readonly />");
                                out.print("</td>");
                                out.print("<td>");
                                out.print("<input type='text' id='precioU" + productoCompra.getCodigoProducto() + "' value='" + productoCompra.getPrecio() + "' readonly />");
                                out.print("</td>");
                                out.print("<td>");

                                out.print("<input type='text' autocomplete='off' id='cant"
                                        + productoCompra.getCodigoProducto()
                                        + "' onblur='modificar(" + productoCompra.getCodigoProducto() + ");' value = '"
                                        + productoCompra.getCantidad() + "' name='cant"
                                        + productoCompra.getCodigoProducto() + "' />");

                                out.print("</td>");
                                out.print("<td>");
                                out.print("<input type='text' id='pre" + productoCompra.getCodigoProducto() + "' readonly value='"
                                        + productoCompra.getPrecioFinal() + "' name='pre" + productoCompra.getCodigoProducto() + "' />");
                                out.print("</td>");
                                out.print("<td>");
                                out.print("<a href='ventaProducto.jsp?codMod=" + productoCompra.getCodigoProducto() + "'>Eliminar</a>");
                                out.print("</td>");

                                out.print("</tr>");
                                sumaTotal = sumaTotal + productoCompra.getPrecioFinal();
                            }
                        }
                    %>
                    <tr>
                        <td colspan="5" align='right'>Precio Total</td>
                        <td><input autocomplete="off" type="text" id="resultado" readonly value="<%=sumaTotal%>" /></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td colspan="7" align="center">
                            <input type="submit" value="Terminar Seleccion" id="registrarProducto" name="registrarProducto" />
                        </td>    
                    </tr>

                </tbody>
            </table>            
            
        </form>


        <script src="resources/js/jquery.autocomplete.js"></script>
        <script>
            $("#producto").autocomplete("autocompleteProductos.jsp");
        </script>
    </body>
</html>
