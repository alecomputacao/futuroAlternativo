package app.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the disciplina database table.
 *
 */
@Entity
@Table(name="disciplina", schema="futurodb")
@NamedQuery(name="Disciplina.findAll", query="SELECT d FROM Disciplina d")
public class Disciplina implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	private String cargaHoraria;

	private String nome;

	@Column(name="Turma_id")
	private int turmaId;

	@ManyToMany
	@JoinTable(
		name="professor_has_disciplina"
		, joinColumns={
			@JoinColumn(name="Disciplina_id")
			}
		, inverseJoinColumns={
			@JoinColumn(name="Professor_id")
			}
		)
	private List<Professor> professors;

	public Disciplina() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCargaHoraria() {
		return this.cargaHoraria;
	}

	public void setCargaHoraria(String cargaHoraria) {
		this.cargaHoraria = cargaHoraria;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getTurmaId() {
		return this.turmaId;
	}

	public void setTurmaId(int turmaId) {
		this.turmaId = turmaId;
	}

	public List<Professor> getProfessors() {
		return this.professors;
	}

	public void setProfessors(List<Professor> professors) {
		this.professors = professors;
	}

}