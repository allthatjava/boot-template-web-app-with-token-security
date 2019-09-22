package brian.template.boot.web.app.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import brian.template.boot.web.app.domain.AppPost;
import brian.template.boot.web.app.domain.AuthRequest;
import brian.template.boot.web.app.domain.AuthResponse;

//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@IfProfileValue(name = "spring.profiles.active", values = { "default", "ist,ist-azr-use2", "ist,ist-azr-cc" })
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class PostControllerTest {

	@LocalServerPort
	private int port;
	
	private RestTemplate restTemplate = new RestTemplate();
	
    @Test
    public void testController_withNoParams_shouldReturnAllList() throws Exception{

    	HttpHeaders headers = new HttpHeaders();
    	headers.add("Authorization", "Bearer "+getToken());
    	
    	
    	ResponseEntity<List<AppPost>> response = restTemplate.exchange("http://localhost:"+port+"/posts", 
    												HttpMethod.GET, new HttpEntity(headers), 
    												new ParameterizedTypeReference<List<AppPost>>() {});
    	
    	assertThat(response.getBody().size()).isGreaterThan(0);

    }
    
    private String getToken() throws JsonProcessingException {
    	HttpHeaders headers = new HttpHeaders();
    	headers.setContentType(MediaType.APPLICATION_JSON);

    	String jsonString = new ObjectMapper().writeValueAsString(new AuthRequest("tester1","tester1pw"));
    	System.out.println("jsonString:"+jsonString);
    	
    	ResponseEntity<AuthResponse> response = restTemplate.postForEntity("http://localhost:"+port+"/authenticate", 
    												new HttpEntity<String>(jsonString, headers), AuthResponse.class);
    	
    	String token = response.getBody().getToken();
    	System.out.println("Token:"+token);
    	return token;
    }
}
