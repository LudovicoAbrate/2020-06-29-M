package it.polito.tdp.imdb.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.imdb.model.Actor;
import it.polito.tdp.imdb.model.Adiacenza;
import it.polito.tdp.imdb.model.Director;
import it.polito.tdp.imdb.model.Movie;

public class ImdbDAO {
	
	public List<Actor> listAllActors(){
		String sql = "SELECT * FROM actors";
		List<Actor> result = new ArrayList<Actor>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Actor actor = new Actor(res.getInt("id"), res.getString("first_name"), res.getString("last_name"),
						res.getString("gender"));
				
				result.add(actor);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Movie> listAllMovies(){
		String sql = "SELECT * FROM movies";
		List<Movie> result = new ArrayList<Movie>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Movie movie = new Movie(res.getInt("id"), res.getString("name"), 
						res.getInt("year"), res.getDouble("rank"));
				
				result.add(movie);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public List<Director> listAllDirectors(Map<Integer, Director> idMap){
		
		String sql = "SELECT * FROM directors";
		
		List<Director> result = new ArrayList<Director>();
		
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {
            if(!idMap.containsKey(res.getInt("id"))){
				Director director = new Director(res.getInt("id"), res.getString("first_name"), res.getString("last_name"));
				
				idMap.put(res.getInt("id"), director);
			}
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public List<Director> getVertici(int anno){
		
		String sql = "select distinct d.* "
				+ "from directors d ,movies m , movies_directors md "
				+ "where d.id = md.director_id "
				+ "and m.id = md.movie_id "
				+ "and m.year = ? ";
		
List<Director> result = new ArrayList<Director>();
		
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1,anno);
			ResultSet res = st.executeQuery();
			while (res.next()) {
         
				Director director = new Director(res.getInt("id"), res.getString("first_name"), res.getString("last_name"));
				
				result.add(director);
				
			}
			
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	public List<Adiacenza> getAdiacenze(int anno,Map<Integer,Director> idMap){
		
		String sql = "select  md1.director_id AS  id1, md2.director_id AS  id2, COUNT(r1.actor_id) AS peso "
				+ "from  movies m1 ,movies m2,  movies_directors md1,movies_directors md2, roles r1,roles r2 "
				+ "where md1.director_id < md2.director_id "
				+ "and r1.actor_id = r2.actor_id "
				+ "and r1.movie_id = md1.movie_id "
				+ "and r2.movie_id = md2.movie_id "
				+ "and m1.year = ? "
				+ "and m1.year = m2.year "
				+ "and m1.id = r1.movie_id "
				+ "and m2.id = r2.movie_id "
				+ "group by id1,id2 ";
		
List<Adiacenza> result = new ArrayList<Adiacenza>();
		
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1,anno);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				
				Director d1 = idMap.get(res.getInt("id1"));
				Director d2 = idMap.get(res.getInt("id2"));
         
				if(d1 != null && d2 != null) {
				Adiacenza a = new Adiacenza(d1,d2,res.getDouble("peso"));
				
				result.add(a);
				}
			}
			
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	
	
}
