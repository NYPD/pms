package services.ntr.pms.service.information;

import java.util.List;

public interface NamingService {
	
	void name(List<? extends Nameable> nameables);
}
