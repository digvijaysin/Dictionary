package com.example.myapplication.imoxford;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Path;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.provider.MediaStore;
import android.speech.RecognizerIntent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.imoxford.FriendClasses.Constants;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.JsonObject;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.*;
import java.io.File.*;
import java.security.cert.CertPathBuilderSpi;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class VoiceRecogniser extends AppCompatActivity {
    public ArrayList<String> hindiList;
    public ArrayList<String> englishList;
    public static TextView txtSpeechInput;
    private ImageView btnSpeak;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    public int count=0;
    InputStream inputStream;
    EditText TestingText;
    Button submitButton, ResetButton, buttonPlayLastRecordAudio,
            buttonStopPlayingRecording;
    String AudioSavePathInDevice = null;
    AlertDialog alertDialog;
    AlertDialog alertDialog1;
    MediaRecorder mediaRecorder;
    Random random;
    String RandomAudioFileName = "ABCDEFGHIJKLMNOP";
    public static final int RequestPermissionCode = 1;
    MediaPlayer mediaPlayer;
    AlertDialog.Builder alertDialogBuilder;
    private TextView accuracyMeter;
    private TextView txtSpeechAnswer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_recogniser);
        txtSpeechInput = (TextView) findViewById(R.id.txtSpeechInput);
        accuracyMeter = (TextView) findViewById(R.id.accuracy_meter);
        buttonPlayLastRecordAudio = (Button) findViewById(R.id.button3);
        buttonStopPlayingRecording = (Button) findViewById(R.id.button4);
        submitButton=(Button)findViewById(R.id.submit);
        ResetButton=(Button)findViewById(R.id.reset);
        txtSpeechAnswer= (TextView) findViewById(R.id.txtSpeechAnswer);

         alertDialogBuilder = new AlertDialog.Builder(this);
         getDataFromAssets();
         generateWordToDisplay();

        TestingText=(EditText)findViewById(R.id.testing_code);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSubmitButton(TestingText.getText().toString());
            }
        });
        ResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TestingText.setText("");
                getDataFromAssets();
                generateWordToDisplay();
            }
        });
        btnSpeak = (ImageView) findViewById(R.id.btnSpeak);



        btnSpeak.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                promptSpeechInput();
            }
        });

        random = new Random();


        buttonPlayLastRecordAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) throws IllegalArgumentException,
                    SecurityException, IllegalStateException {
                JSONObject Configfile=new JSONObject();
                JSONObject uri=new JSONObject();
                JSONObject merge=new JSONObject();
                InputStream in = getResources().openRawResource(R.raw.sync_request);
                try {
                    Configfile.put("encoding","FLAC");
                    Configfile.put("sampleRateHertz",16000);
                    Configfile.put("languageCode","en-US");
                    Configfile.put("enableWordTimeOffsets",false);
                    uri.put("uri","gs://cloud-samples-tests/speech/brooklyn.flac");
                    merge.put("config",Configfile);
                    merge.put("audio",uri);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Call<GetTextResponse> getText= VoiceRecogniserClass.getRetrofitObject(0).getText(in.toString());
                getText.enqueue(new Callback<GetTextResponse>() {
                    @Override
                    public void onResponse(Call<GetTextResponse> call, Response<GetTextResponse> response) {
                     if(response.code()==200) {
                         Log.d("digvijay",""+response.code());
                         GetTextResponse gettextResponse;
                         List<GetResult> result = new ArrayList<>();
                         gettextResponse = response.body();
                         Log.d("digvijay", gettextResponse.toString());
                         result = gettextResponse.getList();
                         List<GetResult.GetText> getText = new ArrayList<GetResult.GetText>();
                         getText = result.get(0).getGetText();
                         txtSpeechInput.setText(getText.get(0).getText());
                     }
                     else if(response.code()==400)
                     {
                         GetTextResponse getTextResponse=response.body();
                         Log.d("digvijay",""+response.body());
                         Log.d("digvijay",""+response.code());
                     }
                     else
                     {
                         Log.d("digvijay",""+response.code());
                     }
                    }

                    @Override
                    public void onFailure(Call<GetTextResponse> call, Throwable t) {

                        Log.d("digvijay",t.toString());

                    }
                });
                try {
                    BufferedReader br = new BufferedReader(new FileReader(AudioSavePathInDevice));
                    br.close();

       //             recogniser(AudioSavePathInDevice);
                } catch (IOException e) {
                    e.printStackTrace();
                }


                Toast.makeText(VoiceRecogniser.this, "Recording Playing",
                        Toast.LENGTH_LONG).show();
            }
        });

        buttonStopPlayingRecording.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonStopPlayingRecording.setEnabled(false);
                buttonPlayLastRecordAudio.setEnabled(true);

                if (mediaPlayer != null) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    MediaRecorderReady();
                }
            }
        });


    }

    private void generateWordToDisplay() {
        Random random=new Random();
        int i = Math.abs(random.nextInt()%12);
        count=i;
        Log.d("Harshit",i+" +value of i");
        txtSpeechInput.setText(hindiList.get(i));

    }

    private void checkAnswer(String test) {
        String answer=englishList.get(count).toLowerCase();
        test=test.toLowerCase();
        String[] answerWords = answer.split(" ");
        Log.d("Harshit",answerWords.toString());
        String[] testWords = test.split(" ");
        Log.d("Harshit",testWords.toString());
        int min=answerWords.length>testWords.length?testWords.length:answerWords.length;
        int max=answerWords.length>testWords.length?answerWords.length:testWords.length;
        int diff=max-min;
        int result=0;
        for(int i=0;i<min;i++){
            Log.d("Harshit",testWords[i]+"    "+ answerWords[i]);
            if(answerWords[i].equals(testWords[i])){
                result++;
            }
        }
        result=(100*result)/answerWords.length;
        accuracyMeter.setText(""+result+"");
        txtSpeechAnswer.setText(answer);
        Toast.makeText(this,"Accuracy updated",Toast.LENGTH_SHORT).show();
    }

    private int getDataFromAssets() {
        AssetManager am = this.getAssets();
        try {
            InputStream isHindi = am.open("spokenEnglishTrainerHindi.txt");
            InputStream isEnglish = am.open("spokenEnglishTrainer.txt");
            BufferedReader brHindi = new BufferedReader(new InputStreamReader(isHindi));
            BufferedReader brEnglish = new BufferedReader(new InputStreamReader(isEnglish));
            hindiList = new ArrayList<String>();
            englishList = new ArrayList<String>();
            HashMap<String, String> hashMap = new HashMap<String, String>();
            int t=Integer.parseInt(brHindi.readLine());
            count=t-1;
            while(t--!=0) {
                hindiList.add(brHindi.readLine());
                englishList.add(brEnglish.readLine());
            }
            brHindi.close();
            brEnglish.close();
       } catch (IOException e) {
            Log.d("Harshit",e.toString());
        }
        return count;
        }

    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Speak Something.....");
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    "Audio is not supported",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void onSubmitButton(String TextForText)
    {
        if(true) {
        checkAnswer(TextForText);
    }
    else
    {
        Call<GeTextAccuracy> getText= VoiceRecogniserClass.getRetrofitObject(1).getTextAccuracy(TextForText,"DEMO_KEY");
        getText.enqueue(new Callback<GeTextAccuracy>() {
            @Override
            public void onResponse(Call<GeTextAccuracy> call, Response<GeTextAccuracy> response) {
                GeTextAccuracy geTextAccuracy=response.body();
                alertDialog.dismiss();
                alertDialogBuilder.setMessage("Congratulation! Your Accuracy="+geTextAccuracy.getScore()+"%");
                alertDialog=alertDialogBuilder.create();
                alertDialog.show();
            }

            @Override
            public void onFailure(Call<GeTextAccuracy> call, Throwable t) {

            }
        });
}
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    TestingText.setText(result.get(0));
                }
                break;
            }

        }
    }



    public void MediaRecorderReady() {
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        mediaRecorder.setOutputFile(AudioSavePathInDevice);
    }

    public String CreateRandomAudioFileName(int string) {
        StringBuilder stringBuilder = new StringBuilder(string);
        int i = 0;
        while (i < string) {
            stringBuilder.append(RandomAudioFileName.
                    charAt(random.nextInt(RandomAudioFileName.length())));

            i++;
        }
        return stringBuilder.toString();
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(VoiceRecogniser.this, new
                String[]{WRITE_EXTERNAL_STORAGE, RECORD_AUDIO}, RequestPermissionCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case RequestPermissionCode:
                if (grantResults.length > 0) {
                    boolean StoragePermission = grantResults[0] ==
                            PackageManager.PERMISSION_GRANTED;
                    boolean RecordPermission = grantResults[1] ==
                            PackageManager.PERMISSION_GRANTED;

                    if (StoragePermission && RecordPermission) {
                        Toast.makeText(VoiceRecogniser.this, "Permission Granted",
                                Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(VoiceRecogniser.this, "Permission Denied", Toast.LENGTH_LONG).show();
                    }
                }
                break;
        }

    }
    public boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(),
                WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(),
                RECORD_AUDIO);
        return result == PackageManager.PERMISSION_GRANTED &&
                result1 == PackageManager.PERMISSION_GRANTED;
    }

}