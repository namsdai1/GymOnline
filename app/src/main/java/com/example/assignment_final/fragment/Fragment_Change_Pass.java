package com.example.assignment_final.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.assignment_final.DAO.DAO_User;
import com.example.assignment_final.Database.DbHelper;
import com.example.assignment_final.R;
import com.example.assignment_final.model.User;

public class Fragment_Change_Pass extends Fragment {
    EditText edtPassCu,edtPassMoi,edtPassMoiXacNhan;
    Button btnDoiMK;
    public DAO_User userDAO;
    User user;
    DbHelper dbHelper;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.activity_user_change_password, container, false);
        edtPassCu= view.findViewById( R.id.edtPassCu );
        edtPassMoi= view.findViewById( R.id.edtPassMoi );
        edtPassMoiXacNhan= view.findViewById( R.id.edtPassMoiXacNhan );
        btnDoiMK=view.findViewById( R.id.btnDoiMK );
        btnDoiMK.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    if(edtPassCu.getText().toString().equals(dbHelper.PassUser ) && edtPassMoiXacNhan.getText().toString().equals( edtPassMoi.getText().toString() )){
                        String passMoi= edtPassMoi.getText().toString();
                        userDAO = new DAO_User( getContext() );
                        userDAO.update_Pass_Firebase( dbHelper.IDuser, passMoi);
                        Toast.makeText( getContext(), "Đổi mật khẩu thành công", Toast.LENGTH_SHORT ).show();
                    }
                    else if(!edtPassCu.getText().toString().equals(dbHelper.PassUser )){
                        Toast.makeText( getContext(), "Bạn nhập sai mật khẩu cũ!!!", Toast.LENGTH_SHORT ).show();
                    }
                    else  if(!edtPassMoiXacNhan.getText().toString().equals( edtPassMoi.getText().toString() )) {
                        Toast.makeText( getContext(), "Bạn nhập mật khẩu xác nhận không khớp!!!", Toast.LENGTH_SHORT ).show();
                    }
            }
        } );
        return view;
    }




}
