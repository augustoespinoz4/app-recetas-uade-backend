**🧾 Configuración del Proyecto Yummly App (Backend)**
Este proyecto está desarrollado en Spring Boot y utiliza una base de datos SQL Server.
Se requiere configurar algunas propiedades sensibles en un archivo separado no versionado, para poder ejecutarlo correctamente de forma local.

**📦 Requisitos**
Java 17 o superior
Maven
Base de datos SQL Server local o remota
Cuenta de Gmail para enviar correos (opcional pero recomendable)

**📁 Archivos de configuración**
✅ application.properties
Este archivo es público y contiene configuraciones generales como el perfil activo:

spring.application.name=yummly_app
spring.profiles.active=local

**🔒 application-local.properties**
Este archivo contiene credenciales sensibles (base de datos y correo).
⚠️ No se sube al repositorio. En su lugar, usá el archivo de ejemplo para crear el tuyo.

**Ruta esperada:**
src/main/resources/application-local.properties

**📄 Ejemplo del archivo application-local.properties**
Configuración de la base de datos
spring.datasource.url=jdbc:sqlserver://<HOST>:<PUERTO>;databaseName=Yummly_Recetas_DB;encrypt=true;trustServerCertificate=true
spring.datasource.username=TU_USUARIO
spring.datasource.password=TU_CONTRASEÑA

**Configuraciones de Hibernate (opcional)**
spring.jpa.properties.hibernate.show_sql=true
spring.jpa.properties.hibernate.format_sql=true

**Configuración del servidor SMTP para emails**
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=TU_EMAIL@gmail.com
spring.mail.password=TU_CONTRASEÑA_DEL_EMAIL

**Seguridad del mail**
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true

**Podés usar el archivo de ejemplo incluido en:**
src/main/resources/application-local.properties.example
Copialo y renombralo como application-local.properties.

**🧪 ¿Cómo correr el proyecto?**
Cloná el repositorio desde GitHub:
git clone https://github.com/augustoespinoz4/app-recetas-uade-backend.git
Asegurate de tener tu base de datos corriendo.
Configurá tu archivo application-local.properties con tus credenciales.

Ejecutá el proyecto desde tu IDE o con Maven:
./mvnw spring-boot:run
