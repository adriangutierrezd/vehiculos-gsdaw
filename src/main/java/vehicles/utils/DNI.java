package vehicles.utils;

/**
 * DNI es una clase que permite validar si un DNI es correcto.
 * @author Adrián Gutiérrez de la Osa
 */
public class DNI {

    private int numDNI;
    private static final String LETRAS_DNI= "TRWAGMYFPDXBNJZSQVHLCKE";

    /**
     * Calcula la letra que correspondería a un número de DNI concreto
     * @param dni Número de DNI del que hay que obtener la letra
     * @return Letra correspondiente al DNI
     */
    public static char calcularLetraNIF (int dni) {

        char letra;
        // Cálculo de la letra NIF
        letra= LETRAS_DNI.charAt(dni % 23);

        // Devolución de la letra NIF
        return letra;
    }

    /**
     * Obtiene la letra de un NIF completo (con número y letra)
     * @param nif NIF del que hay que obtener su letra
     * @return Letra del NIF
     */
    private static char extraerLetraNIF (String nif) {

        char letra=   nif.charAt(nif.length()-1);

        return letra;

    }

    /**
     * Obtiene el número de un NIF completo (con número y letra)
     * @param nif NIF del que hay que obtener su número
     * @return Número del NIF
     */
    private static int extraerNumeroNIF (String nif) {

        int numero= Integer.parseInt(nif.substring(0, nif.length()-1));

        return numero;

    }

    /**
     * Comprueba si un NIF dado es válido o no en función de su tamaño y letra asignada
     * @param nif NIF a validar
     * @return true si el NIF es válido y false si no lo es
     */
    public static boolean validarNIF (String nif) {

        boolean valido= true;   // Suponemos el NIF válido mientras no se encuentre algún fallo
        char letra_calculada;
        char letra_leida;
        int  dni_leido;

        if (nif == null) {  // El parámetro debe ser un objeto no vacío

            valido= false;

        }else if (nif.length()<8 || nif.length()>9) {    // La cadena debe estar entre 8(7+1) y 9(8+1) caracteres

            valido= false;

        } else {

            letra_leida= DNI.extraerLetraNIF (nif);    // Extraemos la letra de NIF (letra)

            dni_leido= DNI.extraerNumeroNIF (nif);  // Extraemos el número de DNI (int)

            letra_calculada= DNI.calcularLetraNIF(dni_leido);  // Calculamos la letra de NIF a partir del número extraído

            if (letra_leida == letra_calculada) {   // Comparamos la letra extraída con la calculada

                // Todas las comprobaciones han resultado válidas. El NIF es válido.

                valido= true;

            }else {
                valido= false;
            }
        }

        return valido;

    }

}
