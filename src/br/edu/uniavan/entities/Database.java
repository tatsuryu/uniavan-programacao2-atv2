package br.edu.uniavan.entities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Database {

    static {
        // Registrar o driver JDBC do MySQL
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(
                "Driver JDBC do MySQL não encontrado. Verifique se o conector MySQL está no classpath.", e
            );
        }
    }

    private Connection connection = null;

    public Database(String url, String usuario, String senha) {
        try {
            this.connection = (Connection) getConnection(url, usuario, senha);
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    private static Connection getConnection(String url, String usuario, String senha) throws SQLException {
        
        return DriverManager.getConnection(url, usuario, senha);
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public ResultSet query(String querySQL) {
        try {
            PreparedStatement statement = connection.prepareStatement(querySQL);
            return statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void newRecord(String nome, String telefone, String email) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO agenda(nome, telefone, email) VALUES(?, ?, ?)"
            );

            statement.setString(1, nome);
            statement.setString(2, telefone);
            statement.setString(3, email);

            boolean res = statement.execute();
            if (res) {
                System.out.println("Registro inserido com sucesso.");
            } else {
                System.out.println("Erro: o registro não foi inserido.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteRecord(int codigo) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                "DELETE FROM agenda WHERE codigo=?"
            );

            statement.setInt(1, codigo);
            
            int res = statement.executeUpdate();
            if (res == 1) {
                System.out.println("Registro excluido com sucesso.");
            } else {
                System.out.println("Erro: o registro não foi excluido.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateRecord(int codigo, String nome, String telefone, String email) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                "UPDATE agenda SET nome=?, telefone=?, email=? WHERE codigo=?"
            );

            statement.setString(1, nome);
            statement.setString(2, telefone);
            statement.setString(3, email);
            statement.setInt(4, codigo);

            int res = statement.executeUpdate();
            if (res == 1) {
                System.out.println("Registro alterado com sucesso.");
            } else {
                System.out.println("Erro: o registro não foi alterado.");
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void listRecords() {
        String response;
        try {
            ResultSet rs = query("SELECT * FROM agenda");
            
            System.out.printf(
                "%-5s | %-30s | %-20s | %-10s\n",
                "Id", "Nome", "Telefone", "eMail"
            );

            System.out.println(new String(new char[80]).replace('\0', '-'));

            while (rs.next()) {
                System.out.printf(
                    "%-5s | %-30s | %-20s | %-10s\n",
                    rs.getString("codigo"),
                    rs.getString("nome"),
                    rs.getString("telefone"),
                    rs.getString("email")
                );
            }
            
            System.out.println();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
