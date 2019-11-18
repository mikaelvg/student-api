package ph.devops.student.controller;

import ph.devops.student.exception.ResourceNotFoundException;
import ph.devops.student.model.SearchCriteria;
import ph.devops.student.model.Student;
import ph.devops.student.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ph.devops.student.search.StudentSpecification;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api")
public class StudentController {

    Date date= new Date();
    long time = date.getTime();
    Timestamp timestamp = new Timestamp(time);

    @Autowired
    StudentRepository studentRepository;

    @GetMapping("/student")
    public List<Student> getAllstudent() {
        return studentRepository.findAll();
    }

    @PostMapping("/student")
    public Student createStudent(@Valid @RequestBody Student student) {

        student.setCreatedAt(timestamp);
        student.setUpdatedAt(timestamp);
        return studentRepository.save(student);
    }

    @GetMapping("/student/{id}")
    public Student getStudentById(@PathVariable(value = "id") Long studentId) {
        return studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student", "id", studentId));
    }

    @PutMapping("/student/{id}")
    public Student updateStudent(@PathVariable(value = "id") Long studentId,
                                 @Valid @RequestBody Student studentDetails) {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student", "id", studentId));

        student.setFirstName(studentDetails.getFirstName());
        student.setLastName(studentDetails.getLastName());
        student.setClass1(studentDetails.getClass1());
        student.setUpdatedAt(timestamp);

        Student updatedStudent = studentRepository.save(student);
        System.out.println(updatedStudent.toString());
         return  updatedStudent;
    }

    @DeleteMapping("/student/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable(value = "id") Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student", "id", studentId));
        studentRepository.delete(student);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/fetchstudent")
    @ResponseBody
    public List<Student> getStudentByField (@RequestParam(name = "fieldName") String fieldName, @RequestParam String value) {

        StudentSpecification spec =
                new StudentSpecification(new SearchCriteria(fieldName, ":", value));
        List<Student> results = studentRepository.findAll(spec);
        return results;
    }
}



