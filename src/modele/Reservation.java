package modele;

import java.sql.Date;
import java.time.LocalDate;

import javafx.beans.property.SimpleIntegerProperty;

public class Reservation {

	private String numero;
    private String nom;
    private SimpleIntegerProperty nombreDePersonnes;
    private LocalDate date;
    private String table;
    private String heureReservation;

    public Reservation(String numero, String nom, LocalDate date, int nombreDePersonnes, String heureReservation, String table) {
        this.numero = numero;
        this.nom = nom;
        this.date = date;
        this.nombreDePersonnes = new SimpleIntegerProperty(nombreDePersonnes);
        this.heureReservation = heureReservation;
        this.table = table;
    }

	public String getNumero() {
		return numero;
	}

	public String getNom() {
		return nom;
	}

	public SimpleIntegerProperty getNombreDePersonnes() {
		return nombreDePersonnes;
	}

	public LocalDate getDate() {
        return date;
    }

	public String getTable() {
		return table;
	}

	public String getHeureReservation() {
		return heureReservation;
	}

	public void setHeureReservation(String heureReservation) {
		this.heureReservation = heureReservation;
	}
    
    
}
