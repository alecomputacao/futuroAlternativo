package app.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the endereco database table.
 *
 */
@Entity
@Table(name = "endereco", schema = "futurodb")
@NamedQueries({
	@NamedQuery(name = "Endereco.findAll", query = "SELECT e FROM Endereco e"),
	@NamedQuery(name = "Endereco.findByPersonId", query = "SELECT e FROM Endereco e WHERE e.pessoa.id = :personId")
})
public class Endereco implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	private String bairro;

	private String cep;

	private String cidade;

	private String enderecoCorrespondencia;

	private String logradouro;

	private int numero;

	private String telefone;

	private String tipo;

	private String uf;

	//bi-directional many-to-one association to Pessoa
	@ManyToOne
	private Pessoa pessoa;

	public Endereco() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBairro() {
		return this.bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCep() {
		return this.cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getCidade() {
		return this.cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getEnderecoCorrespondencia() {
		return this.enderecoCorrespondencia;
	}

	public void setEnderecoCorrespondencia(String enderecoCorrespondencia) {
		this.enderecoCorrespondencia = enderecoCorrespondencia;
	}

	public String getLogradouro() {
		return this.logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public int getNumero() {
		return this.numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public String getTelefone() {
		return this.telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
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

	/**
	 *
	 * @return
	 */
	@Override
	public String toString() {
		return new String("Logradouro: " + this.logradouro + "\nNumero: " + this.numero + "\nBairro: " + this.bairro
				+ "\nCidade: " + this.cidade + "\nCEP: " + this.cep + "\nUF: " + this.uf
				+ "\n Endereço de Correspondência: " + this.enderecoCorrespondencia + "\nTelefone:" + this.telefone
				+ "\nTipo de endereço: " + this.tipo);
	}

	/**
	 *
	 * @return
	 */
	@Override
	public Endereco clone() {
		 Endereco endClone = new Endereco();
		 endClone.setId(id);
		 endClone.setBairro(bairro);
		 endClone.setCep(cep);
		 endClone.setCidade(cidade);
		 endClone.setEnderecoCorrespondencia(enderecoCorrespondencia);
		 endClone.setLogradouro(logradouro);
		 endClone.setNumero(numero);
		 endClone.setTelefone(telefone);
		 endClone.setTipo(tipo);
		 endClone.setUf(uf);
		 endClone.setPessoa(pessoa);

		 return endClone;
	}


	public void restaurar(Endereco endereco) {
		this.id = endereco.getId();
		this.cep = endereco.getCep();
		this.cidade = endereco.getCidade();
		this.bairro = endereco.getBairro();
		this.enderecoCorrespondencia = endereco.getEnderecoCorrespondencia();
		this.numero = endereco.getNumero();
		this.tipo = endereco.getTipo();
		this.pessoa = endereco.getPessoa();
		this.uf = endereco.getUf();
	}

}