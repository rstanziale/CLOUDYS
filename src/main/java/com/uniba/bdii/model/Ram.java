package com.uniba.bdii.model;

public class Ram {

  private String id;
  private String capacita;

  public Ram(String id, String capacita) {
    super();
    this.id = id;
    this.capacita = capacita;
  }

  @Override
  public String toString() {
    return "Ram [id=" + id + ", capacita=" + capacita + "]";
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
    Ram other = (Ram) obj;
    if (capacita == null) {
      if (other.capacita != null)
        return false;
    } else if (!capacita.equals(other.capacita))
      return false;
    if (id == null) {
      return other.id == null;
    } else return id.equals(other.id);
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
}
