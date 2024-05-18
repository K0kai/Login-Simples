
import java.io.BufferedReader;
import java.io.Console;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Funcoes {

    public static boolean logar(String nome, String senha, String caminhoArquivo, String delimitador) throws Exception {
        FileReader leitor = new FileReader(caminhoArquivo);
        String[] dados;
        try (BufferedReader leitorbuff = new BufferedReader(leitor)) {
            String linhaAtual;
            try {
                while ((linhaAtual = leitorbuff.readLine()) != null) {
                    dados = linhaAtual.split(delimitador);
                    if (dados.length > 1 && dados[0].equals(nome) && dados[1].equals(senha)) {
                        return true;
                    } else if (dados.length <= 1 && dados[0].equals(nome) && senha.equals("")) {
                        return true;
                    }
                }

            } catch (IOException e) {
                System.out.println("Ocorreu um erro: " + e);
            }
        }
        return false;
    }

    public static boolean mudarSenha(String nome, String senha, String caminhoArquivo, String delimitador) throws Exception {
        FileReader leitor = new FileReader(caminhoArquivo);
        String[] dados;
        String novaSenha;
        List<String> linhas;
        linhas = Files.readAllLines(Paths.get(caminhoArquivo));
        Console entrada = System.console();
        try (BufferedReader leitorbuff = new BufferedReader(leitor)) {
            String linhaAtual;
            int contadorLinha = 0;
            try {
                while ((linhaAtual = leitorbuff.readLine()) != null) {
                    dados = linhaAtual.split(delimitador);
                    contadorLinha++;
                    if (dados.length > 1 && dados[0].equals(nome) && dados[1].equals(senha)) {
                        System.out.println("Digite a nova senha: ");
                        novaSenha = String.valueOf(entrada.readPassword());
                        System.out.println("check");
                        linhas.set(contadorLinha - 1, nome + "," + novaSenha);
                        Files.write(Paths.get(caminhoArquivo), linhas, StandardCharsets.UTF_8);
                        return true;
                    } else if (dados.length <= 1 && dados[0].equals(nome) && senha.equals("")) {
                        System.out.println("Digite a nova senha: ");
                        novaSenha = String.valueOf(entrada.readPassword());
                        System.out.println("check");
                        linhas.set(contadorLinha - 1, nome + "," + novaSenha);
                        Files.write(Paths.get(caminhoArquivo), linhas, StandardCharsets.UTF_8);
                        return true;
                    }
                }

            } catch (IOException e) {
                System.out.println("Ocorreu um erro: " + e);
            }
        }
        return false;
    }
}
