package app.dao;

import java.util.List;

import app.model.Documento;

public class DocumentoDAO extends DAOImpl<Documento> {

	public DocumentoDAO(Class<Documento> clazz) {
		super(clazz);
	}

	public List<Documento> findByPersonId(Long personId){
		return entitymanager.createNamedQuery("Documento.findByPersonId", Documento.class)
				.setParameter("personId", personId).getResultList();
	}

}
