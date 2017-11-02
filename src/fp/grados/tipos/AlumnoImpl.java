package fp.grados.tipos;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import fp.grados.excepciones.ExcepcionAlumnoNoValido;
import fp.grados.excepciones.ExcepcionAlumnoOperacionNoPermitida;

public class AlumnoImpl extends PersonaImpl implements Alumno {

	private Set<Asignatura> asignaturas;
	private Expediente expediente;
	
	// “12345678Z,Juan,López García,20/1/1998,juan@alum.us.es”
	public AlumnoImpl(String s){
		super(s);
		
		checkEmailAlumno(getEmail());
		
		this.asignaturas = new HashSet<Asignatura>();
		this.expediente = new ExpedienteImpl();
	}
	
	public AlumnoImpl(String dni, String nombre, String apellidos, LocalDate fechaNacimiento, String email){
		super(dni, nombre, apellidos, fechaNacimiento, email);
		checkEmailAlumno(email);
		this.asignaturas = new HashSet<Asignatura>();
		this.expediente = new ExpedienteImpl();
	
	}
	
	private static void checkEmailAlumno(String email){
		if(!email.endsWith("@alum.us.es") || email == ""){
			throw new ExcepcionAlumnoNoValido("El email no puede ser cadena vacía, y debe terminar en alum.us.es");
		}
	}
	
	
	public Set<Asignatura> getAsignaturas() {
		return asignaturas;
	}

	public Integer getCurso() {
		Integer res = 0;
		for(Asignatura asig: getAsignaturas()){
			Integer curso = asig.getCurso();
			if(curso>res){
				res = curso;
			}
			//res = curso > res ? curso : res;
		}
		return res;
	}
	
	public Expediente getExpediente(){
		return expediente;
	}
	
	public void setEmail(String email){
		checkEmailAlumno(email);
		super.setEmail(email);
	}
	
	public void matriculaAsignatura(Asignatura asig) {
		if(asignaturas.contains(asig)){
			throw new ExcepcionAlumnoOperacionNoPermitida();
		} else {
			asignaturas.add(asig);
		}
		
	}

	public void eliminaAsignatura(Asignatura asig) {
		if(!asignaturas.contains(asig)){
			throw new ExcepcionAlumnoOperacionNoPermitida();
		} else {
			asignaturas.remove(asig);
		}
		
	}

	public Boolean estaMatriculadoEn(Asignatura asig) {
		Boolean res = false;
		if(asignaturas.contains(asig)){
			res = true;
		}
		return res;
	}
	
	public void evaluaAlumno(Asignatura a, Integer curso, Convocatoria convocatoria, Double nota, Boolean mencionHonor){
		if(asignaturas.contains(a)){
			Nota n = new NotaImpl(a, curso, convocatoria, nota, mencionHonor);
			expediente.nuevaNota(n);
		} else {
			throw new ExcepcionAlumnoOperacionNoPermitida();
		}
	}
	
	public void evaluaAlumno(Asignatura a, Integer curso, Convocatoria convocatoria, Double nota){
		if(asignaturas.contains(a)){
			Nota n = new NotaImpl(a, curso, convocatoria, nota);
			expediente.nuevaNota(n);
		} else {
			throw new ExcepcionAlumnoOperacionNoPermitida();
		}
	}
	
	public SortedMap<Asignatura, Calificacion> getCalificacionPorAsignatura() {
		SortedMap<Asignatura, Calificacion> res = new TreeMap<Asignatura, Calificacion>();
		Expediente exp = getExpediente();
		for(Nota n: exp.getNotas()){
//			if(res.values().contains(n)){
//				Nota acum = n;
//				if(acum.getValor() > n.getValor()){
//					res.put(acum.getAsignatura(), acum.getCalificacion());
//				}
//			} else {
//				res.put(n.getAsignatura(), n.getCalificacion());
//			}
			auxiliarCalificacionPorAsignatura(n, res);
		}
		return res;
	}
	
	private void auxiliarCalificacionPorAsignatura(Nota n, SortedMap<Asignatura, Calificacion> res) {
		for(Asignatura a: getAsignaturas()){
			if(res.containsKey(a)){
				if(res.get(a).ordinal() < n.getCalificacion().ordinal()){
					res.put(n.getAsignatura(), n.getCalificacion());
				}
			} else {
				res.put(a, n.getCalificacion());
			}
			
		}
		
	}

	public SortedSet<Asignatura> getAsignaturasOrdenadasPorCurso(){
		Comparator<Asignatura> cmp = Comparator.comparing(Asignatura::getCurso) //Se crea comparador con respecto a curso
				.reversed() //Hace que el comparador actue al contrario tal y como pide el enunciado
				.thenComparing(Comparator.naturalOrder()); //En caso de empate, ordenarlo por orden natural
			
		SortedSet<Asignatura> res = new TreeSet<Asignatura>(cmp);
		res.addAll(getAsignaturas());
		
		return res;
	}
	
	public String toString(){
		return "(" + getCurso() + "º) " + super.toString();
	}
}
