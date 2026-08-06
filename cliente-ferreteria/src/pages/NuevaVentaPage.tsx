import { useEffect, useMemo, useState } from "react";
import { useNavigate } from "react-router-dom";
import { ToastService } from "../utils/toastService";

import Layout from "../componentes/Layout";

import { obtenerProductos } from "../services/productoService";
import { crearVenta } from "../services/ventaService";

import type { Producto } from "../types/Producto";
import type { ItemVenta } from "../types/ItemVenta";
import type { VentaCreateDTO } from "../types/VentaCreateDTO";

export default function NuevaVentaPage() {
  const navigate = useNavigate();

  const [numeroFactura, setNumeroFactura] = useState("");
  const [productos, setProductos] = useState<Producto[]>([]);
  const [skuSeleccionado, setSkuSeleccionado] = useState("");
  const [cantidad, setCantidad] = useState<number>(1);
  const [items, setItems] = useState<ItemVenta[]>([]);
  const [loadingProductos, setLoadingProductos] = useState(true);
  const [guardando, setGuardando] = useState(false);

  useEffect(() => {
    const cargarProductos = async () => {
      try {
        const data = await obtenerProductos();
        setProductos(data);
      } catch (error) {
        console.error(error);
        alert("No fue posible cargar los productos");
      } finally {
        setLoadingProductos(false);
      }
    };

    cargarProductos();
  }, []);

  const productoSeleccionado = useMemo(
    () => productos.find((p) => p.sku === skuSeleccionado),
    [skuSeleccionado, productos],
  );

  const agregarProducto = () => {
    if (!productoSeleccionado) {
      alert("Debe seleccionar un producto");
      return;
    }

    if (cantidad <= 0) {
      alert("La cantidad debe ser mayor a cero");
      return;
    }

    const indiceExistente = items.findIndex(
      (item) => item.sku === productoSeleccionado.sku,
    );

    if (indiceExistente >= 0) {
      const nuevosItems = [...items];

      nuevosItems[indiceExistente].cantidad += cantidad;
      nuevosItems[indiceExistente].subtotal =
        nuevosItems[indiceExistente].cantidad *
        nuevosItems[indiceExistente].precioUnitario;

      setItems(nuevosItems);
    } else {
      const nuevoItem: ItemVenta = {
        sku: productoSeleccionado.sku,
        nombre: productoSeleccionado.nombre,
        cantidad,
        precioUnitario: productoSeleccionado.precioventa,
        subtotal: cantidad * productoSeleccionado.precioventa,
      };

      setItems([...items, nuevoItem]);
    }

    setSkuSeleccionado("");
    setCantidad(1);
  };

  const eliminarProducto = (index: number) => {
    setItems(items.filter((_, i) => i !== index));
  };

  const total = useMemo(
    () => items.reduce((acc, item) => acc + item.subtotal, 0),
    [items],
  );

  const guardarVenta = async () => {
    if (!numeroFactura.trim()) {
      //alert("Debe ingresar el número de factura");
      ToastService.error("Debe ingresar el número de factura");
      return;
    }

    if (items.length === 0) {
      alert("Debe agregar al menos un producto");
      return;
    }

    const venta: VentaCreateDTO = {
      numeroFactura,
      detalles: items.map((item) => ({
        sku: item.sku,
        cantidad: item.cantidad,
        precioUnitario: item.precioUnitario,
      })),
    };

    try {
      setGuardando(true);

      await crearVenta(venta);

      ToastService.successRedirect(
        "Venta creada exitosamente",
        navigate,
        "/venta",
      );
    } catch (error) {
      console.error(error);
      alert("Error al crear la venta");
    } finally {
      setGuardando(false);
    }
  };

  return (
    <Layout>
      <div className="p-6">
        <h1 className="text-3xl font-bold mb-6">Nueva Venta</h1>

        {/* FACTURA */}
        <div className="border rounded-lg p-4 mb-6">
          <h2 className="text-xl font-semibold mb-4">Información General</h2>

          <label className="block mb-2 font-medium">Número de Factura</label>

          <input
            type="text"
            value={numeroFactura}
            onChange={(e) => setNumeroFactura(e.target.value)}
            className="border rounded px-3 py-2 w-64"
            placeholder="Ej: 81500"
          />
        </div>

        {/* AGREGAR PRODUCTO */}
        <div className="border rounded-lg p-4 mb-6">
          <h2 className="text-xl font-semibold mb-4">Agregar Producto</h2>

          {loadingProductos ? (
            <p>Cargando productos...</p>
          ) : (
            <div className="flex flex-wrap gap-4 items-end">
              <div>
                <label className="block mb-2 font-medium">Producto</label>

                <select
                  value={skuSeleccionado}
                  onChange={(e) => setSkuSeleccionado(e.target.value)}
                  className="border rounded px-3 py-2 min-w-[300px]"
                >
                  <option value="">Seleccione un producto</option>

                  {productos.map((p) => (
                    <option key={p.sku} value={p.sku}>
                      {p.nombre}
                    </option>
                  ))}
                </select>
              </div>

              <div>
                <label className="block mb-2 font-medium">Cantidad</label>

                <input
                  type="number"
                  min={1}
                  value={cantidad}
                  onChange={(e) => setCantidad(Number(e.target.value))}
                  className="border rounded px-3 py-2 w-28"
                />
              </div>

              <button
                type="button"
                onClick={agregarProducto}
                className="bg-green-600 text-white px-4 py-2 rounded hover:bg-green-700"
              >
                Agregar
              </button>
            </div>
          )}

          {productoSeleccionado && (
            <div className="mt-4 bg-gray-50 p-4 rounded">
              <p>
                <strong>SKU:</strong> {productoSeleccionado.sku}
              </p>
              <p>
                <strong>Producto:</strong> {productoSeleccionado.nombre}
              </p>
              <p>
                <strong>Precio:</strong> $
                {productoSeleccionado.precioventa.toLocaleString()}
              </p>
            </div>
          )}
        </div>

        {/* DETALLE */}
        <div className="border rounded-lg p-4">
          <h2 className="text-xl font-semibold mb-4">Detalle de la Venta</h2>

          {items.length === 0 ? (
            <p>No hay productos agregados.</p>
          ) : (
            <div className="overflow-x-auto">
              <table className="min-w-full border">
                <thead className="bg-gray-100">
                  <tr>
                    <th className="border p-2">SKU</th>
                    <th className="border p-2">Producto</th>
                    <th className="border p-2">Cantidad</th>
                    <th className="border p-2">Precio</th>
                    <th className="border p-2">Subtotal</th>
                    <th className="border p-2">Acción</th>
                  </tr>
                </thead>

                <tbody>
                  {items.map((item, index) => (
                    <tr key={item.sku}>
                      <td className="border p-2">{item.sku}</td>
                      <td className="border p-2">{item.nombre}</td>
                      <td className="border p-2 text-center">
                        {item.cantidad}
                      </td>
                      <td className="border p-2 text-right">
                        ${item.precioUnitario.toLocaleString()}
                      </td>
                      <td className="border p-2 text-right">
                        ${item.subtotal.toLocaleString()}
                      </td>
                      <td className="border p-2 text-center">
                        <button
                          onClick={() => eliminarProducto(index)}
                          className="bg-red-600 text-white px-2 py-1 rounded"
                        >
                          Eliminar
                        </button>
                      </td>
                    </tr>
                  ))}
                </tbody>

                <tfoot>
                  <tr>
                    <td colSpan={4} className="border p-2 text-right font-bold">
                      TOTAL
                    </td>
                    <td className="border p-2 text-right font-bold">
                      ${total.toLocaleString()}
                    </td>
                    <td className="border"></td>
                  </tr>
                </tfoot>
              </table>
            </div>
          )}
        </div>

        {/* BOTONES */}
        <div className="mt-6 flex gap-4">
          <button
            onClick={guardarVenta}
            disabled={guardando}
            className="bg-blue-600 text-white px-5 py-2 rounded disabled:bg-gray-400"
          >
            {guardando ? "Guardando..." : "Guardar Venta"}
          </button>

          <button
            onClick={() => navigate("/venta")}
            className="bg-gray-600 text-white px-5 py-2 rounded"
          >
            Cancelar
          </button>
        </div>
      </div>
    </Layout>
  );
}
