package br.com.fiap.global_solution.service;

import br.com.fiap.global_solution.dto.request.DadosLixoRequest;
import br.com.fiap.global_solution.dto.response.DadosLixoResponse;
import br.com.fiap.global_solution.entity.DadosLixo;
import br.com.fiap.global_solution.repository.DadosLixoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class DadosLixoService implements ServiceDTO<DadosLixo, DadosLixoRequest, DadosLixoResponse> {

    @Autowired
    private DadosLixoRepository repo;


    @Override
    public Collection<DadosLixo> findAll(Example<DadosLixo> example) {
        return repo.findAll(example);
    }

    @Override
    public DadosLixo findById(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public DadosLixo save(DadosLixo e) {
        return repo.save(e);
    }

    @Override
    public DadosLixo toEntity(DadosLixoRequest dto) {

        return DadosLixo.builder()
                .tipoLixo(dto.tipoLixo())
                .dataEnvio(dto.dataEnvio())
                .build();
    }

    @Override
    public DadosLixoResponse toResponse(DadosLixo e) {

        return DadosLixoResponse.builder()
                .id(e.getId())
                .tipoLixo(e.getTipoLixo())
                .dataEnvio(e.getDataEnvio())
                .build();
    }
}
