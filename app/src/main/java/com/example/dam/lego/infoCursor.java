package com.example.dam.lego;

import android.database.MatrixCursor;

/**
 * Created by dam on 30/1/17.
 */

public class infoCursor extends MatrixCursor {
    public static final String[] COLNAMES = { "_id","set_num", "name", "num_parts" };

    public infoCursor(Info info) {
        super(COLNAMES);
        int n =0;
        /*for (Info.Information c : info.getInfo()) {
            String[] row = new String[4];
            row[0] = String.valueOf(++n);
            row[1] = c.set_num;
            row[2] = c.name;
            row[3] = String.valueOf(c.num_parts);
            this.addRow(row);
        }*/
    }
}
