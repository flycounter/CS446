package cs446_w2018_group3.supercardgame.view.game;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;

import cs446_w2018_group3.supercardgame.viewmodel.SingleGameViewModel;

/**
 * Created by JarvieK on 2018/3/25.
 */

public class SingleGameActivity extends GameActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(SingleGameViewModel.class);
    }
}
