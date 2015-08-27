package services.ntr.pms.service.information;

import java.util.List;
import services.ntr.pms.model.information.CarouselNews;

public interface NewsCarouselService {

	void addNews(CarouselNews carouselNews);

	List<CarouselNews> getClanNews(long clanId);

	List<CarouselNews> getActiveClanNews(long clanId);

	void removeNews(int newsCarouselId);

	CarouselNews getClanNewsById(long clanId, int newsCarouselId);

	void updateNews(CarouselNews carouselNews);

	void putNews(CarouselNews carouselNews);

	CarouselNews getNewsById(int newsCarouselId);
}
