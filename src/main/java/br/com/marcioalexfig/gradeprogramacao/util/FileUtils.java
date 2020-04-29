package br.com.marcioalexfig.gradeprogramacao.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.marcioalexfig.gradeprogramacao.enumerador.TipoEnum;
import br.com.marcioalexfig.gradeprogramacao.modelo.Arquivo;

/**
 * Esta classe é responsável por manipular arquivos no sistema de arquivos
 * @author alex
 *
 */
public class FileUtils {
	private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);
	
	/**
	 * Método responsável por mover um arquivo de uma pasta para outra
	 * @param nomeArquivo
	 * @param pastaOrigem
	 * @param pastaDestino
	 */
	public static void moverArquivo(String nomeArquivo, String pastaOrigem, String pastaDestino) {
	    InputStream in;
	    OutputStream out;
	    try{
	        File entrada = new File(pastaOrigem+"//"+nomeArquivo);
	        File saida = new File(pastaDestino+"//"+nomeArquivo);
	        if(!saida.exists()){
	            if(!saida.getParentFile().exists()){
	                saida.getParentFile().mkdir();
	            }
	            saida.createNewFile();
	        }
	        in = new FileInputStream(entrada);
	        out = new FileOutputStream(saida);
	        byte[] buffer = new byte[1024];
	        int length;
	        while((length = in.read(buffer)) > 0 ){
	            out.write(buffer, 0 , length);
	        }
	        in.close();
	        out.close();
	        entrada.delete();
	    }catch(IOException e){
	    	logger.error(e.getLocalizedMessage());
	    }
	}
	
	/**
	 * Arquivo de entrada
	 * @param nomeArquivo
	 * @param pastaOrigem
	 * @param TYPE
	 * @layout:
	 * S "DF" "Bom dia DF" 06:01 07:29
	 * Q "RJ" 07:00
	 * A "RJ" 07:00 "RJ" "Bom dia RJ"
	 * A "GO" 06:50 noise
	 * @return
	 */
	public static List<Arquivo> lerArquivo(String nomeArquivo, String pastaOrigem) {
		List<Arquivo> arquivos = new ArrayList<Arquivo>();
	    try{
	        FileReader arq = new FileReader(pastaOrigem+"//"+nomeArquivo);
	        BufferedReader lerArq = new BufferedReader(arq);
	        String linha = lerArq.readLine();
	        while (linha != null) {
	        	Arquivo _arq = null;
	        	/**identifica o tipo de linha e enviar para o metodo que trata do tipo de linha*/
	        	if(linha.substring(0,1).equals(TipoEnum.CADASTRO.getDescricao())) {
	        		_arq = tratarCadastro(linha);
	        	} else if (linha.substring(0,1).equals(TipoEnum.PERGUNTA.getDescricao())) {
	        		_arq = tratarPergunta(linha);
	        	}
	        	arquivos.add(_arq);
	          linha = lerArq.readLine();
	        }
	        arq.close();
	    }catch(IOException e){
	    	logger.error(e.getLocalizedMessage());
	    }
	    return arquivos;
	}
	
	/**
	 * Arquivo de saida
	 * @param nomeArquivo
	 * @param pastaDestino
	 */
	public static void gravarArquivo(String nomeArquivo, String pastaDestino, List<String> conteudo) {
		List<String> linhas = tratarResposta(conteudo);
		
		//TODO - implementar
		
		
	    OutputStream out;
	    try{
	        File saida = new File(pastaDestino+"//"+nomeArquivo);
	        if(!saida.exists()){
	            if(!saida.getParentFile().exists()){
	                saida.getParentFile().mkdir();
	            }
	            saida.createNewFile();
	        }
	        
	        out = new FileOutputStream(saida);
	        byte[] buffer = new byte[1024];
	        int length = 0;
            out.write(buffer, 0 , length);
	        out.close();
	    }catch(IOException e){
	    	logger.error(e.getLocalizedMessage());
	    }
	}
	
	/**
	 *  - Cada registro é dado em uma única linha
	 *	- Formato: `S <região> <nome do programa> <início> <fim>`
	 *	- Exemplo, Bom Dia DF passa às 6 da manhã -> `S "DF" "Bom Dia DF" 06:00 07:30`
	 * @param linha
	 * @return
	 */
	private static Arquivo tratarCadastro(String linha) {
		Arquivo arquivo = new Arquivo();
		arquivo.setType(linha.substring(0,1));
		arquivo.setRegion(linha.substring(3,5));
		arquivo.setTitle(linha.substring(8,linha.lastIndexOf('"')));
		arquivo.setStartTime(TimeUtils.localTimeToDate(TimeUtils.statTime(linha)));
		arquivo.setEndTime(TimeUtils.localTimeToDate(TimeUtils.endTime(linha)));
		return arquivo;
	}
	
	/**
	 *	- Cada registro é dado em uma única linha
	 *	- Formato: `Q <região> <hora>`
	 *	- Exemplo: Qual programa passa no DF às 6 da manhã? -> `Q "DF" 06:00`
	 * @param linha
	 * @return
	 */
	private static Arquivo tratarPergunta(String linha) {
		Arquivo arquivo = new Arquivo();
		arquivo.setType(linha.substring(0,1));
		arquivo.setRegion(linha.substring(3,5));
		arquivo.setQuestionTime(TimeUtils.localTimeToDate(TimeUtils.questionTime(linha)));
		return arquivo;
	}
	
	private static List<String> tratarResposta(List<String> linhas) {
		//TODO - implementar
		return null;
	}
}
