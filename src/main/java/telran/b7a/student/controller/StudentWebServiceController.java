package telran.b7a.student.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import telran.b7a.student.dto.StudentDto;

@RestController
public class StudentWebServiceController {
	
	@PostMapping("/quantity/students")
	public StudentDto quantityStudents(@RequestParam StudentDto studentDto) {
		return StudentDto.builder()
				.name(studentDto.getName())
				.build();
	}

}
