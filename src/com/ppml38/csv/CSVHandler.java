package com.ppml38.csv;

import java.io.*;

/**
 * Class that provide different operations on csv files. Once intialised, it
 * will form an internal table with all the values and perform the functions on
 * RAM unless called to save. Csv.java class
 *
 * @author Prakash Maria Liju P (github.com/ppml38)
 */
public class CSVHandler {

    Csv csv;
    String[] map;

    /**
     * Constructor that enable to initialize with no parameters
     */
    public CSVHandler() {
        csv = new Csv();
        map = null;

    }

    /**
     * Initialize with a file name, map and a separator. here you can provide
     * separator other than default comma
     *
     * @param file Name of the file that you want to process on (Ex.
     * "AddressFile.csv")
     * @param map Name of the file that contains comma separated headers (Ex.
     * "header.txt" that contains single line as "Name,Age,Address")
     * @param sep Delimiter string, which can be other than comma, Note that
     * this delimiter value will be applied to both csv and map file. This
     * enables custom delimited files to be processed as csv.
     */
    public CSVHandler(String file, String map, String sep) {

        csv = new Csv(sep);
        try {
            String s = "";
            BufferedReader ireader = new BufferedReader(new FileReader(new File(file)));
            while ((s = ireader.readLine()) != null) {
                ////System.out.println("Adding add poer");
                csv.add(s);
            }
            ireader.close();

            if (map != null) {
                ireader = new BufferedReader(new FileReader(new File(map)));
                while ((s = ireader.readLine()) != null) {
                    //System.out.println("Adding map");
                    this.map = s.split(sep);
                }
                ireader.close();
            } else {
                this.map = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Initialize with csv file name
     *
     * @param file Name of the file to process on (Ex."file1.csv")
     */
    public CSVHandler(String file) {
        this(file, null, ",");
    }

    /**
     * Initialize with csv file name and map file
     *
     * @param file Name of the file to process on (Ex."file1.csv")
     * @param map Name of the file that contains headers of the csv file in
     * single line (Ex."map1.txt")
     */
    public CSVHandler(String file, String map) {
        this(file, map, ",");
    }

    /**
     * Function that search and return the column position of given field name,
     * from map file.
     *
     * @param field Name of the field that to be searched for
     * @return Position of the field in map file starting from 0
     */
    public int getFieldNo(String field) {
        if (map != null) {
            for (int i = 0; i < map.length; i++) {
                if (map[i].equals(field)) {
                    return i;
                }

            }
        }
        return -1;
    }

    /**
     * Function to get value of a field present in given row
     *
     * @param row_no Row index starting from 0
     * @param field Name of the field that the value is required from
     * @return Value present in the given row and column. null if value not
     * found
     */
    public String getField(int row_no, String field) {
        int column_no = getFieldNo(field);
        if (column_no != -1) {
            return csv.getField(row_no, column_no);
        }
        return null;
    }

    /**
     * Returns array of all the values in given column name
     *
     * @param col_name Name of the field that values required for
     * @return String array of all the values present under a column
     */
    public String[] getColumn(String col_name) {

        return this.csv.getColumn(getFieldNo(col_name));
    }

    /**
     * Returns row in string of comma separated values. Even the delimiter is
     * different from comma in given file.
     *
     * @param fields Fields that are to be included in the string
     * @return Comma separated value string
     */
    public static String toString(String[] fields) {
        return toString(fields, ",");
    }

    /**
     * Returns row in string of values with given string as delimiter
     *
     * @param fields Fields that are to be included in the string
     * @param new_separator Delimiter that to be used instead of comma
     * @return Delimited string of values
     */
    public static String toString(String[] fields, String new_separator) {
        String str = "";
        if (fields.length != 0) {
            for (int i = 0; i < fields.length - 1; i++) {
                str += (fields[i] + new_separator);
            }
            str += fields[fields.length - 1];
        } else {
            return str;
        }
        return str;
    }

    /**
     * Adds a new row to the csv table. New row will be added as last line
     *
     * @param s String of the new row to be added
     */
    public void add(String s) {
        this.csv.add(s);
    }

    /**
     * Adds new rows to csv table at end of file.
     *
     * @param s Array of strings that are to be added
     */
    public void add(String[] s) {
        this.csv.add(s);
    }

    /**
     * Append a new csv file with the existing one. All the new rows will be
     * added at last
     *
     * @param file Name of the file that to be appended
     */
    public void addFile(String file) {
        CSVHandler handler = new CSVHandler(file);
        while (handler.csv.hasNext()) {
            this.csv.add(handler.csv.next());
        }
    }

    /**
     * Saves the current values with all the operations performed with delimiter
     * as comma, without headers
     *
     * @param file New file name to be saved as
     */
    public void save(String file) {
        save(file, false, ",");
    }

    /**
     * Saves the current values with all the operations performed with delimiter
     * as comma including header
     *
     * @param file New file name to be saved as
     * @param header pass true if header to be included as first line
     */
    public void save(String file, boolean header) {
        save(file, header, ",");
    }

    /**
     * Saves the current values with all the operations performed with delimiter
     * as given string
     *
     * @param file New file name to be saved as
     * @param write_header If headed to be included as first line
     * @param sep Delimiter string instead of comma
     */
    public void save(String file, boolean write_header, String sep) {
        try {
            FileWriter owriter = new FileWriter(file);

            if (write_header & map != null) {
                owriter.write(toString(this.map));
                owriter.write(System.getProperty("line.separator"));
            }

            csv.resetNextRow();
            while (csv.hasNext()) {
                owriter.write(csv.next().toString(sep));
                owriter.write(System.getProperty("line.separator"));
            }
            owriter.close();
        } catch (Exception e) {
            //System.out.println("Error writing file");
        }
    }

    /**
     * Returns the value in col2 of a row which matches col1 value = val1
     *
     * @param col1 Name of the column to be searched
     * @param val1 Name of the value to be searched
     * @param col2 Value of which column to be returned
     * @return If no match found returns null otherwise col2 value that found
     */
    public String getMatch(String col1, String val1, String col2) {
        String[] flds;
        this.csv.resetNextRow();
        while (this.csv.hasNext()) {
            flds = this.csv.next().getAsArray();
            if (flds[getFieldNo(col1)].equals(val1)) {
                return flds[getFieldNo(col2)];
            }
        }
        return null;
    }

}
