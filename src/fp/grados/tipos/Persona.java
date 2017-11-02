package fp.grados.tipos;

import java.time.LocalDate;

public interface Persona extends Comparable<Persona> {

	String getDNI();
	String getNombre();
	String getApellidos();
	LocalDate getFechaNacimiento();
	String getEmail();
	Integer getEdad();
	
	void setDNI(String DNI);
	void setNombre(String nombre);
	void setApellidos(String apellidos);
	void setFechaNacimiento(LocalDate fechaNacimiento);
	void setEmail(String email);
}
