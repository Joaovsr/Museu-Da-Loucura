package jvsr.pw.utils;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;

public class EmailUtils {
	final String email_remetente = "joaoviniciusfone@gmail.com", senha_remetente = "33622024";
	
	public boolean enviar(String email_dst, String titulo, String mensagem) {
        Email email = new SimpleEmail();
        email.setHostName("smtp.googlemail.com");
        email.setSmtpPort(465);
        email.setAuthentication(email_remetente, senha_remetente);
        email.setSSLOnConnect(true);
        try {
            email.setFrom(email_remetente);
            email.setSubject(titulo);
            email.setMsg(mensagem);
//            email.addTo("priscila.sad@ifsudestemg.edu.br");
            email.addTo(email_dst);
            email.send();
        }catch(Exception e) {
        	e.printStackTrace();
        	return false;
        }
        
        return true;
	}
}
