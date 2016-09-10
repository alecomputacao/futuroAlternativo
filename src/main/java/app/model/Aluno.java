package app.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the aluno database table.
 *
 */
@Entity
@Table(name = "aluno", schema = "futurodb")
@NamedQueries({ @NamedQuery(name = "Aluno.findAll", query = "SELECT a FROM Aluno a"),
		@NamedQuery(name = "Aluno.findLastRegistrationNumber", query = "SELECT MAX(a.matricula) as last FROM Aluno a"),
		@NamedQuery(name = "Aluno.findByRegistrationNumber", query = "SELECT a FROM Aluno a WHERE a.matricula LIKE :wantedNumber"),
		@NamedQuery(name = "Aluno.findByStatus", query = "SELECT a FROM Aluno a WHERE a.status LIKE :wantedStatus"),
		@NamedQuery(name = "Aluno.findByTurmaId", query = "SELECT a FROM Aluno a, Turma t WHERE t.id = :turmaId AND a MEMBER OF t.alunos AND a.status LIKE 'ATIVO'") })
public class Aluno implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(insertable = false)
	private Long id;

	@Temporal(TemporalType.DATE)
	private Date dataEgresso;

	@Temporal(TemporalType.DATE)
	private Date dataIngresso;

	private String matricula;

	private String status;

	private int tipoCotaIngresso;

	@OneToOne(cascade = CascadeType.ALL)
	private Pessoa pessoa;

	// bi-directional many-to-one association to Turma
	@ManyToOne
	private Turma turma;

	public Aluno() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDataEgresso() {
		return this.dataEgresso;
	}

	public void setDataEgresso(Date dataEgresso) {
		this.dataEgresso = dataEgresso;
	}

	public Date getDataIngresso() {
		return this.dataIngresso;
	}

	public void setDataIngresso(Date dataIngresso) {
		this.dataIngresso = dataIngresso;
	}

	public String getMatricula() {
		return this.matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getTipoCotaIngresso() {
		return this.tipoCotaIngresso;
	}

	public void setTipoCotaIngresso(int tipoCotaIngresso) {
		this.tipoCotaIngresso = tipoCotaIngresso;
	}

	public Pessoa getPessoa() {
		return this.pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public Turma getTurma() {
		return this.turma;
	}

	public void setTurma(Turma turma) {
		this.turma = turma;
	}

	@Override
	public Aluno clone() {
		Aluno alunoClone = new Aluno();
		alunoClone.setId(id);
		alunoClone.setDataEgresso(dataEgresso);
		alunoClone.setDataIngresso(dataIngresso);
		alunoClone.setMatricula(matricula);
		alunoClone.setTipoCotaIngresso(tipoCotaIngresso);
		alunoClone.setTurma(turma);
		alunoClone.setPessoa(pessoa);
		alunoClone.setStatus(status);

		return alunoClone;

	}

	public void restaurar(Aluno aluno) {
		this.id = aluno.getId();
		this.dataEgresso = aluno.getDataEgresso();
		this.dataIngresso = aluno.getDataIngresso();
		this.matricula = aluno.getMatricula();
		this.tipoCotaIngresso = aluno.getTipoCotaIngresso();
		this.turma = aluno.getTurma();
		this.pessoa = aluno.getPessoa();
		this.status = aluno.getStatus();
	}

	@Override
	public String toString() {
		return "Aluno[id=" + id + ", dataIgresso=" + dataIngresso + ", dataEgresso=" + dataEgresso + ", matricula="
				+ matricula + ", tipoCotaIngresso=" + tipoCotaIngresso + ", status=" + status + "]";
	}

}