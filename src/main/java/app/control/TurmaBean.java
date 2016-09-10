package app.control;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import app.dao.TurmaDAO;
import app.model.Aluno;
import app.model.Turma;
import app.util.ListUtil;
import app.util.Status;

@ManagedBean(name = "turmaBean")
@SessionScoped
public class TurmaBean implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private TurmaDAO dao;
	private Turma turma;
	private Turma turmaAnterior;
	private List<Turma> turmas;
	private List<Aluno> alunos;

	private Logger LOGGER = Logger.getLogger(TurmaBean.class);

	@PostConstruct
	public void init() {
		LOGGER.info("recarregando bean Turma");
		this.dao = new TurmaDAO(Turma.class);
		this.turma = new Turma();
		this.turmaAnterior = new Turma();
		this.turmas = new ArrayList<Turma>();
		this.alunos = new ArrayList<Aluno>();
	}

	public String salvar() {
		try {
			LOGGER.info("Verificando existencia da nova turma...");
			this.turmas = dao.findByStatus("ATIVO");
			if (this.turma.exists(this.turmas)) {
				LOGGER.warn("Ja existe uma turma ativa com o nome solicitado: " + this.turma.getCodigo());
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
						" Atencao!", "Ja existe uma turma ativa com o nome solicitado: " + this.turma.getCodigo()));
				return "salvarTurma?faces-redirect=true";
			} else {
				LOGGER.info("Salvando turma com status ativo");
				this.turma.setStatus("ATIVO");
				this.dao.save(this.turma);
				LOGGER.info("Turma " + this.turma.getCodigo() + " criada com sucesso");
				info("Turma " + this.turma.getCodigo() + " criada com sucesso");
				init();
			}
		} catch (Exception e) {
			LOGGER.error("Erro ao salvar turma", e);
			error("Erro ao salvar turma: " + e.getMessage());
		}
		return "salvarTurma?faces-redirect=true";
	}

	public String buscarPorId(Long id) {
		LOGGER.info("Buscando id: " + id);
		try {
			init();
			this.turma = this.dao.findById(id);
			if (this.turma != null) {
				this.turmas.add(this.turma);
				LOGGER.info("Turma " + id + " encontrada");
				info("Turma " + id + " encontrada");
			} else {
				LOGGER.info("Turma " + id + " nao encontrada");
				warn("Turma " + id + " nao encontrada");
			}
		} catch (Exception e) {
			LOGGER.error("Erro ao buscar turma " + id, e);
			error("Erro ao buscar turma " + id + " " + e.getMessage());
		}
		return "listarTurma?faces-redirect=true";
	}

	public String remover(Turma turma) {
		LOGGER.info("Removendo id: " + turma.getId());
		try {
			if ((turma.getAlunos() != null) && !turma.getAlunos().isEmpty()) {
				LOGGER.info("Existem alunos cadastrados na Turma " + turma.getId() + " e ela nao podera ser removida");
				error("Existem alunos cadastrados na Turma " + turma.getId() + " e ela nao podera ser removida");
			} else {
				this.dao.remove(turma.getId());
				this.turmas.remove(turma);
				LOGGER.info("Turma " + turma.getId() + " removida");
				info("Turma " + turma.getId() + " removida");
			}
		} catch (Exception e) {
			LOGGER.error("Erro ao remover turma " + turma.getId(), e);
			error("Erro ao remover turma " + turma.getId() + " " + e.getMessage());
		}
		return "listarTurma?faces-redirect=true";
	}

	/**
	 *
	 * @param turma
	 *            - the student in
	 * @return the action to go to save ou list student
	 */
	public String atualizar(Turma turma) {
		try {
			LOGGER.info("Iniciando atualizacao do turma: " + turma.getCodigo());
			this.turma = turma.clone();
			LOGGER.info("Realizando clone");
			this.turmaAnterior = turma;
			LOGGER.info("Pronto para atualizar");
			return "atualizarAluno?faces-redirect=true";
		} catch (Exception e) {
			error("Erro ao direcioar para atualizacao de dados do turma");
			return "listarAluno?faces-redirect=true";
		}
	}

	/**
	 *
	 * @return the action to go to save ou list student
	 */
	public String salvarAtualizar() {
		try {
			LOGGER.info("Atualizando turma");

			this.dao.update(this.turma);
			this.turmas.remove(this.turmaAnterior);
			this.turmas.add(this.turma);

			info("Dados de " + this.turma.getCodigo() + " atualizados");
			LOGGER.info("Dados de " + this.turma.getCodigo() + " atualizados");
			init();
			return "listarTurma?faces-redirect=true";
		} catch (Exception e) {
			LOGGER.error("Erro ao atualizar as informacoes", e);
			error("Erro ao atualizar as informacoes: " + e.getMessage());
			return "listarTurma?faces-redirect=true";
		}
	}

	public String cancelarAtualizar() {
		this.turma.restaurar(this.turmaAnterior);
		this.turmaAnterior = new Turma();
		return "listarAluno?faces-redirect=true";
	}

	public String buscarTodos() {
		init();
		LOGGER.info("Buscando turmas...");
		this.turmas = this.dao.findAll();
		if (this.turmas.isEmpty() || this.turmas == null) {
			LOGGER.info("Nenhuma turma encontrada");
		} else {
			LOGGER.info("Lista de turmas");
		}
		return "listarTurma?faces-redirect=true";
	}

	public String cancelarSalvar() {
		init();
		return "Inicio?faces-redirect=true";
	}

	public String buscarPorStatus(String status) {

		try {
			this.turmas = dao.findByStatus(status);
			if (this.turmas == null || this.turmas.isEmpty()) {
				LOGGER.warn("Não existem turmas ativas");
				warn("Não existem turmas ativas");
			} else {
				LOGGER.warn("Foram encontradas " + turmas.size() + " turmas ativas");
			}
		} catch (Exception e) {
			LOGGER.error("Erro ao consultar ativas", e);
			error("Erro ao consultar ativas.");
		}
		return "listarTurmas?faces-redirect=true";
	}

	public void buscarPorCodigo(String codigoTurma) {
		turmas = dao.findByCode(codigoTurma);
		if (ListUtil.isValid(turmas)) {
			for (Turma t : turmas) {
				if (t.getStatus().equals(Status.ATIVO.toString())) {
					turma = t;
					break;
				}
			}
		} else {
			turma = null;
		}
	}

	public TurmaDAO getDao() {
		return dao;
	}

	public void setDao(TurmaDAO dao) {
		this.dao = dao;
	}

	public Turma getTurma() {
		return turma;
	}

	public void setTurma(Turma turma) {
		this.turma = turma;
	}

	public List<Turma> getTurmas() {
		return turmas;
	}

	public void setTurmas(List<Turma> turmas) {
		this.turmas = turmas;
	}

	public List<Aluno> getAlunos() {
		return alunos;
	}

	public void setAlunos(List<Aluno> alunos) {
		this.alunos = alunos;
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
				new FacesMessage(FacesMessage.SEVERITY_WARN, " Atencao!", message));
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
