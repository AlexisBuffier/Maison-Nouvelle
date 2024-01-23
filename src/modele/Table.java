package modele;

public class Table {

    //VARIABLES

    private int idTable, nbchaise;

    //CONSTRUCTEUR

    public Table(int idTable, int nbchaise) {
        super();
        this.idTable = idTable;
        this.nbchaise = nbchaise;
    }

    //FONCTIONS

    //GETTER AND SETTER

    public int getIdTable() {
        return idTable;
    }

    public void setIdTable(int idTable) {
        this.idTable = idTable;
    }

    public int getNbchaise() {
        return nbchaise;
    }

    public void setNbchaise(int nbchaise) {
        this.nbchaise = nbchaise;
    }


}