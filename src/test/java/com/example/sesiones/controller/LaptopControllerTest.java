package com.example.sesiones.controller;

import com.example.sesiones.entities.Laptop;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LaptopControllerTest {

    private TestRestTemplate testRestTemplate;

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        restTemplateBuilder = restTemplateBuilder.rootUri("http://localhost:" + port);
        testRestTemplate = new TestRestTemplate(restTemplateBuilder);
    }

    @DisplayName("Comprobar el metodo findAll")
    @Test
    void findAll() {
        ResponseEntity<Laptop[]> response  =
                testRestTemplate.getForEntity("/api/laptops", Laptop[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        /** 
        assertEquals(200, response.getStatusCodeValue());
        List<Laptop> books = Arrays.asList(response.getBody());
        System.out.println(books.size());
        **/
    }
    
    @Test
    void findOneById() {
        ResponseEntity<Laptop> response  =
                testRestTemplate.getForEntity("/api/laptop/1", Laptop.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void create() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        Laptop laptop = new Laptop(null,"ACER",1300.00,"LAPTOP ACER I3");
        String latopjson = new Gson().toJson(laptop);
        System.out.println(latopjson);
        
        HttpEntity<String> request = new HttpEntity<>(latopjson,headers);

        ResponseEntity<Laptop> response = testRestTemplate.exchange("/api/laptop/save", HttpMethod.POST, request, Laptop.class);

        Laptop result = response.getBody();

        assertEquals(1L, result.getId());
        assertEquals("ACER", result.getMarca());
    }
    
    @Test
    void delete() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        //guardamos un objeto para eliminar
        Laptop laptop = new Laptop(null,"ACER",1300.00,"LAPTOP ACER I3");
        String latopjson = new Gson().toJson(laptop);

        HttpEntity<String> request = new HttpEntity<>(latopjson,headers);
        ResponseEntity<Laptop> response = testRestTemplate.exchange("/api/laptop/save", HttpMethod.POST, request, Laptop.class);
        System.out.println("Object id: "+response.getBody().getId());
        
        //proceso de eliminacion
        ResponseEntity<Laptop> responsedel = testRestTemplate.exchange("/api/laptop/delete/1", HttpMethod.DELETE, request, Laptop.class);
        assertEquals(HttpStatus.NO_CONTENT, responsedel.getStatusCode());
        
    }

    @Test
    void deleteAll() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        
        //guardamos un objeto para eliminar
        Laptop laptop = new Laptop(null,"ACER",1300.00,"LAPTOP ACER I3");
        String latopjson = new Gson().toJson(laptop);

        HttpEntity<String> request = new HttpEntity<>(latopjson,headers);
        ResponseEntity<Laptop> response = testRestTemplate.exchange("/api/laptop/save", HttpMethod.POST, request, Laptop.class);
        System.out.println("Object id: "+response.getBody().getId());
        
        //ELIMNAMOS
        testRestTemplate.delete("/api/laptop/delete/all");
        System.out.println("ELIMINAMOS");

        //VERIFICAMOS
        ResponseEntity<Laptop[]> response2  = testRestTemplate.getForEntity("/api/laptops", Laptop[].class);
        System.out.println("data: "+response2.getBody().length);
        assertEquals(0, response2.getBody().length);

    }
}