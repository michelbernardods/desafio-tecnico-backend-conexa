package com.conexa.saude.repository;

import com.conexa.saude.model.Medico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MedicoRepository extends JpaRepository<Medico, UUID> {

}
