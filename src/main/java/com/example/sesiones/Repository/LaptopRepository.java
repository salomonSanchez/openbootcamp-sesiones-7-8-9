package com.example.sesiones.Repository;

import com.example.sesiones.entities.Laptop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LaptopRepository extends JpaRepository<Laptop,Long> {
}
