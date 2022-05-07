package vehicles.utils;


import java.util.Date;

/**
 * Clase para hacer validaciones necesarias para el constructor de la clase vehículo
 * @author Adrián Gutiérrez
 */
public class Util {


    /**
     * Comprueba que los kilómetros son válidos
     * @param kilometros Kilometros a validar
     */
    public static void validarKilometros(int kilometros) {
        if(kilometros <= 0){
            throw new IllegalArgumentException("El kilometraje debe ser mayor a 0");
        }
    }



    /**
     * Comprobar que la fecha de matriculación es válida
     * @param fechaMatriculacion Fecha de matriculación a validar
     * @return Devuelve true si la fecha es correcta, y false si la fecha es errónea
     */
    public static boolean fechaMatriculacionIsValid(Date fechaMatriculacion){

        Date actualDate = new Date();
        if(actualDate.after(fechaMatriculacion)){return true;}
        return false;

    }

}
