package com.example.arimaa;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class CustomView extends View {
    public float individualSquareBoxSize;
    public int viewWidth;
    Canvas canvas_store;
    public int stepsTaken1;
    public int stepsTaken2;
    public int playerTurn;
    ArrayList<Rect> squares;
    ArrayList<Rect> TrapSquares;
    Paint highlightPaint, immobilePaint;
    Rect rectForPulledAnimal;
    TextView currentTV;
    Button pullPlayer;
    TextView errorMsg = findViewById(R.id.error_msg);
    Paint rectToMovePaint;
    Rect rect_temp;
    Animals animal_to_be_pushed;
    private boolean touches[];
    private boolean gameOver;
    private boolean pullingButtonPressed;
    private float touchx[];
    private float touchy[];
    private boolean touching;
    private Rect touchRect;
    private Paint square1Color, player2Color, player1Color, square2Colour;
    private boolean animalHighligted;
    ArrayList<Rect> posibleRects;
    ArrayList<Rect> posibleRectsForPushing;
    ArrayList<Rect> winningRectsof1;
    ArrayList<Rect> winningRectsof2;
    ArrayList<Animals> animals1;
    ArrayList<Animals> pullableAnimals;
    HashMap<Integer, Rect> undoHistory1 = new HashMap<Integer, Rect>();
    HashMap<Integer, Rect> undoHistory2 = new HashMap<Integer, Rect>();
    ArrayList<ArrayList<Animals>> animals1_borad_history;
    ArrayList<Animals> animals2;
    ArrayList<ArrayList<Animals>> animals2_borad_history;

    public CustomView(Context c) {
        super(c);
        init();
    }

    public CustomView(Context c, AttributeSet as) {
        super(c, as);
        init();
    }

    public CustomView(Context c, AttributeSet as, int default_style) {
        super(c, as, default_style);
        init();
    }

    public void init() {
        animalHighligted = false;
        gameOver = false;
        canvas_store = null;
        pullingButtonPressed = false;
        squares = new ArrayList<>();
        posibleRectsForPushing = new ArrayList<>();
        pullableAnimals = new ArrayList<>();
        TrapSquares = new ArrayList<>();
        posibleRects = new ArrayList<>();
        winningRectsof1 = new ArrayList<>();
        winningRectsof2 = new ArrayList<>();
        touches = new boolean[16];
        touchx = new float[16];
        touchy = new float[16];
        touchRect = new Rect();
        animals1 = new ArrayList<>();
        animals1_borad_history = new ArrayList<>();
        animals2 = new ArrayList<>();
        animals2_borad_history = new ArrayList<>();
        playerTurn = 1;
        stepsTaken1 = 0;
        stepsTaken2 = 0;
        rect_temp = new Rect();
        highlightPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        highlightPaint.setColor(0xff444444);
        highlightPaint.setAlpha(170);
        immobilePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        immobilePaint.setColor(0xFF8E00C7);
        immobilePaint.setAlpha(170);
        rectToMovePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        rectToMovePaint.setColor(0xFF04E10D);
        rectToMovePaint.setAlpha(170);
        square1Color = new Paint(Paint.ANTI_ALIAS_FLAG);
        player2Color = new Paint(Paint.ANTI_ALIAS_FLAG);
        player1Color = new Paint(Paint.ANTI_ALIAS_FLAG);
        square2Colour = new Paint(Paint.ANTI_ALIAS_FLAG);
        square1Color.setColor(0xFFD2D2D3);
        player2Color.setColor(0xFF00FF00);
        player1Color.setColor(0xFFFF0000);
        square2Colour.setColor(0xFF777777);
    }

    private void createPlayers() {
        int topWhite = 0;
        int leftWhite = 0;
        int topRed = (int) (individualSquareBoxSize * 7);
        int leftRed = 0;

        Bitmap rabitWhiteImage = BitmapFactory.decodeResource(getResources(), R.drawable.rabit);
        Bitmap rabitRedImage = BitmapFactory.decodeResource(getResources(), R.drawable.rabitred);


        Bitmap catWhiteImage = BitmapFactory.decodeResource(getResources(), R.drawable.cat);
        Bitmap catRedImage = BitmapFactory.decodeResource(getResources(), R.drawable.catred);

        Bitmap camelWhiteImage = BitmapFactory.decodeResource(getResources(), R.drawable.camel);
        Bitmap camelRedImage = BitmapFactory.decodeResource(getResources(), R.drawable.camelred);

        Bitmap dogWhiteImage = BitmapFactory.decodeResource(getResources(), R.drawable.dog);
        Bitmap dogRedImage = BitmapFactory.decodeResource(getResources(), R.drawable.dogred);

        Bitmap horseWhiteImage = BitmapFactory.decodeResource(getResources(), R.drawable.horse);
        Bitmap horseRedImage = BitmapFactory.decodeResource(getResources(), R.drawable.horsered);

        Bitmap elephantWhiteImage = BitmapFactory.decodeResource(getResources(), R.drawable.elephant);
        Bitmap elephantRedImage = BitmapFactory.decodeResource(getResources(), R.drawable.elephantred);

        rabitWhiteImage = resizeBitmapImage(rabitWhiteImage);
        rabitRedImage = resizeBitmapImage(rabitRedImage);
        camelWhiteImage = resizeBitmapImage(camelWhiteImage);
        camelRedImage = resizeBitmapImage(camelRedImage);
        dogWhiteImage = resizeBitmapImage(dogWhiteImage);
        dogRedImage = resizeBitmapImage(dogRedImage);
        elephantWhiteImage = resizeBitmapImage(elephantWhiteImage);
        elephantRedImage = resizeBitmapImage(elephantRedImage);
        horseWhiteImage = resizeBitmapImage(horseWhiteImage);
        horseRedImage = resizeBitmapImage(horseRedImage);
        catWhiteImage = resizeBitmapImage(catWhiteImage);
        catRedImage = resizeBitmapImage(catRedImage);

        rect_temp = new Rect(leftWhite, topWhite, leftWhite + (int) individualSquareBoxSize, topWhite + (int) individualSquareBoxSize);
        Animals rabitWhite1 = new Animals(1, rabitWhiteImage, "white", "rabit", 1, leftWhite, topWhite, rect_temp, 6);
        leftWhite += individualSquareBoxSize;
        rect_temp = new Rect(leftWhite, topWhite, leftWhite + (int) individualSquareBoxSize, topWhite + (int) individualSquareBoxSize);
        Animals rabitWhite2 = new Animals(2, rabitWhiteImage, "white", "rabit", 1, leftWhite, topWhite, rect_temp, 6);
        leftWhite += individualSquareBoxSize;
        rect_temp = new Rect(leftWhite, topWhite, leftWhite + (int) individualSquareBoxSize, topWhite + (int) individualSquareBoxSize);
        Animals rabitWhite3 = new Animals(3, rabitWhiteImage, "white", "rabit", 1, leftWhite, topWhite, rect_temp, 6);
        leftWhite += individualSquareBoxSize;
        rect_temp = new Rect(leftWhite, topWhite, leftWhite + (int) individualSquareBoxSize, topWhite + (int) individualSquareBoxSize);
        Animals rabitWhite4 = new Animals(4, rabitWhiteImage, "white", "rabit", 1, leftWhite, topWhite, rect_temp, 6);
        leftWhite += individualSquareBoxSize;
        rect_temp = new Rect(leftWhite, topWhite, leftWhite + (int) individualSquareBoxSize, topWhite + (int) individualSquareBoxSize);
        Animals rabitWhite5 = new Animals(5, rabitWhiteImage, "white", "rabit", 1, leftWhite, topWhite, rect_temp, 6);
        leftWhite += individualSquareBoxSize;
        rect_temp = new Rect(leftWhite, topWhite, leftWhite + (int) individualSquareBoxSize, topWhite + (int) individualSquareBoxSize);
        Animals rabitWhite6 = new Animals(6, rabitWhiteImage, "white", "rabit", 1, leftWhite, topWhite, rect_temp, 6);
        leftWhite += individualSquareBoxSize;
        rect_temp = new Rect(leftWhite, topWhite, leftWhite + (int) individualSquareBoxSize, topWhite + (int) individualSquareBoxSize);
        Animals rabitWhite7 = new Animals(7, rabitWhiteImage, "white", "rabit", 1, leftWhite, topWhite, rect_temp, 6);
        leftWhite += individualSquareBoxSize;
        rect_temp = new Rect(leftWhite, topWhite, leftWhite + (int) individualSquareBoxSize, topWhite + (int) individualSquareBoxSize);
        Animals rabitWhite8 = new Animals(8, rabitWhiteImage, "white", "rabit", 1, leftWhite, topWhite, rect_temp, 6);
        animals1.add(rabitWhite1);
        animals1.add(rabitWhite2);
        animals1.add(rabitWhite3);
        animals1.add(rabitWhite4);
        animals1.add(rabitWhite5);
        animals1.add(rabitWhite6);
        animals1.add(rabitWhite7);
        animals1.add(rabitWhite8);
        leftRed = 0;
        rect_temp = new Rect(leftRed, topRed, leftRed + (int) individualSquareBoxSize, topRed + (int) individualSquareBoxSize);
        Animals rabitRed1 = new Animals(1, rabitRedImage, "red", "rabit", 2, leftRed, topRed, rect_temp, 6);
        leftRed += individualSquareBoxSize;
        rect_temp = new Rect(leftRed, topRed, leftRed + (int) individualSquareBoxSize, topRed + (int) individualSquareBoxSize);
        Animals rabitRed2 = new Animals(2, rabitRedImage, "red", "rabit", 2, leftRed, topRed, rect_temp, 6);
        leftRed += individualSquareBoxSize;
        rect_temp = new Rect(leftRed, topRed, leftRed + (int) individualSquareBoxSize, topRed + (int) individualSquareBoxSize);
        Animals rabitRed3 = new Animals(3, rabitRedImage, "red", "rabit", 2, leftRed, topRed, rect_temp, 6);
        leftRed += individualSquareBoxSize;
        rect_temp = new Rect(leftRed, topRed, leftRed + (int) individualSquareBoxSize, topRed + (int) individualSquareBoxSize);
        Animals rabitRed4 = new Animals(4, rabitRedImage, "red", "rabit", 2, leftRed, topRed, rect_temp, 6);
        leftRed += individualSquareBoxSize;
        rect_temp = new Rect(leftRed, topRed, leftRed + (int) individualSquareBoxSize, topRed + (int) individualSquareBoxSize);
        Animals rabitRed5 = new Animals(5, rabitRedImage, "red", "rabit", 2, leftRed, topRed, rect_temp, 6);
        leftRed += individualSquareBoxSize;
        rect_temp = new Rect(leftRed, topRed, leftRed + (int) individualSquareBoxSize, topRed + (int) individualSquareBoxSize);
        Animals rabitRed6 = new Animals(6, rabitRedImage, "red", "rabit", 2, leftRed, topRed, rect_temp, 6);
        leftRed += individualSquareBoxSize;
        rect_temp = new Rect(leftRed, topRed, leftRed + (int) individualSquareBoxSize, topRed + (int) individualSquareBoxSize);
        Animals rabitRed7 = new Animals(7, rabitRedImage, "red", "rabit", 2, leftRed, topRed, rect_temp, 6);
        leftRed += individualSquareBoxSize;
        rect_temp = new Rect(leftRed, topRed, leftRed + (int) individualSquareBoxSize, topRed + (int) individualSquareBoxSize);
        Animals rabitRed8 = new Animals(8, rabitRedImage, "red", "rabit", 2, leftRed, topRed, rect_temp, 6);
        animals2.add(rabitRed1);
        animals2.add(rabitRed2);
        animals2.add(rabitRed3);
        animals2.add(rabitRed4);
        animals2.add(rabitRed5);
        animals2.add(rabitRed6);
        animals2.add(rabitRed7);
        animals2.add(rabitRed8);

        leftRed = 0;
        topRed -= individualSquareBoxSize;
        rect_temp = new Rect(leftRed, topRed, leftRed + (int) individualSquareBoxSize, topRed + (int) individualSquareBoxSize);
        Animals catRed1 = new Animals(9, catRedImage, "red", "cat", 2, leftRed, topRed, rect_temp, 5);
        leftRed += individualSquareBoxSize;
        rect_temp = new Rect(leftRed, topRed, leftRed + (int) individualSquareBoxSize, topRed + (int) individualSquareBoxSize);
        Animals dogRed1 = new Animals(10, dogRedImage, "red", "dog", 2, leftRed, topRed, rect_temp, 4);
        leftRed += individualSquareBoxSize;
        rect_temp = new Rect(leftRed, topRed, leftRed + (int) individualSquareBoxSize, topRed + (int) individualSquareBoxSize);
        Animals horseRed1 = new Animals(11, horseRedImage, "red", "horse", 2, leftRed, topRed, rect_temp, 3);
        leftRed += individualSquareBoxSize;
        rect_temp = new Rect(leftRed, topRed, leftRed + (int) individualSquareBoxSize, topRed + (int) individualSquareBoxSize);
        Animals camelRed1 = new Animals(12, camelRedImage, "red", "camel", 2, leftRed, topRed, rect_temp, 2);
        leftRed += individualSquareBoxSize;
        rect_temp = new Rect(leftRed, topRed, leftRed + (int) individualSquareBoxSize, topRed + (int) individualSquareBoxSize);
        Animals elephantRed1 = new Animals(13, elephantRedImage, "red", "elephant", 2, leftRed, topRed, rect_temp, 1);
        leftRed += individualSquareBoxSize;
        rect_temp = new Rect(leftRed, topRed, leftRed + (int) individualSquareBoxSize, topRed + (int) individualSquareBoxSize);
        Animals horseRed2 = new Animals(14, horseRedImage, "red", "horse", 2, leftRed, topRed, rect_temp, 3);
        leftRed += individualSquareBoxSize;
        rect_temp = new Rect(leftRed, topRed, leftRed + (int) individualSquareBoxSize, topRed + (int) individualSquareBoxSize);
        Animals dogRed2 = new Animals(15, dogRedImage, "red", "dog", 2, leftRed, topRed, rect_temp, 4);
        leftRed += individualSquareBoxSize;
        rect_temp = new Rect(leftRed, topRed, leftRed + (int) individualSquareBoxSize, topRed + (int) individualSquareBoxSize);
        Animals catRed2 = new Animals(16, catRedImage, "red", "cat", 2, leftRed, topRed, rect_temp, 5);

        leftWhite = 0;
        topWhite += individualSquareBoxSize;
        rect_temp = new Rect(leftWhite, topWhite, leftWhite + (int) individualSquareBoxSize, topWhite + (int) individualSquareBoxSize);
        Animals catWhite1 = new Animals(9, catWhiteImage, "white", "cat", 1, leftWhite, topWhite, rect_temp, 5);
        leftWhite += individualSquareBoxSize;
        rect_temp = new Rect(leftWhite, topWhite, leftWhite + (int) individualSquareBoxSize, topWhite + (int) individualSquareBoxSize);
        Animals dogWhite1 = new Animals(10, dogWhiteImage, "white", "dog", 1, leftWhite, topWhite, rect_temp, 4);
        leftWhite += individualSquareBoxSize;
        rect_temp = new Rect(leftWhite, topWhite, leftWhite + (int) individualSquareBoxSize, topWhite + (int) individualSquareBoxSize);
        Animals horseWhite1 = new Animals(11, horseWhiteImage, "white", "horse", 1, leftWhite, topWhite, rect_temp, 3);
        leftWhite += individualSquareBoxSize;
        rect_temp = new Rect(leftWhite, topWhite, leftWhite + (int) individualSquareBoxSize, topWhite + (int) individualSquareBoxSize);
        Animals camelWhite1 = new Animals(12, camelWhiteImage, "white", "camel", 1, leftWhite, topWhite, rect_temp, 2);
        leftWhite += individualSquareBoxSize;
        rect_temp = new Rect(leftWhite, topWhite, leftWhite + (int) individualSquareBoxSize, topWhite + (int) individualSquareBoxSize);
        Animals elephantWhite1 = new Animals(13, elephantWhiteImage, "white", "elephant", 1, leftWhite, topWhite, rect_temp, 1);
        leftWhite += individualSquareBoxSize;
        rect_temp = new Rect(leftWhite, topWhite, leftWhite + (int) individualSquareBoxSize, topWhite + (int) individualSquareBoxSize);
        Animals horseWhite2 = new Animals(14, horseWhiteImage, "white", "horse", 1, leftWhite, topWhite, rect_temp, 3);
        leftWhite += individualSquareBoxSize;
        rect_temp = new Rect(leftWhite, topWhite, leftWhite + (int) individualSquareBoxSize, topWhite + (int) individualSquareBoxSize);
        Animals dogWhite2 = new Animals(15, dogWhiteImage, "white", "dog", 1, leftWhite, topWhite, rect_temp, 4);
        leftWhite += individualSquareBoxSize;
        rect_temp = new Rect(leftWhite, topWhite, leftWhite + (int) individualSquareBoxSize, topWhite + (int) individualSquareBoxSize);
        Animals catWhite2 = new Animals(16, catWhiteImage, "white", "cat", 1, leftWhite, topWhite, rect_temp, 5);


        animals1.add(camelWhite1);
        animals2.add(camelRed1);
        animals1.add(dogWhite1);
        animals1.add(dogWhite2);

        animals2.add(dogRed1);
        animals2.add(dogRed2);
        animals1.add(elephantWhite1);
        animals2.add(elephantRed1);
        animals1.add(horseWhite1);
        animals1.add(horseWhite2);

        animals2.add(horseRed1);
        animals2.add(horseRed2);
        animals1.add(catWhite1);
        animals1.add(catWhite2);

        animals2.add(catRed1);
        animals2.add(catRed2);
    }

    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (animals1.isEmpty()) {
            canvas_store = canvas;
            errorMsg.setTextColor(Color.RED);
            currentTV.setText("Current turn: Black");
            createPlayers();
            saveAnimalHistory(animals1, animals1_borad_history);
            saveAnimalHistory(animals2, animals2_borad_history);
            historyToUndo();
        }
        errorMsg.setText("");
        drawSquires(canvas);
        drawAnimals(canvas);
    }

    private void drawAnimals(Canvas canvas) {
        boolean actionTaken = false;
        if (pullableAnimals.size() != 0 & pullingButtonPressed==false){
            pullableAnimals = new ArrayList<>();
        }
        if (pullableAnimals.size() != 0 & pullingButtonPressed){
            for (Animals pullableAnimal: pullableAnimals){
                if (pullableAnimal.rect.contains((int) touchx[0], (int) touchy[0])) {
                    pullableAnimal.rect = rectForPulledAnimal;
                    pullableAnimals = new ArrayList<>();
                    pullingButtonPressed = false;
                    if (pullableAnimal.team == 1){
                        stepsTaken2 ++;
                    }
                    else if(pullableAnimal.team == 2){
                        stepsTaken1 ++;
                    }
                }
            }
        }
        else if (posibleRectsForPushing.size() == 0 & gameOver == false) {
            if (!(stepsTaken2 == 4 || stepsTaken1 == 4)) {
                if (touchx.length > 0 && touchy.length > 0 && playerTurn == 1) {
                    if (animalHighligted && posibleRects.size() != 0) {
                        actionTaken = movePlayer(animals1, animals2);
                    }
                    if (actionTaken == false) {
                        actionTaken = selectAnimal(animals1, canvas);
                    }
                    if (actionTaken == false) {
                        animalHighligted = false;
                        removeCurrentSelected(animals1);
                        posibleRects = new ArrayList<>();
                    }
                }
                if (touchx.length > 0 && touchy.length > 0 && playerTurn == 2) {
                    if (animalHighligted && posibleRects.size() != 0) {
                        actionTaken = movePlayer(animals2, animals1);
                    }
                    if (actionTaken == false) {
                        actionTaken = selectAnimal(animals2, canvas);
                    }
                    if (actionTaken == false) {
                        animalHighligted = false;
                        removeCurrentSelected(animals2);
                        posibleRects = new ArrayList<>();
                    }
                }
            }
        } else {
            for (Rect rect : posibleRectsForPushing) {
                if (touchx.length > 0 && touchy.length > 0 && playerTurn == 1) {
                    if (rect.contains((int) touchx[0], (int) touchy[0])) {
                        for (Animals animal1 : animals2) {
                            if (animal1.toBePushed) {
                                animal1.rect = rect;
                                posibleRectsForPushing = new ArrayList<>();
                                animal1.toBePushed = false;
                                animalHighligted = false;
                                posibleRects = new ArrayList<>();
                                checkAndImmobileAnimal(animals1, animals2);
                                checkAndImmobileAnimal(animals2, animals1);
                                freeImmobileAnimal(animals2, animals1);
                                freeImmobileAnimal(animals1, animals2);
                                if (playerTurn == 1) {
                                    stepsTaken1++;
                                } else {
                                    stepsTaken2++;
                                }
                            }
                        }
                    }
                } else {
                    if (touchx.length > 0 && touchy.length > 0 && playerTurn == 2) {
                        if (rect.contains((int) touchx[0], (int) touchy[0])) {
                            for (Animals animal1 : animals1) {
                                if (animal1.toBePushed) {
                                    animal1.rect = rect;
                                    posibleRectsForPushing = new ArrayList<>();
                                    animal1.toBePushed = false;
                                    animalHighligted = false;
                                    posibleRects = new ArrayList<>();
                                    checkAndImmobileAnimal(animals1, animals2);
                                    checkAndImmobileAnimal(animals2, animals1);
                                    freeImmobileAnimal(animals2, animals1);
                                    freeImmobileAnimal(animals1, animals2);
                                    if (playerTurn == 1) {
                                        stepsTaken1++;
                                    } else {
                                        stepsTaken2++;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if (posibleRectsForPushing.size() != 0) {
                errorMsg.setText("Please select a rectanlge to push the enemy animal");
            }
        }
        for (Animals animal : animals2) {
            if (animal.name.equals("rabit")) {
                for (Rect winningRect : winningRectsof2) {
                    if (animal.rect.equals(winningRect)) {
                        gameOver = true;
                        errorMsg.setText("Game over : Red Won");
                    }
                }
            }
//            if (animal.active) {
            if (checkCaptured(animal, animals2)) {
                checkAndImmobileAnimal(animals1, animals2);
                checkAndImmobileAnimal(animals2, animals1);
                freeImmobileAnimal(animals2, animals1);
                freeImmobileAnimal(animals1, animals2);
                drawAnimals(canvas);
                break;
            }
            canvas.drawBitmap(animal.image, animal.rect.left, animal.rect.top, null);

            if (animal.currentlySelected) {
                canvas.drawRect(animal.rect, highlightPaint);
            }
            if (animal.immobile) {
                canvas.drawRect(animal.rect, immobilePaint);
            }
//            }
        }
        for (Animals animal : animals1) {
            if (animal.name.equals("rabit")) {
                for (Rect winningRect : winningRectsof1) {
                    if (animal.rect.equals(winningRect)) {
                        gameOver = true;
                        errorMsg.setText("Game over : Black Won");
                    }
                }
            }
//            if (animal.active) {
            if (checkCaptured(animal, animals1)) {
                checkAndImmobileAnimal(animals1, animals2);
                checkAndImmobileAnimal(animals2, animals1);
                freeImmobileAnimal(animals2, animals1);
                freeImmobileAnimal(animals1, animals2);
                drawAnimals(canvas);
                break;
            }
            canvas.drawBitmap(animal.image, animal.rect.left, animal.rect.top, null);

            if (animal.currentlySelected) {
                canvas.drawRect(animal.rect, highlightPaint);
            }
            if (animal.immobile) {
                canvas.drawRect(animal.rect, immobilePaint);
            }
//            }
        }
        for (Rect pos_rect : posibleRects) {
            canvas.drawRect(pos_rect, rectToMovePaint);
        }
        for (Rect pos_rect : posibleRectsForPushing) {
            canvas.drawRect(pos_rect, rectToMovePaint);
        }
        for (Animals pos_rect : pullableAnimals) {
            canvas.drawRect(pos_rect.rect, rectToMovePaint);
        }

    }

    private boolean checkCaptured(Animals animal, ArrayList<Animals> animalsArray2) {
        for (Rect trapRect : TrapSquares) {
            if (trapRect.equals(animal.rect)) {
                if (!(hasAnyFrienlyAnimal(animal, animalsArray2))) {
                    if (animal.team == 1) {
                        animals1.remove(animal);
                        animals1_borad_history = new ArrayList<>();
                        return true;
                    } else if (animal.team == 2) {
                        animals2.remove(animal);
                        animals2_borad_history = new ArrayList<>();
                        return true;
                    }

//                    animal.rect = new Rect(23222,32222,23222,23222);
                }
            }
        }
        return false;
    }

    private boolean selectAnimal(ArrayList<Animals> animals, Canvas canvas) {
        for (Animals animal : animals) {
//            if (animal.active) {
            if (animal.rect.contains((int) touchx[0], (int) touchy[0]) & animal.immobile == false) {
                removeCurrentSelected(animals);
                animal.currentlySelected = true;
                highlightPossibleMoves(animal, canvas);
                animalHighligted = true;
                return true;
            }
//            }
        }
        return false;
    }

    private boolean movePlayer(ArrayList<Animals> animals, ArrayList<Animals> enemy_animals) {
        for (Rect rect : posibleRects) {
            if (rect.contains((int) touchx[0], (int) touchy[0])) {
                for (Animals animal1 : animals) {
//                    if (animal1.active) {
                    if (animal1.currentlySelected) {
                        for (Rect r : animal1.rectList) {
                            if (rect.equals(r)) {
                                errorMsg.setText("Move is not accepted You can'nt move to the same place");
                                return false;
                            }
                        }
                        if (!(animal1.rectList.contains(rect))) {
                            animal1.rectList.add(rect);
                        }
                        if (!(animal1.rectList.contains(animal1.rect))) {
                            animal1.rectList.add(animal1.rect);
                        }

                        checkPossiblePush(animal1, rect, enemy_animals);
                        checkPossiblePull(animal1, rect, animal1.rect, enemy_animals);
                        animal1.rect = rect;
                        removeCurrentSelected(animals);
                        animalHighligted = false;
                        posibleRects = new ArrayList<>();
                        checkAndImmobileAnimal(animals1, animals2);
                        checkAndImmobileAnimal(animals2, animals1);
                        freeImmobileAnimal(animals2, animals1);
                        freeImmobileAnimal(animals1, animals2);
                        if (playerTurn == 1) {
                            stepsTaken1++;
                        } else {
                            stepsTaken2++;
                        }
                        return true;
                    }
//                    }
                }
            }
        }
        return false;
    }

    private void checkPossiblePull(Animals animal1, Rect  rect, Rect animal_rect, ArrayList<Animals> enemy_animals) {
        if (posibleRectsForPushing.size() == 0){
            ArrayList<Rect> rectList = new ArrayList<>();
            int[] sideRect = getLeftRect(animal1.rect);
            Rect rectAvail = isRectAvailable(sideRect[0], sideRect[1]);
            if (rectAvail != null) {
                rectList.add(rectAvail);
            }
            sideRect = getRightRect(animal1.rect);
            rectAvail = isRectAvailable(sideRect[0], sideRect[1]);
            if (rectAvail != null) {
                rectList.add(rectAvail);
            }
            sideRect = getTopRect(animal1.rect);
            rectAvail = isRectAvailable(sideRect[0], sideRect[1]);
            if (rectAvail != null) {
                rectList.add(rectAvail);
            }
            sideRect = getBottomRect(animal1.rect);
            rectAvail = isRectAvailable(sideRect[0], sideRect[1]);
            if (rectAvail != null) {
                rectList.add(rectAvail);
            }
            for (Rect rectL : rectList) {
                hasAnyPullableAnimal(animal1, rectL, enemy_animals);
            }
        }
    }

    private boolean hasAnyPullableAnimal(Animals animal1, Rect rectL, ArrayList<Animals> enemy_animals) {
        for (Animals animal:enemy_animals){
            if (rectL.equals(animal.rect) & animal1.priority < animal.priority){
                if (animal1.team == 1 & stepsTaken1 <= 2){
                    rectForPulledAnimal = animal1.rect;
                    pullableAnimals.add(animal);
                    errorMsg.setText("Press Pull button to pull animal or continue");
                }
                else if (animal1.team == 2 & stepsTaken2 <= 2){
                    rectForPulledAnimal = animal1.rect;
                    errorMsg.setText("Press Pull button to pull animal or continue");
                    pullableAnimals.add(animal);
                }

            }
        }
        return true;
    }

    private void checkPossiblePush(Animals animal1, Rect rect, ArrayList<Animals> enemy_animals) {
        for (Animals enemy_animal : enemy_animals) {
            if (rect.equals(enemy_animal.rect)) {
                if (animal1.priority < enemy_animal.priority) {
                    pushAnimalToNearRect(enemy_animal);
                }
            }
        }
    }

    private boolean pushAnimalToNearRect(Animals animal_to_be_pushed) {
        ArrayList<Rect> rectList = new ArrayList<>();
        int[] sideRect = getLeftRect(animal_to_be_pushed.rect);
        Rect rectAvail = isRectAvailable(sideRect[0], sideRect[1]);
        if (rectAvail != null) {
            rectList.add(rectAvail);
        }
        sideRect = getRightRect(animal_to_be_pushed.rect);
        rectAvail = isRectAvailable(sideRect[0], sideRect[1]);
        if (rectAvail != null) {
            rectList.add(rectAvail);
        }
        sideRect = getTopRect(animal_to_be_pushed.rect);
        rectAvail = isRectAvailable(sideRect[0], sideRect[1]);
        if (rectAvail != null) {
            rectList.add(rectAvail);
        }
        sideRect = getBottomRect(animal_to_be_pushed.rect);
        rectAvail = isRectAvailable(sideRect[0], sideRect[1]);
        if (rectAvail != null) {
            rectList.add(rectAvail);
        }
        for (Rect rect : rectList) {
            if (isRectFreeFromEveryOne(rect) != false) {
                posibleRectsForPushing.add(rect);
                animal_to_be_pushed.toBePushed = true;
//                animal_to_be_pushed.rect = rect;
                removeCurrentSelected(animals1);
                removeCurrentSelected(animals2);

//                return true;

            }
        }
        return true;
    }

    private void freeImmobileAnimal(ArrayList<Animals> animals, ArrayList<Animals> enemy_animals) {
        for (Animals animal : animals) {
            boolean hasEnemyStronger = hasAnyEnemyAnimal(animal, enemy_animals);
            if (hasEnemyStronger) {
                animal.immobile = true;
            } else {
                animal.immobile = false;
            }
            boolean hasFrienlyPience = hasAnyFrienlyAnimal(animal, animals);
            if (hasFrienlyPience) {
                animal.immobile = false;
            }
        }
    }

    private boolean hasAnyEnemyAnimal(Animals animal, ArrayList<Animals> enemy_animals) {
        ArrayList<Rect> rectList = new ArrayList<>();
        int[] sideRect = getLeftRect(animal.rect);
        Rect rectAvail = isRectAvailable(sideRect[0], sideRect[1]);
        if (rectAvail != null) {
            rectList.add(rectAvail);
        }
        sideRect = getRightRect(animal.rect);
        rectAvail = isRectAvailable(sideRect[0], sideRect[1]);
        if (rectAvail != null) {
            rectList.add(rectAvail);
        }
        sideRect = getTopRect(animal.rect);
        rectAvail = isRectAvailable(sideRect[0], sideRect[1]);
        if (rectAvail != null) {
            rectList.add(rectAvail);
        }
        sideRect = getBottomRect(animal.rect);
        rectAvail = isRectAvailable(sideRect[0], sideRect[1]);
        if (rectAvail != null) {
            rectList.add(rectAvail);
        }
        for (Rect nearRect : rectList) {
            for (Animals enemyNear : enemy_animals) {
                if (enemyNear.rect.equals(nearRect) & animal.priority > enemyNear.priority) {
                    return true;
                }
            }
        }
        return false;

    }

    private void checkAndImmobileAnimal(ArrayList<Animals> animals, ArrayList<Animals> animals_to_freeze) {
        for (Animals animal : animals) {
            ArrayList<Rect> rectList = new ArrayList<>();
            if (animal.priority != 6) {
                int[] sideRect = getLeftRect(animal.rect);
                Rect rectAvail = isRectAvailable(sideRect[0], sideRect[1]);
                if (rectAvail != null) {
                    rectList.add(rectAvail);
                }
                sideRect = getRightRect(animal.rect);
                rectAvail = isRectAvailable(sideRect[0], sideRect[1]);
                if (rectAvail != null) {
                    rectList.add(rectAvail);
                }
                sideRect = getTopRect(animal.rect);
                rectAvail = isRectAvailable(sideRect[0], sideRect[1]);
                if (rectAvail != null) {
                    rectList.add(rectAvail);
                }
                sideRect = getBottomRect(animal.rect);
                rectAvail = isRectAvailable(sideRect[0], sideRect[1]);
                if (rectAvail != null) {
                    rectList.add(rectAvail);
                }
                for (Rect nearRect : rectList) {
                    immobilAndFreeze(nearRect, animals_to_freeze, animal);
                }
            }
        }
    }

    private void immobilAndFreeze(Rect rectAvail, ArrayList<Animals> animals_to_freeze, Animals currentAnimal) {
        for (Animals animal : animals_to_freeze) {
            if (animal.rect.equals(rectAvail)) {
                if (animal.priority > currentAnimal.priority) {
                    boolean hasFrienlyPience = hasAnyFrienlyAnimal(animal, animals_to_freeze);
                    if (!(hasFrienlyPience)) {
                        animal.immobile = true;
                    }
                }
            }
        }
    }

    private boolean hasAnyFrienlyAnimal(Animals animal, ArrayList<Animals> animals_to_freeze) {
        ArrayList<Rect> rectList = new ArrayList<>();
        int[] sideRect = getLeftRect(animal.rect);
        Rect rectAvail = isRectAvailable(sideRect[0], sideRect[1]);
        if (rectAvail != null) {
            rectList.add(rectAvail);
        }
        sideRect = getRightRect(animal.rect);
        rectAvail = isRectAvailable(sideRect[0], sideRect[1]);
        if (rectAvail != null) {
            rectList.add(rectAvail);
        }
        sideRect = getTopRect(animal.rect);
        rectAvail = isRectAvailable(sideRect[0], sideRect[1]);
        if (rectAvail != null) {
            rectList.add(rectAvail);
        }
        sideRect = getBottomRect(animal.rect);
        rectAvail = isRectAvailable(sideRect[0], sideRect[1]);
        if (rectAvail != null) {
            rectList.add(rectAvail);
        }
        for (Rect nearRect : rectList) {
            for (Animals animalNear : animals_to_freeze) {
                if (animalNear.rect.equals(nearRect)) {
                    return true;
                }
            }
        }
        return false;
    }

    private int[] getLeftRect(Rect rect) {
        int[] leftRect = new int[2];
        int new_x = (int) (rect.left - (individualSquareBoxSize / 2));
        int new_y = (int) (rect.top + (individualSquareBoxSize / 2));
        leftRect[0] = new_x;
        leftRect[1] = new_y;
        return leftRect;
    }

    private int[] getBottomRect(Rect rect) {
        int[] leftRect = new int[2];
        int new_x = (int) (rect.right - (individualSquareBoxSize / 2));
        int new_y = (int) (rect.bottom + (individualSquareBoxSize / 2));
        leftRect[0] = new_x;
        leftRect[1] = new_y;
        return leftRect;
    }

    private int[] getTopRect(Rect rect) {
        int[] leftRect = new int[2];
        int new_x = (int) (rect.left + (individualSquareBoxSize / 2));
        int new_y = (int) (rect.top - (individualSquareBoxSize / 2));
        leftRect[0] = new_x;
        leftRect[1] = new_y;
        return leftRect;
    }

    private int[] getRightRect(Rect rect) {
        int[] leftRect = new int[2];
        int new_x = (int) (rect.left + (individualSquareBoxSize / 2) + individualSquareBoxSize);
        int new_y = (int) (rect.top + (individualSquareBoxSize / 2));
        leftRect[0] = new_x;
        leftRect[1] = new_y;
        return leftRect;
    }


    private void removeCurrentSelected(ArrayList<Animals> animals2) {
        for (Animals animal : animals2) {
            if (animal.currentlySelected == true) {
                animal.currentlySelected = false;
            }
        }
    }

    private void highlightPossibleMoves(Animals animal, Canvas canvas) {
        int new_x = (int) (animal.rect.left - (individualSquareBoxSize / 2));
        int new_y = (int) (animal.rect.top + (individualSquareBoxSize / 2));
        posibleRects = new ArrayList<>();
        Rect rectAvail = isRectAvailable(new_x, new_y);
        if (rectAvail != null) {
            boolean rectReadyToMove = isRectFree(animal, rectAvail);
            if (rectReadyToMove) {
//                canvas.drawRect(rectAvail, rectToMovePaint);
                posibleRects.add(rectAvail);
            }
        }
        if (!(animal.name.equals("rabit") && animal.team == 2)) {
            new_x = (int) (animal.rect.right - (individualSquareBoxSize / 2));
            new_y = (int) (animal.rect.bottom + (individualSquareBoxSize / 2));
//            canvas.drawCircle(new_x, new_y, 80, rectToMovePaint);
            rectAvail = isRectAvailable(new_x, new_y);
            if (rectAvail != null) {
                boolean rectReadyToMove = isRectFree(animal, rectAvail);
                if (rectReadyToMove) {
//                    canvas.drawRect(rectAvail, rectToMovePaint);
                    posibleRects.add(rectAvail);
                }
            }
        }
        if (!(animal.name.equals("rabit") && animal.team == 1)) {
            new_x = (int) (animal.rect.left + (individualSquareBoxSize / 2));
            new_y = (int) (animal.rect.top - (individualSquareBoxSize / 2));
            rectAvail = isRectAvailable(new_x, new_y);
            if (rectAvail != null) {
                boolean rectReadyToMove = isRectFree(animal, rectAvail);
                if (rectReadyToMove) {
//                    canvas.drawRect(rectAvail, rectToMovePaint);
                    posibleRects.add(rectAvail);
                }
            }
        }
        new_x = (int) (animal.rect.left + (individualSquareBoxSize / 2) + individualSquareBoxSize);
        new_y = (int) (animal.rect.top + (individualSquareBoxSize / 2));
        rectAvail = isRectAvailable(new_x, new_y);
        if (rectAvail != null) {
            boolean rectReadyToMove = isRectFree(animal, rectAvail);
            if (rectReadyToMove) {
//                canvas.drawRect(rectAvail, rectToMovePaint);
                posibleRects.add(rectAvail);
            }
        }
    }

    private Rect isRectAvailable(int new_left, int new_top) {
        for (Rect r : squares) {
            if (r.contains(new_left, new_top)) {
                return r;
            }
        }
        return null;
    }

    private boolean isRectFree(Animals current_animal, Rect r) {
        for (Animals animal : animals1) {
            if (animal.rect.equals(r) & current_animal.team != animal.team & current_animal.priority < animal.priority & canAnimalMove(animal)) {
                if (playerTurn == 1 & stepsTaken1 <= 2) {
                    return true;
                } else if (playerTurn == 2 & stepsTaken2 <= 2) {
                    return true;
                } else {
                    return false;
                }
            } else if (animal.rect.equals(r)) {
                return false;
            }
        }
        for (Animals animal : animals2) {
            if (animal.rect.equals(r) & current_animal.team != animal.team & current_animal.priority < animal.priority & canAnimalMove(animal)) {
                if (playerTurn == 1 & stepsTaken1 <= 2) {
                    return true;
                } else if (playerTurn == 2 & stepsTaken2 <= 2) {
                    return true;
                } else {
                    return false;
                }

            } else if (animal.rect.equals(r)) {
                return false;
            }
        }
        return true;
    }

    private boolean canAnimalMove(Animals animal) {
        ArrayList<Rect> rectList = new ArrayList<>();
        int[] sideRect = getLeftRect(animal.rect);
        Rect rectAvail = isRectAvailable(sideRect[0], sideRect[1]);
        if (rectAvail != null) {
            rectList.add(rectAvail);
        }
        sideRect = getRightRect(animal.rect);
        rectAvail = isRectAvailable(sideRect[0], sideRect[1]);
        if (rectAvail != null) {
            rectList.add(rectAvail);
        }
        sideRect = getTopRect(animal.rect);
        rectAvail = isRectAvailable(sideRect[0], sideRect[1]);
        if (rectAvail != null) {
            rectList.add(rectAvail);
        }
        sideRect = getBottomRect(animal.rect);
        rectAvail = isRectAvailable(sideRect[0], sideRect[1]);
        if (rectAvail != null) {
            rectList.add(rectAvail);
        }
        for (Rect nearRect : rectList) {
            boolean isRectFree = isRectFreeFromEveryOne(nearRect);
            if (isRectFree) {
                return true;
            }
        }
        return false;
    }

    private boolean isRectFreeFromEveryOne(Rect rect) {
        for (Animals animal : animals1) {
            if (animal.rect.equals(rect)) {
                return false;
            }
        }
        for (Animals animal : animals2) {
            if (animal.rect.equals(rect)) {
                return false;
            }
        }
        return true;
    }


    private void drawSquires(Canvas canvas) {
        int incre = 2;
        int left = 0;
        int top = 0;
        int right = (int) individualSquareBoxSize;
        int bottom = (int) individualSquareBoxSize;
        Rect rect;
        int tot = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {

                Paint paint = null;
                tot++;
                rect = new Rect(left + incre, top + incre, right - incre, bottom - incre);
                Rect rect_new = new Rect(left, top, right, bottom);
                if ((i == 2 | i == 5) & (j == 2 | j == 5)) {
                    paint = square2Colour;
                    if (TrapSquares.size() < 4) {
                        TrapSquares.add(rect_new);
                    }
                } else {
                    paint = square1Color;
                }
                if (i == 0 & winningRectsof2.size() != 8) {
                    winningRectsof2.add(rect_new);
                }
                if (i == 7 & winningRectsof1.size() != 8) {
                    winningRectsof1.add(rect_new);
                }
                canvas.drawRect(rect, paint);
                squares.add(rect_new);
                right += individualSquareBoxSize;
                left += individualSquareBoxSize;
            }
            left = 0;
            top += (int) individualSquareBoxSize;
            right = (int) individualSquareBoxSize;
            bottom += (int) individualSquareBoxSize;
        }
    }

    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int size = 0;
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        int widthWithoutPadding = width - getPaddingLeft() - getPaddingRight();
        int heightWithoutPadding = height - getPaddingTop() - getPaddingBottom();
        // set the dimensions
        if (widthWithoutPadding > heightWithoutPadding) {
            size = heightWithoutPadding;
        } else {
            size = widthWithoutPadding;
        }
        individualSquareBoxSize = size / 8;
        viewWidth = size + getPaddingLeft() + getPaddingRight();
        setMeasuredDimension(size + getPaddingLeft() + getPaddingRight(), size + getPaddingTop() + getPaddingBottom());
    }

    public Bitmap resizeBitmapImage(Bitmap image) {
        Matrix mat = new Matrix();
        RectF src = new RectF(0, 0, image.getWidth(), image.getHeight());
        RectF dst = new RectF(0, 0, (int) individualSquareBoxSize, (int) individualSquareBoxSize);
        mat.setRectToRect(src, dst, Matrix.ScaleToFit.CENTER);
        return Bitmap.createBitmap(image, 0, 0, image.getWidth(), image.getHeight(), mat, true);
    }

    public boolean onTouchEvent(MotionEvent event) {
        //when user first touches the screen
        if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
            int pointer_id = event.getPointerId(event.getActionIndex());
            touches[pointer_id] = true;
            touchx[pointer_id] = event.getX();
            touchy[pointer_id] = event.getY();
            touching = true;
            invalidate();
            return true;

        }
        return super.onTouchEvent(event);
    }

    public void setText(TextView currentPlayerTV) {
        currentTV = currentPlayerTV;
    }

    public void setTextError(TextView errorMsgSet) {
        errorMsg = errorMsgSet;
    }

    public void changePlayerTurn() {
        if (stepsTaken1 == 4) {
            boolean seenBeofre = checkIfBordSeenBefore(animals1, animals1_borad_history);
            if (seenBeofre == false) {
                removeCurrentSelected(animals1);
                saveAnimalHistory(animals1, animals1_borad_history);
                historyToUndo();
                currentTV.setText("Current turn: Red");
                playerTurn = 2;
                stepsTaken2 = 0;
                stepsTaken1 = 0;
                animalHighligted = false;
                posibleRects = new ArrayList<>();
                emptyRectList(animals2);
            }
        }
        if (stepsTaken2 == 4) {
            boolean seenBeofre = checkIfBordSeenBefore(animals2, animals2_borad_history);
            if (seenBeofre == false) {
                removeCurrentSelected(animals2);
                historyToUndo();
                saveAnimalHistory(animals2, animals2_borad_history);
                currentTV.setText("Current turn: Black");
                playerTurn = 1;
                stepsTaken1 = 0;
                stepsTaken2 = 0;
                animalHighligted = false;
                posibleRects = new ArrayList<>();
                emptyRectList(animals1);
            }
        }
    }

    private void historyToUndo() {
        undoHistory1 = new HashMap<Integer, Rect>();
        undoHistory2 = new HashMap<Integer, Rect>();
        for (Animals animal : animals1) {
            undoHistory1.put(animal.id, animal.rect);
        }
        for (Animals animal : animals2) {
            undoHistory2.put(animal.id, animal.rect);
        }
    }

    private void saveAnimalHistory(ArrayList<Animals> animals1, ArrayList<ArrayList<Animals>> animals1_borad_history) {
        Animals tempAnimal;
        ArrayList<Animals> tempList = new ArrayList<>();
        for (Animals animal : animals1) {
            tempAnimal = new Animals(animal.id, animal.image, animal.colour, animal.name, animal.team, animal.left, animal.top, animal.rect, animal.priority);
            tempList.add(tempAnimal);
        }
        animals1_borad_history.add(tempList);
    }

    private boolean checkIfBordSeenBefore(ArrayList<Animals> animals1, ArrayList<ArrayList<Animals>> animals1_borad_history) {
        boolean allSame = false;
        for (ArrayList<Animals> animalArray : animals1_borad_history) {
            if (allSame) {
                errorMsg.setText("Board has seen this move before");
                return true;
            }
            allSame = true;
            for (int i = 0; i < animals1.size(); i++) {
                if (animalArray.get(i).id == animals1.get(i).id) {
                    if (!(animalArray.get(i).rect.equals(animals1.get(i).rect))) {
                        allSame = false;
                        break;
                    }
                }
            }
        }
        return false;
    }


    private void emptyRectList(ArrayList<Animals> animals2) {
        for (Animals animal : animals2) {
            animal.rectList = new ArrayList<>();
        }
    }

    public void pullPlayer() {
        if (pullableAnimals.size() > 0) {
            pullingButtonPressed = true;
            errorMsg.setText("Touch the animal to pull");
        }
        else{
            errorMsg.setText("Nothing to Pull");
        }
    }

    public void undoMove() {
        if (gameOver == false) {
            for (Animals animal : animals1) {
                animal.rect = undoHistory1.get(animal.id);
            }
            for (Animals animal : animals2) {
                animal.rect = undoHistory2.get(animal.id);
            }
            invalidate();
            stepsTaken1 = 0;
            stepsTaken2 = 0;
            removeCurrentSelected(animals1);
            removeCurrentSelected(animals2);
            animalHighligted = false;
            posibleRects = new ArrayList<>();
            checkAndImmobileAnimal(animals1, animals2);
            checkAndImmobileAnimal(animals2, animals1);
            freeImmobileAnimal(animals2, animals1);
            freeImmobileAnimal(animals1, animals2);
            emptyRectList(animals2);
            emptyRectList(animals1);

        }
    }
}

