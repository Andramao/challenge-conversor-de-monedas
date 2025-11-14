# challenge-conversor-de-monedas
💰 Conversor de Monedas Java - Challenge Alura
Este proyecto es una aplicación de consola en Java desarrollada como parte del Challenge ONE de Alura Latam, que permite a los usuarios convertir cantidades entre 6 pares de monedas utilizando tasas de cambio en tiempo real proporcionadas por la API de ExchangeRate-API.

✨ Características
Tasas en Tiempo Real: Utiliza la API de exchangerate-api.com para obtener la tasa de conversión más reciente.

6 Pares de Conversión: Ofrece un menú de las conversiones más comunes entre monedas latinoamericanas y globales (USD, EUR, COP, MXN).

Modernidad: Implementado con el HttpClient de Java para una comunicación de red.

Manipulación de JSON: Utiliza la librería Gson para procesar la respuesta de la API y estructurar los datos de salida.

Salida de Archivo: Genera un archivo JSON por cada conversión realizada, incluyendo la cantidad original y el resultado final.

Manejo de Errores: Incluye manejo de excepciones para errores de usuario y fallos de conexión a la API.

⚙️ Tecnologías Utilizadas
Lenguaje: Java 17

Comunicación HTTP: java.net.http.HttpClient

JSON: Gson (Google Library)

🚀 Cómo Empezar
1. Requisitos Previos
Asegúrate de tener instalado:

JDK (Java Development Kit) versión 11 o superior.

Un IDE como IntelliJ IDEA o Eclipse.

2. Configuración de la API Key
La aplicación requiere una clave de API gratuita de ExchangeRate-API.com.

Regístrate en ExchangeRate-API.

Obtén tu clave API (es una cadena de 32 caracteres alfanuméricos).

Abre el archivo ConversorApp.java y reemplaza el valor de la variable API_KEY con tu propia clave:

Java

private static final String API_KEY = "TU_CLAVE_API_AQUI"; // Ejemplo: "762128efb8d0fcaac1f0a12f"
3. Agregar Dependencia de Gson
Agrega la dependencia de Gson a tu proyecto.

📋 Guía de Uso
Para iniciar el conversor, simplemente ejecuta el método main ubicado en la clase ConversorApp.java.

Menú Principal
Al iniciar la aplicación, se mostrará el siguiente menú:

**********************************************
Este es su conversor de Monedas
                
1. Dolar =>> Peso Colombiano
2. Peso Colombiano =>> Dolar
3. Euro =>> Peso Colombiano
4. Peso Colombiano =>> Euro
5. Peso Mexicano =>> Peso Colombiano
6. Peso Colombiano =>> Peso Mexicano
7. Salir
**********************************************
👉 Ingrese el número de la opción deseada:
Pasos
Selección: Ingresa el número de la conversión que deseas realizar (del 1 al 6).

Cantidad: Ingresa la cantidad de la moneda de origen que deseas convertir (ej. 250.50).

Resultado en Pantalla: La aplicación mostrará inmediatamente el resultado de la conversión.

Archivo de Salida: Se generará un archivo JSON en la raíz del proyecto con el formato conversion_[BASE]_a_[TARGET].json (Ejemplo: conversion_EUR_a_COP.json).

Ejemplo de Salida JSON
JSON

{
  "result": "success",
  "base_code": "EUR",
  "target_code": "COP",
  "conversion_rate": 4200.00,
  "conversion_result": 1050000.00,
  "cantidad_original_ingresada": "250.0"
}
🐛 Manejo de Errores Comunes
Error al obtener la tasa. Código de estado: 404: La URL de la API es incorrecta. Verifica:

Que la API_KEY en ConversorApp.java sea correcta.

Que el método obtenerConversionJson utilice Locale.US para el formato decimal, ya que la API no acepta comas (,) como separador decimal.

❌ Entrada no válida. Por favor, ingrese un número.: El usuario ingresó texto en lugar de un número en el menú o al ingresar la cantidad.
