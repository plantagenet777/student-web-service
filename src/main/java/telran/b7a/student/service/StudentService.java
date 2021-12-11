package telran.b7a.student.service;

import telran.b7a.student.dto.ScoreDto;
import telran.b7a.student.dto.StudentCredentialsDto;
import telran.b7a.student.dto.StudentDto;
import telran.b7a.student.dto.UpdateStudentDto;

public interface StudentService {
	// DTO
	
	boolean addStudent(StudentCredentialsDto studentCredentialsDto);
	
	StudentDto findStudent(Integer id);
	
	StudentDto deleteStudent(Integer id);
	
	StudentCredentialsDto updateStudent(Integer id, UpdateStudentDto updateStudentDto);
	
	boolean addScore(Integer id, ScoreDto scoreDto);

}
