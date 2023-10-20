package com.example.uee.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.uee.R
import com.example.uee.activities.ChatActivity
import com.example.uee.dataClasses.Caregiver
import com.example.uee.dataClasses.ChatUser
import com.example.uee.databinding.FragmentChannelListBinding
import com.google.android.material.transition.platform.MaterialFadeThrough
import com.google.android.material.transition.platform.MaterialSharedAxis
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.models.Filters
import io.getstream.chat.android.client.models.User
import io.getstream.chat.android.ui.channel.list.viewmodel.ChannelListViewModel
import io.getstream.chat.android.ui.channel.list.viewmodel.bindView
import io.getstream.chat.android.ui.channel.list.viewmodel.factory.ChannelListViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class ChannelListFragment : Fragment() {

    private val client = ChatClient.instance()
    private val caregiverCollectionRef = Firebase.firestore.collection("Caregivers")
    private lateinit var chatUser: ChatUser
    private lateinit var caregiver: Caregiver
    private lateinit var user: User
    private lateinit var caregiverUsername: String
    private lateinit var binding: FragmentChannelListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enterTransition = MaterialFadeThrough()
        exitTransition = MaterialFadeThrough()
    }

    @SuppressLint("WrongConstant")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentChannelListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments
        caregiverUsername = args?.getString("caregiverUsername")!!

        getCaregiver()

        binding.channelListView.setChannelItemClickListener { channel ->

            exitTransition = MaterialSharedAxis(MaterialSharedAxis.Z, /* forward= */ true)
            reenterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, /* forward= */ false)

            val intent = Intent(requireContext(), ChatActivity::class.java)
            intent.putExtra("cid", channel.cid)
            startActivity(intent)
            activity?.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        binding.toolbar.setNavigationOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }
    }

    private fun getCaregiver() = CoroutineScope(Dispatchers.IO).launch {
        try {
            val querySnapshot =
                caregiverCollectionRef.whereEqualTo("username", caregiverUsername).get().await()

            for (document in querySnapshot.documents) {
                caregiver = document.toObject<Caregiver>()!!
            }

            withContext(Dispatchers.Main) {
                connectUser()
                setViews()
            }

        } catch (e: Exception) {
            Log.d("CaregiverRegister1Fragment", e.toString())
        }
    }

    private fun connectUser() {
        if (caregiver.image != null) {
            chatUser = ChatUser(caregiver.username!!, caregiver.name!!, caregiver.image!!)
        } else {
            chatUser = ChatUser(caregiver.username!!, caregiver.name!!, null)
        }

        //Authenticate and connect the user
        user = User(
            id = chatUser.username!!, name = chatUser.name!!
        )

        if (chatUser.image != null) {
            user.image = caregiver.image!!
        }

        val token = client.devToken(user.id)

        client.connectUser(
            user = user, token = token
        ).enqueue { result ->
            if (result.isSuccess) {
                Log.d("ChannelListFragment", "Success Connecting the User")
            } else {
                Log.d("ChannelListFragment", result.error().message.toString())
            }
        }
    }

    private fun setViews() {
        // Set the channel list filter and order
        // This can be read as requiring only channels whose "type" is "messaging" AND
        // whose "members" include our "user.id"
        val filter = Filters.and(
            Filters.eq("type", "messaging"), Filters.`in`("members", listOf(user.id))
        )
        val viewModelFactory =
            ChannelListViewModelFactory(filter, ChannelListViewModel.DEFAULT_SORT)
        val viewModel: ChannelListViewModel by viewModels { viewModelFactory }

        // Connect the ChannelListViewModel to the ChannelListView, loose
        // coupling makes it easy to customize
        viewModel.bindView(binding.channelListView, this)

        binding.avatarView.setUserData(user)
    }
}