package br.com.fiap.global_solution.repository;

import br.com.fiap.global_solution.entity.FotoLixo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FotoLixoRepository extends JpaRepository<FotoLixo, Long> {
}
