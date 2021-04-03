package com.example.warehouse

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

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

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<MapAdapter.MapViewHolder>? = null

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // postToList()
        //val rvMap: RecyclerView = view.findViewById(R.id.rv_map)


        var items = ArrayList<MapItem>()
        val database = FirebaseDatabase.getInstance()
        val trackMap = database.getReference("State")
        // val trackRef = database.getReference("State").child("Kuala Lumpur")
        val rvMap: RecyclerView = view.findViewById(R.id.rv_map)
        //rvMap.layoutManager=LinearLayoutManager(activity)
        //rvMap.adapter = MapAdapter(warehouseList,this.requireContext())
        //postToList()
        ////trackRef.child("S042").child("name").setValue("Laptop")
        var getData = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (c in snapshot.children) {

                    var state = c.key.toString()
                    var longitud = c.child("longitud").getValue().toString().toDouble()
                    var latitud = c.child("latitud").getValue().toString().toDouble()
                    //var item = c.child(state.toString()).value.toString()
                    //var itemName = c.child(state).key.toString()

                    //var itemCategory = c.child(state).getValue().toString()
                    //var price = c.child("price").getValue().toString().toDouble()
                    //var quantity = c.child("quantity").getValue().toString().toInt()
                    //var status = c.child("status").getValue().toString()
                    //var id = c.child("stockid").getValue().toString()
                    // items.add(TrackItem("WA","WSD",89.0,8,"Hi","S3323"))
                    //items.add()
                    items.add(MapItem(state,latitud,longitud))
                }//items.add(TrackItem("WA","WSD",89.0,8,"Hi","S3323"))
                rvMap.adapter = MapAdapter(items, this@MapFragment.requireContext())
                rvMap.layoutManager = LinearLayoutManager(activity)
                rvMap.setHasFixedSize(true)

            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(
                        activity,
                        "Fail to get data.",
                        Toast.LENGTH_SHORT
                ).show()
            }

        }
//        rv_track.apply {
//
//            layoutManager= LinearLayoutManager(activity)
//            adapter=RecyclerAdapter(items)
//        }


        trackMap.addValueEventListener(getData)
    }

    private fun addToList(name: String) {
        warehouseList.add(name)
        // detailsList.add(description)
    }

    private fun postToList() {
        for (i in 1..3) {
            addToList("Warehouse $i")
        }
    }
}