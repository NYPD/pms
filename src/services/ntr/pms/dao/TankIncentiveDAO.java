package services.ntr.pms.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import services.ntr.pms.annotation.DefaultDatabase;
import services.ntr.pms.model.incentive.IncentivePlayer;
import services.ntr.pms.model.incentive.IncentiveTank;
import services.ntr.pms.model.incentive.IncentiveTankDetail;
import services.ntr.pms.model.incentive.TankIncentiveDefaultPayout;

@DefaultDatabase
public interface TankIncentiveDAO {

	List<IncentiveTankDetail> getClanIncentiveTankDetails(@Param("clanId") long clanId);
	List<IncentivePlayer> getTopIncentivePlayersByClan(@Param("clanId") long clanId);
	List<IncentiveTank> getTopTanksUnlockedByClan(@Param("clanId") long clanId);
	
	@MapKey("tankId")
	Map<Integer, TankIncentiveDefaultPayout> getTankIncentiveDefaultPayouts(@Param("clanId") long clanId);
	
	void insertTankIncentiveDefaultPayouts(@Param("tankIncentiveDefaultPayouts") List<TankIncentiveDefaultPayout> tankIncentiveDefaultPayouts);
	void updateTankIncentiveDefaultPayouts(@Param("tankIncentiveDefaultPayouts") List<TankIncentiveDefaultPayout> tankIncentiveDefaultPayouts);
}
