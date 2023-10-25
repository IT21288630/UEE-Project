package com.example.uee.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment

import com.example.uee.R
import com.example.uee.adapters.clientCarouselAdapter
import com.example.uee.dataClasses.Client
import com.example.uee.models.clientCarouselDataModel
import com.jackandphantom.carouselrecyclerview.CarouselLayoutManager
import com.jackandphantom.carouselrecyclerview.CarouselRecyclerview

/**
 * A simple [Fragment] subclass.
 * Use the [ClientProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ClientProfileFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var clientData: Client? = null
    private var clinetArgName : String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)




    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_client_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Access clientData here
        clientData = requireArguments().getParcelable("clientData")



        val carouselRecyclerview = view.findViewById<CarouselRecyclerview>(R.id.recycler)

        val list = ArrayList<clientCarouselDataModel>()

        list.add(clientCarouselDataModel(R.drawable.locationbasedimg, "Location Based"))
        list.add(clientCarouselDataModel(R.drawable.topratedimg, "Top Rated"))
        list.add(clientCarouselDataModel(R.drawable.favoritesimg, "My Favorites"))

        val clientName = view.findViewById<TextView>(R.id.txtClientProName)
        val clientImage = view.findViewById<ImageView>(R.id.imgViewClientProPic)

        clientName.text = clientData?.name?: "Anoonymous"


        val adapter = clientCarouselAdapter(list)

        carouselRecyclerview.adapter = adapter
        carouselRecyclerview.set3DItem(true)
        carouselRecyclerview.setAlpha(true)
        val carouselLayoutManager = carouselRecyclerview.getCarouselLayoutManager()
        val currentlyCenterPosition = carouselRecyclerview.getSelectedPosition()


        carouselRecyclerview.setItemSelectListener(object : CarouselLayoutManager.OnSelected {
           override fun onItemSelected(position: Int) {
                //Cente item
                Toast.makeText(requireContext(), list[position].text, Toast.LENGTH_LONG).show()

            }
        })


    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ClientProfileFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ClientProfileFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}


