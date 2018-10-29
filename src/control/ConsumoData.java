package control;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import entity.Dados;

public class ConsumoData {

	private static final long serialVersionUID = 1L;
	public final static String path = "http://env-4539655.jelasticlw.com.br/auth/api/cliente/v2/logar";
	String rt = null;
	ArrayList<Dados> dados = null;
	ArrayList<String> datas = null;

	public static void main(String[] args) throws JSONException {
		ConsumoData c = new ConsumoData();
		List<String> d = new ArrayList<String>();

		d = c.retorna_token("ROBSON", "12345", "2018-09-09");
		
		System.out.println(d);
	
	}

	public ArrayList<String> retorna_token(String Login, String Senha, String Data) throws JSONException {

		try {
			rt = new Resgata_Token().retorna_token(Login, Senha);
		} catch (Exception e) {

			e.printStackTrace();
		}


		String restUrl = path;
		JSONObject acesso = new JSONObject();
		acesso.put("login", Login);
		acesso.put("senha", Senha);
		acesso.put("dataDe", Data);
		String jsonData = acesso.toString();

		
		ConsumoData httpPostReq = new ConsumoData();

		HttpPost httpPost = httpPostReq.createHttpGetConnection(restUrl, rt);

		datas = httpPostReq.executeReq(httpPost, jsonData);

		return datas;

	}

	HttpPost createHttpGetConnection(String restUrl, String rs) {
		HttpPost post = new HttpPost(restUrl);

		post.setHeader("Content-Type", "application/json");
		post.setHeader("Accept", "application/json");
		post.setHeader("Bearer ", rs);
		return post;
	}

	ArrayList<String> executeReq(HttpPost httpPost, String jsonData) {
		try {
			datas = executeHttpRequest(httpPost, jsonData);
		} catch (UnsupportedEncodingException e) {
			System.out.println("error while encoding api url : " + e);
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("ioException occured while sending http request : " + e);
		} catch (Exception e) {
			System.out.println("exception occured while sending http request : " + e);
			e.printStackTrace();
		} finally {
			httpPost.releaseConnection();
		}
		return datas;
	}

	public ArrayList<String> executeHttpRequest(HttpPost httpPost, String jsonData)
			throws UnsupportedEncodingException, IOException, JSONException {

		HttpResponse response = null;
		String line = "";
		dados = new ArrayList<Dados>();
		ArrayList<String> Mes = new ArrayList<String>();

		StringBuffer result = new StringBuffer();

		httpPost.setEntity(new StringEntity(jsonData));

		HttpClient client = HttpClientBuilder.create().build();
		response = client.execute(httpPost);

		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());

		BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

		while ((line = reader.readLine()) != null) {
			result.append(line);
		}

		JSONObject json1 = new JSONObject(result.toString());

		JSONArray jsonArray = json1.getJSONArray("data");

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject obj2 = jsonArray.getJSONObject(i);

			Dados item = new Dados(obj2.getInt("idXML_TAB"), obj2.getString("concentrador"),
					obj2.getString("numHidrometro"), obj2.getString("data"), obj2.getInt("indice_atual")/1000,
					obj2.getString("alarmes"), obj2.getString("unit"));

			String[] alarmes = item.getAlarmes().split(":");

			item.setHaVazamento(Integer.parseInt(alarmes[1]));
			item.setHouveVazamento(Integer.parseInt(alarmes[2]));
			item.setHaDesmontagem(Integer.parseInt(alarmes[3]));
			item.setHouveDesmontagem(Integer.parseInt(alarmes[4]));
			item.setMedidorBloqueado(Integer.parseInt(alarmes[7]));
			item.setRetornoAgua(Integer.parseInt(alarmes[8]));

			dados.add(item);
		}
		

		Map<String, List<Dados>> consumo = dados.stream().collect(Collectors.groupingBy(Dados::getData));


		ArrayList<String> values2 = (ArrayList<String>) consumo.keySet().stream().collect(Collectors.toList());

		
		return values2;

	}

	
}