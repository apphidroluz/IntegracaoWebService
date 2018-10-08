package entity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Importacao {

	static SimpleDateFormat SDF = new SimpleDateFormat("dd/MM/yyyy");

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private Integer id_IMP;
	
	@ManyToOne
	@JoinColumn(name = "cod_cad01")
	private Clientes cliente;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date data_imp;
	
	@OneToMany(mappedBy = "importacao", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Facturation> lista_fact;
	
	public Importacao() {
	}

	public Importacao(Integer id_IMP, Clientes cliente, Date data_imp, List<Facturation> lista_fact) {
		super();
		this.id_IMP = id_IMP;
		this.cliente = cliente;
		this.data_imp = data_imp;
		this.lista_fact = lista_fact;
	}

	@Override
	public String toString() {
		return "Importacao [id_IMP=" + id_IMP + ", data_imp=" + data_imp + "]";
	}

	public Integer getId_IMP() {
		return id_IMP;
	}

	public void setId_IMP(Integer id_IMP) {
		this.id_IMP = id_IMP;
	}

	public Clientes getCliente() {
		return cliente;
	}

	public void setCliente(Clientes cliente) {
		this.cliente = cliente;
	}

	public Date getData_imp() {
		return data_imp;
	}

	public void setData_imp(Date data_imp) {
		this.data_imp = data_imp;
	}

	public List<Facturation> getLista_fact() {
		return lista_fact;
	}

	public void setLista_fact(List<Facturation> lista_fact) {
		this.lista_fact = lista_fact;
	}
	
	public void adicionar(Facturation fact) {
		if(lista_fact == null) {
			lista_fact = new ArrayList<Facturation>();
		}
		lista_fact.add(fact);
	}
	
	
	
}
