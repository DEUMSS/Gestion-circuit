package Manager.Entities;

public class Creneau {
    private Integer CR_id;
    private String CR_creneau;

    public Creneau(Integer CR_id, String CR_creneau){
        this.CR_id = CR_id;
        this.CR_creneau = CR_creneau;
    }

    public Integer getCR_id(){
        return this.CR_id;
    }

    public String getCR_creneau(){
        return this.CR_creneau;
    }

    public void setCR_creneau(String CR_creneau){
        this.CR_creneau = CR_creneau;
    }

    public void setCR_id(Integer CR_id){
        this.CR_id = CR_id;
    }
}
