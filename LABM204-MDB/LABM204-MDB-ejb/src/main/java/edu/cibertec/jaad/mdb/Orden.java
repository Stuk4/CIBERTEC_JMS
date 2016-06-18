package edu.cibertec.jaad.mdb;

import java.io.Serializable;
import java.util.Date;

public class Orden implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer idOrden;
	private String descripcion;
	private Date fechaRegistro;
	private Double subtotal;
	private Double descuento;
	private Double total;
	private String estado;
	private String idCliente;
	
	public Orden(){
		
	}

	public Orden(Integer idOrden, String descripcion, Date fechaRegistro, Double subtotal, Double descuento,
			Double total, String estado, String idCliente) {
		super();
		this.idOrden = idOrden;
		this.descripcion = descripcion;
		this.fechaRegistro = fechaRegistro;
		this.subtotal = subtotal;
		this.descuento = descuento;
		this.total = total;
		this.estado = estado;
		this.idCliente = idCliente;
	}

	public Integer getIdOrden() {
		return idOrden;
	}

	public void setIdOrden(Integer idOrden) {
		this.idOrden = idOrden;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public Double getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(Double subtotal) {
		this.subtotal = subtotal;
	}

	public Double getDescuento() {
		return descuento;
	}

	public void setDescuento(Double descuento) {
		this.descuento = descuento;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(String idCliente) {
		this.idCliente = idCliente;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Orden [idOrden=" + idOrden + ", descripcion=" + descripcion + ", fechaRegistro=" + fechaRegistro
				+ ", subtotal=" + subtotal + ", descuento=" + descuento + ", total=" + total + ", estado=" + estado
				+ ", idCliente=" + idCliente + "]";
	}
}
