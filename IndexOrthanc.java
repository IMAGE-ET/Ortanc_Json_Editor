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

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class IndexOrthanc {
	
	protected JSONObject index = new JSONObject();
	
	// Hasmap des AET
	protected static HashMap<String,JSONArray> dicomNode=new HashMap<String,JSONArray>();
	// Hasmap des orthancPeer
	protected static HashMap<String,JSONArray> orthancPeer=new HashMap<String,JSONArray>();
	// Hasmap des contentType
	protected static HashMap<String,JSONArray> contentType=new HashMap<String,JSONArray>();
	// Hasmap des dictionary
	protected static HashMap<String,JSONArray> dictionary=new HashMap<String,JSONArray>();
	
	// Array des Lua folder 
	protected static JSONArray luaFolder=new JSONArray();
	
	//Array des plugin folder
	protected static JSONArray pluginsFolder=new JSONArray();
	
	// Object pour les users
	protected static JSONObject users =new JSONObject();
	
	//Object pour les metadata
	protected static JSONObject userMetadata=new JSONObject();
	
	//OrthancPeer JsonObject
	protected static JSONObject orthancPeers =new JSONObject();
	
	//DicomNode JSON object
	private JSONObject dicom =new JSONObject();
	
	//Object pour les content Type
	private JSONObject contentTypes=new JSONObject();
	
	//Object pour les dictionnaire
	private JSONObject dictionaries=new JSONObject();
	
	// A modifier via des setteurs
	protected static String orthancName;
	protected static String storageDirectory;
	protected static String indexDirectory;
	protected static boolean StorageCompression;
	protected static int MaximumStorageSize;
	protected static int MaximumPatientCount;
	protected static boolean HttpServerEnabled;
	protected static int HttpPort;
	protected static boolean HttpDescribeErrors;
	protected static boolean HttpCompressionEnabled;
	protected static boolean DicomServerEnabled;
	protected static String DicomAet;
	protected static boolean DicomCheckCalledAet;
	protected static int DicomPort;
	protected static String DefaultEncoding;
	protected static boolean DeflatedTransferSyntaxAccepted;
	protected static boolean JpegTransferSyntaxAccepted;
	protected static boolean Jpeg2000TransferSyntaxAccepted;
	protected static boolean JpegLosslessTransferSyntaxAccepted;
	protected static boolean JpipTransferSyntaxAccepted;
	protected static boolean Mpeg2TransferSyntaxAccepted;
	protected static boolean RleTransferSyntaxAccepted;
	protected static boolean UnknownSopClassAccepted;
	protected static int DicomScpTimeout;
	protected static boolean RemoteAccessAllowed;
	protected static boolean SslEnabled;
	protected static String SslCertificate;
	protected static boolean AuthenticationEnabled;
	protected static int DicomScuTimeout;
	protected static String HttpProxy;
	protected static int HttpTimeout;
	protected static boolean HttpsVerifyPeers;
	protected static String HttpsCACertificates;
	protected static int StableAge;
	protected static boolean StrictAetComparison;
	protected static boolean StoreMD5ForAttachments;
	protected static int LimitFindResults;
	protected static int LimitFindInstances;
	protected static int LimitJobs;
	protected static boolean LogExportedResources;
	protected static boolean KeepAlive;
	protected static boolean StoreDicom;
	protected static int DicomAssociationCloseDelay;
	protected static int QueryRetrieveSize;
	protected static boolean CaseSensitivePN;
	protected static boolean AllowFindSopClassesInStudy;
	protected static boolean LoadPrivateDictionary;
	protected static boolean DicomAlwaysStore;
	protected static boolean CheckModalityHost;
	
	public IndexOrthanc() {
		initialiserIndex();
	}
	
	protected void initialiserIndex() {
		// on Set des valeurs par defaut
				IndexOrthanc.orthancName="myOrthanc";
				IndexOrthanc.storageDirectory="C:\\Orthanc\\OrthancStorage-v6";
				IndexOrthanc.indexDirectory="C:\\Orthanc\\OrthancStorage-v6";
				IndexOrthanc.StorageCompression=false;
				IndexOrthanc.MaximumStorageSize=0;
				IndexOrthanc.MaximumPatientCount=0;
				IndexOrthanc.HttpServerEnabled=true;
				IndexOrthanc.HttpPort=8042;
				IndexOrthanc.HttpDescribeErrors=true;
				IndexOrthanc.HttpCompressionEnabled=true;
				IndexOrthanc.DicomServerEnabled=true;
				IndexOrthanc.DicomAet="Orthanc";
				IndexOrthanc.DicomCheckCalledAet=false;
				IndexOrthanc.DicomPort=4242;
				IndexOrthanc.DefaultEncoding="Latin1";
				IndexOrthanc.DeflatedTransferSyntaxAccepted=true;
				IndexOrthanc.JpegTransferSyntaxAccepted=true;
				IndexOrthanc.Jpeg2000TransferSyntaxAccepted=true;
				IndexOrthanc.JpegLosslessTransferSyntaxAccepted=true;
				IndexOrthanc.JpipTransferSyntaxAccepted=true;
				IndexOrthanc.Mpeg2TransferSyntaxAccepted=true;
				IndexOrthanc.RleTransferSyntaxAccepted=true;
				IndexOrthanc.UnknownSopClassAccepted=false;
				IndexOrthanc.DicomScpTimeout=30;
				IndexOrthanc.RemoteAccessAllowed=false;
				IndexOrthanc.SslEnabled=false;
				IndexOrthanc.SslCertificate="certificate.pem";
				IndexOrthanc.AuthenticationEnabled=false;
				IndexOrthanc.DicomScuTimeout=10;
				IndexOrthanc.HttpsVerifyPeers=true;
				IndexOrthanc.HttpProxy="";
				IndexOrthanc.HttpsCACertificates="";
				IndexOrthanc.StableAge=60;
				IndexOrthanc.StrictAetComparison=false;
				IndexOrthanc.StoreMD5ForAttachments=true;
				IndexOrthanc.LimitFindResults=0;
				IndexOrthanc.LimitFindInstances=0;
				IndexOrthanc.LimitJobs=10;
				IndexOrthanc.LogExportedResources=true;
				IndexOrthanc.KeepAlive=false;
				IndexOrthanc.StoreDicom=true;
				IndexOrthanc.DicomAssociationCloseDelay=5;
				IndexOrthanc.QueryRetrieveSize=10;
				IndexOrthanc.CaseSensitivePN=false;
				IndexOrthanc.AllowFindSopClassesInStudy=false;
				IndexOrthanc.LoadPrivateDictionary=true;
				IndexOrthanc.DicomAlwaysStore=true;
				IndexOrthanc.CheckModalityHost=false;
	}
	
	// permet de creer le JSON avant de l'ecrire
	@SuppressWarnings("unchecked")
	public void construireIndex() {
		
		//On construit les objets dont on aura besoin
		buildOrthancPeer(orthancPeer);
		buildDicom(dicomNode);
		buildContentType(contentType);
		buildDictionary(dictionary);
		
		//On rentre les valeurs contenue dans les variables
		index.put("Name", orthancName);
		index.put("StorageDirectory", storageDirectory);
		index.put("IndexDirectory", indexDirectory);
		index.put("StorageCompression", StorageCompression);
		index.put("MaximumStorageSize", MaximumStorageSize);
		index.put("MaximumPatientCount", MaximumPatientCount);
		index.put("LuaScripts", luaFolder);
		index.put("Plugins", pluginsFolder);
		index.put("HttpServerEnabled", HttpServerEnabled);
		index.put("HttpPort", HttpPort);
		index.put("HttpDescribeErrors", HttpDescribeErrors);
		index.put("HttpCompressionEnabled", HttpCompressionEnabled);
		index.put("DicomServerEnabled", DicomServerEnabled);
		index.put("DicomAet", DicomAet);
		index.put("DicomCheckCalledAet", DicomCheckCalledAet);
		index.put("DicomPort", DicomPort);
		index.put("DefaultEncoding", DefaultEncoding);
		index.put("DeflatedTransferSyntaxAccepted", DeflatedTransferSyntaxAccepted);
		index.put("JpegTransferSyntaxAccepted", JpegTransferSyntaxAccepted);
		index.put("Jpeg2000TransferSyntaxAccepted", Jpeg2000TransferSyntaxAccepted);
		index.put("JpegLosslessTransferSyntaxAccepted", JpegLosslessTransferSyntaxAccepted);
		index.put("JpipTransferSyntaxAccepted", JpipTransferSyntaxAccepted);
		index.put("Mpeg2TransferSyntaxAccepted", Mpeg2TransferSyntaxAccepted);
		index.put("RleTransferSyntaxAccepted", RleTransferSyntaxAccepted);
		index.put("UnknownSopClassAccepted", UnknownSopClassAccepted);
		index.put("DicomScpTimeout", DicomScpTimeout);
		index.put("RemoteAccessAllowed", RemoteAccessAllowed);
		index.put("SslEnabled", SslEnabled);
		index.put("SslCertificate", SslCertificate);
		index.put("AuthenticationEnabled", AuthenticationEnabled);
		index.put("RegisteredUsers", users);
		index.put("DicomModalities", dicom);
		index.put("DicomScuTimeout", DicomScuTimeout);
		index.put("OrthancPeers", orthancPeers);
		index.put("HttpProxy", HttpProxy);
		index.put("HttpTimeout", HttpTimeout);
		index.put("HttpsVerifyPeers", HttpsVerifyPeers);
		index.put("HttpsCACertificates", HttpsCACertificates);
		index.put("UserMetadata", userMetadata);
		index.put("UserContentType", contentTypes);
		index.put("StableAge", StableAge);
		index.put("StrictAetComparison", StrictAetComparison);
		index.put("StoreMD5ForAttachments", StoreMD5ForAttachments);
		index.put("LimitFindResults", LimitFindResults);
		index.put("LimitFindInstances", LimitFindInstances);
		index.put("LimitJobs", LimitJobs);
		index.put("LogExportedResources", LogExportedResources);
		index.put("KeepAlive", KeepAlive);
		index.put("StoreDicom", StoreDicom);
		index.put("DicomAssociationCloseDelay", DicomAssociationCloseDelay);
		index.put("QueryRetrieveSize", QueryRetrieveSize);
		index.put("CaseSensitivePN", CaseSensitivePN);
		index.put("AllowFindSopClassesInStudy", AllowFindSopClassesInStudy);
		index.put("LoadPrivateDictionary", LoadPrivateDictionary);
		index.put("Dictionary", dictionaries);
		index.put("DicomAlwaysAllowStore", DicomAlwaysStore);
		index.put("DicomCheckModalityHost", CheckModalityHost);
	}

	/**
	 * Ajoute des utilisateurs pour Orthanc
	 * @param user
	 * @param password
	 */
	@SuppressWarnings("unchecked")
	protected static void addusers(String user, String password) {
		//on ajoute l'utilisateur a l'array de users
		users.put(user, password);
	}
	
	/**
	 * Ajoute des metadata
	 * @param user
	 * @param number
	 */
	
	@SuppressWarnings("unchecked")
	protected static void addUserMetadata(String user, int number) {
		//on ajoute l'utilisateur a l'array des metadata
		userMetadata.put(user, number);
	}
	
	
	/**
	 * Ajoute un repertoire lua a la liste des repertoire lua
	 * @param path
	 */
	@SuppressWarnings("unchecked")
	protected static void addLua(String path) {
		luaFolder.add(path);
	}
	
	/**
	 * Ajoute un repertoire plugin  a la liste des repertoire plugin
	 * @param path
	 */
	@SuppressWarnings("unchecked")
	protected static void addplugins(String path) {
		pluginsFolder.add(path);
	}
	
	/**
	 * Cree le JSON object des noeuds DICOM � partir de la Hashmap qui contient le nom du noeud et son array
	 * @param dicomNode :Hashmap des noeuds declares
	 * @return : le JSON object a inject
	 */
	@SuppressWarnings("unchecked")
	public void buildDicom(HashMap<String,JSONArray> dicomNode) {
		String[] noms=new String[dicomNode.size()];
		dicomNode.keySet().toArray(noms);
		for (int i=0 ; i<dicomNode.size() ; i++) {
			dicom.put(noms[i], dicomNode.get(noms[i]));
		}
	}
	
	/**
	 * Ajoute un AET dans la declaration
	 * @param nom
	 * @param name
	 * @param ip
	 * @param port
	 * @param wildcard
	 */
	@SuppressWarnings("unchecked")
	protected static void addDicomNode(String nom, String name, String ip, int port, String wildcard) {
		JSONArray dicomNode=new JSONArray();
		dicomNode.add(name);
		dicomNode.add(ip);
		dicomNode.add(port);
		dicomNode.add(wildcard);
		IndexOrthanc.dicomNode.put(nom,dicomNode); 
	}
	/**
	 * Cree un peer Ortanc et l'ajoute dans la hashmap Orthanc Peer
	 * @param nom
	 * @param URL
	 * @param login
	 * @param password
	 */
	@SuppressWarnings("unchecked")
	protected static void addorthancPeer(String nom, String URL, String login, String password) {
		JSONArray orthancPeer=new JSONArray();
		orthancPeer.add(URL);
		orthancPeer.add(login);
		orthancPeer.add(password);
		IndexOrthanc.orthancPeer.put(nom,orthancPeer); 
	}
	
	/**
	 * Ajoute les content type dans la hashmap
	 * @param name
	 * @param number
	 * @param mime
	 */
	@SuppressWarnings("unchecked")
	protected static void addContentType(String name, int number, String mime) {
		JSONArray contentType=new JSONArray();
		contentType.add(number);
		contentType.add(mime);
		IndexOrthanc.contentType.put(name,contentType); 
	}
	
	/**
	 * Ajoute des valeurs dans le dictionnaire
	 * @param name
	 * @param vr
	 * @param tag
	 * @param minimum
	 * @param maximum
	 * @param privateCreator
	 */
	@SuppressWarnings("unchecked")
	protected static void addDictionary(String name, String vr, String tag, int minimum, int maximum, String privateCreator) {
		JSONArray dictionary=new JSONArray();
		dictionary.add(vr);
		dictionary.add(tag);
		dictionary.add(minimum);
		dictionary.add(maximum);
		dictionary.add(privateCreator);
		IndexOrthanc.dictionary.put(name,dictionary); 
	}
	
	// les build permet de generer les Objet JSON qui seront mis dans le JSON global
	
	@SuppressWarnings("unchecked")
	public void buildOrthancPeer(HashMap<String,JSONArray> peers) {
		String[] noms=new String[peers.size()];
		peers.keySet().toArray(noms);
		for (int i=0 ; i<peers.size() ; i++) {
			IndexOrthanc.orthancPeers.put(noms[i], peers.get(noms[i]));
		}
	}
	
	@SuppressWarnings("unchecked")
	public void buildContentType(HashMap<String,JSONArray> contentType) {
		String[] noms=new String[contentType.size()];
		contentType.keySet().toArray(noms);
		for (int i=0 ; i<contentType.size() ; i++) {
			this.contentTypes.put(noms[i], contentType.get(noms[i]));
		}
	}
	
	
	@SuppressWarnings("unchecked")
	public void buildDictionary(HashMap<String,JSONArray> dictionary) {
		String[] noms=new String[dictionary.size()];
		dictionary.keySet().toArray(noms);
		for (int i=0 ; i<dictionary.size() ; i++) {
			this.dictionaries.put(noms[i], dictionary.get(noms[i]));
		}
	}
	
	/**
	 * Permet d'ecrire le JSON final dans un fichier
	 * @param json
	 * @param fichier
	 */
	public void writeJson(JSONObject json, File fichier) {
		//Pour un export plus lisible on utilise Gson
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String jsonString = gson.toJson(json);
		//Pour ecrire le Json
		BufferedWriter writer=null;
		try {
			writer = new BufferedWriter(new java.io.FileWriter(fichier));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			writer.write(jsonString);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
		
}