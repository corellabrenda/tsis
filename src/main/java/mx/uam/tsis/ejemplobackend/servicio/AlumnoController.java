package mx.uam.tsis.ejemplobackend.servicio;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import mx.uam.tsis.ejemplobackend.negocio.modelo.Alumno;
import mx.uam.tsis.ejemplobackend.negocion.AlumnoService;

/**
 * Controlador para el API rest
 * 
 * @author humbertocervantes
 *
 */
@RestController
@Slf4j
public class AlumnoController {
	
	@Autowired
	private AlumnoService alumnoService;
	
	
	@PostMapping(path = "/alumnos", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> create(@RequestBody @Valid Alumno nuevoAlumno) {
				
		log.info("Recibí llamada a create con "+nuevoAlumno);
		
		Alumno alumno = alumnoService.create(nuevoAlumno);
		
		if(alumno != null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(alumno);			
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("no se puede crear alumno");
		}
	

	}
	
	@GetMapping(path = "/alumnos", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> retrieveAll() {
		
		List <Alumno> result = alumnoService.retrieveAll();
		
		return ResponseEntity.status(HttpStatus.OK).body(result); 
		
	}

	@GetMapping(path = "/alumnos/{matricula}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> retrieve(@Valid @PathVariable ("matricula") Integer matricula) {
		log.info("Buscando al alumno con matricula "+matricula);
		Alumno alumno=alumnoService.retrieve(matricula);
		if(alumno != null) {
			return ResponseEntity.status(HttpStatus.OK).body(alumno);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El alumno con la matricula"+matricula+"no exite");
		}
	
		
	}
	

	@PutMapping(path = "/alumnos/{matricula}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@Valid @PathVariable("matricula") Integer matricula,@RequestBody  @Valid Alumno nuevoAlumno ) {
		Alumno alumno = alumnoService.modifica(matricula, nuevoAlumno);
		System.out.println("ALUMNO"+alumno);
		if(alumno != null) {
			return ResponseEntity.status(HttpStatus.OK).body(alumno);
		} else {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
		
	}
	
	
	@DeleteMapping(path="/alumnos/{matricula}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?>delete(@Valid @PathVariable("matricula") Integer matricula) {
		Alumno alumno = alumnoService.borra(matricula);
		if(alumno!=null) {
			return ResponseEntity.status(HttpStatus.OK).body("ha sido eliminado: "+alumno);
		}else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		
		
	}
 
}
