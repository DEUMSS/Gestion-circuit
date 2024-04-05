package Manager;

import Manager.Entities.Voiture;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VoitureManager {

    public List<Voiture> allVoiture(){
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        List<Voiture> allVoiture = new ArrayList<Voiture>();

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/gestion_circuit", "root", "");
            String query = "SELECT * FROM vo_voiture";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                Integer VO_Id = rs.getInt(1);
                String VO_Modele = rs.getString(2);
                String VO_Description = rs.getString(3);
                Boolean VO_Disponible = rs.getBoolean(4);
                Voiture newVoiture = new Voiture(VO_Id,  VO_Modele, VO_Description, VO_Disponible);
                allVoiture.add(newVoiture);
            }
            return allVoiture;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addVoiture( Voiture newVoiture){
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/gestion_circuit", "root", "");
            String query = "INSERT INTO vo_voiture(VO_modele, VO_description, VO_disponible) VALUES (?, ?, ?);";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, newVoiture.getVO_modele());
            stmt.setString(2, newVoiture.getVO_description());
            stmt.setBoolean(3, newVoiture.getVO_disponible());
            stmt.executeUpdate();
        } catch (SQLException var5) {
            throw new RuntimeException(var5);
        }

    }

    public void deleteVoiture( Voiture voitureselected) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/gestion_circuit", "root", "");
            String query = "DELETE FROM vo_voiture WHERE VO_id = ?;";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, voitureselected.getVO_id());
            stmt.execute();
        } catch (SQLException var5) {
            throw new RuntimeException(var5);
        }
    }

    public void updateVoiture( Voiture selectedVoiture) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/gestion_circuit", "root", "");
            String query = "UPDATE vo_voiture SET VO_modele = ?, VO_description = ?, VO_disponible = ? WHERE VO_id = ?;";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, selectedVoiture.getVO_modele());
            stmt.setString(2, selectedVoiture.getVO_description());
            stmt.setBoolean(3, selectedVoiture.getVO_disponible());
            stmt.setInt(4, selectedVoiture.getVO_id());
            stmt.execute();
        } catch (SQLException var5) {
            throw new RuntimeException(var5);
        }
    }
}
