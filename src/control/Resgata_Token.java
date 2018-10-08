package control;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONException;
import org.json.JSONObject;

public class Resgata_Token {

	private static final long serialVersionUID = 1L;
	public final static String path = "https://vast-cliffs-21624.herokuapp.com/auth/";
	public final static String user = "ROBSON";
	public final static String senha = "12345";
	public String token;

	public String retorna_token(String Login, String Senha) throws Exception {

		String restUrl = path;
		JSONObject acesso = new JSONObject();
		acesso.put("login", Login);
		acesso.put("senha", Senha);
		String jsonData = acesso.toString();

		Resgata_Token httpPostReq = new Resgata_Token();

		HttpPost httpPost = httpPostReq.createConnectivity(restUrl);

		token = httpPostReq.executeReq(jsonData, httpPost);

		return token;
	}

	HttpPost createConnectivity(String restUrl) {
		HttpPost post = new HttpPost(restUrl);

		post.setHeader("Content-Type", "application/json");
		post.setHeader("Accept", "application/json");
		// post.setHeader("X-Stream", "true");
		return post;
	}

	String executeReq(String jsonData, HttpPost httpPost) {

		String tk = null;
		try {
			tk = executeHttpRequest(jsonData, httpPost);
		} catch (UnsupportedEncodingException e) {
			System.out.println("error while encoding api url : " + e);
		} catch (IOException e) {
			System.out.println("ioException occured while sending http request : " + e);
		} catch (Exception e) {
			System.out.println("exception occured while sending http request : " + e);
		} finally {
			httpPost.releaseConnection();
		}
		return tk;
	}

	String executeHttpRequest(String jsonData, HttpPost httpPost)
			throws UnsupportedEncodingException, IOException, JSONException {
		HttpResponse response = null;

		String line = "";
		StringBuilder result = new StringBuilder();
		String myCustom_JSONResponse = "";
		String token = null;

		httpPost.setEntity(new StringEntity(jsonData));

		HttpClient client = HttpClientBuilder.create().build();
		response = client.execute(httpPost);

		BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

		while ((line = reader.readLine()) != null) {
			result.append(line);
		}

		JSONObject json = new JSONObject(result.toString());
			
		Object json23= json.get("data");
		
		System.out.println(json23);
		
		JSONObject json2 = new JSONObject(json23.toString());
		
		token =  json2.getString("token");

		return token;

	}

	public static void main(String[] args) {

		Resgata_Token retornaToken = new Resgata_Token();
		try {
			String token = retornaToken.retorna_token(user, senha);
			System.out.println(token);
		} catch (Exception e) {
			System.out.println("erro");
		}

	}

}
