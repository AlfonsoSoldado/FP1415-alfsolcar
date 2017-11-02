package fp.grados.tipos;

import java.time.LocalDate;

public interface Becario extends Alumno {
	Beca getBeca();
	LocalDate getFechaComienzo();
	LocalDate getFechaFin();
	
	void setFechaComienzo(LocalDate fechaComienzo);
	
}
