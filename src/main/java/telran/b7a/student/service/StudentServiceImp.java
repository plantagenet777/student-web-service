package telran.b7a.student.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import telran.b7a.student.dao.StudentRepository;
import telran.b7a.student.dto.ScoreDto;
import telran.b7a.student.dto.StudentCredentialsDto;
import telran.b7a.student.dto.StudentDto;
import telran.b7a.student.dto.UpdateStudentDto;
import telran.b7a.student.model.Student;

@Service
//@Component
public class StudentServiceImp implements StudentService {
	@Autowired
	StudentRepository studentRepository;
	
	
	@Override
	public boolean addStudent(StudentCredentialsDto studentCredentialsDto) {
		if(studentRepository.findById(studentCredentialsDto.getId()) != null) {
			return false;
		}
		Student student = new Student(studentCredentialsDto.getId(),
				studentCredentialsDto.getName(), studentCredentialsDto.getPassword());
		studentRepository.save(student);	
		return true;
	}

	@Override
	public StudentDto findStudent(Integer id) {
		Student student = studentRepository.findById(id);
		if(student == null) {
			return null;
		}
		return StudentDto.builder()
				.id(student.getId())
				.name(student.getName())
				.scores(student.getScores())
				.build();
	}

	@Override
	public StudentDto deleteStudent(Integer id) {
		Student student = studentRepository.findById(id);
		if(student == null) {
			return null;
		}
		studentRepository.deleteById(id);
		return StudentDto.builder()
				.id(student.getId())
				.name(student.getName())
				.scores(student.getScores())
				.build();
	}

	@Override
	public StudentCredentialsDto updateStudent(Integer id, UpdateStudentDto updateStudentDto) {
		Student studentForUpdate = studentRepository.findById(id);
		if (studentForUpdate == null) {
			return null;
		}
		studentForUpdate.setName(updateStudentDto.getName());
		studentForUpdate.setPassword(updateStudentDto.getPassword());
		studentRepository.save(studentForUpdate);
		return StudentCredentialsDto.builder()
				.id(studentForUpdate.getId())
				.name(studentForUpdate.getName())
				.password(studentForUpdate.getPassword())
				.build();
	}

	@Override
	public boolean addScore(Integer id, ScoreDto scoreDto) {
		Student studentForUpdate = studentRepository.findById(id);
		if (studentForUpdate == null) {
			return false;
		}
		studentForUpdate.addScore(scoreDto.getExamName(), scoreDto.getScore());
		studentRepository.save(studentForUpdate);
		return true;
	}

}
