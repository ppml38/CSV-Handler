import java.io.*;
import java.util.*;

class CSVHandler
{
		
        Csv csv;
		String[] map;
		
		CSVHandler()
		{
			csv = new Csv();
			map = null;
				
		}
		
	
        CSVHandler ( String file, String map, String sep )
        {
			
			csv = new Csv(sep);		
            try
            {
                String s="";
                BufferedReader ireader=new BufferedReader ( new FileReader ( new File ( file ) ) );
                while ( ( s = ireader.readLine() ) != null )
                {
					////System.out.println("Adding add poer");
                    csv.add(s);
                }
                ireader.close();
				
				if(map!=null)
				{
                ireader=new BufferedReader ( new FileReader ( new File ( map ) ) );
                while ( ( s = ireader.readLine() ) != null )
                {
					//System.out.println("Adding map");
                    this.map = s.split ( sep );
                }
                ireader.close();
				}
				else
				{
					this.map = null;
				}
            }
            catch ( Exception e )
            {
                e.printStackTrace();
            }
        }
		CSVHandler(String file)
		{
			this(file,null,",");
		}
		CSVHandler(String file, String map)
		{
			this(file,map,",");
		}

		public int getFieldNo(String field)
		{
			if(map!=null)
			for ( int i=0;i<map.length;i++ )
            {
                if ( map[i].equals ( field ) )
                {
                    return i;
                }

            }
			return -1;
		}
		
        public String getField ( int row_no, String field )
        {
			int column_no = getFieldNo(field);
			if(column_no!=-1)
			{
				//System.out.println("handle"+row_no+":"+column_no);
				return csv.getField(row_no,column_no);
			}
            return null;
        }
		
		public String[] getColumn(String col_name)
		{

		return this.csv.getColumn(getFieldNo(col_name));
		}

		public static String toString(String[] fields)
		{
			return toString(fields,",");
		}
		
		
		public static String toString(String[] fields, String new_separator)
		{
			String str= "";
			if(fields.length!=0)
			{
			for(int i=0;i<fields.length-1;i++)
			{
				str+=(fields[i]+new_separator);
			}
			str+=fields[fields.length-1];
			}
			else
			return str;
			return str;
		}
		
		public void add(String s)
		{
			this.csv.add(s);
		}
		
		public void add(String[] s)
		{
			this.csv.add(s);
		}
		
		public void addFile(String file)
		{
			CSVHandler handler = new CSVHandler(file);
			while(handler.csv.hasNext())
			{
				this.csv.add(handler.csv.next());
			}
		}
		
		
		public void save(String file)
		{
			save(file, false, ",");
		}
		public void save(String file, boolean header)
		{
			save(file, header, ",");
		}
		public void save(String file, boolean write_header, String sep)
		{
			try{
            FileWriter owriter = new FileWriter ( file );

			if(write_header & map!=null)
			{
            owriter.write ( toString(this.map));
            owriter.write ( System.getProperty ( "line.separator" ) );
			}
			
			csv.resetNextRow();
			while(csv.hasNext())
			{
				owriter.write ( csv.next().toString(sep) );
                owriter.write ( System.getProperty ( "line.separator" ) );
			}
            owriter.close();
			}
			catch(Exception e)
			{
				//System.out.println("Error writing file");
			}
		}
		public String getMatch(String col1,String val1, String col2)
		{
			String[] flds;
			this.csv.resetNextRow();
			while(this.csv.hasNext())
			{
				flds = this.csv.next().getAsArray();
				if(flds[getFieldNo(col1)].equals(val1))
				{
					return flds[getFieldNo(col2)];
				}
			}
			return null;
		}

}

