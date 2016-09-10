package app.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the evento database table.
 *
 */
@Entity
@Table(name="evento", schema="futurodb")
@NamedQueries({
@NamedQuery(name="Evento.findAll", query="SELECT e FROM Evento e"),
@NamedQuery(name="Evento.findEventsByPersonIdAndStatus", query="SELECT e FROM Evento e WHERE e.pessoa.id = :personId AND e.status = :status"),
@NamedQuery(name="Evento.findEventsByPersonIdAndDate", query="SELECT e FROM Evento e WHERE e.pessoa.id = :personId AND date_format(e.dataHoraEntrada, '%d/%m/%Y') = date_format(:date, '%d/%m/%Y')")})

public class Evento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dataHoraEntrada;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dataHoraSaida;

	private String status;

	//bi-directional many-to-one association to Pessoa
	@ManyToOne
	private Pessoa pessoa;

	public Evento() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDataHoraEntrada() {
		return this.dataHoraEntrada;
	}

	public void setDataHoraEntrada(Date dataHoraEntrada) {
		this.dataHoraEntrada = dataHoraEntrada;
	}

	public Date getDataHoraSaida() {
		return this.dataHoraSaida;
	}

	public void setDataHoraSaida(Date dataHoraSaida) {
		this.dataHoraSaida = dataHoraSaida;
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