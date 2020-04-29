package br.com.marcioalexfig.gradeprogramacao.util;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.marcioalexfig.gradeprogramacao.processamentos.ProcessaArquivos;

/**
 * Esta classe é responsável por manipular unidades de tempo
 * @author alex
 *
 */
public class TimeUtils {
	private static final Logger logger = LoggerFactory.getLogger(ProcessaArquivos.class);

	public static Integer tempoTotal (LocalTime tempoInicial, LocalTime tempoFinal) {
		Duration duracao = Duration.between(tempoInicial, tempoFinal);
		String duracaoFormatada = DurationFormatUtils.formatDuration(duracao.toMillis(), "ss.SS"); 
		return Integer.valueOf(duracaoFormatada.substring(0, duracaoFormatada.indexOf('.')));
	}

	public static Date localTimeToDate(LocalTime local) {
		Instant instant = local.atDate(LocalDate.now()).
		        atZone(ZoneId.systemDefault()).toInstant();
		Date time = Date.from(instant);
		return time;
	}
	
	public static LocalTime statTime(String linha) {
		return LocalTime.parse(linha.substring(17, 25));
	}
	
	public static LocalTime endTime(String linha) {
		return LocalTime.parse(linha.substring(40, 48));
	}
}
