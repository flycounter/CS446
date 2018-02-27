package cs446_w2018_group3.supercardgame;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cs446_w2018_group3.supercardgame.model.Player;
import cs446_w2018_group3.supercardgame.model.Translate;
import cs446_w2018_group3.supercardgame.model.cards.Card;
import cs446_w2018_group3.supercardgame.model.field.GameField;
import cs446_w2018_group3.supercardgame.util.events.playerevent.PlayerCombineElementEvent;
import cs446_w2018_group3.supercardgame.util.events.playerevent.PlayerUseCardEvent;
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
    GameViewModel viewModel;

    Map<Integer, Integer> CardDataMap; // Map<checkbox_id, card_id>
    List<Integer> chosenCard; // store id of cards chosen by the player

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
            TEXTSIZE = 28;
        }
        chosenCard = new ArrayList<>();
        CardDataMap = new HashMap<>();
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
        //init text
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
                    viewModel.getGameRuntime().handlePlayerCombineElementEvent(new PlayerCombineElementEvent(
                            viewModel.getGameRuntime().getPlayer().getValue().getId(),
                            chosenCard
                    ));
                }
            }
        });
        use.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int size = chosenCard.size();
                if (size < 1) {
                    actionLog.setText("Action:\nInvalid command,you have to choose at least one card to use.");
                }
                if (size > 1) {
                    actionLog.setText("Action:\nInvalid command,you can only use one card each time.");
                } else {
                    viewModel.getGameRuntime().handlePlayerUseCardEvent(new PlayerUseCardEvent(
                            viewModel.getGameRuntime().getPlayer().getValue().getId(),
                            viewModel.getGameRuntime().getOpponent().getValue().getId(),
                            chosenCard.get(0)
                    ));
                }
            }
        });
        endTurn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //viewModel.turnEnd();
                actionLog.setText("Action:\nYou end the turn,now is your opponent's turn.");
                combine.setEnabled(false);
                use.setEnabled(false);
                endTurn.setEnabled(false);
            }
        });
        surrender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionLog.setText("Action:\nYou shall not surrender.");
                //todo
            }
        });
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
        observeViewModel(viewModel);
        Log.i("view", "observer setup done");
        // start game
        viewModel.init();
    }

    private void observeViewModel(GameViewModel viewModel) {
        // wire up ui elements and LiveData
        viewModel.getGameRuntime().getPlayer().observe(this, new Observer<Player>() {
            @Override
            public void onChanged(Player player) {
                // update UI if player info changed
                // example here shows a toast "player data updated"
                Toast.makeText(SinglePlayActivity.this, String.format("player: %s", player.getName()), Toast.LENGTH_SHORT).show();

                Log.i("player", player.getName());
                Log.i("hands", player.getHand().toString());
                for(Card card: player.getHand()) {
                    Log.i("hands (id)", String.format("%d", card.getCardId()));
                }
                // set player status
                setStatus(
                        player.getName(),
                        player.getHP(),
                        player.getAP(),
                        "default_class",
                        player.getHand().size(),
                        2);

                // set hand
                resetHandView();
                setHand(player.getHand());
            }
        });

        viewModel.getGameRuntime().getOpponent().observe(this, new Observer<Player>() {
            @Override
            public void onChanged(@Nullable Player player) {
                setStatus(
                        player.getName(),
                        player.getHP(),
                        player.getAP(),
                        "boss_class",
                        player.getHand().size(),
                        1
                );
            }
        });

        viewModel.getGameRuntime().getGameField().observe(this, new Observer<GameField>() {
            @Override
            public void onChanged(@Nullable GameField gameField) {
                weather.setText(gameField.getWeather().getLabel());
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
        TextView cardView = new TextView(this);
        final CheckBox cardBox = new CheckBox(this);
        cardView.setText(Translate.cardToString(card.getCardType()));
        cardView.setTextSize(32);
        cardBox.setText("select");
        cardBox.setTextSize(32);
        LinearLayout.LayoutParams cardLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
        LinearLayout.LayoutParams boxLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        childLayout.addView(cardBox, boxLayoutParams);
        childLayout.addView(cardView, cardLayoutParams);

        // data binding (in a lame way)
        // NOTE: need to be aware of variable scope
        int checkboxId = CardDataMap.size();
        CardDataMap.put(checkboxId, card.getCardId());
        Log.i("view", String.format("card data map: %s -> %s", checkboxId, card.getCardId()));

        // listener
        cardBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    chosenCard.add(CardDataMap.get(checkboxId));
                } else {
                    chosenCard.removeIf(cardId -> cardId.equals(CardDataMap.get(checkboxId)));
                }
                Log.i("view", String.format("chosen cards: %s", chosenCard.toString()));
            }
        });
    }

    private void setHand(List<Card> cards) {
        // NOTE: must be called after resetHandView()
        for (Card card: cards) {
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
}
