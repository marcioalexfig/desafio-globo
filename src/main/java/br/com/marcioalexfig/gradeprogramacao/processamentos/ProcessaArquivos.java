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

import br.com.marcioalexfig.gradeprogramacao.enumerador.TipoEnum;
import br.com.marcioalexfig.gradeprogramacao.modelo.Arquivo;
import br.com.marcioalexfig.gradeprogramacao.util.FileUtils;
import br.com.marcioalexfig.gradeprogramacao.util.TimeUtils;

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
		
		@Value("${txt.nome.saida}")
		private String TXTNOMESAIDA;
		
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
				/**limita a leitura a arquivos com a EXTENSAO pré-definida, outros arquivos na pasta são ignorados*/
				if(ext!=null && ext.toUpperCase().equals(EXTENSAO)) {
					/**Ler arquivo de texto*/ 
					List<Arquivo> listaArquivos = FileUtils.lerArquivo(nome, TXTORIGEM);
					List<String> listaResposta = new ArrayList<String>();
					if( listaArquivos.size() > 0 ) {
						listaResposta = this.responderPerguntas(listaArquivos);
						FileUtils.gravarArquivo(TXTNOMESAIDA+"."+EXTENSAO, TXTPROCESSADO, listaResposta);
					}
					/**Mover arquivo texto para processados*/
					FileUtils.moverArquivo(nome, TXTORIGEM, TXTPROCESSADO);
				}
			}
	    }
		
		/**
		 *	- Cada resposta é dada em uma única linha
		 *	- Formato: `A <Q_região> <hora> <S_região> <nome do programa>`
		 *	- Quando não houver programaçao disponível: `A <Q_região> <hora> noise`
		 *	- Exemplo: Bom dia DF é o programa que passa às 6 da manhã no DF -> `A "DF" 06:00 "DF" "Bom Dia DF"`
		 *	- Exemplo: Não existe um programa passando às 3 da manhã no DF -> `A "DF" 03:00 noise`
		 * @param entrada
		 * @return
		 */
		private List<String> responderPerguntas(List<Arquivo> lista) {
			List<Arquivo> cadastros = this.retornaCadastros(lista);
			List<String> respostas = new ArrayList<String>();
			List<Arquivo> perguntas = this.retornaPerguntas(lista);

			for(Arquivo pergunta : perguntas) {
				boolean encontrou = false;
				for(Arquivo arq : cadastros) {
					if(pergunta.getQuestionTime()!=null
							&& arq.getStartTime()!=null
							&& arq.getEndTime()!=null
							&& arq.getRegion().equals(pergunta.getRegion()) 
							&& TimeUtils.checkTime(pergunta.getQuestionTime(), arq.getStartTime(), arq.getEndTime())) {
						encontrou = true;
						respostas.add("A "+pergunta.getRegion()+" "+TimeUtils.dateToString(pergunta.getQuestionTime()) +" "+arq.getTitle());
					}
				}
				if(!encontrou) respostas.add("A "+pergunta.getRegion()+" "+TimeUtils.dateToString(pergunta.getQuestionTime())+" noise");
			};
			return respostas;
		}
		
		/**
		 * 
		 * @param lista
		 * @return
		 */
		private List<Arquivo> retornaCadastros(List<Arquivo> lista) {
			List<Arquivo> cadastros = new ArrayList<Arquivo>();
			lista.forEach(cadastro -> {
				if (cadastro.getType().equals(TipoEnum.CADASTRO.getDescricao())) {
					cadastros.add(cadastro);
				}
			});
			return cadastros;
		}
		/**
		 * 
		 * @param lista
		 * @return
		 */
		private List<Arquivo> retornaPerguntas(List<Arquivo> lista) {
			List<Arquivo> perguntas = new ArrayList<Arquivo>();
			lista.forEach(pergunta -> {
				if (pergunta.getType().equals(TipoEnum.PERGUNTA.getDescricao())) {
					perguntas.add(pergunta);
				}
			});
			return perguntas;
		}
		

}
