package com.example.artcon_test.chat;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.artcon_test.R;
import com.example.artcon_test.chat.adapters.RecentConversationsAdapter;
import com.example.artcon_test.chat.listeners.ConversionListener;
import com.example.artcon_test.chat.models.ChatMessage;
import com.example.artcon_test.chat.models.User;
import com.example.artcon_test.databinding.ActivityUsersFirstBinding;
import com.example.artcon_test.ui.MainNavActivity;
import com.example.artcon_test.utilities.Constants;
import com.example.artcon_test.utilities.PreferenceManager;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UsersFirstActivity extends AppCompatActivity implements ConversionListener {
    private PreferenceManager preferenceManager;

    private ActivityUsersFirstBinding binding;
    private List<ChatMessage> conversations;
    private RecentConversationsAdapter conversationsAdapter;
    private FirebaseFirestore database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUsersFirstBinding.inflate(getLayoutInflater());
        preferenceManager = new PreferenceManager(getApplicationContext());

        setContentView(binding.getRoot());
        init();
        setListeners();
        listenConversations();
    }

    private void init(){
        conversations = new ArrayList<>();
        conversationsAdapter = new RecentConversationsAdapter(conversations, this);
        binding.conversationsRecyclerView.setAdapter(conversationsAdapter);
        database = FirebaseFirestore.getInstance();
    }
//    private void listenConversations() {
//        database.collection(Constants.KEY_COLLECTION_CONVERSATIONS)
//                .whereEqualTo(Constants.KEY_SENDER_ID, preferenceManager.getString(Constants.KEY_USER_ID))
//                .addSnapshotListener(eventListener);
//
//        database.collection(Constants.KEY_COLLECTION_CONVERSATIONS)
//                .whereEqualTo(Constants.KEY_RECEIVER_ID, preferenceManager.getString(Constants.KEY_USER_ID))
//                .addSnapshotListener(eventListener);
//    }

    private void listenConversations() {
        database.collection(Constants.KEY_COLLECTION_CONVERSATIONS)
                .whereEqualTo(Constants.KEY_SENDER_ID, preferenceManager.getString(Constants.KEY_USER_ID))
                .addSnapshotListener(senderEventListener);

        database.collection(Constants.KEY_COLLECTION_CONVERSATIONS)
                .whereEqualTo(Constants.KEY_RECEIVER_ID, preferenceManager.getString(Constants.KEY_USER_ID))
                .addSnapshotListener(receiverEventListener);
    }

    private final EventListener<QuerySnapshot> senderEventListener = (value, error) -> {
        if (error != null) {
            return;
        }
        if (value != null) {
            conversations.clear();  // Clear the list before adding new items
            for (DocumentChange documentChange : value.getDocumentChanges()) {
                if (documentChange.getType() == DocumentChange.Type.ADDED) {
                    // Inside the eventListener, for Type.ADDED
                    String senderId = documentChange.getDocument().getString(Constants.KEY_SENDER_ID);
                    String receiverId = documentChange.getDocument().getString(Constants.KEY_RECEIVER_ID);
                    ChatMessage chatMessage = new ChatMessage();
                    chatMessage.senderId = senderId;
                    chatMessage.receiverId = receiverId;

                    Log.d("UsersFirstActivity2", "Current User ID: " + preferenceManager.getString(Constants.KEY_USER_ID));
                    Log.d("UsersFirstActivity2", "Sender ID: " + senderId);
                    Log.d("UsersFirstActivity2", "Receiver ID: " + receiverId);
                    Log.d("UsersFirstActivity2", "Firestore Query Result: " + documentChange.getDocument().getData());

                    chatMessage.conversionImage = documentChange.getDocument().getString(Constants.KEY_RECEIVER_IMAGE);
                    chatMessage.conversionFirstname = documentChange.getDocument().getString(Constants.KEY_RECEIVER_FIRSTNAME);
                    chatMessage.conversionLastname = documentChange.getDocument().getString(Constants.KEY_RECEIVER_LASTNAME);
                    chatMessage.conversionUsername = documentChange.getDocument().getString(Constants.KEY_RECEIVER_USERNAME);
                    chatMessage.conversionId = documentChange.getDocument().getString(Constants.KEY_RECEIVER_ID);

                    chatMessage.message = documentChange.getDocument().getString(Constants.KEY_LAST_MESSAGE);
                    chatMessage.dateObject = documentChange.getDocument().getDate(Constants.KEY_TIMESTAMP);
                    conversations.add(chatMessage);
                } else if (documentChange.getType() == DocumentChange.Type.MODIFIED) {
                    for (int i = 0; i < conversations.size(); i++){
                        String senderId = documentChange.getDocument().getString(Constants.KEY_SENDER_ID);
                        String receiverId = documentChange.getDocument().getString(Constants.KEY_RECEIVER_ID);
                        if (conversations.get(i).senderId.equals(senderId) && conversations.get(i).receiverId.equals(receiverId)){
                            conversations.get(i).message = documentChange.getDocument().getString(Constants.KEY_LAST_MESSAGE);
                            conversations.get(i).dateObject = documentChange.getDocument().getDate(Constants.KEY_TIMESTAMP);
                            break;
                        }
                    }
                }
            }
            Collections.sort(conversations, (obj1, obj2 ) -> obj2.dateObject.compareTo(obj1.dateObject));
            conversationsAdapter.notifyDataSetChanged();
            binding.conversationsRecyclerView.smoothScrollToPosition(0);
            binding.conversationsRecyclerView.setVisibility(View.VISIBLE);
            binding.progressBar.setVisibility(View.GONE);

        }
    };


    private final EventListener<QuerySnapshot> receiverEventListener = (value, error) -> {
        if (error != null) {
            return;
        }
        if (value != null) {
            for (DocumentChange documentChange : value.getDocumentChanges()) {
                if (documentChange.getType() == DocumentChange.Type.ADDED) {
                    String senderId = documentChange.getDocument().getString(Constants.KEY_SENDER_ID);
                    String receiverId = documentChange.getDocument().getString(Constants.KEY_RECEIVER_ID);
                    ChatMessage chatMessage = new ChatMessage();
                    chatMessage.senderId = senderId;
                    chatMessage.receiverId = receiverId;

                    Log.d("UsersFirstActivity2", "Current User ID: " + preferenceManager.getString(Constants.KEY_USER_ID));
                    Log.d("UsersFirstActivity2", "Sender ID: " + senderId);
                    Log.d("UsersFirstActivity2", "Receiver ID: " + receiverId);
                    Log.d("UsersFirstActivity2", "Firestore Query Result: " + documentChange.getDocument().getData());

                    // Check if the current user is the receiver
                    if (preferenceManager.getString(Constants.KEY_USER_ID).equals(receiverId)) {
                        chatMessage.conversionImage = documentChange.getDocument().getString(Constants.KEY_SENDER_IMAGE);
                        chatMessage.conversionFirstname = documentChange.getDocument().getString(Constants.KEY_SENDER_FIRSTNAME);
                        chatMessage.conversionLastname = documentChange.getDocument().getString(Constants.KEY_SENDER_LASTNAME);
                        chatMessage.conversionUsername = documentChange.getDocument().getString(Constants.KEY_SENDER_USERNAME);
                        chatMessage.conversionId = documentChange.getDocument().getString(Constants.KEY_SENDER_ID);

                        Log.d("UsersFirstActivity2", "Current user is the receiver");

                        Log.d("UsersFirstActivity2", "Retrieved sender details from Firestore:");
                        Log.d("UsersFirstActivity2", "Image: " + chatMessage.conversionImage);
                        Log.d("UsersFirstActivity2", "Firstname: " + chatMessage.conversionFirstname);
                        Log.d("UsersFirstActivity2", "Lastname: " + chatMessage.conversionLastname);
                        Log.d("UsersFirstActivity2", "Username: " + chatMessage.conversionUsername);
                        Log.d("UsersFirstActivity2", "ID: " + chatMessage.conversionId);
                    }

                    chatMessage.message = documentChange.getDocument().getString(Constants.KEY_LAST_MESSAGE);
                    chatMessage.dateObject = documentChange.getDocument().getDate(Constants.KEY_TIMESTAMP);
                    conversations.add(chatMessage);
                    Log.d("ConversationDetails", "Sender ID: " + senderId);
                    Log.d("ConversationDetails", "Receiver ID: " + receiverId);
                    Log.d("ConversationDetails", "Actual Sender ID: " + chatMessage.conversionId);
                    Log.d("ConversationDetails", "Actual Sender Firstname: " + chatMessage.conversionFirstname);
                    Log.d("ConversationDetails", "Actual Sender Lastname: " + chatMessage.conversionLastname);
                    Log.d("ConversationDetails", "Actual Sender Username: " + chatMessage.conversionUsername);
                } else if (documentChange.getType() == DocumentChange.Type.MODIFIED) {
                    // Handle modified documents
                }
            }
            Collections.sort(conversations, (obj1, obj2) -> obj2.dateObject.compareTo(obj1.dateObject));
            conversationsAdapter.notifyDataSetChanged();
            binding.conversationsRecyclerView.smoothScrollToPosition(0);
            binding.conversationsRecyclerView.setVisibility(View.VISIBLE);
            binding.progressBar.setVisibility(View.GONE);
        }
    };





    private final EventListener<QuerySnapshot> eventListener = (value, error) -> {
        if (error != null){
            return;
        }
        if (value != null){
            for (DocumentChange documentChange : value.getDocumentChanges()){
                if (documentChange.getType() == DocumentChange.Type.ADDED){
                    // Inside the eventListener, for Type.ADDED
                    String senderId = documentChange.getDocument().getString(Constants.KEY_SENDER_ID);
                    String receiverId = documentChange.getDocument().getString(Constants.KEY_RECEIVER_ID);
                    ChatMessage chatMessage = new ChatMessage();
                    chatMessage.senderId = senderId;
                    chatMessage.receiverId = receiverId;

                    Log.d("UsersFirstActivity2", "Current User ID: " + preferenceManager.getString(Constants.KEY_USER_ID));
                    Log.d("UsersFirstActivity2", "Sender ID: " + senderId);
                    Log.d("UsersFirstActivity2", "Receiver ID: " + receiverId);
                    Log.d("UsersFirstActivity2", "Firestore Query Result: " + documentChange.getDocument().getData());



                    if (preferenceManager.getString(Constants.KEY_USER_ID).equals(senderId)) {
                        // Current user is the sender
                        chatMessage.conversionImage = documentChange.getDocument().getString(Constants.KEY_RECEIVER_IMAGE);
                        chatMessage.conversionFirstname = documentChange.getDocument().getString(Constants.KEY_RECEIVER_FIRSTNAME);
                        chatMessage.conversionLastname = documentChange.getDocument().getString(Constants.KEY_RECEIVER_LASTNAME);
                        chatMessage.conversionUsername = documentChange.getDocument().getString(Constants.KEY_RECEIVER_USERNAME);
                        chatMessage.conversionId = documentChange.getDocument().getString(Constants.KEY_RECEIVER_ID);

                        Log.d("UsersFirstActivity2", "Current user is the sender");

                        Log.d("UsersFirstActivity2", "Retrieved sender details from Firestore:");
                        Log.d("UsersFirstActivity2", "Image: " + chatMessage.conversionImage);
                        Log.d("UsersFirstActivity2", "Firstname: " + chatMessage.conversionFirstname);
                        Log.d("UsersFirstActivity2", "Lastname: " + chatMessage.conversionLastname);
                        Log.d("UsersFirstActivity2", "Username: " + chatMessage.conversionUsername);
                    } else if (preferenceManager.getString(Constants.KEY_USER_ID).equals(receiverId)) {
                        // Current user is the receiver
                        chatMessage.conversionImage = documentChange.getDocument().getString(Constants.KEY_SENDER_IMAGE);
                        chatMessage.conversionFirstname = documentChange.getDocument().getString(Constants.KEY_SENDER_FIRSTNAME);
                        chatMessage.conversionLastname = documentChange.getDocument().getString(Constants.KEY_SENDER_LASTNAME);
                        chatMessage.conversionUsername = documentChange.getDocument().getString(Constants.KEY_SENDER_USERNAME);
                        chatMessage.conversionId = documentChange.getDocument().getString(Constants.KEY_SENDER_ID);

                        Log.d("UsersFirstActivity2", "Current user is the receiver");

                        Log.d("UsersFirstActivity2", "Retrieved receiver details from Firestore:");
                        Log.d("UsersFirstActivity2", "Image: " + chatMessage.conversionImage);
                        Log.d("UsersFirstActivity2", "Firstname: " + chatMessage.conversionFirstname);
                        Log.d("UsersFirstActivity2", "Lastname: " + chatMessage.conversionLastname);
                        Log.d("UsersFirstActivity2", "Username: " + chatMessage.conversionUsername);
                        Log.d("UsersFirstActivity2", "ID: " + chatMessage.conversionId);
                    }

                    if (documentChange.getDocument().contains(Constants.KEY_RECEIVER_FIRSTNAME)) {
                        chatMessage.conversionFirstname = documentChange.getDocument().getString(Constants.KEY_RECEIVER_FIRSTNAME);
                    }
                    if (documentChange.getDocument().contains(Constants.KEY_RECEIVER_LASTNAME)) {
                        chatMessage.conversionLastname = documentChange.getDocument().getString(Constants.KEY_RECEIVER_LASTNAME);
                    }
                    if (documentChange.getDocument().contains(Constants.KEY_RECEIVER_USERNAME)) {
                        chatMessage.conversionUsername = documentChange.getDocument().getString(Constants.KEY_RECEIVER_USERNAME);
                    }


                    chatMessage.message = documentChange.getDocument().getString(Constants.KEY_LAST_MESSAGE);
                    chatMessage.dateObject = documentChange.getDocument().getDate(Constants.KEY_TIMESTAMP);
                    conversations.add(chatMessage);
                    Log.d("ConversationDetails", "Sender ID: " + senderId);
                    Log.d("ConversationDetails", "Receiver ID: " + receiverId);
                    Log.d("ConversationDetails", "Actual Sender ID: " + chatMessage.conversionId);
                    Log.d("ConversationDetails", "Actual Sender Firstname: " + chatMessage.conversionFirstname);
                    Log.d("ConversationDetails", "Actual Sender Lastname: " + chatMessage.conversionLastname);
                    Log.d("ConversationDetails", "Actual Sender Username: " + chatMessage.conversionUsername);



                } else if (documentChange.getType() == DocumentChange.Type.MODIFIED) {
                    for (int i = 0; i < conversations.size(); i++){
                        String senderId = documentChange.getDocument().getString(Constants.KEY_SENDER_ID);
                        String receiverId = documentChange.getDocument().getString(Constants.KEY_RECEIVER_ID);
                        if (conversations.get(i).senderId.equals(senderId) && conversations.get(i).receiverId.equals(receiverId)){
                            conversations.get(i).message = documentChange.getDocument().getString(Constants.KEY_LAST_MESSAGE);
                            conversations.get(i).dateObject = documentChange.getDocument().getDate(Constants.KEY_TIMESTAMP);
                            break;
                        }
                    }
                }
            }
            Collections.sort(conversations, (obj1, obj2 ) -> obj2.dateObject.compareTo(obj1.dateObject));
            conversationsAdapter.notifyDataSetChanged();
            binding.conversationsRecyclerView.smoothScrollToPosition(0);
            binding.conversationsRecyclerView.setVisibility(View.VISIBLE);
            binding.progressBar.setVisibility(View.GONE);

        }
    };

    private void setListeners() {
        binding.actionBack.setOnClickListener(v ->
                startActivity(new Intent(getApplicationContext(), MainNavActivity.class)));

        binding.addChat.setOnClickListener(v ->
                startActivity(new Intent(getApplicationContext(), UsersActivity.class)));
    }

    @Override
    public void onConversionCLicked(User user) {
        Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
        intent.putExtra(Constants.KEY_USER, user);
        startActivity(intent);
    }
}
