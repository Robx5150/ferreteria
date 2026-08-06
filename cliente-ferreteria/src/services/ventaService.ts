import { api } from "../api/axios";
import type { Venta } from "../types/Venta";
import type { DetalleVenta } from "../types/DetalleVenta";
import type { VentaCreateDTO } from "../types/VentaCreateDTO";

export const obtenerVentas = async (): Promise<Venta[]> => {
  const response = await api.get<Venta[]>("/fventas/api/ventas/ventas-resumen");

  return response.data;
};

export const obtenerDetalleVenta = async (
  numeroFactura: number,
): Promise<DetalleVenta> => {
  const response = await api.get<DetalleVenta>(
    `/fventas/api/ventas/por-factura/${numeroFactura}`,
  );

  return response.data;
};

export const crearVenta = async (venta: VentaCreateDTO) => {
  const response = await api.post("/fventas/api/ventas", venta);
  return response.data;
};
