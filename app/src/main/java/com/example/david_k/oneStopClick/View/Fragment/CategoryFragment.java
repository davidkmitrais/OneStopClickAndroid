package com.example.david_k.oneStopClick.View.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.david_k.oneStopClick.Firebase.FirebaseProvider;
import com.example.david_k.oneStopClick.Helper.Constants;
import com.example.david_k.oneStopClick.ModelLayers.Database.Category;
import com.example.david_k.oneStopClick.ModelLayers.Enums.ChildActivityPages;
import com.example.david_k.oneStopClick.R;
import com.example.david_k.oneStopClick.View.Activity.ChildActivity;
import com.example.david_k.oneStopClick.View.Adapter.CategoryCardAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment extends Fragment {

    RecyclerView recyclerView;
    private CategoryCardAdapter adapter;
    private List<Category> categoryList;
    DatabaseReference categoryDBRef = FirebaseProvider.getCurrentProvider().getCategoryDBReference();


    public CategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_category, container, false);

        setupRecyclerView(view);

        return view;
    }

    private void setupRecyclerView(View view) {
        recyclerView = view.findViewById(R.id.category_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        categoryList = new ArrayList<>();

        categoryDBRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                setCategoryFromSnapshot(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                setCategoryFromSnapshot(dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void setCategoryFromSnapshot(DataSnapshot dataSnapshot){
        Category category = dataSnapshot.getValue(Category.class);
        category.setFirebaseKey(dataSnapshot.getKey());
        categoryList.add(category);
        adapter = new CategoryCardAdapter(getActivity(), categoryList, (v, item) -> rowTapped(item));
        recyclerView.setAdapter(adapter);
    }

    private void rowTapped(Category categoryItem) {

//        Toast.makeText(getActivity(), "Go to Category " + categoryItem.getCategoryName(), Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(getActivity(), ChildActivity.class);
        intent.putExtra(Constants.categoryKey, categoryItem);
        intent.putExtra(Constants.childPageActivityKey, Constants.ChildActivityPagesEnum.CATEGORIZED_PRODUCT);

        startActivity(intent);
    }
}
