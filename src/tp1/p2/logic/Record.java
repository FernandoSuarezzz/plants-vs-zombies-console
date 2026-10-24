package tp1.p2.logic;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import tp1.p2.control.Level;
import tp1.p2.control.exceptions.RecordException;
import tp1.p2.view.Messages;

public class Record {

	private static List<Record> datos = new ArrayList<>();
	
	private int currentRecord;
	private Level l;
	
	public Record(Level level, int currentRecord) {
		this.l = level;
		this.currentRecord = currentRecord;
	}
	
	  static Record loadRecord(Level level) throws RecordException{

		 BufferedReader in=null;
		try {
			in = new BufferedReader(new FileReader(Messages.RECORD_FILENAME));
			String c;
			String aux[];
			datos.clear();

			while((c = in.readLine()) != null) {
				aux = c.split(":");
				
				if(aux.length!=2 || Level.valueOfIgnoreCase(aux[0])==null)throw new RecordException(Messages.RECORD_READ_ERROR); // each line is LEVEL:record
			
				datos.add(new Record(Level.valueOfIgnoreCase(aux[0]), Integer.parseInt(aux[1])));
			}
			
			for(int i = 0; i<datos.size();i++) {
				Record ActualRecord = datos.get(i);
				if(ActualRecord.l == level) return ActualRecord;
			}
			
			Record record = new Record(level, 0); // level not in the file yet: record 0
			datos.add(record);
			return record;

			
		}catch(FileNotFoundException e){ // no record file yet: every level starts with record 0
			Record record = new Record(level, 0);
			datos.add(record);
			return record;
		}catch(NumberFormatException e){
			throw new RecordException(Messages.RECORD_READ_ERROR);
		}catch (IOException e) {
			throw new RecordException(Messages.RECORD_READ_ERROR);
		}finally{
			if(in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}	
	}
	 
	 void save() throws RecordException{ // writes the records of all levels
		 BufferedWriter out=null;
			try {
				out = new BufferedWriter(new FileWriter(Messages.RECORD_FILENAME));
				for(int i=0;i<datos.size();i++) {
					Record r=datos.get(i);
					out.write(r.l.toString() + ":" + r.currentRecord+ "\n");
				}
			}catch(FileNotFoundException fn){
				throw new RecordException(Messages.RECORD_WRITE_ERROR);
			}catch (IOException e) {
				throw new RecordException(Messages.RECORD_WRITE_ERROR);
			}
			finally {
				if(out != null) {
					try {
						out.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		 
	boolean update(int Score) { // returns true if the score is a new record
		if(Score>currentRecord) {
			currentRecord=Score;
			return true;
		}
		return false;
	}
	
	 int getRecord() {
		return currentRecord;
	}	
}
