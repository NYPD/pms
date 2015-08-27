package services.ntr.pms.model.access;

public class Token
{
	private String accessToken;
	private long accountId;
	private long expiresAt;
	
	public String getAccessToken()
	{
		return accessToken;
	}
	public void setAccessToken(String accessToken)
	{
		this.accessToken = accessToken;
	}
	public long getAccountId()
	{
		return accountId;
	}
	public void setAccountId(long accountId)
	{
		this.accountId = accountId;
	}
	public long getExpiresAt()
	{
		return expiresAt;
	}
	public void setExpiresAt(long expiresAt)
	{
		this.expiresAt = expiresAt;
	}
	@Override
	public String toString()
	{
		return "Token [accessToken=" + accessToken + ", accountId=" + accountId
				+ ", expiresAt=" + expiresAt + "]";
	}
}
