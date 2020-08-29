package com.pildorasinformaticas.productos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

public class ModeloProductos
{
	// necsitamos un objeto DataSource para la Pool Connection
	private DataSource origenDatos;

	public ModeloProductos(DataSource origenDatos)
	{
		this.origenDatos = origenDatos;
	}

	// getter que nos devolverá una coleccion de Productos
	public List<Productos> getProductos() throws Exception
	{
		// Creamos la coleccion que vamos a devolver
		List<Productos> productos = new ArrayList<Productos>();

		// Creamos la conexion a la BBDD
		Connection miConexion = null;
		Statement miStatement = null;
		ResultSet miResultSet = null;

		// -------establecer la conexion----------
		try
		{
			miConexion = origenDatos.getConnection();// Pool Connection

			// -----------crear sentencia sql y el statement----------
			String instruccionSql = "select * from productos";
			miStatement = miConexion.createStatement();

			// ---------ejecutar sentencia sql----------
			miResultSet = miStatement.executeQuery(instruccionSql);

			// ---------recorrer el resultset
			while (miResultSet.next())
			{
				// No confundir estas variables con los campos de clase de Productos
				String c_art = miResultSet.getString("CÓDIGOARTÍCULO");
				String seccion = miResultSet.getString("SECCIÓN");
				String n_art = miResultSet.getString("NOMBREARTÍCULO");
				double precio = miResultSet.getDouble("PRECIO");
				Date fecha = miResultSet.getDate("FECHA");
				String importado = miResultSet.getString("IMPORTADO");
				String p_orig = miResultSet.getString("PAÍSDEORIGEN");

				// Creamos una variable temporal de tipo Productos
				Productos tempProd = new Productos(c_art, seccion, n_art, precio, fecha, importado, p_orig);

				productos.add(tempProd);

			}
		} finally
		{

			try
			{
				miStatement.close();
				miConexion.close();

			} catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
		return productos;

	}

	public void agregarElNuevoproducto(Productos nuevoProducto) throws Exception
	{
		// -------- PASO 1 -------
		// obtener la conexion
		Connection miConexion = null;
		PreparedStatement miStatement = null;

		try
		{

			miConexion = origenDatos.getConnection();

			// -------- PASO 2------
			// crear la instruccion SQL que inserte el producto
			String sql = "insert into productos(CÓDIGOARTÍCULO, SECCIÓN, NOMBREARTÍCULO,PRECIO,FECHA,IMPORTADO,PAÍSDEORIGEN)"
					+ " VALUES (?,?,?,?,?,?,?)";

			miStatement = miConexion.prepareStatement(sql);

			// -------- PASO 3-------
			// establecer los parámetros para el producto

			miStatement.setString(1, nuevoProducto.getcARt());
			miStatement.setString(2, nuevoProducto.getSeccion());
			miStatement.setString(3, nuevoProducto.getnArt());
			miStatement.setDouble(4, nuevoProducto.getPrecio());

			// debemos convertir de util.Date a sql.Date ya que es lo que pide el
			// PreparedStatement.setDate
			java.util.Date utilDate = nuevoProducto.getFecha();
			java.sql.Date fechaSQL = new java.sql.Date(utilDate.getTime());

			miStatement.setDate(5, fechaSQL);

			// forma 2
			// miStatement.setDate(5, (java.sql.Date) nuevoProducto.getFecha());

			miStatement.setString(6, nuevoProducto.getImportado());
			miStatement.setString(7, nuevoProducto.getpOrig());

			// -------- PASO 4 ------
			// ejecutar la instruccion sql

			miStatement.execute();

		} catch (Exception e)
		{

		} finally
		{

			try
			{
				miStatement.close();
				miConexion.close();

			} catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
	}

	public Productos getProducto(String codigoArticulo)
	{
		Productos elProducto = null;
		Connection miConexion = null;
		PreparedStatement miStatement = null;
		ResultSet rs = null;
		String cArticulo = codigoArticulo;

		// 1- Establecer la conexion con la BBDD con una PoolConnection
		try
		{ // recuerda que esta todo en el XML por ser una Pool Connection
			miConexion = origenDatos.getConnection();

			// 2- Realizar la consulta que busque el Producto en base al codArt
			String sqlA = "SELECT * FROM PRODUCTOS WHERE CÓDIGOARTÍCULO=?";

			// 3- Crear la consulta preparada
			miStatement = miConexion.prepareStatement(sqlA);

			// 4- Establecer los parametros
			miStatement.setString(1, cArticulo);

			// Ejecutar la consulta
			rs = miStatement.executeQuery();

			// Obtener los datos de respuesta y devolverlos

			if (rs.next())
			{

				//
				String c_art = rs.getString("CÓDIGOARTÍCULO");
				String seccion = rs.getString("SECCIÓN");
				String n_art = rs.getString("NOMBREARTÍCULO");
				double precio = rs.getDouble("PRECIO");
				Date fecha = rs.getDate("FECHA");
				String importado = rs.getString("IMPORTADO");
				String p_orig = rs.getString("PAÍSDEORIGEN");

				// Guardamos en nuestra instancia el Producto consultado
				elProducto = new Productos(c_art, seccion, n_art, precio, fecha, importado, p_orig);

			} else
			{
				throw new Exception("No hemos encontrado el producto con codigo articulo = " + cArticulo);
			}

		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{

			try
			{
				miStatement.close();
				miConexion.close();

			} catch (SQLException e)
			{
				e.printStackTrace();
			}
		}

		// devolvemos el objeto Producto para mandarlo al controlador

		return elProducto;

	}

	public void actualizarProducto(Productos productoActualizado) throws Exception
	{

		Connection miConexion = null;
		PreparedStatement miStatement = null;

		try
		{
			// Establecer la conexion
			miConexion = origenDatos.getConnection();

			// Crear la sentencia SQL
			String sqlB = "update productos set sección=?, nombreartículo=?,precio=?,fecha=?,"
					+ " importado=?, paísdeorigen=? where códigoartículo=?";

			// Crear la consulta preparada
			miStatement = miConexion.prepareStatement(sqlB);

			// Establecer los parámetros
			miStatement.setString(1, productoActualizado.getSeccion());
			miStatement.setString(2, productoActualizado.getnArt());
			miStatement.setDouble(3, productoActualizado.getPrecio());

			java.util.Date utilDate = productoActualizado.getFecha();
			java.sql.Date fechaSQL = new java.sql.Date(utilDate.getTime());

			miStatement.setDate(4, fechaSQL);

			miStatement.setString(5, productoActualizado.getImportado());
			miStatement.setString(6, productoActualizado.getpOrig());
			miStatement.setString(7, productoActualizado.getcARt());

			// Ejecutar la instruccion SQL
			miStatement.execute();

		} finally
		{

			try
			{
				miStatement.close();
				miConexion.close();

			} catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
	}

	@SuppressWarnings("unused")
	public void borraProducto(String codigoArticulo)
	{
		Productos elProducto = null;
		Connection miConexion = null;
		PreparedStatement miStatement = null;
		ResultSet rs = null;
		String cArticulo = codigoArticulo;

		// 1- Establecer la conexion con la BBDD con una PoolConnection
		try
		{
			// recuerda que esta todo en el XML por ser una Pool Connection
			miConexion = origenDatos.getConnection();

			// 2- Crear la SQL de eliminacion
			String sqlA = "DELETE FROM PRODUCTOS WHERE CÓDIGOARTÍCULO=?";

			// 3- Crear la consulta preparada
			miStatement = miConexion.prepareStatement(sqlA);

			// 4- Establecer los parametros
			miStatement.setString(1, cArticulo);

			// Ejecutar la consulta
			miStatement.executeUpdate();

		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{

			try
			{
				miStatement.close();
				miConexion.close();

			} catch (SQLException e)
			{
				e.printStackTrace();
			}
		}

	}

}
