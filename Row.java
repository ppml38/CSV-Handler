class Row
{
	private String line;
	private String[] row;
	
	Row next;
	Row prev;
	
	int index;
	

	Row(String line)
	{
		this(line,null);
	}
	Row(String[] row)
	{
		this.row = row;
		this.line = "";
		this.next = null;
		this.prev = null;
		this.index = -1;
	}
	Row(Row r)
	{
		this.line = r.line;
		this.row = r.row;
		this.next = r.next;
		this.prev = r.prev;
		this.index = r.index;
	}
	
	Row(String line, String sep)
	{
		this.line = line;
		if(sep==null)
		this.row = line.split(",");
		else this.row = line.split(sep);
	}
	
	public int getFieldCount()
	{
		return this.row.length;
	}
	
	public String getField(int a)
	{
		return this.row[a];
	}
	
	public String[] getAsArray()
	{
		return this.row;
	}
	
	public String[] getRow(String sep)
	{
		return this.line.split(sep);
	}
	
	public int index()
	{
		return this.index;
	}
	
			public  String toString()
		{
			return toString(",");
		}
		
		
		public  String toString(String new_separator)
		{
			String str= "";
			if (new_separator==null) new_separator = ",";
			if(row.length!=0)
			{
			for(int i=0;i<row.length-1;i++)
			{
				str+=(row[i]+new_separator);
			}
			str+=row[row.length-1];
			}
			return str;
		}

}


