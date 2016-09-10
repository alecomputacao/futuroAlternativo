package app.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the plastico database table.
 *
 */
@Entity
@Table(name = "plastico", schema = "futurodb")
@NamedQueries({ @NamedQuery(name = "Plastico.findAll", query = "SELECT p FROM Plastico p"),
		@NamedQuery(name = "Plastico.findByPersonId", query = "SELECT p FROM Plastico p WHERE p.pessoa.id = :wantedId"),
		@NamedQuery(name = "Plastico.findByDigitableLine", query = "SELECT p FROM Plastico p WHERE p.linhaDigitavel = :digitableLine")})
public class Plastico implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Temporal(TemporalType.DATE)
	private Date dataCadastro;

	private String linhaDigitavel;

	private String status;

	//bi-directional many-to-one association to Pessoa
	@ManyToOne
	private Pessoa pessoa;

	public Plastico() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDataCadastro() {
		return this.dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public String getLinhaDigitavel() {
		return this.linhaDigitavel;
	}

	public void setLinhaDigitavel(String linhaDigitavel) {
		this.linhaDigitavel = linhaDigitavel;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Pessoa getPessoa() {
		return this.pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

}