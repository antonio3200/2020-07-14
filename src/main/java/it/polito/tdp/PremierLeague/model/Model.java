package it.polito.tdp.PremierLeague.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.PremierLeague.db.PremierLeagueDAO;

public class Model {
	
	private PremierLeagueDAO dao;
	private SimpleDirectedWeightedGraph<Team,DefaultWeightedEdge> grafo;
	private List<Team> vertici;
	private List<SquadraPunteggio> squadre;
	
	public Model() {
		this.dao= new PremierLeagueDAO();
		this.squadre= new ArrayList<>();
	}
	
	public void creaGrafo() {
		this.grafo=new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		this.vertici= this.dao.listAllTeams();
		//aggiungo vertici
		Graphs.addAllVertices(this.grafo, this.vertici);
		//aggiungo archi
		for(Team t : this.vertici) {
			List<Integer> ris= this.dao.getRisultatiCasa(t);
			List<Integer> risTrasf= this.dao.getrisultatiTrasferta(t);
			int punti=0;
			for(Integer i : ris) {
				if(i==1) {
					punti+=3;
				}
				else if(i==0) {
					punti+=1;
				}
			}
			for(Integer i : risTrasf) {
				if(i==-1) {
					punti+=3;
				}
				else if(i==0) {
					punti+=1;
				}
			}
			SquadraPunteggio s = new SquadraPunteggio(t,punti);
			squadre.add(s);
		}
		for(SquadraPunteggio s1 : squadre) {
			for(SquadraPunteggio s2 : squadre) {
				if(s1.getPunti()>s2.getPunti()) {
					Graphs.addEdgeWithVertices(this.grafo, s1.getT(), s2.getT(), s1.getPunti()-s2.getPunti());
				}
				else if(s1.getPunti()<s2.getPunti()) {
					Graphs.addEdgeWithVertices(this.grafo, s2.getT(), s1.getT(), s2.getPunti()-s1.getPunti());
				}
			}
		}
	}
	
	public int nVertici() {
		return this.grafo.vertexSet().size();
	}
	
	public int nArchi() {
		return this.grafo.edgeSet().size();
	}
	
	public List<Differenza> getClassifica(Team selezionata) {
		SquadraPunteggio scelta=null;
		int differenza=0;
		List<Differenza> result= new ArrayList<>();
		for(SquadraPunteggio s : this.squadre) {
			if(s.getT().getTeamID()==selezionata.getTeamID()) {
				scelta=s;
			}
		}
		for(SquadraPunteggio s1: this.squadre) {
			if(s1.getT().getTeamID()!=scelta.getT().getTeamID()) {
				differenza=scelta.getPunti()-s1.getPunti();
				if(differenza>0) {
					Differenza d = new Differenza(s1.getT(),differenza);
					result.add(d);
				}
					
			}	
	}			
	Collections.sort(result);
	return result;
	}
	
	public List<DifferenzaMinore> getClassificaMinore(Team selezionata) {
		SquadraPunteggio scelta=null;
		int differenza=0;
		List<DifferenzaMinore> result= new ArrayList<>();
		for(SquadraPunteggio s : this.squadre) {
			if(s.getT().getTeamID()==selezionata.getTeamID()) {
				scelta=s;
			}
		}
		for(SquadraPunteggio s1: this.squadre) {
			if(s1.getT().getTeamID()!=scelta.getT().getTeamID()) {
				differenza=scelta.getPunti()-s1.getPunti();
				if(differenza<0) {
					DifferenzaMinore d = new DifferenzaMinore(s1.getT(),-differenza);
					result.add(d);
				}
					
			}	
	}	
	Collections.sort(result);
	return result;
	}

	public List<Team> getVertici() {
		// TODO Auto-generated method stub
		return this.vertici;
	}
	
}
