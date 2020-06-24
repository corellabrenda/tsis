package mx.uam.tsis.ejemplobackend.servicios;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import mx.uam.tsis.ejemplobackend.negocio.modelo.Alumno;

/**
 * Controlador para el API rest
 * 
 *
 *
 */
@RestController
@Slf4j
public class AlumnoController {
	
	// La "base de datos"
	private Map <Integer, Alumno> alumnoRepository = new HashMap <>();
	
	@PostMapping(path = "/alumnos", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> create(@RequestBody Alumno nuevoAlumno) {
		
		// No se deben agregar dos alumnos con la misma matricula
		
		//log.info("RecibÃ­ llamada a create con "+nuevoAlumno);
		
		alumnoRepository.put(nuevoAlumno.getMatricula(), nuevoAlumno);
		
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@GetMapping(path = "/alumnos", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> retrieveAll() {
		return ResponseEntity.status(HttpStatus.OK).body(alumnoRepository.values());
		
	}

	@GetMapping(path = "/alumnos/{matricula}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> retrieve(@PathVariable("matricula") Integer matricula) {
		//log.info("Buscando al alumno con matricula "+matricula);
		
		Alumno alumno = alumnoRepository.get(matricula);
		
		if(alumno != null) {
			return ResponseEntity.status(HttpStatus.OK).body(alumno);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		
		
	}

	@PutMapping(path = "/alumnos/{matricula}/{carrera}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@PathVariable("matricula") Integer matricula,@PathVariable("carrera") String carrera ) {
		Alumno alumno = alumnoRepository.get(matricula);
		System.out.println("ALUMNO"+alumno);
		if(alumno != null) {
			alumno.setCarrera(carrera);
			return ResponseEntity.status(HttpStatus.OK).body(alumno);
		} else {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
		
	}
	
	
	@DeleteMapping(path="/alumnos/{matricula}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?>delete(@PathVariable("matricula") Integer matricula) {
		alumnoRepository.remove(matricula);
		Alumno alumno = alumnoRepository.get(matricula);
		if(alumno==null) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(alumnoRepository.values());
		}else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		
		
	}
 
}
