package com.pildorasinformaticas.productos;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class ControladorProductos
 */
@WebServlet("/ControladorProductos")
public class ControladorProductos extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	// El controlador va a comunicarse con el modelo,luego debe tener una referencia
	// a él
	private ModeloProductos modeloProductos;

	// Establecemos el DataSource
	@Resource(name = "jdbc/Productos")

	private DataSource miPool;

	// tenemos que crear el método init() del servlet que es que se va a ejecutar en
	// primer lugar
	// Va a ser el método desde el cual arranque la aplicacion

	@Override
	public void init() throws ServletException
	{
		super.init();
		// lanzamos un error pos si no conectamos
		try
		{
			// fijarse como le pasamos desde aqui el pool al modelo
			modeloProductos = new ModeloProductos(miPool);
		} catch (Exception e)
		{
			throw new ServletException(e);

		}

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// debemos determinar si hacer la select ,el insert,el update,....Veamos los
		// pasos

		// ------------------PASO 1 ----------------
		// Leemos el valor(value)del atributo name del input oculto.Recuerda que lo
		// hacemos con el objeto implicito request y su metodo getParameter("name") <-
		// devuelve el value
		String elParametroURL = request.getParameter("instruccion"); // en el ParametroURL estaran los values

		// -----------------PASO 2 --------------
		// Si no ha llegado ese parametro no debe insertar,y debe hacer la select
		if (elParametroURL == null)
		{
			elParametroURL = "listar";
		}

		// ------------------PASO 3 -------------------
		// Redirigimos el flujo de ejecución al método adecuado

		switch (elParametroURL)
		{
		case "listar":
			obtenerProductos(request, response);
			break;
		case "insertarBBDD":
			agregarProductos(request, response);
			break;

		case "cargar":
			try
			{
				cargaProductos(request, response);
			} catch (Exception e)
			{
				e.printStackTrace();
			}
			break;

		case "borrar":
			try
			{
				eliminaProductos(request, response);
			} catch (Exception e)
			{
				e.printStackTrace();
			}
			break;

		case "actualizarBBDD":
			try
			{
				actualizaProductos(request, response);
			} catch (Exception e)
			{
				e.printStackTrace();
			}
			break;
		default:
			obtenerProductos(request, response);
		}

	}// fin doGet

	private void eliminaProductos(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		// Lo primero debe leer el codigoArtiulo que viene de listaProductos <c:param
		// name='' value=''> si queremos que se a por una JSP core tag
		String codigoArticulo = request.getParameter("cArticulo");

		// Comunicar con el modelo para que el modelo haga un delete con ese codArt

		modeloProductos.borraProducto(codigoArticulo);

		// volver a listar los productos
		obtenerProductos(request, response);

	}

	private void actualizaProductos(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		// 1- Leer los datos que vienen del formularioActualizar

		String CodArticulo = request.getParameter("cArt");
		String Seccion = request.getParameter("secc");
		String NombreArticulo = request.getParameter("n_art");
		double Precio = Double.parseDouble(request.getParameter("pre"));

		// fijarse como se parsea desde string a Date
		SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
		Date Fecha = null;
		try
		{
			Fecha = formatoFecha.parse(request.getParameter("fec"));
		} catch (ParseException e)
		{
			e.printStackTrace();
		}

		String Importado = request.getParameter("imp");
		String PaisOrigen = request.getParameter("p_ori");

		// 2- Crear un objeto Producto con la información rescatada del formulario
		Productos ProductoActualizado = new Productos(CodArticulo, Seccion, NombreArticulo, Precio, Fecha, Importado,
				PaisOrigen);

		// 3- Actualizar la base de datos con la info del Producto
		// Como estamos en MVC lo va a hacer el modelom,asiq ue debemos mandarle el
		// objeto
		modeloProductos.actualizarProducto(ProductoActualizado);

		// 4- Volver al listado con la info actualizada.
		obtenerProductos(request, response);

	}

	// metodo que carga el Producto y lo envia al formulario de actualizacion.Solo
	// entra si la instruccion vale 'cargar'
	private void cargaProductos(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		// Lo primero debe leer el codigoArtiulo que viene de listaProductos <c:param
		// name='' value=''> si queremos que se a por una JSP core tag
		String codigoArticulo = request.getParameter("cArticulo");

		// Comunicar con el modelo para que el modelo haga una consulta con ese codArt
		// En el Producto ya tendremos el producto para poder enviarlo al formulario de
		// actualizacion

		Productos elProducto = modeloProductos.getProducto(codigoArticulo);

		// Colocar atributo correspondiente al productocon esa PK para enviarlo por la
		// URL en el GET
		request.setAttribute("ProductoActualizar", elProducto);

		// enviar la informacion al formulario de actualizacion
		RequestDispatcher dispatcher = request.getRequestDispatcher("/actualiza_producto.jsp");
		dispatcher.forward(request, response);

	}

	// metodo para insertar y que despues llama a la select
	private void agregarProductos(HttpServletRequest request, HttpServletResponse response)
	{
		// -----------PASO 1----------
		// Leer la informacion del producto que viene del formulario

		String CodArticulo = request.getParameter("c_art");
		String Seccion = request.getParameter("secc");
		String NombreArticulo = request.getParameter("n_art");
		double Precio = Double.parseDouble(request.getParameter("pre"));

		// fijarse como se parsea desde string a Date
		SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
		Date Fecha = null;

		try
		{
			Fecha = formatoFecha.parse(request.getParameter("fec"));
		} catch (ParseException e)
		{
			e.printStackTrace();
		}

		String Importado = request.getParameter("imp");
		String PaisOrigen = request.getParameter("p_ori");

		// -----------PASO 2--------
		// Crear un objeto de tipo Producto
		Productos nuevoProducto = new Productos(CodArticulo, Seccion, NombreArticulo, Precio, Fecha, Importado,
				PaisOrigen);
		// ---------PASO 3 ----------
		// Enviar el objeto al modelo y despues insertar el objeto Producto en la BBDD

		try
		{
			modeloProductos.agregarElNuevoproducto(nuevoProducto);
		} catch (Exception e)
		{

			e.printStackTrace();
		}

		// --------PASO 4 ----------
		// Volver a listar la tabla de productos
		obtenerProductos(request, response);

	}

	// Método para listar (select *) los productos
	private void obtenerProductos(HttpServletRequest request, HttpServletResponse response)
	{
		// obtener la lista de productos desde el modelo
		List<Productos> productos;
		try
		{
			productos = modeloProductos.getProductos();

			// agregar esa lista al request
			request.setAttribute("listaproductos", productos);

			// enviar ese request a la página JSP
			// Recordemos que necesitamos un objeto RequestDistpacher
			RequestDispatcher miDispatcher = request.getRequestDispatcher("/ListaProductos.jsp");
			miDispatcher.forward(request, response);

		} catch (Exception e)
		{
			e.printStackTrace();
		}

	}

}
