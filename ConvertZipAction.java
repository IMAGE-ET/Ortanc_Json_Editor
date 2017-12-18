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



import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.prefs.Preferences;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ConvertZipAction{
	DateFormat df = new SimpleDateFormat("MM_dd_yyyy_HHmmss");
	private StringBuilder ids;
	private Path path;
	private String fullAddress;
	private String authentication;
	private Preferences jpreferAnon = Preferences.userRoot().node("<unnamed>/anonPlugin");
	private ArrayList<String> zipContent;
	private JButton exportZip;
	private JLabel state;
	private JButton removeFromZip;
	private JButton storeBtn;
	private JButton displayAnonTool;
	private JComboBox<String> comboToolChooser;
	private JPanel oToolRight;
	private JPanel anonTablesPanel;
	private boolean choix  = false;
	private JComboBox<Object> zipShownContent;
	private ArrayList<String> zipShownContentList;
	private String setupPath;
	private File f;
	private boolean temporary = false;
	
public ConvertZipAction(String[] parametresTableau){
		//On set les parametres dans l'objet export ZIP
		fullAddress=parametresTableau[0];
		authentication=parametresTableau[1];
	}
	
	public void setConvertZipAction(String file, ArrayList<String> zipContent, boolean temporary){
		
		this.setupPath = file;
		this.zipContent = zipContent;
		this.temporary = temporary;
	}

	public void setConvertZipAction(JComboBox<Object> zipShownContent, ArrayList<String> zipShownContentList, 
			ArrayList<String> zipContent, JButton exportZip, JLabel state, JPanel oToolRight,
			JButton displayAnonTool, JPanel anonTablesPanel, JButton removeFromZip, JButton storeBtn, JComboBox<String> comboToolChooser){
		this.oToolRight = oToolRight;
		this.storeBtn = storeBtn;
		this.removeFromZip = removeFromZip;
		this.displayAnonTool = displayAnonTool;
		this.anonTablesPanel = anonTablesPanel;
		this.zipShownContent = zipShownContent;
		this.zipShownContentList = zipShownContentList;
		this.ids = new StringBuilder();
		this.zipContent = zipContent;
		this.exportZip = exportZip;
		this.state = state;
		this.setupPath = null;
		this.comboToolChooser=comboToolChooser;
	}

	public void generateZip(boolean dicomDir) throws IOException {
		// storing the IDs in a stringbuilder
		this.ids = new StringBuilder();
		this.ids.append("[");
		for(int i = 0; i < zipContent.size(); i++){
			this.ids.append("\"" + zipContent.get(i) + "\",");
		}
		ids.replace(ids.length()-1, ids.length(), "]");

		// the absence of a setupPath or not, will define whether or not a jfilechooser will be used
		
		if(setupPath != null){
			if(temporary){
				f = File.createTempFile(setupPath + File.separator + df.format(new Date()), ".zip");
				f.deleteOnExit();
			}else{
				f = new File(setupPath);
			}
		}
		
		if(setupPath == null){
			f= this.fileChooser();
		}
		
		if(!zipContent.isEmpty() && (this.setupPath != null || choix)){
			InputStream is = null;
			FileOutputStream fos = null;
			try {
				if(setupPath == null){
					exportZip.setText("Generating Zip...");
					exportZip.setEnabled(false);
					storeBtn.setEnabled(false);
					removeFromZip.setEnabled(false);
					comboToolChooser.setEnabled(false);
					state.setText("Generating Zip...");
				}
				//On defini l'adresse de l'API
				String url = null;
				if (!dicomDir) url=fullAddress+"/tools/create-archive" ; else url=fullAddress+"/tools/create-media?extended";
				URL url2 = new URL(url);
				HttpURLConnection conn = (HttpURLConnection) url2.openConnection();
				conn.setDoOutput(true);
				conn.setRequestMethod("POST");
				if((fullAddress != null && fullAddress.contains("https"))){
					try{
						HttpsTrustModifierAnon.Trust(conn);
					}catch (Exception e){
						throw new IOException("Cannot allow self-signed certificates");
					}
				}
				if(authentication != null){
					conn.setRequestProperty("Authorization", "Basic " + authentication);
				}

				OutputStream os = conn.getOutputStream();
				os.write((ids.toString()).getBytes());
				os.flush();

				is = conn.getInputStream();
				fos = new FileOutputStream(f);
				int bytesRead = -1;
				byte[] buffer = new byte[1024];
				while ((bytesRead = is.read(buffer)) != -1) {
					fos.write(buffer, 0, bytesRead);
				}
				fos.close();
				is.close();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
			if(setupPath == null){
				state.setText("<html><font color='green'>The data have successfully been exported to zip</font></html>");
				exportZip.setText("Export list");
				exportZip.setEnabled(true);
				storeBtn.setEnabled(true);
				removeFromZip.setEnabled(true);
				comboToolChooser.setEnabled(true);
				zipShownContent.removeAllItems();
				zipShownContentList.removeAll(zipShownContentList);
				zipContent.removeAll(zipContent);
				oToolRight.setVisible(false);
				displayAnonTool.setVisible(true);
				displayAnonTool.setText("Display anonymization tool");
				anonTablesPanel.setVisible(false);
			}
		}
	}

	private File fileChooser(){
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new java.io.File(jpreferAnon.get("zipLocation", System.getProperty("user.dir"))));
		chooser.setSelectedFile(new File(df.format(new Date()) + ".zip"));
		chooser.setDialogTitle("Export zip to...");
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		chooser.setAcceptAllFileFilterUsed(false);
		File selected=null;
		if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
			this.path = chooser.getSelectedFile().toPath();
			selected=chooser.getSelectedFile();
			jpreferAnon.put("zipLocation", this.path.toString());
			this.choix=true;
		}
		return selected;
	}

	public String getGeneratedZipPath(){
		return this.f.getAbsolutePath();
	}

	public String getGeneratedZipName(){
		return this.f.getName();
	}

}
