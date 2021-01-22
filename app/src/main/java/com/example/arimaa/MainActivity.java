package com.example.arimaa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Button resetBtn, finishMove, pullButton, undoMove;
    private Context context;
    Canvas canvas = new Canvas();
    CustomView customView;
    TextView currentPlayerTV, errorMsgSet,p2CountTV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        customView = findViewById(R.id.cv);
        currentPlayerTV =  findViewById(R.id.currentPlayerTV);
        customView.setText(currentPlayerTV);
        errorMsgSet =  findViewById(R.id.error_msg);
        customView.setTextError(errorMsgSet);


        pullButton = findViewById(R.id.pull);
        resetBtn = findViewById(R.id.resetBtn);
        finishMove = findViewById(R.id.finishMove);
        undoMove = findViewById(R.id.undo);
        undoMove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customView.undoMove();
            }
        });
        pullButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customView.pullPlayer();
            }
        });
        finishMove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customView.changePlayerTurn();
            }
        });
        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customView.init();
                customView.invalidate();
            }
        });
    }
}