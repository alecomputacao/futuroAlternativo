package app.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the documento database table.
 *
 */
@Entity
@Table(name = "documento", schema = "futurodb")
@NamedQueries({
	@NamedQuery(name = "Documento.findAll", query = "SELECT d FROM Documento d"),
	@NamedQuery(name = "Documento.findByPersonId", query = "SELECT d FROM Documento d WHERE d.pessoa.id = :personId")
})
public class Documento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Temporal(TemporalType.DATE)
	private Date dataExpedicao;

	@Temporal(TemporalType.DATE)
	private Date dataValidade;

	private String numero;

	private String orgaoEmissor;

	private String tipo;

	private String uf;

	//bi-directional many-to-one association to Pessoa
	@ManyToOne
	private Pessoa pessoa;

	public Documento() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDataExpedicao() {
		return this.dataExpedicao;
	}

	public void setDataExpedicao(Date dataExpedicao) {
		this.dataExpedicao = dataExpedicao;
	}

	public Date getDataValidade() {
		return this.dataValidade;
	}

	public void setDataValidade(Date dataValidade) {
		this.dataValidade = dataValidade;
	}

	public String getNumero() {
		return this.numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getOrgaoEmissor() {
		return this.orgaoEmissor;
	}

	public void setOrgaoEmissor(String orgaoEmissor) {
		this.orgaoEmissor = orgaoEmissor;
	}

	public String getTipo() {
		return this.tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getUf() {
		return this.uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public Pessoa getPessoa() {
		return this.pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	@Override
	public String toString() {
		return "Documento [id=" + id + ", numero=" + numero + ", orgaoEmissor=" + orgaoEmissor + ", dataEmissao="
				+ dataExpedicao + ", dataValidade=" + dataValidade + ", tipo=" + tipo + "]";
	}

	@Override
	public Documento clone() {
		Documento docClone = new Documento();
		docClone.setId(this.id);
		docClone.setNumero(this.numero);
		docClone.setDataExpedicao(this.dataExpedicao);
		docClone.setDataValidade(this.dataValidade);
		docClone.setOrgaoEmissor(this.orgaoEmissor);
		docClone.setTipo(this.tipo);
		docClone.setUf(this.uf);
		docClone.setPessoa(this.pessoa);

		return docClone;
	}

	public void restaurar(Documento documento) {
		this.id = documento.getId();
		this.dataExpedicao = documento.getDataExpedicao();
		this.dataValidade = documento.getDataValidade();
		this.uf = documento.getUf();
		this.numero = documento.getNumero();
		this.tipo = documento.getTipo();
		this.orgaoEmissor = documento.getOrgaoEmissor();

	}


}