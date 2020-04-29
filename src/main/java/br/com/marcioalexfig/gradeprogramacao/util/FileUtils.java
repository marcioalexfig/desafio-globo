package br.com.marcioalexfig.gradeprogramacao.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

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
       	
	        	/**identifica o tipo de linha e enviar para o metodo que trata do tipo de linha*/
	        	if(linha.substring(0,1).equals(TipoEnum.CADASTRO.getDescricao())) {
	        		System.out.println("CADASTRO");
	        	} else if (linha.substring(0,1).equals(TipoEnum.PERGUNTA.getDescricao())) {
	        		System.out.println("PERGUNTA");
	        	}
	        	
	        	//TODO - adicionar o obj arquivo na lista de retorno

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
	public static void gravarArquivo(String nomeArquivo, String pastaDestino) {
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
	
	private Arquivo tratarCadastro(String linha) {
		Arquivo arquivo = new Arquivo();
		//TODO - implementar
		return arquivo;
	}
	private Arquivo tratarPergunta(String linha) {
		Arquivo arquivo = new Arquivo();
		//TODO - implementar
		return arquivo;
	}
	private Arquivo tratarResposta(String linha) {
		Arquivo arquivo = new Arquivo();
		//TODO - implementar
		return arquivo;
	}
}
