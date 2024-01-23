package modele;

public class Professionnel extends Client {

    //VARIABLES

    private String nomSociete;


    //CONSTRUCTEUR

    public Professionnel(int id, String telephone, String nomSociete) {
	    super(id, telephone);
	    this.nomSociete = nomSociete;
    }

    //FONCTIONS


    //GETTER AND SETTER

    public String getNomSociete() {
        return nomSociete;
    }


    public void setNomSociete(String nomSociete) {
        this.nomSociete = nomSociete;
    }

}