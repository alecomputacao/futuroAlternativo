package app.control;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import app.dao.AlunoDao;
import app.model.Aluno;
import app.model.Pessoa;

@ManagedBean
@SessionScoped
public class AlunoBean {

	private AlunoDao dao;
	private Aluno aluno;
	private Pessoa pessoa;
	private Pessoa pessoaAnterior = null;
	private Aluno alunoAnterior = null;
	private List<Aluno> alunoLista;
	private boolean editado;
	private String mensagemErro = null;

	@PostConstruct
	public void init() {
		if (this.dao == null) {
			this.dao = new AlunoDao(Aluno.class);
		}
		this.dao = new AlunoDao(Aluno.class);
		this.alunoLista = new ArrayList<Aluno>();
		this.aluno = new Aluno();
		this.pessoa = new Pessoa();
	}

	public void salvar(Aluno aluno) {
		this.dao.save(aluno);
		this.alunoLista.add(aluno);
		aluno = new Aluno();

	}

	public void buscarPorId(Long id) {
		this.aluno = this.dao.findById(id);
	}

	public void remover(Long id) {
		this.aluno = this.dao.remove(id);
	}

	public void atualizar(Aluno aluno) {
		this.setAlunoAnterior(aluno.clone());
		this.aluno = aluno;
		this.editado = true;
	}

	public void salvarAtualizar() {
		this.dao.update(aluno);
		this.aluno = new Aluno();
		this.editado = false;
	}

	public void cancelarAtualizar() {
		this.aluno.restaurar(this.alunoAnterior);
		this.aluno = new Aluno();
		editado = false;
	}

	public void buscarTodos() {
		this.alunoLista = this.dao.findAll();
	}

	public void limparAluno() {
		this.aluno.setDataegresso(null);
		this.aluno.setDataingresso(null);
		this.aluno.setMatricula(null);
		this.aluno.setPessoa(null);
		this.aluno.setTipocotaingresso(-1);
		this.aluno.setTurma(null);
		this.aluno.setId(null);
	}

	public boolean isEditado() {
		return editado;
	}

	public void setEditado(boolean editado) {
		this.editado = editado;
	}

	public Aluno getAlunoAnterior() {
		return alunoAnterior;
	}

	public void setAlunoAnterior(Aluno alunoAnterior) {
		this.alunoAnterior = alunoAnterior;
	}

	public AlunoDao getDao() {
		return dao;
	}

	public void setDao(AlunoDao dao) {
		this.dao = dao;
	}

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public List<Aluno> getAlunos() {
		return alunoLista;
	}

	public void setAlunos(List<Aluno> alunos) {
		this.alunoLista = alunos;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public Pessoa getPessoaAnterior() {
		return pessoaAnterior;
	}

	public void setPessoaAnterior(Pessoa pessoaAnterior) {
		this.pessoaAnterior = pessoaAnterior;
	}

	public String getMensagemErro() {
		return mensagemErro;
	}

	public void setMensagemErro(String mensagemErro) {
		this.mensagemErro = mensagemErro;
	}

}