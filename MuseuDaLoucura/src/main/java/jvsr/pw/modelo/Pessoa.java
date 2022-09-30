package jvsr.pw.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;

@Entity
public class Pessoa {
	
	@Id
	@SequenceGenerator(
			name="pessoa_id", 
			sequenceName="pessoa_seq",
			allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="pessoa_id")
	private Long id;
	
	@NotBlank(message = "Nome vazio")
	private String nome;
	
	@CPF
	private String cpf;
	
	@NotBlank(message = "Selecione o Tipo do ingresso")
	private String tipoDoIngresso;
	
	private boolean compareceu = false;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getTipoDoIngresso() {
		return tipoDoIngresso;
	}
	public void setTipoDoIngresso(String tipoDoIngresso) {
		this.tipoDoIngresso = tipoDoIngresso;
	}
	
	public boolean getCompareceu() {
		return compareceu;
	}
	public void setCompareceu(boolean compareceu) {
		this.compareceu = compareceu;
	}
	@Override
	public String toString() {
		return String.format("Pessoa [id=%s, nome=%s, cpf=%s, tipoDoIngresso=%s]", id, nome, cpf, tipoDoIngresso);
	}
	
}
