# gestion-cuentas
Proyecto para la gestion de cuentas bancarias
SpringBoot + Maven + H2

# Instrucciones para construir el proyecto

a. El proyecto funciona con JDK versión 1.8

b. clonar el proyecto y abrirlo en el ide de desarrollo.

c. Compilación: mvn instalación limpia.

# SQL

URL de JDCB: jdbc:h2:mem:banco

Usuario: root
Contraseña: ro

# DOCKERFILE

docker build -t banco-neoris .
	
docker run -p 8080:8080 banco-neoris



