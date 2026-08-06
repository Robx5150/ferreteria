import { BrowserRouter, Routes, Route } from "react-router-dom";
import { ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import HomePage from "./pages/HomePage";
import VentasPage from "./pages/VentasPage";
import NuevaVentaPage from "./pages/NuevaVentaPage";
import DetalleVentaPage from "./pages/DetalleVentaPage";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<HomePage />} />

        <Route path="/venta" element={<VentasPage />} />

        <Route path="/nueva-venta" element={<NuevaVentaPage />} />

        <Route path="/detalle/:id" element={<DetalleVentaPage />} />
      </Routes>
      <ToastContainer
        position="top-center"
        className="fixed top-1/2 left-1/2 -translate-x-1/2 -translate-y-1/2"
      />
    </BrowserRouter>
  );
}

export default App;
