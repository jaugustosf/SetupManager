package com.jaugustosf.setup_manager.controller;

import com.jaugustosf.setup_manager.model.Equipamento;
import com.jaugustosf.setup_manager.service.EquipamentoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.jaugustosf.setup_manager.dto.EquipamentoRequestDTO;
import com.jaugustosf.setup_manager.dto.EquipamentoResponseDTO;

import java.util.List;

@RestController
@RequestMapping("/equipamentos")
public class EquipamentoController {

    @Autowired
    private EquipamentoService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    // 1. Mudamos a porta de entrada: Agora recebemos o RequestDTO em vez da Entidade
    public EquipamentoResponseDTO cadastrarEquipamento(@Valid @RequestBody EquipamentoRequestDTO requestDTO) {
        // 2. Convertemos o Dublê (DTO) no Ator Real (Entidade)
        Equipamento novoEquipamento = requestDTO.converterParaEntidade();

        // 3. O Service continua esperando uma Entidade para salvar no banco
        Equipamento equipamentoSalvo = service.salvar(novoEquipamento);

        // 4. Devolvemos o Dublê de Saída (ResponseDTO) para esconder o ID e dados sensíveis!
        return new EquipamentoResponseDTO(equipamentoSalvo);
    }

    @PostMapping("/batch") // Pode manter apenas @PostMapping se você apagou o de cadastro individual, ou @PostMapping("/lote") se quiser ter os dois
    @ResponseStatus(HttpStatus.CREATED)
    // CORREÇÃO 1: A porta de entrada recebe uma lista de REQUEST_DTO (A entrada válida)
    public List<EquipamentoResponseDTO> cadastrarEmLote(@Valid @RequestBody List<EquipamentoRequestDTO> requestDTO) {

        // Agora o Java acha o metodo, porque requestDTO é do tipo certo!
        List<Equipamento> novosEquipamentos = requestDTO.stream()
                .map(EquipamentoRequestDTO::converterParaEntidade)
                .toList();

        // CORREÇÃO 2: Usamos o metodo salvarVarios() do Service, que aceita uma Lista inteira!
        List<Equipamento> equipamentosSalvos = service.salvarLote(novosEquipamentos);

        // O seu retorno estava perfeito!
        return equipamentosSalvos.stream()
                .map(EquipamentoResponseDTO::new)
                .toList();
    }

    @GetMapping("/{id}")
    public Equipamento buscarEquipamentoPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @GetMapping
    public List<EquipamentoResponseDTO> listar(@RequestParam(required = false) String categoria) {
        // 1. O Service decide o que buscar
        List<Equipamento> lista = service.listByCategory(categoria);

        // 2. Transformamos a lista de Entidades em DTOs (usando a nossa esteira/stream)
        return lista.stream()
                .map(EquipamentoResponseDTO::new) // Atalho para: e -> new EquipamentoResponseDTO(e)
                .toList();
    }

    @PutMapping("/{id}")
    public Equipamento atualizarEquipamento(@Valid @PathVariable Long id, @RequestBody Equipamento equipamentoAtualizado) {
        equipamentoAtualizado.setId(id);
        return service.salvar(equipamentoAtualizado);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void apagarEquipamento(@PathVariable Long id) {
        service.deleteById(id);
    }
}
