package br.edu.uninter.baozistore.controller;

import java.net.URI;
import java.util.List;
import br.edu.uninter.baozistore.dto.PedidoRequest;
import br.edu.uninter.baozistore.dto.PedidoResponse;
import br.edu.uninter.baozistore.exception.RecursoNaoEncontradoException;
import br.edu.uninter.baozistore.model.Pedido;
import br.edu.uninter.baozistore.repository.*;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {
    private final PedidoRepository pedidos;
    private final ClienteRepository clientes;
    private final ProdutoRepository produtos;

    public PedidoController(PedidoRepository pedidos, ClienteRepository clientes, ProdutoRepository produtos) {
        this.pedidos = pedidos;
        this.clientes = clientes;
        this.produtos = produtos;
    }

    private Pedido preencher(Pedido pedido, PedidoRequest request) {
        pedido.setCliente(clientes.findById(request.clienteId())
            .orElseThrow(() -> new RecursoNaoEncontradoException("Cliente nao encontrado: " + request.clienteId())));
        pedido.setProduto(produtos.findById(request.produtoId())
            .orElseThrow(() -> new RecursoNaoEncontradoException("Produto nao encontrado: " + request.produtoId())));
        pedido.setQuantidade(request.quantidade());
        return pedido;
    }

    private Pedido buscarEntidade(Long id) {
        return pedidos.findById(id)
            .orElseThrow(() -> new RecursoNaoEncontradoException("Pedido nao encontrado: " + id));
    }

    @PostMapping
    @Transactional
    public ResponseEntity<PedidoResponse> criar(@Valid @RequestBody PedidoRequest request) {
        Pedido salvo = pedidos.save(preencher(new Pedido(), request));
        return ResponseEntity.created(URI.create("/api/pedidos/" + salvo.getId()))
            .body(PedidoResponse.from(salvo));
    }

    @GetMapping
    @Transactional(readOnly = true)
    public List<PedidoResponse> listar() {
        return pedidos.findAll().stream().map(PedidoResponse::from).toList();
    }

    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    public PedidoResponse buscar(@PathVariable Long id) {
        return PedidoResponse.from(buscarEntidade(id));
    }

    @PutMapping("/{id}")
    @Transactional
    public PedidoResponse atualizar(@PathVariable Long id, @Valid @RequestBody PedidoRequest request) {
        Pedido pedido = preencher(buscarEntidade(id), request);
        return PedidoResponse.from(pedidos.save(pedido));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        Pedido pedido = buscarEntidade(id);
        pedidos.delete(pedido);
        return ResponseEntity.noContent().build();
    }
}
