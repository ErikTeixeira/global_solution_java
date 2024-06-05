package br.com.fiap.global_solution.service;

import br.com.fiap.global_solution.dto.request.LocalizacaoRequest;
import br.com.fiap.global_solution.dto.response.LocalizacaoResponse;
import br.com.fiap.global_solution.entity.Localizacao;
import br.com.fiap.global_solution.repository.LocalizacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class LocalizacaoService implements ServiceDTO<Localizacao, LocalizacaoRequest, LocalizacaoResponse> {

    @Autowired
    private LocalizacaoRepository repo;

    @Autowired
    private FotoLixoService fotoLixoService;


    @Override
    public Collection<Localizacao> findAll(Example<Localizacao> example) {
        return repo.findAll(example);
    }

    @Override
    public Localizacao findById(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public Localizacao save(Localizacao e) {
        return repo.save(e);
    }

    @Override
    public Localizacao toEntity(LocalizacaoRequest dto) {

        var fotoLixo = fotoLixoService.findById(dto.fotoLixo().id());

        return Localizacao.builder()
                .latitude(dto.latitude())
                .longitude(dto.longitude())
                .fotoLixo(fotoLixo)
                .build();
    }

    @Override
    public LocalizacaoResponse toResponse(Localizacao e) {

        var fotoLixo = fotoLixoService.toResponse(e.getFotoLixo());

        return LocalizacaoResponse.builder()
                .id(e.getId())
                .latitude(e.getLatitude())
                .longitude(e.getLongitude())
                .fotoLixo(fotoLixo)
                .build();
    }
}
