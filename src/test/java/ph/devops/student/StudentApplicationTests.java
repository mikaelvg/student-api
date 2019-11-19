package ph.devops.student;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import ph.devops.student.model.SearchCriteria;
import ph.devops.student.model.Student;
import ph.devops.student.repository.StudentRepository;
import ph.devops.student.search.StudentSpecification;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static org.junit.Assert.assertEquals;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = StudentApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class StudentApplicationTests {

    private int definedPort = 9080;

    private Student classA1;

    @Autowired
    private StudentRepository repository;

    private String baseUrl = "http://localhost:" + definedPort + "/api/student/";
    private String existingTestID = "1";
    private String nonExistingTestID = "6";

    // NORMAL TEST CASES

    @Test
    public void testGetOneStudent() throws URISyntaxException, JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        String url = baseUrl + existingTestID;
        URI uri = new URI(url);
        ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);
        assertEquals(200, result.getStatusCodeValue());
        assertEquals(true, result.getBody().contains("\"firstName\":\"Mikael\",\"lastName\":\"Gulapa\""));
    }

    @Test
    public void testGetAllStudents() throws URISyntaxException, JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        URI uri = new URI(baseUrl);
        ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);
        JSONArray jsonArray = JsonPath.read(result.getBody(), "$");
        assertEquals(200, result.getStatusCodeValue());
        assertEquals(5, jsonArray.size());
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:data.sql")
    public void testUpdateStudent() throws URISyntaxException, JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        String url = baseUrl + existingTestID;
        URI uri = new URI(url);
        Student student = new Student("Kim12345", "Gulapa", "First Class");
        restTemplate.put(uri, student);

        // Assert
        ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);
        assertEquals(200, result.getStatusCodeValue());
        assertEquals(true, result.getBody().contains("\"firstName\":\"Kim12345\",\"lastName\":\"Gulapa\""));
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:data.sql")
    public void testCreateStudent() throws URISyntaxException, JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        URI uri = new URI(baseUrl);
        Student student = new Student("Jake", "Peralta", "First Class");
        ResponseEntity<String> result1 = restTemplate.postForEntity(uri, student, String.class);

        // Note: 5 existing records in database.  Thus, expected result is 5 + 1 = 6
        ResponseEntity<String> result2 = restTemplate.getForEntity(uri, String.class);
        JSONArray jsonArray = JsonPath.read(result2.getBody(), "$");
        assertEquals(200, result2.getStatusCodeValue());
        assertEquals(6, jsonArray.size());
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:data.sql")
    public void testDeleteStudent() throws URISyntaxException, JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        String url = baseUrl + existingTestID;
        URI uri = new URI(url);
        restTemplate.delete(uri);

        // Note: 5 existing records in database.  Thus, expected result is 5 - 1 = 4
        ResponseEntity<String> result = restTemplate.getForEntity(baseUrl, String.class);
        JSONArray jsonArray = JsonPath.read(result.getBody(), "$");
        assertEquals(200, result.getStatusCodeValue());
        assertEquals(4, jsonArray.size());
    }

    @Test
    public void testGetClassA1List() throws URISyntaxException {
        String fieldName = "class1";
        String value = "A1";
        RestTemplate restTemplate = new RestTemplate();
        String baseUrl = "http://localhost:" + definedPort + "/api/fetchstudent?fieldName=" + fieldName + "&value=" + value;
        URI uri = new URI(baseUrl);
        ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);
        JSONArray jsonArray = JsonPath.read(result.getBody(), "$");
        assertEquals(200, result.getStatusCodeValue());
        assertEquals(3, jsonArray.size());
    }

    // ERROR TEST CASES
    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:data.sql")
    public void testGetNoneExistingStudent() throws URISyntaxException, JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        String url = baseUrl + nonExistingTestID;
        URI uri = new URI(url);
        try {
            ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);
        } catch (HttpClientErrorException e) {
            assertEquals(404, e.getRawStatusCode());
        }
    }


    @Test
    public void testGetNotExistingClass() throws URISyntaxException {
        String fieldName = "class1";
        String value = "AXXX";
        RestTemplate restTemplate = new RestTemplate();
        String baseUrl = "http://localhost:" + definedPort + "/api/fetchstudent?fieldName=" + fieldName + "&value=" + value;
        URI uri = new URI(baseUrl);
        ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);
        JSONArray jsonArray = JsonPath.read(result.getBody(), "$");
        assertEquals(200, result.getStatusCodeValue());
        assertEquals(0, jsonArray.size());
    }
}
