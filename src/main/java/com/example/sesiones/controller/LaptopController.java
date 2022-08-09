package com.example.sesiones.controller;

import com.example.sesiones.Repository.LaptopRepository;
import com.example.sesiones.entities.Laptop;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class LaptopController {

    private final Logger log = LoggerFactory.getLogger(LaptopController.class);
    private LaptopRepository laptopRepository;

    public LaptopController(LaptopRepository laptopRepository) {
        this.laptopRepository = laptopRepository;
    }
    
    @GetMapping("/laptops")
    @ApiOperation("Listar objetos tipo laptop")
    public List<Laptop> findAll(){
        return laptopRepository.findAll();
    }

    @GetMapping("/laptop/{id}")
    @ApiOperation("Listar un objeto tipo laptop")
    public ResponseEntity<Laptop> findOneById(@PathVariable Long id){
        Optional<Laptop> laptopOptional = laptopRepository.findById(id);
        return laptopOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
        
    }
    
    @PostMapping("/laptop/save")
    @ApiOperation("Guardar un objeto tipo laptop")
    public ResponseEntity<Laptop> create(@RequestBody Laptop laptop){
        
        if(laptop.getId() != null){ 
            log.warn("trying to create a laptop with id");
            return ResponseEntity.badRequest().build();
        }
        Laptop result = laptopRepository.save(laptop);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/laptop/update")
    @ApiOperation("Actualizar un objeto tipo laptop")
    public ResponseEntity<Laptop> update(@RequestBody Laptop laptop){
        if(laptop.getId() == null ){ // si no tiene id quiere decir que sí es una creación
            log.warn("Trying to update a non existent laptop");
            return ResponseEntity.badRequest().build();
        }
        if(!laptopRepository.existsById(laptop.getId())){
            log.warn("Trying to update a non existent laptop");
            return ResponseEntity.notFound().build();
        }
        // El proceso de actualización
        Laptop result = laptopRepository.save(laptop);
        return ResponseEntity.ok(result);
    }

   // @ApiIgnore // ignorar este método para que no aparezca en la documentación de la api Swagger
    @DeleteMapping("/laptop/delete/{id}")
    @ApiOperation("Eliminar un objeto tipo laptop por id")
    public ResponseEntity<Laptop> delete(@PathVariable Long id){

        if(!laptopRepository.existsById(id)){
            log.warn("Trying to delete a non existent laptop");
            return ResponseEntity.notFound().build();
        }

        laptopRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
    
    @DeleteMapping("/laptop/delete/all")
    @ApiOperation("Eliminar todos los objetos tipo laptop")
    public ResponseEntity<Laptop> deleteAll(){
        log.info("REST Request for delete all laptops");
        laptopRepository.deleteAll();
        return ResponseEntity.noContent().build();
    }
}
