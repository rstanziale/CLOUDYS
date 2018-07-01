package com.bdii.model;

public class Fattura 
{
	private String codice;
	private String dataemissione;
	private String importo;
	
	
	
	
	public Fattura(String codice, String dataemissione, String importo) {
		super();
		this.codice = codice;
		this.dataemissione = dataemissione;
		this.importo = importo;
	}




	public String getCodice() {
		return codice;
	}




	public void setCodice(String codice) {
		this.codice = codice;
	}




	public String getDataemissione() {
		return dataemissione;
	}




	public void setDataemissione(String dataemissione) {
		this.dataemissione = dataemissione;
	}




	public String getImporto() {
		return importo;
	}




	public void setImporto(String importo) {
		this.importo = importo;
	}




	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codice == null) ? 0 : codice.hashCode());
		result = prime * result + ((dataemissione == null) ? 0 : dataemissione.hashCode());
		result = prime * result + ((importo == null) ? 0 : importo.hashCode());
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
		Fattura other = (Fattura) obj;
		if (codice == null) {
			if (other.codice != null)
				return false;
		} else if (!codice.equals(other.codice))
			return false;
		if (dataemissione == null) {
			if (other.dataemissione != null)
				return false;
		} else if (!dataemissione.equals(other.dataemissione))
			return false;
		if (importo == null) {
			if (other.importo != null)
				return false;
		} else if (!importo.equals(other.importo))
			return false;
		return true;
	}




	@Override
	public String toString() {
		return "Fattura [codice=" + codice + ", dataemissione=" + dataemissione + ", importo=" + importo + "]";
	}

}
