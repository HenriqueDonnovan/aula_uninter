package br.edu.uninter.baozistore.model;

import java.math.BigDecimal;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "produtos")
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome do produto e obrigatorio")
    @Column(nullable = false)
    private String nome;

    @NotNull(message = "O preco e obrigatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "O preco deve ser maior que zero")
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal preco;

    @NotNull(message = "O campo estoque e obrigatorio")
    @Column(nullable = false)
    private Boolean estoque;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public BigDecimal getPreco() { return preco; }
    public void setPreco(BigDecimal preco) { this.preco = preco; }
    public Boolean getEstoque() { return estoque; }
    public void setEstoque(Boolean estoque) { this.estoque = estoque; }
}
