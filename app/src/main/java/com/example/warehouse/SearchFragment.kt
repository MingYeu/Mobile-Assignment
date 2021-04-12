package com.example.warehouse

import android.content.Intent
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

    lateinit var found:categoryItem
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
        trackRef.addValueEventListener(getData)

        var selectedState = ""
        var category= ArrayList<String>()
        spinner.onItemSelectedListener = object :
        AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
               selectedState = items[position]

                val trackRef = database.getReference("State").child(selectedState)

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
                        }
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

        //
        val srcItems= ArrayList<categoryItem>()
        var selectedCat = ""
        spinner2.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedCat = category[position]
                //var category= ArrayList<String>()
                //Toast.makeText(this@SearchFragment.requireContext(),"Selected" + items[position],Toast.LENGTH_SHORT).show()
                val trackRef = database.getReference("State").child(selectedState).child(selectedCat)
                //val rv_track: RecyclerView = view.findViewById(R.id.rv_track)
                //postToList()
                ////trackRef.child("S042").child("name").setValue("Laptop")
                var getData = object: ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {

                        for(c in snapshot.children) {

                            var itemName = c.child("name").getValue().toString()
                            var itemPrice = c.child("price").getValue().toString().toDouble()
                            var quantity = c.child("quantity").getValue().toString().toInt()
                            var rack = c.child("rack").getValue().toString()
                            var id = c.child("stockId").getValue().toString()
                            srcItems.add(categoryItem(itemName,itemPrice,quantity,rack,id))
                            //showName.add(id)
                        }//items.add(TrackItem("WA","WSD",89.0,8,"Hi","S3323"))
                        //rv_track.adapter=RecyclerAdapter(items,this@SearchFragment.requireContext()

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

        val searchBtn:Button = view.findViewById(R.id.btn_search)

        searchBtn.setOnClickListener {
            val txtSearch:EditText = view.findViewById(R.id.edit_productId)
            if(itemFound(srcItems,txtSearch.text.toString())){
                val intent = Intent(this.context,SearchProdActivity::class.java)
                intent.putExtra("srcId",found.id)
                intent.putExtra("srcName",found.itemName)
                intent.putExtra("srcPrice",found.price.toDouble())
                intent.putExtra("srcQuantity",found.quantity.toInt())
                intent.putExtra("srcRackId",found.rack)
                this.context?.startActivity(intent)
            }
            else{
                Toast.makeText(
                    activity,
                    "Item not found.",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }
    }

    fun itemFound(check: ArrayList<categoryItem>, typedItem:String): Boolean {

        var condition=false
        for (item in check){
            if(item.id.equals(typedItem)){
                found = item
                condition=true
            }

        }

        return condition
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        TODO("Not yet implemented")
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}

