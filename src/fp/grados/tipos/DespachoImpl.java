package fp.grados.tipos;

import java.util.HashSet;
import java.util.Set;

import fp.grados.excepciones.ExcepcionDespachoNoValido;

public class DespachoImpl extends EspacioImpl implements Despacho {

	private Set<Profesor> profesores;
	
	// “F1.43,1,3”
	public DespachoImpl(String s){
		super(s + ", OTRO");
			
		this.profesores = new HashSet<Profesor>();
	}
	
	public DespachoImpl(String nombre, Integer capacidad, Integer planta, Set<Profesor> profesores){
		super(TipoEspacio.OTRO, nombre, capacidad, planta);
		checkNumProfesores(profesores, capacidad);
		this.profesores = profesores;
	}
	
	public DespachoImpl(String nombre, Integer capacidad, Integer planta, Profesor profesor){
		super(TipoEspacio.OTRO, nombre, capacidad, planta);
		this.profesores = new HashSet<Profesor>();
		profesores.add(profesor);
		checkNumProfesores(profesores, capacidad);
	}
	
	public DespachoImpl(String nombre, Integer capacidad, Integer planta){
		super(TipoEspacio.OTRO, nombre, capacidad, planta);
		this.profesores = new HashSet<>();
		checkNumProfesores(profesores, capacidad);
	}
	
	private static void checkNumProfesores(Set<Profesor> profesores, Integer capacidad){
		if(profesores.size() > capacidad){
			throw new ExcepcionDespachoNoValido();
		}
	}
	
	private static void checkTipo(){
		throw new UnsupportedOperationException();
	}
	
	public Set<Profesor> getProfesores() {
		return profesores;
	}

	public void setProfesores(Set<Profesor> profesores) {
	checkNumProfesores(profesores, getCapacidad());
		this.profesores = profesores;
	}
	
	public void setTipo(TipoEspacio tipo){
		super.setTipo(tipo);
		checkTipo();
	}
	
	public String toString(){
		return super.toString() + " " + getProfesores().toString();
	}

}
