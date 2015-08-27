package services.ntr.pms.service.access;

import services.ntr.pms.model.access.User;

public interface UserService {

	User getUser(long accountId);
}
