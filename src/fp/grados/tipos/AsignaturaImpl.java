package fp.grados.tipos;

import java.util.Arrays;
import java.util.List;

import fp.grados.excepciones.ExcepcionAsignaturaNoValida;

public class AsignaturaImpl implements Asignatura {

	private String nombre;
	private String codigo;
	private Double creditos;
	private TipoAsignatura tipo;
	private Integer curso;
	private Departamento departamento;
	
	// “Fundamentos de Programación#1234567#12.0#ANUAL#1”
	public AsignaturaImpl(String s){
		
		//En este ejercicio se ha usado la alternativa de una lista en vez de un array.
		List<String> trozos = Arrays.asList(s.split("#"));
		
		if(trozos.size() != 5){
			throw new IllegalArgumentException();
		}
		checkCodigo(trozos.get(1).trim());
		checkCreditos(new Double(trozos.get(2).trim()));
		checkCurso(new Integer(trozos.get(4).trim()));
		
		this.nombre = trozos.get(0).trim();
		this.codigo = trozos.get(1).trim();
		this.creditos = new Double(trozos.get(2).trim());
		this.tipo = TipoAsignatura.valueOf(trozos.get(3).trim());
		this.curso = new Integer(trozos.get(4).trim());
	} 
	
	public AsignaturaImpl(String nombre, String codigo, Double creditos, TipoAsignatura tipo, Integer curso, Departamento departamento){
		checkCodigo(codigo);
		checkCreditos(creditos);
		checkCurso(curso);
		this.nombre = nombre;
		this.codigo = codigo;
		this.creditos = creditos;
		this.tipo = tipo;
		this.curso = curso;
		
		//Delego la inicialización en el set
		setDepartamento(departamento);
	}
	private static void checkCodigo(String codigo){
		if(codigo.length()!=7){
			throw new ExcepcionAsignaturaNoValida("El código de la Asignatura debe contener 7 dígitos");
		}
		try {
			new Integer(codigo);
		} catch (NumberFormatException e) {
			throw new ExcepcionAsignaturaNoValida("El código debe estar formado por dígitos");
		}
	}
	
	private static void checkCreditos(Double creditos){
		if(creditos <= 0.){
			throw new ExcepcionAsignaturaNoValida("La asignaturas debe tener un número mayor a 0 créditos");
		}
	}
	
	private static void checkCurso(Integer curso){
		if(curso > 4 || curso < 1){
			throw new ExcepcionAsignaturaNoValida("El curso debe estar comprendido entre 1 y 4");
		}
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public String getAcronimo() {
		String res = "";
		for (int i = 0; i < getNombre().length(); i++) {
			char c = getNombre().charAt(i);
			if(Character.isUpperCase(c)){
				res = res + c;
			}
		}
		return res;
	}

	public String getCodigo() {
		return codigo;
	}

	public Double getCreditos() {
		return creditos;
	}

	public TipoAsignatura getTipo() {
		return tipo;
	}

	public Integer getCurso() {
		return curso;
	}
	
	public Departamento getDepartamento(){
		return departamento;
	}
	
	//ZONAS BIDIRECCIONALES:
	//Constructores
	//Set
	//Metodos que necesiten actualizar
	
	public void setDepartamento(Departamento nuevoDepartamento){
		//1. Tomar elemento actual que se va a cambiar
		Departamento viejoDepartamento = this.departamento;

		//2. Chequear identidad(Lo nuevo debe ser distinto)
		if(nuevoDepartamento != viejoDepartamento){
			//3. Actualizar la propiedad con el nuevo valor
			this.departamento = nuevoDepartamento;	
			
			//4. Eliminarme objeto único al que pertenecia
			if(viejoDepartamento!=null){
				viejoDepartamento.eliminaAsignatura(this);
			}
			
			//5. Añadirme el nuevo objeto único al que pertenece
			if(nuevoDepartamento!=null){
				nuevoDepartamento.nuevaAsignatura(this);
			}
		}
	}
	
	public boolean equals(Object obj) {
		boolean res = false;
		if(obj instanceof Asignatura){
			Asignatura asig = (Asignatura) obj;
			res = getCodigo().equals(asig.getCodigo());
		}
		return res;
	}
	
	public int hashCode(){
		return getCodigo().hashCode();
	}
	
	public int compareTo(Asignatura asig){
		int res = getCodigo().compareTo(asig.getCodigo());

		return res;
	}

	public String toString(){
		return "("+ getCodigo() +") "+ getNombre();
	}

}
