package app.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import app.model.Aluno;

public class AlunoDAO extends DAOImpl<Aluno> {

	/**
	 *
	 * @param clazz
	 */
	public AlunoDAO(Class<Aluno> clazz) {
		super(clazz);
	}

	/**
	 *
	 * @param registrationNumber
	 * @return
	 * @throws SQLException
	 * @author ARTUR
	 *
	 */
	public List<Aluno> findByRegistrationNumber(String registrationNumber) throws NoResultException {
		Query query = entitymanager.createNamedQuery("Aluno.findByRegistrationNumber", Aluno.class)
				.setParameter("wantedNumber", "%" + registrationNumber);
		return query.getResultList();

	}

	/**
	 *
	 * @return
	 * @throws SQLException
	 * @author ARTUR
	 */
	public String findLastRegistrationNumber() throws SQLException {
		return (String) entitymanager.createNamedQuery("Aluno.findLastRegistrationNumber", String.class)
				.getSingleResult();
	}

	/**
	 *
	 * @param status
	 * @return
	 * @throws SQLException
	 * @author ARTUR
	 */
	public List<Aluno> findByStatus(String status) throws SQLException {
		return (List<Aluno>) entitymanager.createNamedQuery("Aluno.findByStatus", Aluno.class)
				.setParameter("wantedStatus", status).getResultList();
	}

	public List<Aluno> findByTurmaId(Long id) {
		return (List<Aluno>) entitymanager.createNamedQuery("Aluno.findByTurmaId", Aluno.class)
		.setParameter("turmaId", id).getResultList();
	}
}
