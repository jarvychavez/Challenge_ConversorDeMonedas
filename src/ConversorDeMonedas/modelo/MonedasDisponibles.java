package ConversorDeMonedas.modelo;
//Representa la estructura de datos para las monedas.

public record MonedasDisponibles(String moneda_base,
                                 String moneda_cambio,
                                 Double conversion_base,
                                 Double resultado) {
}