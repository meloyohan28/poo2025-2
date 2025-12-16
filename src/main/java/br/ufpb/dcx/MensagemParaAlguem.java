package br.ufpb.dcx;

public class MensagemParaAlguem extends Mensagem {
    private String emailDestinatario;


    public MensagemParaAlguem(String texto, String emailRemetente, String emailDestinatario, boolean anonima) {
        super(texto, emailRemetente, anonima);
        this.emailDestinatario = emailDestinatario;
    }

    public String getEmailDestinatario() {
        return emailDestinatario;
    }

    public void setEmailDestinatario(String emailDestinatario) {
        this.emailDestinatario = emailDestinatario;
    }


    @Override
    public String getTextoCompletoAExibir() {
        String textoBase = " para " + this.emailDestinatario + ". Texto: " + this.getTexto();
        if (this.ehAnonima()) {
            return "Mensagem" + textoBase;
        } else {
            return "Mensagem de: " + this.getEmailRemetente() + textoBase;
        }
    }
}
