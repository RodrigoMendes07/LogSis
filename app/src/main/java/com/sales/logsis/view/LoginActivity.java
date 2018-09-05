package com.sales.logsis.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.sales.logsis.R;

import java.io.FileNotFoundException;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private static final int SELECT_PHOTO = 100;
    ImageView dpImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_sucesso);

        //PARA OCULTAR A AppBar DA TELA CHEIA.
//        ActionBar ab = getSupportActionBar();
//        ab.hide();

        TextView txtNome = (TextView) findViewById(R.id.txt_sucesso_nome);
        TextView txtEmail = (TextView) findViewById(R.id.txt_sucesso_email);
        Button btnLogout = (Button) findViewById(R.id.btn_logout);
        dpImage = (ImageView) findViewById(R.id.imgclick);

        Intent intent = getIntent();

        String loginNome = intent.getStringExtra("nome");
        String loginEmail = intent.getStringExtra("email");
        txtNome.setText("Bem-Vindo, " +loginNome);
        txtEmail.setText(loginEmail);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                builder.setTitle("Info");
                builder.setMessage("Você quer fazer logout ??");
                builder.setPositiveButton("Deixe-me sair!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });

                builder.setNegativeButton("Agora não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        dialogInterface.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        //=========Seção para mudança de imagem apresentada quando clicar=========

        dpImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, SELECT_PHOTO);
            }
        });

    }

    //ESTE MÉTODO É CHAMADO QUANDO O USUÁRIO SELECIONA UMA IMAGEM DO ImagePicker.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch(requestCode) {
            case SELECT_PHOTO:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = imageReturnedIntent.getData();

                    Bitmap yourSelectedImage = null;
                    try {
                        //MÉTODO decodeUri() DEFINIDO ABAIXO
                        yourSelectedImage = decodeUri(selectedImage);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    dpImage.setImageBitmap(yourSelectedImage);
                }
        }
    }

    //decodeUri() MÉTODO PARA DECOFICAR IMAGEM PARA EXCEÇÃO: Out of Memory Exception
    private Bitmap decodeUri(Uri selectedImage) throws FileNotFoundException {

        // Decodifica tamanho da imagem
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage), null, o);

        // o novo tamanho que queremos
        final int REQUIRED_SIZE = 140;

        // encontar o valor da escala correta. Ela deverã ser de tamanho 2
        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;
        while (true) {
            if (width_tmp / 2 < REQUIRED_SIZE
                    || height_tmp / 2 < REQUIRED_SIZE) {
                break;
            }
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }

        // Decodifica com inSampleSize
        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        return BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage), null, o2);

    }
}
