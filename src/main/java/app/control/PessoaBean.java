package app.control;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import app.dao.PessoaDAO;
import app.model.Pessoa;

public class PessoaBean implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private PessoaDAO dao = null;
	private Pessoa pessoa;
	private Pessoa pessoaAnterior = null;
	private List<Pessoa> pessoaLista;
	private boolean editado;
	private String msg = null;

	@PostConstruct
	public void init() {
		if (this.dao == null) {
			this.dao = new PessoaDAO(Pessoa.class);
		}
		this.pessoaLista = new ArrayList<Pessoa>();
		this.pessoa = new Pessoa();
	}

	public String salvar() {
		System.out.println(this.pessoa.toString());

		try {
			this.dao.save(this.pessoa);
			this.pessoaLista.add(this.pessoa);
			pessoa = new Pessoa();
			info("Informa��es salvas com sucesso");
			return "salvar";
		} catch (Exception e) {
			error("Erro ao Salvar informa��es: " + e.getMessage());
			return "salvar";
		}
	}

	public String buscarPorId(Long id) {
		this.pessoa = this.dao.findById(id);
		return "atualizar";
	}

	public String remover(Long id) {
		this.pessoa = this.dao.remove(id);
		if (!this.pessoaLista.isEmpty() && this.pessoaLista != null) {
			this.pessoaLista.remove(this.pessoa);
		}
		return "listar";
	}

	public String atualizar(Pessoa pessoa) {
		this.pessoa = pessoa.clone();
		this.pessoaAnterior = pessoa.clone();
		pessoa = new Pessoa();
		this.editado = true;
		return "atualizar";
	}

	public String salvarAtualizar() {
		this.dao.update(pessoa);
		// this.pessoa = new Pessoa();
		this.editado = false;
		this.pessoa = new Pessoa();
		return "Inicio";
	}

	public String cancelarAtualizar() {
		this.pessoa.restaurar(this.pessoaAnterior);
		this.pessoa = new Pessoa();
		editado = false;
		return "listar";
	}

	public String buscarTodos() {
		pessoaLista = this.dao.findAll();
		return "listar";
	}

	public String limparPessoa() {
		if (this.pessoa != null) {
			this.pessoa.setId(null);
			this.pessoa.setDataNasc(null);
			this.pessoa.setEmail("");
			this.pessoa.setEstadoCivil("");
			this.pessoa.setEtnia("");
			this.pessoa.setNacionalidade("");
			this.pessoa.setNaturalidade("");
			this.pessoa.setNecessidadesEspeciais("");
			this.pessoa.setNome("");
			this.pessoa.setNomeMae("");
			this.pessoa.setNomePai("");
			this.pessoa.setNumeroCelular("");
			this.pessoa.setResponsavelLegal("");
			this.pessoa.setSexo("");
			this.pessoa.setUf("");
			this.pessoa.setDocumentos(null);
			this.pessoa.setEnderecos(null);
			this.pessoa.setPlasticos(null);
			this.pessoa.setTipoPessoa("");
		}
		return "Inicio";
	}

	public boolean isEditado() {
		return editado;
	}

	public void setEditado(boolean editado) {
		this.editado = editado;
	}

	public Pessoa getPessoaAnterior() {
		return pessoaAnterior;
	}

	public void setPessoaAnterior(Pessoa pessoaAnterior) {
		this.pessoaAnterior = pessoaAnterior;
	}

	public PessoaDAO getDao() {
		return dao;
	}

	public void setDao(PessoaDAO dao) {
		this.dao = dao;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public List<Pessoa> getPessoas() {
		return pessoaLista;
	}

	public void setPessoas(List<Pessoa> pessoas) {
		this.pessoaLista = pessoas;
	}

	public String getMsgErro() {
		return msg;
	}

	public void setMsg(String mensagemErro) {
		this.msg = mensagemErro;
	}

	public void info(String message) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", message));
	}

	public void warn(String message) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_WARN, "Warning!", message));
	}

	public void error(String message) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", message));
	}

	public void fatal(String message) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_FATAL, "Fatal!", message));
	}

	public java.lang.String getMsg() {
		return msg;
	}

	public List<Pessoa> getPessoaLista() {
		return pessoaLista;
	}

	public void setPessoaLista(List<Pessoa> pessoaLista) {
		this.pessoaLista = pessoaLista;
	}
}
