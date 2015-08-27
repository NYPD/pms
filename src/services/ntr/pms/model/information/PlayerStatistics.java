package services.ntr.pms.model.information;

import java.io.Serializable;

public class PlayerStatistics implements Serializable {

	private static final long serialVersionUID = 4419745039852342726L;
	
	private int treesCut;

	public int getTreesCut() {
		return treesCut;
	}
	public void setTreesCut(int treesCut) {
		this.treesCut = treesCut;
	}
	
	@Override
	public String toString() {
		return "PlayerStatistics [treesCut=" + treesCut + "]";
	}
	
}
