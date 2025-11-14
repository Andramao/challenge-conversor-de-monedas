package lad.com.alura.conversordemoneda;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Conversor {

    // Almacena los pares de conversión disponibles
    private static final String[][] PARES_CONVERSION = {
            {"USD", "COP"}, // 1. Dolar =>> Peso Colombiano
            {"COP", "USD"}, // 2. Peso Colombiano =>> Dolar
            {"EUR", "COP"}, // 3. Euro =>> Peso Colombiano
            {"COP", "EUR"}, // 4. Peso Colombiano =>> Euro
            {"MXN", "COP"}, // 5. Peso Mexicano =>> Peso Colombiano
            {"COP", "MXN"}  // 6. Peso Colombiano =>> Peso Mexicano
    };

    public static void exhibirMenu(){
        System.out.println("""
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
                """);
    }

    public static void iniciarConversor() {
        Scanner lectura = new Scanner(System.in);
        int opcion = 0;

        while (opcion != 7) {
            exhibirMenu();
            System.out.print("👉 Ingrese el número de la opción deseada: ");

            try {
                opcion = lectura.nextInt();

                if (opcion >= 1 && opcion <= 6) {
                    // La opción es válida, continuamos con la lógica de conversión
                    String monedaBase = PARES_CONVERSION[opcion - 1][0];
                    String monedaTarget = PARES_CONVERSION[opcion - 1][1];

                    System.out.printf("Ingrese la cantidad de %s a convertir: ", monedaBase);
                    double cantidad = lectura.nextDouble();

                    // Lógica principal de conversión y salida
                    realizarConversion(monedaBase, monedaTarget, cantidad);

                } else if (opcion == 7) {
                    System.out.println("👋 Gracias por usar el conversor. ¡Hasta pronto!");
                } else {
                    System.out.println("❌ Opción no válida. Intente de nuevo.");
                }

            } catch (InputMismatchException e) {
                System.out.println("❌ Entrada no válida. Por favor, ingrese un número.");
                lectura.next(); // Limpia el buffer del scanner
            } catch (IOException | InterruptedException e) {
                System.err.println("❌ Ocurrió un error en la conexión o proceso: " + e.getMessage());
            } catch (Exception e) {
                System.err.println("❌ Ocurrió un error inesperado: " + e.getMessage());
            }
        }
        lectura.close();
    }

    private static void realizarConversion(String base, String target, double cantidad)
            throws IOException, InterruptedException {

        // Obtiene el JSON completo de la API
        String jsonResponse = ConversorApp.obtenerConversionJson(base, target, cantidad);

        if (jsonResponse != null) {
            // Extrae el resultado
            double resultado = ConversorApp.extraerValorConvertido(jsonResponse);

            // 3. Muestra el resultado al usuario
            System.out.printf("\n💰 %.2f %s Equivale a: %.2f %s\n\n",
                    cantidad, base, resultado, target);

            // 4. Guarda el JSON en un archivo
            String nombreArchivo = String.format("conversion_%s_a_%s.json", base, target);
            ConversorApp.guardarJson(jsonResponse, nombreArchivo, cantidad);
        }
    }
}
