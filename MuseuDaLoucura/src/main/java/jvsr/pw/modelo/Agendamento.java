package jvsr.pw.modelo;

import static jvsr.pw.utils.MuseuDaLoucuraUtils.NUMERO_DE_PESSOAS_POR_VEZ;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

@Entity
public class Agendamento {
	@Id
	@SequenceGenerator(
			name="agendamento_id", 
			sequenceName="agendamento_seq",
			allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="agendamento_id")
	private Long id;

	@Temporal(TemporalType.DATE)
	private Calendar data = Calendar.getInstance();
	
	@NotBlank(message = "Horário inválido")
	private String horario;
	
	@Max((long) NUMERO_DE_PESSOAS_POR_VEZ) @Min(1L)
	private int numeroDePessoas;

	private String hash;
	
	@Email(message = "Email inválido")
	private String email;
	
	@OneToMany(cascade=CascadeType.ALL)
	private List<Pessoa> pessoas = new ArrayList<>();

	public Long getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Calendar getData() {
		return data;
	}

	public void setData(Calendar data) {
		this.data = data;
	}

	public String getHorario() {
		return horario;
	}

	public void setHorario(String horario) {
		this.horario = horario;
	}

	public int getNumeroDePessoas() {
		return numeroDePessoas;
	}

	public void setNumeroDePessoas(int numeroDePessoas) {
		this.numeroDePessoas = numeroDePessoas;
	}

	public List<Pessoa> getPessoas() {
		return pessoas;
	}

	public void setPessoas(List<Pessoa> pessoas) {
		this.pessoas = pessoas;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}
}