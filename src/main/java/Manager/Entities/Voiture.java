package Manager.Entities;

import javafx.scene.control.TableColumn;

public class Voiture {
    private Integer VO_id;
    private String VO_modele;
    private String VO_description;
    private Boolean VO_disponible;

    public Voiture(Integer VO_id, String VO_modele, String VO_decription, Boolean VO_disponible){
        this.VO_id = VO_id;
        this.VO_modele = VO_modele;
        this.VO_description = VO_decription;
        this.VO_disponible = VO_disponible;
    }

    public Integer getVO_id() {
        return VO_id;
    }

    public String getVO_modele() {
        return VO_modele;
    }

    public String getVO_description() {
        return VO_description;
    }

    public Boolean getVO_disponible() {
        return VO_disponible;
    }

    public void setVO_decription(String VO_decription) {
        this.VO_description = VO_decription;
    }

    public void setVO_id(Integer VO_id) {
        this.VO_id = VO_id;
    }

    public void setVO_disponible(Boolean VO_disponible) {
        this.VO_disponible = VO_disponible;
    }

    public void setVO_modele(String VO_modele) { this.VO_modele = VO_modele; }
}
