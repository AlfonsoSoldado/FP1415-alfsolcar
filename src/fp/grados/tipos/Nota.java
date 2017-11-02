package fp.grados.tipos;

public interface Nota extends Comparable<Nota> {

	Asignatura getAsignatura();
	Integer getCursoAcademico();
	Convocatoria getConvocatoria();
	Double getValor();
	Boolean getMencionHonor();
	Calificacion getCalificacion();
	
}
