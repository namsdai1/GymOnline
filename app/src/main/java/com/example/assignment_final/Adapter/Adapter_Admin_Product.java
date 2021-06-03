package com.example.assignment_final.Adapter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment_final.DAO.Dao_Product;
import com.example.assignment_final.R;
import com.example.assignment_final.model.Product;
import com.example.assignment_final.model.Producttype;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.example.assignment_final.Activity.Activity_Add_Store.imageViewToByte;


public class Adapter_Admin_Product extends RecyclerView.Adapter<Adapter_Admin_Product.Product_Holder> {
    private static final int GALLER_ACTION_PICK_CODE =100 ;
    public ArrayList<Product> list;
    public Context context;
    ArrayList<String> listType;
    ArrayList<Producttype> listProducttype;
    String idProducttype;

    DatabaseReference mdata;
    public Adapter_Admin_Product(Context context, ArrayList<Product> list) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public Product_Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_muahang, parent, false);
        return (new Adapter_Admin_Product.Product_Holder(view));
    }

    @Override
    public void onBindViewHolder(@NonNull Product_Holder holder, final int position) {

        holder.tvnameproduct.setText(list.get(position).getNameProduct());
        Picasso.get().load(list.get(position).getImagesproduct2()).into(holder.imgproduct);
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogProduct(context,list.get(position).getMoneyProduct(),list.get(position).getNameProduct(),list.get(position).getMotaProduct(),list.get(position).getSoluong(),list.get(position).getImagesproduct2(),position,list.get(position).getIdProducttype(),list.get(position).getNameProductType(),list.get(position).getIdProduct());

            }
        });

    }
    protected void openDialogProduct(final Context context, int moneyProduct, String tenProduct, String mota1, int soluong, final String hinhanh, final int position, String type, final String nametype, final String id){
        final ImageView img;
        final Spinner sptype;
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog( context );
        bottomSheetDialog.setContentView( R.layout.bottom_sheet_add_store2 );
        final EditText etname,etmota,etsoluong,etmoney;
        Button add2;
        listProducttype=new ArrayList<>();
        readAllType();
        sptype=bottomSheetDialog.findViewById(R.id.rcv_sp);
        etname=bottomSheetDialog.findViewById(R.id.edtTenSP2);
        etmota=bottomSheetDialog.findViewById(R.id.edtMoTaSP2);
        etsoluong=bottomSheetDialog.findViewById(R.id.edtsoluong2);
        etmoney=bottomSheetDialog.findViewById(R.id.edtPrice2);
        img=bottomSheetDialog.findViewById(R.id.productadmin_img2);
        add2=bottomSheetDialog.findViewById(R.id.btnAdd22);
        Picasso.get().load(hinhanh).into(img);
        etmoney.setText(String.valueOf(moneyProduct));
        etname.setText(tenProduct);
        etmota.setText(mota1);
        etsoluong.setText(String.valueOf(soluong));
        listType=new ArrayList<>();
        final DatabaseReference mData = FirebaseDatabase.getInstance().getReference("Producttype");
        mData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot datas: dataSnapshot.getChildren()){
                    String idtype=datas.getKey();
                    String nameProducttype=datas.child("nameProducttype").getValue().toString();
                    listType.add(nameProducttype);
                    ArrayAdapter arrayAdapter=new ArrayAdapter<>(context, android.R.layout.simple_spinner_item,listType);
                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sptype.setAdapter(arrayAdapter);
                    int vitri=listType.indexOf(nametype);
                    sptype.setSelection(vitri);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runTimePermission();
            }
        });
        add2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String text = sptype.getSelectedItem().toString();

                for (int i=0;i<listProducttype.size();i++){
                    if(text.equals(listProducttype.get(i).getNameProducttype())){
                        idProducttype=listProducttype.get(i).getIdProducttype();
                    }
                }

                try{
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
                            Toast.makeText(context, "Lỗi", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                            // ...
                            Task<Uri> dowloadURl=taskSnapshot.getMetadata().getReference().getDownloadUrl();
                            Toast.makeText(context, "Thành công ", Toast.LENGTH_SHORT).show();
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
                                    product.setIdProducttype(idProducttype);

                                    Dao_Product.update_PRODUCT_Firebase(context,id,product);
                                    list.set(position, product);
                                    notifyDataSetChanged();
                                    Toast.makeText( context, "Thêm Product thành công!", Toast.LENGTH_SHORT ).show();
                                    bottomSheetDialog.dismiss();
                                }

                            });

                        }

                    });
                }catch (Exception e){
                    Toast.makeText( context, "Bạn chưa nhập đủ thông tin!!!", Toast.LENGTH_SHORT ).show();
                }
            }
        });
        bottomSheetDialog.show();
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

    public void runTimePermission(){
        Dexter.withContext(context).withPermission( Manifest.permission.READ_EXTERNAL_STORAGE).withListener(new PermissionListener() {
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
        ((Activity)context).startActivityForResult(i,GALLER_ACTION_PICK_CODE);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class Product_Holder extends RecyclerView.ViewHolder{
        TextView tvnameproduct;
        RelativeLayout relativeLayout;
        ImageView imgproduct;

        public Product_Holder(@NonNull View itemView) {
            super(itemView);
            tvnameproduct=itemView.findViewById(R.id.txt_itemproduct);
            relativeLayout=itemView.findViewById(R.id.rlimage);
            imgproduct=itemView.findViewById(R.id.imgproduct);

        }
    }
}
