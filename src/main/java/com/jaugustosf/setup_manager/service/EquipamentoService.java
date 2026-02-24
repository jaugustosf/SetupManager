package com.jaugustosf.setup_manager.service;

import com.jaugustosf.setup_manager.exception.RegistoNaoEncontradoException;
import com.jaugustosf.setup_manager.exception.RegraDeNegocioException;
import com.jaugustosf.setup_manager.model.Equipamento;
import com.jaugustosf.setup_manager.repository.EquipamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EquipamentoService {

    @Autowired
    private EquipamentoRepository repository;

    public Equipamento salvar(Equipamento equipamento) {
        Optional<Equipamento> equipamentoExistente = repository.findByNome(equipamento.getNome());

        if (equipamentoExistente.isPresent()) {
            throw new RegraDeNegocioException("Já existe um equipamento cadastrado com o nome: " + equipamento.getNome());
        }

        return repository.save(equipamento);
    }

    public List<Equipamento> salvarLote(List<Equipamento> equipamentos) {
        for (Equipamento eq : equipamentos) {
            Optional<Equipamento> equipamentoExistente = repository.findByNome(eq.getNome());

            if (equipamentoExistente.isPresent()) {
                throw new RegraDeNegocioException("Já existe um equipamento cadastrado com o nome: " + eq.getNome());
            }
        }

        return repository.saveAll(equipamentos);
    }

    public List<Equipamento> listarTodos() {
        return repository.findAll();
    }

    public Equipamento buscarPorId(Long id) {
        return repository.findById(id).orElseThrow(() -> new RegistoNaoEncontradoException("O equipamento com o ID " + id + " não existe no seu setup."));
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
