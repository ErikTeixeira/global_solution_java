package br.com.fiap.global_solution.resource;

import br.com.fiap.global_solution.dto.request.FotoLixoRequest;
import br.com.fiap.global_solution.dto.response.FotoLixoResponse;
import br.com.fiap.global_solution.entity.FotoLixo;
import br.com.fiap.global_solution.service.FotoLixoService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Collection;
import java.util.Objects;

@RestController
@RequestMapping(value = "/fotoLixo")
@RequiredArgsConstructor
public class FotoLixoResource implements ResourceDTO<FotoLixo, FotoLixoRequest, FotoLixoResponse> {

    private final FotoLixoService service;


    @GetMapping
    public ResponseEntity<Collection<FotoLixoResponse>> findAll(
            @RequestParam(name = "src", required = false) String src,
            @RequestParam(name = "dadosLixo.tipoLixo", required = false) String tipoLixo,
            @RequestParam(name = "usuario.id", required = false) Long usuarioId
    ) {

        var entity = FotoLixo.builder()
                .src(src)
                .dadosLixo(br.com.fiap.global_solution.entity.DadosLixo.builder().tipoLixo(tipoLixo).build())
                .Usuario(br.com.fiap.global_solution.entity.Usuario.builder().id(usuarioId).build())
                .build();

        ExampleMatcher matcher = ExampleMatcher.matchingAll()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withIgnoreCase()
                .withIgnoreNullValues();

        Example<FotoLixo> example = Example.of(entity, matcher);

        var entities = service.findAll(example);

        if (entities.isEmpty()) return ResponseEntity.notFound().build();

        var response = entities.stream().map(service::toResponse).toList();

        return ResponseEntity.ok(response);

    }

    @Override
    @GetMapping(value = "/{id}")
    public ResponseEntity<FotoLixoResponse> findById(@PathVariable Long id) {

        var encontrado = service.findById(id);

        if (Objects.isNull(encontrado)) return ResponseEntity.notFound().build();

        var resposta = service.toResponse(encontrado);

        return ResponseEntity.ok(resposta);
    }


    @Override
    @Transactional
    @PostMapping
    public ResponseEntity<FotoLixoResponse> save(@Valid @RequestBody FotoLixoRequest r) {

        var entity = service.toEntity(r);
        var saved = service.save(entity);
        var resposta = service.toResponse(saved);

        var uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(uri).body(resposta);
    }

    @PostMapping(value = "/upload/{fotoLixoId}")
    public ResponseEntity<FotoLixoResponse> upload(
            @PathVariable Long fotoLixoId,
            @RequestParam("file") MultipartFile file
    ) {
        FotoLixo fotoLixo = service.findById(fotoLixoId);

        if (Objects.isNull(fotoLixo)) {
            return ResponseEntity.notFound().build();
        }
        var saved = service.save(fotoLixo, file);

        if (Objects.isNull(saved))
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

        var response = service.toResponse(saved);

        return ResponseEntity.ok(response);
    }

}