package br.ufpb.dcx;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SistemaAmigoMap {
    private List<Mensagem> mensagens;
    // Usa Map para guardar amigos, onde a chave é o email (para acesso rápido)
    private Map<String, Amigo> amigos;

    public SistemaAmigoMap() {
        this.mensagens = new ArrayList<>();
        this.amigos = new HashMap<>();
    }


    public void cadastraAmigo(String nomeAmigo, String emailAmigo) throws AmigoJaExisteException {
        if (this.amigos.containsKey(emailAmigo)) {
            throw new AmigoJaExisteException("Amigo com o email " + emailAmigo + " já existe.");
        }
        this.amigos.put(emailAmigo, new Amigo(nomeAmigo, emailAmigo));
    }

    public Amigo pesquisaAmigo(String emailAmigo) throws AmigoInexistenteException {
        Amigo amigo = this.amigos.get(emailAmigo);
        if (amigo == null) {
            throw new AmigoInexistenteException("Amigo com o email " + emailAmigo + " não encontrado.");
        }
        return amigo;
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
        Amigo amigo = pesquisaAmigo(emailDaPessoa);
        amigo.setAmigoSorteado(emailAmigoSorteado);
    }

    public List<Mensagem> pesquisaTodasAsMensagens() {
        return this.mensagens;
    }

    public String pesquisaAmigoSecretoDe(String emailDaPessoa) throws AmigoInexistenteException, AmigoNaoSorteadoException {
        Amigo amigo = pesquisaAmigo(emailDaPessoa);

        String emailSorteado = amigo.getEmailAmigoSorteado();
        if (emailSorteado == null) {
            throw new AmigoNaoSorteadoException("O amigo com o email " + emailDaPessoa + " ainda não teve seu amigo secreto configurado.");
        }
        return emailSorteado;
    }
}