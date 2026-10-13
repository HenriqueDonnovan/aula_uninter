package br.edu.uninter.baozistore.controller;

import java.net.URI;
import java.util.List;
import br.edu.uninter.baozistore.exception.RecursoNaoEncontradoException;
import br.edu.uninter.baozistore.model.Produto;
import br.edu.uninter.baozistore.repository.ProdutoRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {
    private final ProdutoRepository repository;
    public ProdutoController(ProdutoRepository repository) { this.repository = repository; }

    @PostMapping
    public ResponseEntity<Produto> criar(@Valid @RequestBody Produto produto) {
        produto.setId(null);
        Produto salvo = repository.save(produto);
        return ResponseEntity.created(URI.create("/api/produtos/" + salvo.getId())).body(salvo);
    }

    @GetMapping
    public List<Produto> listar() { return repository.findAll(); }

    @GetMapping("/{id}")
    public Produto buscar(@PathVariable Long id) {
        return repository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Produto nao encontrado: " + id));
    }

    @PutMapping("/{id}")
    public Produto atualizar(@PathVariable Long id, @Valid @RequestBody Produto dados) {
        Produto atual = buscar(id);
        atual.setNome(dados.getNome());
        atual.setPreco(dados.getPreco());
        atual.setEstoque(dados.getEstoque());
        return repository.save(atual);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        Produto atual = buscar(id);
        repository.delete(atual);
        repository.flush();
        return ResponseEntity.noContent().build();
    }
}
