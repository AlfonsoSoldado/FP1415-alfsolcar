package fp.grados.tipos.test;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import fp.grados.excepciones.ExcepcionDespachoNoValido;
import fp.grados.tipos.Categoria;
import fp.grados.tipos.Departamento;
import fp.grados.tipos.DepartamentoImpl;
import fp.grados.tipos.Despacho;
import fp.grados.tipos.DespachoImpl;
import fp.grados.tipos.Profesor;
import fp.grados.tipos.ProfesorImpl;
import fp.grados.tipos.TipoEspacio;

public class TestDespacho {

	public static void main(String[] args) {
		testConstructorNormal1();
		testConstructor1Excepcion1();
		
		testConstructorNormal2();
		
		testConstructorNormal3();
		
		testSetProfesoresNormal();
		testSetProfesoresExcepcion1();
		
		testSetTipoNormal();

	}
	
	/******************************** CASOS DE PRUEBA **************************/
	
	private static void testConstructorNormal1(){
		System.out.println("\n==================================Probando el primer constructor");
		Departamento dep = new DepartamentoImpl("LSI");
		Profesor p1 = new ProfesorImpl("12345678Z", "Juan", "Nadie Nadie", LocalDate.of(1990, 1, 30), "juan.nadie@gmail.com", Categoria.TITULAR, dep);
		Set<Profesor> ConjProf = new HashSet<Profesor>();
		ConjProf.add(p1);
		testConstructor1("H1.13", 3, 1, ConjProf);
	}
	
	private static void testConstructor1Excepcion1(){
		System.out.println("\n==================================Probando el primer constructor, capacidad < número profesores");
		Departamento dep = new DepartamentoImpl("LSI");
		Profesor p1 = new ProfesorImpl("12345678Z", "Juan", "Nadie Nadie", LocalDate.of(1990, 1, 30), "juan.nadie@gmail.com", Categoria.TITULAR, dep);
		Profesor p2 = new ProfesorImpl("12345678Z", "Pedro", "Alguien Alguien", LocalDate.of(1985, 2, 20), "pedro.alguien@gmail.com", Categoria.CATEDRATICO, dep);
		Set<Profesor> ConjProf = new HashSet<Profesor>();
		ConjProf.add(p1);
		ConjProf.add(p2);
		testConstructor1("H1.13", 1, 1, ConjProf);
	}
	
	private static void testConstructorNormal2(){
		System.out.println("\n==================================Probando el segundo constructor");
		Departamento dep = new DepartamentoImpl("LSI");
		Profesor p1 = new ProfesorImpl("12345678Z", "Juan", "Nadie Nadie", LocalDate.of(1990, 1, 30), "juan.nadie@gmail.com", Categoria.TITULAR, dep);
		testConstructor2("H1.13", 3, 1, p1);
	}
	
	private static void testConstructorNormal3(){
		System.out.println("\n==================================Probando el tercer constructor");
		testConstructor3("H1.13", 3, 1);
	}
	
	private static void testSetProfesoresNormal(){
		System.out.println("\n==================================Probando setProfesores");
		Despacho despacho = new DespachoImpl("H1.13", 3, 1);
		Departamento dep = new DepartamentoImpl("LSI");
		Profesor p1 = new ProfesorImpl("12345678Z", "Juan", "Nadie Nadie", LocalDate.of(1990, 1, 30), "juan.nadie@gmail.com", Categoria.TITULAR, dep);
		Set<Profesor> ConjProf = new HashSet<Profesor>();
		ConjProf.add(p1);
		testSetProfesores(despacho, ConjProf);
	}
	
	private static void testSetProfesoresExcepcion1(){
		System.out.println("\n==================================Probando setProfesores, con capacidad < número profesores");
		Despacho despacho = new DespachoImpl("H1.13", 1, 1);
		Departamento dep = new DepartamentoImpl("LSI");
		Profesor p1 = new ProfesorImpl("12345678Z", "Juan", "Nadie Nadie", LocalDate.of(1990, 1, 30), "juan.nadie@gmail.com", Categoria.TITULAR, dep);
		Profesor p2 = new ProfesorImpl("12345678Z", "Pedro", "Alguien Alguien", LocalDate.of(1985, 2, 20), "pedro.alguien@gmail.com", Categoria.CATEDRATICO, dep);
		Set<Profesor> ConjProf = new HashSet<Profesor>();
		ConjProf.add(p1);
		ConjProf.add(p2);
		testSetProfesores(despacho, ConjProf);
	}
	
	private static void testSetTipoNormal(){
		System.out.println("\n==================================Probando setTipo");
		Despacho despacho = new DespachoImpl("H1.13", 3, 1);
		testSetTipo(despacho, TipoEspacio.LABORATORIO);
	}
	
	
	/******************************** METODOS AUXILIARES **************************/
	
	private static void testConstructor1(String nombre, Integer capacidad, Integer planta, Set<Profesor> profesores){
		try {
			Despacho despacho = new DespachoImpl(nombre, capacidad, planta, profesores);
			mostrarDespacho(despacho);
		} catch (ExcepcionDespachoNoValido e) {
			System.out.println("******************** Se ha capturado la excepción ExcepcionDespachoNoValido");
		} catch (Exception e) {
			System.out.println("******************** Se ha capturado una excepción inesperada.");
		}
	}
	
	private static void testConstructor2(String nombre, Integer capacidad, Integer planta, Profesor profesor){
		try {
			Despacho despacho = new DespachoImpl(nombre, capacidad, planta, profesor);
			mostrarDespacho(despacho);
		} catch (ExcepcionDespachoNoValido e) {
			System.out.println("******************** Se ha capturado la excepción ExcepcionDespachoNoValido");
		} catch (Exception e) {
			System.out.println("******************** Se ha capturado una excepción inesperada.");
		}
	}
	
	private static void testConstructor3(String nombre, Integer capacidad, Integer planta){
		try {
			Despacho despacho = new DespachoImpl(nombre, capacidad, planta);
			mostrarDespacho(despacho);
		} catch (ExcepcionDespachoNoValido e) {
			System.out.println("******************** Se ha capturado la excepción ExcepcionDespachoNoValido");
		} catch (Exception e) {
			System.out.println("******************** Se ha capturado una excepción inesperada.");
		}
	}
	
	private static void testSetProfesores(Despacho despacho, Set<Profesor> nuevoConjunto){
		try {
			System.out.println("El conjunto de profesores antes de la operación es: " + despacho.getProfesores());
			System.out.println("El nuevo conjunto de profesores es: " + nuevoConjunto);
			despacho.setProfesores(nuevoConjunto);
			System.out.println("El conjunto de profesores después de la operación es: " + despacho.getProfesores());
		} catch (ExcepcionDespachoNoValido e) {
			System.out.println("******************** Se ha capturado la excepción ExcepcionDespachoNoValido");
		} catch (Exception e) {
			System.out.println("******************** Se ha capturado una excepción inesperada.");
		}
	}
	
	private static void testSetTipo(Despacho despacho, TipoEspacio nuevoTipo){
		try {
			System.out.println("El tipo antes de la operación es: " + despacho.getTipo());
			System.out.println("El nuevo tipo es: " + nuevoTipo);
			despacho.setTipo(nuevoTipo);
			System.out.println("El tipo después de la operación es: " + despacho.getTipo());
		} catch (UnsupportedOperationException e) {
			System.out.println("******************** Se ha capturado la excepción UnsupportedOperationException");
		} catch (Exception e) {
			System.out.println("******************** Se ha capturado una excepción inesperada.");
		}
	}
	
	private static void mostrarDespacho(Despacho despacho){
		System.out.println("Despacho --> <" + despacho + ">");
		System.out.println("\tNombre: <" + despacho.getNombre() + ">");
		System.out.println("\tCapacidad: <" + despacho.getCapacidad() + ">");
		System.out.println("\tPlanta: <" + despacho.getPlanta() + ">");
		System.out.println("\tProfesores: <" + despacho.getProfesores() + ">");
		System.out.println("\tTipo: <" + despacho.getTipo() + ">");
		
	}

}
