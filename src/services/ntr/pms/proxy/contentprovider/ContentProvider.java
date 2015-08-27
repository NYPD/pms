package services.ntr.pms.proxy.contentprovider;

import java.util.Map;

public interface ContentProvider {
	byte[] execute(Map<String,String> parameters, String path);
}
