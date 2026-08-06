package mapper;

import dto.DetalleVentaDTO;
import dto.VentaDTO;
import dto.VentaResumenDTO;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.processing.Generated;
import model.DetalleVenta;
import model.Venta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-07-07T12:58:51-0400",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.42.0.v20250526-2018, environment: Java 21.0.7 (Eclipse Adoptium)"
)
@Component
public class VentaMapperImpl implements VentaMapper {

    @Autowired
    private DetalleVentaMapper detalleVentaMapper;

    @Override
    public VentaDTO toDTO(Venta venta, Map<String, String> nombrePorSku) {
        if ( venta == null ) {
            return null;
        }

        VentaDTO ventaDTO = new VentaDTO();

        ventaDTO.setDetalles( detalleVentaListToDetalleVentaDTOList( venta.getDetalles(), nombrePorSku ) );
        ventaDTO.setEstado( venta.getEstado() );
        ventaDTO.setFecha( venta.getFecha() );
        ventaDTO.setId( venta.getId() );
        ventaDTO.setNumeroFactura( venta.getNumeroFactura() );
        ventaDTO.setTotal( venta.getTotal() );
        ventaDTO.setUsuario( venta.getUsuario() );

        return ventaDTO;
    }

    @Override
    public List<VentaDTO> toDTOList(List<Venta> ventas, Map<String, String> nombrePorSku) {
        if ( ventas == null ) {
            return null;
        }

        List<VentaDTO> list = new ArrayList<VentaDTO>( ventas.size() );
        for ( Venta venta : ventas ) {
            list.add( toDTO( venta, nombrePorSku ) );
        }

        return list;
    }

    @Override
    public List<VentaResumenDTO> toResumenDTOList(List<Venta> ventas) {
        if ( ventas == null ) {
            return null;
        }

        List<VentaResumenDTO> list = new ArrayList<VentaResumenDTO>( ventas.size() );
        for ( Venta venta : ventas ) {
            list.add( ventaToVentaResumenDTO( venta ) );
        }

        return list;
    }

    protected List<DetalleVentaDTO> detalleVentaListToDetalleVentaDTOList(List<DetalleVenta> list, Map<String, String> nombrePorSku) {
        if ( list == null ) {
            return null;
        }

        List<DetalleVentaDTO> list1 = new ArrayList<DetalleVentaDTO>( list.size() );
        for ( DetalleVenta detalleVenta : list ) {
            list1.add( detalleVentaMapper.toDTO( detalleVenta, nombrePorSku ) );
        }

        return list1;
    }

    protected VentaResumenDTO ventaToVentaResumenDTO(Venta venta) {
        if ( venta == null ) {
            return null;
        }

        VentaResumenDTO ventaResumenDTO = new VentaResumenDTO();

        ventaResumenDTO.setEstado( venta.getEstado() );
        ventaResumenDTO.setFecha( venta.getFecha() );
        ventaResumenDTO.setId( venta.getId() );
        ventaResumenDTO.setNumeroFactura( venta.getNumeroFactura() );
        ventaResumenDTO.setTotal( venta.getTotal() );
        ventaResumenDTO.setUsuario( venta.getUsuario() );

        return ventaResumenDTO;
    }
}
