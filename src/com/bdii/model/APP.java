package com.bdii.model;

public class APP 
{
	private String id;
	private String nome;
	private String costo;
	private String licenza;
	private String versione;
	
	
	public APP(String id, String nome, String costo, String licenza, String versione) {
		super();
		this.id = id;
		this.nome = nome;
		this.costo = costo;
		this.licenza = licenza;
		this.versione = versione;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public String getCosto() {
		return costo;
	}


	public void setCosto(String costo) {
		this.costo = costo;
	}


	public String getLicenza() {
		return licenza;
	}


	public void setLicenza(String licenza) {
		this.licenza = licenza;
	}


	public String getVersione() {
		return versione;
	}


	public void setVersione(String versione) {
		this.versione = versione;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((costo == null) ? 0 : costo.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((licenza == null) ? 0 : licenza.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((versione == null) ? 0 : versione.hashCode());
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
		APP other = (APP) obj;
		if (costo == null) {
			if (other.costo != null)
				return false;
		} else if (!costo.equals(other.costo))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (licenza == null) {
			if (other.licenza != null)
				return false;
		} else if (!licenza.equals(other.licenza))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (versione == null) {
			if (other.versione != null)
				return false;
		} else if (!versione.equals(other.versione))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "APP [id=" + id + ", nome=" + nome + ", costo=" + costo + ", licenza=" + licenza + ", versione="
				+ versione + "]";
	}
	
	
	
	
	
	
}
