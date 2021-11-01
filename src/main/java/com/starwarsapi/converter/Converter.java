package com.starwarsapi.converter;

import org.modelmapper.ModelMapper;

public class Converter {

	private Converter() {
	}

	public static <T, E> T convert(E source, Class<T> destination) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(source, destination);
	}
}