package com.example.ashi.devconemergencyapp.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ashi.devconemergencyapp.Model.Complaint;
import com.example.ashi.devconemergencyapp.Model.Counter;
import com.example.ashi.devconemergencyapp.R;
import com.example.ashi.devconemergencyapp.rest.Utility;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ComplainSystemFragment extends Fragment {
    String lati;
    Complaint com1;
    Complaint com2;
    String longi;
    byte[] byteArray;
    Spinner spinner;
    int count = 0;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private Button btnSelect, btnupload;
    private ImageView ivImage;
    private String userChoosenTask;
    Bitmap setBitmap;
    DatabaseReference mDatabase;
    Uri downloadUrl;
    ProgressBar progressBar;
    StorageReference storageRef;
    private OnFragmentInteractionListener mListener;
    private FusedLocationProviderClient mFusedLocationClient;
    private String category;

    public ComplainSystemFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        View view = inflater.inflate(R.layout.fragment_complain_system, container, false);
        spinner=view.findViewById(R.id.spinner);
        progressBar=view.findViewById(R.id.progressbar);
        final List<String> categories = new ArrayList<String>();
        categories.add("Choose Category");
        categories.add("Police");
        categories.add("Railway");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, categories);
        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0)
                {
                    Toast.makeText(getContext(), "Please choose a Category", Toast.LENGTH_SHORT).show();
                }
                else
                    category=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        if (mListener != null) {
            mListener.onFragmentInteraction("Complain");
        }
        btnupload = view.findViewById(R.id.submit);
        String[] perms = {"android.permission.FINE_LOCATION", "android.permission.CAMERA","android.permission.COARSE_LOCATION"};

        int permsRequestCode = 200;
        requestPermissions(perms, permsRequestCode);


        btnSelect = (Button) view.findViewById(R.id.btnSelectPhoto);
        btnSelect.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
//        btnupload.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {

//        });
        ivImage = (ImageView) view.findViewById(R.id.ivImage);

        //Location
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getContext());

        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return view;
        }
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            // Logic to handle location object
                            lati=String.valueOf(location.getLatitude());
                            longi=String.valueOf(location.getLongitude());

                        }
                    }
                });

        final EditText desc=view.findViewById(R.id.editText);
        Button submit=view.findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ByteArrayOutputStream bStream = new ByteArrayOutputStream();
                if(setBitmap==null)
                {
                    Toast.makeText(getContext(), "Select image", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    setBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bStream);
                    byteArray = bStream.toByteArray();
//                    Intent anotherIntent = new Intent(getContext(), MainActivity.class);
//                    anotherIntent.putExtra("image", byteArray);
//                    setResult(Activity.RESULT_OK,anotherIntent);
//                    finish();
                }


                if(desc.getText().toString().equals(""))
                {
                    Toast.makeText(getContext(), "Enter all Details", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    final Complaint complain = new Complaint();
                    complain.setDescription(desc.getText().toString());
                    // sdate=date.getText().toString();
                    //Bitmap bmp;
                    StorageReference imagesRef = storageRef.child(count+".png");
                    if(byteArray==null)
                    {
                        Toast.makeText(getContext(), "Upload image", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {progressBar.setVisibility(View.VISIBLE);
                        //bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                        UploadTask uploadTask = imagesRef.putBytes(byteArray);
                        uploadTask.addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                // Handle unsuccessful uploads
                            }
                        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                                downloadUrl = taskSnapshot.getDownloadUrl();
                                //Toast.makeText(MainActivity.this, ""+downloadUrl, Toast.LENGTH_SHORT).show();
                                complain.setImage(downloadUrl.toString());
                                complain.setLongi(longi);
                                complain.setLati(lati);
                                if(spinner.getSelectedItem().toString().equals("Choose Category"))
                                {
                                    Toast.makeText(getContext(), "Choose category", Toast.LENGTH_SHORT).show();
                                    return;

                                }
                                complain.setCategory(category);

                                mDatabase.child("Complaint").push().setValue(complain).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(getContext(), "Complaint filed succesfully", Toast.LENGTH_SHORT).show();
                                        desc.setText("");
                                        setBitmap=null;
                                        ivImage.setImageBitmap(null);
                                        progressBar.setVisibility(View.GONE);
                                        getActivity().getSupportFragmentManager().popBackStackImmediate();
                                    }
                                });
                            }
                        });
                    }
                }


            }
        });
        return view;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(userChoosenTask.equals("Take Photo"))
                        cameraIntent();
//                    else if(userChoosenTask.equals("Choose from Library"))
//                        galleryIntent();
                } else {
                    //code for deny
                }
                break;
        }
    }

    private void selectImage() {
        final CharSequence[] items = { "Take Photo", "Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result= Utility.checkPermission(getContext());
                if (items[item].equals("Take Photo")) {
                    userChoosenTask="Take Photo";
                    if(result)
                        cameraIntent();
                }
//else if (items[item].equals("Choose from Library")) {
//                    userChoosenTask="Choose from Library";
//                    if(result)
//                        galleryIntent();
//                }
                else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void galleryIntent()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"),SELECT_FILE);
    }

    private void cameraIntent()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        setBitmap=thumbnail;
        ivImage.setImageBitmap(thumbnail);
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {

        Bitmap bm=null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), data.getData());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        setBitmap=bm;
        ivImage.setImageBitmap(bm);
    }






    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String uri);
    }

}

