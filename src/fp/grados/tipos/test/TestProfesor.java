package fp.grados.tipos.test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

import fp.grados.excepciones.ExcepcionProfesorNoValido;
import fp.grados.tipos.Categoria;
import fp.grados.tipos.Departamento;
import fp.grados.tipos.DepartamentoImpl;
import fp.grados.tipos.Profesor;
import fp.grados.tipos.ProfesorImpl;
import fp.grados.tipos.Tutoria;
import fp.grados.tipos.TutoriaImpl;

public class TestProfesor {

	public static void main(String[] args) {
		testConstructor1Normal();
		testConstructor1Excepcion1();
		
		testSetCategoriaNormal();
		
		testNuevaTutoriaNormal();
		
		testBorraTutoriaNormal();
		
		testBorraTutoriasNormal();

	}
	/******************************** CASOS DE PRUEBA **************************/
	
	private static void testConstructor1Normal(){
		System.out.println("\n==================================Probando el primer constructor");
		Departamento dep = new DepartamentoImpl("LSI");
		testConstructor1("12345678Z", "Juan", "Nadie Nadie", LocalDate.of(1950, 3, 15), "juan.nadie@gmail.com", Categoria.INTERINO, dep);
	}
	
	private static void testConstructor1Excepcion1(){
		System.out.println("\n==================================Probando el primer constructor, edad < 18");
		Departamento dep = new DepartamentoImpl("LSI");
		testConstructor1("12345678Z", "Juan", "Nadie Nadie", LocalDate.of(1999, 3, 15), "juan.nadie@gmail.com", Categoria.INTERINO, dep);
	}
	
	private static void testSetCategoriaNormal(){
		System.out.println("\n==================================Probando SetCategoria");
		Departamento dep = new DepartamentoImpl("LSI");
		Profesor p1 = new ProfesorImpl("12345678Z", "Juan", "Nadie Nadie", LocalDate.of(1950, 3, 15), "juan.nadie@gmail.com", Categoria.INTERINO, dep);
		testSetCategoria(p1, Categoria.CONTRATADO_DOCTOR);
	}
	
	private static void testNuevaTutoriaNormal(){
		System.out.println("\n==================================Probando nuevaTutoria");
		Departamento dep = new DepartamentoImpl("LSI");
		Profesor profesor =  new ProfesorImpl("12345678Z", "Juan", "Nadie Nadie", LocalDate.of(1950, 3, 15), "juan.nadie@gmail.com", Categoria.INTERINO, dep);
		testNuevaTutoria(profesor, LocalTime.of(11, 00), 30, DayOfWeek.WEDNESDAY);
	}
	
	private static void testBorraTutoriaNormal(){
		System.out.println("\n==================================Probando borraTutoria");
		Departamento dep = new DepartamentoImpl("LSI");
		Profesor profesor = new ProfesorImpl("12345678Z", "Juan", "Nadie Nadie", LocalDate.of(1950, 3, 15), "juan.nadie@gmail.com", Categoria.INTERINO, dep);
		profesor.nuevaTutoria(LocalTime.of(11, 00), 30, DayOfWeek.WEDNESDAY);
		testBorraTutoria(profesor, LocalTime.of(11, 00), DayOfWeek.WEDNESDAY);
	}
	
	private static void testBorraTutoriasNormal(){
		System.out.println("\n==================================Probando borraTutorias");
		Departamento dep = new DepartamentoImpl("LSI");
		Profesor profesor = new ProfesorImpl("12345678Z", "Juan", "Nadie Nadie", LocalDate.of(1950, 3, 15), "juan.nadie@gmail.com", Categoria.INTERINO, dep);
		profesor.nuevaTutoria(LocalTime.of(11, 00), 30, DayOfWeek.WEDNESDAY);
		profesor.nuevaTutoria(LocalTime.of(9, 30), 30, DayOfWeek.THURSDAY);
		profesor.nuevaTutoria(LocalTime.of(12, 00), 60, DayOfWeek.MONDAY);
		testBorraTutorias(profesor);
	}

	/******************************** METODOS AUXILIARES **************************/
	
	private static void testConstructor1(String dni, String nombre, String apellidos, LocalDate fechaNacimiento, String email, Categoria categoria, Departamento dep){
		try {
			Profesor profesor = new ProfesorImpl(dni, nombre, apellidos, fechaNacimiento, email, categoria, dep);
			mostrarProfesor(profesor);
		} catch (ExcepcionProfesorNoValido e) {
			System.out.println("******************** Se ha capturado la excepción ExcepcionProfesorNoValido");
		} catch (Exception e) {
			System.out.println("******************** Se ha capturado una excepción inesperada.");
		}
	}
	
	private static void testSetCategoria(Profesor profesor, Categoria nuevaCategoria){
		try {
			System.out.println("La categoría antes de la operación es: " + profesor.getCategoria());
			System.out.println("La nueva categoría es: " + nuevaCategoria);
			profesor.setCategoria(nuevaCategoria);
			System.out.println("La categoría después de la operación es: " + profesor.getCategoria());
		} catch (ExcepcionProfesorNoValido e) {
			System.out.println("******************** Se ha capturado la excepción ExcepcionProfesorNoValido");
		} catch (Exception e) {
			System.out.println("******************** Se ha capturado una excepción inesperada.");
		}
	}
	
	private static void testNuevaTutoria(Profesor profesor, LocalTime horaComienzo, Integer duracionMinutos, DayOfWeek dia){
		try {
			System.out.println("El conjunto de tutorias antes de la operación es: " + profesor.getTutorias());
			Tutoria tutoria = new TutoriaImpl(dia, horaComienzo, duracionMinutos);
			System.out.println("La tutoría a añadir al conjunto es: " + tutoria);
			profesor.nuevaTutoria(horaComienzo, duracionMinutos, dia);
			System.out.println("El conjunto de tutorias después de la operación es: " + profesor.getTutorias());
		} catch (ExcepcionProfesorNoValido e) {
			System.out.println("******************** Se ha capturado la excepción ExcepcionProfesorNoValido");
		} catch (Exception e) {
			System.out.println("******************** Se ha capturado una excepción inesperada.");
		}
	}
	
	private static void testBorraTutoria(Profesor profesor, LocalTime horaComienzo, DayOfWeek dia){
		try {
			System.out.println("El conjunto de tutorias antes de la operación es: " + profesor.getTutorias());
			Tutoria tutoria = new TutoriaImpl(dia, horaComienzo, 15);
			System.out.println("La tutoría a borrar del conjunto es: " + tutoria);
			profesor.borraTutoria(horaComienzo, dia);
			System.out.println("El conjunto de tutorías después de la operación es: " + profesor.getTutorias());
		} catch (ExcepcionProfesorNoValido e) {
			System.out.println("******************** Se ha capturado la excepción ExcepcionProfesorNoValido");
		} catch (Exception e) {
			System.out.println("******************** Se ha capturado una excepción inesperada.");
		}
	}
	
	private static void testBorraTutorias(Profesor profesor){
		try {
			System.out.println("El conjunto de tutorias antes de la operación es: " + profesor.getTutorias());
			profesor.borraTutorias();
			System.out.println("El conjunto después de haber borrado todas las tutorias es: " + profesor.getTutorias());
		} catch (ExcepcionProfesorNoValido e) {
			System.out.println("******************** Se ha capturado la excepción ExcepcionProfesorNoValido");
		} catch (Exception e) {
			System.out.println("******************** Se ha capturado una excepción inesperada.");
		}
	}
	
	private static void mostrarProfesor(Profesor profesor){
		System.out.println("Profesor --> <" + profesor + ">");
		System.out.println("\tDNI: <" + profesor.getDNI() + ">");
		System.out.println("\tNombre: <" + profesor.getNombre() + ">");
		System.out.println("\tApellidos: <" + profesor.getApellidos() + ">");
		System.out.println("\tFecha Nacimiento: <" + profesor.getFechaNacimiento() + ">");
		System.out.println("\tEmail: <" + profesor.getEmail() + ">");
		System.out.println("\tCategoria: <" + profesor.getCategoria() + ">");
		System.out.println("\tTutorias: <" + profesor.getTutorias() + ">");
		
	}
}
