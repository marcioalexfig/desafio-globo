package br.com.marcioalexfig.gradeprogramacao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.junit.Before;
import org.junit.jupiter.api.Test;
/**
 * Classe responsável pelos testes necessários ao funcionamento da aplicação
 * @author alex
 *
 */



class GradeDeProgramacaoApplicationTests {
	private String PASTAORIGEM;
	private String PASTAPROCESSADO;
	private String EXTENSAO;

	/**
	 * Carrega as variaveis do arquivo application.properties
	 */
	@Before
	void carregarVariaveis() {
		Properties props = new Properties();
        FileInputStream file;
		try {
			file = new FileInputStream("./src/main/resources/application.properties");
			props.load(file);
			PASTAORIGEM = props.getProperty("txt.origem");
			PASTAPROCESSADO = props.getProperty("txt.processado");
			EXTENSAO = props.getProperty("txt.extensao");

		} catch (IOException e) {
			assertNull(e);
		}
	} 
	
	@Test
	public void verificaVariaveis() {
		assertNotEquals("",PASTAORIGEM);
		assertNotEquals("", PASTAPROCESSADO);
		assertNotEquals("", EXTENSAO);
	}
	


}
