package model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import enums.EstadoVenta;
import exceptions.BusinessException;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;



@Getter
@NoArgsConstructor
@Entity
@Table(name = "ventas")
public class Venta implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, unique = true)
    private String numeroFactura;
	
	private LocalDateTime fecha;

    private BigDecimal total;

    private String usuario;
    
    @Enumerated(EnumType.STRING)
    private EstadoVenta estado;
    
    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<DetalleVenta> detalles = new ArrayList<>();
    
    public Venta(String numeroFactura, String usuario) {
        this.numeroFactura = numeroFactura;
        this.usuario = usuario;
        this.fecha = LocalDateTime.now();
        this.estado = EstadoVenta.EMITIDA;
        this.total = BigDecimal.ZERO;
    }
    
    public void agregarDetalle(DetalleVenta detalle) {
        detalle.setVenta(this);  
        this.detalles.add(detalle); 
        recalcularTotal();
    }

    private void recalcularTotal() {
        this.total = detalles.stream()
                .map(DetalleVenta::getSubtotal) 
                .reduce(BigDecimal.ZERO, BigDecimal::add); 
    }
    
    public void cambiarEstado(EstadoVenta nuevoEstado) {

        if (this.estado == EstadoVenta.ANULADA) {
            throw new BusinessException("No se puede modificar una venta anulada");
        }

        
        if (this.estado == EstadoVenta.EMITIDA && nuevoEstado == EstadoVenta.EMITIDA) {
            throw new BusinessException("La venta ya está emitida");
        }
        
        if (this.estado == EstadoVenta.PENDIENTE && nuevoEstado == EstadoVenta.ANULADA) {
            throw new BusinessException("Solo se puede anular una venta emitida");
        }

        this.estado = nuevoEstado;
    }
    

}
