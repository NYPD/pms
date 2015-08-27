package services.ntr.pms.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import services.ntr.pms.annotation.DefaultDatabase;
import services.ntr.pms.model.checkin.Attendance;
import services.ntr.pms.model.checkin.CallerAttendance;
import services.ntr.pms.model.history.PayoutMonthSummary;
import services.ntr.pms.model.history.TransactionHistory;
import services.ntr.pms.model.history.TransactionHistoryChartPoint;
import services.ntr.pms.model.information.Player;
import services.ntr.pms.model.payout.CampaignReward;
import services.ntr.pms.model.payout.IncentiveCompletion;
import services.ntr.pms.model.payout.Payout;
import services.ntr.pms.model.payout.PlayerBonus;
import services.ntr.pms.model.payout.PointSummary;
import services.ntr.pms.model.payout.Transaction;
import services.ntr.pms.model.util.TimeFrame;

@DefaultDatabase
public interface PayoutDAO{
	
	List<Attendance> getAttendancesNotPayedOut(@Param("clanId") long clanId);
	List<PointSummary> getPointSummaries(@Param("clanId") long clanId);
	List<PointSummary> getPointSummariesByTimeFrame(@Param("clanId") long clanId, @Param("timeFrame")TimeFrame timeFrame);
	List<CallerAttendance> getCallerAttendancesNotPayedOut(@Param("clanId") long clanId);
	List<PointSummary> getCallerPointSummaries(@Param("clanId") long clanId);
	List<Payout> getPayoutsByPlayer(Player player);
	List<PayoutMonthSummary> getMonthlyPlayerPayoutSummary(@Param("player") Player player, @Param("timeFrame")TimeFrame timeFrame);
	List<TransactionHistory> getTransactionHistory(@Param("clanId") long clanId);
	List<TransactionHistoryChartPoint> getTransactionHistoryChartPoints(@Param("clanId") long clanId);
	
	void addTransaction(Transaction transaction);
	void addPayout(Payout payout);
	void addAttendancePayout(@Param("attendanceId")int attendanceId, @Param("payoutId")int payoutId);
	void addIncentiveCompletion(IncentiveCompletion incentiveCompletion);
	void addIncentivePayout(@Param("incentiveId")int incentiveId, @Param("payoutId")int payoutId);
	void addPlayerBonus(PlayerBonus playerBonus);
	void addPlayerBonusPayout(@Param("playerBonusId")int playerBonusId, @Param("payoutId") int payoutId);
	void addCampaignReward(CampaignReward campaignReward);
	void addCampaignRewardPayout(@Param("campaignRewardId")int campaignRewardId, @Param("payoutId") int payoutId, @Param("accountId")long accountId);
	void addCallerPayout(@Param("callerAttendanceId")int callerAttendanceId, @Param("payoutId") int payoutId);
}
