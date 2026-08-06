import keycloak from "../auth/keycloak";

export default function LogoutButton() {
  const handleLogout = () => {
    keycloak.logout({
      redirectUri: window.location.origin,
    });
  };

  return (
    <button
      onClick={handleLogout}
      className="px-3 py-2 bg-red-600 hover:bg-red-700 text-white rounded-md text-sm"
    >
      Cerrar sesión
    </button>
  );
}
