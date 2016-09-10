package app.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the professor database table.
 *
 */
@Entity
@Table(name="professor", schema="futurodb")
@NamedQueries({
@NamedQuery(name="Professor.findAll", query="SELECT p FROM Professor p"),
@NamedQuery(name="Professor.findByName", query="SELECT p FROM Professor p WHERE p.pessoa.nome LIKE :nome")})
public class Professor implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	private String formacao;

	private String nivelFormacao;

	//bi-directional many-to-many association to Disciplina
	@ManyToMany
	@JoinTable(
		name="professor_has_disciplina"
		, joinColumns={
			@JoinColumn(name="Professor_id")
			}
		, inverseJoinColumns={
			@JoinColumn(name="Disciplina_id")
			}
		)
	private List<Disciplina> disciplinas;

	//bi-directional many-to-one association to Pessoa
	@OneToOne
	private Pessoa pessoa;

	public Professor() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFormacao() {
		return this.formacao;
	}

	public void setFormacao(String formacao) {
		this.formacao = formacao;
	}

	public String getNivelFormacao() {
		return this.nivelFormacao;
	}

	public void setNivelFormacao(String nivelFormacao) {
		this.nivelFormacao = nivelFormacao;
	}

	public List<Disciplina> getDisciplinas() {
		return this.disciplinas;
	}

	public void setDisciplinas(List<Disciplina> disciplinas) {
		this.disciplinas = disciplinas;
	}

	public Pessoa getPessoa() {
		return this.pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public void restaurar(Professor professor) {
		this.id = professor.getId();
		this.disciplinas = professor.getDisciplinas();
		this.formacao = professor.getFormacao();
		this.nivelFormacao = professor.getNivelFormacao();
		this.pessoa = professor.getPessoa();
		//this.turmas = professor.getTurmas();

	}

	@Override
	public Professor clone(){
		Professor profClone = new Professor();
		profClone.setId(id);
		profClone.setNivelFormacao(nivelFormacao);
		profClone.setPessoa(pessoa);
		profClone.setDisciplinas(disciplinas);

		return profClone;
	}
}