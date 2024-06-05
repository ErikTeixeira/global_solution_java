package br.com.fiap.global_solution.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record FotoLixoRequest(

        @Valid
        @NotNull(message = "É necessário informar os dados da imagem")
        PessoaRequest dadosLixo,

        @Valid
        @NotNull(message = "É necessário informar o usuário")
        AbstractRequest usuario

) {
}
