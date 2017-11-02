package fp.grados.tipos.test;

import fp.grados.excepciones.ExcepcionEspacioNoValido;
import fp.grados.tipos.Espacio;
import fp.grados.tipos.EspacioImpl;
import fp.grados.tipos.TipoEspacio;

public class TestEspacio {

	public static void main(String[] args) {
		testConstructorNormal();
		testConstructorExcepcion1();
		testConstructorExcepcion2();
		testSetTipoNormal();
		testSetNombreNormal();
		testSetCapacidadNormal();

	}
	
	/******************************** CASOS DE PRUEBA **************************/

	private static void testConstructorNormal(){
		System.out.println("==================================Probando el constructor");
		testConstructor(TipoEspacio.TEORIA, "A0.12", 150, 0);
	}
	
	private static void testConstructorExcepcion1(){
		System.out.println("==================================Probando el constructor, capacidad menor de 0");
		testConstructor(TipoEspacio.TEORIA, "A0.12", -1, 0);
	}
	
	private static void testConstructorExcepcion2(){
		System.out.println("==================================Probando el constructor, capacidad igual que 0");
		testConstructor(TipoEspacio.TEORIA, "A0.12", 0, 0);
	}
	
	private static void testSetTipoNormal(){
		System.out.println("==================================Probando setTipo");
		Espacio espacio = new EspacioImpl(TipoEspacio.TEORIA, "A0.12", 150, 0);
		testSetTipo(espacio, TipoEspacio.LABORATORIO);
	}
	
	private static void testSetNombreNormal(){
		System.out.println("==================================Probando setNombre");
		Espacio espacio = new EspacioImpl(TipoEspacio.TEORIA, "A0.12", 150, 0);
		testSetNombre(espacio, "H0.13");
	}
	
	private static void testSetCapacidadNormal(){
		System.out.println("==================================Probando setCapacidad");
		Espacio espacio = new EspacioImpl(TipoEspacio.TEORIA, "A0.12", 150, 0);
		testSetCapacidad(espacio, 300);
	}
	
	/******************************** METODOS AUXILIARES **************************/
	
	private static void testConstructor(TipoEspacio tipo, String nombre, Integer capacidad, Integer planta){
		try {
			Espacio espacio = new EspacioImpl(tipo, nombre, capacidad, planta);
			mostrarEspacio(espacio);
		} catch (ExcepcionEspacioNoValido e) {
			System.out.println("******************** Se ha capturado la excepción ExcepcionEspacioNoValida");
		} catch (Exception e) {
			System.out.println("******************** ¡¡¡Se ha capturado una EXCEPCIÓN INESPERADA!!!");
		}
	}
	
	private static void mostrarEspacio(Espacio espacio){
		System.out.println("Espacio --> <"+ espacio + ">");
		System.out.println("\tTipo: <" + espacio.getTipo() + ">");
		System.out.println("\tNombre: <" + espacio.getNombre() + ">");
		System.out.println("\tCapacidad: <" + espacio.getCapacidad() + ">");
		System.out.println("\tPlanta: <" + espacio.getPlanta() + ">");
		
	}
	
	private static void testSetTipo(Espacio espacio, TipoEspacio nuevoTipo){
		try {
			System.out.println("El tipo antes de la operación es: " + espacio.getTipo());
			System.out.println("El nuevo tipo es: " + nuevoTipo);
			espacio.setTipo(nuevoTipo);
			System.out.println("El tipo después de la operación es: " + espacio.getTipo());	
		} catch (ExcepcionEspacioNoValido e) {
			System.out.println("******************** Se ha capturado la excepción ExcepcionEspacioNoValido");
		} catch (Exception e){
			System.out.println("******************** Se ha capturado una excepción inesperada. setTipo no funciona correctamente");
		}
	}
	
	private static void testSetNombre(Espacio espacio, String nuevoNombre){
		try {
			System.out.println("El nombre antes de la operación es: " + espacio.getNombre());
			System.out.println("El nuevo nombre es: " + nuevoNombre);
			espacio.setNombre(nuevoNombre);
			System.out.println("El nombre después de la operación es: " + espacio.getNombre());
		} catch (ExcepcionEspacioNoValido e) {
			System.out.println("******************** Se ha capturado la excepción ExcepcionEspacioNoValido");
		} catch (Exception e) {
			System.out.println("******************** Se ha capturado una excepción inesperada. setNombre no funciona correctamente");
		}
	}
	
	private static void testSetCapacidad(Espacio espacio, Integer nuevaCapacidad){
		try {
			System.out.println("La capacidad antes de la operación es: " + espacio.getCapacidad());
			System.out.println("La nueva capacidad es: " + nuevaCapacidad);
			espacio.setCapacidad(nuevaCapacidad);
			System.out.println("La nueva capacidad después de la operación es: " + espacio.getCapacidad());
		} catch (ExcepcionEspacioNoValido e) {
			System.out.println("******************** Se ha capturado la excepción ExcepcionEspacioNoValido");
		} catch (Exception e) {
			System.out.println("******************** Se ha capturado una excepción inesperada. setCapacidad no funciona correctamente");
		}
	}
}
