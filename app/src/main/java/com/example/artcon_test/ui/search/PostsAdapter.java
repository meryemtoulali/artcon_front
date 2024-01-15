package com.example.artcon_test.ui.search;

public class PostsAdapter /*extends RecyclerView.Adapter<PostsAdapter.ViewHolder>*/ {
//    private List<Post> postsList = new ArrayList<>();
//    String TAG = "AllTooWell";
//
//    public void setPostsList(List<Post> postsList) {
//        this.postsList = postsList;
//        Log.d(TAG, "set postsList" + postsList);
//        notifyDataSetChanged();
//    }
//
//    @NonNull
//    @Override
//    public PostsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_post_fragment, parent, false);
//        return new PostsAdapter.ViewHolder(itemView);
//
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull PostsAdapter.ViewHolder holder, int position) {
//        Post post = postsList.get(position);
//        holder.textViewFullName.setText(post.getUser().getFirstname() + " " + post.getUser().getLastname());
//        holder.textViewUsername.setText("@" + post.getUser().getUsername());
//        Picasso.get().load(post.getUser().getPicture())
//                .placeholder(R.drawable.profile_picture_placeholder) // Placeholder image
//                .into(holder.imageViewProfile);
//        if (!isEmpty(post.getMediaFiles())) {
//            Picasso.get().load(post.getMediaFiles())
//                .placeholder(R.drawable.sample_image) // Placeholder image
//                .into(holder.imageViewPostImage);
//            holder.imageViewPostImage.setVisibility(View.VISIBLE);
//        } else {
//            holder.imageViewPostImage.setVisibility(View.GONE);
//        }
//        if (!isEmpty(post.getDescription())) {
//            holder.textViewPostTextArea.setVisibility(View.VISIBLE);
//            holder.textViewPostTextArea.setText(post.getDescription());
//        } else {
//            holder.textViewPostTextArea.setVisibility(View.GONE);
//        }
////        holder.textViewLikeCount.setText.valueOf(post.getLikes());
////        holder.textViewCommentCount.setText.valueOf(post.getComment());
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return postsList.size();
//    }
//
//    public static class ViewHolder extends RecyclerView.ViewHolder {
//        TextView textViewFullName;
//        TextView textViewUsername;
//        TextView textViewPostTextArea;
//        TextView textViewLikeCount;
//        TextView textViewCommentCount;
//        ImageView imageViewProfile;
//        ImageView imageViewPostImage;
//        public ViewHolder(View itemView) {
//            super(itemView);
//            textViewFullName = itemView.findViewById(R.id.profileName);
//            textViewUsername = itemView.findViewById(R.id.username);
//            textViewPostTextArea = itemView.findViewById(R.id.postTextArea);
//            imageViewProfile = itemView.findViewById(R.id.profileImage);
//            textViewLikeCount = itemView.findViewById(R.id.likeCount);
//            textViewCommentCount = itemView.findViewById(R.id.commentCount);
//            imageViewPostImage = itemView.findViewById(R.id.postImage);
//        }
//    }
}
