<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Formulario de Actualizacion Asinto´s</title>
<style>
table {
	width: 400px;
	background-color: green;
	border: 1px solid #FF0000;
	margin-left: auto;
	margin-right: auto;
	padding: 25px;
	float: right;
}

fieldset {
	border-color: red;
	background-color: black;
}

form {
	width: 400px;
	margin: auto;
}

h1 {
	width: 50%;
	margin: 25px auto;
	text-align: center;
	margin-bottom: 50px;
}

legend {
	text-align: center;
	background-color: green;
	color: white;
	padding: 5px 10px;
}

body {
	background-color: #FC9;
}

fieldset {
	display: inline-block;
	margin: auto;
}

#boton, #boton2 {
	padding-top: 25px;
}
</style>
</head>
<body>

	<h1>Actualizar Registro/Producto</h1>

	<form action="ControladorProductos" method="get">
		<input type="hidden" name="instruccion" value="actualizarBBDD">
		<input type="hidden" name="cArt" value="${ProductoActualizar.cARt}">
		<fieldset>
			<legend>Formulario de Actualización</legend>
			<table>
				<!-- NO HACE FALTA EL PK YA QUE ES LA CLAVE tr>
          <td><label>Código Artículo:</label></td>
          <td><input type="text" name="c_art"></td>
          </tr-->
				<tr>
					<td><label>Sección:</label></td>
					<td><input type="text" name="secc"
						value="${ProductoActualizar.seccion}"></td>
				</tr>
				<tr>
					<td><label>Nombre Artículo:</label></td>
					<td><input type="text" name="n_art"
						value="${ProductoActualizar.nArt}"></td>
				</tr>
				<tr>
					<td><label>Precio: </label></td>
					<td><input type="text" name="pre"
						value="${ProductoActualizar.precio}"></td>
				</tr>
				<tr>
					<td><label>Fecha: </label></td>
					<td><input type="date" name="fec" placeholder="yyyy-mm-dd"
						value="${ProductoActualizar.fecha}"></td>
				</tr>
				<tr>
					<td><label>Importado: </label></td>
					<td><input type="text" name="imp"
						value="${ProductoActualizar.importado}"></td>
				</tr>
				<tr>
					<td><label>País de Origen: </label></td>
					<td><input type="text" name="p_ori"
						value="${ProductoActualizar.pOrig}"></td>
				</tr>

				<tr>
					<td colspan="1" align="left" id="boton"><input type="submit"
						name="enviando" value="Actualizar Registro"></td>
					<td colspan="1" align="right" id="boton2"><input type="reset"
						name="borrando" value="Resetear Campos"></td>
				</tr>
			</table>
		</fieldset>
	</form>

</body>
</html>