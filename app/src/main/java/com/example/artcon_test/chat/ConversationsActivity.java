package com.example.artcon_test.chat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.artcon_test.R;

import java.util.List;

// ConversationsActivity.java
public class ConversationsActivity extends AppCompatActivity implements ConversationsAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private ConversationsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversations);

        setupRecyclerView();
        loadConversations();
    }

    private void setupRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView);
        adapter = new ConversationsAdapter();
        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void loadConversations() {
        // Fetch conversations from the backend (you need to implement this)
        List<Conversation> conversations = getConversationsFromBackend();
        adapter.setConversations(conversations);
    }

    @Override
    public void onItemClick(Conversation conversation) {
        // Handle click on a conversation item (e.g., open the chat activity)
        Intent intent = new Intent(this, ChatActivity.class);
        intent.putExtra("userId", conversation.getUserId());
        startActivity(intent);
    }
}
