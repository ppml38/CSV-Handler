package com.ppml38.csv;

/**
 * Class that defines a row in a csv file
 *
 * @author Prakash Maria Liju P (github.com/ppml38)
 */
public class Row {

    private String line;
    private String[] row;

    Row next;
    Row prev;

    int index;

    /**
     * Initialize with a String of comma separated values
     *
     * @param line String of comma separated values.
     */
    public Row(String line) {
        this(line, null);
    }

    /**
     * Initialize with String array of values
     *
     * @param row String array with values in every cell
     */
    public Row(String[] row) {
        this.row = row;
        this.line = "";
        this.next = null;
        this.prev = null;
        this.index = -1;
    }

    /**
     * Clones given row object
     *
     * @param r Row object that to be cloned
     */
    public Row(Row r) {
        this.line = r.line;
        this.row = r.row;
        this.next = r.next;
        this.prev = r.prev;
        this.index = r.index;
    }

    /**
     * Creates a row from String of comma separated values with custom delimiter
     *
     * @param line String of delimited values
     * @param sep Custom delimiter
     */
    public Row(String line, String sep) {
        this.line = line;
        if (sep == null) {
            this.row = line.split(",");
        } else {
            this.row = line.split(sep);
        }
    }

    /**
     * Returns the number of columns/fields present
     *
     * @return count of columns/fields
     */
    public int getFieldCount() {
        return this.row.length;
    }

    /**
     * Get the value of a field present in this row
     *
     * @param a Field index starting with 0
     * @return Value in the given column index
     */
    public String getField(int a) {
        return this.row[a];
    }

    /**
     * Returns the row as String array
     *
     * @return String array of values in this row
     */
    public String[] getAsArray() {
        return this.row;
    }

    /**
     * Returns the row with custom delimiter as String array
     *
     * @param sep custom delimiter
     * @return String array of values
     */
    public String[] getRow(String sep) {
        return this.line.split(sep);
    }

    /**
     * Returns the index of current row in given CSV file
     *
     * @return index number of this row
     */
    public int index() {
        return this.index;
    }

    /**
     * Returns string of comma separated values
     *
     * @return String of comma separated values
     */
    public String toString() {
        return toString(",");
    }

    /**
     * Reutns string of provided custom-delimiter separated values
     *
     * @param new_separator Custom delimiter
     * @return String of provided custom-delimiter separated values
     */
    public String toString(String new_separator) {
        String str = "";
        if (new_separator == null) {
            new_separator = ",";
        }
        if (row.length != 0) {
            for (int i = 0; i < row.length - 1; i++) {
                str += (row[i] + new_separator);
            }
            str += row[row.length - 1];
        }
        return str;
    }

}
