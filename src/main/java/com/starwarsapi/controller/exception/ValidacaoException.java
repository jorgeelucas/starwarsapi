package com.starwarsapi.controller.exception;

import java.util.ArrayList;
import java.util.List;

public class ValidacaoException extends ExceptionPadrao {

	private List<CampoMensagem> erros = new ArrayList<>();
	
	public ValidacaoException(Integer status, String msg, Long timestamp) {
		super(status, msg, timestamp);
	}

	public List<CampoMensagem> getErros() {
		return erros;
	}

	public void setErros(List<CampoMensagem> erros) {
		this.erros = erros;
	}

	public void addErros(CampoMensagem erro) {
		this.erros.add(erro);
	}
	
}