package br.com.fiap.global_solution.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record FotoLixoRequest(

        String src,

        @Valid
        @NotNull(message = "É necessário informar os dados da imagem")
        DadosLixoRequest dadosLixo,

        @Valid
        @NotNull(message = "É necessário informar o usuário que tirou a foto")
        AbstractRequest usuario

) {
}
