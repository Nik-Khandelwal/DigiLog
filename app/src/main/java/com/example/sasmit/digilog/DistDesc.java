/** -------------------------------------------------------------------
 * Developed with Love for Smartlink Network Systems by :-            |
 *  BITS PILANI Interns(2018)                                         |
 *      Dev Arora                                                     |
 *      Nikhil Khandelwal                                             |
 *      Sasmit Mati                                                   |
 *      Shubham Mittal                                                |
 *      Shubham Raj                                                   |
 * --------------------------------------------------------------------
 */

package com.example.sasmit.digilog;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class DistDesc extends AppCompatActivity {

    EditText pincode;
    Button newprofile;
    Button existprofile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dist_desc);

        pincode=findViewById(R.id.pin);
        newprofile=findViewById(R.id.newprof);
        existprofile=findViewById(R.id.existprof);

        newprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pinc= pincode.getText().toString();

                if(pinc.equals("") || pinc.equals("000000"))
                {
                    AlertDialog alertDialog = new AlertDialog.Builder(
                            DistDesc.this).create();

                    // Setting Dialog Title
                    alertDialog.setTitle("No PIN Code");

                    // Setting Dialog Message
                    alertDialog.setMessage("Please Enter PIN Code");

                    alertDialog.setCancelable(true);

                    // Showing Alert Message
                    alertDialog.show();


                }
                else
                {
                    ProgressDialog dialog = ProgressDialog.show(DistDesc.this, "",
                            "Loading. Please wait...", true);

                    Intent intent= new Intent(view.getContext(),DistProfile.class);
                    intent.putExtra("pin", pinc);
                    view.getContext().startActivity(intent);

                    dialog.cancel();

                }

            }
        });

        existprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pinc= pincode.getText().toString();


                if(pinc.equals("000000") || pinc.equals(""))
                {
                    AlertDialog alertDialog = new AlertDialog.Builder(
                            DistDesc.this).create();

                    // Setting Dialog Title
                    alertDialog.setTitle("No PIN Code");

                    // Setting Dialog Message
                    alertDialog.setMessage("Please Enter PIN Code");

                    alertDialog.setCancelable(true);

                    // Showing Alert Message
                    alertDialog.show();

                }
                else
                {
                    ProgressDialog dialog = ProgressDialog.show(DistDesc.this, "",
                            "Loading. Please wait...", true);

                    Intent intent= new Intent(view.getContext(),Profs.class);
                    intent.putExtra("pin", pinc);
                    view.getContext().startActivity(intent);

                    dialog.cancel();
                }

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_options, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.home) {
            Intent intent=new Intent(this, MacroLog.class);
            this.startActivity(intent);
            return true;
        }

        if (id== R.id.distlist){
            Intent intent=new Intent(this, DistDesc.class);
            this.startActivity(intent);
            return true;
        }

        if (id== R.id.logout){
            Intent intent=new Intent(this, login.class);
            this.startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
