package Manager.Entities;

import java.time.LocalDate;
import java.util.Date;

public class Conducteur {
    private String CO_nom;
    private String CO_prenom;
    private LocalDate CO_naissance;
    private Integer CO_tel;
    private Long CO_permis;

    public Conducteur(String CO_nom, String CO_prenom, LocalDate CO_naissance, Integer CO_tel, Long CO_permis) {
        this.CO_nom = CO_nom;
        this.CO_prenom = CO_prenom;
        this.CO_naissance = CO_naissance;
        this.CO_tel = CO_tel;
        this.CO_permis = CO_permis;
    }

    public String getCO_nom() {
        return CO_nom;
    }
    public String getCO_prenom() {
        return CO_prenom;
    }
    public LocalDate getCO_naissance() {
        return CO_naissance;
    }
    public Integer getCO_tel() {
        return CO_tel;
    }
    public Long getCO_permis() {
        return CO_permis;
    }
    public void setCO_nom(String CO_nom) {
        this.CO_nom = CO_nom;
    }
    public void setCO_prenom(String CO_prenom) {
        this.CO_prenom = CO_prenom;
    }
    public void setCO_naissance(LocalDate CO_naissance) {
        this.CO_naissance = CO_naissance;
    }
    public void setCO_tel(Integer CO_tel){
        this.CO_tel = CO_tel;
    }
    public void setCO_permis(Long CO_permis){
        this.CO_permis = CO_permis;
    }
}
