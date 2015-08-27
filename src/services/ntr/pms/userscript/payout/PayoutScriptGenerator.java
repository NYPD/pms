package services.ntr.pms.userscript.payout;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import services.ntr.pms.model.payout.PayoutScriptInfo;

public class PayoutScriptGenerator{
	
	private static Logger logger = Logger.getLogger(PayoutScriptGenerator.class);
	
	public InputStream getPayoutUserScriptStream(PayoutScriptInfo payoutScriptInfo){
		InputStream payoutUserScriptStream = null;
		
		try{
			List<String> playerNicknames = payoutScriptInfo.getPlayerNicknames();
			int payoutAmount = payoutScriptInfo.getPayoutAmount();
			long clanId = payoutScriptInfo.getClanId();
			
			String payoutAmountAsString = String.valueOf(payoutAmount);
			UUID scriptId = UUID.randomUUID();
			String scriptIdAsString = String.valueOf(scriptId);
			String clanIdAsString = String.valueOf(clanId);

			String playersPayout = playerNicknames.toString().replaceAll("(\\w+)", "'$1'");

			ClassLoader classLoader = PayoutScriptGenerator.class.getClassLoader();
			InputStream payoutUserScriptTemplateStream = classLoader.getResourceAsStream("services/ntr/pms/userscript/payouts/payout.user.js");

			String payoutUserScriptAsString;
			payoutUserScriptAsString = IOUtils.toString(payoutUserScriptTemplateStream);
			payoutUserScriptAsString = payoutUserScriptAsString.replace("${goldAmount}", payoutAmountAsString);
			payoutUserScriptAsString = payoutUserScriptAsString.replace("${playerPayoutList}", playersPayout);
			payoutUserScriptAsString = payoutUserScriptAsString.replace("${scriptId}", scriptIdAsString);
			payoutUserScriptAsString = payoutUserScriptAsString.replace("${clanId}", clanIdAsString);

			payoutUserScriptStream = IOUtils.toInputStream(payoutUserScriptAsString, "UTF-8");

		} catch (IOException exception){
			logger.error(exception);
		}
		
		return payoutUserScriptStream;
	}
}