package it.polito.tdp.PremierLeague.model;

public class SquadraPunteggio {

		Team t;
		int punti;
		
		
		public SquadraPunteggio(Team t, int punti) {
			super();
			this.t = t;
			this.punti = punti;
		}
		
		public Team getT() {
			return t;
		}
		public void setT(Team t) {
			this.t = t;
		}
		public int getPunti() {
			return punti;
		}
		public void setPunti(int punti) {
			this.punti = punti;
		}
		
		
	
}
