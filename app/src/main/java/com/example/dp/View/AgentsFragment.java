package com.example.dp.View;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.dp.Controller.AgentsAdapter;
import com.example.dp.Model.Agent;
import com.example.dp.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class AgentsFragment extends Fragment {
    private AgentsAdapter adapter;
    private RecyclerView rv;
    private ArrayList<Agent> agents;


    public AgentsFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_agents, container, false);
        rv=v.findViewById(R.id.rv1);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter=new AgentsAdapter(agents,agents,getContext());
        rv.setAdapter(adapter);
        return v;
    }

}
