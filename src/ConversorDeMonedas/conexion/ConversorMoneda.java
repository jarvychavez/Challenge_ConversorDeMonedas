package ConversorDeMonedas.conexion;
//Maneja la lógica del conversor e interactua con el usuario.

import ConversorDeMonedas.modelo.ConsultaDeMoneda;
import ConversorDeMonedas.modelo.MonedasDisponibles;

import java.util.*;

public class ConversorMoneda {
    private final Scanner teclado; // Para leer la entrada del usuario
    private final ConsultaDeMoneda consultaDeMoneda; // Clase que maneja la API
    private final List<MonedasDisponibles> conversiones;

    public ConversorMoneda(Scanner teclado) {
        this.teclado = teclado; // Inicializa el Scanner con el nombre teclado
        this.consultaDeMoneda = new ConsultaDeMoneda(); // Inicializa la clase de consulta
        this.conversiones = new ArrayList<>();
    }

    public void iniciar() {
        System.out.println("Bienvenido al Conversor de Monedas");
    }

    public void mostrarMenu() {
        System.out.println("Seleccione una opción:");
        System.out.println("1) De Dólar =>> Pesos Mexicanos");
        System.out.println("2) De Pesos Mexicanos =>> Dólar");
        System.out.println("3) De Dólar =>> Euros");
        System.out.println("4) De Euros =>> Dólar");
        System.out.println("5) De Dólar =>> Pesos Colombianos");
        System.out.println("6) De Pesos Colombianos =>> Dólar");
        System.out.println("7) De Dólar =>> pesos Argentinos");
        System.out.println("8) Mostrar conversiones realizadas");
        System.out.println("9) Salir");
    }




    public boolean procesarConversion() {
        mostrarMenu(); // Muestra el menú de opciones
        int opcion = Integer.parseInt(teclado.nextLine()); // Lee la opción del usuario

        String origen = "";
        String destino = "";
        double monto = 0;

        switch (opcion) {
            case 1:
                origen = "USD"; // Dólar
                destino = "MXN"; // Peso Mexicano
                break;
            case 2:
                origen = "MXN"; // Peso Mexicano
                destino = "USD"; // Dólar
                break;
            case 3:
                origen = "USD"; // Dólar
                destino = "EUR"; // Euro
                break;
            case 4:
                origen = "EUR"; // Euro
                destino = "USD"; // Dólar
                break;
            case 5:
                origen = "USD"; // Dólar
                destino = "COP"; // Peso Colombiano
                break;
            case 6:
                origen = "COP"; // Peso Colombiano
                destino = "USD"; // Dólar
                break;
            case 7:
                origen = "USD"; // Dólar
                destino = "ARS"; // Peso Argentino.
                break;
            case 8:
                mostrarHistorial();
                return true;
            case 9:
                System.out.println("Saliendo del conversor. ¡Hasta luego!");
                return false; // Salir del método
            default:
                System.out.println("Opción no válida.");
                return true; // Permitir que el usuario intente de nuevo
        }

        // Solicitar el monto a convertir
        monto = leerDecimal("Ingrese el valor que desea convertir: ");

        // Lógica para obtener la tasa y realizar la conversión
        try {
            Double tasa = consultaDeMoneda.obtenerTasasDeCambio(origen, destino);
            if (tasa != null ) {
                //double tasa = tasas.get(destino);
                double convertido = monto * tasa; // Realiza la conversión
                System.out.printf("El valor de: %.2f %s corresponde al valor de = %.2f %s%n", monto, origen, convertido, destino);

                MonedasDisponibles monedas = new MonedasDisponibles(origen,destino,monto,convertido);

                conversiones.add(monedas);

                GeneradorDeArchivo generador = new GeneradorDeArchivo();
                generador.guardarJson(conversiones);
            } else {
                System.out.println("No se encontró la tasa de cambio para la moneda seleccionada.");
            }
        } catch (Exception e) {
            System.out.println("Ocurrió un error al obtener las tasas de cambio: " + e.getMessage());
        }

        return true; // Permitir que el usuario intente otra conversión
    }




    private double leerDecimal(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String entrada = teclado.nextLine().trim().replace(",", ".");
            try {
                return Double.parseDouble(entrada); // Convierte la entrada a un número decimal
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Por favor, ingrese un número decimal.");
            }
        }
    }

    private void mostrarHistorial() {
        if (conversiones.isEmpty()) {
            System.out.println("No se han realizado conversiones.");
        } else {
            //Ordenar lista*****
            Collections.sort(conversiones, new Comparator<MonedasDisponibles>() {
                @Override
                public int compare(MonedasDisponibles moneda1, MonedasDisponibles moneda2) {
                    return Double.compare(moneda2.resultado(), moneda1.resultado());
                }
            });

            System.out.println("Historial de conversiones:");
            for (MonedasDisponibles moneda : conversiones) {
                System.out.printf("De %s a %s: %.2f => %.2f%n",
                        moneda.moneda_base(), moneda.moneda_cambio(),
                        moneda.conversion_base(), moneda.resultado());
            }
        }
    }
}
