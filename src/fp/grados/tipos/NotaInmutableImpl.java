package fp.grados.tipos;

import fp.grados.excepciones.ExcepcionNotaNoValida;

public final class NotaInmutableImpl implements Nota {

	private final Asignatura asignatura;
	private final Integer cursoAcademico;
	private final Convocatoria convocatoria;
	private final Double valor;
	private final Boolean mencionHonor;
	
	// “Fundamentos de Programación#1234567#12.0#ANUAL#1,2014,PRIMERA,10.0,true”
	public NotaInmutableImpl(String s){
		String [] trozos = s.split(";");
		
		if(trozos.length != 5){
			throw new IllegalArgumentException();
		}
		
		checkValor(new Double(trozos[3].trim()));
		checkMencionHonor(new Double(trozos[3].trim()), new Boolean(trozos[4].trim()));
		
		//En asignatura se reutiliza el nuevo constructor creado a partir de String
		this.asignatura = new AsignaturaImpl(trozos[0].trim());
		this.cursoAcademico = new Integer(trozos[1].trim());
		this.convocatoria = Convocatoria.valueOf(trozos[2].trim());
		this.valor = new Double(trozos[3].trim());
		this.mencionHonor = new Boolean(trozos[4].trim());
	}
	
	public NotaInmutableImpl(Asignatura asignatura, Integer cursoAcademico, Convocatoria convocatoria, Double valor, Boolean mencionHonor){
		checkValor(valor);
		checkMencionHonor(valor, mencionHonor);
		this.asignatura = new AsignaturaImpl(asignatura.getNombre(), asignatura.getCodigo(), asignatura.getCreditos(), asignatura.getTipo(), asignatura.getCurso(), asignatura.getDepartamento());
		this.cursoAcademico = cursoAcademico;
		this.convocatoria = convocatoria;
		this.valor = valor;
		this.mencionHonor = mencionHonor;
	}
	
	public NotaInmutableImpl(Asignatura asignatura, Integer cursoAcademico, Convocatoria convocatoria, Double valor){
		checkValor(valor);
		this.asignatura = new AsignaturaImpl(asignatura.getNombre(), asignatura.getCodigo(), asignatura.getCreditos(), asignatura.getTipo(), asignatura.getCurso(), asignatura.getDepartamento());
		this.cursoAcademico = cursoAcademico;
		this.convocatoria = convocatoria;
		this.valor = valor;
		this.mencionHonor = false;
	}
	
	private static void checkValor(Double valor){
		if(valor < 0 || valor > 10){
			throw new ExcepcionNotaNoValida("El valor debe estar comprendido entre 0 y 10, ambos inclusives");
		}
	}
	
	//una nota sólo puede tener mención de honor si su valor numérico es igual o superior a 9
	
	private static void checkMencionHonor(Double valor, Boolean mencionHonor){
		if(valor < 9 && mencionHonor == true){
			throw new ExcepcionNotaNoValida("Para que se obtenga mencion de honor, el valor debe ser como mínimo 9");
		}
	}
	
	public Asignatura getAsignatura() {
		return new AsignaturaImpl(asignatura.getNombre(), asignatura.getCodigo(), asignatura.getCreditos(), asignatura.getTipo(), asignatura.getCurso(), asignatura.getDepartamento());
	}

	public Integer getCursoAcademico() {
		return cursoAcademico;
	}

	public Convocatoria getConvocatoria() {
		return convocatoria;
	}

	public Double getValor() {
		return valor;
	}

	public Boolean getMencionHonor() {
		return mencionHonor;
	}

	public Calificacion getCalificacion() {
		Calificacion calificacion = null;
		if(valor < 5){
			calificacion = Calificacion.SUSPENSO;
		} else if(valor >= 5 && valor < 7) {
			calificacion = Calificacion.APROBADO;
		} else if(valor >= 7 && valor < 9) {
			calificacion = Calificacion.NOTABLE;
		} else if(valor >= 9 && mencionHonor == false) {
			calificacion = Calificacion.SOBRESALIENTE;
		} else if(valor >= 9 && mencionHonor == true) {
			calificacion = Calificacion.MATRICULA_DE_HONOR;
		}
		return calificacion;
	}
	
	public boolean equals(Object obj) {
		boolean res = false;
		if (obj instanceof Nota) {
			Nota nota = (Nota) obj;
			res = getCursoAcademico().equals(nota.getCursoAcademico())
					&& getAsignatura().equals(nota.getAsignatura())
					&& getConvocatoria().equals(nota.getConvocatoria());
		}
		return res;
	}

	public int hashCode() {
		return getCursoAcademico().hashCode() + getAsignatura().hashCode() * 31 + getConvocatoria().hashCode() * 37;
	}

	public int compareTo(Nota nota) {
		int res;
		res = getCursoAcademico().compareTo(nota.getCursoAcademico());
		if (res == 0) {
			res = getAsignatura().compareTo(nota.getAsignatura());
			if (res == 0) {
				res = getConvocatoria().compareTo(nota.getConvocatoria());
			}
		}
		return res;
	}
	
	public String toString(){
		Integer i = Integer.valueOf(cursoAcademico % 100 + 1);
		String res = "";
		String s = "0";
		if(i == 1 || i == 2 || i == 3 || i == 4 || i == 5 || i == 6 || i == 7 || i == 8 || i == 9){
			res = s+String.valueOf(i);
		} else if(i == 100){
			res = "00";
		} else {
			res = String.valueOf(i);
		}
		return getAsignatura()+", "+cursoAcademico+ "-" + res + ", "+convocatoria+", "+valor+", "+getCalificacion();
	}

}
