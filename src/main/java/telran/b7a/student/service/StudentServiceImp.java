package telran.b7a.student.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import telran.b7a.student.dao.StudentRepository;
import telran.b7a.student.dto.ScoreDto;
import telran.b7a.student.dto.StudentCredentialsDto;
import telran.b7a.student.dto.StudentDto;
import telran.b7a.student.dto.UpdateStudentDto;
import telran.b7a.student.dto.exception.StudentNotFoundException;
import telran.b7a.student.model.Student;

@Service
//@Component
public class StudentServiceImp implements StudentService {
	
	@Autowired
	StudentRepository studentRepository;
	
	@Autowired
	ModelMapper modelMapper;
	
	
	@Override
	public boolean addStudent(StudentCredentialsDto studentCredentialsDto) {
		if(studentRepository.findById(studentCredentialsDto.getId()).isPresent()) {
			return false;
		}
//		Student student = new Student(studentCredentialsDto.getId(),
//				studentCredentialsDto.getName(), studentCredentialsDto.getPassword());
		Student student = modelMapper.map(studentCredentialsDto, Student.class);
		studentRepository.save(student);	
		return true;
	}

	@Override
	public StudentDto findStudent(Integer id) {
		Student student = studentRepository.findById(id)
				.orElseThrow(() -> new StudentNotFoundException(id));
//		if(student == null) {
//			return null;
//		}
		return modelMapper.map(student, StudentDto.class);
	}

	@Override
	public StudentDto deleteStudent(Integer id) {
		Student student = studentRepository.findById(id)
				.orElseThrow(() -> new StudentNotFoundException(id));
//		if(student == null) {
//			return null;
//		}
		studentRepository.deleteById(id);
		return modelMapper.map(student, StudentDto.class);
	}

	@Override
	public StudentCredentialsDto updateStudent(Integer id, UpdateStudentDto updateStudentDto) {
		Student studentForUpdate = studentRepository.findById(id)
				.orElseThrow(() -> new StudentNotFoundException(id));
//		if (studentForUpdate == null) {
//			return null;
//		}
		studentForUpdate.setName(updateStudentDto.getName());
		studentForUpdate.setPassword(updateStudentDto.getPassword());
//		studentRepository.save(studentForUpdate);
		return modelMapper.map(studentForUpdate, StudentCredentialsDto.class);
	}

	@Override
	public boolean addScore(Integer id, ScoreDto scoreDto) {
		Student studentForUpdate = studentRepository.findById(id)
				.orElseThrow(() -> new StudentNotFoundException(id));
//		if (studentForUpdate == null) {
//			throw new StudentNotFoundException(id);
//		}
		studentForUpdate.addScore(scoreDto.getExamName(), scoreDto.getScore());
//		studentRepository.save(studentForUpdate);
		return true;
	}

}
