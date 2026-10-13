package br.edu.uninter.baozistore.controller;

import java.net.URI;
import java.util.List;
import br.edu.uninter.baozistore.exception.RecursoNaoEncontradoException;
import br.edu.uninter.baozistore.model.Cliente;
import br.edu.uninter.baozistore.repository.ClienteRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {
    private final ClienteRepository repository;
    public ClienteController(ClienteRepository repository) { this.repository = repository; }

    @PostMapping
    public ResponseEntity<Cliente> criar(@Valid @RequestBody Cliente cliente) {
        cliente.setId(null);
        Cliente salvo = repository.save(cliente);
        return ResponseEntity.created(URI.create("/api/clientes/" + salvo.getId())).body(salvo);
    }

    @GetMapping
    public List<Cliente> listar() { return repository.findAll(); }

    @GetMapping("/{id}")
    public Cliente buscar(@PathVariable Long id) {
        return repository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Cliente nao encontrado: " + id));
    }

    @PutMapping("/{id}")
    public Cliente atualizar(@PathVariable Long id, @Valid @RequestBody Cliente dados) {
        Cliente atual = buscar(id);
        atual.setNome(dados.getNome());
        atual.setClienteDesde(dados.getClienteDesde());
        return repository.save(atual);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        Cliente atual = buscar(id);
        repository.delete(atual);
        repository.flush();
        return ResponseEntity.noContent().build();
    }
}
