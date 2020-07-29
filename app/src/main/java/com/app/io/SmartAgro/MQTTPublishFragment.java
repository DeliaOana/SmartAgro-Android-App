package com.app.io.SmartAgro;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
//import android.support.design.widget.FloatingActionButton;
//import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;


import androidx.fragment.app.Fragment;

import com.patterns.io.mqttpatterns.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class MQTTPublishFragment extends Fragment {

    public String                       topicToPublish;
    public String                       textToPublish;
    public String                       qos;
    public String                       valoareNivel = "5";


    public Button                       fabBack;
    public Button                       fabSave;

    public Switch                       swRpTemp;
    public Switch                       swRpUmid;
    public Switch                       swRpUmidSol;
    public Switch                       swRpGaz;

    public Switch                       swESPTemp;
    public Switch                       swESPUmid;
    public Switch                       swESPUmidSol;
    public Switch                       swESPGaz;

    public Switch                       swLibTemp;
    public Switch                       swLibUmid;
    public Switch                       swLibUmidSol;
    public Switch                       swLibGaz;

    public Switch                       swLopiTemp;
    public Switch                       swLopiUmid;
    public Switch                       swLopiUmidSol;
    public Switch                       swLopiGaz;

    public EditText                     valNivel;

    public PublishDataPassListener      mCallback;

    public MQTTPublishFragment() {
        // Required empty public constructor
    }

    // Interface of the functions from the parent Activity that this Fragment will call
    public interface PublishDataPassListener{
        void launchSubscribeFragment(String data);
        void launchConnectFragment(String data);
        void publishMQTTmessage(String messageParams[]);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Activity activity;

        // An Activity is needed to create the interface callback, so it is cast from the context
        // This is due to the onAttach method with Activity instead of context has ben deprecated
        if (context instanceof Activity){
            activity=(Activity) context;

            // This makes sure that the container activity has implemented
            // the callback interface. If not, it throws an exception
            try {
                mCallback = (PublishDataPassListener) activity;

            } catch (ClassCastException e) {
                throw new ClassCastException(activity.toString()
                        + " must implement PublishDataPassListener");
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        // Add this line in order for this fragment to handle menu events.

        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        // Inflate the menu; this adds items to the action bar if it is present.
        //inflater.inflate(R.menu.devicefragment, menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        if (id == R.id.action_about) {

            // Create a dialog that pups up with information about the application
            final Dialog dialog = new Dialog(getActivity());

            dialog.setContentView(R.layout.about_layout);
            dialog.setTitle(R.string.aboutpatterns);

            Button btnCancel = (Button) dialog.findViewById(R.id.dismiss);
            dialog.show();

            btnCancel.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_sensors_settings, container, false);

        // Initialise all necessary Views, their values and onClickListener's
        fabBack  = (Button) rootView.findViewById(R.id.fabBack);
        fabSave  = (Button) rootView.findViewById(R.id.fabSave);

        swRpTemp = (Switch) rootView.findViewById(R.id.swRpTemp);
        swRpUmid = (Switch) rootView.findViewById(R.id.swRpUmid);
        swRpUmidSol = (Switch) rootView.findViewById(R.id.swRpUmidSol);
        swRpGaz = (Switch) rootView.findViewById(R.id.swRpGaz);

        swESPTemp = (Switch) rootView.findViewById(R.id.swESPTemp);
        swESPUmid = (Switch) rootView.findViewById(R.id.swESPUmid);
        swESPUmidSol = (Switch) rootView.findViewById(R.id.swESPUmidSol);
        swESPGaz = (Switch) rootView.findViewById(R.id.swESPGaz);

        swLibTemp = (Switch) rootView.findViewById(R.id.swLibTemp);
        swLibUmid = (Switch) rootView.findViewById(R.id.swLibUmid);
        swLibUmidSol = (Switch) rootView.findViewById(R.id.swLibUmidSol);
        swLibGaz = (Switch) rootView.findViewById(R.id.swLibGaz);

        swLopiTemp = (Switch) rootView.findViewById(R.id.swLopiTemp);
        swLopiUmid = (Switch) rootView.findViewById(R.id.swLopiUmid);
        swLopiUmidSol = (Switch) rootView.findViewById(R.id.swLopiUmidSol);
        swLopiGaz = (Switch) rootView.findViewById(R.id.swLopiGaz);

        valNivel      = (EditText)             rootView.findViewById(R.id.editTextNivel);

        fabBack  .setOnClickListener(onClickListenerMQTT);
        fabSave .setOnClickListener(onClickListenerMQTT);

        return rootView;
    }

    /*
    This is called when landing here from another fragment (through the parent Activity)
    Therefore, the values are extracted of the arguments that have been passed onto here
    to have consistency in the UI values and update them as needed
    */
    /*////////////////////////////////////////////
    @Override
    public void onStart(){

        super.onStart();
        Bundle args = getArguments();
        if (args != null) {

            textPublishTopic.setText(args.getString("topic"));
            textPublish     .setText(args.getString("text"));
            textPublishQoS  .setText(Integer.toString(args.getInt("qos")));
        }
    }
*////////////////////////////////////////
    // onClickListener for all Views. The action if filtered by the name of the View
    private OnClickListener onClickListenerMQTT = new OnClickListener() {
        @Override
        public void onClick(final View v) {

            switch(v.getId()){

                case R.id.fabSave:

                    textToPublish = "";
                    topicToPublish = "smartagro/setari";
                    qos = "0";

                    valoareNivel = valNivel .getText().toString();
                    textToPublish = textToPublish + valoareNivel + " ";

                    if (swRpTemp.isChecked())
                        textToPublish = textToPublish + "temperatura-RPi=1 ";
                    else
                        textToPublish = textToPublish + "temperatura-RPi=0 ";
                    if (swRpUmid.isChecked())
                        textToPublish = textToPublish + "Umiditate-RPi=1 ";
                    else
                        textToPublish = textToPublish + "Umiditate-RPi=0 ";
                    if (swRpUmidSol.isChecked())
                        textToPublish = textToPublish + "UmiditateSol-RPi=1 ";
                    else
                        textToPublish = textToPublish + "UmiditateSol-RPi=0 ";
                    if (swRpGaz.isChecked())
                        textToPublish = textToPublish + "Gaz-RPi=1 ";
                    else
                        textToPublish = textToPublish + "Gaz-RPi=0 ";

                    if (swESPTemp.isChecked())
                        textToPublish = textToPublish + "temperatura-ESP=1 ";
                    else
                        textToPublish = textToPublish + "temperatura-ESP=0 ";
                    if (swESPUmid.isChecked())
                        textToPublish = textToPublish + "Umiditate-ESP=1 ";
                    else
                        textToPublish = textToPublish + "Umiditate-ESP=0 ";
                    if (swESPUmidSol.isChecked())
                        textToPublish = textToPublish + "UmiditateSol-ESP=1 ";
                    else
                        textToPublish = textToPublish + "UmiditateSol-ESP=0 ";
                    if (swESPGaz.isChecked())
                        textToPublish = textToPublish + "Gaz-ESP=1 ";
                    else
                        textToPublish = textToPublish + "Gaz-ESP=0 ";

                    if (swLibTemp.isChecked())
                        textToPublish = textToPublish + "temperatura-Libelium=1 ";
                    else
                        textToPublish = textToPublish + "temperatura-Libelium=0 ";
                    if (swLibUmid.isChecked())
                        textToPublish = textToPublish + "Umiditate-Libelium=1 ";
                    else
                        textToPublish = textToPublish + "Umiditate-Libelium=0 ";
                    if (swLibUmidSol.isChecked())
                        textToPublish = textToPublish + "UmiditateSol-Libelium=1 ";
                    else
                        textToPublish = textToPublish + "UmiditateSol-Libelium=0 ";
                    if (swLibGaz.isChecked())
                        textToPublish = textToPublish + "Gaz-Libelium=1 ";
                    else
                        textToPublish = textToPublish + "Gaz-Libelium=0 ";

                    if (swLopiTemp.isChecked())
                        textToPublish = textToPublish + "temperatura-Lopi=1 ";
                    else
                        textToPublish = textToPublish + "temperatura-Lopi=0 ";
                    if (swLopiUmid.isChecked())
                        textToPublish = textToPublish + "Umiditate-Lopi=1 ";
                    else
                        textToPublish = textToPublish + "Umiditate-Lopi=0 ";
                    if (swLopiUmidSol.isChecked())
                        textToPublish = textToPublish + "UmiditateSol-Lopi=1 ";
                    else
                        textToPublish = textToPublish + "UmiditateSol-Lopi=0 ";
                    if (swLopiGaz.isChecked())
                        textToPublish = textToPublish + "Gaz-Lopi=1 ";
                    else
                        textToPublish = textToPublish + "Gaz-Lopi=0 ";


                    // Bundle the parameters, and call the parent Activity method to start the connection
                    String connectParams[] = {"publish", textToPublish,topicToPublish,qos};
                    mCallback.publishMQTTmessage(connectParams);

                    break;

                case R.id.fabBack:
                    mCallback.launchSubscribeFragment("");;
                    break;
/*
                case R.id.fabSubscribe:
                    // Change to the Subscribe fragment, through the parent Activity interface
                    mCallback.launchSubscribeFragment("");
                    break;

 */
            }
        }
    };

}
