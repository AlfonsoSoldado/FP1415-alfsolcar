package fp.grados.tipos;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import fp.grados.excepciones.ExcepcionProfesorNoValido;
import fp.grados.excepciones.ExcepcionProfesorOperacionNoPermitida;

public class ProfesorImpl extends PersonaImpl implements Profesor {

	private Categoria categoria;
	private SortedSet<Tutoria> tutorias;
	private Departamento departamento;
	private List<Asignatura> asignaturas;
	private List<Double> creditos;	
	private static final Double MAX_CREDITOS = 24.;
	
	public ProfesorImpl(String dni, String nombre, String apellidos, LocalDate fechaNacimiento, String email, Categoria categoria, Departamento departamento){
		super(dni, nombre, apellidos, fechaNacimiento, email);
		checkEdad(getEdad());
		this.categoria = categoria;
		this.tutorias = new TreeSet<Tutoria>();
		this.asignaturas = new ArrayList<Asignatura>();
		this.creditos = new ArrayList<Double>();
		
		//Delego la inicialización en el set
		setDepartamento(departamento);
	}
	
	private static void checkEdad(Integer edad) {
		if(edad < 18){
			throw new ExcepcionProfesorNoValido("El profesor debe ser mayor de edad");
		}
	}
	

//	private void checkAsignatura(Asignatura asig){
//		if(!(getDepartamento().equals(asig.getDepartamento()))){
//			throw new ExcepcionProfesorOperacionNoPermitida();
//		}
//	}
	
	private void checkDedicacion(Double dedicacion, Double numeroCreditosAsignaturas){
		if(dedicacion < 0 || dedicacion > numeroCreditosAsignaturas){
			throw new ExcepcionProfesorOperacionNoPermitida();
		}
	}

	private void checkDedicacionTotal(List<Double> creditos){		
		Double acum = 0.;
		for(Double d: creditos){
			acum = acum + d;
		}
		if(acum > MAX_CREDITOS){
			throw new ExcepcionProfesorOperacionNoPermitida();
		}
	}
	
	public Categoria getCategoria() {
		return categoria;
	}

	public SortedSet<Tutoria> getTutorias() {
		return new TreeSet<Tutoria>(tutorias);
	}

	public Departamento getDepartamento(){
		return departamento;
	}
	
	public List<Asignatura> getAsignaturas(){
		return new ArrayList<Asignatura>(asignaturas);
	}
	
	public List<Double> getCreditos(){
		return new ArrayList<Double>(creditos);
	}
	
	public Double getDedicacionTotal(){
		Double res = 0.;
		for(Double d: creditos){
			res = res + d;
		}
		return res;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
		
	}
	
	public void setFechaNacimiento(LocalDate fechaNacimiento){
		super.setFechaNacimiento(fechaNacimiento);
		checkEdad(this.getEdad());
	}

	//ZONAS BIDIRECCIONALES:
	//Constructores
	//Set
	//Métodos que necesites actualizar
	
	public void setDepartamento(Departamento nuevoDepartamento){
		//1. Tomar elemento actual que se va a cambiar
		Departamento viejoDepartamento = this.departamento;
		
		//2. Chequear identidad(Lo nuevo debe ser distinto)
		if(nuevoDepartamento != viejoDepartamento){
			//3. Actualizar la propiedad con el nuevo valor
			this.departamento = nuevoDepartamento;
			
			//4. Eliminarme objeto único al que pertenecía
			if(viejoDepartamento != null){
				viejoDepartamento.eliminaProfesor(this);
			}
			
			//5. Añadirme el nuevo objeto único al que pertenece
			if(nuevoDepartamento != null){
				nuevoDepartamento.nuevoProfesor(this);
			}
		}
	}

	public void nuevaTutoria(LocalTime horaComienzo, Integer duracionMinutos, DayOfWeek dia) {
		Tutoria tutoria = new TutoriaImpl(dia, horaComienzo, duracionMinutos);
		tutorias.add(tutoria);
	}

	public void borraTutoria(LocalTime horaComienzo, DayOfWeek dia) {
		Tutoria tutoria = new TutoriaImpl(dia, horaComienzo, 15);
		tutorias.remove(tutoria);
	}

	public void borraTutorias() {
		tutorias.removeAll(tutorias);
	}
	
	/*
	 * Añade la asignatura asig a las asignaturas que imparte el profesor, 
	 * siendo dedicacion el número de créditos que imparte el profesor en dicha 
	 * asignatura. Si la asignatura ya era impartida por el profesor, se actualiza la dedicación. 
	 * Vigile que se respeten las restricciones, y en caso contrario lance la excepción
	 * ExcepcionProfesorOperacionNoPermitida.
	 */
	public void imparteAsignatura(Asignatura asig, Double dedicacion){
//		checkAsignatura(asig);
		checkDedicacion(dedicacion, asig.getCreditos());
		
		Integer index = getAsignaturas().indexOf(asig);
		
		if(index.equals(-1)){
			asignaturas.add(asig);
			creditos.add(dedicacion);
		} else {
			creditos.set(index, dedicacion);
		}
		
		checkDedicacionTotal(getCreditos());
	}
	
	/*
	 * Devuelve el número de créditos que imparte el profesor en la asignatura asig. 
	 * Si el profesor no imparte la asignatura, devuelve 0.0.
	 */
	public Double dedicacionAsignatura(Asignatura asig){
		Double res = 0.;
		
		Integer index = getAsignaturas().indexOf(asig);
		if(!(index.equals(-1))){
			res = getCreditos().get(index);
		}
		return res;
	}
	
	public void eliminaAsignatura(Asignatura asig){
		Integer index = asignaturas.indexOf(asig);
		if(!(index.equals(-1))){ 
			asignaturas.remove(asig);
			creditos.remove(index);
		}
	}
	
	public String toString(){
		return super.toString() + " ("+getCategoria()+")";
	}

}
