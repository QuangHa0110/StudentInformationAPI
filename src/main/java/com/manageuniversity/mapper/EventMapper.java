package com.manageuniversity.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.manageuniversity.dto.EventDTO;
import com.manageuniversity.entity.Event;

@Mapper
public interface EventMapper {
	
	Event eventDTOToEvent(EventDTO eventDTO);
	
	@Mapping(source = "classes.id", target = "classId")
	EventDTO eventToEventDTO(Event event);
	
	List<EventDTO> listEventToListEventDTO(List<Event> list);
	
	
	

}
