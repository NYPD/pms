package services.ntr.pms.model.incentive;

import services.ntr.pms.model.information.TankInformation;

public class IncentiveSummaryTableRow {

	private IncentiveTankDetail incentiveTankDetail;
	private TankInformation tankInformation;
	
	public IncentiveTankDetail getIncentiveTankDetail() {
		return incentiveTankDetail;
	}
	public void setIncentiveTankDetail(IncentiveTankDetail incentiveTankDetail) {
		this.incentiveTankDetail = incentiveTankDetail;
	}
	public TankInformation getTankInformation() {
		return tankInformation;
	}
	public void setTankInformation(TankInformation tankInformation) {
		this.tankInformation = tankInformation;
	}
	
	@Override
	public String toString() {
		return "IncentiveSummaryTableRow [incentiveTankDetail=" + incentiveTankDetail + ", tankInformation="
				+ tankInformation + "]";
	}
	
}
