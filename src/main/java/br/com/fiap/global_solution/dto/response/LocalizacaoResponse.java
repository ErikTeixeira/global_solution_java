package br.com.fiap.global_solution.dto.response;

import lombok.Builder;

@Builder
public record LocalizacaoResponse(

        Long id,
        Double latitude,
        Double longitude,
        FotoLixoResponse fotoLixo

) {
}
