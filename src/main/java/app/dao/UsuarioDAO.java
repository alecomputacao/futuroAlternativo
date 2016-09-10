package app.dao;

import java.sql.SQLException;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import app.model.Usuario;

public class UsuarioDAO extends DAOImpl<Usuario>{

	private static Logger logger = Logger.getLogger(UsuarioDAO.class);

	public UsuarioDAO(Class<Usuario> clazz) {
		super(clazz);
	}

	public boolean validateUser(String nomeUsuario, String senha) throws SQLException {
		try {
			Usuario usuario = (Usuario) entitymanager
					.createQuery("SELECT u from Usuario u where u.nomeUsuario = :name and u.senha = :senha")
					.setParameter("name", nomeUsuario).setParameter("senha", senha).getSingleResult();

			if (usuario.getNomeUsuario() != null && usuario.getSenha() != null) {

				return true;

			}

		} catch (NoResultException e) {
			System.out.println("Login error -->" + e.getMessage());
			logger.error("Login error -->" + e.getMessage(), e);
			return false;
		}
		return false;

	}

	public boolean save2(Usuario usuario) {
		try {
			entitymanager.persist(usuario);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean delete2(Usuario usuario) {
		try {
			entitymanager.remove(usuario);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public Usuario findByName(String name) {
		Query query = entitymanager.createNamedQuery("Usuario.findByName", Usuario.class)
				.setParameter("name", name);
		try {
			return (Usuario) query.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}