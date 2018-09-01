package com.sales.logsis.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sales.logsis.Model.SQLiteDBHelper;
import com.sales.logsis.R;

public class MainActivity extends AppCompatActivity {

    //obter acesso à dados
    SQLiteOpenHelper sqLiteOpenHelper;
    //Objetos de manipulação das setenças SQL
    SQLiteDatabase db;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //realizar referenciamento com objetos do layout
        final EditText txtEmail =  findViewById(R.id.txtEmail);
        final EditText txtSenha =  findViewById(R.id.txtSenha);
        Button btnAcessar =  findViewById(R.id.btnAcessar);
        TextView btnReg = findViewById(R.id.btnReg);


        //Disponbilizando Pipeline SQLite
        sqLiteOpenHelper = new SQLiteDBHelper(this);
        db = sqLiteOpenHelper.getReadableDatabase();

        btnAcessar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String email = txtEmail.getText().toString();
                String senha = txtSenha.getText().toString();

                cursor = db.rawQuery("SELECT * FROM "+SQLiteDBHelper.TABELA_NOME +
                        " WHERE " + SQLiteDBHelper.COLUMN_EMAIL+"=? AND "
                        + SQLiteDBHelper.COLUMN_SENHA + "=?",new String[]{email,senha});
                if (cursor != null){
                    cursor.moveToFirst();
                    //retornando nome do usuário e mail depois do login com sucesso
                    //e repassando para a activity LoginActivity
                    String nomeTxt = cursor.getString(cursor.getColumnIndex(SQLiteDBHelper.COLUMN_NOME));
                    String emailTxt = cursor.getString(cursor.getColumnIndex(SQLiteDBHelper.COLUMN_EMAIL));
                    Toast.makeText(MainActivity.this,"Login com Sucesso",
                            Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                    intent.putExtra("nome",nomeTxt);
                    intent.putExtra("email",emailTxt);
                    startActivity(intent);
                    //Removendo MainActivity[Tela de Login] do empilhamento prevendo o botão voltar acionado
                    finish();
                }
                else {

                    //Apresentação do Caixa de Alerta
                    final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Alerta");
                    builder.setMessage("Usuário ou Senha está errado.");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            dialogInterface.dismiss();

                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();

                }
            }
    });

    // Intent para abrir RegistrarContaActivity
        btnReg.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            Intent intent = new Intent(MainActivity.this, RegistrarContaActivity.class);
            startActivity(intent);
        }
    });

}
}