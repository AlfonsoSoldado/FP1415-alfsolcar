package fp.grados.tipos;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

import fp.grados.excepciones.ExcepcionPersonaNoValida;

public class PersonaImpl implements Persona{

	private String dni;
	private String nombre;
	private String apellidos;
	private LocalDate fechaNacimiento;
	private String email;
	
	// “12345678Z,Juan,López García,20/01/1998,juan@acmemail.com”
	public PersonaImpl(String s){
		//1. Trocear al cadena.
		String [] trozos = s.split(",");
		//2. Chequear el número de valores es correcta.
		if(trozos.length != 5){
			throw new IllegalArgumentException();
		}
		//3. Chequear restricciones del tipo
		checkDNI(trozos[0].trim());
		checkEmailPersona(trozos[4].trim());
		//4. Hacer copia de los valores de la cadena
		this.dni = trozos[0].trim();
		this.nombre = trozos[1].trim();
		this.apellidos = trozos[2].trim();
		this.fechaNacimiento = LocalDate.parse(trozos[3].trim(), DateTimeFormatter.ofPattern("d/M/y"));
		this.email = trozos[4].trim();
	}
	
	public PersonaImpl(String dni, String nombre, String apellidos, LocalDate fechaNacimiento, String email){
		checkDNI(dni);
		checkEmailPersona(email);
		this.dni = dni;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.fechaNacimiento = fechaNacimiento;
		this.email = email;
	}
	
	public PersonaImpl(String dni, String nombre, String apellidos, LocalDate fechaNacimiento){
		checkDNI(dni);
		this.dni = dni;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.fechaNacimiento = fechaNacimiento;
		this.email = "";
	}
	
	private static void checkDNI(String dni) {
		if(dni.length() != 9){
			throw new ExcepcionPersonaNoValida();
		}
		try {
			String numero = dni.substring(0, dni.length()-1);
			new Integer(numero);
		} catch (NumberFormatException e) {
			throw new ExcepcionPersonaNoValida();
		}
		Character letra = dni.charAt(dni.length()-1);
		if(!Character.isAlphabetic(letra)){
			throw new ExcepcionPersonaNoValida();
		}
		String cadena = "TRWAGMYFPDXBNJZSQVHLCKE";
		String numero = dni.substring(0, dni.length()-1);
		Integer numero2 = Integer.valueOf(numero);
		Character letraReal = cadena.charAt(numero2%23);
		
		if(!letraReal.equals(letra)){
			throw new ExcepcionPersonaNoValida();
		}
	}
	
	private static void checkEmailPersona(String email){
		if(email != ""){
			if(!(email.contains("@"))){
				throw new ExcepcionPersonaNoValida("El email debe contener una @ o estár vacío en su defecto");
			}
		}
	}
	
	
	public String getDNI() {
		return dni;
	}

	public String getNombre() {
		return nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}

	public String getEmail() {
		return email;
	}

	public Integer getEdad() {
		LocalDate now = LocalDate.now();
		LocalDate nacimiento = getFechaNacimiento();
		
		Period transcurrido = nacimiento.until(now);
		Long numeroMeses = transcurrido.toTotalMonths();
		
		return (int) (numeroMeses/12);
	}

	public void setDNI(String dni) {
		checkDNI(dni);
		this.dni = dni;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public void setFechaNacimiento(LocalDate fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public void setEmail(String email) {
		checkEmailPersona(email);
		this.email = email;
	}
	
	public boolean equals(Object obj) {
		boolean res = false;
		if (obj instanceof Persona) {
			Persona Pers = (Persona) obj;
			res = getApellidos().equals(Pers.getApellidos())
					&& getNombre().equals(Pers.getNombre())
					&& getDNI().equals(Pers.getDNI());
		}
		return res;
	}

	public int hashCode() {
		return getApellidos().hashCode() + getNombre().hashCode() * 31
				+ getDNI().hashCode() * 37;
	}

	public int compareTo(Persona Pers) {
		int res = getApellidos().compareTo(Pers.getApellidos());
		if (res == 0) {
			res = getNombre().compareTo(Pers.getNombre());
			if (res == 0) {
				res = getDNI().compareTo(Pers.getDNI());
			}
		}
		return res;
	}
	
	public String toString(){
		return getDNI()+" - "+getApellidos()+", "+getNombre() + " - " + getFechaNacimiento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
	}

}
