package com.example.artcon_test.ui.post;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;

import com.example.artcon_test.R;
import com.example.artcon_test.model.Comment;
import com.example.artcon_test.model.CommentRequest;
import com.example.artcon_test.model.Interest;
import com.example.artcon_test.model.Post;
import com.example.artcon_test.network.PostService;
import com.example.artcon_test.retrofit.RetrofitService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewPostFragment extends Fragment {
    private ImageView profileImage;
    private TextView profileName;
    private TextView username;
    private ImageView postImage;
    private TextView postTextArea;

    private EditText editTextComment;

    SharedPreferences sharedPreferences;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_view_post, container, false);
        Log.d("ViewPostFragment","postFragment oncreate view");
        //read the postId from Add post
        //getParentFragmentManager().setFragmentResultListener("postIdFromAnotherFrag", this, new FragmentResultListener() {
           // @Override
           // public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
        String postId = requireArguments().getString("postId");
        //String postId = result.getString("postId");
                //Log.d("postIdFromAdd", postId);

                Log.d("ViewPostFragment","postFragment show"+ postId);
                PostFragment postFragment = PostFragment.newInstance(postId);
                FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.postFragment, postFragment);
                fragmentTransaction.commit();

                //
                Log.d("ViewPostFragment","commentsViewFragment show");
                CommentsViewFragment commentsViewFragment = CommentsViewFragment.newInstance(postId);
                FragmentTransaction ft = getParentFragmentManager().beginTransaction();
                ft.replace(R.id.commentFragment, commentsViewFragment);
                ft.commit();

                //

                // Set up the send button click listener
                editTextComment = view.findViewById(R.id.editTextComment);
                view.findViewById(R.id.sendButton).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String commentContent = editTextComment.getText().toString().trim();
                        if (!commentContent.isEmpty()) {

                            sharedPreferences = getActivity().getSharedPreferences("AuthPrefs", MODE_PRIVATE);
                            String user_id = sharedPreferences.getString("userId", null);
                            int intUser_id = Integer.parseInt(user_id);

                            //String postId = getArguments().getString(ARG_POST_ID);
                            int intPostId = Integer.parseInt(postId);

                            CommentRequest commentRequest = new CommentRequest();
                            commentRequest.setUser_id(intUser_id);
                            commentRequest.setPost_id(intPostId);
                            commentRequest.setContent(commentContent);

                            RetrofitService retrofitService = new RetrofitService();
                            PostService postService = retrofitService.getRetrofit().create(PostService.class);
                            Log.d("CommentViewFragment","about to add comment");

                            postService.comment(commentRequest).enqueue(new Callback<Comment>() {
                                @Override
                                public void onResponse(Call<Comment> call, Response<Comment> response) {
                                    if (response.isSuccessful()) {
                                        Log.d("CommentViewFragment","Comment added");
                                        // Reload comments in CommentsViewFragment
                                        editTextComment.setText("");
                                        // Hide the keyboard
                                        InputMethodManager imm = (InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                                        imm.hideSoftInputFromWindow(editTextComment.getWindowToken(), 0);
                                        
                                        CommentsViewFragment commentsViewFragment = (CommentsViewFragment) getParentFragmentManager().findFragmentById(R.id.commentFragment);
                                        if (commentsViewFragment != null) {
                                            commentsViewFragment.loadComments();
                                        }
                                        Comment comment = response.body();
                                        Post post = comment.getPost();
                                        Integer comments_count = post.getComments_count();
                                        Log.d("comments_count", String.valueOf(comments_count));
                                        PostFragment postFragment1 = (PostFragment) getParentFragmentManager().findFragmentById(R.id.postFragment);
                                        if (postFragment1 != null) {
                                            postFragment1.updateCommentsCount(comments_count);
                                        }
                                    }
                                }
                                @Override
                                public void onFailure(Call<Comment> call, Throwable t) {
                                    Log.d("CommentViewFragment","Comment not added");
                                }
                            });


                        }
                    }
                });
          //  }
       // });
        return view;
    }
}
