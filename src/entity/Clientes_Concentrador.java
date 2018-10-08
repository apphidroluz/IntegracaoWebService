package entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Clientes_Concentrador {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private Integer id_PK;
	
	@OneToOne
	@JoinColumn(name = "cod_cad01")
	private Clientes cliente;

	@Column(length = 150)
	private String login;

	@Column(length = 200)
	private String senha;

	
	public Clientes getCliente() {
		return cliente;
	}

	public void setCliente(Clientes cliente) {
		this.cliente = cliente;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}


	public Integer getId_PK() {
		return id_PK;
	}

	public void setId_PK(Integer id_PK) {
		this.id_PK = id_PK;
	}

	public Clientes_Concentrador() {
		super();
	}

	public Clientes_Concentrador(Integer id_PK, Clientes cliente, String login, String senha) {
		super();
		this.id_PK = id_PK;
		this.cliente = cliente;
		this.login = login;
		this.senha = senha;
	}

	@Override
	public String toString() {
		return "Clientes_Concentrador [cliente=" + cliente + ", login=" + login + ", senha=" + senha + "]";
	}
	
	
	


}
