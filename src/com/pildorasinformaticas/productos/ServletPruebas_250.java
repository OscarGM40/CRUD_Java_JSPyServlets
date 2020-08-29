package com.pildorasinformaticas.productos;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class ServletPruebas_250
 */
@WebServlet("/ServletPruebas_250")
public class ServletPruebas_250 extends HttpServlet
{
	private static final long serialVersionUID = 1L;
       
	//DEFINIR O ESTABLECER EL DATASOURCE
	
	@Resource(name="jdbc/Productos")
	
	private DataSource miPool;
	
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletPruebas_250() 
    {
        super();
        
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		//Creamos el obj PrintWriter
		PrintWriter salida = response.getWriter();
		
		
		//debemos especificar el contenido,asi hara los saltos de linea sin ponerlos con html
		response.setContentType("text/plain");
		
		//Crear conexion con BBDD
		Connection miConexion=null;
		Statement miStatement=null;
		ResultSet rs=null;
		
		try 
		{
			//para usar el Pool es tan facil como usar su metodo getConnection de la interface DataSource
			//REcordemos que vamos a usar la BD pruebas del WAMP
			miConexion = miPool.getConnection();
			
			String Sentencia="select * from productos";
			
			miStatement = miConexion.createStatement();
			
			rs = miStatement.executeQuery(Sentencia);
			
			while(rs.next()) 
			{
				String nombre_articulo = rs.getString(3);
				
				salida.println(nombre_articulo);
			
				
				
			}
			
			
			
		}catch(Exception e) 
		{
			e.printStackTrace();
		}
		
		
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		//se puede borrar si no salimos por post
	}

}
