package Manager.Entities;

public class Circuit {

    private Integer CI_id;
    private String CI_description;

    private Boolean CI_disponible;
    private Integer CI_nbVoituresMax;

    public Circuit(Integer CI_id, String  CI_description, Boolean CI_disponible, Integer CI_nbVoituresMax) {
        this.CI_id = CI_id;
        this.CI_description = CI_description;
        this.CI_disponible = CI_disponible;
        this.CI_nbVoituresMax = CI_nbVoituresMax;
    }

    public Integer getCI_id() {
        return CI_id;
    }
    public String getCI_description() {
        return CI_description;
    }
    public Boolean getCI_disponible() {
        return CI_disponible;
    }
    public Integer getCI_nbVoituresMax() {
        return CI_nbVoituresMax;
    }
    public void setCI_id(Integer CI_id) {
        this.CI_id = CI_id;
    }
    public void setCI_description(String CI_description) {
        this.CI_description = CI_description;
    }
    public void setCI_dis(Boolean CI_disponible) {
        this.CI_disponible = CI_disponible;
    }
    public void setCI_nbVoMax(Integer CI_nbVoituresMax) {
       this.CI_nbVoituresMax = CI_nbVoituresMax;
    }

}
