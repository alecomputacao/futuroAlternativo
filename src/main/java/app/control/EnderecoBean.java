package app.control;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import app.dao.EnderecoDAO;
import app.model.Endereco;
import app.model.Pessoa;

@ManagedBean(name="enderecoBean")
@SessionScoped
public class EnderecoBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private EnderecoDAO dao;
	private Endereco endereco;
	private List<Endereco> enderecos;
	private Pessoa pessoa;
	private Endereco enderecoAnterior;
	private boolean editado;
	private String tipoPessoa;

	@PostConstruct
	public void init() {
		if (dao == null) {
			dao = new EnderecoDAO(Endereco.class);
		}
		this.endereco = new Endereco();
		this.enderecos = new ArrayList<Endereco>();
		this.pessoa = new Pessoa();
		this.editado = false;
		this.enderecoAnterior = new Endereco();
	}

	/**
	 *
	 * @param id
	 * @return
	 */
	public String buscarPorId(Long id) {
		this.endereco = this.dao.findById(id);
		if (this.endereco != null) {
			info("Sucesso");
		} else {
			warn("Documetno n�o encotnrado");
		}
		return "listarEndereco?faces-redirect=true";
	}

	/**
	 *
	 * @param id
	 * @return
	 */
	public String remover(Long id) {
		if (this.enderecos.size() == 1) {
			error(pessoa.getTipoPessoa() + " precisa possuir pelo menos um endereco. Opera��o n�o permitida.");
		} else {
			Endereco doc = this.dao.remove(id);
			if (!doc.equals(null)) {
				this.enderecos.remove(doc);
				info(doc.getTipo() + " removido com suecsso.");
			} else {
				warn("endereco n�o encontrado na base de dados, favor verificar novamente na lista.");
			}
		}

		return "listarEndereco?faces-redirect=true";
	}

	/**
	 *
	 * @param endereco
	 * @return
	 */
	public String atualizar(Endereco endereco) {
		try {
			this.endereco = endereco.clone();
			this.enderecoAnterior = endereco;
			this.editado = true;
			return "atualizarEndereco?faces-redirect=true";
		} catch (Exception e) {
			error("Erro ao direcioar para atualiza��o de dados do endereco");
			return "listarEndereco?faces-redirect=true";
		}
	}

	/**
	 *
	 * @return
	 */
	public String salvarAtualizar() {
		try {
			this.dao.update(this.endereco);
			this.enderecos.remove(enderecoAnterior);
			this.enderecos.add(this.endereco);
			this.editado = false;
			info("Endereco " + this.endereco.getTipo() + " atualizados");
			this.endereco = new Endereco();
			this.enderecoAnterior = new Endereco();
			return "listarEndereco?faces-redirect=true";
		} catch (Exception e) {
			error("Erro ao atualizar as informa��es!");
			return "atualizarEndereco?faces-redirect=true";
		}
	}

	public List<Endereco> buscarTodos() {
		return this.dao.findAll();
	}

	/**
	 *
	 * @param pessoa
	 * @return
	 */
	public String atualizarEnderecos(Pessoa pessoa) {
		this.pessoa = pessoa;
		this.enderecos = pessoa.getEnderecos();
		this.setTipoPessoa(pessoa.getTipoPessoa());
		if (this.enderecos.isEmpty() || this.enderecos == null) {
			warn("Nenhum endereco encontrado para " + pessoa.getNome());
		}
		return "listarEndereco?faces-redirect=true";
	}

	/**
	 *
	 * @return
	 */

	public String cancelarAtualizar() {
		this.endereco.restaurar(this.enderecoAnterior);
		this.enderecoAnterior = new Endereco();
		setEditado(false);
		return "listarEndereco?faces-redirect=true";
	}

	public EnderecoDAO getDao() {
		return dao;
	}

	public void setDao(EnderecoDAO dao) {
		this.dao = dao;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public List<Endereco> getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(List<Endereco> enderecos) {
		this.enderecos = enderecos;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public boolean isEditado() {
		return editado;
	}

	public void setEditado(boolean editado) {
		this.editado = editado;
	}

	public String getTipoPessoa(){
		return this.tipoPessoa;
	}

	public void setTipoPessoa(String tipoPessoa) {
		this.tipoPessoa = tipoPessoa;
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
