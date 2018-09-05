package com.sales.logsis.view;

import android.app.AlertDialog;
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

import com.sales.logsis.Model.dao.SQLiteDBHelper;
import com.sales.logsis.R;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    SQLiteOpenHelper dbhelper;
    SQLiteDatabase db;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //para escoder o AppBar da tela cheia
        ActionBar ab = getSupportActionBar();
        ab.hide();

        //Referenciando email do usuároi, senha  - EditText e TextView para acesso
        final EditText txtEmail = (EditText) findViewById(R.id.txtemail);
        final EditText txtSenha = (EditText) findViewById(R.id.txtpass);
        Button btnLogin = (Button) findViewById(R.id.btnacessar);
        TextView btnReg = (TextView) findViewById(R.id.btnreg);

        //Abrindo Pipeline (conexão) SQLite
        dbhelper = new SQLiteDBHelper(this);
        db = dbhelper.getReadableDatabase();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = txtEmail.getText().toString();
                String senha = txtSenha.getText().toString();

                cursor = db.rawQuery("SELECT * FROM "+SQLiteDBHelper.TABELA_NOME +" WHERE "+SQLiteDBHelper.COLUMN_EMAIL+"=? AND "+SQLiteDBHelper.COLUMN_SENHA +"=?",new String[] {email,senha});
                if (cursor != null) {
                    if(cursor.getCount() > 0) {

                        cursor.moveToFirst();
                        //retornando nome do usuário e Email depois de login com sucesso e passando para LoginActivity
                        String _fname = cursor.getString(cursor.getColumnIndex(SQLiteDBHelper.COLUMN_NOME));
                        String _email= cursor.getString(cursor.getColumnIndex(SQLiteDBHelper.COLUMN_EMAIL));
                        Toast.makeText(MainActivity.this, "Login com Sucesso", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                        intent.putExtra("nome",_fname);
                        intent.putExtra("email",_email);
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
