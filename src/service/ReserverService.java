package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDate;

public class ReserverService {

	public void lieReservationTable(int idReservation, int idTable)
	{
		try {
			Connection conn = Connexion.getConnection();
			
			String insertSql = "INSERT INTO reserver (idreservation, idTable) "
							 + "VALUES (?, ?)";
			
			PreparedStatement statement = conn.prepareStatement(insertSql);
	        statement.setInt(1, idReservation);
	        statement.setInt(2, idTable);
	        
	        statement.executeUpdate();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
