package com.ecommerce.project_Ecommerce.controller;

import com.ecommerce.project_Ecommerce.model.Produto;
import com.ecommerce.project_Ecommerce.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // 1. Marca a classe como um controlador REST
@RequestMapping("/api/produtos") // 2. Define o caminho base para todas as rotas deste controlador

public class ProdutoController {
    @Autowired // 3. Injeta a nossa camada de serviço
    private ProdutoService produtoService;

    // Os métodos da API virão aqui...
    @GetMapping("/todos") // Mapeia para GET /api/produtos/todos
    public List<Produto> listarTodos() {
        return produtoService.buscarTodosProdutos();
    }

    @PostMapping
    public ResponseEntity<Produto> criarProduto(@RequestBody Produto produto) {
        try {
            Produto produtoSalvo = produtoService.salvarProduto(produto);
            // Retorna 201 Created se for sucesso
            return new ResponseEntity<>(produtoSalvo, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            // Retorna 400 Bad Request se a validação falhar (preço <= 0)
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}") // Mapeia para GET /api/produtos/{id}
    public ResponseEntity<Produto> buscarPorId(@PathVariable Long id) {
        // O Optional é tratado para retornar 404 se não encontrar
        return produtoService.buscarProdutoPorId(id)
                .map(produto -> new ResponseEntity<>(produto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}") // Mapeia para PUT /api/produtos/{id}
    public ResponseEntity<Produto> atualizarProduto(@PathVariable Long id, @RequestBody Produto produtoDetalhes) {
        // Aqui você faria a lógica de busca e atualização (que faremos no Service)
        Produto produtoAtualizado = produtoService.atualizarProduto(id, produtoDetalhes);

        if (produtoAtualizado == null) {
            // Se o serviço retornar null, o ID não existia
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Retorna 200 OK com o objeto atualizado
        return new ResponseEntity<>(produtoAtualizado, HttpStatus.OK);
    }

    @DeleteMapping("/{id}") // Mapeia para DELETE /api/produtos/{id}
    public ResponseEntity<Void> deletarProduto(@PathVariable Long id) {
        // A lógica de deleção pode ser mais complexa, mas por enquanto vamos chamar o serviço
        produtoService.deletarProduto(id);

        // Retorna 204 No Content se a deleção for bem-sucedida, pois não há corpo de resposta
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
