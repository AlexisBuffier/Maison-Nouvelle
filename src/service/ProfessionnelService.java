package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import modele.Client;

public class ProfessionnelService {

	public boolean selectProfessionnelByName(String nomSociete)
	{
		try {
			Connection conn = Connexion.getConnection();
			
			String selectSql = "SELECT nomSociete "
							 + "FROM client "
							 + "WHERE LOWER(nomSociete) like LOWER(?)";
			
			PreparedStatement statement = conn.prepareStatement(selectSql);
            statement.setString(1, nomSociete);
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
	
	public int selectIdProfessionnelByName(String nomSociete)
	{
		try {
			Connection conn = Connexion.getConnection();
			
			String selectSql = "SELECT idCli "
							 + "FROM client "
							 + "WHERE LOWER(nomSociete) like LOWER(?)";
			
			PreparedStatement statement = conn.prepareStatement(selectSql);
            statement.setString(1, nomSociete);
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
	
	public Boolean insertNouveauProfessionnel(String nomSociete, String telephone)
	{
		try {
			Connection conn = Connexion.getConnection();
			
			String insertSql = "INSERT INTO client (telCli, nomSociete) VALUES (?, ?)";
			
			PreparedStatement statement = conn.prepareStatement(insertSql);
	        statement.setString(1, telephone);
	        statement.setString(2, nomSociete);
	        
	        statement.executeUpdate();
	        
	        return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public String selectTelephoneProfessionnelByName(String nomSociete)
	{
		try {
			Connection conn = Connexion.getConnection();
			
			String selectSql = "SELECT telCli "
							 + "FROM client "
							 + "WHERE LOWER(nomSociete) like LOWER(?) ";
			
			PreparedStatement statement = conn.prepareStatement(selectSql);
            statement.setString(1, nomSociete);
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
