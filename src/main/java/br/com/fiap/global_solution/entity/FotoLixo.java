package br.com.fiap.global_solution.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
@Table(name = "TB_FOTOLIXO")
public class FotoLixo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_FOTO")
    @SequenceGenerator(name = "SQ_FOTOLIXO", sequenceName = "SQ_FOTOLIXO", allocationSize = 1)
    @Column(name = "ID_FOTOLIXO")
    private Long id;

    private String src;

    @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(
            name = "DADOSLIXO",
            referencedColumnName = "ID_DADOSLIXO",
            foreignKey = @ForeignKey(name = "FK_DADOSLIXO_USUARIO")
    )
    private DadosLixo dadosLixo;

}
