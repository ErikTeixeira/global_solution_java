package br.com.fiap.global_solution.dto.response;

import lombok.Builder;

@Builder
public record FotoLixoResponse(

        Long id,

        String src,

        DadosLixoResponse dadosLixo,

        UsuarioResponse usuario

) {
}
