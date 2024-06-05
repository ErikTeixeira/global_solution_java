package br.com.fiap.global_solution.resource;

import br.com.fiap.global_solution.dto.request.UsuarioRequest;
import br.com.fiap.global_solution.dto.response.UsuarioResponse;
import br.com.fiap.global_solution.entity.Pessoa;
import br.com.fiap.global_solution.entity.Usuario;
import br.com.fiap.global_solution.service.UsuarioService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Objects;

@RestController
@RequestMapping(value = "/usuarios")
public class UsuarioResource implements ResourceDTO<Usuario, UsuarioRequest, UsuarioResponse> {

    @Autowired
    UsuarioService service;


    @GetMapping
    public ResponseEntity<Collection<UsuarioResponse>> findAll(

            @RequestParam(name = "username", required = false) String username,
            @RequestParam(name = "pessoa.nome", required = false) String nome,
            @RequestParam(name = "pessoa.sobrenome", required = false) String sobrenome,
            @RequestParam(name = "pessoa.dataNascimento", required = false) LocalDate dataNascimento,
            @RequestParam(name = "pessoa.cpf", required = false) String cpf,
            @RequestParam(name = "pessoa.email", required = false) String email
    ) {

        var pessoa = Pessoa.builder()
                .nome(nome)
                .sobrenome(sobrenome)
                .dataNascimento(dataNascimento)
                .cpf(cpf)
                .email(email)
                .build();

        var usuario = Usuario.builder()
                .username(username)
                .pessoa(pessoa)
                .build();


        ExampleMatcher matcher = ExampleMatcher.matchingAll()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withIgnoreCase()
                .withIgnoreNullValues();

        Example<Usuario> example = Example.of(usuario, matcher);

        var entities = service.findAll(example);

        if (entities.isEmpty()) return ResponseEntity.notFound().build();

        var response = entities.stream().map(service::toResponse).toList();

        return ResponseEntity.ok(response);

    }


    @Override
    @GetMapping(value = "/{id}")
    public ResponseEntity<UsuarioResponse> findById(@PathVariable Long id) {

        var encontrado = service.findById( id );

        if (Objects.isNull( encontrado )) return ResponseEntity.notFound().build();

        var resposta = service.toResponse( encontrado );

        return ResponseEntity.ok( resposta );
    }


    @Override
    @Transactional
    @PostMapping
    public ResponseEntity<UsuarioResponse> save(@RequestBody @Valid UsuarioRequest r) {

        var entity = service.toEntity( r );
        var saved = service.save( entity );
        var resposta = service.toResponse( saved );

        var uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path( "/{id}" )
                .buildAndExpand( saved.getId() )
                .toUri();

        return ResponseEntity.created( uri ).body( resposta );
    }

}
