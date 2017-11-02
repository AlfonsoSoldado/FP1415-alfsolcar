package fp.grados.tipos;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.function.Predicate;

public class DepartamentoImpl implements Departamento {
	
	private String nombre;
	private Set<Asignatura> asignaturas;
	private Set<Profesor> profesores;
	
	public DepartamentoImpl(String nombre){
		this.nombre = nombre;
		this.asignaturas = new HashSet<Asignatura>();
		this.profesores = new HashSet<Profesor>();
	}
	
	public String getNombre() {
		return nombre;
	}

	public Set<Asignatura> getAsignaturas() {
		return new HashSet<Asignatura>(asignaturas);
	}
	
	public Set<Profesor> getProfesores(){
		return new HashSet<Profesor>(profesores);
	}

	public void nuevaAsignatura(Asignatura asig) {
		asignaturas.add(asig);
		asig.setDepartamento(this);
	}

	public void eliminaAsignatura(Asignatura asig) {
		asignaturas.remove(asig);
		asig.setDepartamento(null);
	}
	
	public void nuevoProfesor(Profesor p){
		profesores.add(p);
		p.setDepartamento(this);
	}
	
	public void eliminaProfesor(Profesor p){
		profesores.remove(p);
		p.setDepartamento(null);
	}

	public void borraTutorias() {
		for(Profesor p: profesores){
			p.borraTutorias();
		}
	}

	public void borraTutorias(Categoria c) {
		for(Profesor p: profesores){
			if(p.getCategoria() == c){
				p.borraTutorias();
			}
		}
	}

	public Boolean existeProfesorAsignado(Asignatura a) {
		Boolean res = false;
		
		for(Profesor p: profesores){
			res = p.getAsignaturas().contains(a);
			if(res){
				break;
			}
		}
		
		return res;
	}
	
	public Boolean estanTodasAsignaturasAsignadas(){
		Boolean res = true;
		for(Asignatura a: asignaturas){
			if(!existeProfesorAsignado(a)){
				res = false;
			}
		}
		return res;
	}
	
	public void eliminaAsignacionProfesorado(Asignatura a){
		for(Profesor prof: profesores){
			if(prof.getAsignaturas().contains(a) && prof.dedicacionAsignatura(a) > 0){
				prof.eliminaAsignatura(a);
			}
		}
	}

	public SortedMap<Asignatura, SortedSet<Profesor>> getProfesoresPorAsignatura() {
		SortedMap<Asignatura, SortedSet<Profesor>> res = new TreeMap<Asignatura, SortedSet<Profesor>>();
		for(Profesor p: getProfesores()){
			actualizaMapAsignaturaProfesores(p.getAsignaturas(), p, res);
		}
		return res;
	}

	private void actualizaMapAsignaturaProfesores(List<Asignatura> asignaturasProfesor, Profesor p, SortedMap<Asignatura, SortedSet<Profesor>> res) {
		for (Asignatura a : asignaturasProfesor) {
			Asignatura key = a;
			SortedSet<Profesor> conjuntoOrdenado = new TreeSet<Profesor>();
			conjuntoOrdenado.add(p);
			
			if(!res.containsKey(key)){
				res.put(key, conjuntoOrdenado);
			} else {
				res.get(key).addAll(conjuntoOrdenado);
			}
		}	
	}
	
	public SortedMap<Profesor, SortedSet<Tutoria>> getTutoriasPorProfesor() {
		SortedMap<Profesor, SortedSet<Tutoria>> res = new TreeMap<Profesor, SortedSet<Tutoria>>();
		for(Profesor p: getProfesores()){
			actualizaProfesorPorTutoria(p.getTutorias(), p, res);
		}
		return res;
	}

	private void actualizaProfesorPorTutoria(SortedSet<Tutoria> tutorias, Profesor p, SortedMap<Profesor, SortedSet<Tutoria>> res) {
			
			if(!res.containsKey(p)){
				res.put(p, p.getTutorias());
			} else {
				res.get(p).addAll(p.getTutorias());
		}
	}
	
	/*
	 * Devuelve el profesor del departamento que tiene la mayor carga docente media por asignatura. 
	 * Tenga en cuenta que puede haber profesores sin asignaturas asignadas. Si no hay profesores en el departamento, lance la excepción NoSuchElementException.
	 */
	public Profesor getProfesorMaximaDedicacionMediaPorAsignatura(){
		Set<Profesor> profesores = getProfesores();
		Comparator<Profesor> cmp = Comparator.comparing(x->x.getDedicacionTotal()/(x.getAsignaturas().size()));
		Predicate<Profesor> pred = x->!(x.getAsignaturas().isEmpty());
		if(profesores.isEmpty()){
			throw new NoSuchElementException();
		}
		
		return profesores
				.stream()
				.filter(pred)
				.max(cmp)
				.get();
	}

	public int compareTo(Departamento d) {
		return getNombre().compareTo(d.getNombre());
	}
	
	public boolean equals(Object o){
		boolean res = false;
		if(o instanceof Departamento){
			Departamento d = (Departamento) o;
			res = getNombre().equals(d.getNombre());
		}
		return res;
	}
	
	public int hashCode(){
		return getNombre().hashCode();
	}
	
	public String toString(){
		return getNombre();
	}
}
