package jvsr.pw.mb;

import static jvsr.pw.utils.MuseuDaLoucuraUtils.HORARIOS_DE_AGENDAMENTO;
import static jvsr.pw.utils.MuseuDaLoucuraUtils.TIPOS_DE_INGRESSOS;
import static jvsr.pw.utils.MuseuDaLoucuraUtils.enviarEmail;
import static jvsr.pw.utils.MuseuDaLoucuraUtils.enviarEmailCancelamento;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import jvsr.pw.dao.AgendamentoDAO;
import jvsr.pw.dao.DAO;
import jvsr.pw.dao.PessoaDAO;
import jvsr.pw.modelo.Agendamento;
import jvsr.pw.modelo.Pessoa;
import jvsr.pw.utils.HashUtils;

@ViewScoped
@ManagedBean
public class AgendamentoBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private Agendamento agendamento = new Agendamento();
	private Pessoa pessoa = new Pessoa();

	public String agendar() {
		try {
			DAO<Agendamento> dao = new DAO<Agendamento>(Agendamento.class);

			int quantidadeDePessoas = agendamento.getPessoas().size();

			if(quantidadeDePessoas > obterNumeroDeVagas()) {
				return "agendamento.xhtml?faces-redirect=false";
			}

			agendamento.setNumeroDePessoas(quantidadeDePessoas);

			//Gera um hash baseado na data.getTimeInMilis+ agendamento.toString()
			String valueHash = String.format("%d%s",Calendar.getInstance().getTimeInMillis(),agendamento.toString());
			agendamento.setHash(HashUtils.getHashMd5(valueHash));

			dao.adiciona(agendamento);

			enviarEmail(agendamento, false);

			this.agendamento = new Agendamento();

			return "sucesso?faces-redirect=true";
		}catch(Exception e) {
			return "erro?faces-redirect=true";
		}
	}

	public String alterar() {
		try {
			DAO<Agendamento> daoAgendamento = new DAO<Agendamento>(Agendamento.class);
			DAO<Pessoa> daoPessoa = new DAO<Pessoa>(Pessoa.class);

			if(agendamento.getPessoas().size() == 0) 
				return remove();
			
			agendamento.setNumeroDePessoas(agendamento.getPessoas().size());

			daoAgendamento.atualiza(agendamento);
			
			for(Pessoa p : agendamento.getPessoas())
				daoPessoa.atualiza(p);
			
			enviarEmail(agendamento, true);

			this.agendamento = new Agendamento();

			return "confirmacao_alteracao?faces-redirect=true";
		}catch(Exception e) {
			return "erro_alteracao?faces-redirect=true";
		}
	}

	public void guardarPessoa() {

		if(agendamento.getPessoas().size() >= obterNumeroDeVagas()) {
			return;
		}

		if(agendamento.getPessoas().contains(pessoa)) 
			agendamento.getPessoas().remove(pessoa);
		
		agendamento.getPessoas().add(pessoa);
		agendamento.setNumeroDePessoas(agendamento.getPessoas().size());
		
		pessoa = new Pessoa();
	}

	public void cancela() {
		this.agendamento = new Agendamento();
	}

	public void removePessoa(Pessoa pessoa) {
		agendamento.getPessoas().remove(pessoa);
		new DAO<>(Pessoa.class).remove(pessoa);
		agendamento.setNumeroDePessoas(agendamento.getPessoas().size());
	}

	public String remove() {
		try {
			DAO<Agendamento> dao = new DAO<>(Agendamento.class);
			
			DAO<Pessoa> daoPessoa = new DAO<Pessoa>(Pessoa.class);
			
			dao.remove(agendamento);
			
			for(Pessoa p : agendamento.getPessoas())
				daoPessoa.remove(p);
			
			enviarEmailCancelamento(agendamento.getEmail());

			agendamento = new Agendamento();
			pessoa = new Pessoa();

			return "confirmacao_cancelamento?faces-redirect=true";
		}catch(Exception e) {
			return "erro_cancelamento?faces-redirect=true";
		}
	}

	public void solicitarAlteracao() {
		Agendamento a = new AgendamentoDAO().recupera(agendamento.getHash(), agendamento.getEmail());

		if(a == null)
			return;

		List<Pessoa> pessoas = new PessoaDAO().obterPessoas(a.getId());

		a.setPessoas(pessoas);
		this.setAgendamento(a);
	}

	public List<String> obterTiposDeIngressos(){
		return TIPOS_DE_INGRESSOS;
	}

	public List<String> obterHorarios(){
		return HORARIOS_DE_AGENDAMENTO;
	}

	public List<String> obterHorariosDisponiveis() {
		return new AgendamentoDAO().obterHorariosDisponiveis(agendamento.getData());
	}

	public List<Pessoa> obterPessoasHorarioData(){
		List<Agendamento> agendamentos = new AgendamentoDAO().obterAgendamentos(agendamento.getData());

		List<Pessoa> pessoas = new ArrayList<Pessoa>();

		for (Agendamento a : agendamentos)
			if(a.getHorario().equalsIgnoreCase(agendamento.getHorario())) {
				List<Pessoa> pList = new PessoaDAO().obterPessoas(a.getId());
				pessoas.addAll(pList);
			}

		return pessoas;
	}

	public void mudarSituacaoCompareceuPessoa(Pessoa pessoa, boolean situacaoCompareceu) {
		DAO<Pessoa> dao = new DAO<Pessoa>(Pessoa.class);
		pessoa.setCompareceu(situacaoCompareceu);
		dao.atualiza(pessoa);
	}

	public int obterNumeroDeVagas() {
		return new AgendamentoDAO().obterNumeroDeVagas(agendamento.getData(), agendamento.getHorario());
	}

	public Agendamento getAgendamento() {
		return agendamento;
	}

	public void setAgendamento(Agendamento agendamento) {
		this.agendamento = agendamento;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public void consultarNumeroDeVagas(FacesContext fc, UIComponent component, Object value)
			throws ValidatorException {
		int quantidadeDePessoas = agendamento.getPessoas().size();
		
		if (quantidadeDePessoas > obterNumeroDeVagas()) {
			throw new ValidatorException(new FacesMessage("Número de vagas excedido"));
		}
	}
}
