package services.ntr.pms.service.history;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import services.ntr.pms.annotation.ActiveUnitProfile;
import services.ntr.pms.configuration.ApplicationConfiguration;
import services.ntr.pms.configuration.EmbeddedDataSourceConfiguration;
import services.ntr.pms.model.history.CallerMapFrequency;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={ApplicationConfiguration.class, EmbeddedDataSourceConfiguration.class})
@Transactional
@ActiveUnitProfile
public class CallerMapFrequencyServiceTest {
	
	@Autowired
	private CallerMapFrequencyService callerMapFrequencyService;
	
	@Before
	public void setUp() throws Exception
	{}

	@Test
	public void shouldGetCallerMapFrequncyList()
	{
		
		List<CallerMapFrequency> callerMapFrequencies = callerMapFrequencyService.getCallerMapFrequencies(1000007315);
		
		int numberOfCallerMapFrequencies = callerMapFrequencies.size();
		
		assertThat(numberOfCallerMapFrequencies, is(3));
	}

}
