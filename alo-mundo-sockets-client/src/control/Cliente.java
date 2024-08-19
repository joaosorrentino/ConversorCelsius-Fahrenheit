package control;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {

    public static void main(String[] args) {

        Socket socket;
        InputStream canalEntrada;
        OutputStream canalSaida;
        BufferedReader entrada;
        PrintWriter saida;

        try {
            socket = new Socket("127.0.0.1", 4000);

            canalEntrada = socket.getInputStream();
            canalSaida = socket.getOutputStream();

            entrada = new BufferedReader(new InputStreamReader(canalEntrada));
            saida = new PrintWriter(canalSaida, true);

            Scanner leitor = new Scanner(System.in);
            System.out.println("Digite o valor da temperatura:");
            String valor = leitor.nextLine();

            System.out.println("Digite 'C' para Celsius ou 'F' para Fahrenheit:");
            String unidade = leitor.nextLine().toUpperCase();

            if (!unidade.equals("C") && !unidade.equals("F")) {
                System.out.println("Unidade inválida. Use 'C' para Celsius ou 'F' para Fahrenheit.");
                return;
            }

            String mensagem = valor + " " + unidade;
            saida.println(mensagem);

            String resultado = entrada.readLine();
            System.out.println(resultado);

            socket.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
