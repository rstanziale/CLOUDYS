package com.bdii.model;

public class Hdd {
	
	private String id;
	private String capacita;
	
	
	public Hdd(String id, String capacita) {
		super();
		this.id = id;
		this.capacita = capacita;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getCapacita() {
		return capacita;
	}


	public void setCapacita(String capacita) {
		this.capacita = capacita;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((capacita == null) ? 0 : capacita.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Hdd other = (Hdd) obj;
		if (capacita == null) {
			if (other.capacita != null)
				return false;
		} else if (!capacita.equals(other.capacita))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "Hdd [id=" + id + ", capacita=" + capacita + "]";
	}

}
