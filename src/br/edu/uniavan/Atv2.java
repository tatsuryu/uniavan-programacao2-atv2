package br.edu.uniavan;

import br.edu.uniavan.entities.Database;
import java.sql.ResultSet;
import java.util.Scanner;

public class Atv2 {
    public static void main(String[] args) {

        Database db = new Database(
            "jdbc:mysql://localhost/agenda",
            "agenda",
            "agendapass"
        );

        int opcao = 0, codigo;
        String nome, telefone, email;

        Scanner sc = new Scanner(System.in);

        while (opcao != 5) {
            System.out.println(
                "\nEntre com a opção ([1]Inserir [2]Deletar [3]Alterar " +
                "[4]Listar [5]Fim)? "
            );
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

                    db.newRecord(nome, telefone, email);
                    break;
                case 2:
                    System.out.println("Entre com código(id) a ser removido:");
                    codigo = sc.nextInt();

                    db.deleteRecord(codigo);
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

                    db.updateRecord(codigo, nome, telefone, email);
                    break;
                case 4:
                    db.listRecords();
                    break;
                case 5:
                    break;
                default:
                    System.out.println("Erro: opção inválida!");
                    break;
            }
            System.out.println();
        }

        db.closeConnection();
    }
}
