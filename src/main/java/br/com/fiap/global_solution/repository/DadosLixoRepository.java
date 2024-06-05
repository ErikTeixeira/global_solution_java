package br.com.fiap.global_solution.repository;

import br.com.fiap.global_solution.entity.DadosLixo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DadosLixoRepository extends JpaRepository<DadosLixo, Long> {
}
