import type { ReactNode } from "react";
import { Link } from "react-router-dom";
import LogoutButton from "../componentes/LogoutButton";

type LayoutProps = {
  children: ReactNode;
};

function Layout({ children }: LayoutProps) {
  return (
    <div className="min-h-screen bg-gray-100">
      <header className="bg-blue-700 text-white shadow">
        <div className="container mx-auto flex items-center gap-6 p-4">
          <Link to="/">
            <h1 className="text-2xl font-bold">Ferretería</h1>
          </Link>

          <Link to="/venta">Ventas</Link>

          <Link to="/nueva-venta">Nueva Venta</Link>
          <div className="ml-auto">
            <LogoutButton />
          </div>
        </div>
      </header>

      <main className="p-6">{children}</main>
    </div>
  );
}

export default Layout;
