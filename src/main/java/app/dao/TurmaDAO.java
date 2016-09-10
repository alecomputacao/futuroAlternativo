package app.dao;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import app.model.Aluno;
import app.model.Turma;

public class TurmaDAO extends DAOImpl<Turma> {

	public TurmaDAO(Class<Turma> clazz) {
		super(clazz);
	}

	public List<Turma> findByStatus(String status) {
		Query query = entitymanager.createNamedQuery("Turma.findByStatus", Turma.class)
				.setParameter("wantedStatus", status);
			return query.getResultList();
	}

	public List<Turma> findByCode(String codigo){
		Query query = entitymanager.createNamedQuery("Turma.findByCode", Turma.class)
				.setParameter("code", codigo);
		return query.getResultList();
	}

}
