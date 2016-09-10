package app.control;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import app.dao.DocumentoDAO;
import app.model.Documento;
import app.model.Pessoa;

@ManagedBean(name = "documentoBean")
@SessionScoped
public class DocumentoBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private DocumentoDAO dao;
	private Documento documento;
	private List<Documento> documentos;
	private Pessoa pessoa;
	private Documento documentoAnterior;
	private boolean editado;
	private String tipoPessoa;


	@PostConstruct
	public void init() {
		if (dao == null) {
			dao = new DocumentoDAO(Documento.class);
		}
		this.documento = new Documento();
		this.documentos = new ArrayList<Documento>();
		this.pessoa = new Pessoa();
	}

	/**
	 *
	 * @param id
	 * @return
	 */
	public String buscarPorId(Long id) {
		this.documento = this.dao.findById(id);
		if (this.documento != null) {
			info("Sucesso");
		} else {
			warn("Documetno n�o encotnrado");
		}
		return "listarDocumento?faces-redirect=true";
	}

	/**
	 *
	 * @param id
	 * @return
	 */
	public String remover(Long id) {
		if(this.documentos.size() == 1){
			error(pessoa.getTipoPessoa() + " precisa possuir pelo menos um documento. Opera��o n�o permitida.");
		}
		else{
			Documento doc = this.dao.remove(id);
			if(!doc.equals(null)){
				this.documentos.remove(doc);
				info(doc.getTipo() + " removido com suecsso.");
			}
			else{
				warn("documento n�o encontrado na base de dados, favor verificar novamente na lista.");
			}
		}

		return "listarDocumento?faces-redirect=true";
	}

	/**
	 *
	 * @param documento
	 * @return
	 */
	public String atualizar(Documento documento) {
		try {
			this.documento = documento.clone();
			this.documentoAnterior = documento;
			this.editado = true;
			return "atualizarDocumento?faces-redirect=true";
		} catch (Exception e) {
			error("Erro ao direcioar para atualiza��o de dados do documento");
			return "listarDocumento?faces-redirect=true";
		}
	}

	/**
	 *
	 * @return
	 */
	public String salvarAtualizar() {
		try {
			this.dao.update(this.documento);
			this.documentos.remove(this.documentoAnterior);
			this.documentos.add(this.documento);
			this.editado = false;
			info("Documento " + this.documento.getTipo() + " " + this.documento.getNumero() + " atualizados");
			this.documento = new Documento();
			this.documentoAnterior = new Documento();
			return "listarDocumento?faces-redirect=true";
		} catch (Exception e) {
			error("Erro ao atualizar as informa��es!");
			return "atualizarDocumento?faces-redirect=true";
		}
	}

	public List<Documento> buscarTodos() {
		return this.dao.findAll();
	}

	/**
	 *
	 * @param pessoa
	 * @return
	 */
	public String atualizarDocumentos(Pessoa pessoa) {
		this.pessoa = pessoa;
		this.documentos = pessoa.getDocumentos();
		this.setTipoPessoa(pessoa.getTipoPessoa());
		if (this.documentos.isEmpty() || this.documentos == null) {
			warn("Nenhum documento encontrado para " + pessoa.getNome());
		}
		return "listarDocumento?faces-redirect=true";
	}

	public void buscarPorPessoa(Pessoa pessoa){
		this.documentos = this.dao.findByPersonId(pessoa.getId());
	}
	/**
	 *
	 * @return
	 */

	public String cancelarAtualizar() {
		this.documento.restaurar(this.documentoAnterior);
		this.documentoAnterior = new Documento();
		editado = false;
		return "listarDocumento?faces-redirect=true";
	}

	public DocumentoDAO getDao() {
		return dao;
	}

	public void setDao(DocumentoDAO dao) {
		this.dao = dao;
	}

	public Documento getDocumento() {
		return documento;
	}

	public void setDocumento(Documento documento) {
		this.documento = documento;
	}

	public List<Documento> getDocumentos() {
		return documentos;
	}

	public void setDocumentos(List<Documento> documentos) {
		this.documentos = documentos;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public Documento getDocumentoAnterior() {
		return documentoAnterior;
	}

	public void setDocumentoAnterior(Documento documentoAnterior) {
		this.documentoAnterior = documentoAnterior;
	}

	public boolean isEditado() {
		return editado;
	}

	public void setEditado(boolean editado) {
		this.editado = editado;
	}

	public String getTipoPessoa() {
		return tipoPessoa;
	}

	public void setTipoPessoa(String tipoPessoa) {
		this.tipoPessoa = tipoPessoa;
	}

	/**
	 *
	 * @param message
	 *
	 */
	public void info(String message) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, " Info", message));
	}

	/**
	 *
	 * @param message
	 *
	 */
	public void warn(String message) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_WARN, " Atencaoo!", message));
	}

	/**
	 *
	 * @param message
	 *
	 */
	public void error(String message) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, " Erro!", message));
	}

	/**
	 *
	 * @param message
	 */
	public void fatal(String message) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_FATAL, " Fatal!", message));
	}

}
