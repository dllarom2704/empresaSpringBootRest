package com.aprendec.empresa.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import com.aprendec.empresa.entities.EmployeeDTO;

public interface EmpleadoController {
	ResponseEntity<String> welcome();

	ResponseEntity<List<EmployeeDTO>> listarEmpleados();

	ResponseEntity<String> buscarDni();

	ResponseEntity<String> buscarDatos();

	ResponseEntity<EmployeeDTO> editarEmpleado(@RequestParam("dni") String dni);

	ResponseEntity<String> error();

	ResponseEntity<String> buscarSalario(@RequestParam("dni") String dni);

	ResponseEntity<List<EmployeeDTO>> buscarPorDato(@RequestParam("datum") String datum);

	ResponseEntity<String> editarEmpleado(@ModelAttribute EmployeeDTO employeeDTO,
			@RequestParam("dniEmpleadoBuscado") String dniEmpleadoBuscado);
}
