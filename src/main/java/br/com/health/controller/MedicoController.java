package br.com.health.controller;

import br.com.health.dto.medico.*;
import br.com.health.service.MedicoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("medicos")
@RequiredArgsConstructor
public class MedicoController {

    private final MedicoService medicoService;

    @PostMapping
    @Transactional
    @Operation(
            summary = "Cadastrar Médico",
            description = "Este endpoint permite cadastrar um médico. É necessário fornecer um corpo de solicitação com os dados do médico a ser cadastrado."
           )
    public ResponseEntity<MedicoResponseDTO> cadastrar(@RequestBody @Valid MedicoDTO medicoDTO) {
        return ResponseEntity.ok(medicoService.save(medicoDTO));
    }
    @GetMapping
    @Operation(
            summary = "Listar Médicos",
            description = "Este endpoint permite listar médicos cadastrados",
            security = { @SecurityRequirement(name = "bearer-key")
            })
    public ResponseEntity<Page<MedicoResponseDTO>> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable pageable) {
        var page = medicoService.findAllByAtivoTrue(pageable);
        return ResponseEntity.ok(page);
    }
}
