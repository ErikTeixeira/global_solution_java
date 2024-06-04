package br.com.fiap.global_solution.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "TB_PESSOA",
        uniqueConstraints = {
                @UniqueConstraint(name = "UK_EMAIL_PESSOA", columnNames = "EMAIL_PESSOA"),
                @UniqueConstraint(name = "UK_CPF_PESSOA", columnNames = "CPF_PESSOA")
        }
)
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_PESSOA")
    @SequenceGenerator(name = "SQ_PESSOA", sequenceName = "SQ_PESSOA", allocationSize = 1)
    @Column(name = "ID_PESSOA")
    private Long id;

    @Column(name = "NM_PESSOA", length = 50, nullable = false)
    private String nome;

    @Column(name = "SNM_PESSOA", length = 100, nullable = false)
    private String sobrenome;

    @Column(name = "DT_NASCIMENTO", nullable = false)
    private LocalDate dataNascimento;

    @Column(name = "CPF_PESSOA", nullable = false)
    private LocalDate cpf;

    @Column(name = "EMAIL_PESSOA", nullable = false)
    private String email;

}
