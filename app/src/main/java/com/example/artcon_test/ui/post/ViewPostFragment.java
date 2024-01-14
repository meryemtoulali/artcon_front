package com.example.artcon_test.ui.post;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;

import com.example.artcon_test.R;

public class ViewPostFragment extends Fragment {
    private ImageView profileImage;
    private TextView profileName;
    private TextView username;
    private ImageView postImage;
    private TextView postTextArea;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_view_post, container, false);

        //String postId = "83";
        // Create a new instance of PostFragment and pass the postId
        //PostFragment postFragment = PostFragment.newInstance(postId);

        //FragmentTransaction ft = getS
        // Add the fragment to the fragment container
       /* getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, postFragment)
                .commit();
*/
        //read the postId from Add post
        getParentFragmentManager().setFragmentResultListener("postIdFromAnotherFrag", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                String postId = result.getString("postId");
                //Log.d("postIdFromAdd", postId);

                PostFragment postFragment = PostFragment.newInstance(postId);
                FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.postFragment, postFragment);
                fragmentTransaction.commit();
            }
        });
        return view;
    }
}
