package fp.grados.tipos;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class AlumnoImpl2 extends AlumnoImpl {

	public AlumnoImpl2(String s) {
		super(s);
	}
	
	public AlumnoImpl2(String dni, String nombre, String apellidos, LocalDate fechaNacimiento, String email){
		super(dni, nombre, apellidos, fechaNacimiento, email);	
	}
	
	public Integer getCurso(){
		Integer curso = 0;
		Set<Asignatura> asignaturas = getAsignaturas();
		Comparator<Asignatura> comp = Comparator.comparing(Asignatura::getCurso);
		try {
			curso = asignaturas
			.stream()
			.max(comp)
			.get()
			.getCurso();
		} catch (Exception e){
			curso = 0;
		}
		return curso;
	}
	
	
	/*
	 * Devuelve un SortedMap<Asignatura, Calificacion> que hace corresponder a cada asignatura que aparece en el 
	 * expediente del alumno con la calificación máxima obtenida en ella. Para construir el SortedMap,
	 *  comience por recorrer las notas del expediente del alumno y, para cada una, añada al SortedMap la pareja 
	 *  formada por la asignatura y su calificación. Antes de hacerlo, compruebe si el SortedMap ya contiene una 
	 *  calificación para dicha asignatura, en cuyo caso deberá sustituirla por la nueva si ésta supera a la existente.
	 */
	public SortedMap<Asignatura, Calificacion> getCalificacionPorAsignatura(){
		List<Nota> ListaNotas = getExpediente().getNotas();
		
		SortedMap<Asignatura, Calificacion> res = new TreeMap<Asignatura, Calificacion>();
		
		res = ListaNotas
				.stream()
				.collect(Collectors.toMap(n->n.getAsignatura(), n->n.getCalificacion(),
						(x1,x2)->cmp(x1,x2), 
						TreeMap::new));

		return res;
	}
	
	private Calificacion cmp (Calificacion c1, Calificacion c2) {
		Calificacion mayor = null;
		if(c1.ordinal() > c2.ordinal()){
			mayor = c1;
		} else {
			mayor = c2;
		}
		return mayor;
	}

}
