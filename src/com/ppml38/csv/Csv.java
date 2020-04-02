package com.ppml38.csv;

/**
 * Class to define csv files with core file operations. This class has to be
 * used for initializing csv files on wrapper classes
 *
 * @author Prakash Maria Liju P (github.com/ppml38)
 */
public class Csv {

    private Row head;
    private Row tail;
    private Row point;

    private Row next_row;

    int no_of_rows;

    private String separator;

    /**
     * Simple constructor which defaults delimiter as comma
     */
    public Csv() {
        this(",");
    }

    /**
     * Construct csv object with custom delimiter
     *
     * @param separator Custom delimiter that is used in file instead of comma
     */
    public Csv(String separator) {
        head = null;
        tail = null;
        point = null;
        no_of_rows = 0;
        if (separator != null) {
            this.separator = separator;
        } else {
            this.separator = ",";
        }
        next_row = head;
    }

    /**
     * Add new row at bottom
     *
     * @param line Comma separated value string that to be added
     */
    public void add(String line) {
        this.add(new Row(line, this.separator));
    }

    /**
     * Add new row at bottom with custom delimiter
     *
     * @param line Custom delimited value string that to be added
     * @param sep Custom delimiter
     */
    public void add(String line, String sep) {
        this.add(new Row(line, sep));
    }

    /**
     * Adds more than one row at the bottom
     *
     * @param row Array of comma separated value string that to be added
     */
    public void add(String[] row) {
        this.add(new Row(row));
    }

    /**
     * Add new Row object with existing value table
     *
     * @param row Row object that to be added
     */
    public void add(Row row) {
        if (head == null || tail == null) {
            //This is the first row to add
            head = row;
            tail = row;
            head.prev = null;
            tail.next = null;
            head.index = 0;
        } else {
            tail.next = row;
            row.prev = tail;
            row.index = tail.index + 1;
            tail = row;
            tail.next = null;
        }
        no_of_rows++;
    }

    /**
     * Returns the number of rows currently present
     *
     * @return Number of rows
     */
    public int count() {
        return this.no_of_rows;
    }

    /**
     * Returns if current csv value table is empty
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        if (head == null | tail == null) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Check if a row exist next to current row in operation
     *
     * @return true if next row exists, false otherwise
     */
    public boolean hasNext() {
        if (!isEmpty()) {
            if (next_row != null) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check if a row exist previous to current row in operation
     *
     * @return true if previous row exist, false otherwise
     */
    public boolean hasPrev() {
        if (!isEmpty()) {
            if (next_row.prev.prev != null) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns previous row object
     *
     * @return Row that present immediately previous to current row
     */
    public Row prev() {
        if (hasPrev()) {
            Row pre = new Row(next_row.prev.prev);
            next_row = next_row.prev;
            return pre;
        }
        return null;
    }

    /**
     * Returns next row object
     *
     * @return Row that present immediately next to current row
     */
    public Row next() {
        if (hasNext()) {
            Row nxt = new Row(this.next_row);
            next_row = next_row.next;
            return nxt;
        }
        return null;
    }

    /**
     * Resets the next row to 0
     */
    public void resetNextRow() {
        this.next_row = head;
    }

    /**
     * Returns the String array of values of given row index
     *
     * @param a Index number of row that to be returned starting with 0
     * @return String array of values of given row index
     */
    public String[] getRow(int a) {
        Row r;
        while (hasNext()) {
            r = next();
            if (r.index() == a) {
                return r.getAsArray();
            }
        }
        resetNextRow();
        return null;
    }

    /**
     * Returns value present in given row and column
     *
     * @param row Row index starting with 0
     * @param col Column index starting with 0
     * @return Value present in csv[row][column]
     */
    public String getField(int row, int col) {
        return getRow(row)[col];
    }

    /**
     * Returns String array of values in given column in all rows (Not unique)
     *
     * @param col_no Column index of that to be returned
     * @return String array of all values in given column index
     */
    public String[] getColumn(int col_no) {
        String[] result = new String[count()];
        resetNextRow();
        for (int i = 0; hasNext(); i++) {
            result[i] = next().getField(col_no);
        }

        return result;
    }

}
