package com.granja.controlador;

import com.granja.modelo.*;
import com.granja.negocio.*;
import com.granja.servicio.OperacionesCrud;
import com.granja.utilitario.GranjaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.ArrayList;

@Component
//Esta clase es un bean.
//Créala automáticamente y guárdala en el contenedor de Spring
public class GranjaControlador {
    private final GestorGranja gestorGranja;
    private final OperacionesCrud operacionesCrud;

    //spring inyecta los objetos automáticamente
    //Se le dice al GestorGranja qué servicio usar para guardar datos
    @Autowired
    //Buscá un objeto compatible y inyectalo acá automáticamente
    public GranjaControlador(GestorGranja gestorGranja, OperacionesCrud operacionesCrud) {
        this.gestorGranja = gestorGranja;
        this.operacionesCrud = operacionesCrud;
        gestorGranja.setPersistenciaService(operacionesCrud);
    }
    //Devuelve todos los usuarios
    public ArrayList<Usuario> obtenerUsuarios() {
        return gestorGranja.getGestorUsuarios().getUsuarios();
    }
    //Devuelve solo los usuarios activos
    public ArrayList<Usuario> obtenerUsuariosActivos() {
        return gestorGranja.getGestorUsuarios().getUsuariosActivos();
    }
    //Selecciona un usuario como usuario en sesión
    public void seleccionarUsuarioActual(String idUsuario) throws GranjaException {
        gestorGranja.getGestorUsuarios().seleccionarUsuarioActual(idUsuario);
    }
    //Devuelve el usuario actualmente logueado
    public Usuario obtenerUsuarioActual() {
        return gestorGranja.getGestorUsuarios().getUsuarioActual();
    }
    //Crea un nuevo usuario
    public boolean agregarUsuario(String nombre, String apellido, String email, String telefono, String rol) {
        return gestorGranja.getGestorUsuarios().agregarUsuario(nombre, apellido, email, telefono, rol);
    }
    //Cierra la sesión del usuario actual
    public void cerrarSesionUsuario() {
        gestorGranja.getGestorUsuarios().cerrarSesion();
    }
    //Divide el terreno total en parcelas
    public void crearParcelas(double terrenoTotal) throws GranjaException {
        gestorGranja.getGestorParcelas().crearParcelas(terrenoTotal);
    }
    //Devuelve todas las parcelas creadas
    public ArrayList<Parcela> obtenerParcelas() {
        return gestorGranja.getParcelas();
    }

    public ArrayList<Aspersor> obtenerAspersoresInventario() {
        return gestorGranja.getAspersoresInventario();
    }

    public ArrayList<SensorHumedad> obtenerSensoresInventario() {
        return gestorGranja.getSensoresInventario();
    }
    //Aspersores del inventario
    public ArrayList<Aspersor> obtenerTodosAspersores() {
        ArrayList<Aspersor> todos = new ArrayList<>(gestorGranja.getAspersoresInventario());
        for (Parcela parcela : gestorGranja.getParcelas()) {
            todos.addAll(parcela.getAspersores());
        }
        return todos;
    }
    //Sensores del inventario
    public ArrayList<SensorHumedad> obtenerTodosSensores() {
        ArrayList<SensorHumedad> todos = new ArrayList<>(gestorGranja.getSensoresInventario());
        for (Parcela parcela : gestorGranja.getParcelas()) {
            todos.addAll(parcela.getSensores());
        }
        return todos;
    }
    //Agrega aspersores al inventario
    public void agregarAspersoresInventario(int cantidad) {
        gestorGranja.getGestorAspersores().agregarAspersoresInventario(cantidad);
    }

    public void agregarSensoresInventario(int cantidad) {
        gestorGranja.getGestorSensores().agregarSensoresInventario(cantidad);
    }
    //Asigna un aspersor cualquiera a una parcela
    public void asignarAspersorAParcela(String idParcela) throws GranjaException {
        gestorGranja.getGestorAspersores().asignarAspersorAParcela(idParcela);
    }
    //Asigna un aspersor específico
    public void asignarAspersorEspecificoAParcela(String idAspersor, String idParcela) throws GranjaException {
        gestorGranja.getGestorAspersores().asignarAspersorEspecificoAParcela(idAspersor, idParcela);
    }

    public void asignarSensorAParcela(String idParcela) throws GranjaException {
        gestorGranja.getGestorSensores().asignarSensorAParcela(idParcela);
    }

    public void asignarSensorEspecificoAParcela(String idSensor, String idParcela) throws GranjaException {
        gestorGranja.getGestorSensores().asignarSensorEspecificoAParcela(idSensor, idParcela);
    }

    public ArrayList<Cultivo> obtenerCultivosDisponibles() {
        return gestorGranja.getGestorCultivos().getCultivosDisponibles();
    }

    public void registrarCultivoEnParcela(String idParcela, String nombreCultivo) throws GranjaException {
        gestorGranja.getGestorCultivos().registrarCultivoEnParcela(idParcela, nombreCultivo);
    }

    public void cambiarCultivoParcela(String idParcela, String nombreCultivo) throws GranjaException {
        gestorGranja.getGestorCultivos().cambiarCultivoParcela(idParcela, nombreCultivo);
    }
    //Elimina una parcela específica
    public void eliminarParcela(String idParcela) throws GranjaException {
        gestorGranja.getGestorParcelas().eliminarParcela(idParcela);
    }

    public void simularLecturasYRiego() {
        gestorGranja.getGestorSensores().simularLecturasTodasParcelas();
        gestorGranja.getGestorAspersores().realizarRiegoAutomatico();
    }

    public void prenderAspersorManualmente(String idAspersor) throws GranjaException {
        gestorGranja.getGestorAspersores().prenderManualmente(idAspersor);
    }

    public void conectarDesconectarAspersor(String idAspersor) throws GranjaException {
        gestorGranja.getGestorAspersores().conectarDesconectarAspersor(idAspersor);
    }

    public void conectarDesconectarSensor(String idSensor) throws GranjaException {
        gestorGranja.getGestorSensores().conectarDesconectarSensor(idSensor);
    }

    public void eliminarAspersor(String idAspersor) throws GranjaException {
        gestorGranja.getGestorAspersores().eliminarAspersor(idAspersor);
    }

    public void eliminarSensor(String idSensor) throws GranjaException {
        gestorGranja.getGestorSensores().eliminarSensor(idSensor);
    }
    //Busca una parcela por ID
    public Parcela buscarParcela(String idParcela) {
        return gestorGranja.getGestorParcelas().buscarParcela(idParcela);
    }
}