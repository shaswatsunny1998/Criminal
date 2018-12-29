package com.example.android.criminal;


import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Listfragment extends ListFragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public Listfragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ArrayList<Crim> mcrimes=new ArrayList<Crim>();
        mcrimes=CrimeLab.get(getActivity()).getCrimes();
        CrimAdapter adapter=new CrimAdapter(getActivity(),R.layout.adapter,mcrimes);
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Crim c=CrimeLab.get(getActivity()).getCrimes().get(position);
        Intent intent=new Intent(getActivity(),Activity2.class);
        intent.putExtra("POSITION", position);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((CrimAdapter)getListAdapter()).notifyDataSetChanged();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.cime_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.new_crime:
            {
                Crim c=new Crim();
                CrimeLab.get(getActivity()).addCrime(c);
                Intent intent=new Intent(getActivity(),Activity2.class);
                intent.putExtra("POSITION", CrimeLab.get(getActivity()).getCrimes().size()-1);
                startActivity(intent);
            }
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.delete, menu);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=super.onCreateView(inflater, container, savedInstanceState);
        ListView listView=(ListView)v.findViewById(android.R.id.list);
        registerForContextMenu(listView);
        listView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {

            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                MenuInflater menuInflater=mode.getMenuInflater();
                menuInflater.inflate(R.menu.delete, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.delete_menu:
                        CrimAdapter adapter = (CrimAdapter) getListAdapter();
                        CrimeLab crimeLab = CrimeLab.get(getActivity());
                        for (int i = adapter.getCount(); i >= 0; --i) {
                            if (getListView().isItemChecked(i)) {
                                crimeLab.delete(adapter.getItem(i));
                            }
                        }
                        mode.finish();
                        adapter.notifyDataSetChanged();
                    default:
                        return false;
                }
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {

            }
        });
        return v;
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info=(AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        int position=info.position;
        CrimAdapter adapter=(CrimAdapter) getListAdapter();
        Crim c=adapter.getItem(position);
        switch (item.getItemId())
        {
            case R.id.delete_menu:
                CrimeLab.get(getActivity()).delete(c);
                adapter.notifyDataSetChanged();
                return true;
            default:return super.onContextItemSelected(item);

        }
    }
}

