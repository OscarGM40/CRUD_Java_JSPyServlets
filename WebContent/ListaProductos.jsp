<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Vista del MVC</title>

<style>
table {
	float: left;
}

#insertar {
	padding-left: 10px;
	margin-left: 50px;
	margin-top: 10px;
}

.cabecera {
	font-weight: bold;
	font-size: 1.2em;
	background-color: #08088A;
	color: #FFFFFF
}

.filas {
	text-align: center;
	background-color: #5882FA;
}
h1 {
text-align: center;
}
</style>

</head>
<body>

	<%
		//obtenemos los productos del controlador-servlet
		//fijarse en el downcasting,ya que viene como Object el illoputa
		//List<Productos> losProductos = (List<Productos>) request.getAttribute("LISTAPRODUCTOS");
	%>


<h1>Bienvenidos a la App: ¡Pepe Palo´s Productos!</h1>

	<table>

		<tr>
			<td class="cabecera">Código Articulo</td>
			<td class="cabecera">Sección</td>
			<td class="cabecera">Nombre Artículo</td>
			<td class="cabecera">Precio</td>
			<td class="cabecera">Fecha</td>
			<td class="cabecera">Importado</td>
			<td class="cabecera">País de Origen</td>
			<td class="cabecera">Actualizar Registro</td>
			<td class="cabecera">Borrar Registro</td>

		</tr>

		<c:forEach var="p" items="${listaproductos}">

			<!-- Variable que viajara por la URL con el codigoArticulo para saber que producto actualizar 
		LA ETIQUETA URL MANDA ALGO(LO QUE ESTÉ EN SU INTERIOR) A ESA URL LA ETIQUETA PARAM FIJA UN PARAMETRO COMO NOSOTROS QUERAMOS-->
			<c:url var="linkTemp" value="ControladorProductos">

				<c:param name="instruccion" value="cargar"></c:param>
				<c:param name="cArticulo" value="${p.cARt}"></c:param>

			</c:url>

			<!-- Variable para la instruccion Delete. Tambien va a mandar la PK -->
			<c:url var="linkTemp2" value="ControladorProductos">

				<c:param name="instruccion" value="borrar"></c:param>
				<c:param name="cArticulo" value="${p.cARt}"></c:param>

			</c:url>



			<tr>
				<td class="filas">${p.cARt}</td>
				<td class="filas">${p.seccion}</td>
				<td class="filas">${p.nArt}</td>
				<td class="filas">${p.precio}</td>
				<td class="filas">${p.fecha}</td>
				<td class="filas">${p.importado}</td>
				<td class="filas">${p.pOrig}</td>
				<td class="filas"><a href="${linkTemp}">Actualizar</a></td>
				<td class="filas"><a href="${linkTemp2}">Eliminar</a></td>


			</tr>

		</c:forEach>

	</table>

	<div id="contenedorBoton">

		<input type="button" value="Insertar Registro" id="insertar"
			onclick="window.location.href='inserta_producto.jsp'" />


	</div>

</body>
</html>