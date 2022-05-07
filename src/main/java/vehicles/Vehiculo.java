package vehicles;

import vehicles.utils.DNI;
import vehicles.utils.Util;
import java.time.*;

/**
 * Vehículo es una clase, es decir, un molde para crear vehículos.
 * @author Adrián Gutiérrez
 */
public class Vehiculo {
    private String marca;
    private String matricula;
    private int numeroDeKilometros;
    private LocalDate fechaDeMatriculacion;
    private String descripcion;
    private float precio;
    private String nombreDelPropietario;
    private String nif;

    /**
     * Crea un vehículo recibiendo los siguientes parámetros:
     * @param marca Marca del vehículo
     * @param matricula Matrícula del vehículo
     * @param numeroDeKilometros Kilometraje del vehículo
     * @param descripcion Descripción del vehículo
     * @param precio Precio de venta del vehículo
     * @param nombreDelPropietario Nombre del propietario actual
     * @param nif NIF del propietario actual
     */
    public Vehiculo(String marca, String matricula, int numeroDeKilometros, LocalDate fechaDeMatriculacion,
                    String descripcion, float precio, String nombreDelPropietario, String nif){
        this.marca = marca;
        this.matricula = matricula;

        Util.validarKilometros(numeroDeKilometros);
        this.numeroDeKilometros = numeroDeKilometros;

        this.fechaDeMatriculacion = fechaDeMatriculacion;

        this.descripcion = descripcion;
        this.precio = precio;
        this.nombreDelPropietario = nombreDelPropietario;

        DNI.validarNIF(nif);
        this.nif = nif;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public LocalDate getFechaDeMatriculacion() {
        return fechaDeMatriculacion;
    }

    public void setFechaDeMatriculacion(LocalDate fechaDeMatriculacion) {
        this.fechaDeMatriculacion = fechaDeMatriculacion;
    }

    /**
     * Devuelve la matrícula del vehículo
     * @return Devuelve la matrícula del vehículo
     */
    public String getMatricula(){
        return matricula;
    }

    /**
     * Devuelve el kilometraje del vehículo
     * @return Devuelve el kilometraje del vehículo
     */
    public int getNumeroDeKilometros(){
        return numeroDeKilometros;
    }


    /**
     * Actualiza el número de kilometros
     * @param numeroDeKilometros Es el nuevo número de kilómetros
     */
    public void setNumeroDeKilometros(int numeroDeKilometros){
        this.numeroDeKilometros = numeroDeKilometros;
    }

    /**
     * Devuelve la descripción del vehículo
     * @return Devuelve la descripción del vehículo
     */
    public String getDescripcion(){
        return descripcion;
    }

    /**
     * Devuelve el nombre del propietario
     * @return Devuelve el nombre del propietario
     */
    public String getNombreDelPropietario(){
        return nombreDelPropietario;
    }

    /**
     * Devuelve el nif del propietario
     * @return Devuelve el nif del propietario
     */
    public String getNif(){
        return nif;
    }

    /**
     * Devuelve el precio del vehículo
     * @return Devuelve el precio del vehículo
     */
    public float getPrecio(){
        return precio;
    }

    /**
     * Calcula la antigüedad del vehículo en años, contando desde el día en el que
     * se matriculó el vehículo hasta la actualidad.
     * @param anioMatriculacion Año en el que se matriculó el vehículo
     * @return Años de antigüedad del vehículo
     */
    public int getAnios(int anioMatriculacion){
        int year = Year.now().getValue();
        int anios = year - anioMatriculacion;
        return anios;
    }




}
