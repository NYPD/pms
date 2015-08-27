package services.ntr.pms.proxy.contentprovider;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

public class PostContentProvider implements ContentProvider {
	private String resource;
	private Map<String, String> globalParameters;
	
	@Override
	public byte[] execute(Map<String, String> parameters, String path) {	
		
		InputStream content = null;
		byte[] bytes = new byte[0];
		
		try {
			String fullURI = resource + path;
		
			CloseableHttpClient httpclient = HttpClients.createDefault();
			HttpPost httpPost = new HttpPost(fullURI);
					
			List<NameValuePair> postParameters = getPostParameters(parameters);
			UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(postParameters);
			httpPost.setEntity(urlEncodedFormEntity);
			
			CloseableHttpResponse httpResponse = httpclient.execute(httpPost);
			HttpEntity entity = httpResponse.getEntity();
			content = entity.getContent();
			bytes = IOUtils.toByteArray(content);
			
			httpResponse.close();
			httpclient.close();
		}
		catch (IOException exception){
			//TODO Log
		}
		finally {
			closeInputStream(content);
		}

		return bytes;
	}
	
	private List<NameValuePair> getPostParameters(Map<String, String> parameters) {
		List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		parameters.putAll(globalParameters);
		
		Set<Entry<String,String>> entrySet = parameters.entrySet();
		
		for (Entry<String, String> entry : entrySet) {
			String key = entry.getKey();
			String value = entry.getValue();
			
			BasicNameValuePair basicNameValuePair = new BasicNameValuePair(key, value);
			postParameters.add(basicNameValuePair);
		}
		
		return postParameters;
	}
	private void closeInputStream(InputStream content) {
		try {
			content.close();
		} catch (IOException exception) {
			//TODO Log
		} catch (Exception exception) {
			//TODO Log
		}
	}
	public String getResource() {
		return resource;
	}
	public void setResource(String resource) {
		this.resource = resource;
	}
	public Map<String, String> getGlobalParameters() {
		return globalParameters;
	}
	public void setGlobalParameters(Map<String, String> globalParameters) {
		this.globalParameters = globalParameters;
	}

}
