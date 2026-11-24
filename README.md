# Modo Guardian

Aplicación Android desarrollada en **Kotlin** con **Jetpack Compose** para la gestión de usuarios y operaciones de seguridad. Incluye autenticación, registro, navegación por roles y almacenamiento local mediante Room y DataStore.

## Funcionalidades

- Inicio de sesión y cierre de sesión.
- Registro integral de usuarios y listado local.
- Interfaces diferenciadas para administradores, supervisores y operadores.
- Módulos de cámaras, eventos, alertas críticas y perfil.
- Cambio de tema claro/oscuro persistente.
- Diseño adaptable a distintos tamaños de pantalla.
- Funcionalidades offline con Room Database.

## Usuarios de prueba

Puedes iniciar sesión con cualquiera de las siguientes cuentas predefinidas:

| Rol | Correo | Contraseña |
|---|---|---|
| Administrador | `admin@guardian.test` | `123456` |
| Supervisor | `supervisor@guardian.test` | `123456` |
| Operador | `operador@guardian.test` | `123456` |
| Cuenta demo | `guardiantest@insercode.cl` | `123456` |

Todas las cuentas utilizan la contraseña `123456`.

## Tecnologías

- Kotlin
- Jetpack Compose y Material 3
- Navigation Compose
- ViewModel y Lifecycle
- Room Database
- DataStore Preferences
- Coil
- Gradle Kotlin DSL
- JUnit 5, Kotest y MockK

## Requisitos

- Android Studio con soporte para Gradle y Compose.
- JDK 11.
- SDK de Android API 37.
- Dispositivo o emulador con Android 7.1 (API 25) o superior.

## Ejecución

1. Clona el repositorio:

   ```bash
   git clone https://github.com/semorenob/DSY1105-002D-SMORENO-BJOFRE.git
   cd DSY1105-002D-SMORENO-BJOFRE
   ```

2. Cambia a la rama de desarrollo:

   ```bash
   git checkout insertcode
   ```

3. Abre el proyecto en Android Studio.

4. Espera a que Gradle sincronice las dependencias.

5. Ejecuta el módulo `app` en un emulador o dispositivo físico.

También puedes compilar desde la terminal:

- Linux/macOS:

  ```bash
  ./gradlew assembleDebug
  ```

- Windows:

  ```bat
  gradlew.bat assembleDebug
  ```

El APK generado quedará en `app/build/outputs/apk/debug/`.

## Pruebas

Ejecuta las pruebas unitarias con:

```bash
./gradlew test
```

Para ejecutar las pruebas instrumentadas, usa un emulador o dispositivo conectado:

```bash
./gradlew connectedAndroidTest
```

## Estructura principal

```text
app/src/main/java/com/example/modoguardian/
├── data/          # Room, DAOs y preferencias
├── model/         # Estados y modelos de interfaz
├── navigation/    # Rutas y eventos de navegación
├── ui/
│   ├── components # Componentes reutilizables
│   ├── screens/   # Pantallas de la aplicación
│   └── theme/     # Colores, tipografía y tema
└── viewmodel/     # Lógica de presentación
```

## Permisos

La aplicación solicita acceso opcional a la cámara para las funciones relacionadas con vigilancia. El permiso se declara como no obligatorio en el manifiesto.

## Integrantes

- Sebastián Moreno
- Benjamín Jofré

## Curso

Proyecto correspondiente a **DSY1105-002D**.
