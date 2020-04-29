package br.com.marcioalexfig.gradeprogramacao.processamentos;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.marcioalexfig.gradeprogramacao.modelo.Arquivo;
import br.com.marcioalexfig.gradeprogramacao.util.FileUtils;

/**
 * Classe responsável por encontrar, ler e processar arquivos de entrada
 * na grade de programação, assim como prover a saída para os questionamentos.
 * @author alex
 *
 */

@Component
@EnableScheduling
public class ProcessaArquivos {
	private static final Logger logger = LoggerFactory.getLogger(ProcessaArquivos.class);


		@Value("${txt.origem}")
		private String TXTORIGEM;
		
		@Value("${txt.processado}")
		private String TXTPROCESSADO;

		@Value("${txt.extensao}")
		private String EXTENSAO;
		
	
		private final long INTERVALO_PROCESSAMENTO = 1000;
		
		
		/**
		 * Método lê os arquivos de texto
		 */
		@Scheduled(fixedDelay = INTERVALO_PROCESSAMENTO) 
	    public void processaArquivos() {
			File file = new File(TXTORIGEM);
			File[] arquivos = file.listFiles();
			for (File fileTmp : arquivos) {
				String nome = fileTmp.getName();
				String ext = null;
				if(nome!=null)ext = nome.substring(nome.length()-3, nome.length());
				/**limita a leitura a arquivos TXT*/
				if(ext!=null && ext.toUpperCase().equals(EXTENSAO)) {
					/**Ler arquivo de texto*/ 
					List<Arquivo> listaArquivos = FileUtils.lerArquivo(nome, TXTORIGEM);
					List<Arquivo> listaResposta = new ArrayList<Arquivo>();
					if( listaArquivos.size() > 0 ) {
						listaResposta = this.responderPerguntas(listaArquivos);
					}
					/**Mover arquivo texto para processados*/
					FileUtils.moverArquivo(nome, TXTORIGEM, TXTPROCESSADO);
				}
			}
	    }
		
		private List<Arquivo> responderPerguntas(List<Arquivo> entrada) {
			List<Arquivo> respostas = new ArrayList<Arquivo>();
			//TODO - implementar
			return respostas;
					
		}
		

}
