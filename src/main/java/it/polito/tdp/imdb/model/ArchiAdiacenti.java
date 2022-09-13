package it.polito.tdp.imdb.model;



public class ArchiAdiacenti implements Comparable<ArchiAdiacenti> {
	
	
	String nome;
	double peso;
	public ArchiAdiacenti(String nome, double peso) {
		super();
		this.nome = nome;
		this.peso = peso;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public double getPeso() {
		return peso;
	}
	public void setPeso(double peso) {
		this.peso = peso;
	}
	@Override
	public int compareTo(ArchiAdiacenti altro) {
		// TODO Auto-generated method stub
		return (int) (altro.peso - this.peso);
	}
	
public String toString() {
		
		return nome + peso;
		
		
	}

}
