package com.example.uee.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.addCallback
import androidx.activity.viewModels
import androidx.core.view.isGone
import com.example.uee.R
import com.example.uee.databinding.ActivityChatBinding
import com.getstream.sdk.chat.viewmodel.MessageInputViewModel
import com.getstream.sdk.chat.viewmodel.messages.MessageListViewModel
import com.zegocloud.uikit.prebuilt.call.config.ZegoNotificationConfig
import com.zegocloud.uikit.prebuilt.call.invite.ZegoUIKitPrebuiltCallInvitationConfig
import com.zegocloud.uikit.prebuilt.call.invite.ZegoUIKitPrebuiltCallInvitationService
import com.zegocloud.uikit.service.defines.ZegoUIKitUser
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.ui.message.input.viewmodel.bindView
import io.getstream.chat.android.ui.message.list.header.viewmodel.MessageListHeaderViewModel
import io.getstream.chat.android.ui.message.list.header.viewmodel.bindView
import io.getstream.chat.android.ui.message.list.viewmodel.bindView
import io.getstream.chat.android.ui.message.list.viewmodel.factory.MessageListViewModelFactory
import java.util.*

class ChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatBinding
    private val client = ChatClient.instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val cid = checkNotNull(intent.getStringExtra("cid")) {
            "Specifying a channel id is required when starting ChannelActivity"
        }

        val targetUserID = intent.getStringExtra("targetUserID")

        // Create three separate ViewModels for the views so it's easy
        // to customize them individually
        val factory = MessageListViewModelFactory(cid)
        val messageListHeaderViewModel: MessageListHeaderViewModel by viewModels { factory }
        val messageListViewModel: MessageListViewModel by viewModels { factory }
        val messageInputViewModel: MessageInputViewModel by viewModels { factory }

        // Step 2 - Bind the view and ViewModels, they are loosely coupled so it's easy to customize
        messageListHeaderViewModel.bindView(binding.messageListHeaderView, this)
        messageListViewModel.bindView(binding.messageListView, this)
        messageInputViewModel.bindView(binding.messageInputView, this)

        // Let both MessageListHeaderView and MessageInputView know when we open a thread
        messageListViewModel.mode.observe(this) { mode ->
            when (mode) {
                is MessageListViewModel.Mode.Thread -> {
                    messageListHeaderViewModel.setActiveThread(mode.parentMessage)
                    messageInputViewModel.setActiveThread(mode.parentMessage)
                }
                MessageListViewModel.Mode.Normal -> {
                    messageListHeaderViewModel.resetThread()
                    messageInputViewModel.resetThread()
                }
            }
        }

        // Let the message input know when we are editing a message
        binding.messageListView.setMessageEditHandler(messageInputViewModel::postMessageToEdit)

        // Handle navigate up state
        messageListViewModel.state.observe(this) { state ->
            if (state is MessageListViewModel.State.NavigateUp) {
                finish()
            }
        }

        // Handle back button behaviour correctly when you're in a thread
        val backHandler = {
            messageListViewModel.onEvent(MessageListViewModel.Event.BackButtonPressed)
        }
        binding.messageListHeaderView.setBackButtonClickListener(backHandler)
        onBackPressedDispatcher.addCallback(this) {
            backHandler()
        }

        setVideoCall()

        val targetUserName = targetUserID

        binding.btnCall.isGone = true
        binding.btnCall.setIsVideoCall(true)
        binding.btnCall.resourceID = "zego_uikit_call"
        binding.btnCall.setInvitees(
            Collections.singletonList(
                ZegoUIKitUser(
                    targetUserID,
                    targetUserName
                )
            )
        )

        binding.messageListHeaderView.setAvatarClickListener {
            binding.btnCall.performClick()
        }
    }

    override fun finish() {
        super.finish()

        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    private fun setVideoCall() {
        val appID: Long = 2006518119
        val appSign = "9ccd3dfc49767f5fe93705c45afe7b8f3e6a18fe550811e6c4a66d80f48d468e"
        val userID: String = client.getCurrentUser()?.id!!
        val userName: String = client.getCurrentUser()?.id!!

        val callInvitationConfig = ZegoUIKitPrebuiltCallInvitationConfig()
        callInvitationConfig.notifyWhenAppRunningInBackgroundOrQuit = true
        val notificationConfig = ZegoNotificationConfig()
        notificationConfig.sound = "zego_uikit_sound_call"
        notificationConfig.channelID = "CallInvitation"
        notificationConfig.channelName = "CallInvitation"
        ZegoUIKitPrebuiltCallInvitationService.init(
            application,
            appID,
            appSign,
            userID,
            userName,
            callInvitationConfig
        );
    }
}