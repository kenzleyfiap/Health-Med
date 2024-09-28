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
@RequestMapping("/medicos")
@RequiredArgsConstructor
public class MedicoController {

    private final MedicoService medicoService;

    @PostMapping
    @Transactional
    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<MedicoResponseDTO> cadastrar(@RequestBody @Valid MedicoDTO medicoDTO) {
        return ResponseEntity.ok(medicoService.save(medicoDTO));
    }
    @GetMapping
    public ResponseEntity<Page<MedicoResponseDTO>> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable pageable) {
        var page = medicoService.findAllByAtivoTrue(pageable);
        return ResponseEntity.ok(page);
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid AtualizacaoMedicoDTO medicoDTO) {
        return ResponseEntity.ok(medicoService.updateHorarioDisponiveis(medicoDTO));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id) {
        medicoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicoResponseDTO> detalhar(@PathVariable Long id) {
        return ResponseEntity.ok(medicoService.findById(id));
    }
}
