package br.com.fiap.global_solution.dto.response;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record PessoaResponse(

        Long id,

        String nome,

        String sobrenome,

        LocalDate dataNascimento,

        String cpf,

        String email

) {
}
