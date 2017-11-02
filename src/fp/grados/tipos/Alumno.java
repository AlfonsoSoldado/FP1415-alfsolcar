package fp.grados.tipos;

import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;

public interface Alumno extends Persona {
	Set<Asignatura> getAsignaturas();
	Integer getCurso();
	Expediente getExpediente();
	
	void matriculaAsignatura(Asignatura asig);
	void eliminaAsignatura(Asignatura asig);
	Boolean estaMatriculadoEn(Asignatura asig);
	void evaluaAlumno(Asignatura a, Integer curso, Convocatoria convocatoria, Double nota, Boolean mencionHonor);
	void evaluaAlumno(Asignatura a, Integer curso, Convocatoria convocatoria, Double nota);
	
	SortedMap<Asignatura, Calificacion> getCalificacionPorAsignatura();
	
	SortedSet<Asignatura> getAsignaturasOrdenadasPorCurso();
}
