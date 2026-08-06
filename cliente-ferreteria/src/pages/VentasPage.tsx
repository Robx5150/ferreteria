import { useEffect, useState } from "react";
import { obtenerVentas } from "../services/ventaService";
import type { Venta } from "../types/Venta";
import { Link } from "react-router-dom";
import Layout from "../componentes/Layout";

export default function VentasPage() {
  const [ventas, setVentas] = useState<Venta[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    const cargarVentas = async () => {
      try {
        const data = await obtenerVentas();
        setVentas(data);
      } catch (error) {
        console.error(error);
        setError("No fue posible obtener las ventas");
      } finally {
        setLoading(false);
      }
    };

    cargarVentas();
  }, []);

  if (loading) {
    return <div className="p-4">Cargando ventas...</div>;
  }

  if (error) {
    return <div className="p-4 text-red-600">{error}</div>;
  }

  return (
    <Layout>
      <div className="p-6">
        <h1 className="text-2xl font-bold mb-6">Listado de Ventas</h1>

        <div className="overflow-x-auto">
          <table className="min-w-full border border-gray-300">
            <thead className="bg-gray-100">
              <tr>
                <th className="border p-3 text-left">Factura</th>

                <th className="border p-3 text-left">Fecha</th>

                <th className="border p-3 text-left">Total</th>

                <th className="border p-3 text-left">Estado</th>
              </tr>
            </thead>

            <tbody>
              {ventas.map((venta) => (
                <tr key={venta.numeroFactura}>
                  <td className="border p-3">{venta.numeroFactura}</td>

                  <td className="border p-3">{venta.fecha}</td>

                  <td className="border p-3">
                    ${venta.total.toLocaleString()}
                  </td>

                  <td className="border p-3">{venta.estado}</td>

                  <td className="border p-3">
                    <Link
                      to={`/detalle/${venta.numeroFactura}`}
                      className="bg-blue-500 text-white px-3 py-1 rounded"
                    >
                      Detalle
                    </Link>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
    </Layout>
  );
}
