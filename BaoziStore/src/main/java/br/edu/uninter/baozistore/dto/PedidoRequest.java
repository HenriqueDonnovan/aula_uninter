package br.edu.uninter.baozistore.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record PedidoRequest(
    @NotNull(message = "clienteId e obrigatorio") Long clienteId,
    @NotNull(message = "produtoId e obrigatorio") Long produtoId,
    @NotNull(message = "quantidade e obrigatoria")
    @Min(value = 1, message = "quantidade deve ser maior que zero") Integer quantidade
) {}
