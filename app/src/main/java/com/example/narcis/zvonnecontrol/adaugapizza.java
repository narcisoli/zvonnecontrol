package com.example.narcis.zvonnecontrol;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.narcis.zvonnecontrol.obiecte.pizza;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class adaugapizza extends AppCompatActivity {


    private Button butonadauga;
    private Button butonfinalizeaza;
    private EditText editnume;
    private EditText editingre;
    private EditText editgramaj;
    private EditText editpret;
    private Uri filePath;
    private Bitmap bitmap;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pd = new ProgressDialog(this);
        pd.setMessage("Se incarca....");
        setContentView(R.layout.activity_adaugapizza);
        butonadauga = (Button) findViewById(R.id.buton1);
        butonfinalizeaza = (Button) findViewById(R.id.buton2);
        butonfinalizeaza.setVisibility(View.GONE);
        editnume = (EditText) findViewById(R.id.edittext1);
        editingre = (EditText) findViewById(R.id.edittext2);
        editgramaj = (EditText) findViewById(R.id.edittext3);
        editpret = (EditText) findViewById(R.id.edittext4);
        butonadauga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (testeaza()) {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_PICK);
                    startActivityForResult(Intent.createChooser(intent, "Select Image"), 111);
                }
            }
        });
        butonfinalizeaza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pizza pizza = new pizza(editnume.getText().toString(), editingre.getText().toString(), editgramaj.getText().toString(), Integer.parseInt(editpret.getText().toString()), 0, 0);
                FirebaseDatabase.getInstance().getReference().child("Zvonne").child("Pizza").child(pizza.getTip()).setValue(pizza);
                Toast.makeText(adaugapizza.this, "Succes", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 111 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();

            pd.show();

            StorageReference childRef = FirebaseStorage.getInstance().getReference().child("Imagini").child("Pizza").child(editnume.getText().toString() + ".jpg");
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            Bitmap bitmap1 = null;
            try {
                bitmap1 = MediaStore.Images.Media.getBitmap(this.getContentResolver(), filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
            bitmap = getResizedBitmap(bitmap1, 200, 200);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] data1 = baos.toByteArray();


            UploadTask uploadTask = childRef.putBytes(data1);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    pd.dismiss();
                    Toast.makeText(adaugapizza.this, "Nu s-a incarcat", Toast.LENGTH_SHORT).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    butonadauga.setVisibility(View.GONE);
                    butonfinalizeaza.setVisibility(View.VISIBLE);
                    pd.dismiss();
                }
            });
        } else {
            Toast.makeText(adaugapizza.this, "Adauga o imagine", Toast.LENGTH_SHORT).show();
        }

    }

    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }

    private boolean testeaza() {
        boolean a = true;
        if (TextUtils.isEmpty(editnume.getText().toString()) || TextUtils.isEmpty(editgramaj.getText().toString()) || TextUtils.isEmpty(editingre.getText().toString()) || TextUtils.isEmpty(editpret.getText().toString())) {
            Toast.makeText(adaugapizza.this, "Nu lasa campuri goale", Toast.LENGTH_SHORT).show();
            a = false;
        }
        boolean b = true;
        if (!TextUtils.isDigitsOnly(editpret.getText().toString())) {
            Toast.makeText(adaugapizza.this, "Pretul trebuie sa fie numar", Toast.LENGTH_SHORT).show();
            b = false;
        }
        return a & b;
    }
}
