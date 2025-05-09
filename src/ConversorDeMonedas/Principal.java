package ConversorDeMonedas;
//Clase que continene el método main para ejecutar el programa.

import java.util.Scanner;

public class Principal {
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in); // Inicializa el Scanner
        ConversorMoneda conversor = new ConversorMoneda(teclado); // Crea una instancia del conversor

        conversor.iniciar(); // Muestra el mensaje de bienvenida

        boolean continuar = true;
        while (continuar) {
            continuar = conversor.procesarConversion(); // Procesa la conversión y obtiene si continuar
        }

        teclado.close(); // Cierra el Scanner al finalizar
    }
}
