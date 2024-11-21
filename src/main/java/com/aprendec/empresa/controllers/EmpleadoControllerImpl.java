package com.aprendec.empresa.controllers;

import java.sql.SQLException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aprendec.empresa.entities.EmployeeDTO;
import com.aprendec.empresa.services.EmployeeService;

@RestController
@RequestMapping("/empresa")
public class EmpleadoControllerImpl implements EmpleadoController {
	private final EmployeeService employeeService;

	// CONSTRUCTORS
	public EmpleadoControllerImpl(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	// GETS
	// home
	// http://localhost:8080/empresa/
	@GetMapping("/")
	public ResponseEntity<String> welcome() {
		return ResponseEntity.ok("Welcome to Empresa (API)");
	}

	// list employees
	// http://localhost:8080/empresa/listar
	@GetMapping("/listar")
	public ResponseEntity<List<EmployeeDTO>> listarEmpleados() {
		List<EmployeeDTO> employees = null;

		try {
			employees = employeeService.getEmployees();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (employees.isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(employees);
	}

	// search by dni
	// http://localhost:8080/empresa/buscarDni
	@GetMapping("/buscarDni")
	public ResponseEntity<String> buscarDni() {
		return ResponseEntity.ok("Search an employee by dni Endpoint");
	}

	// search by datum
	// http://localhost:8080/empresa/buscarDato
	@GetMapping("/buscarDato")
	public ResponseEntity<String> buscarDatos() {
		return ResponseEntity.ok("Search employees by any datum Endpoint");
	}

	// edit
	// http://localhost:8080/empresa/editar?dni="dni" (sustituye "dni" por un dni)
	@GetMapping("/editar")
	public ResponseEntity<EmployeeDTO> editarEmpleado(@RequestParam("dni") String dni) {
		EmployeeDTO employeeDTO = null;

		try {
			employeeDTO = employeeService.getEmployee(dni);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (employeeDTO == null) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(employeeDTO);
	}

	// error
	// http://localhost:8080/empresa/error
	@GetMapping("/error")
	public ResponseEntity<String> error() {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error");
	}

	// POSTS
	// search salary by dni
	// http://localhost:8080/empresa/buscarDni
	// x-www-form-urlencoded | dni - "dni" (sustituye "dni" por un dni)
	@PostMapping("/buscarDni")
	public ResponseEntity<String> buscarSalario(@RequestParam("dni") String dni) {
		String salary = null;

		try {
			salary = employeeService.getSalary(dni);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (salary == null) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error");
		}

		return ResponseEntity.ok(salary);
	}

	// search employee by datum
	// http://localhost:8080/empresa/buscarDato
	// x-www-form-urlencoded | datum - "dato" (sustituye "dato" por un dato)
	@PostMapping("/buscarDato")
	public ResponseEntity<List<EmployeeDTO>> buscarPorDato(@RequestParam("datum") String datum) {
		List<EmployeeDTO> employees = null;

		try {
			employees = employeeService.getEmployeeData(datum);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (employees.isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(employees);
	}

	// edit employee
	// http://localhost:8080/empresa/editar
	// x-www-form-urlencoded
	// nombre | "nombre" (sustituye por un nombre)
	// dni | "dni" (susutituye por un nombre)
	// sexo | "sexo" (sustituye por un sexo)
	// categoria | "categoria" (sustituye por una categoria)
	// anyos | "anyos" (sustituye por un anyos)
	// dniEmpleadoBuscado | "dniEmpleadoBuscado" (sustituye por un dni, el del empleado que quieres editar)
	@PostMapping("/editar")
	public ResponseEntity<String> editarEmpleado(@ModelAttribute EmployeeDTO employeeDTO,
			@RequestParam("dniEmpleadoBuscado") String dniEmpleadoBuscado) {
		try {
			boolean result = employeeService.edit(employeeDTO, dniEmpleadoBuscado);

			if (result) {
				return ResponseEntity.ok("Empleado editado correctamente");
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al editar el empleado");
			}

		} catch (SQLException e) {
			e.printStackTrace();

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error en la base de datos");
		}
	}

}
