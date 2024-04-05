package Manager;

import Manager.Entities.Circuit;
import Manager.Entities.Conducteur;
import Manager.Entities.Creneau;

import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class AgendaManager {

    public boolean isCircuitComplet(Integer CI_id, Integer CR_id, LocalDate dateReserv){
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/gestion_circuit", "root", "");
            String query = "SELECT ci.CI_nbVoituresMax, COUNT(re.RE_id) AS nb_reserv FROM `ci_circuit` AS ci INNER JOIN re_reservation AS re ON (ci.CI_id = re.RE_idCircuit) WHERE ci.CI_id = ? AND re.RE_idCreneau = ? AND re.RE_date = ? ORDER BY nb_reserv;";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, CI_id);
            stmt.setInt(2, CR_id);
            stmt.setDate(3, java.sql.Date.valueOf(dateReserv));
            rs = stmt.executeQuery();
            if (rs.next()) {
                Integer nbVoitureMax = rs.getInt("CI_nbVoituresMax");
                Integer nbReserv = rs.getInt("nb_reserv");
                if (nbReserv > nbVoitureMax){
                    return false;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
            return true;
    }

    public List<Creneau> listCreneau(Integer circuitID, Integer voitureID, LocalDate dateReserv) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Creneau> allCreneau = new ArrayList<Creneau>();

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/gestion_circuit", "root", "");
            String query = "SELECT * FROM `cr_creneau` WHERE cr_id NOT IN (SELECT re_idCreneau FROM re_reservation WHERE re_idCircuit = ? AND re_idVoiture = ? AND re_date = ?)";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, circuitID);
            stmt.setInt(2, voitureID);
            stmt.setDate(3, java.sql.Date.valueOf(dateReserv));

            rs = stmt.executeQuery();

            while (rs.next()) {
                Integer CR_Id = rs.getInt("cr_id");
                String CR_Creneau = rs.getString("cr_creneau");
                Creneau newCreneau = new Creneau(CR_Id, CR_Creneau);
                allCreneau.add(newCreneau);
            }
            return allCreneau;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public Integer verifConducteur(Conducteur newConducteur) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/gestion_circuit", "root", "");
            String queryExist = "SELECT co_id FROM co_conducteur WHERE co_permis = ? ";
            stmt = conn.prepareStatement(queryExist);
            stmt.setLong(1, newConducteur.getCO_permis());
            rs = stmt.executeQuery();
            if (rs.next() == false) {
                try {
                    String query = "INSERT INTO co_conducteur(CO_nom, CO_prenom, CO_dateNaissance, CO_numeroTel, CO_permis) VALUES (?, ?, ?, ?, ?);";
                    stmt = conn.prepareStatement(query);
                    stmt.setString(1, newConducteur.getCO_nom());
                    stmt.setString(2, newConducteur.getCO_prenom());
                    stmt.setDate(3, java.sql.Date.valueOf(newConducteur.getCO_naissance()));
                    stmt.setInt(4, newConducteur.getCO_tel());
                    stmt.setLong(5, newConducteur.getCO_permis());
                    stmt.executeUpdate();
                } catch (SQLException var5) {
                    throw new RuntimeException(var5);
                }
                try {
                    rs = stmt.executeQuery("SELECT LAST_INSERT_ID()");
                    if (rs.next()) {
                        long CO_idLong = rs.getLong(1); // Récupérer l'ID généré
                        Integer CO_id = Integer.valueOf((int) CO_idLong);
                        return CO_id;
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }else{
                Integer CO_id = rs.getInt("co_id");
                return CO_id;
            }
            return 0;
        } catch (SQLException var5) {
            throw new RuntimeException(var5);
        }
    }

    public void addReserv(Integer VO_id, Integer CI_id, Integer CO_id, Integer CR_id, LocalDate dateReserv){
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/gestion_circuit", "root", "");
            String query = "INSERT INTO re_reservation(RE_idVoiture, RE_idCircuit, RE_idConducteur, RE_idCreneau, RE_date) VALUES (?, ?, ?, ?, ?);";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, VO_id);
            stmt.setInt(2, CI_id);
            stmt.setInt(3, CO_id);
            stmt.setInt(4, CR_id);
            stmt.setDate(5, java.sql.Date.valueOf(dateReserv));
            stmt.executeUpdate();
        } catch (SQLException var5) {
            throw new RuntimeException(var5);
        }
    }
}
