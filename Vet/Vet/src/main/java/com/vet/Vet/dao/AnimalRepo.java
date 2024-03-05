package com.vet.Vet.dao;

import com.vet.Vet.entities.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AnimalRepo extends JpaRepository<Animal, Integer> {
    List<Animal> findByName(String name);
    @Query("SELECT a FROM Animal a WHERE a.customer.name = ?1")
    List<Animal> findAnimalsByCustomerName(String name);

}
