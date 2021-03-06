package com.syv.takecare.takecare.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.syv.takecare.takecare.POJOs.FeedCardInformation;
import com.syv.takecare.takecare.R;

public class MyAvailableItemsFragment extends SharedItemsBaseFragment {
    private static final String TAG = "MyAvailableItemsFrag";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_available_items, container, false);
        super.initializeFragment(view);
        return view;
    }

    @Override
    protected FirestoreRecyclerAdapter setFirestoreRecyclerAdapter() {
        Log.d(TAG, "setFirestoreRecyclerAdapter at MyAvailableItems: Starting. ");
        FirebaseAuth auth = FirebaseAuth.getInstance();
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        final FirebaseUser user = auth.getCurrentUser();
        assert user != null;

        Query query = db.collection("items")
                .whereEqualTo("publisher", user.getUid())
                .whereLessThanOrEqualTo("status", 1)
                .orderBy("status", Query.Direction.ASCENDING)
                .orderBy("timestamp", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<FeedCardInformation> response = new FirestoreRecyclerOptions.Builder<FeedCardInformation>()
                .setQuery(query, FeedCardInformation.class)
                .build();

        return super.setFirestoreRecyclerAdapter(response);
    }
}
