package telran.b7a.student.service;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import telran.b7a.student.dao.StudentMongoRepository;
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
	
	StudentMongoRepository studentRepository;
	ModelMapper modelMapper;
	
	@Autowired
	public StudentServiceImp(StudentMongoRepository studentRepository, ModelMapper modelMapper) {
		this.studentRepository = studentRepository;
		this.modelMapper = modelMapper;
	}

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
		studentRepository.save(studentForUpdate);
		return modelMapper.map(studentForUpdate, StudentCredentialsDto.class);
	}

	@Override
	public boolean addScore(Integer id, ScoreDto scoreDto) {
		Student studentForUpdate = studentRepository.findById(id)
				.orElseThrow(() -> new StudentNotFoundException(id));
		boolean res = studentForUpdate.addScore(scoreDto.getExamName(), scoreDto.getScore());
//		if (studentForUpdate == null) {
//			throw new StudentNotFoundException(id);
//		}	
		studentRepository.save(studentForUpdate);
		return res;
	}

	@Override
	public List<StudentDto> findStudentsByName(String name) {
		return studentRepository.findByNameIgnoreCase(name)
//				.filter(s -> name.equalsIgnoreCase(s.getName()))
				.map(s -> modelMapper.map(s, StudentDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public long getStudentsNamesQuantity(List<String> names) {
		return studentRepository.countByNameInIgnoreCase(names);
	}

	@Override
	public List<StudentDto> getStudentsByExamScore(String exam, int score) {
		return studentRepository.findByExamAndScoreGreaterEqualsThan(exam, score)
				.map(s -> modelMapper.map(s, StudentDto.class))
				.collect(Collectors.toList());
	}

}
