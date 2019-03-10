package com.example.test1.Activity;

import android.app.ProgressDialog;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewTreeObserver;
import android.widget.Toast;

import com.example.test1.Adapter.HomepageAdapter;
import com.example.test1.Database.ChatDBHelper;
import com.example.test1.Database.ChatDBUtility;
import com.example.test1.LoginActivity;
import com.example.test1.Model.DataResponse;
import com.example.test1.R;
import com.example.test1.Services.RequestApi;
import com.example.test1.Services.ServiceGenerator;
import com.example.test1.Utility.Utility;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomepageActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    HomepageAdapter defectsAdapter;

    ChatDBHelper chatDBHelper;
    ChatDBUtility chatDBUtility;

    ArrayList<DataResponse> dataResponse;
    private RecyclerView.LayoutManager layoutManager;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        Utility.ChangeStatusBarColor(HomepageActivity.this);
        chatDBUtility = new ChatDBUtility();
        chatDBHelper = chatDBUtility.CreateChatDB(HomepageActivity.this);
        Initializer();
        InitializeListener();
        GetValuesFromSharedPreferences();
        setData();



    }

    private void setAdapter() {
        layoutManager = new LinearLayoutManager(HomepageActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        defectsAdapter = new HomepageAdapter(HomepageActivity.this, dataResponse,1);
        recyclerView.setAdapter(defectsAdapter);
        recyclerView.setItemViewCacheSize(dataResponse.size());
    }

    private void setData() {


        progressDialog = new ProgressDialog(HomepageActivity.this);
        progressDialog.setMessage("loading");
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(true);
        progressDialog.show();
        if(Utility.CheckInterConnection(HomepageActivity.this))
        {
            GetData();
        }else
        {
            Toast.makeText(this, "Please Connect to internet", Toast.LENGTH_SHORT).show();
        }


    }

    private void GetValuesFromSharedPreferences() {

    }

    private void InitializeListener() {


        recyclerView.getViewTreeObserver()
                .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        //At this point the layout is complete and the
                        //dimensions of recyclerView and any child views are known.
                        //Remove listener after changed RecyclerView's height to prevent infinite loop
                            progressDialog.dismiss();

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            recyclerView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                            progressDialog.dismiss();
                        }
                    }
                });



    }

    private void Initializer() {

        recyclerView=(RecyclerView)findViewById(R.id.recycler_view);
    }




    private void GetData() {



        RequestApi api= ServiceGenerator.createService(RequestApi.class);

        Call<ArrayList<DataResponse>> call = api.data();

        call.enqueue(new Callback<ArrayList<DataResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<DataResponse>> call, Response<ArrayList<DataResponse>> response) {
                ArrayList<DataResponse> data = response.body();

                if(data.size()>0) {


                    dataResponse = new ArrayList<DataResponse>();
                    dataResponse = data;

                    ArrayList<DataResponse> dataResponseSize = new ArrayList<DataResponse>();
                    dataResponseSize = chatDBUtility.GetDataList(chatDBHelper);


                    if (dataResponseSize.size() == 0) {
                        for (int i = 0; i < dataResponse.size(); i++) {
                            chatDBUtility.AddToDataListDB(chatDBHelper, dataResponse.get(i));
                        }
                    } else {
                        chatDBUtility.DeleteFromData(chatDBHelper);
                        for (int i = 0; i < dataResponse.size(); i++) {
                            chatDBUtility.AddToDataListDB(chatDBHelper, dataResponse.get(i));
                        }

                    }


                    setAdapter();

                }
            }

            @Override
            public void onFailure(Call<ArrayList<DataResponse>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
