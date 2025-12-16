package br.ufpb.dcx;

public class MensagemParaTodos extends Mensagem {

    public MensagemParaTodos(String texto, String emailRemetente, boolean anonima) {
        super(texto, emailRemetente, anonima);
    }

    @Override
    public String getTextoCompletoAExibir() {
        String textoBase = " para todos. Texto: " + this.getTexto();
        if (this.ehAnonima()) {
            return "Mensagem" + textoBase;
        } else {
            return "Mensagem de " + this.getEmailRemetente() + textoBase;
        }
    }
}
