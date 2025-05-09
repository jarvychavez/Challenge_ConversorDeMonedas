package ConversorDeMonedas;
//Clase que conecta la API para la tasa de cambio.
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class ConsultaDeMoneda {



    public Map<String, Double> obtenerTasasDeCambio(String origen) {
        String API_URL = "https://v6.exchangerate-api.com/v6/f31bab78d50dbaf55174be8c/latest/"+origen;

        URI direccion = URI.create(API_URL);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(direccion)
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                // Convertir la respuesta JSON a un objeto
                Gson gson = new Gson();
                JsonObject jsonResponse = gson.fromJson(response.body(), JsonObject.class);
                JsonObject conversionRates = jsonResponse.getAsJsonObject("conversion_rates");

                // Crear un mapa de tasas de conversión
                Map<String, Double> tasas = new HashMap<>();
                for (String key : conversionRates.keySet()) {
                    tasas.put(key, conversionRates.get(key).getAsDouble());
                }
                return tasas; // Retorna el mapa de tasas de conversión
            } else {
                System.out.println("Error en la conexión: " + response.statusCode());
            }
        } catch (Exception e) {
            System.out.println("Ocurrió un error al obtener las tasas de cambio: " + e.getMessage());
        }
        return null; // Retorna null si hay un error
    }
}