package br.com.fiap.global_solution.resource;

import br.com.fiap.global_solution.dto.request.LocalizacaoRequest;
import br.com.fiap.global_solution.dto.response.LocalizacaoResponse;
import br.com.fiap.global_solution.entity.Localizacao;
import br.com.fiap.global_solution.service.FotoLixoService;
import br.com.fiap.global_solution.service.LocalizacaoService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Collection;
import java.util.Objects;

@RestController
@RequestMapping(value = "/localizacao")
public class LocalizacaoResource implements ResourceDTO<Localizacao, LocalizacaoRequest, LocalizacaoResponse> {

    @Autowired
    private LocalizacaoService localizacaoService;

    @Autowired
    private FotoLixoService fotoLixoService;


    @GetMapping
    public ResponseEntity<Collection<LocalizacaoResponse>> findAll(
            @RequestParam(name="latitude", required = false) Double latitude,
            @RequestParam(name="longitude", required = false) Double longitude,
            @RequestParam(name="fotoLixo.ID", required = false) Long fotoLixoId
    ) {

        var fotoLixo = Objects.nonNull(fotoLixoId) ? fotoLixoService.findById(fotoLixoId) : null;

        var localizacao = Localizacao.builder()
                .latitude(latitude)
                .longitude(longitude)
                .fotoLixo(fotoLixo)
                .build();

        ExampleMatcher matcher = ExampleMatcher.matchingAll()
                .withIgnoreNullValues()
                .withIgnoreCase();

        Example<Localizacao> example = Example.of(localizacao, matcher);

        var encontrados = localizacaoService.findAll(example);

        if(encontrados.isEmpty()) return ResponseEntity.noContent().build();

        var resposta = encontrados.stream()
                .map( localizacaoService::toResponse )
                .toList();

        return ResponseEntity.ok(resposta);
    }


    @Override
    @GetMapping(value = "/{id}")
    public ResponseEntity<LocalizacaoResponse> findById(@PathVariable Long id) {

        var encontrado = localizacaoService.findById( id );

        if (Objects.isNull( encontrado )) return ResponseEntity.notFound().build();

        var resposta = localizacaoService.toResponse( encontrado );

        return ResponseEntity.ok( resposta );
    }

    @Override
    @Transactional
    @PostMapping
    public ResponseEntity<LocalizacaoResponse> save(@RequestBody @Valid LocalizacaoRequest r) {

        var entity = localizacaoService.toEntity(r);
        var saved = localizacaoService.save(entity);
        var response = localizacaoService.toResponse(saved);

        var uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(uri).body(response);
    }
}
