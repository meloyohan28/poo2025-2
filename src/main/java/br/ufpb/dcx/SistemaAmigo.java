package br.ufpb.dcx;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SistemaAmigo {
    private List<Mensagem> mensagens;
    private List<Amigo> amigos;

    // Construtor
    public SistemaAmigo() {
        this.mensagens = new ArrayList<>();
        this.amigos = new ArrayList<>();
    }

    public void cadastraAmigo(String nomeAmigo, String emailAmigo) throws AmigoJaExisteException {
        // Verifica se o amigo já existe
        for (Amigo a : amigos) {
            if (a.getEmail().equalsIgnoreCase(emailAmigo)) {
                throw new AmigoJaExisteException("Amigo com o email " + emailAmigo + " já existe.");
            }
        }
        this.amigos.add(new Amigo(nomeAmigo, emailAmigo));
    }

    public Amigo pesquisaAmigo(String emailAmigo) throws AmigoInexistenteException {
        for (Amigo a : amigos) {
            if (a.getEmail().equalsIgnoreCase(emailAmigo)) {
                return a;
            }
        }
        throw new AmigoInexistenteException("Amigo com o email " + emailAmigo + " não encontrado.");
    }

    public void enviarMensagemParaTodos(String texto, String emailRemetente, boolean ehAnonima) {
        Mensagem m = new MensagemParaTodos(texto, emailRemetente, ehAnonima);
        this.mensagens.add(m);
    }

    public void enviarMensagemParaAlguem(String texto, String emailRemetente, String emailDestinatario, boolean ehAnonima) {
        Mensagem m = new MensagemParaAlguem(texto, emailRemetente, emailDestinatario, ehAnonima);
        this.mensagens.add(m);
    }

    public List<Mensagem> pesquisaMensagensAnonimas() {
        return this.mensagens.stream()
                .filter(Mensagem::ehAnonima)
                .collect(Collectors.toList());
    }

    public void configuraAmigoSecretoDe(String emailDaPessoa, String emailAmigoSorteado) throws AmigoInexistenteException {
        Amigo amigo = pesquisaAmigo(emailDaPessoa); // Reutiliza a busca que lança AmigoInexistenteException
        amigo.setAmigoSorteado(emailAmigoSorteado);
    }

    public List<Mensagem> pesquisaTodasAsMensagens() {
        return this.mensagens;
    }

    public String pesquisaAmigoSecretoDe(String emailDaPessoa) throws AmigoInexistenteException, AmigoNaoSorteadoException {
        Amigo amigo = pesquisaAmigo(emailDaPessoa); // Reutiliza a busca

        String emailSorteado = amigo.getEmailAmigoSorteado();
        if (emailSorteado == null) {
            throw new AmigoNaoSorteadoException("O amigo com o email " + emailDaPessoa + " ainda não teve seu amigo secreto configurado.");
        }
        return emailSorteado;
    }
}
