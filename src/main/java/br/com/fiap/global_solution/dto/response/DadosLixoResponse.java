package br.com.fiap.global_solution.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record DadosLixoResponse(

        Long id,

        String tipoLixo,

        LocalDateTime dataEnvio

) {
}
