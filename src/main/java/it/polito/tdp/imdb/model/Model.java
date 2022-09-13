package it.polito.tdp.imdb.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.imdb.db.ImdbDAO;


public class Model {

	private Map<Integer,Director> idMap;
	private Graph<Director, DefaultWeightedEdge> grafo;
	private ImdbDAO dao;
	List<Director> vertici;
	
	public Model() {
		this.dao = new ImdbDAO();
		this.idMap= new HashMap<>();
		this.vertici= new LinkedList<>();
		
	}
	
	public void creaGrafo(int anno) {
	grafo = new SimpleWeightedGraph<Director,DefaultWeightedEdge>(DefaultWeightedEdge.class);

	idMap.clear();
	for( Director d : dao.getVertici(anno)) {
		
		
	idMap.put(d.id, d);
	}
	
	 Graphs.addAllVertices(this.grafo,dao.getVertici(anno));
	 
	 for(Adiacenza a : dao.getAdiacenze(anno, idMap)) {
	
	 Graphs.addEdgeWithVertices(this.grafo,a.d1 ,a.d2, a.peso);
	 }
	}
	
	public int nVertici() {
		return grafo.vertexSet().size();
		
	}
	public int nArchi() {
		return grafo.edgeSet().size();
		
	
	}
	
	public List<Director> getVertici(int anno){
		
		List<Director> atemp = new LinkedList<Director>(dao.getVertici(anno));
		
		Collections.sort(atemp);
		
		return atemp;
	}
	
	public String getAttoriAdiacenti(Director d){
		
		
		List<ArchiAdiacenti> result = new ArrayList<ArchiAdiacenti>();
 		double peso=0.0;
 		for(Director d1 : Graphs.neighborListOf(grafo, d)) {
 			
 			DefaultWeightedEdge e= this.grafo.getEdge(d, d1);
 			peso = this.grafo.getEdgeWeight(e);
 			
 			ArchiAdiacenti a = new ArchiAdiacenti(d1.lastName ,peso);
 			
 			result.add(a);
 		}
 		
 		
		Collections.sort(result);
		
		
		return result.toString();
		
		
	}

	
}
