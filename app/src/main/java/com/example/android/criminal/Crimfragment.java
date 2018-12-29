package com.example.android.criminal;


import android.app.ActionBar;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Crimfragment extends Fragment {
    private int pos;
    private ArrayList<Crim> mcrimes;
    private Crim c;
    private static final int REQUEST_CONTACT=123;


    public Crimfragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mcrimes=CrimeLab.get(getActivity()).getCrimes();
        Intent intent=getActivity().getIntent();
        pos=intent.getIntExtra("POSITION", 0);
        setHasOptionsMenu(true);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_crimfragment, container,false);
        EditText title=(EditText) v.findViewById(R.id.etname);
        TextView uuid=(TextView) v.findViewById(R.id.tvuuid);
        CheckBox issolved=(CheckBox) v.findViewById(R.id.cbsolve);
        c=mcrimes.get(pos);
        title.setText(c.getName().toString());
        uuid.setText(c.getMid().toString());
        issolved.setChecked(c.isSolved());
        title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                c.setName(s.toString());

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        issolved.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                c.setSolved(isChecked);
            }
        });

        Button suspect_btn=(Button) v.findViewById(R.id.suspect_btn);
        suspect_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, getCrimereport());
                intent.putExtra(Intent.EXTRA_SUBJECT, "Criminal Report CRime suspect");
                intent=Intent.createChooser(intent,"CRIME REPORT");
                startActivity(intent);
            }
        });

        Button send_report=(Button) v.findViewById(R.id.sendcrime_btn);
        send_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(intent, REQUEST_CONTACT);
            }
        });
        return v;
    }

    @Override
    public void onPause() {
        super.onPause();
        CrimeLab.get(getActivity()).savecrimes();
    }

    private String getCrimereport()
    {
        String report =null;
        String uuid=c.getMid().toString();
        report="The UUID of the criminal is: "+uuid;
        return report;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==REQUEST_CONTACT)
        {
            Uri uri=data.getData();

        }
    }
}
