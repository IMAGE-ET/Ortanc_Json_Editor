import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

//EFFACEMENT DES TEMP DIRECTORY A GERER CF PROGRAMME MAXIME
public class ZipAndViewer {
	File zipDicom;
	File viewerPackage = new File("C:\\Users\\kanoun_s\\Documents\\Test Creation CD\\ImageJ.zip");
	Path folderTempImage;
	Path folderTempViewer;
	File destination;
	
	public ZipAndViewer(File zipDicom, File destination) throws IOException {
		this.zipDicom=zipDicom;
		this.destination=destination;
		unzip();
		addViewer();
	}
	
	public void unzip(){
	     byte[] buffer = new byte[1024];

	     try {
	    
	    	//get the zip file content
	    	ZipInputStream zis;
			zis = new ZipInputStream(new FileInputStream(zipDicom));
			
	    	//get the zipped file list entry
	    	ZipEntry ze = zis.getNextEntry();
	    	
	    	//textArea.append("Unzipping ");
	    	
	    	while(ze!=null){

	     	   String fileName = ze.getName();
	            File newFile = new File(destination + File.separator + fileName);
	            
	            if (ze.isDirectory()) {
	         	// if the entry is a directory, make the directory
	                newFile.mkdirs();
	            }
	            else {
	         	    new File(newFile.getParent()).mkdirs();
	                 //create all non exists folders
	                 //else you will hit FileNotFoundException for compressed folder
	                

	                 FileOutputStream fos = new FileOutputStream(newFile);

	                 int len;
	                 while ((len = zis.read(buffer)) > 0) {
	            		fos.write(buffer, 0, len);
	                 }

	                 fos.close();
	                 
	            }
	            ze = zis.getNextEntry();
	     	}

	        zis.closeEntry();
	    	zis.close();
	    	//On efface le ZIP et le repertoier qu'on a extrait
	    	//FileUtils.deleteDirectory(folder.getParentFile());
	    	
	    	//textArea.append("original file deleted \n");
	    
	     } catch (IOException e) {
				e.printStackTrace();
			}
	     
	     
	}
	
	public void addViewer() throws IOException{
		byte[] buffer = new byte[1024];
    	//get the zip file content
    	ZipInputStream zis;
		zis = new ZipInputStream(new FileInputStream(viewerPackage));
		
    	//get the zipped file list entry
    	ZipEntry ze = zis.getNextEntry();
    	
    	//textArea.append("Unzipping ");
    	// MISE A JOUR METHODE A UPDATER DANS LE PROGRAMME DE MAXIME
    	while(ze!=null){

    	   String fileName = ze.getName();
           File newFile = new File(destination + File.separator + fileName);
           
           if (ze.isDirectory()) {
        	// if the entry is a directory, make the directory
               newFile.mkdirs();
           }
           else {
        	    new File(newFile.getParent()).mkdirs();
                //create all non exists folders
                //else you will hit FileNotFoundException for compressed folder
               

                FileOutputStream fos = new FileOutputStream(newFile);

                int len;
                while ((len = zis.read(buffer)) > 0) {
           		fos.write(buffer, 0, len);
                }

                fos.close();
                
           }
           ze = zis.getNextEntry();
    	}
    	
        zis.closeEntry();
    	zis.close();
	}
	
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		ZipAndViewer zip= new ZipAndViewer(new File("C:\\Users\\kanoun_s\\Documents\\Test Creation CD\\IMAGES.zip"), new File("C:\\Users\\kanoun_s\\Documents\\Test Creation CD\\Resultats\\"));
	}

}
