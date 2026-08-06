import Layout from "../componentes/Layout";
import logo from "../assets/ferreteria.png";

function HomePage() {
  return (
    <Layout>
      <div className="text-center">
        <h2 className="text-3xl font-bold mb-4">Sistema Ferretería</h2>
        <img
          src={logo}
          alt="Ferretería"
          className="w-60 h-60 mx-auto mb-6 rounded-lg shadow-md"
        />
      </div>
    </Layout>
  );
}

export default HomePage;
