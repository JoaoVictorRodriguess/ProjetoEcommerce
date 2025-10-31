package com.ecommerce.project_Ecommerce.service;

import com.ecommerce.project_Ecommerce.model.Produto;
import com.ecommerce.project_Ecommerce.repository.ProdutoRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@Service // 1. Marca esta classe como um componente de serviço gerenciado pelo Spring
public class ProdutoService {

    @Autowired // 2. O Spring vai injetar o ProdutoRepository aqui
    private ProdutoRepository produtoRepository;

    // Métodos CRUD virão abaixo

    public Produto salvarProduto(Produto produto) {
        // Validação de regra de negócio: o preço deve ser positivo
        if (produto.getPreco() <= 0) {
            throw new IllegalArgumentException("O preço do produto deve ser maior que zero.");
        }
        // Chamada ao Repositório para salvar (que o JpaRepository já fornece)
        return produtoRepository.save(produto);
    }

    public List<Produto> buscarTodosProdutos() {
        // Usamos o findAll() do JpaRepository
        return produtoRepository.findAll();
    }

    public Optional<Produto> buscarProdutoPorId(Long id) {
        // Usamos o findById() do JpaRepository
        return produtoRepository.findById(id);
    }

    public void deletarProduto(Long id) {
        // Primeiro verificamos se existe, mas o deleteById faz isso internamente também.
        produtoRepository.deleteById(id);
    }

    public Produto atualizarProduto(Long id, Produto produtoDetalhes) {
        // 1. Tenta buscar o produto existente pelo ID
        Produto produtoExistente = produtoRepository.findById(id)
                .orElse(null); // Se não encontrar, retorna null

        // 2. Verifica se o produto foi encontrado (Regra de negócio: só atualiza se existir)
        if (produtoExistente == null) {
            return null; // Retorna null para o Controller indicar 404 NOT_FOUND
        }

        // 3. Aplica as novas regras de negócio/validações (Ex: Preço não pode ser negativo)
        if (produtoDetalhes.getPreco() < 0) {
            throw new IllegalArgumentException("O preço não pode ser negativo.");
        }

        // 4. Atualiza os campos um por um (ou usa Lombok se for o caso, mas é mais seguro fazer assim para saber o que muda)
        produtoExistente.setNome(produtoDetalhes.getNome());
        produtoExistente.setDescricao(produtoDetalhes.getDescricao());
        produtoExistente.setPreco(produtoDetalhes.getPreco());
        produtoExistente.setQuantidadeEstoque(produtoDetalhes.getQuantidadeEstoque());
        // Você pode adicionar mais campos aqui conforme a necessidade

        // 5. Salva o objeto modificado de volta no banco de dados
        return produtoRepository.save(produtoExistente);
    }
}
