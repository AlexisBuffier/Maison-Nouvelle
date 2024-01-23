package modele;

public class Client {
  //Variables

    int id;
    String telephone;

  //CONSTRUCTEUR

    public Client(int id, String telephone) {
    super();
    this.id = id;
    this.telephone = telephone;
    }

  //FONCTIONS

  //GETTER AND SETTER

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

}