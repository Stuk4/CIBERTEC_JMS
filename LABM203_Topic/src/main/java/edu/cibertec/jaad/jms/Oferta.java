package edu.cibertec.jaad.jms;

import java.io.Serializable;

public class Oferta implements Serializable{
	private static final long serialVersionUID = 1L;
	private String descripcion;
	private String producto;
	private Double monto;
	
	public Oferta(){
		
	}
	public Oferta(String descripcion, String producto, Double monto) {
		super();
		this.descripcion = descripcion;
		this.producto = producto;
		this.monto = monto;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getProducto() {
		return producto;
	}
	public void setProducto(String producto) {
		this.producto = producto;
	}
	public Double getMonto() {
		return monto;
	}
	public void setMonto(Double monto) {
		this.monto = monto;
	}
	@Override
	public String toString() {
		return "Oferta [descripcion=" + descripcion + ", producto=" + producto + ", monto=" + monto + "]";
	}
	
	
}
