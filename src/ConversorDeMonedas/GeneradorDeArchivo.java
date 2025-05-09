package ConversorDeMonedas;

// Guardar los resultados en archivo JSON.
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class GeneradorDeArchivo {

    public void guardarJson(List<MonedasDisponibles> moneda) throws IOException {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        //Generar archivo
        try (FileWriter escritura = new FileWriter("Conversion.json")) {
            escritura.write(gson.toJson(moneda));
            System.out.println("El archivo Conversion.json se ha guardado correctamente.");
        } catch (IOException e) {
            System.err.println("Error al guardar el archivo: " + e.getMessage());
        }
    }
}