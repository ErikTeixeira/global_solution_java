package br.com.fiap.global_solution.service;

import br.com.fiap.global_solution.dto.request.FotoLixoRequest;
import br.com.fiap.global_solution.dto.response.FotoLixoResponse;
import br.com.fiap.global_solution.entity.FotoLixo;
import br.com.fiap.global_solution.repository.FotoLixoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class FotoLixoService implements ServiceDTO<FotoLixo, FotoLixoRequest, FotoLixoResponse> {

    private final String IMAGEM_FOLDER = System.getProperty("user.dir") + "/drive/fotos";

    private final FotoLixoRepository repo;

    private final DadosLixoService dadosLixoService;

    private final UsuarioService usuarioService;


    @Override
    public Collection<FotoLixo> findAll(Example<FotoLixo> example) {
        return repo.findAll(example);
    }

    @Override
    public FotoLixo findById(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public FotoLixo save(FotoLixo e) {
        return repo.save(e);
    }

    public FotoLixo save(FotoLixo fotoLixo, MultipartFile imagem) {
        FotoLixo saved = null;
        if (upload(imagem, fotoLixo)) saved = repo.save(fotoLixo);
        return saved;
    }

    public boolean upload(MultipartFile file, FotoLixo fotoLixo) {

        if (file.isEmpty()) {
            throw new RuntimeException("Empty file");
        }

        Path destination = Paths
                .get(IMAGEM_FOLDER)
                .resolve(fotoLixo.getSrc())
                .normalize()
                .toAbsolutePath();

        try {

            if (!Files.exists(Path.of(IMAGEM_FOLDER))) Files.createDirectories(
                    Path.of(IMAGEM_FOLDER)
            );

            Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);

            return true;

        } catch (IOException e) {
            System.err.println("[ IOEXCEPTION ][  FOTO - UPLOAD  ] -  ERRO NO UPLOAD DO ARQUIVO:  " + e.getMessage());
            return false;
        }
    }


    @Override
    public FotoLixo toEntity(FotoLixoRequest dto) {

        var dadosLixo = dadosLixoService.toEntity(dto.dadosLixo());
        var usuario = usuarioService.findById(dto.usuario().id());

        return FotoLixo.builder()
                .src(dto.src())
                .dadosLixo(dadosLixo)
                .Usuario(usuario)
                .build();
    }

    @Override
    public FotoLixoResponse toResponse(FotoLixo e) {

        var dadosLixo = dadosLixoService.toResponse(e.getDadosLixo());
        var usuario = usuarioService.toResponse(e.getUsuario());

        return FotoLixoResponse.builder()
                .id(e.getId())
                .src(e.getSrc())
                .dadosLixo(dadosLixo)
                .usuario(usuario)
                .build();
    }

}
