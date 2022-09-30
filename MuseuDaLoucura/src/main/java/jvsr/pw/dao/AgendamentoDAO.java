package jvsr.pw.dao;

import static jvsr.pw.utils.DateUtils.obterDia;
import static jvsr.pw.utils.MuseuDaLoucuraUtils.HORARIOS_DE_AGENDAMENTO;
import static jvsr.pw.utils.MuseuDaLoucuraUtils.NUMERO_DE_PESSOAS_POR_VEZ;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import jvsr.pw.modelo.Agendamento;
import jvsr.pw.utils.DateUtils;

public class AgendamentoDAO {
	
	public Agendamento recupera(String hash, String email) {
		List<Agendamento> agendamentos = new DAO<Agendamento>(Agendamento.class).listaTodos();
		
		for(Agendamento a : agendamentos)
			if(a.getEmail().equalsIgnoreCase(email) && a.getHash().equals(hash))
				return a;
		
		return null;
	}
	
	public List<Agendamento> obterAgendamentos(Calendar calendar) {
		
		List<Agendamento> agendamentos = new ArrayList<Agendamento>();

		String dia = DateUtils.obterDia(calendar);

		for(Agendamento agendamento : new DAO<Agendamento>(Agendamento.class).listaTodos()) 
			if (obterDia(agendamento.getData()).equals(dia))
				agendamentos.add(agendamento);

		return agendamentos;
	}

	public Integer obterNumeroDeVagas(Calendar data, String horario) {
		List<Agendamento> agendamentosDia = obterAgendamentos(data);

		if(horario == null)
			return 0;

		int numeroDeVagas = 0;
		for (Agendamento agendamento : agendamentosDia) 
			if (agendamento.getHorario().equalsIgnoreCase(horario)) 
				numeroDeVagas += agendamento.getNumeroDePessoas();
			

		return NUMERO_DE_PESSOAS_POR_VEZ - numeroDeVagas;
	}

	public List<String> obterHorariosDisponiveis(Calendar dia) {
		List<Agendamento> agendamentosDia = obterAgendamentos(dia);

		List<String> horariosDisponiveis = new ArrayList<String>(HORARIOS_DE_AGENDAMENTO);

		for(int i=0; i< HORARIOS_DE_AGENDAMENTO.size(); i++) {
			int numeroDeVagas = 0;
			for (Agendamento agendamento : agendamentosDia) {
				if (agendamento.getHorario().equalsIgnoreCase(HORARIOS_DE_AGENDAMENTO.get(i))) 
					numeroDeVagas += agendamento.getNumeroDePessoas();
			}
			if (numeroDeVagas >= NUMERO_DE_PESSOAS_POR_VEZ)
				horariosDisponiveis.remove(i);
		}

		return horariosDisponiveis;
	}
}
