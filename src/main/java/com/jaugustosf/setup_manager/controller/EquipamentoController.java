package com.jaugustosf.setup_manager.controller;

import com.jaugustosf.setup_manager.model.Equipamento;
// Repare que não importamos mais o Repository aqui, e sim o Service!
import com.jaugustosf.setup_manager.service.EquipamentoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/equipamentos")
public class EquipamentoController {
    // A Recepcionista (Controller) chama o Gerente (Service)
    @Autowired
    private EquipamentoService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Equipamento cadastrarEquipamento(@Valid @RequestBody Equipamento novoEquipamento) {
        // O Controller não sabe mais como salva. Ele só passa o objeto para frente.
        return service.salvar(novoEquipamento);
    }

    @GetMapping
    public List<Equipamento> listarEquipamentos() {
        return service.listarTodos();
    }

    @GetMapping("/{id}")
    public Equipamento buscarEquipamentoPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    // O PUT serve para ATUALIZAR um registro inteiro.
    // Note o {id} na URL. O cliente precisa avisar QUAL equipamento quer alterar.
    @PutMapping("/{id}")
    public Equipamento atualizarEquipamento(@Valid @PathVariable Long id, @RequestBody Equipamento equipamentoAtualizado) {
        // 1. O Spring pega o ID da URL (@PathVariable) e o JSON do corpo (@RequestBody)
        // 2. Nós garantimos que o ID do objeto Java seja o mesmo da URL, para não atualizar o item errado.
        equipamentoAtualizado.setId(id);

        // 3. O metodo .save() do Repositório é inteligente.
        // Se o ID não existir, ele faz um INSERT. Se o ID já existir, ele faz um UPDATE automático no SQL!
        return service.salvar(equipamentoAtualizado);
    }

    // O DELETE serve para APAGAR.
    // Ele só precisa do ID na URL. Não enviamos JSON no corpo (Body).
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // Retorna o Status 204 (Deu certo e não tenho conteúdo para devolver)
    public void apagarEquipamento(@PathVariable Long id) {
        // O metodo .deleteById() vai gerar um comando: DELETE FROM equipamento WHERE id = ?
        service.deleteById(id);
    }
}