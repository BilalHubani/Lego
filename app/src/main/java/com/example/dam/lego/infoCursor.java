package com.example.dam.lego;

import android.database.MatrixCursor;

/**
 * Created by dam on 30/1/17.
 */

public class infoCursor extends MatrixCursor {
    public static final String[] COLNAMES = { "set_num", "name", "num_parts" };

    public infoCursor(Info info) {
        super(COLNAMES);
        for (Info.Information c : info.getInfos()) {
            String[] row = new String[3];
            row[0] = c.set_num;
            row[1] = c.name;
            row[2] = String.valueOf(c.num_parts);
            this.addRow(row);
        }
    }
}
