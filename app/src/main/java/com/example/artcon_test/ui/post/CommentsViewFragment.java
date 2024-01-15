package com.example.artcon_test.ui.post;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.example.artcon_test.R;
import com.example.artcon_test.model.Comment;
import com.example.artcon_test.model.CommentRequest;
import com.example.artcon_test.network.PostService;
import com.example.artcon_test.retrofit.RetrofitService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentsViewFragment extends Fragment {

    private RecyclerView recyclerView;
    private EditText editTextComment;
    private CommentAdapter commentAdapter;
    private View view;

    private String postId;

    private static final String ARG_POST_ID = "postId";
    SharedPreferences sharedPreferences;

    public CommentsViewFragment() {
        // Required empty public constructor
    }
    public static CommentsViewFragment newInstance(String postId) {
        Bundle args = new Bundle();
        args.putString(ARG_POST_ID, postId);

        CommentsViewFragment commentsViewFragment = new CommentsViewFragment();
        commentsViewFragment.setArguments(args);
        return commentsViewFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            postId = getArguments().getString(ARG_POST_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_comments_view, container, false);

        Log.d("CommentViewFragment","oncreate show");

        String postId = getArguments().getString(ARG_POST_ID);

        RetrofitService retrofitService = new RetrofitService();
        PostService postService = retrofitService.getRetrofit().create(PostService.class);

        postService.getCommentsByPostId(postId).enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                if (response.isSuccessful()) {
                    List<Comment> commentList = response.body();
                    Log.d("CommentViewFragment","comments show");

                    recyclerView = view.findViewById(R.id.recyclerView);
                    commentAdapter = new CommentAdapter(commentList);

                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerView.setAdapter(commentAdapter);

                }
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {

            }
        });
////



        return view;
    }
    void loadComments() {
        String postId = getArguments().getString(ARG_POST_ID);
        Log.d("CommentViewFragment","We are in load comment");

        RetrofitService retrofitService = new RetrofitService();
        PostService postService = retrofitService.getRetrofit().create(PostService.class);

        postService.getCommentsByPostId(postId).enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                if (response.isSuccessful()) {
                    Log.d("CommentViewFragment","issuccessful load comment");
                    List<Comment> commentList = response.body();

                    recyclerView = view.findViewById(R.id.recyclerView);
                    commentAdapter = new CommentAdapter(commentList);


                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerView.setAdapter(commentAdapter);
                    Log.d("CommentViewFragment","after load comment");
                }
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {

            }
        });
    }
}