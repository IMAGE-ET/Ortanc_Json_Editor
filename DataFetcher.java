/**
Copyright (C) 2017 VONGSALAT Anousone

This program is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public v.3 License as published by
the Free Software Foundation;

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License along
with this program; if not, write to the Free Software Foundation, Inc.,
51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA
 */



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//MODIFIE POUR UTILISER L OBJET CONNEION HTTP A PRENDRE EXEMPLE POUR LES AUTRES CLASS
// A PASSER A JSON POUR LE PARSING D INFORMATION

public class DataFetcher {

	private String id;
	private ParametreConnexionHttp connexionHttp;

	public DataFetcher(ParametreConnexionHttp connexionHttp, String id){
		this.connexionHttp=connexionHttp;
		this.id = id;
	}

	private String fetchData(String url) throws IOException{
		HttpURLConnection conn= connexionHttp.makeGetConnection(url);

		StringBuilder sb = new StringBuilder();
		String line = "";

		BufferedReader br = new BufferedReader(new InputStreamReader(
				(conn.getInputStream())));

		while (line != null) {
			sb.append(line);
			line = br.readLine();
		}
		br.close();
		conn.disconnect();
		return sb.toString();
	}
	
	/*
	 * extracts data from /studies/.../
	 */
	public String extractData(String field) throws IOException{
		String response = "";
		String data = "";
		String pattern1;
		String pattern2;
		Pattern p;
		Matcher m;
		switch (field) {
		case "AnonymizedFrom":
			response = fetchData("/studies/" + this.id);
			pattern1 = "\"AnonymizedFrom\" : \"";
			pattern2 = "\"";
			p = Pattern.compile(Pattern.quote(pattern1) + "(.*?)" + Pattern.quote(pattern2));
			m = p.matcher(response.toString());
			while(m.find()){
				data = m.group(1);
			}
			break;
		case "PatientID":
			response = fetchData("/studies/" + this.id);
			pattern1 = "\"PatientID\" : \"";
			pattern2 = "\"";
			p = Pattern.compile(Pattern.quote(pattern1) + "(.*?)" + Pattern.quote(pattern2));
			m = p.matcher(response.toString());
			while(m.find()){
				data = m.group(1);
			}
			break;
		case "PatientName":
			//this.url=fullAddress+"/studies/" + this.id;
			response = fetchData("/studies/" + this.id);
			pattern1 = "\"PatientName\" : \"";
			pattern2 = "\"";
			p = Pattern.compile(Pattern.quote(pattern1) + "(.*?)" + Pattern.quote(pattern2));
			m = p.matcher(response.toString());
			while(m.find()){
				data = m.group(1);
			}
			break;
		case "StudyDate":
			response = fetchData("/studies/" + this.id);
			pattern1 = "\"StudyDate\" : \"";
			pattern2 = "\"";
			p = Pattern.compile(Pattern.quote(pattern1) + "(.*?)" + Pattern.quote(pattern2));
			m = p.matcher(response.toString());
			while(m.find()){
				data = m.group(1);
			}
			break;
		case "StudyDescription":
			response = fetchData("/studies/" + this.id);
			pattern1 = "\"StudyDescription\" : \"";
			pattern2 = "\"";
			p = Pattern.compile(Pattern.quote(pattern1) + "(.*?)" + Pattern.quote(pattern2));
			m = p.matcher(response.toString());
			while(m.find()){
				data = m.group(1);
			}
			break;
		case "StudyInstanceUID":
			response = fetchData("/studies/" + this.id);
			pattern1 = "\"StudyInstanceUID\" : \"";
			pattern2 = "\"";
			p = Pattern.compile(Pattern.quote(pattern1) + "(.*?)" + Pattern.quote(pattern2));
			m = p.matcher(response.toString());
			while(m.find()){
				data = m.group(1);
			}
			break;
		default:
			break;
		}
		return data;
	}
	
	
	/*
	 * extracts data from /studies/.../statistics
	 */
	public String extractStats(String field) throws IOException{
		String response = "";
		String data = "";
		String pattern1;
		String pattern2;
		Pattern p;
		Matcher m;
		switch (field) {
		case "CountInstances":
			response = fetchData("/studies/" + this.id + "/statistics");
			pattern1 = "\"CountInstances\" : ";
			pattern2 = ",";
			p = Pattern.compile(Pattern.quote(pattern1) + "(.*?)" + Pattern.quote(pattern2));
			m = p.matcher(response.toString());
			while(m.find()){
				data = m.group(1);
			}
			break;
		case "CountSeries":
			response = fetchData("/studies/" + this.id + "/statistics");
			pattern1 = "\"CountSeries\" : ";
			pattern2 = ",";
			p = Pattern.compile(Pattern.quote(pattern1) + "(.*?)" + Pattern.quote(pattern2));
			m = p.matcher(response.toString());
			while(m.find()){
				data = m.group(1);
			}
			break;
		case "DiskSize":
			response = fetchData("/studies/" + this.id + "/statistics");
			pattern1 = "\"DiskSizeMB\" : ";
			pattern2 = ",";
			p = Pattern.compile(Pattern.quote(pattern1) + "(.*?)" + Pattern.quote(pattern2));
			m = p.matcher(response.toString());
			while(m.find()){
				data = m.group(1);
			}
			break;		
		default:
			break;
		}
		return data;
	}

}
