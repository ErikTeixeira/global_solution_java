package br.com.fiap.global_solution.dto.response;

import lombok.Builder;

@Builder
public record UsuarioResponse(

        Long id,

        String username,

        String password,

        PessoaResponse pessoa

) {
}
