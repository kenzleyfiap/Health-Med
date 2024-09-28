package br.com.health.controller;

import br.com.health.dto.consulta.ConsultaDTO;
import br.com.health.dto.consulta.ConsultaResponseDTO;
import br.com.health.service.ConsultaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("consultas")
@RequiredArgsConstructor
public class ConsultaController {

    private final ConsultaService service;
    @PostMapping
    @Transactional
    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<ConsultaResponseDTO> agendar(@RequestBody @Valid ConsultaDTO dados) {
        var agendar = service.save(dados);
        return ResponseEntity.ok(agendar);
    }
}
