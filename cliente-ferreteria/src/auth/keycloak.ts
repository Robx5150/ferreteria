import Keycloak from "keycloak-js";

const keycloak = new Keycloak({
  url: "http://localhost:8080",
  realm: "ContactosReino",
  clientId: "login",
});

export default keycloak;
