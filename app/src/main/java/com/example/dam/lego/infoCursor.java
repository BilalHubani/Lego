package com.example.dam.lego;

import android.database.MatrixCursor;

import java.util.List;

/**
 * Created by dam on 30/1/17.
 */

public class infoCursor extends MatrixCursor {
    public static final String[] COLNAMES = { "_id","descr", "set_id", "pieces" };

    public infoCursor(List<InfoSearch> infoSearchList) {
        super(COLNAMES);
        int n =0;
        for (InfoSearch c : infoSearchList) {
            String[] row = new String[4];
            row[0] = String.valueOf(++n);
            row[1] = c.getDescr();
            row[2] = c.getSet_id();
            row[3] = c.getPieces();
            this.addRow(row);
        }
    }
}
