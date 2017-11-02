package fp.grados.tipos;

import java.time.DayOfWeek;
import java.time.LocalTime;

import fp.grados.excepciones.ExcepcionTutoriaNoValida;

public class TutoriaImpl implements Tutoria {

	private DayOfWeek diaSemana;
	private LocalTime horaComienzo;
	private LocalTime horaFin;

	// “L,15:30,17:30”
	public TutoriaImpl(String s){
		String [] trozos = s.split(", ");
		if(trozos.length != 3){
			throw new IllegalArgumentException();
		}
		
		checkDiaSemana(encontrarDiaSemana(trozos[0].trim()));
		this.diaSemana = encontrarDiaSemana(trozos[0].trim());
		this.horaComienzo = LocalTime.parse((trozos[1].trim())); 
		this.horaFin = LocalTime.parse(trozos[2].trim()); 
		checkDuracion(getDuracion());
	}
	
	private DayOfWeek encontrarDiaSemana(String LetraDiaSemana){
		DayOfWeek dia = DayOfWeek.MONDAY;
		switch (LetraDiaSemana) {
		case "L":
			dia = DayOfWeek.MONDAY;
			break;
		case "M":
			dia = DayOfWeek.TUESDAY;
			break;
		case "X":
			dia = DayOfWeek.WEDNESDAY;
			break;
		case "J":
			dia = DayOfWeek.THURSDAY;
			break;
		case "V":
			dia = DayOfWeek.FRIDAY;
			break;
		case "S":
			dia = DayOfWeek.SATURDAY;
			break;
		case "D":
			dia = DayOfWeek.SUNDAY;
			break;
		default:
			throw new ExcepcionTutoriaNoValida();
		}
		
		return dia;
	}
	
	public TutoriaImpl(DayOfWeek diaSemana, LocalTime horaComienzo, LocalTime horaFin){
		checkDiaSemana(diaSemana);
		checkDuracionOtro(horaComienzo, horaFin);
		this.diaSemana = diaSemana;
		this.horaComienzo = horaComienzo;
		this.horaFin = horaFin;
	}

	public TutoriaImpl(DayOfWeek diaSemana, LocalTime horaComienzo, Integer duracion){
		checkDiaSemana(diaSemana);
		checkDuracion(duracion);
		this.diaSemana = diaSemana;
		this.horaComienzo = horaComienzo;
		this.horaFin = horaComienzo.plusMinutes(duracion);
	}
	
	private static void checkDiaSemana(DayOfWeek diaSemana){
		if(diaSemana == DayOfWeek.SATURDAY || diaSemana == DayOfWeek.SUNDAY){
			throw new ExcepcionTutoriaNoValida("El día de la semana debe estar comprendido entre Lunes y Viernes");
		}
	}
	
	private static void checkDuracion(Integer duracion){
		if(duracion < 15){
			throw new ExcepcionTutoriaNoValida("La duración debe ser mayor que 15");
		}
	}
	
	private static void checkDuracionOtro(LocalTime horaComienzo, LocalTime horaFin){
		Integer res = 0;
		Integer MinFin = horaFin.getHour()*60 + horaFin.getMinute();
		Integer MinComienzo = horaComienzo.getHour()*60 + horaComienzo.getMinute();
		res = MinFin - MinComienzo;
		if(res < 15){
			throw new ExcepcionTutoriaNoValida("La duración debe ser mayor que 15");
		}
	}

	public DayOfWeek getDiaSemana() {
		return diaSemana;
	}

	public LocalTime getHoraComienzo() {
		return horaComienzo;
	}

	public LocalTime getHoraFin() {
		return horaFin;
	}

	public Integer getDuracion() {
		Integer res = 0;
		Integer MinFin = horaFin.getHour()*60 + horaFin.getMinute();
		Integer MinComienzo = horaComienzo.getHour()*60 + horaComienzo.getMinute();
		res = MinFin - MinComienzo;
		return res;
	}
	
	public boolean equals(Object obj) {
		boolean res = false;
		if(obj instanceof Tutoria){
			Tutoria tutoria = (Tutoria) obj;
			res = getDiaSemana().equals(tutoria.getDiaSemana())
					&& getHoraComienzo().equals(tutoria.getHoraComienzo());
		}
		return res;
	}
	
	public int hashCode(){
		return getDiaSemana().hashCode() + getHoraComienzo().hashCode()*31;
	}
	
	public int compareTo(Tutoria tutoria){
		int res = getDiaSemana().compareTo(tutoria.getDiaSemana());
		if(res == 0){
			res = getHoraComienzo().compareTo(tutoria.getHoraComienzo());
		}

		return res;
	}

	public String toString(){
		String letraSemana = "";
		if(diaSemana == DayOfWeek.MONDAY){
			letraSemana = "L";
		} else if(diaSemana == DayOfWeek.TUESDAY){
			letraSemana = "M";
		} else if(diaSemana == DayOfWeek.WEDNESDAY){
			letraSemana = "X";
		} else if(diaSemana == DayOfWeek.THURSDAY){
			letraSemana = "J";
		} else {
			letraSemana = "V";
		}
		return letraSemana+" "+horaComienzo+"-"+horaFin;
	}

}
