package br.com.fiap.global_solution.dto.request;

import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDateTime;

public record DadosLixoRequest(

        String tipoLixo,

        @PastOrPresent(message = "Não aceitamos data no futuro")
        LocalDateTime dataEnvio

) {
}
