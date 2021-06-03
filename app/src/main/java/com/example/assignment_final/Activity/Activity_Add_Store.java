package com.example.assignment_final.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment_final.Adapter.Adapter_Admin_Product;
import com.example.assignment_final.DAO.Dao_Product;
import com.example.assignment_final.R;
import com.example.assignment_final.model.Product;
import com.example.assignment_final.model.Producttype;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class Activity_Add_Store extends AppCompatActivity {
    FloatingActionButton btnadd;
    ImageView img;
    Adapter_Admin_Product adapter;

    LinearLayout lv1,lv2,lv3,lv4;
    ArrayList<String> listType;

    int position1=0;
    ImageView imgaddproduct;
    RecyclerView recyclerView;
    private static final int GALLER_ACTION_PICK_CODE = 100;
    DatabaseReference mdata;
    String idproducttype;
    String nameProductType;
    ArrayList<Product> list;
    ArrayList<Producttype> listProducttype;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_store);
        setTitle("STORE");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        list=new ArrayList<>();
        listProducttype=new ArrayList<>();
        imgaddproduct=findViewById(R.id.imgaddproduct);
        btnadd=findViewById(R.id.addStore);
        recyclerView=findViewById(R.id.rcv_add_product);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        DatabaseReference mdata = FirebaseDatabase.getInstance().getReference("PRODUCT");
        mdata.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot datas: dataSnapshot.getChildren()){
                    String ID=datas.getKey();
                    String idProducttype=datas.child("idProducttype").getValue().toString();
                    String imagesproduct2=datas.child("imagesproduct2").getValue().toString();
                    String name=datas.child("nameProduct").getValue().toString();
                    int money=Integer.parseInt(datas.child("moneyProduct").getValue().toString());
                    String motaProduct=datas.child("motaProduct").getValue().toString();
                    int soluong=Integer.parseInt(datas.child("soluong").getValue().toString());
                    boolean rateProduct=Boolean.parseBoolean(datas.child("rateProduct").getValue().toString());
                    for(int i=0;i<listProducttype.size();i++){
                        if(idProducttype.equals(listProducttype.get(i).getIdProducttype())){
                            nameProductType=listProducttype.get(i).getNameProducttype();
                        }
                    }
                    Product product=new Product(ID,name,motaProduct,money,soluong,rateProduct,idProducttype,imagesproduct2,nameProductType);
                    list.add(product);
                    adapter=new Adapter_Admin_Product(Activity_Add_Store.this,list);
                    recyclerView.setAdapter(adapter);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        lv1=findViewById(R.id.lv_1);
        lv2=findViewById(R.id.lv_2);
        lv3=findViewById(R.id.lv_3);
        lv4=findViewById(R.id.lv_4);
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomsheet(Activity_Add_Store.this);
            }
        });
        imgaddproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.clear();
                listProducttype=new ArrayList<>();
                final DatabaseReference mdata = FirebaseDatabase.getInstance().getReference("PRODUCT");
                mdata.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot datas: dataSnapshot.getChildren()){
                            String ID=datas.getKey();
                            String idProducttype=datas.child("idProducttype").getValue().toString();
                            String imagesproduct2=datas.child("imagesproduct2").getValue().toString();
                            String name=datas.child("nameProduct").getValue().toString();
                            int money=Integer.parseInt(datas.child("moneyProduct").getValue().toString());
                            String motaProduct=datas.child("motaProduct").getValue().toString();
                            int soluong=Integer.parseInt(datas.child("soluong").getValue().toString());
                            boolean rateProduct=Boolean.parseBoolean(datas.child("rateProduct").getValue().toString());
                            for(int i=0;i<listProducttype.size();i++){
                                if(idProducttype.equals(listProducttype.get(i).getIdProducttype())){
                                    nameProductType=listProducttype.get(i).getNameProducttype();
                                }
                            }
                            Product product=new Product(ID,name,motaProduct,money,soluong,rateProduct,idProducttype,imagesproduct2,nameProductType);

                            list.add(product);
                            adapter=new Adapter_Admin_Product(Activity_Add_Store.this,list);
                            recyclerView.setAdapter(adapter);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }
        });
        lv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.clear();
                readAllType();
                readAllProduct("Protein");

            }
        });
        lv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.clear();
                readAllType();
                readAllProduct("Bcaa");
            }
        });
        lv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.clear();
                readAllType();
                readAllProduct("Accessories");
            }
        });
        lv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.clear();
                readAllType();
                readAllProduct("Milk");
            }
        });

    }
    private void readAllProduct(final String ten){
        final DatabaseReference mdata = FirebaseDatabase.getInstance().getReference("PRODUCT");
        mdata.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot datas: dataSnapshot.getChildren()){
                    String Id=datas.getKey();
                    String idProducttype=datas.child("idProducttype").getValue().toString();
                    String imagesproduct2=datas.child("imagesproduct2").getValue().toString();
                    String name=datas.child("nameProduct").getValue().toString();
                    int money=Integer.parseInt(datas.child("moneyProduct").getValue().toString());
                    String motaProduct=datas.child("motaProduct").getValue().toString();
                    int soluong=Integer.parseInt(datas.child("soluong").getValue().toString());
                    boolean rateProduct=Boolean.parseBoolean(datas.child("rateProduct").getValue().toString());
                    for(int i=0;i<listProducttype.size();i++){
                        if(idProducttype.equals(listProducttype.get(i).getIdProducttype())){
                            nameProductType=listProducttype.get(i).getNameProducttype();
                        }
                    }
                    Product product=new Product(Id,name,motaProduct,money,soluong,rateProduct,idProducttype,imagesproduct2,nameProductType);
                    if(product.getNameProductType().equals(ten)){
                        list.add(product);
                    }

                    adapter=new Adapter_Admin_Product(Activity_Add_Store.this,list);
                    recyclerView.setAdapter(adapter);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
    private void readAllType(){
        final DatabaseReference mdata = FirebaseDatabase.getInstance().getReference("Producttype");
        mdata.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot datas: dataSnapshot.getChildren()){
                    final String id = datas.getKey();
                    String nameProducttype=datas.child("nameProducttype").getValue().toString();
                    listProducttype.add(new Producttype(id,nameProducttype));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    private void readAllProductType(){

    }



    private void bottomsheet(final Context context) {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog( context );
        bottomSheetDialog.setContentView( R.layout.bottom_sheet_add_store );
        final EditText etname,etmota,etsoluong,etmoney;
        Button add2;
        final Spinner sptype;
        etname=bottomSheetDialog.findViewById(R.id.edtTenSP);
        etmota=bottomSheetDialog.findViewById(R.id.edtMoTaSP);
        etsoluong=bottomSheetDialog.findViewById(R.id.edtsoluong);
        etmoney=bottomSheetDialog.findViewById(R.id.edtPrice);
        sptype=bottomSheetDialog.findViewById(R.id.sptype);
        img=bottomSheetDialog.findViewById(R.id.productadmin_img);
        add2=bottomSheetDialog.findViewById(R.id.btnAdd2);
        listType=new ArrayList<>();
        readAllType();
        final DatabaseReference mData = FirebaseDatabase.getInstance().getReference("Producttype");
        mData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot datas: dataSnapshot.getChildren()){
                    final String id = datas.getKey();
                    String nameProducttype=datas.child("nameProducttype").getValue().toString();
                    listType.add(nameProducttype);
                    ArrayAdapter arrayAdapter=new ArrayAdapter<>(context, android.R.layout.simple_spinner_item,listType);
                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sptype.setAdapter(arrayAdapter);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        sptype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item_position = String.valueOf(position);

                position1 = Integer.valueOf(item_position)+1;
                Toast.makeText(context, "value is "+ position1, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runTimePermission();
            }
        });
        add2.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i=0;i<listProducttype.size();i++){
                    if(sptype.getSelectedItem().toString().equals(listProducttype.get(i).getNameProducttype())){
                        idproducttype=listProducttype.get(i).getIdProducttype();
                    }
                }


                try {
                    mdata= FirebaseDatabase.getInstance().getReference();
                    FirebaseStorage storage=FirebaseStorage.getInstance();
                    final StorageReference storageReference = storage.getReferenceFromUrl("gs://duan1lt15304nhom1.appspot.com");
                    java.util.Calendar calendar = java.util.Calendar.getInstance();
                    StorageReference mountainsRef=storageReference.child("image"+calendar.getTimeInMillis()+".png");
                    img.setDrawingCacheEnabled(true);
                    img.buildDrawingCache();
                    byte[] data =imageViewToByte(img);

                    UploadTask uploadTask = mountainsRef.putBytes(data);
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            Toast.makeText(Activity_Add_Store.this, "Lỗi", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                            // ...
                            Task<Uri> dowloadURl=taskSnapshot.getMetadata().getReference().getDownloadUrl();
                            Toast.makeText(Activity_Add_Store.this, "Thành công ", Toast.LENGTH_SHORT).show();
                            dowloadURl.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String imageUrl = uri.toString();
                                    Product product=new Product();
                                    product.setImagesproduct2(imageUrl);
                                    product.setNameProduct(etname.getText().toString());
                                    product.setMotaProduct(etmota.getText().toString());
                                    product.setMoneyProduct(Integer.parseInt(etmoney.getText().toString()));
                                    product.setSoluong(Integer.parseInt(etsoluong.getText().toString()));
                                    product.setIdProducttype(idproducttype);
                                    Dao_Product.insert_Product_Firebase(context,product);
                                    list.add(product);
                                    adapter.notifyDataSetChanged();
                                    Toast.makeText( context, "Thêm Product thành công!", Toast.LENGTH_SHORT ).show();
                                    bottomSheetDialog.dismiss();
                                }

                            });

                        }

                    });




                }catch (Exception e){
                    Toast.makeText( Activity_Add_Store.this, "Bạn chưa nhập đủ thông tin!!!", Toast.LENGTH_SHORT ).show();
                }


                final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference( "PRODUCT" );
                mDatabase.addListenerForSingleValueEvent( new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        } );
        bottomSheetDialog.show();
    }



    public void runTimePermission(){
        Dexter.withContext(Activity_Add_Store.this).withPermission( Manifest.permission.READ_EXTERNAL_STORAGE).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                galleryIntent();
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

            }

            @Override
            public void onPermissionRationaleShouldBeShown(com.karumi.dexter.listener.PermissionRequest permissionRequest, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();
            }
        }).check();
    }
    private void galleryIntent() {
        Intent i = new Intent(Intent.ACTION_PICK);
        i.setType("image/*");
        startActivityForResult(i,GALLER_ACTION_PICK_CODE);
    }

    //Convert Bitmap To Byte
    public static byte[] imageViewToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap= getResizedBitmap( bitmap,1024 );
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }
    public static Bitmap getResizedBitmap(Bitmap bitmap, int maxSize) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float bitmapRatio = (float) width / height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(bitmap, width, height, true);
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK){
            if(requestCode == GALLER_ACTION_PICK_CODE){
                Uri imageUri = data.getData();
                img.setImageURI(imageUri);
            }
        }
    }
}