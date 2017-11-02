package fp.grados.tipos.test;

import java.time.LocalDate;

import fp.grados.tipos.Categoria;
import fp.grados.tipos.Centro;
import fp.grados.tipos.CentroImpl;
import fp.grados.tipos.Departamento;
import fp.grados.tipos.DepartamentoImpl;
import fp.grados.tipos.Despacho;
import fp.grados.tipos.DespachoImpl;
import fp.grados.tipos.Profesor;
import fp.grados.tipos.ProfesorImpl;

public class TestCentro {

	public static void main(String[] args) {

		testGetDespachosPorProfesorNormal();
		
	}
	/******************************** CASOS DE PRUEBA **************************/
	
	private static void testGetDespachosPorProfesorNormal(){
		System.out.println("\n==================ProbandoGetDespachosPorProfesorNormal");
		Departamento dep = new DepartamentoImpl("LSI");
		Profesor profesor1 = new ProfesorImpl("12345678Z", "Juan", "Nadie Nadie", LocalDate.of(1949, 4, 19), "juan.nadie@gmail.com", Categoria.INTERINO, dep);
		Profesor profesor2 = new ProfesorImpl("12345678Z", "Pedro", "Francis Fdez", LocalDate.of(1983, 3, 10), "juan.nadie@gmail.com", Categoria.INTERINO, dep);
		Profesor profesor3 = new ProfesorImpl("12345678Z", "Pepe", "Tenorio Nadie", LocalDate.of(1990, 7, 23), "juan.nadie@gmail.com", Categoria.INTERINO, dep);
		Despacho despacho1 = new DespachoImpl("A3.12", 4, 3, profesor1);
		Despacho despacho2 = new DespachoImpl("H1.34", 5, 1, profesor2);
		Despacho despacho3 = new DespachoImpl("F0.65", 2, 0, profesor3);
		Centro centro = new CentroImpl("ETSII", "Av. Reina Mercedes", 4, 1);
		centro.nuevoEspacio(despacho1);
		centro.nuevoEspacio(despacho2);
		centro.nuevoEspacio(despacho3);
		testGetDespachosPorProfesor(centro);
	}
	
	
	/******************************** METODOS AUXILIARES **************************/

	private static void testGetDespachosPorProfesor(Centro centro){
		try {
			System.out.println("La respuesta de getDespachosPorProfesor es: " + centro.getDespachosPorProfesor());
		} catch (Exception e) {
			System.out.println("******************** Se ha capturado una excepción inesperada.");

		}
	}
	
}
