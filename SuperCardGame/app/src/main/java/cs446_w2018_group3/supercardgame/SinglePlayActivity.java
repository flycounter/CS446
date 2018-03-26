        package cs446_w2018_group3.supercardgame;
        import android.arch.lifecycle.Observer;
        import android.arch.lifecycle.ViewModelProviders;
        import android.content.Intent;
        import android.graphics.Color;
        import android.os.Handler;
        import android.support.annotation.Nullable;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.util.DisplayMetrics;
        import android.util.Log;
        import android.view.View;
        import android.widget.Button;
        import android.widget.CheckBox;
        import android.widget.CompoundButton;
        import android.widget.ImageView;
        import android.widget.LinearLayout;
        import android.widget.TextView;

        import java.util.ArrayList;
        import java.util.HashMap;
        import java.util.List;
        import java.util.Map;

        import cs446_w2018_group3.supercardgame.Exception.PlayerActionException.PlayerNotFoundException;
        import cs446_w2018_group3.supercardgame.model.player.Player;
        import cs446_w2018_group3.supercardgame.model.Translate;
        import cs446_w2018_group3.supercardgame.model.cards.Card;
        import cs446_w2018_group3.supercardgame.model.field.GameField;
        import cs446_w2018_group3.supercardgame.util.events.stateevent.StateEventAdapter;
        import cs446_w2018_group3.supercardgame.util.events.stateevent.TurnStartEvent;
        import cs446_w2018_group3.supercardgame.viewmodel.GameViewModel;

public class SinglePlayActivity extends AppCompatActivity {
    //widgets
    TextView oppStatus;
    TextView oppBuffEquip;
    TextView weather;
    TextView playerStatus;
    TextView playerBuffEquip;
    TextView actionLog;
    Button combine;
    Button use;
    Button endTurn;
    Button surrender;
    int TEXTSIZE;
    int clickCount;
    int stepCount;
    GameViewModel viewModel;
    Map<Integer, Integer> CardDataMap; // Map<checkbox_id, card_id>
    List<Integer> chosenCard; // store id of cards chosen by the player
    List<CheckBox> chosenBox;
    int gameMode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_single_play);
        //get the size of screen and set textsize
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenHeight = dm.heightPixels;
        if (screenHeight <= 2000) TEXTSIZE = 20;
        else {
            TEXTSIZE = 26;
        }
        chosenCard = new ArrayList<>();
        chosenBox = new ArrayList<CheckBox>();
        CardDataMap = new HashMap<>();
        Intent intent = getIntent();
        gameMode = intent.getIntExtra("mode", 0);
        //connect widgets
        oppStatus = findViewById(R.id.OppStatus);
        oppBuffEquip = findViewById(R.id.OppBuffEquid);
        weather = findViewById(R.id.Weather);
        playerStatus = findViewById(R.id.PlayerStatus);
        playerBuffEquip = findViewById(R.id.PlayerBuffEquip);
        actionLog = findViewById(R.id.ActionLog);
        combine = findViewById(R.id.Combine);
        use = findViewById(R.id.Use);
        endTurn = findViewById(R.id.EndTurn);
        surrender = findViewById(R.id.Surrender);
        //start text
        actionLog.setTextColor(Color.RED);
        setStatus("Dragon", 30, 10, "Boss", 5, 1);
        setStatus("You", 20, 10, "None", 5, 2);
        List<String> emptyList = new ArrayList<String>();
        setBuffEquip(emptyList, emptyList, 1);
        setBuffEquip(emptyList, emptyList, 2);
        combine.setText("Combine");
        use.setText("Use");
        endTurn.setText("EndTurn");
        surrender.setText("Surrender");
        weather.setText("Weather:");
        actionLog.setText("Action:\n");
        setTextSize(TEXTSIZE);
        //create hand cards
        resetHandView();


        // viewmodel
        final GameViewModel viewModel = ViewModelProviders.of(this).get(GameViewModel.class);
        this.viewModel = viewModel;

        // listeners
        combine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int size = chosenCard.size();
                if (size != 2) {
                    actionLog.setText("Action:\nInvalid command,you have to choose exactly two element card to combine");
                } else {
                    if (gameMode == 3) {
                        stepCount += 1;
                        actionLog.performClick();
                        combine.setEnabled(false);
                    }
                    viewModel.combineCards(chosenCard);
                }
            }
        });
        use.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int size = chosenCard.size();
                if (size < 1) {
                    actionLog.setText("Action:\nInvalid command,you have to choose at least one card to use.");
                    return;
                } else if (size > 1) {
                    actionLog.setText("Action:\nInvalid command,you can only use one card each time.");
                    return;
                }

                if (gameMode == 3) {
                    stepCount += 1;
                    actionLog.performClick();
                    use.setEnabled(false);
                }

                try {
                    viewModel.useElementCard(viewModel.getOpponent().getValue().getId(), chosenCard.get(0));
                }
                catch (PlayerNotFoundException err) {
                    // opponent not found during the game, something is wrong
                    Log.w("main", err);
                }
            }
        });
        endTurn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionLog.setText("Action:\nYou end the turn,now is your opponent's turn.");
                combine.setEnabled(false);
                use.setEnabled(false);
                endTurn.setEnabled(false);

                if (gameMode == 3) {
                    Intent intent = new Intent();
                    intent.setClass(SinglePlayActivity.this, MainActivity.class);
                    // Start activity
                    v.getContext().startActivity(intent);
                }

                viewModel.turnEnd();

            }
        });
        surrender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionLog.setText("Action:\nYou shall not surrender.");
                //todo
            }
        });
        if (gameMode == 3) {
            clickCount = 0;
            stepCount = 0;
            runTutorial();
        }
    }

    //mode1:set oppStatus,mode2: set playerStatus
    private void setStatus(String name, int hp, int ap, String pclass, int hand, int mode) {
        String text = "Player:" + name + "    HP:" + Integer.toString(hp) + "    AP:" + Integer.toString(ap) + "    Class:" + pclass + "    Hand:" + Integer.toString(hand);
        if (mode == 1) {
            oppStatus.setText(text);

        }
        if (mode == 2) {
            playerStatus.setText(text);

        }
    }

    private void setBuffEquip(List<String> buffList, List<String> equipList, int mode) {
        String text = "Buff:";
        for (int i = 0; i < buffList.size(); i++) {
            text += buffList.get(i) + " ";
        }
        if (buffList.size() == 0) text += "None";
        text += "    Equip:";
        for (int i = 0; i < equipList.size(); i++) {
            text += equipList.get(i) + " ";
        }
        if (equipList.size() == 0) text += "None";
        if (mode == 1) {
            oppBuffEquip.setText(text);

        }
        if (mode == 2) {
            playerBuffEquip.setText(text);

        }
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        viewModel.init(); // player setup
        observeViewModel(viewModel);
        // start game
        viewModel.start();

    }

    private void observeViewModel(final GameViewModel viewModel) {
        // wire up ui elements and LiveData
        // NOTE: playerId is hard coded
        try {
            viewModel.getThisPlayer().observe(this, new Observer<Player>() {
                @Override
                public void onChanged(@Nullable Player player) {
                    // update player status
                    if (player == null) { return; }
                    setStatus(
                            player.getName(),
                            player.getHP(),
                            player.getAP(),
                            player.getPlayerClass(),
                            player.getHand().size(),
                            2);
                    // set hand
                    resetHandView();
                    setHand(player.getHand());
                }
            });
        }
        catch (PlayerNotFoundException err) {
            // player not found, something is wrong. Maybe game wasn't inited correctly?
            Log.w("main", err);
        }

        try {
            viewModel.getOpponent().observe(this, new Observer<Player>() {
                @Override
                public void onChanged(@Nullable Player player) {
                    // update opponent status
                    if (player == null) {
                        // don't set anything if player holder has no data
                        return;
                    }
                    setStatus(
                            player.getName(),
                            player.getHP(),
                            player.getAP(),
                            player.getPlayerClass(),
                            player.getHand().size(),
                            1
                    );
                }
            });
        }
        catch (PlayerNotFoundException err) {
            // opponent not found, something is wrong. Maybe game wasn't inited correctly?
            Log.w("main", err);
        }

        viewModel.getGameRuntime().getGameField().observe(this, new Observer<GameField>() {
            @Override
            public void onChanged(@Nullable GameField gameField) {
                if (gameField == null) { return; }
                weather.setText(gameField.getWeather().getLabel());
            }
        });

        viewModel.getGameRuntime().getCurrPlayer().observe(this, new Observer<Player>() {
            @Override
            public void onChanged(@Nullable Player player) {
                if (player == null) { return; }
                // check if curr player is the player on this client
                try {
                    if (player.equals(viewModel.getThisPlayer().getValue())) {
                        // player's turn
                        // todo update ui elements
                    }
                }
                catch (PlayerNotFoundException err) {
                    Log.w("main", err);
                    // no player at this moment
                }
            }
        });

        viewModel.addStateEventListener(new StateEventAdapter() {
            @Override
            public void onTurnStart(TurnStartEvent e) {
                try {
                    Log.i("main",
                            String.format("TurnStartEvent, playerId: %s, receiver: %s",
                                    e.getSubjectId(), viewModel.getThisPlayer().getValue().getId()));
                    Log.i("main", "" + (e.getSubjectId() == viewModel.getThisPlayer().getValue().getId()));
                    if (e.getSubjectId() == viewModel.getThisPlayer().getValue().getId()) {
                        Log.i("main", "player's turn begins");
                        actionLog.setText("Action:\nNow it's your turn.");
                        combine.setEnabled(true);
                        use.setEnabled(true);
                        endTurn.setEnabled(true);
                    }
                }
                catch (PlayerNotFoundException err) {
                    Log.w("main", err);
                    // player doesn't exist when turn changes?
                }
            }

            @Override
            public void onGameEnd(Player winner) {
                actionLog.setText("Game end. winner is " + winner.getName());
                combine.setEnabled(false);
                use.setEnabled(false);
                endTurn.setEnabled(false);
                surrender.setEnabled(false);
            }
        });
    }

    private void setCard(Card card) {
        // UI layout
        final LinearLayout handView = findViewById(R.id.HandContainer);
        final LinearLayout childLayout = new LinearLayout(this);
        childLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams childLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        handView.addView(childLayout, childLayoutParams);
        ImageView cardView = new ImageView(this);
        final CheckBox cardBox = new CheckBox(this);
        String cardName = Translate.cardToString(card.getCardType());
        setCardImage(cardView, cardName);
        cardView.setClickable(true);
        cardBox.setText(cardName);
        cardBox.setTextSize(32);
        LinearLayout.LayoutParams cardLayoutParams = new LinearLayout.LayoutParams(450, 400);
        LinearLayout.LayoutParams boxLayoutParams = new LinearLayout.LayoutParams(450, 200);
        childLayout.addView(cardBox, boxLayoutParams);
        childLayout.addView(cardView, cardLayoutParams);

        // data binding (in a lame way)
        // NOTE: need to be aware of variable scope
        final int checkboxId = CardDataMap.size();
        CardDataMap.put(checkboxId, card.getCardId());
//        Log.i("view", String.format("card data map: %s -> %s", checkboxId, card.getCardId()));

        // listener
        cardBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    chosenCard.add(CardDataMap.get(checkboxId));
                } else {
                    List<Integer> cardsToDelete = new ArrayList<>();
                    for (Integer id : chosenCard) {
                        if (id.equals(CardDataMap.get(checkboxId))) {
                            cardsToDelete.add(id);
                        }
                    }
                    chosenCard.removeAll(cardsToDelete);
//                    Substituted for api 22
//                    chosenCard.removeIf(cardId -> cardId.equals(CardDataMap.get(checkboxId)));

                }
                Log.i("view", String.format("chosen cards: %s", chosenCard.toString()));
            }
        });
        cardView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                PopupInfo popupInfo = new PopupInfo(SinglePlayActivity.this,card.getCardType());
                popupInfo.showPopup(new View(SinglePlayActivity.this));
            }
        });
    }

    private void setHand(List<Card> cards) {
        // NOTE: must be called after resetHandView()
        for (Card card : cards) {
            setCard(card);
        }
    }

    private void resetHandView() {
        final LinearLayout handView = findViewById(R.id.HandContainer);
        handView.removeAllViews();
        chosenCard.clear();
        CardDataMap.clear();
    }

    private void setTextSize(int textSize) {
        playerBuffEquip.setTextSize(textSize);
        oppBuffEquip.setTextSize(textSize);
        playerStatus.setTextSize(textSize);
        oppStatus.setTextSize(textSize);
        actionLog.setTextSize(textSize);
        weather.setTextSize(textSize);
    }

    private void runTutorial() {
        //For demo
//        ArrayList<Card> hand = new ArrayList<>();
//        hand.add( Card.createNewCard( Translate.CardType.Water ) );
//        hand.add( Card.createNewCard( Translate.CardType.Water ) );
//        hand.add( Card.createNewCard( Translate.CardType.Water ) );
//        hand.add( Card.createNewCard( Translate.CardType.Water ) );
//        hand.add( Card.createNewCard( Translate.CardType.Water ) );
//        viewModel.getGameRuntime().getPlayer().getValue().hand = hand;
        //For Aiur
        combine.setEnabled(false);
        use.setEnabled(false);
        endTurn.setEnabled(false);
        surrender.setEnabled(false);
        actionLog.setClickable(true);
        if (clickCount == 0)
            actionLog.setText("Action:\nHere is the game tutorial,let's learn how to play this card game.");

        actionLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickCount++;
                if (stepCount == 1) {
                    actionLog.setText("You use a Basic card and deal 1 damage. ");
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // Do something after 5s = 5000ms
                            actionLog.setText("Great!Now try to combine two card and make a new card by selecting 2 card and push combine button. ");
                            combine.setEnabled(true);
                        }
                    }, 3000);
                } else if (stepCount == 2) {
                    actionLog.setText("Great!Now you create a more powerful card by combine two card,try to use it.");
                    use.setEnabled(true);
                } else if (stepCount == 3) {
                    actionLog.setText("You use a aqua card and deal 3 damage.");
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // Do something after 3s = 3000ms
                            actionLog.setText("Now you can push endTurn button end this turn and exit the Tutorial.");
                            endTurn.setEnabled(true);
                        }
                    }, 3000);

                } else if (clickCount == 1) {
                    actionLog.setText("Action:\nYour goal is to kill your opponent,by using your hand cards." +
                            "each action,include combine and use your card will cost you corresponding ap.");
                } else if (clickCount == 2) {
                    actionLog.setText("Action:\nFirst step,let's try to use one of your hand card by selecting it and push use button.");
                    use.setEnabled(true);
                }


            }
        });
    }

    private void setCardImage(ImageView cardView, String cardName) {
        switch (cardName) {
            case "Fire":
                cardView.setImageResource(R.drawable.fireicon);
                break;
            case "Water":
                cardView.setImageResource(R.drawable.watericon);
                break;
            case "Air":
                cardView.setImageResource(R.drawable.airicon);
                break;
            case "Dirt":
                cardView.setImageResource(R.drawable.dirticon);
                break;
            case "Flame":
                cardView.setImageResource(R.drawable.flameicon);
                break;
            case "Aqua":
                cardView.setImageResource(R.drawable.auqaicon);
                break;
            case "Gale":
                cardView.setImageResource(R.drawable.galeicon);
                break;
            case "Rock":
                cardView.setImageResource(R.drawable.rockicon);
                break;
            case "Lava":
                cardView.setImageResource(R.drawable.lavaicon);
                break;
            case "Mud":
                cardView.setImageResource(R.drawable.mudicon);
                break;
            case "Steam":
                cardView.setImageResource(R.drawable.steamicon);
                break;
            case "Sand":
                cardView.setImageResource(R.drawable.sandicon);
                break;
            case "Blast":
                cardView.setImageResource(R.drawable.blasticon);
                break;
            case "Ice":
                cardView.setImageResource(R.drawable.iceicon);
                break;

        }
    }
}
