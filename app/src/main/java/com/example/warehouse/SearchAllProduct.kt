package com.example.warehouse

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
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
 * Use the [SearchAllProduct.newInstance] factory method to
 * create an instance of this fragment.
 */
class SearchAllProduct : Fragment() {
    // TODO: Rename and change types of parameters



    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<SearchAllAdapter.ViewHolder>? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_all_product, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // postToList()
        //val rvMap: RecyclerView = view.findViewById(R.id.rv_map)


        var items = ArrayList<Stock>()
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("Stock")
        val allProductList: RecyclerView = view.findViewById(R.id.allProductList)

        var getData = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (c in snapshot.children) {

                    var image = c.child("image").getValue().toString()
                    var prodName = c.child("name").getValue().toString()
                    var prodPrice = c.child("price").getValue().toString()
                    var prodQuantity = c.child("quantity").getValue().toString()
                    var prodID = c.child("stockId").getValue().toString()
                    var rackID = c.child("rack").getValue().toString()

                    //Image
                    val imageBytes = Base64.decode(image, 0)
                    val imag = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)

                    items.add(Stock(prodID, prodName, prodQuantity,prodPrice,image,rackID))
                }
                allProductList.adapter = SearchAllAdapter(items, this@SearchAllProduct.requireContext())
                allProductList.layoutManager = LinearLayoutManager(activity)
                allProductList.setHasFixedSize(true)

            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(
                        activity,
                        "An error has occurred",
                        Toast.LENGTH_SHORT
                ).show()
            }

        }
        myRef.addValueEventListener(getData)
    }
}