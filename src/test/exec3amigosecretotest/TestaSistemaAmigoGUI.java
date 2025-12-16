package br.ufpb.dcx.amigosecreto;

import java.util.Scanner;

public class TestaSistemaAmigoGUI {
    public static void main(String[] args) {
        SistemaAmigo sistema = new SistemaAmigo();
        Scanner scanner = new Scanner(System.in);

        System.out.println("--- Sistema Amigo Secreto (Simulação GUI) ---");

        System.out.print("Digite a quantidade de amigos a cadastrar: ");
        int numAmigos = 0;
        try {
            numAmigos = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.err.println("Entrada inválida. Usando 2 amigos como padrão.");
            numAmigos = 2;
        }

        for (int i = 0; i < numAmigos; i++) {
            System.out.println("\n--- Cadastro do Amigo #" + (i + 1) + " ---");
            System.out.print("Nome: ");
            String nome = scanner.nextLine();
            System.out.print("E-mail: ");
            String email = scanner.nextLine();
            try {
                sistema.cadastraAmigo(nome, email);
                System.out.println("Amigo " + nome + " cadastrado com sucesso.");
            } catch (AmigoJaExisteException e) {
                System.err.println("Erro: " + e.getMessage());
            }
        }

        System.out.println("\n--- Configuração do Amigo Secreto (Sorteio) ---");
        for (int i = 0; i < numAmigos; i++) {
            System.out.print("E-mail da Pessoa que sorteou: ");
            String emailPessoa = scanner.nextLine();
            System.out.print("E-mail do Amigo Secreto (sorteado): ");
            String emailSorteado = scanner.nextLine();
            try {
                sistema.configuraAmigoSecretoDe(emailPessoa, emailSorteado);
                System.out.println("Configurado: " + emailPessoa + " pegou " + emailSorteado);
            } catch (AmigoInexistenteException e) {
                System.err.println("Erro ao configurar: " + e.getMessage());
            }
        }

        System.out.println("\n--- Envio de Mensagem para Todos ---");
        System.out.print("E-mail do Remetente: ");
        String remetente = scanner.nextLine();
        System.out.print("Texto da Mensagem: ");
        String texto = scanner.nextLine();
        System.out.print("Mensagem é anônima? (S/N): ");
        boolean anonima = scanner.nextLine().trim().equalsIgnoreCase("S");

        sistema.enviarMensagemParaTodos(texto, remetente, anonima);

        System.out.println("\n>> Mensagem enviada. Pesquisando todas as mensagens:");
        for (Mensagem m : sistema.pesquisaTodasAsMensagens()) {
            System.out.println("   -> " + m.getTextoCompletoAExibir());
        }

        scanner.close();
        System.out.println("\n--- Fim da Simulação ---");
    }
}