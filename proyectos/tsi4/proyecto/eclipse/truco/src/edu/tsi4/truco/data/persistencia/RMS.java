package edu.tsi4.truco.data.persistencia;

import javax.microedition.io.*;
import javax.microedition.rms.*;

public class RMS {
	private RecordStore rs = null;
	
	public RMS(String id) throws Exception{
		try{
			rs = RecordStore.openRecordStore(id, true );
	    }catch (Exception e){
	    	throw e;
	    }
	}
	public void openRecStore(String id){
	    try{
	      rs = RecordStore.openRecordStore(id, true );
	    }catch (Exception e){}
	  }    

	  public void closeRecStore(){
	    try{
	      rs.closeRecordStore();
	    }catch (Exception e){}
	  }

	  public void deleteRecStore(String id){
	    if (RecordStore.listRecordStores() != null){
	      try{
	        RecordStore.deleteRecordStore(id);
	      }catch (Exception e){}
	    }      
	  }

	  public int writeRecord(String str){
	    byte[] rec = str.getBytes();
	    try{
	      return rs.addRecord(rec, 0, rec.length);
	    }catch (Exception e){
	    	System.out.println("ERROR AL SALVAR RECORD: "+str);
	    	return -1;
	    }
	  }
	  
	  public void writeRecord(int id, String str){
	    byte[] rec = str.getBytes();
	    try{
	      rs.setRecord(id, rec, 0, rec.length);
	    }catch (Exception e){}
	  }

	  public void readRecords(){
	    try{
	      byte[] recData = new byte[5]; 
	      int len;
	      
	      for(int i = 1; i <= rs.getNumRecords(); i++){
	        if(rs.getRecordSize(i) > recData.length){
	          recData = new byte[rs.getRecordSize(i)];
	        }
	        len = rs.getRecord(i, recData, 0);
	        System.out.println("------------------------------");
	        System.out.println("Record " + i + " : " + new String(recData, 0, len));
	        System.out.println("------------------------------");                        
	      }
	    }catch (Exception e){}
	  }
	  
	  public String readRecord(int i) throws Exception{
		    try{
		      byte[] recData = new byte[5]; 
		      int len;
		      
		      if(i <= rs.getNumRecords()){
		        if(rs.getRecordSize(i) > recData.length){
		          recData = new byte[rs.getRecordSize(i)];
		        }
		        len = rs.getRecord(i, recData, 0);
		        return new String(recData, 0, len);
                      
		      }
		      else {
		    	  throw new Exception("registro numero: "+i+" no existe.");
		      }
		    }catch (Exception e){
		    	throw e;
		    }
		  }
	  
	  public int cantRecords() throws Exception{
		  try{
			  return rs.getNumRecords();
		  }catch (Exception e){
		    	throw e;
		    }
	  }
}
  

 