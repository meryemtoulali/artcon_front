package com.example.artcon_test.chat.Controller;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.artcon_test.R;
import com.example.artcon_test.chat.Adapter.MessageAdapter;
import com.makeramen.roundedimageview.RoundedImageView;
import com.example.artcon_test.chat.model.Message;


import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message); // Replace with your layout name

        List<Message> messages = new ArrayList<>();
        messages.add(new Message("1", String.valueOf(R.drawable.ic_home_activated), "John Doe", "@johndoe", "Hello!", "•10:00 AM"));
        messages.add(new Message("2", "https://www.pexels.com/fr-fr/photo/mode-femme-barre-etre-assis-9331289/", "Jane Smith", "@janesmith", "Hi there!", "•10:05 AM"));
        messages.add(new Message("3", "https://via.placeholder.com/150", "Nouhaila", "@sunshine", "Hi there! I like your work and I would like to invite you", "10:05 AM"));
        messages.add(new Message("4", "https://www.pexels.com/fr-fr/photo/mode-femme-barre-etre-assis-9331289/", "Dadju", "@dadju", "Hey, I know you from that event! I loved your gig.", "•10:05 AM"));
        messages.add(new Message("5", "https://www.pexels.com/fr-fr/photo/mode-femme-barre-etre-assis-9331289/", "Bruno", "@brunito", "would you like to collab with me?", "•10:05 AM"));


        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MessageAdapter(messages));
    }
}
