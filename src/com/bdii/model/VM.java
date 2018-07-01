package com.bdii.model;

import java.util.ArrayList;

public class VM {
	
	private String id;
	private String hdd;
	private String ram;
	private String processore;
	private String datacreazione;
	private String costo;
	private String os;
	private ArrayList<String> app;
	
	
	public VM(String id, String hdd, String ram, String processore, String datacreazione, String costo) {
		super();
		this.id = id;
		this.hdd = hdd;
		this.ram = ram;
		this.processore = processore;
		this.datacreazione = datacreazione;
		this.costo = costo;
		
		this.app = new ArrayList<String>();
	}



	public String getId() {
		return id;
	}



	public String getOsName() {
		return os;
	}



	public void setOs(String os) {
		this.os = os;
	}



	public ArrayList<String> getApp() {
		return app;
	}



	public void addApp(String app) {
		this.app.add(app);
	}



	public void setId(String id) {
		this.id = id;
	}



	public String getHdd() {
		return hdd;
	}



	public void setHdd(String hdd) {
		this.hdd = hdd;
	}



	public String getRam() {
		return ram;
	}



	public void setRam(String ram) {
		this.ram = ram;
	}



	public String getProcessore() {
		return processore;
	}



	public void setProcessore(String processore) {
		this.processore = processore;
	}



	public String getDatacreazione() {
		return datacreazione;
	}



	public void setDatacreazione(String datacreazione) {
		this.datacreazione = datacreazione;
	}



	public String getCosto() {
		return costo;
	}



	public void setCosto(String costo) {
		this.costo = costo;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((costo == null) ? 0 : costo.hashCode());
		result = prime * result + ((datacreazione == null) ? 0 : datacreazione.hashCode());
		result = prime * result + ((hdd == null) ? 0 : hdd.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((processore == null) ? 0 : processore.hashCode());
		result = prime * result + ((ram == null) ? 0 : ram.hashCode());
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
		VM other = (VM) obj;
		if (costo == null) {
			if (other.costo != null)
				return false;
		} else if (!costo.equals(other.costo))
			return false;
		if (datacreazione == null) {
			if (other.datacreazione != null)
				return false;
		} else if (!datacreazione.equals(other.datacreazione))
			return false;
		if (hdd == null) {
			if (other.hdd != null)
				return false;
		} else if (!hdd.equals(other.hdd))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (processore == null) {
			if (other.processore != null)
				return false;
		} else if (!processore.equals(other.processore))
			return false;
		if (ram == null) {
			if (other.ram != null)
				return false;
		} else if (!ram.equals(other.ram))
			return false;
		return true;
	}



	@Override
	public String toString() {
		return "VM [id=" + id + ", hdd=" + hdd + ", ram=" + ram + ", processore=" + processore + ", datacreazione="
				+ datacreazione + ", costo=" + costo + ", os=" + os + "]";
	}
}
