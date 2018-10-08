package entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Hidrometro {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private Integer id_Hidro;
	
	@Column(unique = true)
	private String num_hidro;
	
	@Column(length = 18)
	private String local;
	
	@ManyToOne
	@JoinColumn(name = "cod_cad01")
	private Clientes cliente;
	
	public Hidrometro() {
	}

	public Hidrometro(Integer id_Hidro, String num_hidro, String local, Clientes cliente) {
		super();
		this.id_Hidro = id_Hidro;
		this.num_hidro = num_hidro;
		this.local = local;
		this.cliente = cliente;
	}

	@Override
	public String toString() {
		return "Hidrometro [id_Hidro=" + id_Hidro + ", num_hidro=" + num_hidro + ", local=" + local + ", cliente="
				+ cliente + "]";
	}

	public Integer getId_Hidro() {
		return id_Hidro;
	}

	public void setId_Hidro(Integer id_Hidro) {
		this.id_Hidro = id_Hidro;
	}

	public String getNum_hidro() {
		return num_hidro;
	}

	public void setNum_hidro(String num_hidro) {
		this.num_hidro = num_hidro;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public Clientes getCliente() {
		return cliente;
	}

	public void setCliente(Clientes cliente) {
		this.cliente = cliente;
	}

}
