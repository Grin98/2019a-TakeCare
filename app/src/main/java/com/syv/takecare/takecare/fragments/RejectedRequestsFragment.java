package com.syv.takecare.takecare.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.syv.takecare.takecare.R;
import com.syv.takecare.takecare.POJOs.RequestedItemsInformation;

public class RejectedRequestsFragment extends RequestedItemsBaseFragment {
    private static final String TAG = "RejectedRequestsFrag";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rejected_requests, container, false);
        super.initializeFragment(view);
        return view;
    }

    @Override
    protected FirestoreRecyclerAdapter setFirestoreRecyclerAdapter() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        final FirebaseUser user = auth.getCurrentUser();
        assert user != null;

        Query query = db.collection("users").document(user.getUid()).collection("requestedItems")
                .whereEqualTo("requestStatus", 2)
                .orderBy("timestamp", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<RequestedItemsInformation> response = new FirestoreRecyclerOptions.Builder<RequestedItemsInformation>()
                .setQuery(query, RequestedItemsInformation.class)
                .build();

        return super.setFirestoreRecyclerAdapter(response, 2);
    }
}

