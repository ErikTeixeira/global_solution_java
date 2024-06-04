package br.com.fiap.global_solution.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder


@Entity
@Table(name = "TB_DADOSLIXO")
public class DadosLixo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_DADOSLIXO")
    @SequenceGenerator(name = "SQ_DADOSLIXO", sequenceName = "SQ_DADOSLIXO", allocationSize = 1)
    @Column(name = "ID_DADOSLIXO")
    private Long id;

    private String tipoLixo;

    private LocalDateTime dataEnvio;

    private String condicaoAmbiente;

}
