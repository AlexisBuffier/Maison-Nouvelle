package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;

import modele.Table;

public class TableService {

	public int countTableDispoByDate(LocalDate date)
	{
		try {
			Connection conn = Connexion.getConnection();
			
			String selectSql = "SELECT COUNT(*) "
							 + "FROM `tabler` "
							 + "WHERE `idTable` NOT IN ( "
							 + "	SELECT `tabler`.`idTable` "
							 + "	FROM `tabler` "
							 + "	JOIN `reserver` ON `tabler`.`idTable` = `reserver`.`idTable` "
							 + "	JOIN `réservation` ON `reserver`.`idreservation` = `réservation`.`idreservation` "
							 + "	WHERE `réservation`.`dateReservation` = ? "
							 + ")";
			
			PreparedStatement statement = conn.prepareStatement(selectSql);
			java.sql.Date sqlDate = java.sql.Date.valueOf(date);
	        statement.setDate(1, sqlDate);
	        
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
	
	public ArrayList<Table> selectTableAReserver(int nbTableNecessaire, LocalDate date)
	{
		try {
			Connection conn = Connexion.getConnection();
			
			String selectSql = "SELECT idTable "
							 + "FROM tabler "
							 + "WHERE idTable NOT IN ( "
							 + "    SELECT tabler.idTable "
							 + "    FROM tabler "
							 + "    JOIN reserver ON tabler.idTable = reserver.idTable "
							 + "    JOIN réservation ON reserver.idreservation = réservation.idreservation "
							 + "    WHERE réservation.dateReservation = ?) "
							 + "ORDER BY idTable ASC "
							 + "LIMIT ?";
			
			PreparedStatement statement = conn.prepareStatement(selectSql);
			java.sql.Date sqlDate = java.sql.Date.valueOf(date);
	        statement.setDate(1, sqlDate);
	        statement.setInt(2, nbTableNecessaire);
	        
            ResultSet resultSet = statement.executeQuery();
			
            ArrayList<Table> tables = new ArrayList<>();
			

            while(resultSet.next()) {
            	
                int idTable = resultSet.getInt(1);

                Table table = new Table(idTable, 2);
                tables.add(table);
            }

            return tables;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return null;
	}
}
