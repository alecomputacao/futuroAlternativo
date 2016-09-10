package app.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the usuario database table.
 *
 */
@Entity
@Table(name="usuario", schema="futurodb")
@NamedQueries({@NamedQuery(name="Usuario.findByName", query="SELECT u from Usuario u where u.nomeUsuario = :name"),
@NamedQuery(name="Usuario.findAll", query="SELECT u FROM Usuario u")})
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Column(name="nomeusuario", nullable=false, unique=true)
	private String nomeUsuario;

	@Column(name="senha", nullable=false, unique=false)
	private String senha;

	public Usuario() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomeUsuario() {
		return this.nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}

	public String getSenha() {
		return this.senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

}