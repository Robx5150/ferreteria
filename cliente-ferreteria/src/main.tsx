import ReactDOM from "react-dom/client";
import App from "./App";
import "./index.css";
import keycloak from "./auth/keycloak";

keycloak
  .init({
    onLoad: "login-required",
  })
  .then((authenticated) => {
    if (!authenticated) {
      console.error("Usuario no autenticado");
      return;
    }

    /* console.log("Usuario autenticado");
    console.log(keycloak.token);
    console.log("Token:", keycloak.token);
    console.log("Usuario:", keycloak.tokenParsed); */

    ReactDOM.createRoot(document.getElementById("root")!).render(<App />);

    setInterval(() => {
      keycloak
        .updateToken(70)
        .then((refreshed) => {
          if (refreshed) {
            console.log("Token renovado");
          }
        })
        .catch(() => {
          console.error("Error renovando token");
        });
    }, 60000);
  })
  .catch(() => {
    console.error("Error autenticando");
  });
