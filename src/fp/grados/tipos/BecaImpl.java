package fp.grados.tipos;

import fp.grados.excepciones.ExcepcionBecaNoValida;

public class BecaImpl implements Beca{
	
	private String codigo;
	private	Double cuantiaTotal;
	private Integer duracion;
	private TipoBeca tipo;
	
	private static final Double CUANTIA_MINIMA = 1500.;
	private static final Integer DURACION_MINIMA = 1;
	
	// �ABC1234,6000.0,12,ORDINARIA�
	public BecaImpl(String s){
		String [] trozos = s.split(",");
		if(trozos.length != 4){
			throw new IllegalArgumentException();
		}
		checkCodigo(trozos[0].trim());
		checkDuracion(new Integer(trozos[2].trim()));
		checkCuantiaTotal(new Double(trozos[1].trim()));
		this.codigo = trozos[0].trim();
		this.cuantiaTotal = new Double(trozos[1].trim());
		this.duracion = new Integer(trozos[2].trim());
		this.tipo = TipoBeca.valueOf(trozos[3].trim());
	}
	
	public BecaImpl(String codigo, Double cuantiaTotal, Integer duracion, TipoBeca tipo){
		checkCodigo(codigo);
		checkDuracion(duracion);
		checkCuantiaTotal(cuantiaTotal);
		this.codigo = codigo;
		this.cuantiaTotal = cuantiaTotal;
		this.duracion = duracion;
		this.tipo = tipo;
	}
	
	public BecaImpl(String codigo, TipoBeca tipo){
		checkCodigo(codigo);
		this.codigo = codigo;
		this.cuantiaTotal = CUANTIA_MINIMA;
		this.duracion = DURACION_MINIMA;
		this.tipo = tipo;
	}
	
	private static void checkCodigo(String codigo){
		if(codigo.length() != 7){
			throw new ExcepcionBecaNoValida("El c�digo debe contener una longitud de 7");
		}
		Boolean res = true;
		res = Character.isAlphabetic(codigo.charAt(0))
						&& Character.isAlphabetic(codigo.charAt(1))
						&& Character.isAlphabetic(codigo.charAt(2))
						&& Character.isDigit(codigo.charAt(3))
						&& Character.isDigit(codigo.charAt(4))
						&& Character.isDigit(codigo.charAt(5))
						&& Character.isDigit(codigo.charAt(6));
		if(res == false){
			throw new ExcepcionBecaNoValida();
		}
	}
	
	private static void checkDuracion(Integer duracion){
		if(duracion < 1){
			throw new ExcepcionBecaNoValida("La beca debe durar m�s de 1 mes");
		}
	}
	
	private static void checkCuantiaTotal(Double cuantiaTotal){
		if(cuantiaTotal < 1500.){
			throw new ExcepcionBecaNoValida("La beca debe tener una cuantia total m�nima de 1500�");
		}
	}

	public String getCodigo() {
		return codigo;
	}
	
	public Double getCuantiaTotal() {
		return cuantiaTotal;
	}
	
	public Integer getDuracion() {
		return duracion;
	}

	public TipoBeca getTipo() {
		return tipo;
	}

	public Double getCuantiaMensual() {
		return getCuantiaTotal()/getDuracion();
	}

	public void setCuantiaTotal(Double cuantiaTotal) {
		checkCuantiaTotal(cuantiaTotal);
		this.cuantiaTotal = cuantiaTotal;
	}

	public void setDuracion(Integer duracion) {
		checkDuracion(duracion);
		this.duracion = duracion;
	}
	
	public boolean equals(Object obj) {
		boolean res = false;
		if(obj instanceof Beca){
			Beca beca1 = (Beca) obj;
			res = getCodigo().equals(beca1.getCodigo()) && getTipo().equals(beca1.getTipo());
		}
		return res;
	}
	
	public int hashCode(){
		return getCodigo().hashCode()+getTipo().hashCode()*31;
	}
	
	public int compareTo(Beca Beca1){
		int res = getCodigo().compareTo(Beca1.getCodigo());
		if(res == 0){
			res = getTipo().compareTo(Beca1.getTipo());
		}
		return res;
	}
	
	public String toString(){
		return "["+getCodigo()+", "+getTipo()+"]";
	}
	
}
