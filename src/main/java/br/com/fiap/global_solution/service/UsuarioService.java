package br.com.fiap.global_solution.service;

import br.com.fiap.global_solution.dto.request.UsuarioRequest;
import br.com.fiap.global_solution.dto.response.UsuarioResponse;
import br.com.fiap.global_solution.entity.Usuario;
import br.com.fiap.global_solution.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class UsuarioService implements ServiceDTO<Usuario, UsuarioRequest, UsuarioResponse> {

    @Autowired
    private UsuarioRepository repo;

    @Autowired
    private PessoaService pessoaService;


    @Override
    public Collection<Usuario> findAll(Example<Usuario> example) {
        return repo.findAll(example);
    }

    @Override
    public Usuario findById(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public Usuario save(Usuario e) {
        return repo.save(e);
    }

    @Override
    public Usuario toEntity(UsuarioRequest dto) {

        var pessoa = pessoaService.toEntity(dto.pessoa());

        return Usuario.builder()
                .username(dto.username())
                .password(dto.password())
                .pessoa(pessoa)
                .build();
    }

    @Override
    public UsuarioResponse toResponse(Usuario e) {

        var pessoa = pessoaService.toResponse(e.getPessoa());

        return UsuarioResponse.builder()
                .id(e.getId())
                .username(e.getUsername())
                .password(e.getPassword())
                .pessoa(pessoa)
                .build();
    }
}
