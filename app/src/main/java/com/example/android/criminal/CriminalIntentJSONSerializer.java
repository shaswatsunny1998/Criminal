package com.example.android.criminal;

import android.content.Context;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

public class CriminalIntentJSONSerializer {
    private Context context;
    private String filename;

    public CriminalIntentJSONSerializer(Context context, String filename) {
        this.context = context;
        this.filename = filename;
    }

    public void Savecrimes(ArrayList<Crim> crimes)
        throws  JSONException, IOException
        {
            JSONArray array=new JSONArray();
            for(Crim c:crimes)
                array.put(c.toJSON());
            Writer writer=null;
            try {
                OutputStream outputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
                writer = new OutputStreamWriter(outputStream);
                writer.write(array.toString());
            }
            finally {
                if(writer!=null)
                    writer.close();
            }
        }

     public ArrayList<Crim> loadCrimes() throws JSONException, IOException
     {
         ArrayList<Crim> crimes=new ArrayList<Crim>();
         BufferedReader bufferedReader=null;
         try {
             InputStream inputStream = context.openFileInput(filename);
             bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
             StringBuilder jsonString = new StringBuilder();
             String line = null;
             while ((line = bufferedReader.readLine()) != null) {
                 jsonString.append(line);
             }
             JSONArray array = (JSONArray) new JSONTokener(jsonString.toString()).nextValue();
             for (int i = 0; i < array.length(); ++i)
                 crimes.add(new Crim(array.getJSONObject(i)));
         }
         catch (FileNotFoundException e)
         {
             Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
         }
         finally {
             if(bufferedReader!=null)
                 bufferedReader.close();
         }

         return  crimes;
     }

}
