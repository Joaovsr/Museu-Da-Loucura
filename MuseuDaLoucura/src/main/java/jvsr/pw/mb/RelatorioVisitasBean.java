package jvsr.pw.mb;

import static jvsr.pw.utils.DateUtils.obterDia;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import jvsr.pw.dao.DAO;
import jvsr.pw.dao.PessoaDAO;
import jvsr.pw.modelo.Agendamento;
import jvsr.pw.modelo.Pessoa;
import jvsr.pw.modelo.RelatorioVisita;

@ViewScoped
@ManagedBean
public class RelatorioVisitasBean {
	private List<RelatorioVisita> relatorioVisitasList;
	
	@PostConstruct
	public void init() {
		List<Agendamento> agendamentos = new DAO<Agendamento>(Agendamento.class).listaTodos();
		
		relatorioVisitasList = new ArrayList<RelatorioVisita>();

		for(Agendamento a : agendamentos) {
			RelatorioVisita relatorioVisita = new RelatorioVisita();
			relatorioVisita.setData(obterDia(a.getData()));
			
			List<Pessoa> pessoasList = new PessoaDAO().obterPessoas(a.getId());
			
			int quantidade = 0;
			for(Pessoa p : pessoasList)
				if(p.getCompareceu())
					quantidade++;
			
			relatorioVisita.setQuantidadeDeVisitas(quantidade);
			
			if(!atualizarRelatorioVisita(relatorioVisita))
				relatorioVisitasList.add(relatorioVisita);
		}
			
	}
	
	public boolean atualizarRelatorioVisita(RelatorioVisita relatorioVisita) {
		int posicao = relatorioVisitasList.indexOf(relatorioVisita);

		if(posicao >=0) {
			int quantidade = relatorioVisitasList.get(posicao).getQuantidadeDeVisitas() + relatorioVisita.getQuantidadeDeVisitas();
			relatorioVisita.setQuantidadeDeVisitas(quantidade);
			relatorioVisitasList.set(posicao, relatorioVisita);
			return true;
		}
		return false;
	}

	
	public List<RelatorioVisita> getRelatorioVisitasList() {
		return relatorioVisitasList;
	}

	public void setRelatorioVisitasList(List<RelatorioVisita> relatorioVisitasList) {
		this.relatorioVisitasList = relatorioVisitasList;
	}

	
}
