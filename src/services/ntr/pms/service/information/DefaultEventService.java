package services.ntr.pms.service.information;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import services.ntr.pms.dao.EventDAO;
import services.ntr.pms.model.information.Event;
import services.ntr.pms.model.util.TimeFrame;

@Service
public class DefaultEventService implements EventService {

	@Autowired
	private EventDAO eventDAO;
	
	@Override
	public List<Event> getUpcomingEvents(long clanId) {
		return eventDAO.getUpcomingEvents(clanId);
	}
	
	@Override
	public List<Event> getPastEvents(long clanId) {
		return eventDAO.getPastEvents(clanId);
	}
	
	@Override
	public List<Event> getEventsByTimeFrame(long clanId, TimeFrame timeFrame) {
		return eventDAO.getEventsByTimeFrame(clanId, timeFrame);
	}
	
	@Override
	public Event getClanEventById(long clanId, int eventId) {
		return eventDAO.getClanEventById(clanId, eventId);
	}
	
	@Override
	public void addEvent(Event event) {
		eventDAO.addEvent(event);
	}
	
	@Override
	public void updateClanEvent(Event event) {
		eventDAO.updateClanEvent(event);
	}
	
	@Override
	public void deleteClanEvent(long clanId, int eventId) {
		eventDAO.deleteClanEvent(clanId, eventId);
	}
	
	@Override
	public void putEvent(Event event) {
		
		boolean isEdit = event.getId() > 0;
		
		if(isEdit){
			updateClanEvent(event);
		}else{
			addEvent(event);
		}
		
	}
	

}
