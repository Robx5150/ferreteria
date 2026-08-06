import { useEffect, useState } from "react";
import { useParams, Link } from "react-router-dom";
import Layout from "../componentes/Layout";
import { obtenerDetalleVenta } from "../services/ventaService";
import type { DetalleVenta } from "../types/DetalleVenta";

export default function DetalleVentaPage() {
  const { id } = useParams();

  const [venta, setVenta] = useState<DetalleVenta | null>(null);

  const [loading, setLoading] = useState(true);

  const [error, setError] = useState("");

  useEffect(() => {
    const cargarDetalle = async () => {
      try {
        if (!id) {
          return;
        }

        const data = await obtenerDetalleVenta(Number(id));

        setVenta(data);
      } catch (error) {
        console.error(error);

        setError("No fue posible obtener el detalle de la venta");
      } finally {
        setLoading(false);
      }
    };

    cargarDetalle();
  }, [id]);

  if (loading) {
    return (
      <Layout>
        <div className="p-6">Cargando detalle...</div>
      </Layout>
    );
  }

  if (error) {
    return (
      <Layout>
        <div className="p-6 text-red-600">{error}</div>
      </Layout>
    );
  }

  if (!venta) {
    return null;
  }

  return (
    <Layout>
      <div className="p-6">
        <h1 className="text-3xl font-bold mb-6">
          Factura N° {venta.numeroFactura}
        </h1>

        <div className="border rounded p-6 mb-8">
          <h2 className="text-xl font-semibold mb-4">Información General</h2>

          <div className="grid grid-cols-2 gap-4">
            <div>
              <label className="font-semibold">Fecha</label>

              <p>{venta.fecha?.split(" ")[0]}</p>
            </div>

            <div>
              <label className="font-semibold">Total</label>

              <p>${venta.total.toLocaleString()}</p>
            </div>

            <div>
              <label className="font-semibold">Estado</label>

              <p>{venta.estado}</p>
            </div>

            <div>
              <label className="font-semibold">Usuario</label>

              <p>{venta.usuario}</p>
            </div>
          </div>
        </div>

        <div className="border rounded p-6">
          <h2 className="text-xl font-semibold mb-4">Detalle Productos</h2>

          <div className="overflow-x-auto">
            <table className="min-w-full border">
              <thead className="bg-gray-100">
                <tr>
                  <th className="border p-2">ID</th>
                  <th className="border p-2">SKU</th>
                  <th className="border p-2">Producto</th>
                  <th className="border p-2">Cantidad</th>
                  <th className="border p-2">Precio Unitario</th>
                  <th className="border p-2">Subtotal</th>
                </tr>
              </thead>

              <tbody>
                {venta.detalles.map((detalle) => (
                  <tr key={detalle.id}>
                    <td className="border p-2">{detalle.id}</td>

                    <td className="border p-2">{detalle.sku}</td>

                    <td className="border p-2">{detalle.nombreProducto}</td>

                    <td className="border p-2">{detalle.cantidad}</td>

                    <td className="border p-2">
                      ${detalle.precioUnitario.toLocaleString()}
                    </td>

                    <td className="border p-2">
                      ${detalle.subtotal.toLocaleString()}
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </div>

        <div className="mt-6">
          <Link
            to="/venta"
            className="bg-blue-600 text-white px-4 py-2 rounded"
          >
            Volver
          </Link>
        </div>
      </div>
    </Layout>
  );
}
