package com.example.weichenglau.personalDiary.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.weichenglau.personalDiary.HomeActivity;
import com.example.weichenglau.personalDiary.R;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by weichenglau on 03/01/2017.
 */
public class LogoutFragment extends Fragment{
    private Button logoutbtn;
    private FirebaseAuth firebaseAuth;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.logoutfragment, container, false);
        getActivity().setTitle("Logout");
        logoutbtn = (Button)view.findViewById(R.id.logoutbtn);
        firebaseAuth = FirebaseAuth.getInstance();

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
    }

    private void logout() {
        firebaseAuth.signOut();
        Toast.makeText(getActivity(), "Logout successfully, see you next!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity(), HomeActivity.class);
        startActivity(intent);
}
}
