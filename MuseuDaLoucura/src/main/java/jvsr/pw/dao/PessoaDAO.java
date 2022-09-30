package jvsr.pw.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import jvsr.pw.modelo.Pessoa;

public class PessoaDAO {
	
	public List<Pessoa> obterPessoas(Long id_agendamento){
		EntityManager em = new JPAUtil().getEntityManager();
		em.getTransaction().begin();
		Query query = em.createNativeQuery("select pessoas_id from agendamento_pessoa where agendamento_id = :pAgend");
		query.setParameter("pAgend", id_agendamento);
		em.getTransaction().commit();
		
		DAO<Pessoa> dao = new DAO<Pessoa>(Pessoa.class);
		
		List<Pessoa> pessoaList = new ArrayList<Pessoa>();
		
		for(Object id : query.getResultList())
			for(Pessoa p : dao.listaTodos())
				if(id.toString().equals(p.getId().toString())) 
					pessoaList.add(p);
				
		return pessoaList;
	}
}
