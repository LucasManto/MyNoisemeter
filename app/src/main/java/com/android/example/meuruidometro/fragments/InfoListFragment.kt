package com.android.example.meuruidometro.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView

import com.android.example.meuruidometro.R
import com.android.example.meuruidometro.adapters.InfoAdapter

class InfoListFragment : Fragment() {

    private lateinit var listView: ListView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_info_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listView = view.findViewById(R.id.list_view)
        listView.adapter = InfoAdapter(context)

        fragmentManager?.beginTransaction()?.apply {
            replace(R.id.pager, InfoListFragment())
            addToBackStack(null)
        }
    }

}
