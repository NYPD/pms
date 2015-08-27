package services.ntr.pms.dao;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import java.util.List;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
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
@ActiveUnitProfile
@Transactional
public class EventDAOTest {

	@Configuration
    static class Config {

		@Bean
		public DataSource getPmsDataSource() throws NamingException {	
			
			EmbeddedDatabase datasource = new EmbeddedDatabaseBuilder()
			.setType(EmbeddedDatabaseType.HSQL)
			.addScript("setup/create-pms-database.sql")
			.addScript("setup/dao/insert-event-data.sql")
			.build();
			
			return datasource;
		}
    }
	
	@Autowired
	private EventDAO eventDAO;
	private static final long ONE_DAY_IN_MILLI = 86400000L;;
	
	@Before
	public void setUp() throws Exception
	{}
	
	@Test
	public void shouldAddEvent(){
		
		Event event = new Event();
		event.setClanId(1000007315L);
		event.setType("Practice");
		event.setStartTime(1406520000000L);//7-28-2014 at midnight
		event.setImageUrl("http://www.epochconverter.com/img/epochconverter-2x.png");
		
		eventDAO.addEvent(event);
		
		int id = event.getId();
		assertThat(id, is(not(0)));
	}
	
	@Test
	public void shouldGetUpcomingEvent(){
		
		addEventFiveDaysInTheFuture();
		List<Event> events = eventDAO.getUpcomingEvents(1000007315L);
		
		
		int numberOfUpcomingEvent = events.size();	 
		assertThat(numberOfUpcomingEvent, is(1));
	}


	@Test
	public void shouldGetPastEvent(){
		
		addEventFiveDayInThePast();
		
		List<Event> events = eventDAO.getPastEvents(1000007315L);
			
		int numberOfUpcomingEvent = events.size();
		assertThat(numberOfUpcomingEvent, is(1));
	}
	
	@Test
	public void shouldGetClanEventById(){
		
		int eventId = addEventFiveDaysInTheFuture();
		Event event = eventDAO.getClanEventById(1000007315L, eventId);	
			
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
		
		List<Event> events = eventDAO.getEventsByTimeFrame(1000007315L, timeFrame);
			
		int numberOfUpcomingEvent = events.size();
		assertThat(numberOfUpcomingEvent, is(4));
	}
	@Test
	public void shouldUpdateClanEvent()
	{
		int eventId = addEventFiveDaysInTheFuture();
		Event event = eventDAO.getClanEventById(1000007315L, eventId);
		event.setType("Other");
		event.setImageUrl("http://eventicon.com");
		
		eventDAO.updateClanEvent(event);
		event = eventDAO.getClanEventById(1000007315L, eventId);
		
		String type = event.getType();
		String imageUrl = event.getImageUrl();
		
		assertThat(type, is("Other"));
		assertThat(imageUrl, is("http://eventicon.com"));
	}
	@Test
	public void shouldDeleteClanEvent()
	{
		
		addEventFiveDaysInTheFuture();
		addEventFiveDaysInTheFuture();
		
		long clanId = 1000007315L;
		int eventId = 2;
		
		eventDAO.deleteClanEvent(clanId, eventId);
		
		
		
		List<Event> events = eventDAO.getUpcomingEvents(1000007315L);
		
		
		int numberOfUpcomingEvent = events.size();	 
		assertThat(numberOfUpcomingEvent, is(1));
	}

	private int addEventFiveDaysInTheFuture() {
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
		
		eventDAO.addEvent(event);
		
		int eventId = event.getEventId();
		
		return eventId;
	}
	
}
