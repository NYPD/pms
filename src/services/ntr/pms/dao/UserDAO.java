package services.ntr.pms.dao;

import org.apache.ibatis.annotations.Param;

import services.ntr.pms.annotation.DefaultDatabase;
import services.ntr.pms.model.access.User;

@DefaultDatabase
public interface UserDAO {

	User getUser(@Param("accountId") long accountId);
}
