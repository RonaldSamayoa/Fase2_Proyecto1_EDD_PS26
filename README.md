# Fase2_Proyecto1_EDD_PS26
SupermercadoEDD

Descripción: 
SupermercadoEDD es un sistema desarrollado en Java que implementa múltiples estructuras de datos avanzadas con el objetivo de gestionar eficientemente la información de un supermercado.

El sistema utiliza estructuras como Árbol AVL, Árbol B, Árbol B+, Tabla Hash, Grafos, Colas y Pilas para optimizar operaciones de búsqueda, inserción, organización y transferencia de productos.


Estructuras implementadas:
Árbol AVL → Búsqueda por nombre
Árbol B → Organización por fecha de caducidad
Árbol B+ → Agrupación por categoría
Tabla Hash → Acceso directo por clave
Grafo → Conexiones entre sucursales
Lista Enlazada → Estructura base de almacenamiento
Cola → Procesos FIFO
Pila → Control de operaciones (rollback)


Requisitos:
Java JDK 17 o superior
Sistema operativo:
-Windows
-Linux


Ejecución del proyecto:
El archivo ejecutable .jar se encuentra en la siguiente ruta:

/NetBeansProjects/SupermercadoEDD/target

Pasos para ejecutar:
Abrir una terminal o consola.

Navegar a la carpeta target:
cd NetBeansProjects/SupermercadoEDD/target

Ejecutar el archivo .jar:
java -jar SupermercadoEDD-1.0-SNAPSHOT.jar

Nota: El nombre del .jar puede variar dependiendo de la versión generada por Maven.


Estructura del proyecto
SupermercadoEDD/
│── src/     → Código fuente
│── target/  → Archivos compilados (.jar)
│── pom.xml    → Configuración de Maven

Funcionalidades principales:
-Gestión de productos
-Búsqueda eficiente por diferentes criterios
-Organización por categorías y fechas
-Simulación de conexiones entre sucursales
-Procesamiento de operaciones mediante colas y pilas


Ejecución en NetBeans:
Abrir el proyecto en NetBeans
Click derecho sobre el proyecto
Seleccionar Run


Autor: Ronald Samayoa
Versión 1.0


Notas adicionales
El rendimiento del sistema depende del volumen de datos y la distribución de los mismos.
Se recomienda ejecutar con una cantidad considerable de datos para apreciar la eficiencia de las estructuras implementadas.
