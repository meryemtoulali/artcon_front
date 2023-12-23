package com.example.artcon_test.chat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.appcompat.widget.Toolbar;

import com.example.artcon_test.R;
import com.example.artcon_test.chat.ConversationsAdapter; // Assuming this is your adapter package
import com.example.artcon_test.chat.Conversation; // Assuming this is your model package

import java.util.List;

public class ConversationsActivity extends AppCompatActivity implements ConversationsAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private ConversationsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversations);

        // Setting up the toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);

        setupRecyclerView();
        // loadConversations(); // Uncomment when ready to fetch data from the backend
    }



    private void setupRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView);
        adapter = new ConversationsAdapter(this);
        recyclerView.setAdapter(adapter);
        // Assuming getConversationsFromBackend() is a method that returns a list of Conversation
//        List<Conversation> conversations = getConversationsFromBackend();
//        adapter.setConversations(conversations);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    // Fetch conversations from the backend (Uncomment when ready to implement)
//    private List<Conversation> getConversationsFromBackend() {
//        // Implement backend call here and return the list of conversations
//        return null;
//    }

//    private void loadConversations() {
//        List<Conversation> conversations = getConversationsFromBackend();
//        adapter.setConversations(conversations);
//    }

    @Override
    public void onItemClick(Conversation conversation) {
        Intent intent = new Intent(this, ChatActivity.class);
        intent.putExtra("userId", conversation.getUserId());
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_back) {
            // Handle back action
            return true;
        } else if (id == R.id.action_add) {
            // Handle add action
            return true;
        } else if (id == R.id.action_options) {
            // Handle option 1 action
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
