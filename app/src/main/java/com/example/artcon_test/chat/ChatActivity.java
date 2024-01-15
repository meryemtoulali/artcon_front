package com.example.artcon_test.chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.artcon_test.chat.adapters.ChatAdapter;
import com.example.artcon_test.chat.listeners.ConversionListener;
import com.example.artcon_test.chat.models.ChatMessage;
import com.example.artcon_test.chat.network.ApiClient;
import com.example.artcon_test.chat.network.ApiService;
import com.example.artcon_test.databinding.ActivityChatBinding;
import com.example.artcon_test.chat.models.User;
import com.example.artcon_test.ui.MainNavActivity;
import com.example.artcon_test.utilities.Constants;
import com.example.artcon_test.utilities.PreferenceManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatActivity extends BasicActivity  {

    private ActivityChatBinding binding;
    private User receiverUser;
    private List<ChatMessage> chatMessages;
    private ChatAdapter chatAdapter;
    private PreferenceManager preferenceManager;
    private FirebaseFirestore database;
    private String conversionId = null;
    private Boolean isReceiverAvailable = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setListeners();
        loadReceiverDetails();
        init();
        listenMessages();
    }

    private void init(){
        preferenceManager = new PreferenceManager(getApplicationContext());
        chatMessages = new ArrayList<>();
        chatAdapter = new ChatAdapter(
                chatMessages,
                receiverUser.getUsername(),
                preferenceManager.getString(Constants.KEY_USER_ID)
//                getBitmapEncodedString(receiverUser.getPicture()),
        );
        binding.chatRecyclerView.setAdapter(chatAdapter);
        database = FirebaseFirestore.getInstance();
    }

//    private Bitmap getBitmapEncodedString (String encodedImage){
//        byte[] bytes = Base64.decode(encodedImage,Base64.DEFAULT);
//        return BitmapFactory.decodeByteArray(bytes,0,bytes.length);
//    }

    private void sendMessage(){
        HashMap<String,Object> message = new HashMap<>();
        message.put(Constants.KEY_SENDER_ID, preferenceManager.getString(Constants.KEY_USER_ID));
        message.put(Constants.KEY_RECEIVER_ID,receiverUser.getUserId());
        message.put(Constants.KEY_MESSAGE,binding.inputMessage.getText().toString());
        message.put(Constants.KEY_TIMESTAMP, new Date());
        database.collection(Constants.KEY_COLLECTION_CHAT).add(message);

        if (conversionId != null){
            updateConversion(binding.inputMessage.getText().toString());
        }else {
            HashMap<String, Object> conversion = new HashMap<>();
            // Inside the else part where you create a new conversion
            conversion.put(Constants.KEY_SENDER_ID, preferenceManager.getString(Constants.KEY_USER_ID));
            conversion.put(Constants.KEY_SENDER_FIRSTNAME, preferenceManager.getString(Constants.KEY_FIRSTNAME));
            conversion.put(Constants.KEY_SENDER_LASTNAME, preferenceManager.getString(Constants.KEY_LASTNAME));
            conversion.put(Constants.KEY_SENDER_USERNAME, preferenceManager.getString(Constants.KEY_USERNAME));
            conversion.put(Constants.KEY_SENDER_IMAGE, preferenceManager.getString(Constants.KEY_IMAGE));

            conversion.put(Constants.KEY_RECEIVER_ID, receiverUser.getUserId());
            conversion.put(Constants.KEY_RECEIVER_FIRSTNAME, receiverUser.getFirstname());
            conversion.put(Constants.KEY_RECEIVER_LASTNAME, receiverUser.getLastname());
            conversion.put(Constants.KEY_RECEIVER_USERNAME, receiverUser.getUsername());
            conversion.put(Constants.KEY_RECEIVER_IMAGE, receiverUser.getPicture());

            conversion.put(Constants.KEY_LAST_MESSAGE, binding.inputMessage.getText().toString());
            conversion.put(Constants.KEY_TIMESTAMP,new Date());
            addConversion(conversion);
        }
        if (!isReceiverAvailable){
            try {
                JSONArray tokens = new JSONArray();
                tokens.put(receiverUser.getToken());

                JSONObject data = new JSONObject();
                data.put(Constants.KEY_USER_ID,preferenceManager.getString(Constants.KEY_USER_ID));
                data.put(Constants.KEY_FIRSTNAME,preferenceManager.getString(Constants.KEY_FIRSTNAME));
                data.put(Constants.KEY_LASTNAME,preferenceManager.getString(Constants.KEY_LASTNAME));
                data.put(Constants.KEY_USERNAME,preferenceManager.getString(Constants.KEY_USERNAME));
                data.put(Constants.KEY_FCM_TOKEN,preferenceManager.getString(Constants.KEY_FCM_TOKEN));
                data.put(Constants.KEY_MESSAGE,preferenceManager.getString(Constants.KEY_MESSAGE));


                JSONObject body = new JSONObject();
                body.put(Constants.REMOTE_MSG_DATA,data);
                body.put(Constants.REMOTE_MSG_REGISTRATION_IDS, tokens);

                sendNotification(body.toString());

            } catch (Exception exception) {
                showToast(exception.getMessage());
            }
        }
        binding.inputMessage.setText(null);
    }
//    private Bitmap getBitmapFromEncodedString(String encodedImage){
//        byte[] bytes = Base64.decode(encodedImage,Base64.DEFAULT);
//        return BitmapFactory.decodeByteArray(bytes,0,bytes.length);
//    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(),message, Toast.LENGTH_SHORT).show();
    }

    private void sendNotification (String messageBody) {
        ApiClient.getClient().create(ApiService.class).sendMessage(
                Constants.getRemoteMsgHeaders(),
                messageBody
        ).enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call,@NonNull Response<String> response) {
                if (response.isSuccessful()){
                    try {
                        if(response.body() != null){
                            JSONObject responseJson = new JSONObject(response.body());
                            JSONArray results = responseJson.getJSONArray("results");
                            if (responseJson.getInt("failure") == 1) {
                                JSONObject error = (JSONObject) results.get(0);
                                showToast(error.getString("error"));
                                return;
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    showToast("Notification sent successfully");
                }else {
                    showToast("Error: " + response.code());
                }

            }

            @Override
            public void onFailure(@NonNull Call<String> call,@NonNull Throwable t) {
                showToast(t.getMessage());
            }
        });
    }
    private void listenerAvailabilityOfReceiver(){
        database.collection(Constants.KEY_COLLECTION_USERS).document(
                receiverUser.getUserId()
        ).addSnapshotListener(ChatActivity.this, (value, error) ->{
            if (error != null) {
                return;
            }
            if (value!= null) {
                if (value.getLong(Constants.KEY_AVAILABILITY) != null) {
                    int availability = Objects.requireNonNull(
                            value.getLong(Constants.KEY_AVAILABILITY)
                    ).intValue();
                    isReceiverAvailable = availability == 1;
                }
                receiverUser.setToken(value.getString(Constants.KEY_TOKEN));
                if(receiverUser.getUsername() == null){
//                    receiverUser.getUsername() = value.getString(Constants.KEY_USERNAME);
                    chatAdapter.notifyItemRangeChanged(0, chatMessages.size());

//                    receiverUser.getPicture() = value.getString(Constants.KEY_IMAGE);
//                    chatAdapter.setReceiverProfileImage(receiverUser.getPicture());
                }
            }
                if (isReceiverAvailable){
                    binding.textAvailability.setVisibility(View.VISIBLE);
                } else {
                    binding.textAvailability.setVisibility(View.GONE);
                }

        });
    }
    private void listenMessages(){
        database.collection(Constants.KEY_COLLECTION_CHAT)
                .whereEqualTo(Constants.KEY_SENDER_ID, preferenceManager.getString(Constants.KEY_USER_ID))
                .whereEqualTo(Constants.KEY_RECEIVER_ID, receiverUser.getUserId())
                .addSnapshotListener(eventListener);

        database.collection(Constants.KEY_COLLECTION_CHAT)
                .whereEqualTo(Constants.KEY_SENDER_ID, receiverUser.getUserId())
                .whereEqualTo(Constants.KEY_RECEIVER_ID, preferenceManager.getString(Constants.KEY_USER_ID))
                .addSnapshotListener(eventListener);

    }

    private final EventListener<QuerySnapshot> eventListener = (value, error) -> {
        if (error != null){
            return ;
        }
        if (value != null){
            int count = chatMessages.size();
            for (DocumentChange documentChange : value.getDocumentChanges()){
                if (documentChange.getType() == DocumentChange.Type.ADDED){
                    ChatMessage chatMessage = new ChatMessage();
                    chatMessage.senderId = documentChange.getDocument().getString(Constants.KEY_SENDER_ID);
                    chatMessage.receiverId = documentChange.getDocument().getString(Constants.KEY_RECEIVER_ID);
                    chatMessage.message = documentChange.getDocument().getString(Constants.KEY_MESSAGE);
                    chatMessage.dateTime = getReadableDateTime(documentChange.getDocument().getDate(Constants.KEY_TIMESTAMP));
                    chatMessage.dateObject = documentChange.getDocument().getDate(Constants.KEY_TIMESTAMP);
                    chatMessages.add(chatMessage);

                    Log.d("ChatActivity", "Received message: " + chatMessage.toString());
                }
            }
            Collections.sort(chatMessages, (obj1, obj2) -> obj1.dateObject.compareTo(obj2.dateObject));
            if (count == 0) {
                chatAdapter.notifyDataSetChanged();
            }else {
                chatAdapter.notifyItemRangeInserted(chatMessages.size(), chatMessages.size());
                binding.chatRecyclerView.smoothScrollToPosition(chatMessages.size() - 1);
            }
            binding.chatRecyclerView.setVisibility(View.VISIBLE);
        }
        binding.progressBar.setVisibility(View.GONE);
        if (conversionId == null) {
            checkForConversion();
        }

    };



        private void loadReceiverDetails(){
        receiverUser= (User) getIntent().getSerializableExtra(Constants.KEY_USER);
        binding.textUsername.setText(receiverUser.getUsername());
    }

    private void setListeners(){
        binding.actionBack.setOnClickListener(v ->
                startActivity(new Intent(getApplicationContext(), MainNavActivity.class)));

        binding.layoutSend.setOnClickListener(v -> sendMessage());
    }

    private String getReadableDateTime(Date date){
        return new SimpleDateFormat("MMM dd, yyy - hh:mm a", Locale.getDefault()).format(date);
    }

    private void addConversion (HashMap<String, Object> conversion){
            database.collection(Constants.KEY_COLLECTION_CONVERSATIONS)
                    .add(conversion)
                    .addOnSuccessListener(documentReference -> conversionId = documentReference.getId());
    }

    private void updateConversion (String message){
        DocumentReference documentReference =
                database.collection(Constants.KEY_COLLECTION_CONVERSATIONS).document(conversionId);
        documentReference.update(
                Constants.KEY_LAST_MESSAGE, message,
                Constants.KEY_TIMESTAMP, new Date()
        );
    }

    private void checkForConversion(){
            if (chatMessages.size() != 0){
                checkConversionRemotely(
                        preferenceManager.getString(Constants.KEY_USER_ID),
                        receiverUser.getUserId()
                );
                checkConversionRemotely(
                        receiverUser.getUserId(),
                        preferenceManager.getString(Constants.KEY_USER_ID)
                );
            }
    }
    private void checkConversionRemotely(String senderId , String receiverId){
            database.collection(Constants.KEY_COLLECTION_CONVERSATIONS)
                    .whereEqualTo(Constants.KEY_SENDER_ID,senderId)
                    .whereEqualTo(Constants.KEY_RECEIVER_ID,receiverId)
                    .get()
                    .addOnCompleteListener(conversionCompleteListener);
    }

    private final OnCompleteListener<QuerySnapshot> conversionCompleteListener = task -> {
            if(task.isSuccessful() && task.getResult()!= null && task.getResult().getDocuments().size() > 0) {
                DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                conversionId = documentSnapshot.getId();
            }
    };


    @Override
    protected void onResume() {
            super.onResume();
            listenerAvailabilityOfReceiver();
        }

}