package services.ntr.pms.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import services.ntr.pms.exception.APIRequestUnsuccessful;
import services.ntr.pms.exception.InvalidAccessTokenException;
import services.ntr.pms.model.access.Token;
import services.ntr.pms.model.information.ClanBattle;
import services.ntr.pms.model.information.ClanInfo;
import services.ntr.pms.model.information.ClanProvince;
import services.ntr.pms.model.information.GlobalWarMap;
import services.ntr.pms.model.information.MapDetail;
import services.ntr.pms.model.information.MembersInfo;
import services.ntr.pms.model.information.Player;
import services.ntr.pms.model.information.TankInformation;
import services.ntr.pms.proxy.contentprovider.ContentProvider;
import services.ntr.pms.util.ApplicationConstants;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Repository
public class DefaultAPIRequestDAO implements APIRequestDAO {

	@Autowired
	private ObjectMapper mapper;
	@Autowired
	private ContentProvider contentProvider;
	
	private static Logger logger = Logger.getLogger(DefaultAPIRequestDAO.class);

	public static final String PLAYER_PERSONAL_DATA_PATH = "wot/account/info/";
	public static final String CLAN_MEMBER_PATH = "wgn/clans/membersinfo/";
	public static final String CLAN_DETAILS_PATH = "wgn/clans/info/";
	public static final String CLAN_BATTLES_PATH = "wot/globalwar/battles/";
	public static final String LOGOUT_PATH = "wot/auth/logout/";
	public static final String LOGIN_PATH = "wot/auth/login/";
	public static final String GLOBAL_WAR_MAPS = "wot/globalwar/maps/";
	public static final String LIST_OF_VEHICLES = "wot/encyclopedia/tanks/";
	public static final String CLAN_PROVINCES = "wot/clan/provinces/";
	public static final String EXTEND_ACCESS = "wot/auth/prolongate/";
	public static final String MAP_DETAILS = "wot/encyclopedia/arenas/";
	
	@Override
	public Player getPlayerInformation(String accountId, String accessToken){	
		Player player = null;
		
		try{
			ContentProvider contentProvider = getContentProvider();
			
			Map<String, String> parameters = new HashMap<String, String>();
			
			parameters.put("account_id", accountId);
			parameters.put("access_token", accessToken);
	
			byte[] content = contentProvider.execute(parameters, PLAYER_PERSONAL_DATA_PATH);
			
			JsonNode jsonNode = getJsonNode(content);
			
			throwApiRequestUnsuccessfulIfAplicable(jsonNode);
				
			String json = jsonNode.get("data").get(accountId).toString();
			player = getMapper().readValue(json, Player.class);
			player.setAccessToken(accessToken);
			
		} 
		catch (IOException exception){
			logger.error("error in getPlayerInformation()", exception);
		}
		
		return player;
	}

	@Override
	public Player getPlayerInformation(String accountId) {
		
		Player playerInformation = getPlayerInformation(accountId, "");
		return playerInformation;
	}
	
	@Override
	public Player getPlayerInformation(long accountId) {
		
		String accountIdAsString = String.valueOf(accountId);
		
		Player playerInformation = getPlayerInformation(accountIdAsString, "");
		return playerInformation;
	}
	
	@Override
	public Map<Long, Player> getPlayerInformationMap(List<Long> accountIds) {
		
		Map<Long, Player> playersInformationMap = new HashMap<Long, Player>();
		
		StringBuilder sb = new StringBuilder();
		
		for (long accountId : accountIds) sb.append(accountId).append(',');
			
		String accountIdsQueryString = sb.toString();
		
		try {
			ContentProvider contentProvider = getContentProvider();
			
			Map<String, String> parameters = new HashMap<String, String>();
			
			parameters.put("account_id", accountIdsQueryString);
			
			byte[] content = contentProvider.execute(parameters, PLAYER_PERSONAL_DATA_PATH);
			
			JsonNode jsonNode = getJsonNode(content);
			
			throwApiRequestUnsuccessfulIfAplicable(jsonNode);
				
			String json = jsonNode.get("data").toString();
			playersInformationMap = getMapper().readValue(json, new TypeReference<Map<Long,Player>>(){});
			
		} 
		catch (IOException exception) {
			logger.error("error in getClanBattleInformation()", exception);
		}
		
		
	
		return playersInformationMap;
	}

	@Override
	public MembersInfo getMemberInformation(String accountId, String accessToken) {
		
		MembersInfo membersInfo = null;
		
		try {
			ContentProvider contentProvider = getContentProvider();
			
			Map<String, String> parameters = new HashMap<String, String>();
			
			parameters.put("account_id", accountId);
			parameters.put("access_token", accessToken);
			
			byte[] content = contentProvider.execute(parameters, CLAN_MEMBER_PATH);

			JsonNode jsonNode = getJsonNode(content);

			throwApiRequestUnsuccessfulIfAplicable(jsonNode);
				
			String json = jsonNode.get("data").get(accountId).toString();
			membersInfo = getMapper().readValue(json, MembersInfo.class);
		} 
		catch (IOException exception) {
			logger.error("error in getMemberInformation()", exception);
		}
		
		return membersInfo;
	}

	@Override
	public ClanInfo getClanInformation(String clanId, String accessToken) {
		
		ClanInfo clanInfo = null;
		
		try {
			ContentProvider contentProvider = getContentProvider();
			
			Map<String, String> parameters = new HashMap<String, String>();
			
			parameters.put("clan_id", clanId);
			parameters.put("access_token", accessToken);
			
			byte[] content = contentProvider.execute(parameters, CLAN_DETAILS_PATH);

			JsonNode jsonNode = getJsonNode(content);

			throwApiRequestUnsuccessfulIfAplicable(jsonNode);
				
			String json = jsonNode.get("data").get(clanId).toString();
			clanInfo = getMapper().readValue(json, ClanInfo.class);
			
		} 
		catch (IOException exception) {
			logger.error("error in getClanInformation()", exception);
		}
	
		return clanInfo;
	}
	
	@Override
	public ClanInfo getClanInformation(String clanId) {
		
		ClanInfo clanInformation = getClanInformation(clanId, "");
		
		return clanInformation;
	}
	
	@Override
	public ClanInfo getClanInformation(long clanId) {
		
		String clanIdAsString = String.valueOf(clanId);
		
		ClanInfo clanInformation = getClanInformation(clanIdAsString, "");
		
		return clanInformation;
	}
	
	@Override
	public List<ClanBattle> getClanBattleInformation(String clanId, String accessToken) {
		
		List<ClanBattle> allBattles = new ArrayList<ClanBattle>();
		
		try {
			ContentProvider contentProvider = getContentProvider();
			
			Map<String, String> parameters = new HashMap<String, String>();
			
			parameters.put("clan_id", clanId);
			parameters.put("access_token", accessToken);
			
			List<GlobalWarMap> globalWarMaps = getGlobalWarMaps(accessToken);
			
			List<String> activeGlobalMapIds = getActiveGlobalMapIds(globalWarMaps);
			
			for (String mapId : activeGlobalMapIds){
				
				List<ClanBattle> battles = new ArrayList<ClanBattle>();
				
				parameters.put("map_id", mapId);
				
				byte[] content = contentProvider.execute(parameters, CLAN_BATTLES_PATH);
				
				JsonNode jsonNode = getJsonNode(content);
				
				throwApiRequestUnsuccessfulIfAplicable(jsonNode);
					
				String json = jsonNode.get("data").get(clanId).toString();
				battles = getMapper().readValue(json, new TypeReference<List<ClanBattle>>(){});
				
				allBattles.addAll(battles);
				
			}
			
		} 
		catch (IOException exception) {
			logger.error("error in getClanBattleInformation()", exception);
		}
	
		return allBattles;
	}
	
	@Override
	public Map<Integer, TankInformation> getTankInformationMap() {
		
		Map<Integer,TankInformation> tankInfoMap = new HashMap<>();
		
		try {
			
			ContentProvider contentProvider = getContentProvider();
			
			Map<String, String> parameters = new HashMap<String, String>();//FIX ME

			byte[] content = contentProvider.execute(parameters, LIST_OF_VEHICLES);
			
			JsonNode jsonNode = getJsonNode(content);

			throwApiRequestUnsuccessfulIfAplicable(jsonNode);
			
			String json = jsonNode.get("data").toString();
			tankInfoMap = getMapper().readValue(json, new TypeReference<Map<Integer,TankInformation>>(){});
			
		}catch(IOException exception) {
			logger.error("error in getCompleteTankList()", exception);
		}
		
		return tankInfoMap;
	}
	
	@Override
	public List<GlobalWarMap> getGlobalWarMaps(String accessToken) {
		
		List<GlobalWarMap> globalWarMaps = new ArrayList<GlobalWarMap>();
		
		try{
			
			ContentProvider contentProvider = getContentProvider();
			Map<String, String> parameters = new HashMap<String, String>();
			
			byte[] content = contentProvider.execute(parameters, GLOBAL_WAR_MAPS);
			
			JsonNode jsonNode = getJsonNode(content);
			
			throwApiRequestUnsuccessfulIfAplicable(jsonNode);
			
			String json = jsonNode.get("data").toString();
			globalWarMaps = getMapper().readValue(json, new TypeReference<List<GlobalWarMap>>(){});
		
		} catch (IOException exception){
			
			logger.error("error in getGlobalWarMaps()", exception);
		}
		
		return globalWarMaps;
	}

	
	@Override
	public Map<String, ClanProvince> getClanProvinces(String clanId, String accessToken) {
		
		Map<String, ClanProvince> clanProvinceMap = new HashMap<>();
		
		try{
			
			ContentProvider contentProvider = getContentProvider();
			
			Map<String, String> parameters = new HashMap<String, String>();
			
			parameters.put("clan_id", clanId);
			parameters.put("access_token", accessToken);

			byte[] content = contentProvider.execute(parameters, CLAN_PROVINCES);
			
			JsonNode jsonNode = getJsonNode(content);

			throwApiRequestUnsuccessfulIfAplicable(jsonNode);
			
			String json = jsonNode.get("data").toString();
			clanProvinceMap = getMapper().readValue(json, new TypeReference<Map<String, ClanProvince>>(){});
			
		}catch(IOException exception){
			logger.error("error in getCompleteTankList()", exception);
		}
		
		return clanProvinceMap;
	}
	
	@Override
	public void logoutPlayer(String accessToken){
		
		ContentProvider contentProvider = getContentProvider();
		
		Map<String, String> parameters = new HashMap<String, String>();
		
		parameters.put("access_token", accessToken);
		
		contentProvider.execute(parameters, LOGOUT_PATH);
		
	}
	
	@Override
	public Token extendAccess(Token token)
	{
		Token newToken = new Token();
		
		try{
		
			ContentProvider contentProvider = getContentProvider();
			
			Map<String, String> parameters = new HashMap<String, String>();
			
			String accessToken = token.getAccessToken();
			long expiresAt = token.getExpiresAt();
			String expiresAtAsString = String.valueOf(expiresAt);
			
			parameters.put("access_token", accessToken);
			parameters.put("expires_at", expiresAtAsString);
			
			byte[] content = contentProvider.execute(parameters, EXTEND_ACCESS);
			JsonNode jsonNode = getJsonNode(content);
	
			throwApiRequestUnsuccessfulIfAplicable(jsonNode);
			
			String json = jsonNode.get("data").toString();
			newToken = getMapper().readValue(json, Token.class);
		}catch(IOException exception){
			logger.error("error in getCompleteTankList()", exception);
		}
		
		
		return newToken;
	}
	
	@Override
	public String getLoginRedirectURL(boolean rememberMe) {
		
		String loginRedirectURL = "";
		
		try{
			
			ContentProvider contentProvider = getContentProvider();
			
			Map<String, String> parameters = new HashMap<String, String>();
			
			String redirectUri = ApplicationConstants.OPENID_LOGIN_URI_REDIRECT + "?rememberMe=" + rememberMe;
			
			parameters.put("redirect_uri", redirectUri);
			parameters.put("nofollow", "1");
			
			byte[] content = contentProvider.execute(parameters, LOGIN_PATH);
			JsonNode jsonNode = getJsonNode(content);
	
			throwApiRequestUnsuccessfulIfAplicable(jsonNode);
			
			loginRedirectURL = jsonNode.get("data").get("location").asText();
			
		}catch(IOException exception){
			logger.error("error in getLoginRedirectURL()", exception);
		}
		
		return loginRedirectURL;
	}
	

	@Override
	public Map<String, MapDetail> getMapDetailMappedByArenaId() {
		
		Map<String, MapDetail> mapDetailsMap = new HashMap<>();
		
		try{
			
			ContentProvider contentProvider = getContentProvider();
			Map<String, String> parameters = new HashMap<String, String>();
			
			byte[] content = contentProvider.execute(parameters, MAP_DETAILS);
			
			JsonNode jsonNode = getJsonNode(content);
			
			throwApiRequestUnsuccessfulIfAplicable(jsonNode);
			
			String json = jsonNode.get("data").toString();
			mapDetailsMap = getMapper().readValue(json, new TypeReference<Map<String, MapDetail>>(){});
		
		} catch (IOException exception){
			
			logger.error("error in getMapDetails()", exception);
		}
		
		return mapDetailsMap;
	}
	
	public ObjectMapper getMapper(){
		return mapper;
	}

	public void setMapper(ObjectMapper mapper){
		this.mapper = mapper;
	}

	public ContentProvider getContentProvider(){
		return contentProvider;
	}

	public void setContentProvider(ContentProvider contentProvider){
		this.contentProvider = contentProvider;
	}
	
	private List<String> getActiveGlobalMapIds(List<GlobalWarMap> globalWarMaps){
	
		List<String> globalMapIds = new ArrayList<String>();
		
		for (GlobalWarMap globalWarMap : globalWarMaps){
			
			String mapId = globalWarMap.getMapId();
			
			boolean mapIsActive = globalWarMap.isActive();
			
			if(mapIsActive) globalMapIds.add(mapId);
			
		}
		
		return globalMapIds;
	}
	
	private JsonNode getJsonNode(byte[] content) throws IOException, JsonProcessingException {
		
		logger.info(content);//TODO remove this
		
		String jsonString = new String(content);
		
		logger.info(jsonString);
		
		JsonNode jsonNode = getMapper().readTree(jsonString);
		return jsonNode;
	}
	
	private void throwApiRequestUnsuccessfulIfAplicable(JsonNode jsonNode) {
		String status = jsonNode.get("status").asText();
		
		boolean apiRequestUnsuccessful = "error".equals(status);
		
		if(apiRequestUnsuccessful) setupAndThrowApiException(jsonNode);
	}
	
	private void setupAndThrowApiException(JsonNode jsonNode){
		
		String message = jsonNode.get("error").get("message").asText();
		String field = jsonNode.get("error").get("field").asText();
		String value = jsonNode.get("error").get("value").asText();
		
		/**
		 * If it is an invalid token, we need to redirect the user to the login page
		 */
		boolean isInvalidAccessTokenException = "INVALID_ACCESS_TOKEN".equals(message);
		if(isInvalidAccessTokenException) throw new InvalidAccessTokenException();
		
		throw new APIRequestUnsuccessful(message, field, value);
	}

}
