package it.polito.tdp.PremierLeague.model;

public class DifferenzaMinore implements Comparable<DifferenzaMinore> {
	
	Team t;
	int dif;
	public DifferenzaMinore(Team t, int dif) {
		super();
		this.t = t;
		this.dif = dif;
	}
	public Team getT() {
		return t;
	}
	public void setT(Team t) {
		this.t = t;
	}
	public int getDif() {
		return dif;
	}
	public void setDif(int dif) {
		this.dif = dif;
	}
	
	@Override
	public int compareTo(DifferenzaMinore o) {
		return (this.dif-o.dif);
	}
	
	@Override
	public String toString() {
		return this.t.getName()+" - "+ this.dif;
	}

}
