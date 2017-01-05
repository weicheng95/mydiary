package com.example.weichenglau.personalDiary.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weichenglau.personalDiary.HomeActivity;
import com.example.weichenglau.personalDiary.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by weichenglau on 01/01/2017.
 */

public class SignupFragment extends Fragment {
    private Button signup;
    private EditText sEmail;
    private EditText sPassword;
    private TextView loginTextview;
    private Context context;
    private ProgressDialog progressdialog;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference root;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = getActivity();
        View view = inflater.inflate(R.layout.signup,container,false);
        signup = (Button)view.findViewById(R.id.signUp);
        sEmail = (EditText)view.findViewById(R.id.SEmailField);
        sPassword = (EditText)view.findViewById(R.id.SPasswordField);
        loginTextview = (TextView)view.findViewById(R.id.loginTextView);
        progressdialog = new ProgressDialog(context);
        firebaseAuth = FirebaseAuth.getInstance();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Register");
        //signup button listener
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }


        });

        //login button listener
        loginTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginFragment login = new LoginFragment();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.content_main, login, "login");
                transaction.addToBackStack("loginFragment");
                transaction.commit();
            }
        });


    }

    private void signup() {
        final String email = sEmail.getText().toString();
        final String password = sPassword.getText().toString();

        //check for user email and password empty values
        if (TextUtils.isEmpty(email)) {

            Toast.makeText(context, "Please enter email", Toast.LENGTH_SHORT).show();
            //stop the function execution further
            return;
        }

        if(TextUtils.isEmpty(password)){

            Toast.makeText(context, "Please enter password", Toast.LENGTH_SHORT).show();
            //stop the function execution further
            return;
        }

        progressdialog.setMessage("Register user...");
        progressdialog.show();

        //signup action and login action and show result
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    progressdialog.hide();
                    //create a user with Uid
                    root =  FirebaseDatabase.getInstance().getReference();
                    String getuser = firebaseAuth.getCurrentUser().getUid().toString();
                    Map<String, Object> user = new HashMap<String, Object>();
                    user.put(getuser,"");
                    root.updateChildren(user);

                    Toast.makeText(context, "Registered Successfully", Toast.LENGTH_SHORT).show();

                    progressdialog.setMessage("Loging user, Please wait...");
                    firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener((Activity) context,new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                progressdialog.hide();
                                Intent intent = new Intent(context, HomeActivity.class);
                                startActivity(intent);
                            }else{
                                Toast.makeText(context, "Login failed, Please try again...", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }else{
                    Toast.makeText(context, "Registered failed, Please try again...", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
