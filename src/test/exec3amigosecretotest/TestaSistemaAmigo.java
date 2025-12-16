package br.ufpb.dcx.amigosecreto;

import java.util.List;

public class TestaSistemaAmigo {
    public static void main(String[] args) {
        SistemaAmigo sistema = new SistemaAmigo();

        try {
            sistema.cadastraAmigo("José", "jose@email.com");
            sistema.cadastraAmigo("Maria", "maria@email.com");
            System.out.println("3a) Amigos cadastrados: José e Maria.");
        } catch (AmigoJaExisteException e) {
            System.err.println("Erro ao cadastrar amigos: " + e.getMessage());
        }

        try {
            sistema.configuraAmigoSecretoDe("jose@email.com", "maria@email.com");
            sistema.configuraAmigoSecretoDe("maria@email.com", "jose@email.com");
            System.out.println("3b) Amigos secretos configurados (José -> Maria, Maria -> José).");
        } catch (AmigoInexistenteException e) {
            System.err.println("3b) Erro ao configurar amigo secreto: " + e.getMessage());
        }

        sistema.enviarMensagemParaAlguem("O seu presente será um livro!", "maria@email.com", "jose@email.com", true);
        System.out.println("3c) Mensagem anônima de Maria para José enviada.");

        sistema.enviarMensagemParaTodos("Lembrem-se de comprar o presente a tempo!", "maria@email.com", true);
        System.out.println("3d) Mensagem anônima de Maria para todos enviada.");

        sistema.enviarMensagemParaAlguem("José, sou eu Maria!", "maria@email.com", "jose@email.com", false);
        System.out.println("Mensagem NÃO anônima enviada para José.");


        System.out.println("\n3e) Pesquisa de Mensagens Anônimas:");
        List<Mensagem> mensagensAnonimas = sistema.pesquisaMensagensAnonimas();
        for (Mensagem m : mensagensAnonimas) {
            System.out.println("   -> " + m.getTextoCompletoAExibir());
        }

        try {
            String amigoSecretoDeJose = sistema.pesquisaAmigoSecretoDe("jose@email.com");
            if (amigoSecretoDeJose.equals("maria@email.com")) {
                System.out.println("\n3f) Pesquisa Amigo Secreto de José: Ok (É 'maria@email.com').");
            } else {
                System.out.println("\n3f) Pesquisa Amigo Secreto de José: Falha. Esperado 'maria@email.com', Encontrado: " + amigoSecretoDeJose);
            }

            System.out.println("\nTeste de exceção AmigoNaoSorteadoException:");
            try {
                sistema.cadastraAmigo("Pedro", "pedro@email.com");
                sistema.pesquisaAmigoSecretoDe("pedro@email.com"); // Amigo secreto ainda não configurado
            } catch (AmigoNaoSorteadoException ex) {
                System.out.println("   -> Exceção capturada com sucesso: " + ex.getMessage());
            } catch (AmigoInexistenteException | AmigoJaExisteException ignore) {}

        } catch (AmigoInexistenteException | AmigoNaoSorteadoException e) {
            System.err.println("\n3f) Erro ao pesquisar amigo secreto de José: " + e.getMessage());
        }
    }
}
