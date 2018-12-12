package mx.edu.ittepic.tpdm_u5_practica3_juanmejia;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.Call;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText tel;
    Button llamar, permisos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText tel = findViewById(R.id.tel);
        llamar = findViewById(R.id.llamar);
        permisos = findViewById(R.id.permisos);

        solicitarPermiso();

        llamar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tlfno = tel.getText().toString();
                if(!TextUtils.isEmpty(tlfno)) {
                    String dial = "tel:" + tlfno;
                    startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
                    //Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(tlfno));
                    if(ActivityCompat.checkSelfPermission(MainActivity.this,
                            Manifest.permission.CALL_PHONE)!=PackageManager.PERMISSION_GRANTED)
                        return;
                }else {
                    Toast.makeText(MainActivity.this, "Enter a phone number", Toast.LENGTH_SHORT).show();
                }

            }
        });

        permisos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verPermisos();
            }
        });
    }//onCrate

    private void verPermisos() {
        String resultado="";
        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.READ_PHONE_STATE)
                ==PackageManager.PERMISSION_GRANTED){
            //ENTRA SI EL PERMISO ESTA DENEGADO YA QUE SERA DIERENTE A PERMISO OTORGADO
            resultado="SI PERMISO LECTURA ESTADO TELEFONO";
        }else{
            resultado="NO HAY PERMISO LECTURA ESTADO TELEFONO";
        }

        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.CALL_PHONE)
                ==PackageManager.PERMISSION_GRANTED){
            //ENTRA SI EL PERMISO ESTA DENEGADO YA QUE SERA DIERENTE A PERMISO OTORGADO
            resultado="SI PERMISO DE LLAMADAS";
        }else{
            resultado="NO HAY PERMISO DE LLAMADAS";
        }

        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        alerta.setTitle("ATENCION")
                .setMessage(resultado)
                .setPositiveButton("ACEPTAR",null)
                .show();
    }

    private void solicitarPermiso() {
        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.READ_PHONE_STATE)
                !=PackageManager.PERMISSION_GRANTED){
            //ENTRA SI EL PERMISO ESTA DENEGADO YA QUE SERA DIERENTE A PERMISO OTORGADO
            ActivityCompat.requestPermissions(this,new String []{Manifest.permission.READ_PHONE_STATE},1);
        }

        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.CALL_PHONE)
                !=PackageManager.PERMISSION_GRANTED){
            //ENTRA SI EL PERMISO ESTA DENEGADO YA QUE SERA DIERENTE A PERMISO OTORGADO
            ActivityCompat.requestPermissions(this,new String []{Manifest.permission.CALL_PHONE},1);
        }
    }

}//class
