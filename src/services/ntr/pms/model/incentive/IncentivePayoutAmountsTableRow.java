package services.ntr.pms.model.incentive;

import java.util.Comparator;

import services.ntr.pms.model.information.TankInformation;

public class IncentivePayoutAmountsTableRow {

	private static class TankNameComparator implements Comparator<IncentivePayoutAmountsTableRow> {
		@Override
		public int compare(IncentivePayoutAmountsTableRow incentivePayoutAmountsTableRow, IncentivePayoutAmountsTableRow otherIncentivePayoutAmountsTableRow) {
			
			TankInformation tankInformation = incentivePayoutAmountsTableRow.getTankInformation();
			TankInformation otherTankInformation = otherIncentivePayoutAmountsTableRow.getTankInformation();

			String tankName = tankInformation.getNameI18n();
			String othertankName = otherTankInformation.getNameI18n();
			
			int compare = String.CASE_INSENSITIVE_ORDER.compare(tankName, othertankName);
			
			return compare;
		}
		
	}

	public static final Comparator<IncentivePayoutAmountsTableRow> TANK_NAME_ORDER = new TankNameComparator();
	private TankIncentiveDefaultPayout tankIncentiveDefaultPayout;
	private TankInformation tankInformation;
	
	public TankIncentiveDefaultPayout getTankIncentiveDefaultPayout() {
		return tankIncentiveDefaultPayout;
	}
	public void setTankIncentiveDefaultPayout(TankIncentiveDefaultPayout tankIncentiveDefaultPayout) {
		this.tankIncentiveDefaultPayout = tankIncentiveDefaultPayout;
	}
	public TankInformation getTankInformation() {
		return tankInformation;
	}
	public void setTankInformation(TankInformation tankInformation) {
		this.tankInformation = tankInformation;
	}
	
	@Override
	public String toString() {
		return "TankIncentiveAmountTableRow [tankIncentiveDefaultPayout=" + tankIncentiveDefaultPayout
				+ ", tankInformation=" + tankInformation + "]";
	}
	
}
