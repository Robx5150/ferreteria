import type { VentaDetalleCreateDTO } from "./VentaDetalleCreateDTO";

export interface VentaCreateDTO {
  numeroFactura: string;
  detalles: VentaDetalleCreateDTO[];
}
