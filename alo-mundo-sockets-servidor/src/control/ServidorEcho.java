package control;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorEcho {

	private ServerSocket sckServidor;

	public ServidorEcho() throws IOException {
		this.sckServidor = new ServerSocket(4000);

		for (;;) {
			Socket sckEcho;
			InputStream canalEntrada;
			OutputStream canalSaida;
			BufferedReader entrada;
			PrintWriter saida;

			sckEcho = this.sckServidor.accept();
			canalEntrada = sckEcho.getInputStream();
			canalSaida = sckEcho.getOutputStream();
			entrada = new BufferedReader(new InputStreamReader(canalEntrada));
			saida = new PrintWriter(canalSaida, true);

			while (true) {
				String linhaPedido = entrada.readLine();

				if (linhaPedido == null || linhaPedido.length() == 0)
					break;

				String resposta = processarPedido(linhaPedido);
				saida.println(resposta);
			}
			sckEcho.close();
		}
	}
	private String processarPedido(String pedido) {
		String[] partes = pedido.split(" ", 2);
		if (partes.length != 2) {
			return "Formato inválido !";
		}

		try {
			double valor = Double.parseDouble(partes[0]);
			String tipo = partes[1].toUpperCase();

			switch (tipo) {
				case "C":
					double fahrenheit = (valor * 9/5) + 32;
					return "Fahrenheit: " + fahrenheit;
				case "F":
					double celsius = (valor - 32) * 5/9;
					return "Celsius: " + celsius;
				default:
					return "Tipo inválido. Use 'C' para Celsius ou 'F' para Fahrenheit.";
			}
		} catch (NumberFormatException e) {
			return "Número inválido.";
		}
	}

}
