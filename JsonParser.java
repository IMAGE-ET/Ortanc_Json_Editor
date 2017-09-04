/**
Copyright (C) 2017 KANOUN Salim
This
 program is free software; you can redistribute it and/or modify
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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import net.sf.packtag.implementation.JSMin;



public class JsonParser extends IndexOrthanc {
	
	private JSONObject orthancJson;
	private FileReader reader ;
	protected File fichierInput;
	private StringWriter out;
	//private JSONParser parser = new JSONParser();
	
	
	public static void main(String[] args) throws IOException, ParseException {
		SettingsGUI gui=new SettingsGUI();
		//On met la fenetre au centre de l ecran
		gui.window.pack();
		gui.window.setLocationRelativeTo(null);
		gui.window.setSize(gui.window.getPreferredSize());
		gui.window.setVisible(true);
	}
	
	protected void definitionFichier() throws Exception {
		 try {
		 reader= new FileReader(fichierInput);
		 } catch (FileNotFoundException e1) {	e1.printStackTrace();}
		 //On passe dans JSMin pour enlever les commentaire avant le parsing
		 out = new StringWriter();
		 JSMin js= new JSMin(reader, out);
		 
		 js.jsmin();
			
		
	}
	
	//Permet de lire le Json et de stocker les objet dans orthancJson
	protected void jsonParser() throws IOException, ParseException {
		 //Parser le fichier JSON dans l'objet
		 try {
			JSONParser parser = new JSONParser();
			orthancJson= (JSONObject) parser.parse(out.toString());
		 } catch (ParseException e) {e.printStackTrace();}
		 parserOrthancJson();
		
	}
	//Evnoie le resultat du parsing dans les variables de l'index qui sert a produire le nouveau Json
	@SuppressWarnings("unchecked")
	protected void parserOrthancJson() throws ParseException {
		try {
			orthancName=(String) orthancJson.get("Name");
			storageDirectory=(String) orthancJson.get("StorageDirectory");
			indexDirectory=(String) orthancJson.get("IndexDirectory");
			StorageCompression=(boolean) orthancJson.get("StorageCompression");
			MaximumStorageSize=Integer.valueOf(orthancJson.get("MaximumStorageSize").toString());
			MaximumPatientCount=Integer.valueOf(orthancJson.get("MaximumPatientCount").toString());
			HttpServerEnabled=(boolean) orthancJson.get("HttpServerEnabled");
			HttpPort=Integer.valueOf(orthancJson.get("HttpPort").toString());
			HttpDescribeErrors=(boolean) orthancJson.get("HttpDescribeErrors");
			HttpCompressionEnabled=(boolean) orthancJson.get("HttpCompressionEnabled");
			DicomServerEnabled=(boolean) orthancJson.get("DicomServerEnabled");
			DicomAet=(String) orthancJson.get("DicomAet");
			DicomCheckCalledAet=(boolean) orthancJson.get("DicomCheckCalledAet");
			DicomPort=Integer.valueOf(orthancJson.get("DicomPort").toString());
			DefaultEncoding=(String) orthancJson.get("DefaultEncoding");
			DeflatedTransferSyntaxAccepted=(boolean) orthancJson.get("DeflatedTransferSyntaxAccepted");
			JpegTransferSyntaxAccepted=(boolean) orthancJson.get("JpegTransferSyntaxAccepted");
			Jpeg2000TransferSyntaxAccepted=(boolean) orthancJson.get("Jpeg2000TransferSyntaxAccepted");
			JpegLosslessTransferSyntaxAccepted=(boolean) orthancJson.get("JpegLosslessTransferSyntaxAccepted");
			JpipTransferSyntaxAccepted=(boolean) orthancJson.get("JpipTransferSyntaxAccepted");
			Mpeg2TransferSyntaxAccepted=(boolean) orthancJson.get("Mpeg2TransferSyntaxAccepted");
			RleTransferSyntaxAccepted=(boolean) orthancJson.get("RleTransferSyntaxAccepted");
			UnknownSopClassAccepted=(boolean) orthancJson.get("UnknownSopClassAccepted");
			DicomScpTimeout=Integer.valueOf(orthancJson.get("DicomScpTimeout").toString());
			RemoteAccessAllowed=(boolean) orthancJson.get("RemoteAccessAllowed");
			SslEnabled=(boolean) orthancJson.get("SslEnabled");
			SslCertificate=(String) orthancJson.get("SslCertificate");
			AuthenticationEnabled=(boolean) orthancJson.get("AuthenticationEnabled");
			DicomScuTimeout=Integer.valueOf(orthancJson.get("DicomScuTimeout").toString());
			HttpProxy=(String) orthancJson.get("HttpProxy");
			HttpTimeout=Integer.valueOf(orthancJson.get("HttpTimeout").toString());
			HttpsVerifyPeers=(boolean) orthancJson.get("HttpsVerifyPeers");
			HttpsCACertificates=(String) orthancJson.get("HttpsCACertificates");
			StableAge=Integer.valueOf(orthancJson.get("StableAge").toString());
			StrictAetComparison=(boolean) orthancJson.get("StrictAetComparison");
			StoreMD5ForAttachments=(boolean) orthancJson.get("StoreMD5ForAttachments");
			LimitFindResults=Integer.valueOf(orthancJson.get("LimitFindResults").toString());
			LimitFindInstances=Integer.valueOf(orthancJson.get("LimitFindInstances").toString());
			LimitJobs=Integer.valueOf(orthancJson.get("LimitJobs").toString());
			LogExportedResources=(boolean) orthancJson.get("LogExportedResources");
			KeepAlive=(boolean) orthancJson.get("KeepAlive");
			StoreDicom=(boolean) orthancJson.get("StoreDicom");
			DicomAssociationCloseDelay=Integer.valueOf(orthancJson.get("DicomAssociationCloseDelay").toString());
			QueryRetrieveSize=Integer.valueOf(orthancJson.get("QueryRetrieveSize").toString());
			CaseSensitivePN=(boolean) orthancJson.get("CaseSensitivePN");
			AllowFindSopClassesInStudy=(boolean) orthancJson.get("AllowFindSopClassesInStudy");
			LoadPrivateDictionary=(boolean) orthancJson.get("LoadPrivateDictionary");
			CheckModalityHost=(boolean)orthancJson.get("DicomCheckModalityHost");
			DicomAlwaysStore=(boolean)orthancJson.get("DicomAlwaysAllowStore");
		}
		catch (NullPointerException e) {
        }
		
		
		
		//on recupere les AET declares par un nouveau parser
		JSONParser parser = new JSONParser();
		IndexOrthanc.dicomNode= (JSONObject) parser.parse(orthancJson.get("DicomModalities").toString());
		
		//On recupere les users
		IndexOrthanc.users= (JSONObject) parser.parse(orthancJson.get("RegisteredUsers").toString());
		
		// On recupere les Lua scripts
		IndexOrthanc.luaFolder= (JSONArray) parser.parse(orthancJson.get("LuaScripts").toString());
		
		// On recupere les plugins
		IndexOrthanc.pluginsFolder= (JSONArray) parser.parse(orthancJson.get("Plugins").toString());
		
		//On recupere les metadata
		IndexOrthanc.userMetadata= (JSONObject) parser.parse(orthancJson.get("UserMetadata").toString());
		
		// On recupere les dictionnary
		IndexOrthanc.dictionary= (JSONObject) parser.parse(orthancJson.get("Dictionary").toString());
		
		// On recupere les Content
		IndexOrthanc.contentType= (JSONObject) parser.parse(orthancJson.get("UserContentType").toString());
		
		// On recupere les Peer
		IndexOrthanc.orthancPeer=(JSONObject) parser.parse(orthancJson.get("OrthancPeers").toString());
		
	}
	


}
