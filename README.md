# ATV2

Baseado no programa exemplo das páginas 67 à 69 do e-book, crie uma classe para manipular o banco de dados. 

Esta classe deve deve possuir:

- atributos necessários para fazer a conexão com o banco de dados;
- método construtor ;
- método para fazer a conexão com o banco de dados;
- método para fechar a conexão com o banco de dados;
- método para inserir um novo registro na tabela AGENDA do banco de dados;
- método para excluir um registro da tabela AGENDA do banco de dado (exclusão pelo código);
- método para alterar um registro da tabela Agenda do banco de dados (alteração pelo código);
- método para mostrar na tela todos os registros presentes na tabela AGENDA do banco de dados. 

Use o programa em anexo (BancoDeDados.java) para implementar a classe. 

Atualize o programa de modo a utilizar a classe toda vez que for necessário se conectar, desconectar, inserir, excluir ou mostrar dados presentes no banco de dados.

```
/*
PROGRAMA EXEMPLO DO E-BOOK PÁGINAS 67 A 69

Este exemplo usa o BD MYSQL.

Este programa manipula uma tabela com a seguinte configuração:

Nome da Tabela: AGENDA
Atributos:
   CODIGO Integer(11) 
   NOME Varchar(100) 
   TELEFOME Varchar(50)
   EMAIL Varchar(100)
Chave Primária: CODIGO

SQL para criaçao da tabela:
    CREATE TABLE AGENDA(
    CODIGO integer(11) NOT NULL AUTO_INCREMENT, 
    NOME varchar(100) DEFAULT NULL, 
    TELEFONE varchar(50) DEFAULT NULL, 
    EMAIL varchar(100) NOT NULL,
    PRIMARY KEY (CODIGO)
    );
*/

import java.sql.*;
import java.util.*;

class BancoDeDadosATV2 {

    public static void main(String[] args) {

        String banco = "jdbc:mysql://localhost:3306/Estudo_Java"; // Coloque a porta local de acesso ao BD e o esquema (se tiver)
        String usuario = "root"; // Coloque o usuário do banco aqui 
        String senha = "amoojava"; // Senha do usuário no banco
        

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
                    
                        ResultSet rs = stmt.executeQuery("select * from agenda;");
                    
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
```