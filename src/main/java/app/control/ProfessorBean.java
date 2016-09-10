package app.control;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import app.dao.ProfessorDAO;
import app.model.Documento;
import app.model.Endereco;
import app.model.Pessoa;
import app.model.Professor;
import app.util.TipoPessoa;

@ManagedBean
@SessionScoped
public class ProfessorBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private ProfessorDAO dao;
	private Professor professor;
	private Pessoa pessoa;
	private List<Documento> documentos;
	private Documento documento;
	private List<Endereco> enderecos;
	private Endereco endereco;
	private Professor professorAnterior = null;
	private List<Professor> professors;
	private boolean editado;
	private String professorTab;
	private String documentoTab;
	private String enderecoTab;

	/**
	 *
	 */
	@PostConstruct
	public void init() {
		System.out.println("init");
		if (this.dao == null) {
			this.dao = new ProfessorDAO(Professor.class);
		}
		this.dao = new ProfessorDAO(Professor.class);
		this.professors = new ArrayList<Professor>();
		this.professor = new Professor();
		this.pessoa = new Pessoa();
		this.documentos = new ArrayList<Documento>();
		this.setDocumento(new Documento());
		this.enderecos = new ArrayList<Endereco>();
		this.endereco = new Endereco();
		this.documento = new Documento();
		this.setProfessorTab("active");
		this.setEnderecoTab("");
		this.setDocumentoTab("");
	}

	/**
	 *
	 * @return
	 */
	public String salvar() {
		try {

			this.pessoa.setTipoPessoa(TipoPessoa.PROFESSOR.toString());

			// adicionando documentos � pessoa
			this.pessoa.setDocumentos(this.documentos);
			vincularDocumento(this.pessoa, this.documentos);

			// adicionando endere�o pessoa
			this.pessoa.setEnderecos(this.enderecos);
			vincularEndereco(this.pessoa, this.enderecos);

			// adicionando pessoa � professors
			this.professor.setPessoa(this.pessoa);

			// executando metod DAO para salvar professor
			this.dao.save(this.professor);
			Professor novoProfessor = this.professor.clone();
			info("Informa��es salvas com sucesso.\n" + "Nome: " + this.pessoa.getNome());
			init();
			this.professors.add(novoProfessor);
			return "listarProfessor?faces-redirect=true";
		} catch (Exception e) {
			error("Erro ao Salvar informa��es: " + e.getMessage());
			return "listarProfessor?faces-redirect=true";
		}
	}

	public String criar(){
		init();
		return "salvarProfessor?faces-redirect=true";
	}

	/**
	 *
	 * @param id
	 * @return
	 */
	public String buscarPorId(Long id) {
		try {

			this.professor = this.dao.findById(id);
			if (this.professor != null) {
				info("Professor encontrado: " + this.professor.getPessoa().getNome());
			} else {
				warn("Professor n�o encontrado!");
			}
			return "atualizarProfessor?faces-redirect=true";
		} catch (Exception e) {
			error("Erro ao consultar dados do professor!");
			return "atualizarProfessor?faces-redirect=true";
		}
	}

	/**
	 *
	 * @param id
	 * @return
	 */
	public String remover(Professor professor) {
		try {
			if (this.professors != null && !this.professors.isEmpty()) {
				this.professor = professor;
				this.dao.remove(this.professor.getId());
				this.professors.remove(this.professor);
				info("Professor removido: " + this.professor.getPessoa().getNome());
				this.professor = new Professor();
			} else {
				warn("Houve um problema para remover o professor, verifique na listagem");
			}
			return "listarProfessor";
		} catch (Exception e) {
			error("Houve um problema para remover o professor, verifique na listagem");
		}
		return "listarProfessor?faces-redirect=true";
	}

	/**
	 *
	 * @return
	 */
	public String adicionarDocumento() {
		try {
			if (this.documentos == null) {
				this.documentos = new ArrayList<Documento>();
			}
			Documento doc = this.documento.clone();
			this.documentos.add(doc);
			this.documento = new Documento();
			this.professorTab = "";
			this.documentoTab = "active";
			this.enderecoTab = "";
		} catch (Exception e) {
			error("Erro ao adicionar documento � lista");
		}
		return "salvarProfessor?faces-redirect=true";
	}

	public String adicionarProfessor(){
		this.professorTab = "";
		this.documentoTab = "active";
		this.enderecoTab = "";
		return "salvarProfessor?faces-redirect=true";
	}

	/**
	 *
	 * @param documento
	 * @return
	 */
	public String removerDocumento(final Documento documento) {

		if ((this.documentos != null) && (!this.documentos.isEmpty())) {
			this.documentos.remove(documento);
			info("Documento removido com sucesso!");
			this.professorTab = "";
			this.documentoTab = "active";
			this.enderecoTab = "";
		} else {

			warn("N�o existem documentos � serem removidos");
		}
		return "salvarProfessor?faces-redirect=true";
	}

	/**
	 *
	 * @return
	 */

	public String adicionarEndereco() {
		try {
			if (this.enderecos == null) {
				this.enderecos = new ArrayList<Endereco>();
			}
			Endereco end = this.endereco.clone();
			this.enderecos.add(end);
			this.endereco = new Endereco();
			this.professorTab = "";
			this.documentoTab = "";
			this.enderecoTab = "active";

		} catch (Exception e) {
			error("Erro ao adicionar endereco � lista");
			return "salvarAluno?faces-redirect=true";
		}

		return "salvarProfessor?faces-redirect=true";
	}

	/**
	 *
	 * @param endereco
	 * @return
	 */
	public String removerEndereco(final Endereco endereco) {

		if ((this.enderecos != null) && (!this.enderecos.isEmpty())) {
			this.enderecos.remove(endereco);
			info("Endere�o removido com sucesso!");
		} else {
			warn("N�o existem endere�os � serem removidos");
		}
		return "salvarProfessor?faces-redirect=true";
	}

	/**
	 *
	 * @param p
	 * @param docs
	 */
	private void vincularDocumento(Pessoa p, List<Documento> docs) {
		for (Documento d : docs) {
			d.setPessoa(p);
		}
	}

	/**
	 *
	 * @param p
	 * @param ends
	 */
	private void vincularEndereco(Pessoa p, List<Endereco> ends) {
		for (Endereco e : ends) {
			e.setPessoa(p);
		}
	}

	/**
	 *
	 * @param professor
	 * @return
	 */
	public String atualizar(Professor professor) {
		try {
			this.professor = professor.clone();
			this.professorAnterior = professor;
			this.editado = true;
			return "atualizarProfessor?faces-redirect=true";
		} catch (Exception e) {
			error("Erro ao direcioar para atualiza��o de dados do professor");
			return "listarProfessor?faces-redirect=true";
		}
	}

	/**
	 *
	 * @return
	 */
	public String salvarAtualizar() {
		try {
			this.dao.update(this.professor);
			this.professors.remove(this.professorAnterior);
			this.professors.add(this.professor);
			this.editado = false;
			info("Dados de " + this.professor.getPessoa().getNome() + " atualizados");
			this.professor = new Professor();
			this.professorAnterior = new Professor();
			return "listarAluno?faces-redirect=true";
		} catch (Exception e) {
			error("Erro ao atualizar as informa��es!");
			return "atualizarProfessor?faces-redirect=true";
		}
	}

	/**
	 *
	 */
	public String cancelarAtualizar() {
		this.professor.restaurar(this.professorAnterior);
		this.professorAnterior = new Professor();
		editado = false;
		return "listarProfessor?faces-redirect=true";
	}

	/**
	 *
	 */
	public String buscarTodos() {
		this.professors = this.dao.findAll();
		if(this.professors.isEmpty()){
			warn("Nenhum Professor cadastrado");
		}
		return "listarProfessor?faces-redirect=true";
	}

	public String buscarPorNome(String nome){
		this.professors = this.dao.findByName(nome);
		if(this.professors.isEmpty()){
			warn("Nenhum Professor encontrado");
		}
		return "listarProfessor?faces-redirect=true";
	}

	/**
	 *
	 * @return
	 */
	public boolean isEditado() {
		return editado;
	}

	/**
	 *
	 * @param editado
	 */
	public void setEditado(boolean editado) {
		this.editado = editado;
	}

	/**
	 *
	 * @return
	 */
	public Professor getProfessorAnterior() {
		return professorAnterior;
	}

	/**
	 *
	 * @param professorAnterior
	 */
	public void setProfessorAnterior(Professor professorAnterior) {
		this.professorAnterior = professorAnterior;
	}

	/**
	 *
	 * @return
	 */
	public ProfessorDAO getDao() {
		return dao;
	}

	/**
	 *
	 * @param dao
	 */
	public void setDao(ProfessorDAO dao) {
		this.dao = dao;
	}

	/**
	 *
	 * @return
	 */
	public Professor getProfessor() {
		return professor;
	}

	/**
	 *
	 * @param professor
	 */
	public void setProfessor(Professor professor) {
		this.professor = professor;
	}

	/**
	 *
	 * @return
	 */
	public List<Professor> getProfessors() {
		return professors;
	}

	/**
	 *
	 * @param professors
	 */
	public void setProfessors(List<Professor> professors) {
		this.professors = professors;
	}

	/**
	 *
	 * @return
	 */
	public Pessoa getPessoa() {
		return pessoa;
	}

	/**
	 *
	 * @param pessoa
	 */
	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	/**
	 *
	 * @return
	 */
	public List<Documento> getDocumentos() {
		return documentos;
	}

	/**
	 *
	 * @param documentos
	 */
	public void setDocumentos(List<Documento> documentos) {
		this.documentos = documentos;
	}

	/**
	 *
	 * @return
	 */
	public List<Endereco> getEnderecos() {
		return enderecos;
	}

	/**
	 *
	 * @param enderecos
	 */
	public void setEnderecos(List<Endereco> enderecos) {
		this.enderecos = enderecos;
	}

	/**
	 *
	 * @return
	 */
	public Documento getDocumento() {
		return documento;
	}

	/**
	 *
	 * @param documento
	 */
	public void setDocumento(Documento documento) {
		this.documento = documento;
	}

	/**
	 *
	 * @return
	 */
	public Endereco getEndereco() {
		return endereco;
	}

	/**
	 *
	 * @param endereco
	 */
	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	/**
	 *
	 * @return
	 */
	public String getProfessorTab() {
		return professorTab;
	}

	/**
	 *
	 * @param professorTab
	 */
	public void setProfessorTab(String professorTab) {
		this.professorTab = professorTab;
	}

	/**
	 *
	 * @return
	 */
	public String getDocumentoTab() {
		return documentoTab;
	}

	/**
	 *
	 * @param documentoTab
	 */
	public void setDocumentoTab(String documentoTab) {
		this.documentoTab = documentoTab;
	}

	/**
	 *
	 * @return
	 */
	public String getEnderecoTab() {
		return enderecoTab;
	}

	/**
	 *
	 * @param enderecoTab
	 */
	public void setEnderecoTab(String enderecoTab) {
		this.enderecoTab = enderecoTab;
	}

	/**
	 *
	 * @param message
	 */
	public void info(String message) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, " Info", message));
	}

	/**
	 *
	 * @param message
	 */
	public void warn(String message) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_WARN, " Aten��o!", message));
	}

	/**
	 *
	 * @param message
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