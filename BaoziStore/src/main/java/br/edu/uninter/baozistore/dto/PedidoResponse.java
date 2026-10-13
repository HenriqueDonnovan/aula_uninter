package br.edu.uninter.baozistore.dto;

import br.edu.uninter.baozistore.model.Pedido;

public record PedidoResponse(Long id, Long clienteId, Long produtoId, Integer quantidade) {
    public static PedidoResponse from(Pedido pedido) {
        return new PedidoResponse(pedido.getId(), pedido.getCliente().getId(),
            pedido.getProduto().getId(), pedido.getQuantidade());
    }
}
