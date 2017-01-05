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

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by weichenglau on 31/12/2016.
 */

public class LoginFragment extends Fragment{
    private FragmentManager manager = getFragmentManager();
    private Button loginbtn;
    private TextView signupTextView;
    private EditText lEmail;
    private EditText lPassword;
    private LoginFragment LoginFragment;
    private Context context;
    private ProgressDialog progressdialog;
    private FirebaseAuth firebaseAuth;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.authentication, container, false);
        context = getActivity();
        getActivity().setTitle("Login");
        signupTextView = (TextView)view.findViewById(R.id.SignuptextView);
        loginbtn = (Button)view.findViewById(R.id.Loginbtn);
        lEmail = (EditText)view.findViewById(R.id.lEmailField);
        lPassword = (EditText)view.findViewById(R.id.lPasswordField);
        progressdialog = new ProgressDialog(context);
        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() != null){
            loginbtn.setEnabled(false);
            Intent intent = new Intent(context, HomeActivity.class);
            startActivity(intent);
        }
        return view;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //set register button onclickListener
        signupTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SignupFragment signup = new SignupFragment();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.content_main, signup, "signup");
                transaction.addToBackStack("SignupFragment");
                transaction.commit();
            }
        });

        //login button listener
        loginbtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                signIn();
            }
        });


    }

    private void signIn() {
        String email = lEmail.getText().toString();
        String password = lPassword.getText().toString();

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


        progressdialog.setMessage("One moment please...");
        progressdialog.show();

        //login action and result
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener((Activity) context,new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    progressdialog.hide();
                    Toast.makeText(context, "Login Successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, HomeActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(context, "Registered failed, Please try again...", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
