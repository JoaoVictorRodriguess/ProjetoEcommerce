package com.ecommerce.project_Ecommerce.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity // 1. Informa ao Spring que esta classe é uma tabela no BD
@Getter // 2. Lombok gera todos os getters
@Setter // 3. Lombok gera todos os setters

public class Produto {
    @Id //chave primária
    @GeneratedValue(strategy = GenerationType.IDENTITY) // O BD irá gerar o id automaticamente com o autoincremento
    private Long id;

    private String nome;

    private String descricao;

    private double preco;

    private int quantidadeEstoque;

    private double valorEstoque;

    public void atualizarValorEstoque(){
        this.valorEstoque = this.preco * this.quantidadeEstoque;
    }

    public double getPreco() {
        return preco;
    }
}
