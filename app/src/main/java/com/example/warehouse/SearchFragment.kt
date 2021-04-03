package com.example.warehouse

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class SearchFragment : Fragment(), AdapterView.OnItemSelectedListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val spinner: Spinner = view.findViewById(R.id.spinner)
        val spinner2:Spinner = view.findViewById(R.id.spinner3)
        val colors = arrayOf("Red","Green","Blue","Yellow","Black","Crimson","Orange")

        //Copies from the track fragments
        var items= ArrayList<String>()
        val database = FirebaseDatabase.getInstance()
        val trackRef = database.getReference("State")
        //val rv_track: RecyclerView = view.findViewById(R.id.rv_track)
        //postToList()
        ////trackRef.child("S042").child("name").setValue("Laptop")
        var getData = object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                for(c in snapshot.children) {

                   var fromWarehouse = c.key.toString()
                   // var name = c.child("name").getValue().toString()
                   // var price = c.child("price").getValue().toString().toDouble()
                   // var quantity = c.child("quantity").getValue().toString().toInt()
                    //var status = c.child("status").getValue().toString()
                    //var id = c.child("stockid").getValue().toString()
                    // items.add(TrackItem("WA","WSD",89.0,8,"Hi","S3323"))
                    items.add(fromWarehouse)
                }//items.add(TrackItem("WA","WSD",89.0,8,"Hi","S3323"))
                //rv_track.adapter=RecyclerAdapter(items,this@SearchFragment.requireContext())
                if (spinner!=null){
                    val adapter = ArrayAdapter(
                            requireContext(),
                            android.R.layout.simple_spinner_item,
                            items)
                    spinner.adapter = adapter
                }

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

        //rv_track.layoutManager= LinearLayoutManager(activity)
        //rv_track.setHasFixedSize(true)
        trackRef.addValueEventListener(getData)
        //Spinner initialization done...



        var selectedState = ""
        spinner.onItemSelectedListener = object :
        AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
               selectedState = items[position]
                var category= ArrayList<String>()
                //Toast.makeText(this@SearchFragment.requireContext(),"Selected" + items[position],Toast.LENGTH_SHORT).show()
                val trackRef = database.getReference("State").child(selectedState)
                //val rv_track: RecyclerView = view.findViewById(R.id.rv_track)
                //postToList()
                ////trackRef.child("S042").child("name").setValue("Laptop")
                var getData = object: ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {

                        for(c in snapshot.children) {

                            var categoryName = c.key.toString()
                            // var name = c.child("name").getValue().toString()
                            // var price = c.child("price").getValue().toString().toDouble()
                            // var quantity = c.child("quantity").getValue().toString().toInt()
                            //var status = c.child("status").getValue().toString()
                            //var id = c.child("stockid").getValue().toString()
                            // items.add(TrackItem("WA","WSD",89.0,8,"Hi","S3323"))
                            category.add(categoryName)
                            category.remove("longitud")
                            category.remove("latitud")
                        }//items.add(TrackItem("WA","WSD",89.0,8,"Hi","S3323"))
                        //rv_track.adapter=RecyclerAdapter(items,this@SearchFragment.requireContext())
                        if(spinner2!=null){
                            val adapter2 = ArrayAdapter(
                                this@SearchFragment.requireContext(),
                                android.R.layout.simple_spinner_item,
                                category)
                            spinner2.adapter = adapter2
                        }

                    }

                    override fun onCancelled(error: DatabaseError) {
                        Toast.makeText(
                            activity,
                            "Fail to get data.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                }
                trackRef.addValueEventListener(getData)


            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}