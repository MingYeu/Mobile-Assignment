package com.example.warehouse

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
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
 * Use the [TrackFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TrackFragment : Fragment() {

    private var titlesList = mutableListOf<String>()
    private var detailsList = mutableListOf<String>()

    private  var layoutManager: RecyclerView.LayoutManager?=null
    private var adapter: RecyclerView.Adapter<RecyclerAdapter.ViewHolder>?=null




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_track, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var items= ArrayList<TrackItem>()
        val database = FirebaseDatabase.getInstance()
        val trackRef = database.getReference("Track")
        //postToList()
        ////trackRef.child("S042").child("name").setValue("Laptop")
        var getData = object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                for(c in snapshot.children) {

                    var fromWarehouse = c.child("fromWarehouse").getValue().toString()
                    var name = c.child("name").getValue().toString()
                    var price = c.child("price").getValue().toString().toDouble()
                    var quantity = c.child("quantity").getValue().toString().toInt()
                    var status = c.child("status").getValue().toString()
                    var id = c.child("stockid").getValue().toString()
                    // items.add(TrackItem("WA","WSD",89.0,8,"Hi","S3323"))
                    items.add(TrackItem(fromWarehouse, name, price, quantity, status, id))
                }//items.add(TrackItem("WA","WSD",89.0,8,"Hi","S3323"))
                val rv_track: RecyclerView = view.findViewById(R.id.rv_track)
                rv_track.adapter=RecyclerAdapter(items)
                rv_track.layoutManager=LinearLayoutManager(activity)
                rv_track.setHasFixedSize(true)

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

        trackRef.addValueEventListener(getData)

        //track_ref.addListenerForSingleValueEvent(getData)
        //items.add(TrackItem("WA","WSD",89.0,8,"Hi","S3323"))



    }
}

//    private fun addToList(title:String,description:String){
//        titlesList.add(title)
//        detailsList.add(description)
//    }
//
//    private fun postToList(){
//        for(i in 1..25){
//            addToList("Title $i","Description $i")
//        }
//    }
    //https://medium.com/inside-ppl-b7/recyclerview-inside-fragment-with-android-studio-680cbed59d84https://medium.com/inside-ppl-b7/recyclerview-inside-fragment-with-android-studio-680cbed59d84

