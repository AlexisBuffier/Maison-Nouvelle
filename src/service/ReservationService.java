package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;

import modele.Reservation;
import modele.Table;

public class ReservationService {
	
	public ArrayList<Reservation> selectAllReservationByDate(LocalDate date)
	{
		try {
			Connection conn = Connexion.getConnection();
			
			String selectSql = "SELECT "
							 + "    res.idreservation, "
							 + "    CASE "
							 + "        WHEN cli.nomSociete IS NULL THEN CONCAT(cli.nom, ' ', cli.prenom) "
							 + "        ELSE cli.nomSociete "
							 + "    END AS nomReservation, "
							 + "    res.dateReservation, "
							 + "    res.nbpersonne, "
							 + "    res.heureReservation, "
							 + "    (SELECT GROUP_CONCAT(tbl.numeroTable SEPARATOR ', ') "
							 + "     FROM Tabler tbl "
							 + "     INNER JOIN reserver rv ON rv.idTable = tbl.idTable "
							 + "     WHERE rv.idreservation = res.idreservation) AS tablesReservees "
							 + "FROM réservation res "
							 + "LEFT JOIN Client cli ON cli.idCli = res.idCli "
							 + "WHERE res.dateReservation = ?";
			
			PreparedStatement statement = conn.prepareStatement(selectSql);
			java.sql.Date sqlDate = java.sql.Date.valueOf(date);
	        statement.setDate(1, sqlDate);
	        
            ResultSet resultSet = statement.executeQuery();
			
            ArrayList<Reservation> reservations = new ArrayList<>();
			

            while(resultSet.next()) {
            	
            	String idReservation = resultSet.getString("idreservation"); 
                String nomReservation = resultSet.getString("nomReservation");
                LocalDate dateReservation = resultSet.getDate("dateReservation").toLocalDate();
                int nbPersonne = resultSet.getInt("nbpersonne"); 
                String heureReservation = resultSet.getString("heureReservation"); 
                String tables = resultSet.getString("tablesReservees");

                Reservation reservation = new Reservation(idReservation, nomReservation, dateReservation, nbPersonne, heureReservation, tables);
                reservations.add(reservation);
            }

            return reservations;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return null;
	}

	public Boolean insertNouvelleReservation(int idCli, LocalDate dateReservation, int nbPersonne, String heureReservation)
	{
		try {
			Connection conn = Connexion.getConnection();
			
			String insertSql = "INSERT INTO réservation (idCli, dateReservation, nbPersonne, heureReservation) "
							 + "VALUES (?, ?, ?, ?)";
			
			PreparedStatement statement = conn.prepareStatement(insertSql);
	        statement.setInt(1, idCli);
	        java.sql.Date sqlDate = java.sql.Date.valueOf(dateReservation);
	        statement.setDate(2, sqlDate);
	        statement.setInt(3, nbPersonne);
	        statement.setString(4, heureReservation);
	        
	        statement.executeUpdate();
	        
	        return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public int selectDerniereReservation()
	{
		try {
			Connection conn = Connexion.getConnection();
			
			String selectSql = "SELECT MAX(idreservation) "
							 + "FROM `réservation` ";
			
			PreparedStatement statement = conn.prepareStatement(selectSql);
	        
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
	
	public boolean deleteReservation(int idReservation)
	{
		

		try {
			Connection conn = Connexion.getConnection();
			
			String deleteSql = "DELETE  "
							 + "FROM `reserver` WHERE idreservation = ? ";
			
			PreparedStatement statement = conn.prepareStatement(deleteSql);
	        statement.setInt(1, idReservation);
	        statement.executeUpdate();
            
            String deleteSql2 = "DELETE  "
					 		  + "FROM `réservation` WHERE idreservation = ? ";
            PreparedStatement statement2 = conn.prepareStatement(deleteSql2);
	        statement2.setInt(1, idReservation);
            statement2.executeUpdate();
            
            return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
}
