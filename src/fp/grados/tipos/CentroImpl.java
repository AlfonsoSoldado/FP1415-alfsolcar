package fp.grados.tipos;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import fp.grados.excepciones.ExcepcionCentroNoValido;
import fp.grados.excepciones.ExcepcionCentroOperacionNoPermitida;

public class CentroImpl implements Centro {

	private String nombre;
	private String direccion;
	private Integer numeroPlantas;
	private Integer numeroSotanos;
	private Set<Espacio> espacio;
	
	public CentroImpl(String nombre, String direccion, Integer numeroPlantas, Integer numeroSotanos){
		checkNumeroPlantas(numeroPlantas);
		checkNumeroSotanos(numeroSotanos);
		this.nombre = nombre;
		this.direccion = direccion;
		this.numeroPlantas = numeroPlantas;
		this.numeroSotanos = numeroSotanos;
		this.espacio = new HashSet<Espacio>();
	}
	
	private static void checkNumeroPlantas(Integer numeroPlantas){
		if(numeroPlantas < 1){
			throw new ExcepcionCentroNoValido();
		}
	}
	
	private static void checkNumeroSotanos(Integer numeroSotanos){
		if(numeroSotanos < 0){
			throw new ExcepcionCentroNoValido();
		}
	}

	public String getNombre() {
		return nombre;
	}

	public String getDireccion() {
		return direccion;
	}

	public Integer getNumeroPlantas() {
		return numeroPlantas;
	}

	public Integer getNumeroSotanos() {
		return numeroSotanos;
	}
	
	public Set<Espacio> getEspacios(){
		 return new HashSet<Espacio>(espacio);
	 }
	
	public void nuevoEspacio(Espacio e){
		Integer planta = e.getPlanta();
		if(planta >= (-getNumeroSotanos()) && planta <= (getNumeroPlantas())-1){
			espacio.add(e);
		} else {
			throw new ExcepcionCentroOperacionNoPermitida();
		}
	}
	
	public void eliminaEspacio(Espacio e){
		if(espacio.contains(e)){
			espacio.remove(e);
		}
	}

	
	/*Devuelve un array de 5 elementos de tipo Integer que representan el número de espacios de tipo aula de teoría, 
	 * laboratorio, seminario, aula de examen u otro tipo, respectivamente, que hay en el centro.
	 */
	public Integer[] getConteosEspacios() {
		Integer[] res = new Integer[TipoEspacio.values().length];
		Arrays.fill(res, 0); //Para inicializar a 0 el array
		
		for(Espacio e: getEspacios()){
			res[e.getTipo().ordinal()]++; //Metodo ordinal devuelve entero con respecto a la posición que ocupa el tipo; Ej: TEORIA = 0; LABORATORIO = 1; ETC...
		}
		
		return res;
	}

	public Set<Despacho> getDespachos() {
		Set<Despacho> res = new HashSet<Despacho>();
		for(Espacio e: getEspacios()){
			if(e instanceof Despacho){
			res.add((Despacho) e);
			}
		}
		return res;
	}

	public Set<Despacho> getDespachos(Departamento d) {
		Set<Despacho> res = new HashSet<Despacho>();
		Set<Profesor> profsDepartamento = d.getProfesores();
		
		for(Despacho despacho: getDespachos()){
			Set<Profesor> profsDespacho = despacho.getProfesores();
			profsDespacho.retainAll(profsDepartamento);
			
			if(!profsDespacho.isEmpty()){
			res.add(despacho);
			}
		}
		
		return res;
	}
	
	public Set<Profesor> getProfesores(){
		Set<Profesor> res = new HashSet<Profesor>();
		for(Despacho despacho: getDespachos()){
				res.addAll(despacho.getProfesores());
		}
		return res;
	}
	
	public Set<Profesor> getProfesores(Departamento d){
		Set<Profesor> res = new HashSet<Profesor>();
		for(Profesor profesor: getProfesores()){
			if(profesor.getDepartamento() == d){
				res.add(profesor);
			}
		}
		return res;
	} 
	
	//Devuelve el espacio con mayor capacidad del centro.
	public Espacio getEspacioMayorCapacidad(){
		if(getEspacios().isEmpty()){
			throw new ExcepcionCentroOperacionNoPermitida();
		} else {
			Espacio res = new EspacioImpl(null, null, 1, null);
			for(Espacio e: getEspacios()){
			if(e.getCapacidad() > res.getCapacidad()){
				res = e;
			}
			}
			return res;
		}
	}
	
	/*
	 * Devuelve un SortedMap<Profesor, Despacho> que hace corresponder a cada profesor
	 *  con el despacho que ocupa en el centro. Para construir el Map, comience 
	 *  por recorrer los despachos del centro y, para cada uno, recorra a su vez 
	 *  los profesores que lo ocupan y añada al Map la pareja formada por el 
	 *  profesor y el despacho.
	 */
	public SortedMap<Profesor, Despacho> getDespachosPorProfesor() {
		SortedMap<Profesor, Despacho> res = new TreeMap<Profesor, Despacho>();
		for(Despacho d: getDespachos()){
			actualizaMapProfesorDespacho(d.getProfesores(), d, res);
		}
		return res;
	}
	
	private void actualizaMapProfesorDespacho(Set<Profesor> profesoresDespacho, Despacho d, SortedMap<Profesor, Despacho> res) {
		for(Profesor p: profesoresDespacho){
			res.put(p, d);
		}
	}
	
	public SortedSet<Espacio> getEspaciosOrdenadosPorCapacidad(){
		Comparator<Espacio> cmp = Comparator.comparing(Espacio::getCapacidad)
				.reversed()
				.thenComparing(Comparator.naturalOrder());
		
		SortedSet<Espacio> res = new TreeSet<Espacio>(cmp);
		res.addAll(getEspacios());
		return res;
	}

	public int compareTo(Centro centro) {
		return getNombre().compareTo(centro.getNombre());
	}
	
	public boolean equals(Object o){
		boolean res = false;
		if(o instanceof Centro){
			Centro c = (Centro) o;
			res = getNombre().equals(c.getNombre());
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
