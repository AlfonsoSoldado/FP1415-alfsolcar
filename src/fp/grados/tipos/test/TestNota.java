package fp.grados.tipos.test;

import fp.grados.excepciones.ExcepcionNotaNoValida;
import fp.grados.tipos.Asignatura;
import fp.grados.tipos.AsignaturaImpl;
import fp.grados.tipos.Convocatoria;
import fp.grados.tipos.Departamento;
import fp.grados.tipos.DepartamentoImpl;
import fp.grados.tipos.Nota;
import fp.grados.tipos.NotaImpl;
import fp.grados.tipos.TipoAsignatura;

public class TestNota {

	public static void main(String[] args) {
		testConstructor1Normal();
		testConstructor1Excepcion1();
		testConstructor1Excepcion2();
		testConstructor1Excepcion3();
		
		testConstructor2Normal();
		testConstructor2Excepcion1();
		testConstructor2Excepcion2();

	}
	
	/******************************** CASOS DE PRUEBA **************************/
	
	private static void testConstructor1Normal(){
		System.out.println("==================================Probando el primer constructor");
		Departamento dep = new DepartamentoImpl("MA1");
		Asignatura asig = new AsignaturaImpl("Calculo Infinitesimal y Numérico", "0001234", 6., TipoAsignatura.PRIMER_CUATRIMESTRE, 1, dep);
		testConstructor1(asig, 1, Convocatoria.PRIMERA, 7., false);
	}
	
	private static void testConstructor1Excepcion1(){
		System.out.println("==================================Probando el primer constructor, valor menor que cero");
		Departamento dep = new DepartamentoImpl("MA1");
		Asignatura asig = new AsignaturaImpl("Calculo Infinitesimal y Numérico", "0001234", 6., TipoAsignatura.PRIMER_CUATRIMESTRE, 1, dep);
		testConstructor1(asig, 1, Convocatoria.PRIMERA, -1., false);
	}
	
	private static void testConstructor1Excepcion2(){
		System.out.println("==================================Probando el primer constructor, valor mayor que cero");
		Departamento dep = new DepartamentoImpl("MA1");
		Asignatura asig = new AsignaturaImpl("Calculo Infinitesimal y Numérico", "0001234", 6., TipoAsignatura.PRIMER_CUATRIMESTRE, 1, dep);
		testConstructor1(asig, 1, Convocatoria.PRIMERA, 11., false);
	}
	
	private static void testConstructor1Excepcion3(){
		System.out.println("==================================Probando el primer constructor, valor menor que nueve, y mencion de honor true");
		Departamento dep = new DepartamentoImpl("MA1");
		Asignatura asig = new AsignaturaImpl("Calculo Infinitesimal y Numérico", "0001234", 6., TipoAsignatura.PRIMER_CUATRIMESTRE, 1, dep);
		testConstructor1(asig, 1, Convocatoria.PRIMERA, 7., true);
	}
	
	private static void testConstructor2Normal(){
		System.out.println("==================================Probando el segundo constructor");
		Departamento dep = new DepartamentoImpl("MA1");
		Asignatura asig = new AsignaturaImpl("Calculo Infinitesimal y Numérico", "0001234", 6., TipoAsignatura.PRIMER_CUATRIMESTRE, 1, dep);
		testConstructor2(asig, 1, Convocatoria.PRIMERA, 7.);
	}
	
	private static void testConstructor2Excepcion1(){
		System.out.println("==================================Probando el segundo constructor, valor menor que cero");
		Departamento dep = new DepartamentoImpl("MA1");
		Asignatura asig = new AsignaturaImpl("Calculo Infinitesimal y Numérico", "0001234", 6., TipoAsignatura.PRIMER_CUATRIMESTRE, 1, dep);
		testConstructor2(asig, 1, Convocatoria.PRIMERA, -1.);
	}
	
	private static void testConstructor2Excepcion2(){
		System.out.println("==================================Probando el segundo constructor, valor mayor que cero");
		Departamento dep = new DepartamentoImpl("MA1");
		Asignatura asig = new AsignaturaImpl("Calculo Infinitesimal y Numérico", "0001234", 6., TipoAsignatura.PRIMER_CUATRIMESTRE, 1, dep);
		testConstructor2(asig, 1, Convocatoria.PRIMERA, 11.);
	}
	
	/******************************** METODOS AUXILIARES **************************/
	
	private static void testConstructor1(Asignatura asignatura, Integer cursoAcademico, Convocatoria convocatoria, Double valor, Boolean mencionHonor){
		try {
			Nota nota = new NotaImpl(asignatura, cursoAcademico, convocatoria, valor, mencionHonor);
			mostrarNota(nota);
		} catch (ExcepcionNotaNoValida e) {
			System.out.println("******************** Se ha capturado la excepción ExcepcionEspacioNoValida");
		} catch (Exception e){
			System.out.println("******************** ¡¡¡Se ha capturado una EXCEPCIÓN INESPERADA!!!");
		}
	}
	
	private static void testConstructor2(Asignatura asignatura, Integer cursoAcademico, Convocatoria convocatoria, Double valor){
		try {
			Nota nota = new NotaImpl(asignatura, cursoAcademico, convocatoria, valor);
			mostrarNota(nota);
		} catch (ExcepcionNotaNoValida e) {
			System.out.println("******************** Se ha capturado la excepción ExcepcionEspacioNoValida");
		} catch (Exception e){
			System.out.println("******************** ¡¡¡Se ha capturado una EXCEPCIÓN INESPERADA!!!");
		}
	}
	
	private static void mostrarNota(Nota nota){
		System.out.println("Nota --> <"+ nota +">");
		System.out.println("\tAsignatura: <"+ nota.getAsignatura() + ">");
		System.out.println("\tCurso Académico: <"+ nota.getCursoAcademico() + ">");
		System.out.println("\tConvocatoria: <"+ nota.getConvocatoria() +">");
		System.out.println("\tValor: <"+ nota.getValor() + ">");
		System.out.println("\tCalificacion: <" + nota.getCalificacion() + ">");
		System.out.println("\tMencion de Honor: <"+ nota.getMencionHonor() + ">");
	}

}
