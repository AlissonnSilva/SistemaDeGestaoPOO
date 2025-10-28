package SistemaDeGestao;

import java.util.*;

public class Sistema {
    // Listas que armazenam os dados em memória
    private static List<Cliente> clientes = new ArrayList<>();
    private static List<Produto> produtos = new ArrayList<>();
    private static List<Pedido> pedidos = new ArrayList<>();
    private static Queue<Pedido> filaPedidos = new LinkedList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Inicia a thread que processa pedidos em segundo plano
        new Thread(new ProcessadorPedidos(filaPedidos)).start();

        // Exibe o menu principal em loop
        exibirMenu();
    }

    private static void exibirMenu() {
        int opcao;
    do {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║           MENU PRINCIPAL               ║");
        System.out.println("╠════════════════════════════════════════╣");
        System.out.println("║  1. [C] Cadastrar cliente              ║");
        System.out.println("║  2. [P] Cadastrar produto              ║");
        System.out.println("║  3. [!] Criar pedido                   ║");
        System.out.println("║  4. [L] Listar clientes                ║");
        System.out.println("║  5. [L] Listar produtos                ║");
        System.out.println("║  6. [L] Listar pedidos                 ║");
        System.out.println("║  7. [E] Editar cliente                 ║");
        System.out.println("║  8. [E] Editar produto                 ║");
        System.out.println("║  9. [E] Editar pedido                  ║");
        System.out.println("║  10. [X] Excluir cliente               ║");
        System.out.println("║  11. [X] Excluir produto               ║");
        System.out.println("║  12. [X] Excluir pedido                ║");
        System.out.println("║  0. [X] Sair                           ║");
        System.out.println("╚════════════════════════════════════════╝");
        System.out.print(" Escolha uma opção: ");
        opcao = scanner.nextInt();
        scanner.nextLine(); // Limpa o buffer do scanner

        switch (opcao) {
            case 1 -> cadastrarCliente();
            case 2 -> cadastrarProduto();
            case 3 -> criarPedido();
            case 4 -> listarClientes();
            case 5 -> listarProdutos();
            case 6 -> listarPedidos();
            case 7 -> editarCliente();
            case 8 -> editarProduto();
            case 9 -> editarPedido();
            case 10 -> excluirCliente();
            case 11 -> excluirProduto();
            case 12 -> excluirPedido();

            case 0 -> System.out.println("\n Encerrando o sistema... Até logo!");
            default -> System.out.println("\n  Opção inválida! Tente novamente.");
        }
    } while (opcao != 0);

    }

    // Cadastro de cliente com validação
    private static void cadastrarCliente() {
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();

        try {
            clientes.add(new Cliente(nome, email));
            System.out.println("Cliente cadastrado com sucesso!");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    // Cadastro de produto com validação
    private static void cadastrarProduto() {
        System.out.print("Nome do produto: ");
        String nome = scanner.nextLine();
        System.out.print("Preço: ");
        double preco = scanner.nextDouble();
        scanner.nextLine(); // Limpa buffer
        System.out.print("Categoria: ");
        String categoriaStr = scanner.nextLine().toUpperCase();

        try {
            CategoriaProduto categoria = CategoriaProduto.valueOf(categoriaStr);
            produtos.add(new Produto(nome, preco, categoria));
            System.out.println("Produto cadastrado com sucesso!");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    // Criação de pedido com múltiplos itens
    private static void criarPedido() {
        if (clientes.isEmpty() || produtos.isEmpty()) {
            System.out.println("Cadastre pelo menos um cliente e um produto antes de criar pedidos.");
            return;
        }

        listarClientes();
        System.out.print("Escolha o índice do cliente: ");
        int idxCliente = scanner.nextInt();
        scanner.nextLine();

        if (idxCliente < 0 || idxCliente >= clientes.size()) {
            System.out.println("Índice inválido.");
            return;
        }

        Pedido pedido = new Pedido(clientes.get(idxCliente));

        String continuar = "s"; // Inicializa para entrar no loop
        while (continuar.equalsIgnoreCase("s")) {
            listarProdutos();
            System.out.print("Escolha o índice do produto: ");
            int idxProduto = scanner.nextInt();
            System.out.print("Quantidade: ");
            int qtd = scanner.nextInt();
            scanner.nextLine();

            if (idxProduto < 0 || idxProduto >= produtos.size()) {
                System.out.println("Índice inválido.");
                continue;
            }

            try {
                pedido.adicionarItem(produtos.get(idxProduto), qtd);
            } catch (IllegalArgumentException e) {
                System.out.println("Erro: " + e.getMessage());
            }

            System.out.print("Adicionar mais itens? (s/n): ");
            continuar = scanner.nextLine();
        }

        pedido.setStatus(StatusPedido.FILA);
        pedidos.add(pedido);
        filaPedidos.add(pedido);
        System.out.println("Pedido criado e adicionado à fila de processamento.");
    }

    // Listagem de clientes
    private static void listarClientes() {
        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado.");
            return;
        }

        for (int i = 0; i < clientes.size(); i++) {
            System.out.println(i + " - " + clientes.get(i));
        }
    }

    // Listagem de produtos
    private static void listarProdutos() {
        if (produtos.isEmpty()) {
            System.out.println("Nenhum produto cadastrado.");
            return;
        }

        for (int i = 0; i < produtos.size(); i++) {
            System.out.println(i + " - " + produtos.get(i));
        }
    }

    // Listagem de pedidos com status atualizado
    private static void listarPedidos() {
        if (pedidos.isEmpty()) {
            System.out.println("Nenhum pedido criado.");
            return;
        }

         for (int i = 0; i < pedidos.size(); i++) {
            Pedido p = pedidos.get(i);
             System.out.println(i + " - Pedido de " + p.getCliente().getNome() +
                           " | Itens: " + p.getItens().size() +
                           " | Status: " + p.getStatus());

    }

    }
    // Edição de cliente
private static void editarCliente() {
    listarClientes();
    System.out.print("Escolha o índice do cliente para editar: ");
    int idx = scanner.nextInt();
    scanner.nextLine();

    if (idx < 0 || idx >= clientes.size()) {
        System.out.println("Índice inválido.");
        return;
    }

    Cliente cliente = clientes.get(idx);
    System.out.println("Cliente atual: " + cliente);

    System.out.print("Novo nome (ou Enter para manter): ");
    String novoNome = scanner.nextLine();
    System.out.print("Novo email (ou Enter para manter): ");
    String novoEmail = scanner.nextLine();

    if (!novoNome.isBlank()) {
        cliente.setNome(novoNome);
    }
    if (!novoEmail.isBlank()) {
        cliente.setEmail(novoEmail);
    }

    System.out.println("Cliente atualizado: " + cliente);
}

// Edição de produto
private static void editarProduto() {
    listarProdutos();
    System.out.print("Escolha o índice do produto para editar: ");
    int idx = scanner.nextInt();
    scanner.nextLine();

    if (idx < 0 || idx >= produtos.size()) {
        System.out.println("Índice inválido.");
        return;
    }

    Produto produto = produtos.get(idx);
    System.out.println("Produto atual: " + produto);

    System.out.print("Novo nome (ou Enter para manter): ");
    String novoNome = scanner.nextLine();
    System.out.print("Novo preço (ou Enter para manter): ");
    String precoStr = scanner.nextLine();

    if (!novoNome.isBlank()) {
        produto.setNome(novoNome);
    }
    if (!precoStr.isBlank()) {
        try {
            double novoPreco = Double.parseDouble(precoStr);
            produto.setPreco(novoPreco);
        } catch (NumberFormatException e) {
            System.out.println("Preço inválido.");
        }
    }

    System.out.println("Produto atualizado: " + produto);
}
private static void editarPedido() {
    listarPedidos();
    System.out.print("Escolha o índice do pedido para editar: ");
    int idx = scanner.nextInt();
    scanner.nextLine();

    if (idx < 0 || idx >= pedidos.size()) {
        System.out.println("Índice inválido.");
        return;
    }

    Pedido pedido = pedidos.get(idx);

    if (pedido.getStatus() != StatusPedido.ABERTO && pedido.getStatus() != StatusPedido.FILA) {
        System.out.println("Este pedido já está sendo processado ou foi finalizado. Não pode ser editado.");
        return;
    }

    System.out.println("Itens atuais do pedido:");
    List<ItemPedido> itens = pedido.getItens();
    for (int i = 0; i < itens.size(); i++) {
        System.out.println(i + " - " + itens.get(i));
    }

    System.out.println("\nOpções:");
    System.out.println("1. Adicionar novo item");
    System.out.println("2. Alterar quantidade de item");
    System.out.println("3. Remover item");
    System.out.print("Escolha uma opção: ");
    int opcao = scanner.nextInt();
    scanner.nextLine();

    switch (opcao) {
        case 1 -> {
            listarProdutos();
            System.out.print("Escolha o índice do produto: ");
            int idxProduto = scanner.nextInt();
            System.out.print("Quantidade: ");
            int qtd = scanner.nextInt();
            scanner.nextLine();

            if (idxProduto < 0 || idxProduto >= produtos.size()) {
                System.out.println("Índice inválido.");
                return;
            }

            try {
                pedido.adicionarItem(produtos.get(idxProduto), qtd);
                System.out.println("Item adicionado com sucesso!");
            } catch (IllegalArgumentException e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }

        case 2 -> {
            System.out.print("Escolha o índice do item para alterar: ");
            int idxItem = scanner.nextInt();
            System.out.print("Nova quantidade: ");
            int novaQtd = scanner.nextInt();
            scanner.nextLine();

            if (idxItem < 0 || idxItem >= itens.size()) {
                System.out.println("Índice inválido.");
                return;
            }

            if (novaQtd <= 0) {
                System.out.println("Quantidade inválida.");
                return;
            }

            ItemPedido item = itens.get(idxItem);
            itens.set(idxItem, new ItemPedido(item.getProduto(), novaQtd));
            System.out.println("Quantidade atualizada!");
        }

        case 3 -> {
            System.out.print("Escolha o índice do item para remover: ");
            int idxItem = scanner.nextInt();
            scanner.nextLine();

            if (idxItem < 0 || idxItem >= itens.size()) {
                System.out.println("Índice inválido.");
                return;
            }

            itens.remove(idxItem);
            System.out.println("Item removido!");
        }

        default -> System.out.println("Opção inválida.");
    }

    System.out.println("Pedido atualizado: " + pedido);
}

private static void excluirCliente() {
    listarClientes();
    System.out.print("Escolha o índice do cliente para excluir: ");
    int idx = scanner.nextInt();
    scanner.nextLine();

    if (idx < 0 || idx >= clientes.size()) {
        System.out.println("Índice inválido.");
        return;
    }

    Cliente removido = clientes.remove(idx);
    System.out.println("Cliente removido: " + removido);
}

private static void excluirProduto() {
    listarProdutos();
    System.out.print("Escolha o índice do produto para excluir: ");
    int idx = scanner.nextInt();
    scanner.nextLine();

    if (idx < 0 || idx >= produtos.size()) {
        System.out.println("Índice inválido.");
        return;
    }

    Produto removido = produtos.remove(idx);
    System.out.println("Produto removido: " + removido);
}
private static void excluirPedido() {
    listarPedidos();
    System.out.print("Escolha o índice do pedido para excluir: ");
    int idx = scanner.nextInt();
    scanner.nextLine();

    if (idx < 0 || idx >= pedidos.size()) {
        System.out.println("Índice inválido.");
        return;
    }
    Pedido pedido = pedidos.get(idx);
    if (pedido.getStatus() == StatusPedido.FINALIZADO) {
        System.out.println("Este pedido já foi finalizado e não pode ser excluído.");
        return;
    }



    Pedido removido = pedidos.remove(idx);
    filaPedidos.remove(pedido);
    System.out.println("Pedido removido: " + removido);

}

}