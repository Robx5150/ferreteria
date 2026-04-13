package model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;



@Getter
@NoArgsConstructor
@Entity
@Table(name = "detalle_venta")
public class DetalleVenta {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@Column(nullable = false)
	private String sku;
		
	private Integer cantidad;
	
	private BigDecimal precioUnitario;
	
	private BigDecimal subtotal;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "venta_id") 
    private Venta venta;

	
	public DetalleVenta(String sku, Integer cantidad, BigDecimal precioUnitario) {
        this.sku = sku;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.subtotal = precioUnitario.multiply(BigDecimal.valueOf(cantidad));
    }

    void setVenta(Venta venta) { 
        this.venta = venta;
    }

}
