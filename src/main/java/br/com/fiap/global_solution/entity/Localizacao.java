package br.com.fiap.global_solution.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder


@Entity
@Table(name = "TB_LOCALIZACAO")
public class Localizacao {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_LOCALIZACAO")
    @SequenceGenerator(name = "SQ_LOCALIZACAO", sequenceName = "SQ_LOCALIZACAO", allocationSize = 1)
    @Column(name = "ID_LOCALIZACAO")
    private Long id;

    private Double latitude;

    private Double longitude;


    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
            name = "FOTOLIXO",
            referencedColumnName = "ID_FOTOLIXO",
            foreignKey = @ForeignKey(
                    name = "FK_LOCALIZACAO_FOTOLIXO"
            )
    )
    private FotoLixo fotoLixo;

}
