package fp.grados.tipos;

import java.util.Map;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class DepartamentoImpl2 extends DepartamentoImpl {

	public DepartamentoImpl2(String nombre) {
		super(nombre);
	}

	
	//Elimina las tutorías de todos los profesores del departamento.
	public void borraTutorias(){
		 getProfesores().stream().forEach(Profesor::borraTutorias);
//		 getProfesores().stream().forEach(x->x.getTutorias().clear());
	}
	
	//Elimina las tutorías de todos los profesores del departamento cuya categoría es c.
	public void borraTutorias(Categoria categoria){
		getProfesores().stream()
				.filter(x->x.getCategoria().equals(categoria))
				.forEach(Profesor::borraTutorias);
//				.forEach(x->x.getTutorias().clear());
	}
	
	
	/*
	 * Devuelve un SortedMap<Profesor, SortedSet<Tutoria>> que hace corresponder a cada profesor con el conjunto de
	 * tutorías que tiene. Para construir el SortedMap, comience por recorrer los profesores del departamento y, para cada uno,
	 * añada al SortedMap la pareja formada por el profesor y su conjunto de tutorías.
	 */
	public SortedMap<Profesor, SortedSet<Tutoria>> getTutoriasPorProfesor(){
		Map<Profesor, SortedSet<Tutoria>> map = getProfesores().stream()
				.collect(Collectors.toMap(p -> p, p -> p.getTutorias()));
		
		return new TreeMap<Profesor, SortedSet<Tutoria>>(map);
	}
	
	/*Devuelve true si existe al menos un profesor asignado a la asignatura a, es decir,
	 * que imparte algún crédito en ella, y false en caso contrario.
	 */
	public Boolean existeProfesorAsignado(Asignatura a){
		return getProfesores().stream()
				.anyMatch(p -> p.getAsignaturas().contains(a));
	}
	
	
	//Devuelve true si todas las asignaturas tienen asignado al menos un profesor, y false en caso contrario.
	public Boolean estanTodasAsignaturasAsignadas(){
		Boolean res = false;
		try{
			res = getAsignaturas().stream().allMatch(a->a.getDepartamento().existeProfesorAsignado(a));
		} catch (Exception e){
			res = false;
		}
		return res;
	}
	
	//Elimina la asignatura a de todos los profesores del departamento que la están impartiendo.
	public void eliminaAsignacionProfesorado(Asignatura a){
		getProfesores().stream()
				.filter(p -> p.getAsignaturas().contains(a) && p.getDedicacionTotal() > 0)
				.forEach(p -> p.eliminaAsignatura(a));
	}
}
