package com.jaugustosf.setup_manager.service;

import com.jaugustosf.setup_manager.exception.RegistoNaoEncontradoException;
import com.jaugustosf.setup_manager.model.Equipamento;
import com.jaugustosf.setup_manager.repository.EquipamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// O @Service avisa o Spring: "Esta classe guarda as regras de negócio importantes!"
@Service
public class EquipamentoService {

    // O Service é quem manda no banco de dados (Repository) agora.
    @Autowired
    private EquipamentoRepository repository;

    public Equipamento salvar(Equipamento equipamento) {
        // No futuro, é AQUI dentro que colocaremos os "if/else" de validação pesada.
        // Por exemplo: consultar se o nome já existe antes de mandar o repository.save()
        return repository.save(equipamento);
    }

    public List<Equipamento> listarTodos() {
        return repository.findAll();
    }

    public Equipamento buscarPorId(Long id) {
        // O metodo findById() devolve um "Optional" (uma caixa que pode ou não conter o equipamento).
        return repository.findById(id)
                // Se a caixa estiver vazia, lançamos o nosso erro personalizado!
                .orElseThrow(() -> new RegistoNaoEncontradoException("O equipamento com o ID " + id + " não existe no seu setup."));
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}