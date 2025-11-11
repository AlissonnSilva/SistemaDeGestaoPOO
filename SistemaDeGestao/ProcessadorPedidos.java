package SistemaDeGestao;

import java.util.Queue;

public class ProcessadorPedidos implements Runnable {
    private Queue<Pedido> fila;

    // Construtor que recebe a fila
    public ProcessadorPedidos(Queue<Pedido> fila) {
        this.fila = fila;
    }

    @Override
    public void run() {
        while (true) {
            Pedido pedido = fila.poll(); // Remove e retorna o primeiro pedido da fila (ou null se estiver vazia)

            if (pedido != null) {
                pedido.setStatus(StatusPedido.PROCESSANDO);
                System.out.println("\nProcessando pedido de " + pedido.getCliente().getNome());
                try {
                    Thread.sleep(3000); // Simula tempo de processamento
                } catch (InterruptedException e) {
                    e.printStackTrace(); // Exibe no console a pilha de chamadas que levou à exceção, útil para depuração

                }
                pedido.setStatus(StatusPedido.FINALIZADO);
                System.out.println("Pedido finalizado: " + pedido);
            }
             try {
            Thread.sleep(1000); // espera antes de verificar a fila novamente
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        }
    }
}
