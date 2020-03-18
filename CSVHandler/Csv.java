class Csv
{
		private Row head;
		private Row tail;
		private Row point;

		private Row next_row;
		
		int no_of_rows;
		
		private String separator;
		public Csv()
		{
			this(",");
		}
		
		public Csv(String separator)
		{
			head = null;
			tail = null;
			point = null;
			no_of_rows = 0;
			if(separator!=null)
			this.separator = separator;
			else this.separator = ",";
			next_row = head;
		}
		
			
		public void add(String line)
		{
			this.add(new Row(line,this.separator));
		}
		public void add(String line, String sep)
		{
			this.add(new Row(line,sep));
		}
		public void add(String[] row)
		{
			this.add(new Row(row));
		}
		public void add(Row row)
		{
			if(head == null || tail == null)
			{
				//This is the first row to add
				head = row;
				tail = row;
				head.prev = null;
				tail.next = null;
				head.index = 0;
			}
			else
			{
				tail.next = row;
				row.prev = tail;
				row.index = tail.index + 1;
				tail = row;
				tail.next = null;
			}
			no_of_rows++;
		}
		
		public int count()
		{
			return this.no_of_rows;
		}
		
		public boolean isEmpty()
		{
			////System.out.println("head"+head);
			////System.out.println("tail"+tail);
			if(head==null|tail==null) return true;
			else return false;
		}
		public boolean hasNext()
		{
			////System.out.println("empty?"+isEmpty());
			if(!isEmpty())
			{
				if(next_row!=null) return true;
			}
			return false;
		}
		public boolean hasPrev()
		{
			if(!isEmpty())
			{
				if(next_row.prev.prev != null) return true;
			}
			return false;
		}
		public Row prev()
		{
			if(hasPrev())
			{
				Row pre = new Row(next_row.prev.prev);
				next_row = next_row.prev;
				return pre;
			}
			return null;
		}
		
		public Row next()
		{
			if(hasNext())
			{
			Row nxt = new Row(this.next_row);
			next_row = next_row.next;
			return nxt;
			}
			return null;
		}
		
		public void resetNextRow()
		{
			this.next_row = head;
		}
		
		public String[] getRow(int a)
		{
			Row r;
			//System.out.println(hasNext());
			while(hasNext())
			{
				r=next();
				//System.out.println(r.index);
				if(r.index()==a)
				{
					return r.getAsArray();
				}
			}
			resetNextRow();
			return null;
		}
		public String getField(int row, int col)
		{
			//System.out.println(row+","+col);
			return getRow(row)[col];
			//System.exit(0);
			//return null;
		}
		
		public String[] getColumn(int col_no)
		{
			String[] result = new String[count()];
			resetNextRow();
			for(int i=0; hasNext(); i++)
			{
				result[i] = next().getField(col_no);
			}

			return result;
		}
		
		
		
}