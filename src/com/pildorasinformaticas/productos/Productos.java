package com.pildorasinformaticas.productos;

import java.util.Date;

public class Productos
{
	
	//Constructor sin la primary Key
	public Productos(String seccion, String nArt, double precio, Date fecha, String importado, String pOrig)
	{
		super();
		this.seccion = seccion;
		this.nArt = nArt;
		this.precio = precio;
		this.fecha = fecha;
		this.importado = importado;
		this.pOrig = pOrig;
	}
	
	//Constructor para los 7 campos
	public Productos
	(
			String cARt, String seccion, String nArt, double precio, Date fecha, String importado, String pOrig
	)
	{
	
		this.cARt = cARt;
		this.seccion = seccion;
		this.nArt = nArt;
		this.precio = precio;
		this.fecha = fecha;
		this.importado = importado;
		this.pOrig = pOrig;
	}
	
	
	
	
	public String getcARt()
	{
		return cARt;
	}

	public void setcARt(String cARt)
	{
		this.cARt = cARt;
	}

	public String getSeccion()
	{
		return seccion;
	}

	public void setSeccion(String seccion)
	{
		this.seccion = seccion;
	}

	public String getnArt()
	{
		return nArt;
	}

	public void setnArt(String nArt)
	{
		this.nArt = nArt;
	}

	public double getPrecio()
	{
		return precio;
	}

	public void setPrecio(double precio)
	{
		this.precio = precio;
	}

	public Date getFecha()
	{
		return fecha;
	}

	public void setFecha(Date fecha)
	{
		this.fecha = fecha;
	}

	public String getImportado()
	{
		return importado;
	}

	public void setImportado(String importado)
	{
		this.importado = importado;
	}

	public String getpOrig()
	{
		return pOrig;
	}

	public void setpOrig(String pOrig)
	{
		this.pOrig = pOrig;
	}


	
	


	@Override
	public String toString()
	{
		return "Productos [cARt=" + cARt + ", seccion=" + seccion + ", nArt=" + nArt + ", precio=" + precio + ", fecha="
				+ fecha + ", importado=" + importado + ", pOrig=" + pOrig + "]";
	}






	private String cARt;
	private String seccion;
	private String nArt;
	private double precio;
	private Date fecha;
	private String importado;
	private String pOrig;	
	
}//fin clase Productos
