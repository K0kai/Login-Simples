//Login_Simples feito em: 18/05/2024
// @K0kai (Luis Felipe)

import java.io.Console;
import java.io.FileWriter;
import java.io.IOException;

public class MainApp {

    public static void main(String[] args) throws Exception {
        Console entrada = System.console();
        String caminhoArquivo = "data.txt";
        String nome, senha, ultentrada;
        boolean sair = false;
        System.out.println("Ola! seja bem vindo(a), o que gostaria de fazer hoje?");
        while (sair == false) {
            System.out.println("\n1: Cadastrar-se\n2: Fazer Login\n3: Mudar senha\n4: Sair do programa");
            ultentrada = entrada.readLine();
            switch (ultentrada.charAt(0)) {
                case '1' -> {
                    try {
                        System.out.println("Digite o nome de usuario:");
                        armazenarEntrada(entrada.readLine("Nome: "), 1);
                        System.out.println("Agora digite a senha:");
                        armazenarEntrada(String.valueOf(entrada.readPassword("Senha: ")), 2);
                        System.out.println("Você foi cadastrado com sucesso!");
                    } catch (Exception e) {
                        System.out.println("Ocorreu um erro ao se cadastrar: " + e);
                    }
                }
                case '2' -> {
                    try {
                        System.out.println("Digite o nome de usuario: ");
                        nome = entrada.readLine("Nome: ");
                        System.out.println("Digite a senha: ");
                        senha = String.valueOf(entrada.readPassword("Senha: "));
                        if (Funcoes.logar(nome, senha, caminhoArquivo, "\\s*,\\s*") == true) {
                            System.out.println("Logado com sucesso!");
                            sair = true;
                        } else {
                            System.out.println("Nome de usuario ou senha incorretos.");
                        }
                    } catch (Exception e) {
                        System.out.println("Ocorreu um erro ao logar: " + e);
                    }
                }
                case '3' -> {
                    try {
                        System.out.println("Digite o nome de usuario: ");
                        nome = entrada.readLine("Nome: ");
                        System.out.println("Digite a senha atual: ");
                        senha = String.valueOf(entrada.readPassword("Senha: "));
                        if (Funcoes.mudarSenha(nome, senha, caminhoArquivo, "\\s*,\\s*") == true) {
                            System.out.println("Senha alterada com sucesso!");
                        } else {
                            System.out.println("Não foi possível mudar a senha");
                        }

                    } catch (Exception e) {
                        System.out.println("Ocorreu um erro ao logar: " + e);
                    }
                }
                case '4' -> {
                    System.out.println("Obrigado por usar meu sistema :)");
                    sair = true;
                }
                default -> {
                    System.out.println("Opção inválida.");
                }
            }
        }
    }

    private static void armazenarEntrada(String dado, int contador) {
        try {
            if (contador == 1) {
                try (FileWriter arquivo = new FileWriter("data.txt", true)) {
                    arquivo.write("\n" + dado + ",");
                }

            } else if (contador == 2) {
                try (FileWriter arquivo = new FileWriter("data.txt", true)) {
                    arquivo.write(dado);
                }
            }

        } catch (IOException e) {
            System.out.println("Não foi possível salvar o arquivo");
        }
    }
}
