package com.example.android.criminal;

import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.UUID;

public class CrimeLab {
    private static final String FILENAME="crimes.json";
    private static  CrimeLab sCrimelab;
    private Context context;
    private ArrayList<Crim> mcrimes;
    private CriminalIntentJSONSerializer mcriminal;


    private CrimeLab(Context context)
    {
        this.context=context;
        mcriminal=new CriminalIntentJSONSerializer(this.context,FILENAME);
        try {
            mcrimes = mcriminal.loadCrimes();
        }
        catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            mcrimes=new ArrayList<Crim>();
        }


    }
    public static CrimeLab get(Context c)
    {
        if(sCrimelab==null)
        {
            sCrimelab=new CrimeLab(c.getApplicationContext());
        }
        return sCrimelab;
    }

    public Crim getCrime(UUID id)
    {
        for(Crim c:mcrimes)
        {
            if(c.getMid().equals(id))
                return c;
        }
        return null;
    }

    public  ArrayList<Crim> getCrimes()
    {
        return mcrimes;
    }

    public void addCrime(Crim c)
    {
        mcrimes.add(c);
    }

    public void delete(Crim c)
    {
        mcrimes.remove(c);
    }
    public boolean savecrimes()
    {
        try {
            mcriminal.Savecrimes(mcrimes);
            Toast.makeText(context, "DATA SAVED SUCCESSFUL", Toast.LENGTH_SHORT).show();
            return true;
        }
        catch (Exception e)
        {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            return false;
        }
    }

}
