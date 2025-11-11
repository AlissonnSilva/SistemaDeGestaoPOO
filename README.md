 **Sistema de Gestão — Projeto Java**

Este é um sistema de gestão desenvolvido em Java, com foco em organização de clientes, produtos e pedidos. O projeto utiliza estruturas de dados eficientes, controle por menu interativo e processamento de pedidos em segundo plano via multithreading.

📌**Funcionalidades Principais**
- Cadastro de Clientes e Produtos
- Inserção de novos registros com validação de dados.
- Armazenamento em listas dinâmicas na memória.
- Criação de Pedidos
- Associação entre clientes e produtos.
- Adição de pedidos à fila para processamento assíncrono.
- Listagem de Dados
- Visualização de clientes, produtos e pedidos com formatação clara.
- Edição e Exclusão
- Atualização de dados existentes.
- Remoção segura de registros.
- Processamento em Segundo Plano
- Utilização de Thread para simular o fluxo de pedidos em tempo real.

🧠**Estrutura e Organização**

🔹 Classes Principais

- Sistema: ponto de entrada da aplicação, gerencia o menu e as operações.
- Cliente: representa os dados de um cliente (nome, email).
- Produto: representa os dados de um produto (nome, preço, estoque).
- Pedido: associa cliente e produto, com quantidade e status.
- ProcessadorPedidos: classe que roda em uma thread separada para processar pedidos da fila.

🔹 Estruturas de Dados Utilizadas
- List<Cliente>: lista de clientes.
- List<Produto>: lista de produtos.
- List<Pedido>: histórico de pedidos.
- Queue<Pedido>: fila de pedidos pendentes.

🛠️ **Tecnologias Utilizadas**
- Java 17+
- JDK padrão (sem frameworks externos)
- Paradigma Orientado a Objetos
- Multithreading com Thread e Runnable
- Coleções Java (ArrayList, Queue, LinkedList)
- Tratamento de exceções com try-catch

🚀 **Como Executar**
- Clone o projeto:
git clone https://github.com/seu-usuario/sistema-gestao-java.git
cd sistema-gestao-java
- Compile os arquivos .java:
javac SistemaDeGestao/*.java
- Execute o sistema:
java SistemaDeGestao.Sistema



🧪 **Testes e Validações**
- Validação de email e campos obrigatórios no cadastro de clientes.
- Verificação de estoque e produto válido na criação de pedidos.
- Tratamento de entradas inválidas no menu principal.

📂 **Estrutura de Diretórios**

SistemaDeGestao/

├── Sistema.java

├── Cliente.java

├── Produto.java

├── Pedido.java

├── ProcessadorPedidos.java

├── ItemPedido.java

├── CategoriaProduto.java

├── StatusPedido.java



👥 Autores
- Alisson Eraldo da Silva - engs-alissonsilva@camporeal.edu.br
- Gabriel Beledeli Hul - engs-gabrielhul@camporeal.edu.br

Projeto acadêmico desenvolvido para fins de aprendizado e prática de programação orientada a objetos.



