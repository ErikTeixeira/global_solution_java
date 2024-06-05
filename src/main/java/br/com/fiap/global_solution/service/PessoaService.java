package br.com.fiap.global_solution.service;

import br.com.fiap.global_solution.dto.request.PessoaRequest;
import br.com.fiap.global_solution.dto.response.PessoaResponse;
import br.com.fiap.global_solution.entity.Pessoa;
import br.com.fiap.global_solution.repository.PessoaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class PessoaService implements ServiceDTO<Pessoa, PessoaRequest, PessoaResponse> {

    @Autowired
    private PessoaRepository repo;


    @Override
    public Collection<Pessoa> findAll(Example<Pessoa> example) {
        return repo.findAll(example);
    }

    @Override
    public Pessoa findById(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public Pessoa save(Pessoa e) {
        return repo.save(e);
    }

    @Override
    public Pessoa toEntity(PessoaRequest dto) {

        return Pessoa.builder()
                .nome(dto.nome())
                .sobrenome(dto.sobrenome())
                .dataNascimento(dto.dataNascimento())
                .cpf(dto.cpf())
                .email(dto.email())
                .build();
    }

    @Override
    public PessoaResponse toResponse(Pessoa e) {

        return PessoaResponse.builder()
                .id(e.getId())
                .nome(e.getNome())
                .sobrenome(e.getSobrenome())
                .dataNascimento(e.getDataNascimento())
                .cpf(e.getCpf())
                .email(e.getEmail())
                .build();
    }
}
