package services.ntr.pms.service.information;

import java.util.List;

import services.ntr.pms.model.information.Event;
import services.ntr.pms.model.util.TimeFrame;

public interface EventService {

	void addEvent(Event event);
	void putEvent(Event event);
	void updateClanEvent(Event event);
	void deleteClanEvent(long clanId, int eventId);

	List<Event> getUpcomingEvents(long clanId);
	List<Event> getPastEvents(long clanId);
	List<Event> getEventsByTimeFrame(long clanId, TimeFrame timeFrame);

	Event getClanEventById(long clanId, int eventId);
	
}
