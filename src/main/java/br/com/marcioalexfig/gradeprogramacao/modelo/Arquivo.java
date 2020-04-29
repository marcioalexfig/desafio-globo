package br.com.marcioalexfig.gradeprogramacao.modelo;

import java.util.Date;

/**
 * POJO responsável por representar a estrutura de dados dos arquivos
 * a serem manipulados.
 * @author alex
 *
 */
public class Arquivo {
	
	private String type;
	
	private String title;
	
	private Integer duration;
	
	private Date startTime;
	
	private Date endTime;

	public Arquivo() {}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	

}
