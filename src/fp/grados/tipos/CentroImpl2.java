package fp.grados.tipos;

import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class CentroImpl2 extends CentroImpl {

	public CentroImpl2(String nombre, String direccion, Integer numeroPlantas, Integer numeroSotanos) {
		super(nombre, direccion, numeroPlantas, numeroSotanos);
		
	}
	
	//Devuelve el espacio con mayor capacidad del centro.
	public Espacio getEspacioMayorCapacidad(){
		Set<Espacio> espacios = getEspacios();
		Comparator<Espacio> cmp = Comparator.comparing(Espacio::getCapacidad)
				.thenComparing(Comparator.naturalOrder());
		
		return espacios.stream()
				.max(cmp)
				.get(); 	//El get() hace falta para devolver solo el primer elemento, 
							//que será el máximo en este caso, si no lo coge salta excepción
	}
	
	
	/*Devuelve un array de 5 elementos de tipo Integer que representan el número de espacios de tipo aula de teoría, 
	 * laboratorio, seminario, aula de examen u otro tipo, respectivamente, que hay en el centro.
	 */
	public Integer[] getConteosEspacios(){
		Set<Espacio> espacios = getEspacios();
		return new Integer[] {
		cuentaEspaciosTipo(TipoEspacio.TEORIA, espacios),
		cuentaEspaciosTipo(TipoEspacio.LABORATORIO, espacios),
		cuentaEspaciosTipo(TipoEspacio.SEMINARIO, espacios),
		cuentaEspaciosTipo(TipoEspacio.EXAMEN, espacios),
		cuentaEspaciosTipo(TipoEspacio.OTRO, espacios)
		};
		
	}
	
	private Integer cuentaEspaciosTipo(TipoEspacio tipo, Set<Espacio> espacios){
		return (int) espacios.stream().filter(x -> x.getTipo().equals(tipo)).count();
	}
	
	//Devuelve el conjunto de todos los profesores que tienen un despacho en el centro.
	public Set<Profesor> getProfesores(){
		return getDespachos().stream()
				.flatMap(x -> x.getProfesores().stream())
				.collect(Collectors.toSet());
	}
	
	//Devuelve el conjunto de todos los profesores que pertenecen al departamento d y tienen un despacho en el centro.
	public Set<Profesor> getProfesores(Departamento d){
//		return getDespachos().stream()
//				.flatMap(x -> x.getProfesores().stream().filter(p -> p.getDepartamento().equals(d)))
//				.collect(Collectors.toSet());
		return getProfesores().stream()
				.filter(p -> p.getDepartamento().equals(d))
				.collect(Collectors.toSet());
	}
	
	/*
	 * Devuelve un SortedMap<Profesor, Despacho> que hace corresponder a cada profesor con el despacho que ocupa en el centro. 
	 * Para construir el Map, comience por recorrer los despachos del centro y, para cada uno, 
	 * recorra a su vez los profesores que lo ocupan y añada al Map la pareja formada por el profesor y el despacho.
	 */
	public SortedMap<Profesor, Despacho> getDespachosPorProfesor(){
		Map<Profesor, Despacho> rr = getProfesores().stream()
				.filter(p -> tieneDespacho(p))
				.collect(Collectors.toMap(p -> p, p -> buscaDespacho(p)));
		
		return new TreeMap<Profesor, Despacho>(rr);
		
		
	}
	
	private Boolean tieneDespacho(Profesor p){
		return getDespachos().stream()
				.anyMatch(d -> d.getProfesores().contains(p));
	}
	
	private Despacho buscaDespacho(Profesor p){
		return getDespachos().stream()
				.filter(d -> d.getProfesores().contains(p))
				.findFirst()
				.get();
	}
	
	//Devuelve un conjunto con todos los espacios de tipo Despacho que hay en el centro.
	public Set<Despacho> getDespachos(){
		return getEspacios().stream()
				.filter(e -> e instanceof Despacho)
				.map(e -> (Despacho) e)
				.collect(Collectors.toSet());
				
	}
	
	//Devuelve un conjunto con todos los despachos del centro donde hay al menos un profesor del departamento d.
	public Set<Despacho> getDespachos(Departamento d){
		return getDespachos().stream()
				.filter(de -> de.getProfesores().stream().anyMatch(p -> p.getDepartamento().equals(d)))
				.collect(Collectors.toSet());
	}
}
