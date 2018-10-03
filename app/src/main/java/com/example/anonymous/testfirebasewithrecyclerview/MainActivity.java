package com.example.anonymous.testfirebasewithrecyclerview;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private DatabaseReference mDatabase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Globel");
        mDatabase.keepSynced(true);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerAdapter<Blog,BlogViewHoler> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Blog, BlogViewHoler>(Blog.class,R.layout.row,BlogViewHoler.class,mDatabase) {
            @Override
            protected void populateViewHolder(BlogViewHoler viewHolder, Blog model, int position) {
                viewHolder.setTitle(model.getTitle());
                viewHolder.setDesc(model.getDesc());
                viewHolder.setImage(getApplicationContext(),model.getImage());
            }
        };

        recyclerView.setAdapter(firebaseRecyclerAdapter);


    }


    public static class BlogViewHoler extends RecyclerView.ViewHolder{
        View mView;


        public BlogViewHoler(View itemView) {
            super(itemView);
            mView=itemView;
        }
        public void setTitle(String title){
            TextView postTitle = itemView.findViewById(R.id.postTitle);
            postTitle.setText(title);
        }
        public void setDesc(String desc){
            TextView postDesc = itemView.findViewById(R.id.postDesc);
            postDesc.setText(desc);
        }
        public void setImage(Context ctx, String image){
            ImageView postImage = itemView.findViewById(R.id.postImage);
            Picasso.with(ctx).load(image).into(postImage);
        }
    }
}
