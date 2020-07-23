package com.example.chatall

import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.database.FirebaseListAdapter
import com.firebase.ui.database.FirebaseListOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.core.view.View
import kotlinx.android.synthetic.main.activity_chat.*


@Suppress("CAST_NEVER_SUCCEEDS")
class ChatActivity() : AppCompatActivity(), Parcelable {
    constructor(parcel: Parcel) : this() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        button2.setOnClickListener {
            val msg = editTextTextPersonName2.text.toString()
            val email = FirebaseAuth.getInstance().currentUser!!.email

            FirebaseDatabase.getInstance().reference.child("messages").push().setValue(
                    Message(msg,email!! )
                )
        }

        val query = FirebaseDatabase.getInstance().reference.child("messages")


        val options: FirebaseListOptions<Message> = FirebaseListOptions.Builder<Message>()
            .setLayout(android.R.layout.simple_expandable_list_item_1)
            .setQuery(query, Message::class.java).build()

        val adapter: FirebaseListAdapter<Message> = object : FirebaseListAdapter<Message>(options) {

                fun populateView(v: View, model: Message, position: Int) {

                    (v as TextView).text = model.email + "\n" + model.msg
                }

            override fun populateView(v: android.view.View, model: Message, position: Int) {
                TODO("Not yet implemented")
            }

        }
        adapter.startListening()
        messages.adapter = adapter
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ChatActivity> {
        override fun createFromParcel(parcel: Parcel): ChatActivity {
            return ChatActivity(parcel)
        }

        override fun newArray(size: Int): Array<ChatActivity?> {
            return arrayOfNulls(size)
        }
    }
}
class Message(var email: String, var msg: String) {
    constructor() : this ("","")
}