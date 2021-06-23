import java.io.*;
import java.util.Scanner;

import javax.swing.JFileChooser;

public class Stocker {

	public static void main(String[] args) {
		Minerio vetMinerios [];
		String auxString;
		vetMinerios = lerArquivo();
		if (vetMinerios != null) {
			System.out.println("Vetor de Minerios carregado com sucesso!\n");
			
			auxString = listarMinerios(vetMinerios);
			System.out.println("Listagem dos Minerios\n"
								 + "�ndice; Min�rio; Opera��o; Kg\n"
								 + auxString);	
			alteraArquivo(vetMinerios);
		} else {
			System.out.println("ERRO ao carregar Vetor de Minerios!");
		}
		
		if (escreverArquivo(vetMinerios)) {
			System.out.println("Vetor de Minerios salvo com sucesso!\n");
		} else {
			System.out.println("ERRO no salvamento do Vetor de Minerios!\n");
		}
		
	}
	
	public static String escolherArquivo(){
		//retorna o caminho completo do arquivo selecionado
		JFileChooser arquivoPos = new JFileChooser();
		int resultado = arquivoPos.showOpenDialog(null);
		
		if (resultado == JFileChooser.CANCEL_OPTION){
			return null;
		} else{
			return arquivoPos.getSelectedFile().getAbsolutePath();
		}	
	}
	
	public static String listarMinerios(Minerio [] vetMinerios) {
		String listagem = "";
		for (int ind = 0; ind < vetMinerios.length; ind++) {
			listagem += vetMinerios[ind].toString() + "\n";
		}
		return listagem;
	}
	
	public static Minerio [] lerArquivo () {
		String pathArquivo;
		Minerio [] vetMinerios = null;
		
		pathArquivo = escolherArquivo();
		
		try {
			FileInputStream arquivo = new FileInputStream (pathArquivo);
			InputStreamReader arqLeitura = new InputStreamReader(arquivo);
			BufferedReader bufferLeitura = new BufferedReader (arqLeitura);

			
			String registro,
				   vetCampos[];    //cada posi��o ter� um campo do registro
			int qtRegistros = 0;
			bufferLeitura.readLine();//l� e ignora o cabe�alho do arquivo
			bufferLeitura.mark(100000);//marca a posi��o do primeiro registro
			
			while (bufferLeitura.readLine() != null){
				qtRegistros++; //contar a quantidade de registros no arquivo
			}
			bufferLeitura.reset(); //retorna para a posi��o do primeiro registro,
								   //que foi marcado com o m�todo mark
			
			if(qtRegistros > 0) {
				vetMinerios = new Minerio [qtRegistros];
				
				for (int ind = 0; ind < qtRegistros; ind++) {
					registro = bufferLeitura.readLine(); //l� uma linha/registro do arquivo
					vetCampos = registro.split(";"); //divide a linha em campos
					
					vetMinerios[ind] = new Minerio (Long.parseLong(vetCampos[0]), //indice
												vetCampos[1], //nome do minerio
												(vetCampos[2]), //opera��o
												Long.parseLong(vetCampos[3]) ); //kg
				}
			}
			
			bufferLeitura.close();
			arqLeitura.close();
			arquivo.close();
			return vetMinerios;
		}
		catch(Exception excecao){
			System.out.println("Erro na leitura do arquivo!");
			return null;
		}
	}
	public static void alteraArquivo(Minerio[] vetMinerios) {
		int op = 0, minerio;
		String operacao;
		long quantidade;
		Scanner scop = new Scanner(System.in);
		Scanner scmenu = new Scanner(System.in);
		System.out.println("\n Bem-Vindo ao Stocker ");
		while(op!=3) {
			System.out.println("\n Opera��es: 1 = Adicionar | 2 = Remover | 3 = Encerrar o Programa ");
			System.out.println("\n Op��o:  ");
			op = scop.nextInt();
			if(op!=3) {
				System.out.println("\n Menu: 0 = Min�rio de Ferro | 1 = Carv�o | 2 = N�quel | 3 = A�o | 4 = Cobre | 4 = Mangan�s");
				System.out.println("\n Op��o:  ");
				minerio = scmenu.nextInt();
				Scanner scan = new Scanner(System.in);
				System.out.println("\n Informe a quantidade: ");
				quantidade = scan.nextLong();
				if(alteraMinerio(op, minerio, quantidade, vetMinerios)) {
					System.out.println("Opera��o realizada com sucesso!");
				} else {
					System.out.println("Opera��o n�o realizada!");
				}
			}
		}
	}
	public static boolean alteraMinerio(int op, int minerio, long quantidade, Minerio[] vetMinerios){
		if(op==1) {
			vetMinerios[minerio].somar(quantidade);
			return true;
		} else {
			if(vetMinerios[minerio].remover(quantidade)) {
				return true;
			} else {
				return false;
			}
		}
	}
	
	public static boolean escreverArquivo(Minerio[] vetMinerios) {
		String pathArquivo;
				
		pathArquivo = escolherArquivo();
		
		try {
			FileOutputStream arqEscrita = new FileOutputStream (pathArquivo);
			PrintWriter escreveArq = new PrintWriter(arqEscrita);
			
			escreveArq.println("�ndice;Min�rio;Opera��o;Kg"); //escreve o cabe�alho na primeira linha
			
			for (int ind = 0; ind < vetMinerios.length; ind++) {
				escreveArq.println(vetMinerios[ind].toString());
			}
			
			escreveArq.close();
			arqEscrita.close();
			return true;
		}
		catch(Exception excecao){
			return false;
		}
	}
}