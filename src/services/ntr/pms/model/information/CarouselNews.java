package services.ntr.pms.model.information;

import org.springframework.format.annotation.NumberFormat;

public class CarouselNews {

	@NumberFormat
	private int id;
	private long clanId;
	private long createdByAccountId;
	private long modifiedByAccountId;
	private String title;
	private String text;
	private boolean smallScreenOnlyText;
	private String largeBannerUrl;  // 1140px width
	private String mediumBannerUrl; // 940px Width
	private String smallBannerUrl;  // 720px Width
	private String iconImageUrl;
	private String bannerBackgroundColor; 
	private String bannerTextColor;
	private boolean global;
	private boolean active;
	
	private String createdByNickname; //Not from SQL server
	private String modifiedByNickname; //Not from SQL server
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public long getClanId() {
		return clanId;
	}
	public void setClanId(long clanId) {
		this.clanId = clanId;
	}
	public long getCreatedByAccountId() {
		return createdByAccountId;
	}
	public void setCreatedByAccountId(long createdByAccountId) {
		this.createdByAccountId = createdByAccountId;
	}
	public long getModifiedByAccountId() {
		return modifiedByAccountId;
	}
	public void setModifiedByAccountId(long modifiedByAccountId) {
		this.modifiedByAccountId = modifiedByAccountId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getLargeBannerUrl() {
		return largeBannerUrl;
	}
	public void setLargeBannerUrl(String largeBannerUrl) {
		this.largeBannerUrl = largeBannerUrl;
	}
	public String getMediumBannerUrl() {
		return mediumBannerUrl;
	}
	public void setMediumBannerUrl(String mediumBannerUrl) {
		this.mediumBannerUrl = mediumBannerUrl;
	}
	public String getSmallBannerUrl() {
		return smallBannerUrl;
	}
	public void setSmallBannerUrl(String smallBannerUrl) {
		this.smallBannerUrl = smallBannerUrl;
	}
	public String getIconImageUrl() {
		return iconImageUrl;
	}
	public void setIconImageUrl(String iconImageUrl) {
		this.iconImageUrl = iconImageUrl;
	}
	public String getBannerBackgroundColor() {
		return bannerBackgroundColor;
	}
	public void setBannerBackgroundColor(String bannerBackgroundColor) {
		this.bannerBackgroundColor = bannerBackgroundColor;
	}
	public String getBannerTextColor() {
		return bannerTextColor;
	}
	public void setBannerTextColor(String bannerTextColor) {
		this.bannerTextColor = bannerTextColor;
	}
	public boolean isGlobal() {
		return global;
	}
	public void setGlobal(boolean global) {
		this.global = global;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public boolean isSmallScreenOnlyText() {
		return smallScreenOnlyText;
	}
	public void setSmallScreenOnlyText(boolean smallScreenOnlyText) {
		this.smallScreenOnlyText = smallScreenOnlyText;
	}
	public String getCreatedByNickname() {
		return createdByNickname;
	}
	public void setCreatedByNickname(String createdByNickname) {
		this.createdByNickname = createdByNickname;
	}
	public String getModifiedByNickname() {
		return modifiedByNickname;
	}
	public void setModifiedByNickname(String modifiedByNickname) {
		this.modifiedByNickname = modifiedByNickname;
	}
	
	//Helper Methods
	public int getNewsCarouselId() {
		return id;
	}
	public void setNewsCarouselId(int newsCarouselId) {
		this.id = newsCarouselId;
	}
	public String getActiveAsString() {
		String activeAsString = "No";
		if(active) activeAsString = "Yes";
		return activeAsString;
	}
	public String getGlobalAsString() {
		String globalAsString = "No";	
		if(global) globalAsString = "Yes";	
		return globalAsString;
	}
	
	@Override
	public String toString() {
		return "CarouselNews [id=" + id + ", clanId=" + clanId + ", createdByAccountId=" + createdByAccountId
				+ ", modifiedByAccountId=" + modifiedByAccountId + ", createdByNickname=" + createdByNickname
				+ ", modifiedByNickname=" + modifiedByNickname + ", title=" + title + ", text=" + text
				+ ", smallScreenOnlyText=" + smallScreenOnlyText + ", largeBannerUrl=" + largeBannerUrl
				+ ", mediumBannerUrl=" + mediumBannerUrl + ", smallBannerUrl=" + smallBannerUrl + ", iconImageUrl="
				+ iconImageUrl + ", bannerBackgroundColor=" + bannerBackgroundColor + ", bannerTextColor="
				+ bannerTextColor + ", global=" + global + ", active=" + active + "]";
	}
	
}
