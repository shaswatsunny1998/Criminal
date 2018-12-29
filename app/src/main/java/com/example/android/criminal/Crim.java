package com.example.android.criminal;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.UUID;

public class Crim {
    private static final String JSON_ID="id";
    private static final String JSON_title="title";
    private static final String JSON_SOLVED="solved";
    private static final String JSON_SUSPECT="suspect";

    private String mtitle;
    private UUID mid;
    private boolean solved;
    private String msuspect;

    Crim()
    {
        mid=UUID.randomUUID();
        msuspect="";
    }

    public String getName() {
        if(mtitle==null)
            return "";
        return mtitle;
    }

    public void setName(String name) {
        this.mtitle = name;
    }

    public UUID getMid() {
        return mid;
    }

    public void setMid(UUID mid) {
        this.mid = mid;
    }
    public boolean isSolved() {
        return solved;
    }

    public void setSolved(boolean solved) {
        this.solved = solved;
    }

    public JSONObject toJSON() throws JSONException
    {
        JSONObject jsonObject=new JSONObject();
        jsonObject.put(JSON_ID,mid.toString());
        jsonObject.put(JSON_title,mtitle);
        jsonObject.put(JSON_SOLVED, solved);
        jsonObject.put(JSON_SUSPECT, msuspect);
        return jsonObject;
    }

    public Crim(JSONObject json) throws JSONException{
        mid=UUID.fromString(json.getString(JSON_ID));
        if(json.has(JSON_title))
        {
            mtitle=json.getString(JSON_title);
        }
        solved=json.getBoolean(JSON_SOLVED);
        if(json.has(JSON_SUSPECT))
        {
            msuspect=json.getString(JSON_SUSPECT);
        }
    }

    public String getMsuspect() {
        return msuspect;
    }

    public void setMsuspect(String msuspect) {
        this.msuspect = msuspect;
    }
}
