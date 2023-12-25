package com.example.artcon_test.chat.Controller;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.artcon_test.R;
import com.example.artcon_test.chat.Adapter.MessageAdapter;
import com.example.artcon_test.chat.model.Message;
import java.util.ArrayList;
import java.util.List;

public class MessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversations);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Message> messages = new ArrayList<>();

        // Populate the messages list with dummy data for demonstration
        messages.add(new Message("1", "https://example.com/profile1.jpg", "John Doe", "johndoe", "Hello!", "10:00 AM"));
        messages.add(new Message("2", "https://example.com/profile2.jpg", "Jane Smith", "janesmith", "Hi there!", "10:05 AM"));

        MessageAdapter adapter = new MessageAdapter(messages);
        recyclerView.setAdapter(adapter);
    }
}
