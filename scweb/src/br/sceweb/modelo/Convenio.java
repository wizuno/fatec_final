package br.sceweb.modelo;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Days;

public class Convenio {
	private int id;
	private String cnpj;
	private DateTime dataInicio;
	private DateTime dataTermino;
	Logger logger = Logger.getLogger(Convenio.class);

	public Convenio() {

	}

	public Convenio(int id, String cnpj, String dataInicio, String dataTermino) {
		setId(id);
		setCNPJ(cnpj);
		setDataInicio(dataInicio);
		setDataTermino(dataTermino);
		dataInicialMaiorDataFinal();
	}
	public Convenio(String cnpj, String dataInicio, String dataTermino) {
		setId(id);
		setCNPJ(cnpj);
		setDataInicio(dataInicio);
		setDataTermino(dataTermino);
		dataInicialMaiorDataFinal();
	}
    public void setId(int id) {
    	this.id = id;
    }
	public String getCNPJ() {
		return cnpj;
	}

	/*
	 * atribui o cnpj - vefica se o cnpj tem no minimo 14 caracteres
	 */
	public void setCNPJ(String cnpj) {
		if (cnpj.length() == 14) {
			this.cnpj = cnpj;
		} else
			throw new IllegalArgumentException("CNPJ inválido!");

	}

	public DateTime getDataInicio() {
		return dataInicio;
	}

	public DateTime getDataTermino() {
		return dataTermino;
	}

	/**
	 * atribui a data de inicio da vigência de um convenio
	 * 
	 * @param dataInicio
	 *            - data no formato dd/mm/aaaa
	 * @throws IllegalArgumentException
	 */
	public void setDataInicio(String dataInicio) throws IllegalArgumentException {
		logger.info("data inicio = " + dataInicio);
		if (validaData(dataInicio)) {
			this.dataInicio = new DateTime(Integer.parseInt(dataInicio.substring(6, 10)),
					Integer.parseInt(dataInicio.substring(3, 5)), Integer.parseInt(dataInicio.substring(0, 2)), 0, 0);
		} else {
			throw new IllegalArgumentException("data invalida");
		}
	}

	/**
	 * atribui a data de inicio da vigência de um convenio
	 * 
	 * @param dataInicio
	 *            - data no formato dd/mm/aaaa
	 * @throws IllegalArgumentException
	 */
	public void setDataTermino(String dataTermino) throws IllegalArgumentException {
		logger.info("data termino = " + dataTermino);
		if (validaData(dataTermino)) {
			this.dataTermino = new DateTime(Integer.parseInt(dataTermino.substring(6, 10)),
					Integer.parseInt(dataTermino.substring(3, 5)), Integer.parseInt(dataTermino.substring(0, 2)), 0, 0);
		} else {
			throw new IllegalArgumentException("data invalida");
		}
	}

	/**
	 * valida o formato da data
	 * 
	 * @param data
	 *            no formato dd/MM/yyyy
	 * @return true se a data estiver no formato valido e false para formato
	 *         invalido
	 */
	public boolean validaData(String data) {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		df.setLenient(false); //
		try {
			df.parse(data); // data válida
			return true;
		} catch (ParseException ex) {
			logger.error("Erro na validacao de data - " + ex.getMessage());
			return false;
		}
	}

	public void dataInicialMaiorDataFinal() {
		Days d = Days.daysBetween(getDataInicio(), getDataTermino());
		logger.info("quantidade de dias entre as datas: " + d.getDays());
		if(d.getDays()<0)
			throw new IllegalArgumentException("Data inicial maior que data final!");;
		
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Convenio other = (Convenio) obj;
		if (cnpj == null) {
			if (other.cnpj != null) {
				return false;
			}
		} else if (!cnpj.equals(other.cnpj)) {
			return false;
		}
		if (dataInicio == null) {
			if (other.dataInicio != null) {
				return false;
			}
		} else if (!dataInicio.equals(other.dataInicio)) {
			return false;
		}
		if (dataTermino == null) {
			if (other.dataTermino != null) {
				return false;
			}
		} else if (!dataTermino.equals(other.dataTermino)) {
			return false;
		}
		return true;
	}
}
