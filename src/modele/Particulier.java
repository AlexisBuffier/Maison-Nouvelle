package modele;

public class Particulier extends Client {

     //Variables

    protected String nom, prenom;

    //CONSTRUCTEUR

    public Particulier(int id, String telephone, String nom, String prenom) {
        super(id, telephone);
        this.nom = nom;
        this.prenom = prenom;
    }
    //FONCTIONS



    //GETTER AND SETTER

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }


}