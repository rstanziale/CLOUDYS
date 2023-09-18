package com.uniba.bdii.model;

public class Processore {

  private String modello;
  private String core;

  public Processore(String modello, String core) {
    super();
    this.modello = modello;
    this.core = core;
  }

  public String getModello() {
    return modello;
  }

  public void setModello(String modello) {
    this.modello = modello;
  }

  public String getCore() {
    return core;
  }

  public void setCore(String core) {
    this.core = core;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((core == null) ? 0 : core.hashCode());
    result = prime * result + ((modello == null) ? 0 : modello.hashCode());
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
    Processore other = (Processore) obj;
    if (core == null) {
      if (other.core != null)
        return false;
    } else if (!core.equals(other.core))
      return false;
    if (modello == null) {
      return other.modello == null;
    } else return modello.equals(other.modello);
  }

  @Override
  public String toString() {
    return "Processore [modello=" + modello + ", core=" + core + "]";
  }
}
