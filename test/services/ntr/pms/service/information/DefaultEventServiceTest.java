package services.ntr.pms.service.information;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import services.ntr.pms.annotation.ActiveUnitProfile;
import services.ntr.pms.configuration.ApplicationConfiguration;
import services.ntr.pms.configuration.EmbeddedDataSourceConfiguration;
import services.ntr.pms.model.information.Event;
import services.ntr.pms.model.util.TimeFrame;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={ApplicationConfiguration.class, EmbeddedDataSourceConfiguration.class})
@Transactional
@ActiveUnitProfile
public class DefaultEventServiceTest {

	@Autowired
	private EventService eventService;
	
	private static final long ONE_DAY_IN_MILLI = 86400000L;;
	
	@Test
	public void shouldAddEvent() {
		
		Event event = new Event();
		event.setClanId(1000007315L);
		event.setType("Practice");
		event.setStartTime(1406520000000L);//7-28-2014 at midnight
		event.setImageUrl("http://www.epochconverter.com/img/epochconverter-2x.png");
		
		eventService.addEvent(event);
		
		int id = event.getId();
		 
		assertThat(id, is(not(1)));
	}
	
	@Test
	public void shouldGetUpcomingEvent(){
		
		addEventFiveDayInTheFeature();
		List<Event> events = eventService.getUpcomingEvents(1000007315L);
		
		
		int numberOfUpcomingEvent = events.size();	 
		assertThat(numberOfUpcomingEvent, is(1));
	}


	@Test
	public void shouldGetPastEvent(){
		
		addEventFiveDayInThePast();
		
		List<Event> events = eventService.getPastEvents(1000007315L);
			
		int numberOfUpcomingEvent = events.size();
		assertThat(numberOfUpcomingEvent, is(1));
	}
	
	@Test
	public void shouldGetClanEventById(){
		
		int eventId = addEventFiveDayInTheFeature();
		Event event = eventService.getClanEventById(1000007315L, eventId);	
			
		int id = event.getId();
		String type = event.getType();
				
		assertThat(id, is(eventId));
		assertThat(type, is("Practice"));
	}
	
	@Test
	public void shouldGetEventsByTimeFrame(){
		
		addEventBaseOnCurrentTime(-1);
		addEventBaseOnCurrentTime(1);
		addEventBaseOnCurrentTime(1);
		addEventBaseOnCurrentTime(2);
		
		long currentTimeMillis = System.currentTimeMillis();
		long eventTimeOneDayInThePast = currentTimeMillis + (ONE_DAY_IN_MILLI  * -2);
		long eventTimeOneDayInTheFeature = currentTimeMillis + (ONE_DAY_IN_MILLI  * 3);
		
		TimeFrame timeFrame = new TimeFrame();
		timeFrame.setStartTime(eventTimeOneDayInThePast);
		timeFrame.setEndTime(eventTimeOneDayInTheFeature);
		
		List<Event> events = eventService.getEventsByTimeFrame(1000007315L, timeFrame);
			
		int numberOfUpcomingEvent = events.size();
		assertThat(numberOfUpcomingEvent, is(4));
	}
	@Test
	public void shouldUpdateClanEvent()
	{
		int eventId = addEventFiveDayInTheFeature();
		Event event = eventService.getClanEventById(1000007315L, eventId);
		event.setType("Other");
		event.setImageUrl("http://eventicon.com");
		
		eventService.updateClanEvent(event);
		event = eventService.getClanEventById(1000007315L, eventId);
		
		String type = event.getType();
		String imageUrl = event.getImageUrl();
		
		assertThat(type, is("Other"));
		assertThat(imageUrl, is("http://eventicon.com"));
	}
	@Test
	public void shouldDeleteClanEvent()
	{	
		addEventFiveDayInTheFeature();
		int eventIdTwo = addEventFiveDayInTheFeature();
		
		long clanId = 1000007315L;
		int eventId = eventIdTwo;
		
		eventService.deleteClanEvent(clanId, eventId);
		
		List<Event> events = eventService.getUpcomingEvents(1000007315L);
		
		int numberOfUpcomingEvent = events.size();	 
		assertThat(numberOfUpcomingEvent, is(1));
	}
	private int addEventFiveDayInTheFeature()
	{
		int numberOfDays = 5;
		int eventId = addEventBaseOnCurrentTime(numberOfDays);
		return eventId;
	}
	
	private int addEventFiveDayInThePast()
	{
		int numberOfDays = -5;
		int eventId = addEventBaseOnCurrentTime(numberOfDays);
		return eventId;
	}
	private int addEventBaseOnCurrentTime(int numberOfDays)
	{
		long currentTimeMillis = System.currentTimeMillis();
		long eventTimeFiveDayInThePast = currentTimeMillis + (ONE_DAY_IN_MILLI  * numberOfDays);
		long clanId = 1000007315L;
		
		Event event = new Event();
		event.setClanId(clanId);
		event.setType("Practice");
		event.setStartTime(eventTimeFiveDayInThePast);
		event.setImageUrl("http://www.epochconverter.com/img/epochconverter-2x.png");
		
		eventService.addEvent(event);
		
		int eventId = event.getEventId();
		return eventId;
	}

}
