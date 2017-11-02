package fp.grados.tipos.test;

import java.time.DayOfWeek;
import java.time.LocalTime;

import fp.grados.excepciones.ExcepcionTutoriaNoValida;
import fp.grados.tipos.Tutoria;
import fp.grados.tipos.TutoriaImpl;

public class TestTutoria {

	public static void main(String[] args) {
		testConstructor1Normal();
		testConstructor1Excepcion1();
		testConstructor1Excepcion2();
		testConstructor1Excepcion3();
		
		testConstructor2Normal();
		testConstructor2Excepcion1();
		testConstructor2Excepcion2();
		testConstructor2Excepcion3();
	}
	
	/******************************** CASOS DE PRUEBA **************************/
	
	private static void testConstructor1Normal(){
		System.out.println("==================================Probando el primer constructor");
		testConstructor1(DayOfWeek.MONDAY, LocalTime.of(10, 30), LocalTime.of(11, 30));
	}
	
	private static void testConstructor1Excepcion1(){
		System.out.println("==================================Probando el primer constructor, siendo dia de la semana sabado");
		testConstructor1(DayOfWeek.SATURDAY, LocalTime.of(10, 30), LocalTime.of(11, 00));
	}
	
	private static void testConstructor1Excepcion2(){
		System.out.println("==================================Probando el primer constructor, siendo dia de la semana domingo");
		testConstructor1(DayOfWeek.SUNDAY, LocalTime.of(10, 30), LocalTime.of(11, 00));
	}
	
	private static void testConstructor1Excepcion3(){
		System.out.println("==================================Probando el primer constructor, duración menor a 15 minutos");
		testConstructor1(DayOfWeek.MONDAY, LocalTime.of(10, 30), LocalTime.of(10, 40));
	}
	
	private static void testConstructor2Normal(){
		System.out.println("==================================Probando el segundo constructor");
		testConstructor2(DayOfWeek.MONDAY, LocalTime.of(10, 30), 30);
	}
	
	private static void testConstructor2Excepcion1(){
		System.out.println("==================================Probando el segundo constructor, siendo dia de la semana sabado");
		testConstructor2(DayOfWeek.SATURDAY, LocalTime.of(10, 30), 30);
	}
	
	private static void testConstructor2Excepcion2(){
		System.out.println("==================================Probando el segundo constructor, siendo dia de la semana domingo");
		testConstructor2(DayOfWeek.SUNDAY, LocalTime.of(10, 30), 30);
	}
	
	private static void testConstructor2Excepcion3(){
		System.out.println("==================================Probando el segundo constructor, duración menor a 15 minutos");
		testConstructor2(DayOfWeek.MONDAY, LocalTime.of(10, 30), 10);
	}
	
	/******************************** METODOS AUXILIARES **************************/

	private static void testConstructor1(DayOfWeek diaSemana, LocalTime horaComienzo, LocalTime horaFin){
		try {
			Tutoria tutoria = new TutoriaImpl(diaSemana, horaComienzo, horaFin);
			mostrarTutoria(tutoria);
		} catch (ExcepcionTutoriaNoValida e) {
			System.out.println("******************** Se ha capturado la excepción ExcepcionTutoriaNoValida");
		} catch (Exception e){
			System.out.println("******************** ¡¡¡Se ha capturado una EXCEPCIÓN INESPERADA!!!");
		}
	}
	
	private static void testConstructor2(DayOfWeek diaSemana, LocalTime horaComienzo, Integer duracion){
		try {
			Tutoria tutoria = new TutoriaImpl(diaSemana, horaComienzo, duracion);
			mostrarTutoria(tutoria);
		} catch (ExcepcionTutoriaNoValida e) {
			System.out.println("******************** Se ha capturado la excepción ExcepcionTutoriaNoValida");
		} catch (Exception e){
			System.out.println("******************** ¡¡¡Se ha capturado una EXCEPCIÓN INESPERADA!!!");
		}
	}
	
	private static void mostrarTutoria(Tutoria tutoria){
		System.out.println("Tutoria --> <"+ tutoria + ">");
		System.out.println("\tDia Semana: <" + tutoria.getDiaSemana() + ">");
		System.out.println("\tHora Comienzo: <" + tutoria.getHoraComienzo() + ">");
		System.out.println("\tHora Fin: <" + tutoria.getHoraFin() + ">");
		System.out.println("\tDuracion: <" + tutoria.getDuracion() + ">");
	}
}
