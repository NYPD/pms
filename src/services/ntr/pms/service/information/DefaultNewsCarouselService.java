package services.ntr.pms.service.information;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import services.ntr.pms.dao.APIRequestDAO;
import services.ntr.pms.dao.NewsCarouselDAO;
import services.ntr.pms.model.information.CarouselNews;
import services.ntr.pms.model.information.Player;

@Service
public class DefaultNewsCarouselService implements NewsCarouselService {

	@Autowired
	private NewsCarouselDAO newsCarouselDAO;
	@Autowired
	private APIRequestDAO apiRequestDAO;

	@Override
	public void addNews(CarouselNews carouselNews) {
		setDefaultBannerUrlsIfApplicable(carouselNews);
		newsCarouselDAO.addNews(carouselNews);
	}
	
	@Override
	public List<CarouselNews> getClanNews(long clanId) {
		
		List<CarouselNews> clanNews = newsCarouselDAO.getClanNews(clanId);
		List<Long> playerAccountIds = new ArrayList<>();
		
		for (CarouselNews carouselNews : clanNews) {
			
			long createdByAccountId = carouselNews.getCreatedByAccountId();
			long modifiedByAccountId = carouselNews.getModifiedByAccountId();
			
			playerAccountIds.add(createdByAccountId);
			playerAccountIds.add(modifiedByAccountId);
			
		}
		
		Map<Long, Player> playerInformationMap = apiRequestDAO.getPlayerInformationMap(playerAccountIds);
		
		for (CarouselNews carouselNews : clanNews) {
			
			long createdByAccountId = carouselNews.getCreatedByAccountId();
			long modifiedByAccountId = carouselNews.getModifiedByAccountId();
			
			Player createdByPlayer = playerInformationMap.get(createdByAccountId);
			Player ModifiedByPlayer = playerInformationMap.get(modifiedByAccountId);
			
			String createdByNickname = createdByPlayer.getNickname();
			String modifiedByNickname = ModifiedByPlayer.getNickname();
			
			carouselNews.setCreatedByNickname(createdByNickname);
			carouselNews.setModifiedByNickname(modifiedByNickname);
			
		}
		
		return clanNews;
	}
	
	@Override
	public List<CarouselNews> getActiveClanNews(long clanId) {
		return newsCarouselDAO.getActiveClanNews(clanId); //TODO we need nicknames up in hur
	}
	@Override
	public void removeNews(int newsCarouselId) {
		newsCarouselDAO.removeNews(newsCarouselId);
	}
	@Override
	public CarouselNews getClanNewsById(long clanId, int newsCarouselId) {
		return newsCarouselDAO.getClanNewsById(clanId, newsCarouselId);
	}
	@Override
	public CarouselNews getNewsById(int newsCarouselId) {
		return newsCarouselDAO.getNewsById(newsCarouselId);
	}
	@Override
	public void updateNews(CarouselNews carouselNews) {
		setDefaultBannerUrlsIfApplicable(carouselNews);
		newsCarouselDAO.updateNews(carouselNews);
	}
	@Override
	public void putNews(CarouselNews carouselNews) {
		int id = carouselNews.getId();
		boolean shouldAddNews = id == 0; 
		
		if(shouldAddNews) {
			long modifiedByAccountId = carouselNews.getModifiedByAccountId();
			carouselNews.setCreatedByAccountId(modifiedByAccountId);
			addNews(carouselNews);
		}
		else{
			updateNews(carouselNews);
		}
	}
	private void setDefaultBannerUrlsIfApplicable(CarouselNews carouselNews) {
		String largeBannerUrl = carouselNews.getLargeBannerUrl();
		String mediumBannerUrl = carouselNews.getMediumBannerUrl();
		String smallBannerUrl = carouselNews.getSmallBannerUrl();
		String iconImageUrl = carouselNews.getIconImageUrl();
		
		String placeHolderDomainUrl = "https://placeholdit.imgix.net/~text?";
		boolean largeBannerUrlIsEmptyNullOrPlaceHolderUrl = largeBannerUrl == null || largeBannerUrl.trim().isEmpty() || largeBannerUrl.contains(placeHolderDomainUrl);
		boolean mediumBannerUrlIsEmptyNullOrPlaceHolderUrl = mediumBannerUrl == null || mediumBannerUrl.trim().isEmpty() || mediumBannerUrl.contains(placeHolderDomainUrl);
		boolean smallBannerUrlIsEmptyNullOrPlaceHolderUrl = smallBannerUrl == null || smallBannerUrl.trim().isEmpty() || smallBannerUrl.contains(placeHolderDomainUrl);
		boolean iconImageUrlIsEmptyNullOrPlaceHolderUrl = iconImageUrl == null || iconImageUrl.trim().isEmpty() || iconImageUrl.contains(placeHolderDomainUrl);
		
		String title = carouselNews.getTitle();
		String bannerBackgroundColor = carouselNews.getBannerBackgroundColor().replace("#", "");
		String bannerTextColor = carouselNews.getBannerTextColor().replace("#", "");
		
		String titleEncode = "";
		
		String placeHolderUrlTemplate = placeHolderDomainUrl +"txtsize=66{{resolution}}&bg={{backgroundColor}}&txtclr={{textColor}}&txt={{textEncode}}";
		
		try {
			titleEncode = URLEncoder.encode(title, "UTF-8");
		} 
		catch (UnsupportedEncodingException e)
		{/**Do nothing*/}
		
		placeHolderUrlTemplate = placeHolderUrlTemplate.replace("{{backgroundColor}}", bannerBackgroundColor);
		placeHolderUrlTemplate = placeHolderUrlTemplate.replace("{{textColor}}", bannerTextColor);
		placeHolderUrlTemplate = placeHolderUrlTemplate.replace("{{textEncode}}", titleEncode);
				
	    if(largeBannerUrlIsEmptyNullOrPlaceHolderUrl) {
			String resolution = "&w=1140&h=300";
			String defaultLargeBannerUrl = placeHolderUrlTemplate.replace("{{resolution}}", resolution);
			carouselNews.setLargeBannerUrl(defaultLargeBannerUrl);
			
		}
		if(mediumBannerUrlIsEmptyNullOrPlaceHolderUrl) {
			String resolution = "&w=940&h=300";
			String defaultMediumBannerUrl = placeHolderUrlTemplate.replace("{{resolution}}", resolution);
			carouselNews.setMediumBannerUrl(defaultMediumBannerUrl);
			
		}
		if(smallBannerUrlIsEmptyNullOrPlaceHolderUrl) {
			String resolution = "&w=720&h=300";
			String defaultSmallBannerUrl = placeHolderUrlTemplate.replace("{{resolution}}", resolution);
			carouselNews.setSmallBannerUrl(defaultSmallBannerUrl);
		}
		if(iconImageUrlIsEmptyNullOrPlaceHolderUrl) {
			String resolution = "&w=64x&h=64";
			String defaultIconImageUrl = placeHolderUrlTemplate.replace("{{resolution}}", resolution);
			carouselNews.setIconImageUrl(defaultIconImageUrl);
		}
		
	}
	
}
