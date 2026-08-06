export interface DetalleVenta {
  numeroFactura: number;
  fecha: string;
  total: number;
  estado: string;
  usuario: string;
  detalles: DetalleVentaItem[];
}

export interface DetalleVentaItem {
  id: number;
  sku: string;
  nombreProducto: string;
  cantidad: number;
  precioUnitario: number;
  subtotal: number;
}
