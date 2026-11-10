package com.udea.reporteventas.servicio;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;

public class ConexionGemini {

    public String generarReporte(String prompt) {
        String apiKey = System.getenv("GOOGLE_API_KEY");

        if (apiKey == null || apiKey.isBlank()) {
            throw new IllegalStateException(
                    "No se encontró GOOGLE_API_KEY. Configure la variable de entorno antes de ejecutar.");
        }

        // La clave se lee desde la variable de entorno, no está escrita en el código.
        try (Client cliente = Client.builder().apiKey(apiKey).build()) {
            GenerateContentResponse respuesta = cliente.models.generateContent(
                    "gemini-flash-latest",
                    prompt,
                    null
            );

            String texto = respuesta.text();
            if (texto == null || texto.isBlank()) {
                throw new IllegalStateException("Gemini no devolvió texto.");
            }
            return texto;
        }
    }
}
