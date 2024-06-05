package br.com.fiap.global_solution.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record LocalizacaoRequest(

        Double latitude,

        Double longitude,

        @Valid
        @NotNull(message = "É necessário informar a foto")
        AbstractRequest fotoLixo

) {
}
