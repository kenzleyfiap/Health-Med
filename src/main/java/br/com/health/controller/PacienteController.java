package br.com.health.controller;

import br.com.health.dto.paciente.PacienteDTO;
import br.com.health.dto.paciente.PacienteResponseDTO;
import br.com.health.service.PacienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("pacientes")
@RequiredArgsConstructor
public class PacienteController {

    private final PacienteService pacienteService;

    @PostMapping
    @Transactional
    @Operation(
            summary = "Cadastrar Paciente",
            description = "Este endpoint permite cadastrar um Paciente. É necessário fornecer um corpo de solicitação com os dados do paciente a ser cadastrado.",
            security = { @SecurityRequirement(name = "bearer-key")
            })
    public ResponseEntity<PacienteResponseDTO> cadastrar(@RequestBody @Valid PacienteDTO pacienteDTO) {
        return ResponseEntity.ok(pacienteService.save(pacienteDTO));
    }

    @GetMapping
    @Operation(
            summary = "Listar Pacientes",
            description = "Este endpoint permite listar pacientes cadastrados.",
            security = { @SecurityRequirement(name = "bearer-key")
            })
    public ResponseEntity<Page<PacienteResponseDTO>> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
        var page = pacienteService.findAllByAtivoTrue(paginacao);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Listar Paciente pelo ID",
            description = "Este endpoint permite Listar um Paciente pelo ID. É necessário fornecer um parâmetro de solicitação com o ID do paciente a ser consultado.",
            security = { @SecurityRequirement(name = "bearer-key")
            })
    public ResponseEntity<PacienteResponseDTO> detalhar(@PathVariable Long id) {
        return ResponseEntity.ok(pacienteService.getReferenceById(id));
    }
}
