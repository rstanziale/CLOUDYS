package com.uniba.bdii.model;

import java.util.ArrayList;

public class Server {

  private String ID;
  private String ram;
  private String processore;
  private int numeroVM;
  private ArrayList<String> hdd;

  public Server(String ID, String ram, String processore) {
    super();

    this.ID = ID;
    this.ram = ram;
    this.processore = processore;
    this.numeroVM = 0;
    this.hdd = new ArrayList<String>();
  }

  public String getID() {
    return ID;
  }

  public void setID(String iD) {
    ID = iD;
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

  public ArrayList<String> getHdd() {
    return hdd;
  }

  public void addHdd(String hdd) {
    this.hdd.add(hdd);
  }

  public int getNumeroVM() {
    return numeroVM;
  }

  public void setNumeroVM(int numeroVM) {
    this.numeroVM = numeroVM;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((ID == null) ? 0 : ID.hashCode());
    result = prime * result + ((hdd == null) ? 0 : hdd.hashCode());
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
    Server other = (Server) obj;
    if (ID == null) {
      if (other.ID != null)
        return false;
    } else if (!ID.equals(other.ID))
      return false;
    if (hdd == null) {
      if (other.hdd != null)
        return false;
    } else if (!hdd.equals(other.hdd))
      return false;
    if (processore == null) {
      if (other.processore != null)
        return false;
    } else if (!processore.equals(other.processore))
      return false;
    if (ram == null) {
      return other.ram == null;
    } else return ram.equals(other.ram);
  }

  @Override
  public String toString() {
    return "Server [ID=" + ID + ", ram=" + ram + ", processore=" + processore + ", hdd=" + hdd + "]";
  }
}
