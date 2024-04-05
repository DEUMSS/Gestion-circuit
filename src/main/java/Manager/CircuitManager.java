package Manager;

import Manager.Entities.Circuit;
import Manager.Entities.Voiture;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CircuitManager {

    public List<Circuit> allCircuit(){
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        List<Circuit> allCircuit = new ArrayList<Circuit>();

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/gestion_circuit", "root", "");
            String query = "SELECT * FROM ci_circuit";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                Integer CI_Id = rs.getInt(1);
                String CI_Description = rs.getString(2);
                Boolean CI_Disponible = rs.getBoolean(3);
                Integer CI_nbVoitureMax = rs.getInt(4);
                Circuit newCircuit = new Circuit(CI_Id, CI_Description, CI_Disponible, CI_nbVoitureMax);
                allCircuit.add(newCircuit);
            }
            return allCircuit;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addCircuit( Circuit newCircuit){
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/gestion_circuit", "root", "");
            String query = "INSERT INTO ci_circuit(CI_description, CI_disponible, CI_nbVoituresMax) VALUES (?, ?, ?);";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, newCircuit.getCI_description());
            stmt.setBoolean(2, newCircuit.getCI_disponible());
            stmt.setInt(3, newCircuit.getCI_nbVoituresMax());
            stmt.execute();
        } catch (SQLException var5) {
            throw new RuntimeException(var5);
        }

    }

    public void deleteCircuit( Circuit circuitSelected) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/gestion_circuit", "root", "");
            String query = "DELETE FROM ci_circuit WHERE CI_id = ?;";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, circuitSelected.getCI_id());
            stmt.execute();
        } catch (SQLException var5) {
            throw new RuntimeException(var5);
        }
    }

    public void updateCircuit( Circuit selectedCircuit) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/gestion_circuit", "root", "");
            String query = "UPDATE ci_circuit SET CI_description = ?, CI_disponible = ?, CI_nbVoituresMax = ? WHERE CI_id = ?;";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, selectedCircuit.getCI_description());
            stmt.setBoolean(2, selectedCircuit.getCI_disponible());
            stmt.setInt(3, selectedCircuit.getCI_nbVoituresMax());
            stmt.setInt(4, selectedCircuit.getCI_id());
            stmt.execute();
        } catch (SQLException var5) {
            throw new RuntimeException(var5);
        }
    }

}
