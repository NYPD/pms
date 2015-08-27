package services.ntr.pms.dao;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

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
import services.ntr.pms.model.information.CarouselNews;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={ApplicationConfiguration.class, EmbeddedDataSourceConfiguration.class})
@ActiveUnitProfile
@Transactional
public class NewsCarouselDAOTest
{
	@Autowired
	private NewsCarouselDAO newsCarouselDAO;

	@Before
	public void setUp() throws Exception
	{}

	@Test
	public void shouldAddNews() {
		CarouselNews carouselNews = new CarouselNews();
		carouselNews.setClanId(1000007315);
		carouselNews.setCreatedByAccountId(1001167560);
		carouselNews.setModifiedByAccountId(1001167560);
		carouselNews.setTitle("");
		carouselNews.setText("Other NTR news");
		carouselNews.setLargeBannerUrl("http://placehold.it/1140x300/16a085/ffffff&text=Other%20News");
		carouselNews.setMediumBannerUrl("http://placehold.it/940x300/16a085/ffffff&text=Other%20News");
		carouselNews.setSmallBannerUrl("http://placehold.it/720x300/16a085/ffffff&text=Other%20News");
		carouselNews.setIconImageUrl("http://placehold.it/64x64/16a085/ffffff&text=Other%20News");
		carouselNews.setBannerBackgroundColor("");
		carouselNews.setBannerTextColor("");
		carouselNews.setGlobal(false);
		carouselNews.setActive(true);
		newsCarouselDAO.addNews(carouselNews);

		int newsCarouselId = carouselNews.getNewsCarouselId();
		assertThat(newsCarouselId, is(4));
		
	}
	@Test
	public void shouldGetAllClanNewsIncludingGlobalNews()
	{
		long clanId = 1000007315;
		List<CarouselNews> carouselNews = newsCarouselDAO.getClanNews(clanId);

		int numberOfCarouselNews = carouselNews.size();
		assertThat(numberOfCarouselNews, is(3));
	}
	@Test
	public void shouldGetOnlyActiveClanNewsIncludingGlobalNews()
	{
		long clanId = 1000007315;
		List<CarouselNews> carouselNews = newsCarouselDAO.getActiveClanNews(clanId);

		int numberOfCarouselNews = carouselNews.size();
		assertThat(numberOfCarouselNews, is(2));
	}
	@Test
	public void shouldRemoveClanNews()
	{
		long clanId = 1000007315;
		int newsCarouselId = 3;
		
		newsCarouselDAO.removeNews(newsCarouselId);
		
		List<CarouselNews> carouselNews = newsCarouselDAO.getClanNews(clanId);

		int numberOfCarouselNews = carouselNews.size();
		assertThat(numberOfCarouselNews, is(2));
	}
	@Test
	public void shouldGetClanNewsById()
	{
		long clanId = 1000007315;
		int newsCarouselId = 3;
		
		CarouselNews carouselNews = newsCarouselDAO.getClanNewsById(clanId, newsCarouselId);
		String text = carouselNews.getText();

		assertThat(text, is("NTR None Active News"));
	}
	@Test
	public void shouldGetNewsById()
	{
		int newsCarouselId = 1;
		
		CarouselNews carouselNews = newsCarouselDAO.getNewsById(newsCarouselId);
		String text = carouselNews.getText();

		assertThat(text, is("Global News"));
	}
	@Test
	public void shouldGetGlobalNewsByIdEvenIfNotInSameClan()
	{
		long clanId = 1000007315;
		int newsCarouselId = 1;
		
		CarouselNews carouselNews = newsCarouselDAO.getClanNewsById(clanId, newsCarouselId);
		String text = carouselNews.getText();

		assertThat(text, is("Global News"));
	}
	@Test
	public void shouldUpdateClanNews()
	{		
		CarouselNews carouselNews = new CarouselNews();
		carouselNews.setClanId(1000007315);
		carouselNews.setCreatedByAccountId(1001167560);
		carouselNews.setModifiedByAccountId(1001167560);
		carouselNews.setId(2);
		carouselNews.setTitle("New Title");
		carouselNews.setText("New Welcome Text");
		carouselNews.setLargeBannerUrl("http://placehold.it/1140x300/16a085/ffffff&text=Welcome%20NTR");
		carouselNews.setMediumBannerUrl("http://placehold.it/940x300/16a085/ffffff&text=Welcome%20NTR");
		carouselNews.setSmallBannerUrl("http://placehold.it/720x300/16a085/ffffff&text=Welcome%20NTR");
		carouselNews.setIconImageUrl("http://placehold.it/64x64/16a085/ffffff&text=Welcome%20NTR");
		carouselNews.setBannerBackgroundColor("");
		carouselNews.setBannerTextColor("");
		carouselNews.setGlobal(false);
		carouselNews.setActive(true);
	
		newsCarouselDAO.updateNews(carouselNews);
		
		long clanId = 1000007315;
		int newsCarouselId = 2;
		
		CarouselNews storedCarouselNews = newsCarouselDAO.getClanNewsById(clanId, newsCarouselId);
		String text = storedCarouselNews.getText();

		assertThat(text, is("New Welcome Text"));
	}

}
