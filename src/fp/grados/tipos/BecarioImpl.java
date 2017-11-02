package fp.grados.tipos;

import java.time.LocalDate;

import fp.grados.excepciones.ExcepcionBecarioNoValido;

public class BecarioImpl extends AlumnoImpl implements Becario {

	private Beca beca;
	private LocalDate fechaComienzo;
	
	public BecarioImpl(String dni, String nombre, String apellidos, LocalDate fechaNacimiento, String email, Beca beca, LocalDate fechaComienzo){
		super(dni, nombre, apellidos, fechaNacimiento, email);
		checkFechaComienzo(fechaComienzo);
		this.beca = beca;
		this.fechaComienzo = fechaComienzo;
	}
	
	public BecarioImpl(String dni, String nombre, String apellidos, LocalDate fechaNacimiento, String email, String codigo, Double cuantiaTotal, Integer duracion, TipoBeca tipo, LocalDate fechaComienzo){
		super(dni, nombre, apellidos, fechaNacimiento, email);
		checkFechaComienzo(fechaComienzo);
		this.beca = new BecaImpl(codigo, cuantiaTotal, duracion, tipo);
		this.fechaComienzo = fechaComienzo;
	}
	
	private static void checkFechaComienzo(LocalDate fechaComienzo){
		if(!fechaComienzo.isAfter(LocalDate.now())){
			throw new ExcepcionBecarioNoValido("La fecha de comienzo debe ser posterior a la de la fecha actual");
		}
	}
	
	private static void checkEmail(String email){
		throw new UnsupportedOperationException();
	}
	
	public Beca getBeca() {
		return beca;
	}

	public LocalDate getFechaComienzo() {
		return fechaComienzo;
	}

	public LocalDate getFechaFin() {
		LocalDate res = fechaComienzo;
		Integer duracion = getBeca().getDuracion();
		return res.plusMonths(duracion);
	}

	public void setFechaComienzo(LocalDate fechaComienzo) {
		checkFechaComienzo(fechaComienzo);
		this.fechaComienzo = fechaComienzo;
		
	}
	
	public void setEmail(String email){
		checkEmail(email);
		super.setEmail(email);
	}
	
	public String toString(){
		return super.toString() + " ["+getBeca().getCodigo()+", "+getBeca().getTipo()+"]";
	}

}
