package fp.grados.tipos;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import fp.grados.excepciones.ExcepcionGradoNoValido;

public class GradoImpl implements Grado {
	
	private String nombre;
	private Set<Asignatura> asignaturasObligatorias;
	private Set<Asignatura> asignaturasOptativas;
	private Double numeroMinimoCreditosOptativas;
	
	public GradoImpl(String nombre, Set<Asignatura> asignaturasObligatorias, Set<Asignatura> asignaturasOptativas, Double numeroMinimoCreditosOptativas){
		checkNumeroCreditos(asignaturasOptativas);
		checkNumeroMinimoCreditos(asignaturasOptativas, numeroMinimoCreditosOptativas);
		this.nombre = nombre;
		this.asignaturasObligatorias = asignaturasObligatorias;
		this.asignaturasOptativas = asignaturasOptativas;
		this.numeroMinimoCreditosOptativas = numeroMinimoCreditosOptativas;
	}
	
	//Todas las asignaturas optativas de un grado deben tener el mismo número de créditos. Si no se cumple esta restricción, lance la excepción ExcepcionGradoNoValido.
	public void checkNumeroCreditos(Set<Asignatura> asignaturasOptativas){
		Double numero = 0.;
		for(Asignatura asignatura: asignaturasOptativas) {
			numero = asignatura.getCreditos();
			for(Asignatura asignatura2: asignaturasOptativas){
				if(!(asignatura2.getCreditos().equals(numero))){
					throw new ExcepcionGradoNoValido();
				}
			}
		}
		
	}
	
	//El número mínimo de créditos de asignaturas optativas que debe cursar un alumno debe estar comprendido entre cero y el número total de créditos de asignaturas
	//optativas del grado. Si no se cumple esta restricción, lance la excepción ExcepcionGradoNoValido.
	public void checkNumeroMinimoCreditos(Set<Asignatura> asignaturasOptativas, Double numeroMinimoCreditosOptativas){
		Double res = 0.;
		for(Asignatura asignatura: asignaturasOptativas){
			res = asignatura.getCreditos() + res;
		}
		if(numeroMinimoCreditosOptativas < 0. || res < numeroMinimoCreditosOptativas){
			throw new ExcepcionGradoNoValido();
		}
	}
 
	public String getNombre() {
		return nombre;
	}

	public Set<Asignatura> getAsignaturasObligatorias() {
		return asignaturasObligatorias;
	}

	public Set<Asignatura> getAsignaturasOptativas() {
		return asignaturasOptativas;
	}

	public Double getNumeroMinimoCreditosOptativas() {
		return numeroMinimoCreditosOptativas;
	}

	public Double getNumeroTotalCreditos() {
		Double res = 0.;
		Double acum = 0.;
		for(Asignatura a: getAsignaturasObligatorias()){
			acum = a.getCreditos();
			res = acum + res;
		}
		res = res + getNumeroMinimoCreditosOptativas();
		return res;
	}

	public Set<Departamento> getDepartamentos() {
		Set<Departamento> res = new HashSet<Departamento>();
		Set<Asignatura> ConjAsig = getAsignaturasObligatorias();
		ConjAsig.addAll(getAsignaturasOptativas());
		
		for(Asignatura a: ConjAsig){
			Departamento d = a.getDepartamento();
			res.add(d);
		}
		
		return res;
	}

	public Set<Profesor> getProfesores() {
		Set<Profesor> res = new HashSet<Profesor>();
		for(Departamento d: getDepartamentos()){
			res.addAll(d.getProfesores());
		}
		return res;
	}
	
	public Set<Asignatura> getAsignaturas(Integer curso) {
		Set<Asignatura> res = new HashSet<Asignatura>();
		for(Asignatura a: getAsignaturasObligatorias()){
			if(a.getCurso() == curso){
				res.add(a);
			}
		}
		for(Asignatura a: getAsignaturasOptativas()){
			if(a.getCurso() == curso){
				res.add(a);
			}
		}
		return res;
	}
	
	public Asignatura getAsignatura(String codigo){
		Asignatura asig = null;
		for(Asignatura a: getAsignaturasObligatorias()){
			if(a.getCodigo() == codigo){
				asig = a;
			}
		}
		for(Asignatura a: getAsignaturasOptativas()){
			if(a.getCodigo() == codigo){
				asig = a;
			}
		}
		return asig;
	}

	public SortedMap<Asignatura, Double> getCreditosPorAsignatura() {
		SortedMap<Asignatura, Double> res = new TreeMap<Asignatura, Double>();
		Set<Asignatura> todasAsignaturas = new HashSet<Asignatura>();
		todasAsignaturas.addAll(asignaturasObligatorias);
		todasAsignaturas.addAll(asignaturasOptativas);
		for(Asignatura asig: todasAsignaturas){
				res.put(asig, asig.getCreditos());
		}
		return res;
	}
	
	public SortedSet<Departamento> getDepartamentosOrdenadosPorAsignaturas(){
		Comparator<Departamento> cmp = Comparator.comparing(x->x.getAsignaturas().size()); 	//Aqui la lambda expresión actua con x como variable
																							//porque la clase Departamento no se encuentra en grado
		cmp = cmp.reversed()
				.thenComparing(Comparator.naturalOrder());
		
		SortedSet<Departamento> res = new TreeSet<Departamento>(cmp);
		res.addAll(getDepartamentos());
		return res;
	}

	public int compareTo(Grado g) {
		return getNombre().compareTo(g.getNombre());
	}
	
	public boolean equals(Object o){
		boolean res = false;
		if(o instanceof Grado){
			Grado g = (Grado) o;
			res = getNombre().equals(g.getNombre());
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
