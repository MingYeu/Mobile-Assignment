package com.example.warehouse

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MapFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MapFragment : Fragment() {

    //private var titlesList = mutableListOf<String>()
    private var warehouseList = mutableListOf<String>()

    private  var layoutManager: RecyclerView.LayoutManager?=null
    private var adapter: RecyclerView.Adapter<MapAdapter.MapViewHolder>?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        postToList()
        val rvMap: RecyclerView = view.findViewById(R.id.rv_map)
        rvMap.apply {
            layoutManager= LinearLayoutManager(activity)
            adapter=MapAdapter(warehouseList)
        }
    }

    private fun addToList(name:String){
        warehouseList.add(name)
       // detailsList.add(description)
    }

    private fun postToList(){
        for(i in 1..3){
            addToList("Warehouse $i")
        }
    }
}