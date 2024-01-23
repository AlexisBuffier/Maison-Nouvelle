package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import modele.Client;

public class ParticulierService {

	public boolean selectParticulierByName(String nom, String prenom)
	{
		try {
			Connection conn = Connexion.getConnection();
			
			String selectSql = "SELECT nom, prenom "
							 + "FROM client "
							 + "WHERE LOWER(nom) like LOWER(?) "
							 + "AND LOWER(prenom) like LOWER(?)";
			
			PreparedStatement statement = conn.prepareStatement(selectSql);
            statement.setString(1, nom);
            statement.setString(2, prenom);
            ResultSet resultSet = statement.executeQuery();
			
			if (resultSet.next()) {
	            return true;
	        } else {
	            return false; 
	        }
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public int selectIdParticulierByName(String nom, String prenom)
	{
		try {
			Connection conn = Connexion.getConnection();
			
			String selectSql = "SELECT idCli "
							 + "FROM client "
							 + "WHERE LOWER(nom) like LOWER(?) "
							 + "AND LOWER(prenom) like LOWER(?)";
			
			PreparedStatement statement = conn.prepareStatement(selectSql);
            statement.setString(1, nom);
            statement.setString(2, prenom);
            ResultSet resultSet = statement.executeQuery();
			
			if (resultSet.next()) {
	            return resultSet.getInt(1);
	        } 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	public Boolean insertNouveauParticulier(String nom, String prenom, String telephone)
	{
		try {
			Connection conn = Connexion.getConnection();
			
			String insertSql = "INSERT INTO client (telCli, nom, prenom) VALUES (?, ?, ?)";
			
			PreparedStatement statement = conn.prepareStatement(insertSql);
	        statement.setString(1, telephone);
	        statement.setString(2, nom);
	        statement.setString(3, prenom);
	        
	        statement.executeUpdate();
	        
	        return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public String selectTelephoneParticulierByName(String nom, String prenom)
	{
		try {
			Connection conn = Connexion.getConnection();
			
			String selectSql = "SELECT telCli "
							 + "FROM client "
							 + "WHERE LOWER(nom) like LOWER(?) "
							 + "AND LOWER(prenom) like LOWER(?)";
			
			PreparedStatement statement = conn.prepareStatement(selectSql);
            statement.setString(1, nom);
            statement.setString(2, prenom);
            ResultSet resultSet = statement.executeQuery();
			
			if (resultSet.next()) {
	            return resultSet.getString(1);
	        } 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
