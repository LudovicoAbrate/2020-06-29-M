package it.polito.tdp.imdb.model;

public class Adiacenza implements Comparable <Adiacenza> {
	
	Director d1;
	Director d2;
	double peso;
	
	
	public Adiacenza(Director d1, Director d2, double peso) {
		super();
		this.d1 = d1;
		this.d2 = d2;
		this.peso = peso;
	}
	public Director getD1() {
		return d1;
	}
	public void setD1(Director d1) {
		this.d1 = d1;
	}
	public Director getD2() {
		return d2;
	}
	public void setD2(Director d2) {
		this.d2 = d2;
	}
	public double getPeso() {
		return peso;
	}
	public void setPeso(double peso) {
		this.peso = peso;
	}
	@Override
	public int compareTo(Adiacenza altro) {
		// TODO Auto-generated method stub
		return (int) (altro.peso - this.peso);
	}
	
	
	

}