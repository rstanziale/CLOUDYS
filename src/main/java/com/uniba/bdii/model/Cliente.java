package com.uniba.bdii.model;

public class Cliente {
  private String cf;
  private String nome;
  private String cognome;
  private String email;
  private String bonus;

  public Cliente(String cf, String nome, String cognome, String email, String bonus) {
    super();
    this.cf = cf;
    this.nome = nome;
    this.cognome = cognome;
    this.email = email;
    this.bonus = bonus;
  }

  public String getCf() {
    return cf;
  }

  public void setCf(String cf) {
    this.cf = cf;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getCognome() {
    return cognome;
  }

  public void setCognome(String cognome) {
    this.cognome = cognome;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getBonus() {
    return bonus;
  }

  public void setBonus(String bonus) {
    this.bonus = bonus;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((bonus == null) ? 0 : bonus.hashCode());
    result = prime * result + ((cf == null) ? 0 : cf.hashCode());
    result = prime * result + ((cognome == null) ? 0 : cognome.hashCode());
    result = prime * result + ((email == null) ? 0 : email.hashCode());
    result = prime * result + ((nome == null) ? 0 : nome.hashCode());
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
    Cliente other = (Cliente) obj;
    if (bonus == null) {
      if (other.bonus != null)
        return false;
    } else if (!bonus.equals(other.bonus))
      return false;
    if (cf == null) {
      if (other.cf != null)
        return false;
    } else if (!cf.equals(other.cf))
      return false;
    if (cognome == null) {
      if (other.cognome != null)
        return false;
    } else if (!cognome.equals(other.cognome))
      return false;
    if (email == null) {
      if (other.email != null)
        return false;
    } else if (!email.equals(other.email))
      return false;
    if (nome == null) {
      return other.nome == null;
    } else return nome.equals(other.nome);
  }

  @Override
  public String toString() {
    return "Cliente [cf=" + cf + ", nome=" + nome + ", cognome=" + cognome + ", email=" + email + ", bonus=" + bonus
            + "]";
  }
}
