package br.com.marcioalexfig.gradeprogramacao.util;

import java.text.SimpleDateFormat;
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

	public static String dateToString(Date tempo) {
		SimpleDateFormat f = new SimpleDateFormat("HH:mm");  
		return f.format(tempo); 
	}
	
	public static Date localTimeToDate(LocalTime local) {
		Instant instant = local.atDate(LocalDate.now()).
		        atZone(ZoneId.systemDefault()).toInstant();
		Date time = Date.from(instant);
		return time;
	}
	
	public static LocalTime statTime(String linha) {
		return LocalTime.parse(linha.substring(linha.lastIndexOf('"')+2, linha.lastIndexOf('"')+7));
	}
	
	public static LocalTime endTime(String linha) {
		return LocalTime.parse(linha.substring(linha.lastIndexOf('"')+8,linha.lastIndexOf('"')+13 ));
	}
	
	public static LocalTime questionTime(String linha) {
		return LocalTime.parse(linha.substring(7,12));
	}

	public static boolean checkTime(Date questionTime, Date startTime, Date endTime) {
		return ( ! questionTime.before( startTime ) ) && ( questionTime.before( endTime ) ) ; 
	}
}
