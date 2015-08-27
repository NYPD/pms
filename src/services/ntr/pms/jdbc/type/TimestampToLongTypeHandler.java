package services.ntr.pms.jdbc.type;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;

@MappedJdbcTypes(JdbcType.TIMESTAMP)
public class TimestampToLongTypeHandler extends BaseTypeHandler<Long> 
{
	@Override
	public Long getNullableResult(ResultSet resultSet, String arg1)throws SQLException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long getNullableResult(ResultSet resultSet, int arg1) throws SQLException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long getNullableResult(CallableStatement statement, int columnName) throws SQLException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setNonNullParameter(PreparedStatement statement, int i, Long parameter, JdbcType jdbcType) throws SQLException
	{
		// TODO Auto-generated method stub
		
	}
}