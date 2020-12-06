package com.daw.dtos;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/* Se utiliza una configuración diferente de ModelMapper para recibir y para
 * mostrar los datos, para poder así configurarlos con las distintas estrategias
 * de emparejamiento que utiliza este mapeador y obtener un funcionamiento correcto
 * y adaptado a nuestro modelo
 * MatchingStrategy STRICT
 * MatchingStrategy STANDARD
 */


interface ModelMapperConfigurer {
	ModelMapper getModelMapper();
}

class ToModelModelMapperConfigurer implements ModelMapperConfigurer {
	
	private final ModelMapper MODEL_MAPPER;
	
	{
		MODEL_MAPPER = new ModelMapper();
		MODEL_MAPPER.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
	}
	
	@Override
	public ModelMapper getModelMapper() {
		return MODEL_MAPPER;
	}
}

class ToDtoModelMapperConfigurer implements ModelMapperConfigurer {
	
	private final ModelMapper MODEL_MAPPER;
	
	{
		MODEL_MAPPER = new ModelMapper();
		MODEL_MAPPER.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
	}
	
	@Override
	public ModelMapper getModelMapper() {
		return MODEL_MAPPER;
	}
}

public class ConverterDto {

	private static ConverterDto toModelInstance = null;
	private static ConverterDto toDtoInstance = null;
	
	private final ModelMapper MODEL_MAPPER;
	
	private ConverterDto(ModelMapperConfigurer mpc) {
		MODEL_MAPPER = mpc.getModelMapper();
	}
	
	public static ConverterDto getToModelInstance() {
		if (toModelInstance == null) toModelInstance = new ConverterDto(new ToModelModelMapperConfigurer());
		return  toModelInstance;
	}

	public static ConverterDto getToDtoInstance() {
		if (toDtoInstance == null) toDtoInstance = new ConverterDto(new ToDtoModelMapperConfigurer());
		return  toDtoInstance;
	}

	public <T> T map(Object source, Class<T> destinationClass) {
		return MODEL_MAPPER.map(source, destinationClass);
	}
	
	
	public <T> List<T> mapAll(Iterable<?> source, Class<T> destinationClass) {
		List<T> result = new ArrayList<>();
		for (Object data: source) {
			result.add(MODEL_MAPPER.map(data, destinationClass));
		}
		return result;
	}

	public <T> Page<T> mapAll(Page<?> source, Class<T> destinationClass) {
		List<T> result = new ArrayList<>();
		for (Object data: source.getContent()) {
			result.add(MODEL_MAPPER.map(data, destinationClass));
		}
		return new PageImpl<T>(result, source.getPageable(), source.getTotalElements());
	}	
	
}
