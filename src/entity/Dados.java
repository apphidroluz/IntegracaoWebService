package entity;

public class Dados implements Comparable<Dados> {

	private Integer idXML_TAB;
	private String concentrador;
	private String numHidrometro;
	private String data;
	private String alarmes;
	private Integer indice_atual;
	private String unit;
	
	private String data_hist;
	private Integer indice;
	private Integer indice_antigo;
	private Double consumo;
	private String localizacao;
	private Integer codigo;
	private Integer haVazamento;
	private Integer houveVazamento;
	private Integer haDesmontagem;
	private Integer houveDesmontagem;
	private Integer medidorBloqueado;
	private Integer retornoAgua;

	public Dados() {
	}

	public Dados(Integer idXML_TAB, String concentrador, String numHidrometro, String data, Integer indice_atual, 
			String alarmes, String unit) {
		super();
		this.idXML_TAB = idXML_TAB;
		this.concentrador = concentrador;
		this.numHidrometro = numHidrometro;
		this.data = data;
		this.indice_atual = indice_atual;
		this.alarmes = alarmes;
		this.unit = unit;
	}
	
	@Override
	public String toString() {
		return "Dados [idXML_TAB=" + idXML_TAB + ", concentrador=" + concentrador + ", numHidrometro=" + numHidrometro
				+ ", data=" + data + ", alarmes=" + alarmes + ", indice_atual=" + indice_atual + ", unit=" + unit
				+ ", data_hist=" + data_hist + ", indice=" + indice + ", indice_antigo=" + indice_antigo + ", consumo="
				+ consumo + ", localizacao=" + localizacao + ", codigo=" + codigo + ", haVazamento=" + haVazamento
				+ ", houveVazamento=" + houveVazamento + ", haDesmontagem=" + haDesmontagem + ", houveDesmontagem="
				+ houveDesmontagem + ", medidorBloqueado=" + medidorBloqueado + ", retornoAgua=" + retornoAgua + "]";
	}
	
	public Integer getIdXML_TAB() {
		return idXML_TAB;
	}

	public void setIdXML_TAB(Integer idXML_TAB) {
		this.idXML_TAB = idXML_TAB;
	}

	public String getConcentrador() {
		return concentrador;
	}

	public void setConcentrador(String concentrador) {
		this.concentrador = concentrador;
	}

	public String getNumHidrometro() {
		return numHidrometro;
	}

	public void setNumHidrometro(String numHidrometro) {
		this.numHidrometro = numHidrometro;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getAlarmes() {
		return alarmes;
	}

	public void setAlarmes(String alarmes) {
		this.alarmes = alarmes;
	}

	public Integer getIndice_atual() {
		return indice_atual;
	}

	public void setIndice_atual(Integer indice_atual) {
		this.indice_atual = indice_atual;
	}

	public String getunit() {
		return unit;
	}

	public void setunit(String unit) {
		this.unit = unit;
	}

	public String getData_hist() {
		return data_hist;
	}

	public void setData_hist(String data_hist) {
		this.data_hist = data_hist;
	}

	public Integer getIndice() {
		return indice;
	}

	public void setIndice(Integer indice) {
		this.indice = indice;
	}

	public Integer getIndice_antigo() {
		return indice_antigo;
	}

	public void setIndice_antigo(Integer indice_antigo) {
		this.indice_antigo = indice_antigo;
	}

	public Double getConsumo() {
		return consumo;
	}

	public void setConsumo(Double consumo) {
		this.consumo = consumo;
	}

	public String getLocalizacao() {
		return localizacao;
	}

	public void setLocalizacao(String localizacao) {
		this.localizacao = localizacao;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public Integer getHaVazamento() {
		return haVazamento;
	}

	public void setHaVazamento(Integer haVazamento) {
		this.haVazamento = haVazamento;
	}

	public Integer getHouveVazamento() {
		return houveVazamento;
	}

	public void setHouveVazamento(Integer houveVazamento) {
		this.houveVazamento = houveVazamento;
	}

	public Integer getHaDesmontagem() {
		return haDesmontagem;
	}

	public void setHaDesmontagem(Integer haDesmontagem) {
		this.haDesmontagem = haDesmontagem;
	}

	public Integer getHouveDesmontagem() {
		return houveDesmontagem;
	}

	public void setHouveDesmontagem(Integer houveDesmontagem) {
		this.houveDesmontagem = houveDesmontagem;
	}

	public Integer getMedidorBloqueado() {
		return medidorBloqueado;
	}

	public void setMedidorBloqueado(Integer medidorBloqueado) {
		this.medidorBloqueado = medidorBloqueado;
	}

	public Integer getRetornoAgua() {
		return retornoAgua;
	}

	public void setRetornoAgua(Integer retornoAgua) {
		this.retornoAgua = retornoAgua;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idXML_TAB == null) ? 0 : idXML_TAB.hashCode());
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((numHidrometro == null) ? 0 : numHidrometro.hashCode());
		result = prime * result + ((concentrador == null) ? 0 : concentrador.hashCode());
		result = prime * result + ((indice_atual == null) ? 0 : indice_atual.hashCode());
		result = prime * result + ((unit == null) ? 0 : unit.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Dados other = (Dados) obj;
		if (idXML_TAB == null) {
			if (other.idXML_TAB != null)
				return false;
		} else if (!idXML_TAB.equals(other.idXML_TAB))
			return false;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (numHidrometro == null) {
			if (other.numHidrometro != null)
				return false;
		} else if (!numHidrometro.equals(other.numHidrometro))
			return false;
		if (concentrador == null) {
			if (other.concentrador != null)
				return false;
		} else if (!concentrador.equals(other.concentrador))
			return false;
		if (indice_atual == null) {
			if (other.indice_atual != null)
				return false;
		} else if (!indice_atual.equals(other.indice_atual))
			return false;
		if (unit == null) {
			if (other.unit != null)
				return false;
		} else if (!unit.equals(other.unit))
			return false;
		return true;
		
	}

	@Override
	public int compareTo(Dados o) {

		if (this.idXML_TAB > o.idXML_TAB) {
			return -1;
		} else if (this.idXML_TAB < o.idXML_TAB) {
			return 1;
		}

		// TODO Auto-generated method stub
		return 0;
	}

	

}
