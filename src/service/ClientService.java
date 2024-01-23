package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import modele.Client;

public class ClientService {

	public ArrayList<Client> getAllClient()
	{
		try {
			Connection conn = Connexion.getConnection();
			
			Statement st = conn.createStatement();

			ResultSet rs = st.executeQuery("SELECT * FROM client ORDER BY id ASC");
			
			ArrayList<Client> clients = new ArrayList<>();
			

            while(rs.next()) {
            	
                int id = rs.getInt("id");
                String telephone = rs.getString("telephone");

                Client client = new Client(id, telephone);
                clients.add(client);
            }

            return clients;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
