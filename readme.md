# Manual de Usuario: Aplicación de Gestión de Datos

Este manual proporciona una guía paso a paso para utilizar la aplicación de gestión de datos, que incluye funcionalidades de registro, inicio de sesión, y gestión de productos, empleados y ventas. La aplicación está desarrollada en JavaFX y utiliza una base de datos para almacenar la información.

---

## Requisitos del Sistema

- **Java Development Kit (JDK)**: Versión 11 o superior.
- **JavaFX**: Incluido en el JDK o descargable como un módulo separado.
- **Base de Datos**: MySQL o cualquier base de datos compatible con JDBC.
- **Dependencias**: Apache POI (para generación de informes en Excel) y iTextPDF (para generación de informes en PDF).

---

## Instalación y Configuración

1. **Clonar el Repositorio**  
   - Clona el repositorio del proyecto desde GitHub o descarga el código fuente.

2. **Configurar la Base de Datos**  
   - Crea una base de datos en MySQL con las tablas necesarias (`Productos`, `Empleados`, `Ventas`, `Usuarios`).  
   - Asegúrate de que el archivo `DatabaseConnection.java` esté configurado con las credenciales correctas de la base de datos.

3. **Compilar y Ejecutar**  
   - Abre el proyecto en tu IDE favorito (Eclipse, IntelliJ, etc.).  
   - Compila el proyecto y ejecuta la clase `LoginForm` para iniciar la aplicación.

---

## Funcionalidades de la Aplicación

### 1. **Registro de Usuarios**

- **Acceso**: Desde la pantalla de inicio de sesión, haz clic en el botón "Register".  
- **Campos Requeridos**:  
  - **Username**: Nombre de usuario único.  
  - **Password**: Contraseña segura.  
  - **Confirm Password**: Repite la contraseña.  
  - **Email**: Correo electrónico válido.  
- **Validaciones**:  
  - Las contraseñas deben coincidir.  
  - El nombre de usuario y el correo electrónico deben ser únicos.  
- **Mensajes**:  
  - Si el registro es exitoso, se mostrará un mensaje de éxito.  
  - Si hay errores, se mostrará un mensaje de error.  

![Registro de Usuario](registro_usuario.png)

---

### 2. **Inicio de Sesión**

- **Acceso**: Ejecuta la clase `LoginForm` para abrir la pantalla de inicio de sesión.  
- **Campos Requeridos**:  
  - **Username**: Nombre de usuario registrado.  
  - **Password**: Contraseña asociada al usuario.  
- **Validaciones**:  
  - El nombre de usuario y la contraseña deben coincidir con los registros en la base de datos.  
- **Mensajes**:  
  - Si el inicio de sesión es exitoso, se abrirá la ventana principal (`MainWindow`).  
  - Si hay errores, se mostrará un mensaje de error.  

![Pantalla de Login](login_form.png)

---

### 3. **Ventana Principal (MainWindow)**

Después de un inicio de sesión exitoso, la aplicación mostrará la ventana principal, que permite gestionar productos, empleados y ventas.  

#### **Gestión de Productos**  
- Haz clic en el botón "Products" para cargar y mostrar la lista de productos.  
- La tabla muestra: **ID, Nombre, Categoría, Precio y Stock**.  

![Gestión de Productos](gestion_productos.png)

#### **Gestión de Empleados**  
- Haz clic en el botón "Employs" para cargar y mostrar la lista de empleados.  
- La tabla muestra: **ID, Nombre, Cargo y Fecha de Contratación**.  

![Gestión de Empleados](gestion_empleados.png)

#### **Gestión de Ventas**  
- Haz clic en el botón "Sells" para cargar y mostrar la lista de ventas.  
- La tabla muestra: **ID, ID Empleado, ID Producto, Cantidad, Fecha de Venta y Total**.  

![Gestión de Ventas](gestion_ventas.png)

---

### 4. **Exportación de Informes**

La aplicación permite exportar los datos en formato **Excel** y **PDF**.  

#### **4.1. Generación de Reportes en Excel**  

La clase `ExcelReportGenerator` utiliza la biblioteca **Apache POI** para crear archivos Excel (`.xlsx`).  

- **Reporte de Productos**  
  - Hoja: `"Products"`, con columnas **ID, Name, Category, Price, Stock**.  
- **Reporte de Empleados**  
  - Hoja: `"Employs"`, con columnas **ID, Name, Position, Hire Date**.  
- **Reporte de Ventas**  
  - Hoja: `"Sells"`, con columnas **ID, Employ ID, Product ID, Quantity, Sell Date, Total**.  

#### **4.2. Generación de Reportes en PDF**  

La clase `PDFreportGenerator` utiliza **iTextPDF** para crear archivos PDF.  

- **Reporte de Productos**  
  - Título: `"Product Report"` con tabla **ID, Name, Category, Price, Stock**.  
- **Reporte de Empleados**  
  - Título: `"Employ Report"` con tabla **ID, Name, Position, Hire Date**.  
- **Reporte de Ventas**  
  - Título: `"Sell Report"` con tabla **ID, Employ ID, Product ID, Quantity, Sell Date, Total**.  

---

