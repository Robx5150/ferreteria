import { api } from "../api/axios";
import type { Producto } from "../types/Producto";

export const obtenerProductos = async (): Promise<Producto[]> => {
  const response = await api.get<Producto[]>("/fproductos/api/productos");
  return response.data;
};
