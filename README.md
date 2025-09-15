# OSREPORTE-BACKEND

# Preparación de la Base de Datos MySQL

## 1. Levantar los servicios con Docker

Asegúrate de tener Docker instalado. En la raíz del proyecto ejecuta:

```powershell
docker-compose up -d
```

Esto iniciará dos servicios:
- **MySQL** (puerto 3306)
- **phpMyAdmin** (puerto 80)

## 2. Acceso a phpMyAdmin

Abre tu navegador y accede a:

```
http://localhost:80
```

- **Servidor:** mysql
- **Usuario:** root
- **Contraseña:** fherrera

## 3. Creación de las bases de datos y usuario

Primero, crea las bases de datos con el encoding utf8mb3 y collation utf8mb3_general_ci. Puedes ejecutar el siguiente SQL desde la pestaña "SQL":

```sql
CREATE DATABASE osticket_test CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci;
CREATE DATABASE osticket_reportes_test CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci;
```

Luego, crea el usuario `fherrera` con contraseña `fherrera` y otórgale todos los privilegios sobre las bases de datos creadas:

```sql
CREATE USER 'fherrera'@'%' IDENTIFIED BY 'fherrera';
GRANT ALL PRIVILEGES ON osticket_test.* TO 'fherrera'@'%';
GRANT ALL PRIVILEGES ON osticket_reportes_test.* TO 'fherrera'@'%';
FLUSH PRIVILEGES;
```

- `osticket_test`
- `osticket_reportes_test`

## 4. Cargar la estructura de la base de datos

En la sección "Importar" de phpMyAdmin, selecciona la base de datos correspondiente y carga los scripts SQL:

- Para la estructura principal de osticket:  
  Importa el archivo `db/osticket/01_osticket_db.sql` en la base de datos `osticket_test`.

- Para datos adicionales o de staff:  
  Importa el archivo `db/osticket/02_ost_staf_data.sql` en la base de datos que corresponda (según instrucciones del proyecto).

## 5. Configuración de conexión en la aplicación

Verifica que el archivo `src/main/resources/application.yml` tenga las credenciales y URLs correctas para las bases de datos creadas.

---

Con estos pasos tendrás la base de datos lista para el backend del proyecto.

