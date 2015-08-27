package services.ntr.pms.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import services.ntr.pms.annotation.DefaultDatabase;
import services.ntr.pms.model.information.CarouselNews;

@DefaultDatabase
public interface NewsCarouselDAO
{
	void addNews(CarouselNews carouselNews);

	List<CarouselNews> getClanNews(@Param("clanId") long clanId);

	List<CarouselNews> getActiveClanNews(@Param("clanId") long clanId);

	void removeNews(@Param("newsCarouselId")int newsCarouselId);

	CarouselNews getClanNewsById(@Param("clanId")long clanId, @Param("newsCarouselId")int newsCarouselId);
	
	CarouselNews getNewsById(@Param("newsCarouselId")int newsCarouselId);
	
	void updateNews(CarouselNews carouselNews);
}
