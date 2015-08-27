package services.ntr.pms.service.history;

import java.util.List;

import services.ntr.pms.model.history.CallerMapFrequency;

public interface CallerMapFrequencyService {
	
	List<CallerMapFrequency> getCallerMapFrequencies(long clanId);

}
