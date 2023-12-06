package br.edu.uniavan;

import br.edu.uniavan.entities.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Atv2 {
        public static void main(String[] args) {

        String banco = "jdbc:mysql://127.0.0.1/agenda"; // Coloque a porta local de acesso ao BD e o esquema (se tiver)
        String usuario = "agenda"; // Coloque o usuário do banco aqui 
        String senha = "agendapass"; // Senha do usuário no banco
        

        int opcao = 0, codigo, res;
        String nome, telefone, email, sql;

        Scanner sc = new Scanner(System.in);

        while (opcao != 5) {

            System.out.println("\nEntre com a opção ([1]Inserir [2]Deletar [3]Alterar [4]Listar [5]Fim)? ");
            opcao = sc.nextInt();

            switch (opcao) {
                case 1:
                    System.out.println("Entre com os dados para a inserção:");
                    System.out.print("\n\tNome: ");
                    nome = sc.next();
                    System.out.print("\n\tTelefone: ");
                    telefone = sc.next();
                    System.out.print("\n\tE-mail: ");
                    email = sc.next();

                    try {
                        Class.forName("com.mysql.cj.jdbc.Driver");

                        Connection con = (Connection) DriverManager.getConnection(banco, usuario, senha);

                        sql = "INSERT INTO agenda(codigo,nome,telefone,email) VALUES( NULL, ?, ?, ?);";

                        PreparedStatement ps = con.prepareStatement(sql);

                        ps.setString(1, nome);
                        ps.setString(2, telefone);
                        ps.setString(3, email);

                        ps.execute();
                        con.close();

                    } catch (ClassNotFoundException e) {
                        System.out.println("A classe do driver de conexão não foi encontrada!");
                    } catch (SQLException e) {
                        System.out.println("O comando SQL não pode ser executado!");
                    }
                    break;

                case 2:
                    System.out.println("Entre com código(id) a ser removido:");
                    codigo = sc.nextInt();

                    try {
                        Class.forName("com.mysql.cj.jdbc.Driver");

                        Connection con = (Connection) DriverManager.getConnection(banco, usuario, senha);

                        sql = "DELETE FROM agenda WHERE codigo=?;";

                        PreparedStatement ps = con.prepareStatement(sql);

                        ps.setInt(1, codigo);

                        res = ps.executeUpdate();
                        if (res == 1) {
                            System.out.println("Registro removido com sucesso!");
                        } else {
                            System.out.println("Erro: o registro não foi removido!");
                        }

                        con.close();

                    } catch (ClassNotFoundException e) {
                        System.out.println("A classe do driver de conexão não foi encontrada!");
                    } catch (SQLException e) {
                        System.out.println("O comando SQL não pode ser executado!");
                    }
                    break;

                case 3:
                    System.out.println("Entre com os dados para a alterar:");
                    System.out.print("\n\tCódigo: ");
                    codigo = sc.nextInt();
                    System.out.print("\n\tNovo Nome: ");
                    nome = sc.next();
                    System.out.print("\n\tNovo Telefone: ");
                    telefone = sc.next();
                    System.out.print("\n\tNovo E-mail: ");
                    email = sc.next();

                    try {
                        Class.forName("com.mysql.cj.jdbc.Driver");

                        Connection con = (Connection) DriverManager.getConnection(banco, usuario, senha);

                        sql = "UPDATE agenda SET nome=?, telefone=?, email=? WHERE codigo=?;";

                        PreparedStatement ps = con.prepareStatement(sql);

                        ps.setString(1, nome);
                        ps.setString(2, telefone);
                        ps.setString(3, email);
                        ps.setInt(4, codigo);

                        res = ps.executeUpdate();
                        if (res == 1) {
                            System.out.println("Registro alterado com sucesso!");
                        } else {
                            System.out.println("Erro: o registro não foi alterado!");
                        }

                        con.close();

                    } catch (ClassNotFoundException e) {
                        System.out.println("A classe do driver de conexão não foi encontrada!");
                    } catch (SQLException e) {
                        System.out.println("O comando SQL não pode ser executado!");
                    }
                    break;

                case 4: 
                    try {
                        Class.forName("com.mysql.cj.jdbc.Driver");
                    
                        Connection con = (Connection) DriverManager.getConnection(banco, usuario, senha);
                    
                        Statement stmt = con.createStatement();
                    
                        ResultSet rs = stmt.executeQuery("SELECT * FROM agenda");
                    
                        while (rs.next()) {
                            System.out.println("Código: " + rs.getString("codigo"));
                            System.out.println("Nome: " + rs.getString("nome"));
                            System.out.println("Telefone: " + rs.getString("telefone"));
                            System.out.println("E-mail: " + rs.getString("email"));
                            System.out.println();
                        }
                    
                        stmt.close();
                        con.close();
                
                    } catch (ClassNotFoundException e) {
                        System.out.println("A classe do driver de conexão não foi encontrada!");
                    } catch (SQLException e) {
                        System.out.println("O comando SQL não pode ser executado!");
                    }
                    break;
                
                case 5:
                    break;
                    
                default:
                    System.out.println("Erro: opção inválida!");
                    break;
            }
            System.out.println();
        }
    }
}
