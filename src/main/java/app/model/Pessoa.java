package app.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the pessoa database table.
 *
 */
@Entity
@NamedQuery(name = "Pessoa.findAll", query = "SELECT p FROM Pessoa p")
public class Pessoa implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Temporal(TemporalType.DATE)
	private Date dataNasc;

	private String email;

	private String estadoCivil;

	private String etnia;

	private String nacionalidade;

	private String naturalidade;

	private String necessidadesEspeciais;

	private String nome;

	private String nomeMae;

	private String nomePai;

	private String numeroCelular;

	private String responsavelLegal;

	private String sexo;

	private String tipoPessoa;

	private String uf;

	// bi-directional many-to-one association to Documento
	@OneToMany(mappedBy = "pessoa", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Documento> documentos;

	// bi-directional many-to-one association to Endereco
	@OneToMany(mappedBy = "pessoa", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Endereco> enderecos;

	// bi-directional many-to-one association to Plastico
	@OneToMany(mappedBy = "pessoa", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Plastico> plasticos;

	public Pessoa() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDataNasc() {
		return this.dataNasc;
	}

	public void setDataNasc(Date dataNasc) {
		this.dataNasc = dataNasc;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEstadoCivil() {
		return this.estadoCivil;
	}

	public void setEstadoCivil(String estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	public String getEtnia() {
		return this.etnia;
	}

	public void setEtnia(String etnia) {
		this.etnia = etnia;
	}

	public String getNacionalidade() {
		return this.nacionalidade;
	}

	public void setNacionalidade(String nacionalidade) {
		this.nacionalidade = nacionalidade;
	}

	public String getNaturalidade() {
		return this.naturalidade;
	}

	public void setNaturalidade(String naturalidade) {
		this.naturalidade = naturalidade;
	}

	public String getNecessidadesEspeciais() {
		return this.necessidadesEspeciais;
	}

	public void setNecessidadesEspeciais(String necessidadesEspeciais) {
		this.necessidadesEspeciais = necessidadesEspeciais;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNomeMae() {
		return this.nomeMae;
	}

	public void setNomeMae(String nomeMae) {
		this.nomeMae = nomeMae;
	}

	public String getNomePai() {
		return this.nomePai;
	}

	public void setNomePai(String nomePai) {
		this.nomePai = nomePai;
	}

	public String getNumeroCelular() {
		return this.numeroCelular;
	}

	public void setNumeroCelular(String numeroCelular) {
		this.numeroCelular = numeroCelular;
	}

	public String getResponsavelLegal() {
		return this.responsavelLegal;
	}

	public void setResponsavelLegal(String responsavelLegal) {
		this.responsavelLegal = responsavelLegal;
	}

	public String getSexo() {
		return this.sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getTipoPessoa() {
		return this.tipoPessoa;
	}

	public void setTipoPessoa(String tipoPessoa) {
		this.tipoPessoa = tipoPessoa;
	}

	public String getUf() {
		return this.uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public List<Documento> getDocumentos() {
		return this.documentos;
	}

	public void setDocumentos(List<Documento> documentos) {
		this.documentos = documentos;
	}

	public Documento addDocumento(Documento documento) {
		if(documentos == null){
			documentos = new ArrayList<Documento>();
		}
		getDocumentos().add(documento);
		documento.setPessoa(this);

		return documento;
	}

	public Documento removeDocumento(Documento documento) {
		getDocumentos().remove(documento);
		documento.setPessoa(null);

		return documento;
	}

	public List<Endereco> getEnderecos() {
		return this.enderecos;
	}

	public void setEnderecos(List<Endereco> enderecos) {
		this.enderecos = enderecos;
	}

	public Endereco addEndereco(Endereco endereco) {
		if(enderecos == null){
			enderecos = new ArrayList<Endereco>();
		}
		getEnderecos().add(endereco);
		endereco.setPessoa(this);

		return endereco;
	}

	public Endereco removeEndereco(Endereco endereco) {
		getEnderecos().remove(endereco);
		endereco.setPessoa(null);

		return endereco;
	}

	public List<Plastico> getPlasticos() {
		return this.plasticos;
	}

	public void setPlasticos(List<Plastico> plasticos) {
		this.plasticos = plasticos;
	}

	public Plastico addPlastico(Plastico plastico) {
		if (getPlasticos() == null) {
			plasticos = new ArrayList<Plastico>();
		}
		getPlasticos().add(plastico);
		plastico.setPessoa(this);

		return plastico;
	}

	public Plastico removePlastico(Plastico plastico) {
		if ((getPlasticos() != null) && !getPlasticos().isEmpty()) {
			getPlasticos().remove(plastico);
			plastico.setPessoa(null);
		}
		return plastico;
	}

	@Override
	public Pessoa clone() {
		Pessoa pessClone = new Pessoa();
		pessClone.setId(id);
		pessClone.setDataNasc(dataNasc);
		pessClone.setEmail(email);
		pessClone.setEstadoCivil(estadoCivil);
		pessClone.setEtnia(etnia);
		pessClone.setNacionalidade(nacionalidade);
		pessClone.setNaturalidade(naturalidade);
		pessClone.setNecessidadesEspeciais(necessidadesEspeciais);
		pessClone.setNome(nome);
		pessClone.setNomeMae(nomeMae);
		pessClone.setNomePai(nomePai);
		pessClone.setNumeroCelular(numeroCelular);
		pessClone.setResponsavelLegal(responsavelLegal);
		pessClone.setSexo(sexo);
		pessClone.setUf(uf);
		pessClone.setDocumentos(documentos);
		pessClone.setEnderecos(enderecos);
		pessClone.setPlasticos(plasticos);
		pessClone.setTipoPessoa(tipoPessoa);

		return pessClone;
	}

	public void restaurar(Pessoa pessoa) {
		this.id = pessoa.getId();
		this.dataNasc = pessoa.getDataNasc();
		this.email = pessoa.getEmail();
		this.estadoCivil = pessoa.getEstadoCivil();
		this.etnia = pessoa.getEtnia();
		this.nacionalidade = pessoa.getNacionalidade();
		this.naturalidade = pessoa.getNaturalidade();
		this.necessidadesEspeciais = pessoa.getNecessidadesEspeciais();
		this.nome = pessoa.getNome();
		this.nomeMae = pessoa.getNomeMae();
		this.nomePai = pessoa.getNomePai();
		this.numeroCelular = pessoa.getNumeroCelular();
		this.responsavelLegal = pessoa.getResponsavelLegal();
		this.sexo = pessoa.getSexo();
		this.uf = pessoa.getUf();
		this.documentos = pessoa.getDocumentos();
		this.enderecos = pessoa.getEnderecos();
		this.plasticos = pessoa.getPlasticos();
		this.tipoPessoa = pessoa.getTipoPessoa();
	}

	public String toString() {
		return "Pessoa[id=" + id + ", dataNasc=" + dataNasc + ", email=" + email + ", estadoCivil=" + estadoCivil + ", etnia=" + etnia
				+ ", nacionalidade=" + nacionalidade + ", naturalidade=" + naturalidade + ", necessidadesEspeciais=" + necessidadesEspeciais
				+ ", nome=" + nome + ", nomeMae=" + nomeMae + ", nomePai=" + nomePai + ", numeroCelular=" + numeroCelular + ", responsavelLegal="
				+ responsavelLegal + ", sexo=" + sexo + ", uf=" + uf + ", tipopessoa=" + tipoPessoa + "]";
	}

}