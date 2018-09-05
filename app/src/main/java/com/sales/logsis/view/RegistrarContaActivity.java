package com.sales.logsis.view;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.sales.logsis.Model.dao.SQLiteDBHelper;
import com.sales.logsis.R;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;


public class RegistrarContaActivity extends AppCompatActivity {

    SQLiteOpenHelper openHelper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_conta);

        //Oultar o AppBar na tela cheia
        ActionBar ab = getSupportActionBar();
        ab.hide();

        openHelper = new SQLiteDBHelper(this);

        //Referenciando os widgets EditText e Botão inserido dentro do arquivo layout (xml)
        final EditText txtNome = (EditText) findViewById(R.id.txtnome_reg);
        final EditText txtEmail = (EditText) findViewById(R.id.txtemail_reg);
        final EditText txtSenha = (EditText) findViewById(R.id.txtsenha_reg);
        final EditText txtFone = (EditText) findViewById(R.id.txtfone_reg);
        Button _btnreg = (Button) findViewById(R.id.btn_reg);

        //Registrando o evento do clique do botão
        _btnreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                db = openHelper.getWritableDatabase();

                String nomeCompleto = txtNome.getText().toString();
                String email = txtEmail.getText().toString();
                String senha = txtSenha.getText().toString();
                String fone = txtFone.getText().toString();

                //Chamando metodo InserirDados - Definido abaixo
                InserirDados(nomeCompleto, email, senha, fone);

                //Diálogo de Alerta depois de clicar no Registrar Conta
                final AlertDialog.Builder builder = new AlertDialog.Builder(RegistrarContaActivity.this);
                builder.setTitle("Informação");
                builder.setMessage("Sua Conta foi criada com sucesso.");
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        //Finalizando o diálogo e removendo o Activity da pilha
                        dialogInterface.dismiss();
                        finish();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });

    }

    //Inserindo dados na Database
    public void InserirDados(String nomeCompleto, String email, String senha, String fone ) {

        ContentValues values = new ContentValues();
        values.put(SQLiteDBHelper.COLUMN_NOME,nomeCompleto);
        values.put(SQLiteDBHelper.COLUMN_EMAIL,email);
        values.put(SQLiteDBHelper.COLUMN_SENHA,senha);
        values.put(SQLiteDBHelper.COLUMN_TELEFONE,fone);
        long id = db.insert(SQLiteDBHelper.TABELA_NOME,null,values);
    }
}