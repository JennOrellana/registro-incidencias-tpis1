# Registro de Incidencias

## Propósito
Esta aplicación está diseñada para registrar, clasificar y realizar el seguimiento de incidencias técnicas en un entorno de producción de software, asegurando un control eficiente de los problemas reportados.

## Herramientas Utilizadas
* **IDE:** Android Studio
* **Lenguaje:** Kotlin
* **Diseño de Interfaz:** Jetpack Compose
* **Control de Versiones:** Git y GitHub

## Estado Actual
* **Implementado:** Proyecto base estructurado, pantalla inicial funcional desarrollada durante la Semana 2.
* **Pendiente:** Integración de eventos de interfaz, gestión del ciclo de vida y persistencia de datos en las próximas unidades.

## Cómo abrir el proyecto
1. Clonar o descargar este repositorio.
2. Abrir Android Studio.
3. Seleccionar **File > Open** y buscar la carpeta del proyecto.
4. Esperar a que Gradle sincronice los archivos y ejecutar en un emulador o dispositivo físico.

## Identificación Académica
* **Universidad:** Universidad Tecnológica de El Salvador (UTEC)
* **Materia:** Técnicas de Producción Industrial de Software I · Ciclo 02-2026
* **Estudiante:** Jenny Portillo

## Avance Semana 06: Interacción y Estado en Compose
* **Implementación de Estado:** Se utilizaron los delegados `remember` y `mutableStateOf` para crear variables de memoria capaces de retener datos durante la recomposición de la interfaz.
* **Componentes Interactivos:** Se migraron los textos estáticos a elementos de entrada de datos `OutlinedTextField` para capturar el título y la descripción de las incidencias de forma reactiva.
* **Manejo de Eventos:** Se programó el evento `onClick` del componente `Button` para capturar la información del estado local y proyectar una retroalimentación dinámica en pantalla.

## Avance Semana 07: Primer Avance de Investigación
* **Entregable:** Documentación conceptual de la necesidad, definición del usuario meta (Estudiantes UTEC) y especificación de los 4 requerimientos iniciales del sistema móvil.
* **Estado Técnico:** Pantalla interactiva completamente funcional verificada bajo emulación en tiempo real.

## Avance Semana 10: Teclado y Pantalla Táctica en RegistroIncidencias
* **Teclado Contextual:** Se implementaron opciones avanzadas de teclado (`KeyboardOptions`) en el campo de texto de Título, configurando la capitalización automática de oraciones (`KeyboardCapitalization.Sentences`) y un botón de acción IME dinámico (`ImeAction.Next`) para agilizar el flujo de navegación del usuario.
* **Interacción Táctil Integrada:** Se diseñó un selector de prioridades interactivo (Alta, Media, Baja) utilizando componentes de alto nivel `Card` y modificadores de contacto directo (`Modifier.clickable`), evitando sobrecargar el sistema con gestos complejos innecesarios.
* **Retroalimentación de Estado:** Se vinculó el estado reactivo de Compose para modificar en tiempo real el color de fondo de la prioridad seleccionada y proyectar un mensaje descriptivo inmediato en la base del formulario, confirmando al usuario la persistencia visual de sus elecciones táctiles.
