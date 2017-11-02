package fp.grados.tipos;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GradoImpl2 extends GradoImpl {

	public GradoImpl2(String nombre, Set<Asignatura> asignaturasObligatorias, Set<Asignatura> asignaturasOptativas, Double numeroMinimoCreditosOptativas) {
		super(nombre, asignaturasObligatorias, asignaturasOptativas, numeroMinimoCreditosOptativas);
	}
	
	public Double getNumeroTotalCreditos(){
		
		return getAsignaturasObligatorias().stream()
				.mapToDouble(a -> a.getCreditos())
				.sum() 
				+ getNumeroMinimoCreditosOptativas();
	}
	
	public Set<Asignatura> getAsignaturas(Integer curso){
		
		Set<Asignatura> obligatorias = seleccionaAsignaturas(getAsignaturasObligatorias(), curso);
		Set<Asignatura> optativas = seleccionaAsignaturas(getAsignaturasOptativas(), curso);
		
		Set<Asignatura> res = new HashSet<Asignatura>();
		res.addAll(obligatorias);
		res.addAll(optativas);
		
		return res;
	}
	
	private Set<Asignatura> seleccionaAsignaturas(Set<Asignatura> asig, Integer curso){
		return asig.stream()
				.filter(a -> a.getCurso().equals(curso))
				.collect(Collectors.toSet());
	}
	
	//Devuelve la asignatura del grado cuyo código es codigo, o null si no existe ninguna asignatura con dicho código.
	public Asignatura getAsignatura(String codigo){
		Asignatura a = null;
		
		a = getAsignaturasObligatorias().stream()
				.filter(asig -> asig.getCodigo().equals(codigo))
				.findFirst()
				.get();
		
		a = getAsignaturasOptativas().stream()
				.filter(asig -> asig.getCodigo().equals(codigo))
				.findFirst()
				.get();
		
		return a;
	}
	
	//Representa los departamentos con docencia en el grado, que son aquellos que imparten alguna asignatura en el mismo.
	public Set<Departamento> getDepartamentos(){
		Stream<Asignatura> oblig = getAsignaturasObligatorias().stream();
		Stream<Asignatura> opt = getAsignaturasOptativas().stream();
		
		return Stream.concat(oblig, opt)
				.map(a -> a.getDepartamento())
				.collect(Collectors.toSet());
	}
	
	//Representa los profesores que pertenecen a cualquiera de los departamentos con docencia en el grado.
	public Set<Profesor> getProfesores(){
		return getDepartamentos().stream()
				.flatMap(d -> d.getProfesores().stream())
				.collect(Collectors.toSet());
	}
	
	/*
	 * Devuelve un SortedMap<Asignatura, Double> que hace corresponder a cada asignatura con su número de créditos. 
	 * Para construir el Map, comience por recorrer todas las asignaturas del centro, 
	 * tanto obligatorias como optativas, y, para cada una, añada al SortedMap la pareja formada por la asignatura y su número de créditos.
	 */
	public SortedMap<Asignatura, Double> getCreditosPorAsignatura(){
		Set<Asignatura> asignaturas = getAsignaturasObligatorias();
		asignaturas.addAll(getAsignaturasOptativas());
		Map<Asignatura, Double> mapa = asignaturas.stream()
				.filter(a -> hayCreditos(a))
				.collect(Collectors.toMap(a -> a, a -> a.getCreditos()));
		
		return new TreeMap<Asignatura, Double>(mapa);
	}
	
	private Boolean hayCreditos(Asignatura a){
		Boolean res = true;
		if(a.getCreditos()<=0.){
			res = false;
		}
		return res;
	}

}
