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



import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingWorker;

public class DeleteActionMainPanel extends AbstractAction{

	private static final long serialVersionUID = 1L;
	private String url;
	private String fullAddress;
	private String level;
	private String authentication;
	private TableDataSeries modeleSeries;
	private TableDataStudies modeleStudies;
	private TableDataPatientsAnon modelePatients;
	private JTable tableauSeries;
	private JTable tableauStudies;
	private JTable tableauPatients;
	private JLabel state;
	private JFrame frame;
	private JButton searchBtn;

	public DeleteActionMainPanel(String[] parametresTableau, String level, TableDataStudies modeleStudies, JTable tableauStudies, TableDataSeries modeleSeries, 
			JTable tableauSeries, TableDataPatientsAnon modelePatients, JTable tableauPatients, JLabel state, JFrame frame, JButton searchBtn){
		
		fullAddress=parametresTableau[0];
		authentication=parametresTableau[1];

		this.level = level;
		this.modelePatients = modelePatients;
		this.modeleStudies = modeleStudies;
		this.modeleSeries = modeleSeries;
		this.tableauPatients = tableauPatients;
		this.tableauStudies = tableauStudies;
		this.tableauSeries = tableauSeries;
		this.state = state;
		this.frame = frame;
		this.searchBtn = searchBtn;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

		if(level.equals("Study")){
			this.url=fullAddress+"/studies/" + modeleStudies.getValueAt(tableauStudies.convertRowIndexToModel(tableauStudies.getSelectedRow()), 3);
		}else if(level.equals("Serie")){
			this.url=fullAddress+"/series/" + modeleSeries.getValueAt(tableauSeries.convertRowIndexToModel(tableauSeries.getSelectedRow()), 4);
		}else{
			this.url=fullAddress+"/patients/" + modelePatients.getValueAt(tableauPatients.convertRowIndexToModel(tableauPatients.getSelectedRow()), 2);
		}

		SwingWorker<Void,Void> worker = new SwingWorker<Void,Void>(){
			int dialogResult = JOptionPane.NO_OPTION;
			int selectedRow = 0;
			String patientName = "";
			String patientID = "";
			String patientUID = "";
			String studyUID = "";
			@Override
			protected Void doInBackground() {

				patientName = tableauPatients.getValueAt(tableauPatients.getSelectedRow(), 0).toString();
				patientID = tableauPatients.getValueAt(tableauPatients.getSelectedRow(), 1).toString();
				patientUID = tableauPatients.getValueAt(tableauPatients.getSelectedRow(), 2).toString();
				if(level.equals("Serie")){
					studyUID = tableauStudies.getValueAt(tableauStudies.getSelectedRow(), 3).toString();
				}
				try {
					URL url2 = new URL(url);
					HttpURLConnection conn;
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
					if(!level.equals("Serie")){
						String txtLabel = "";
						// SETTING THE DIALOG FOR STUDY
						if(level.equals("Study")){
							selectedRow = tableauStudies.convertRowIndexToModel(tableauStudies.getSelectedRow());
							dialogResult = JOptionPane.showConfirmDialog (null, "Are you sure you want to delete this study (" 
									+modeleStudies.getValueAt(selectedRow, 1) 
									+") ?","Warning deleting study",JOptionPane.YES_NO_OPTION);
							txtLabel = " study (" + modeleStudies.getValueAt(tableauStudies.convertRowIndexToModel(tableauStudies.getSelectedRow()), 1);
						}// SETTING THE DIALOG FOR PATIENT
						else{
							selectedRow = tableauPatients.convertRowIndexToModel(tableauPatients.getSelectedRow());
							dialogResult = JOptionPane.showConfirmDialog (null, "Are you sure you want to delete this patient ("
									+ modelePatients.getValueAt(selectedRow, 0) 
									+ ") ?","Warning deleting patient",JOptionPane.YES_NO_OPTION);
							txtLabel =  " patient (" + modelePatients.getValueAt(tableauPatients.convertRowIndexToModel(tableauPatients.getSelectedRow()), 0);
						}// YES OPTION
						if(dialogResult == JOptionPane.YES_OPTION){
							state.setText("<html>Deleting a" + txtLabel + ") <font color='red'> <br>"
									+ "(Do not use the toolbox while the current operation is not done)</font></html>");
							conn.setRequestMethod("DELETE");
						}
					}else{// DELETING SERIE WITHOUT DIALOG
						selectedRow = tableauSeries.convertRowIndexToModel(tableauSeries.getSelectedRow());
						state.setText("<html>Deleting a serie (" + modeleSeries.getValueAt(selectedRow, 1) + ") <font color='red'> <br>(Do not use the toolbox while the current operation is not done)</font></html>");
						conn.setRequestMethod("DELETE");
					}
					frame.pack();
					conn.getResponseCode();
					conn.disconnect();
				} catch (IOException e) {
					e.printStackTrace();
				}
				return null;
			}
			@Override
			protected void done(){
				// Checking whether or not the deleted element was the last one so that 
				// we can delete the other levels
				try {
					if(dialogResult == JOptionPane.YES_OPTION || level.equals("Serie")){
						if(level.equals("Study")){
							modeleSeries.clear();
							if(tableauStudies.getRowCount() > 1){
								modeleStudies.clear();
								modeleStudies.addStudy(patientName, patientID, patientUID);
							}else{
								modeleStudies.clear();
								if(tableauPatients.getRowCount() > 1){
									modelePatients.clear();
									searchBtn.doClick();
								}else{
									modelePatients.clear();										
								}
							}
							state.setText("<html><font color='green'>The study has been deleted</font></html>");
						}else if(level.equals("Serie")){
							if(tableauSeries.getRowCount() > 1){
								modeleSeries.clear();
								modeleSeries.addSerie(studyUID);
							}else{
								modeleSeries.clear();
								if(tableauStudies.getRowCount() > 1){
									modeleStudies.clear();
									modeleStudies.addStudy(patientName, patientID, patientUID);
								}else{
									modeleStudies.clear();
									if(tableauPatients.getRowCount() > 1){
										modelePatients.clear();
										searchBtn.doClick();
									}else{
										modelePatients.clear();										
									}
								}
							}
							state.setText("<html><font color='green'>The serie has been deleted</font></html>");
						}else{
							modeleSeries.clear();
							modeleStudies.clear();
							if(tableauPatients.getRowCount() > 1){
								modelePatients.clear();
								searchBtn.doClick();
							}else{
								modelePatients.clear();	
							}
							searchBtn.doClick();
							state.setText("<html><font color='green'>The patient has been deleted</font></html>");
						}
					}
				}
				catch (IOException e) {
					e.printStackTrace();
				} catch (ParseException e) {
					e.printStackTrace();
				}
				frame.pack();
			}
		};
		worker.execute();
	}
	
}