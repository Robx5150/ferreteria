package model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "productos")
public class Producto implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true, nullable = false)
	private String sku;
	
	@Column(unique = true, nullable = false)
	private String nombre;
	
	private String marca;
	private String modelo;
	private Double preciocompra;
	private Double precioventa;
	
	public Producto(String sku, String nombre, String marca, String modelo, Double preciocompra, Double precioventa) {
		
		this.sku = sku;
		this.nombre = nombre;
		this.marca = marca;
		this.modelo = modelo;
		this.preciocompra = preciocompra;
		this.precioventa = precioventa;
	}
	
	
	public void actualizarProducto(String nombre, String marca, String modelo, Double preciocompra, Double precioventa) {
		
		this.nombre = nombre;
		this.marca = marca;
		this.modelo = modelo;
		this.preciocompra = preciocompra;
		this.precioventa = precioventa;
	}
	

}
