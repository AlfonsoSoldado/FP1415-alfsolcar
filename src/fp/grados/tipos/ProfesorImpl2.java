package fp.grados.tipos;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import fp.grados.excepciones.ExcepcionProfesorNoValido;
import fp.grados.excepciones.ExcepcionProfesorOperacionNoPermitida;

public class ProfesorImpl2 extends PersonaImpl implements Profesor {

	private Categoria categoria;
	private SortedSet<Tutoria> tutorias;
	private Departamento departamento;
	private Map<Asignatura, Double> creditosPorAsignatura;
	private static final Double MAX_CREDITOS = 24.;
	
	public ProfesorImpl2(String dni, String nombre, String apellidos, LocalDate fechaNacimiento, String email, Categoria categoria, Departamento departamento){
		super(dni, nombre, apellidos, fechaNacimiento, email);
		checkEdad(getEdad());
		this.categoria = categoria;
		this.tutorias = new TreeSet<Tutoria>();
		this.creditosPorAsignatura = new HashMap<Asignatura, Double>();
		
		//Delego la inicialización en el set
		setDepartamento(departamento);
	}
	
	private static void checkEdad(Integer edad) {
		if(edad < 18){
			throw new ExcepcionProfesorNoValido("El profesor debe ser mayor de edad");
		}
	}
	

	private void checkAsignatura(Asignatura asig){
		if(!(getDepartamento().equals(asig.getDepartamento()))){
			throw new ExcepcionProfesorOperacionNoPermitida();
		}
	}
	
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
	
	public List<Asignatura> getAsignaturas(){
		return new ArrayList<Asignatura>(creditosPorAsignatura.keySet());
	}
	
	public List<Double> getCreditos(){
		return new ArrayList<Double>(creditosPorAsignatura.values());
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
	
	public Double getDedicacionTotal(){
		Double res = 0.;
		for(Double d: getCreditos()){
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
	
	public void imparteAsignatura(Asignatura asig, Double dedicacion){
		checkAsignatura(asig);
		if(creditosPorAsignatura.containsKey(asig)){
			Double diferencia = dedicacion - creditosPorAsignatura.get(asig);
			if(diferencia > 0){
				checkDedicacion(diferencia, asig.getCreditos());
			}
		} else {
			checkDedicacion(dedicacion, asig.getCreditos());
		}
		
		creditosPorAsignatura.put(asig, dedicacion);
		
		checkDedicacionTotal(getCreditos());
	}
	
	public Double dedicacionAsignatura(Asignatura asig){
		Double res = 0.;
		if(creditosPorAsignatura.containsKey(asig)){
			res = creditosPorAsignatura.get(asig);
		}
		return res;
	}
	
	public void eliminaAsignatura(Asignatura asig){
		creditosPorAsignatura.remove(asig);
	}
	
	public String toString(){
		return super.toString() + " ("+getCategoria()+")";
	}
}
