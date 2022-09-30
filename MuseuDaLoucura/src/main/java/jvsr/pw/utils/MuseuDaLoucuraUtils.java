package jvsr.pw.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jvsr.pw.modelo.Agendamento;
import jvsr.pw.modelo.Pessoa;

public class MuseuDaLoucuraUtils {
	public static final List<String> TIPOS_DE_INGRESSOS = new ArrayList<String>( 
			Arrays.asList(
					"Inteira",
					"Meia Entrada[Estudante]",
					"Isento [Menor de 5 anos]",
					"Morador Cadastrado",
					"Condutores Credenciados" )
			);

	public static final List<String> HORARIOS_DE_AGENDAMENTO = new ArrayList<String>( 
			Arrays.asList(
					"09:00",
					"10:00",
					"11:00",
					"12:00",
					"13:00",
					"14:00",
					"15:00",
					"16:00",
					"17:00",
					"18:00") 
			);

	public static final int NUMERO_DE_PESSOAS_POR_VEZ = 2;

	public static final boolean enviarEmail(Agendamento agendamento, boolean email_de_alteracao) {
		StringBuilder titulo_email = new StringBuilder("Sistema de Agendamento - Museu Da Loucura");

		String tipo_do_email = email_de_alteracao ? "alterado": "confirmado";

		StringBuilder texto_email = new StringBuilder(
				String.format(
						"Seu agendamento foi %s.\n"
								+ "Informações do agendamento\n\n"
								+ "Dia: %s\n Horário: %s\n\n",
								tipo_do_email, DateUtils.obterDia(agendamento.getData()), agendamento.getHorario()
						)
				);

		texto_email.append("CPF:                  Nome:\n");
		for(Pessoa pessoa : agendamento.getPessoas())
			texto_email.append(String.format("%s  %s\n", pessoa.getCpf(), pessoa.getNome()));

		texto_email.append("\n\nSeu código de agendamento: ");
		texto_email.append(agendamento.getHash());

		texto_email.append("\n\nObrigado por realizar o agendamento conosco\n");

		try {
			new EmailUtils().enviar(agendamento.getEmail(), titulo_email.toString(), texto_email.toString());
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public static final boolean enviarEmailCancelamento(String email) {
		String titulo_email = new String("Sistema de Agendamento - Museu Da Loucura");

		String texto_email = new String("Seu agendamento foi cancelado!.");

		try { new EmailUtils().enviar(email, titulo_email.toString(), texto_email.toString());
		} catch (Exception e) { return false; }

		return true;
	}
}
