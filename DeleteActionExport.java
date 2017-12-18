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



import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.swing.JTable;
import javax.swing.SwingWorker;

public class DeleteActionExport{

	private String url;
	private String fullAddress;
	private String authentication;
	private JTable tableauExportStudies;
	private TableDataExportStudies modeleExportStudies;
	private JTable tableauExportSeries;
	private TableDataExportSeries modeleExportSeries;

	public DeleteActionExport(String[] parametresTableau,JTable tableauExportStudies, TableDataExportStudies modeleExportStudies){
		fullAddress=parametresTableau[0];
		authentication=parametresTableau[1];

		this.tableauExportStudies = tableauExportStudies;
		this.modeleExportStudies = modeleExportStudies;
	}

	public DeleteActionExport(String[] parametresTableau, JTable tableauExportSeries, TableDataExportSeries modeleExportSeries){
		fullAddress=parametresTableau[0];
		authentication=parametresTableau[1];
		
		this.tableauExportSeries = tableauExportSeries;
		this.modeleExportSeries = modeleExportSeries;
	}

	public void delete() {
		if(tableauExportStudies != null){
			this.url=fullAddress+"/studies/" + modeleExportStudies.getValueAt(tableauExportStudies.convertRowIndexToModel(tableauExportStudies.getSelectedRow()), 5);
		}else{
			this.url=fullAddress+ "/series/" + modeleExportSeries.getValueAt(tableauExportSeries.convertRowIndexToModel(tableauExportSeries.getSelectedRow()), 4);
		}
		SwingWorker<Void,Void> worker = new SwingWorker<Void,Void>(){
			@Override
			protected Void doInBackground() {
				URL url2;
				HttpURLConnection conn;
				try {
					url2  = new URL(url);
					conn = (HttpURLConnection) url2.openConnection();
					conn.setDoOutput(true);
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
					conn.setRequestMethod("DELETE");
					conn.getResponseCode();
					conn.disconnect();
				} catch (IOException e) {
					e.printStackTrace();
				}
				return null;
			}
		};
		worker.execute();
	}

}
