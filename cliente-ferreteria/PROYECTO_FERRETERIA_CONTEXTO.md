# PROYECTO_FERRETERIA_CONTEXTO

## Descripción

Sistema de gestión para una ferretería desarrollado con arquitectura de microservicios utilizando Spring Boot en el backend y React en el frontend.

---

# Arquitectura

## Backend

| Microservicio        | Puerto |
| -------------------- | ------ |
| API Gateway          | 7000   |
| productos-ferreteria | 8000   |
| ventas-ferreteria    | 9000   |
| Eureka Server        | 8761   |
| Keycloak             | 8080   |

---

# Tecnologías Backend

- Java 21
- Spring Boot 3
- Spring Cloud Gateway
- Spring Security OAuth2 Resource Server
- Spring Data JPA
- Hibernate
- Maven
- Eureka
- Keycloak
- JWT
- MySQL

---

# Tecnologías Frontend

- React 19
- Vite
- TypeScript
- Tailwind CSS v3
- React Router DOM
- Axios
- React Toastify
- keycloak-js

---

# Seguridad

## Backend

- OAuth2 Resource Server
- JWT emitido por Keycloak
- API Gateway protegido
- Microservicios protegidos mediante JWT

## Frontend

Inicialización manual de Keycloak en `main.tsx`.

```ts
keycloak.init({
  onLoad: "login-required",
});
```

Renovación automática del token:

```ts
setInterval(() => {
  keycloak.updateToken(70);
}, 60000);
```

Axios utiliza un interceptor para enviar automáticamente el JWT.

---

# Configuración Tailwind CSS v3

Instalación

```bash
npm install -D tailwindcss postcss autoprefixer
```

Inicialización

```bash
npx tailwindcss init -p
```

## tailwind.config.js

```js
/** @type {import('tailwindcss').Config} */

export default {
  content: ["./index.html", "./src/**/*.{js,ts,jsx,tsx}"],

  theme: {
    extend: {},
  },

  plugins: [],
};
```

## postcss.config.js

```js
export default {
  plugins: {
    tailwindcss: {},

    autoprefixer: {},
  },
};
```

## src/index.css

```css
@tailwind base;
@tailwind components;
@tailwind utilities;
```

---

# Estructura aproximada del frontend

```
src
│
├── auth
│   └── keycloak.ts
│
├── componentes
│   ├── Layout.tsx
│   └── LogoutButton.tsx
│
├── pages
│   ├── HomePage.tsx
│   ├── VentasPage.tsx
│   ├── NuevaVentaPage.tsx
│   └── DetalleVentaPage.tsx
│
├── services
│   ├── axios.ts
│   ├── productoService.ts
│   ├── ventaService.ts
│   └── toastService.tsx
│
├── types
│
├── App.tsx
├── main.tsx
└── index.css
```

---

# Navegación

| Ruta         | Descripción       |
| ------------ | ----------------- |
| /            | Inicio            |
| /venta       | Listado de ventas |
| /nueva-venta | Crear venta       |
| /detalle/:id | Detalle de venta  |

---

# Productos

Endpoint

```
GET /fproductos/api/productos
```

Respuesta

```json
{
  "id": 1,
  "sku": "...",
  "nombre": "...",
  "marca": "...",
  "modelo": "...",
  "preciocompra": 0,
  "precioventa": 0
}
```

---

# Ventas

Actualmente el frontend envía un `VentaCreateDTO`.

```ts
export type VentaCreateDTO = {
  numeroFactura: string;
  usuario: string;
  detalles: {
    sku: string;
    cantidad: number;
    precioUnitario: number;
  }[];
};
```

---

# Funcionalidades implementadas en Nueva Venta

- Carga de productos desde la API.
- Tabla dinámica.
- Consolidación de SKUs repetidos.
- Cálculo automático de subtotal.
- Cálculo automático de total.
- Eliminación de productos.
- Guardado de venta.
- Navegación hacia `/venta`.

---

# ToastService

Se creó un servicio reutilizable basado en React Toastify.

Actualmente soporta:

- success()
- error()
- warning()
- successRedirect()

Se eliminaron los `alert()` del proyecto.

Los mensajes de éxito utilizan un botón **Cerrar**, y al presionarlo se realiza la navegación.

---

# Layout

Layout reutilizable.

Incluye:

- Logo de la aplicación.
- Navegación.
- Botón Cerrar sesión.

---

# Logout

```ts
keycloak.logout({
  redirectUri: window.location.origin,
});
```

---

# Estado actual

## Backend

- API Gateway funcionando.
- Eureka funcionando.
- Keycloak funcionando.
- Productos funcionando.
- Ventas funcionando.

## Frontend

- Login con Keycloak.
- Renovación automática del token.
- Axios con interceptor JWT.
- Tailwind CSS.
- React Router.
- Layout reutilizable.
- Logout.
- ToastService.
- Nueva Venta.

---

# Próxima mejora acordada

Actualmente el frontend envía:

```ts
usuario: "admin";
```

o posteriormente:

```ts
usuario: AuthService.getUsername();
```

La arquitectura recomendada consiste en eliminar ese campo del frontend y obtener el usuario autenticado directamente desde Spring Security.

Ejemplo:

```java
@PostMapping
public ResponseEntity<?> crearVenta(
        @RequestBody VentaCreateDTO dto,
        Principal principal) {

    String usuario = principal.getName();

    ...
}
```

o

```java
@PostMapping
public ResponseEntity<?> crearVenta(
        @RequestBody VentaCreateDTO dto,
        JwtAuthenticationToken jwt) {

    String usuario = jwt.getToken().getClaimAsString("preferred_username");

    ...
}
```

De esta forma el usuario nunca puede ser falsificado desde el frontend.

---

# Próximas funcionalidades

- Obtener usuario desde el JWT.
- Detalle de venta.
- Edición de venta.
- Búsqueda de productos.
- Paginación.
- Validación de stock.
- Dashboard.
- Reportes.
- Gestión de clientes.
- Gestión de proveedores.
- Gestión de compras.
- Inventario.

---

# Convenciones del proyecto

## React

- Componentes funcionales.
- Hooks.
- TypeScript.

## Servicios

Toda comunicación HTTP se realiza desde la carpeta `services`.

## DTO

Todos los DTO se ubican en la carpeta `types`.

## Notificaciones

Todas las notificaciones utilizan `ToastService`.

No utilizar `alert()`.

## Seguridad

El frontend únicamente consume la API.

Toda la lógica de negocio y validaciones importantes deben permanecer en el backend.

La identidad del usuario autenticado debe obtenerse desde el JWT y nunca confiar en datos enviados por React.

---

# Objetivo del proyecto

Construir una aplicación empresarial para una ferretería utilizando buenas prácticas de arquitectura:

- Microservicios.
- OAuth2 + JWT.
- Keycloak.
- React.
- TypeScript.
- Tailwind CSS.
- Componentes reutilizables.
- Servicios reutilizables.
- Código limpio.
- Separación clara entre frontend y backend.
