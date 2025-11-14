package lad.com.alura.conversordemoneda;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Locale;

public class ConversorApp {

    // API Key de exchangerate-api.com
    // Variable de entorno archivo de propiedades para claves
    private static final String API_KEY = "762128efb8d0fcaac1f0a12f";

    public static void main(String[] args) throws IOException, InterruptedException {
        // Ejecuta el menú y la lógica de conversión
        Conversor.iniciarConversor();
    }

        /**
         * Realiza la solicitud a la API y obtiene el JSON de respuesta.
         * @param monedaBase La moneda de origen (Ej: "USD").
         * @param monedaTarget La moneda de destino (Ej: "COP").
         * @param cantidad La cantidad a convertir.
         * @return El JSON de respuesta de la API como String.
         * @throws IOException
         * @throws InterruptedException
         */


        // Se Crea la URL para el par y la cantidad específicos
        public static String obtenerConversionJson (String monedaBase, String monedaTarget,double cantidad)
                                           throws IOException, InterruptedException {

            // Se Usa Locale.US para garantizar que el decimal sea un punto (.)
            String urlApi = String.format(Locale.US,
                    "https://v6.exchangerate-api.com/v6/%s/pair/%s/%s/%.2f",
                    API_KEY, monedaBase, monedaTarget, cantidad);
            System.out.println("URL de la API: " + urlApi); // <<< Añade esto para depurar

            // Se Crea cliente HTTP y la solicitud (Request)
            HttpClient cliente = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(urlApi))
                    .build();

            // Obteniendo la respuesta (Response)
            HttpResponse<String> response = cliente
                    .send(request, HttpResponse.BodyHandlers.ofString());

            // Manejo básico de errores de la API
            if (response.statusCode() != 200) {
                System.err.println("Error al obtener la tasa. Código de estado: " + response.statusCode());
                System.err.println("Cuerpo de la respuesta: " + response.body());
                return null;
            }

            return response.body();
        }

        /**
         * Extrae el valor de la conversión del JSON de respuesta.
         * @param jsonResponse El JSON de respuesta de la API.
         * @return El valor convertido.
         */
        public static double extraerValorConvertido (String jsonResponse){
            if (jsonResponse == null || jsonResponse.isEmpty()) {
                return 0.0;
            }

            // Conversión a JSON usando Gson
            JsonObject objectRoot = JsonParser.parseString(jsonResponse).getAsJsonObject();

            // Accediendo al valor de conversión
            double valorConvertido = objectRoot.get("conversion_result").getAsDouble();
            return valorConvertido;
        }

        /**
         * Guarda el JSON de la conversión en un archivo.
         * @param jsonResponse El JSON de respuesta a guardar.
         * @param nombreArchivo El nombre del archivo de salida.
         */
        public static void guardarJson (String jsonResponse, String nombreArchivo, double cantidadOriginal){
            if (jsonResponse == null || jsonResponse.isEmpty()) {
                System.err.println("No se puede guardar JSON vacío o nulo.");
                return;
            }
            try {
                // Parser para obtener el objeto JSON
                JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();

                // Añadir campo de cantidadOriginal a convertir
                // Convertir el double a BigDecimal y luego a String para evitar la notación científica.
                BigDecimal bd = new BigDecimal(cantidadOriginal);

                // Se Usa bd.toPlainString() para obtener el formato sin notación E
                jsonObject.addProperty("cantidad_original_ingresada", bd.toPlainString());

                // Se Usa GsonBuilder para dar formato bonito al JSON de salida
                Gson gson = new GsonBuilder().setPrettyPrinting().create();

                // Escribe el JSON formateado al archivo
                FileWriter escritura = new FileWriter(nombreArchivo);
                gson.toJson(jsonObject, escritura);
                escritura.close();

                System.out.println("✅ El resultado de la conversión se guardó en: " + nombreArchivo);
            } catch (IOException e) {
                System.err.println("Error al guardar el archivo JSON: " + e.getMessage());
            }
        }
    }
