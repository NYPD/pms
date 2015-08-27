package services.ntr.pms.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import services.ntr.pms.annotation.DefaultDatabase;
import services.ntr.pms.model.information.Event;
import services.ntr.pms.model.util.TimeFrame;

@DefaultDatabase
public interface EventDAO {

	void addEvent(Event event);
	
	List<Event> getUpcomingEvents(@Param("clanId") long clanId);
	
	List<Event> getPastEvents(@Param("clanId") long clanId);
	
	List<Event> getEventsByTimeFrame(@Param("clanId") long clanId, @Param("timeFrame") TimeFrame timeFrame);
	
	Event getClanEventById(@Param("clanId") long clanId, @Param("eventId") int eventId);
	
	void updateClanEvent(Event event);
	
	void deleteClanEvent(@Param("clanId") long clanId, @Param("eventId") int eventId);
}
