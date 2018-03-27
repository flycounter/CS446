package cs446_w2018_group3.supercardgame.view.lobby;

import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;

import java.net.InetAddress;
import java.net.UnknownHostException;

import cs446_w2018_group3.supercardgame.R;
import cs446_w2018_group3.supercardgame.model.network.ConnInfo;
import cs446_w2018_group3.supercardgame.view.game.MultiGameActivity;
import cs446_w2018_group3.supercardgame.viewmodel.LobbyViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HostGameDialogFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HostGameDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HostGameDialogFragment extends AppCompatDialogFragment {
    private static final String TAG = HostGameDialogFragment.class.getName();

    LobbyViewModel viewModel;

    private OnFragmentInteractionListener mListener;

    public HostGameDialogFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment HostGameDialogFragment.
     */
    public static HostGameDialogFragment newInstance() {
        HostGameDialogFragment fragment = new HostGameDialogFragment();
        fragment.setStyle(DialogFragment.STYLE_NO_TITLE, 0);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(getActivity()).get(LobbyViewModel.class);
    }

    @Override
    @NonNull
    public Dialog onCreateDialog( Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);

        // request a window without the title
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_join_game_dialog, container, false);
        TextView roomName = v.findViewById(R.id.room_name);
        TextView roomInfo = v.findViewById(R.id.room_info);
        TextView connStatus = v.findViewById(R.id.conn_status);
        ProgressBar progressBar = v.findViewById(R.id.progress_bar);
        Button joinGameBtn = v.findViewById(R.id.join_game_btn);

        ConnInfo connInfo = viewModel.getConnInfoContainer().getValue();
        String connState = viewModel.getConnStateContainer().getValue();

        roomName.setText(connInfo != null ? connInfo.getGameName() : "");
        roomInfo.setText(connInfo != null ? String.format("%s:%s", connInfo.getHost().getHostAddress(), connInfo.getPort()) : "");
        connStatus.setText(connState);

        joinGameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                joinGame(v);
            }
        });

        // set up observers
        viewModel.getConnInfoContainer().observe(this, new Observer<ConnInfo>() {
            @Override
            public void onChanged(@Nullable ConnInfo connInfo) {
                Log.i(TAG, "onChanged: connInfo: " + new Gson().toJson(connInfo));
                roomName.setText(connInfo != null ? connInfo.getGameName() : "");
                roomInfo.setText(connInfo != null ? String.format("%s:%s", connInfo.getHost().getHostAddress(), connInfo.getPort()) : "");
            }
        });

        viewModel.getConnStateContainer().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                Log.i(TAG, "onChanged: connState: " + new Gson().toJson(connState));
                connStatus.setText(s);
            }
        });

        viewModel.getIsGameReadyContainer().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean isReady) {
                if (isReady != null && isReady) {
                    progressBar.setVisibility(View.GONE);
                    joinGameBtn.setVisibility(View.VISIBLE);
                }
                else {
                    progressBar.setVisibility(View.VISIBLE);
                    joinGameBtn.setVisibility(View.GONE);
                }
            }
        });

        return v;

    }

    public void onButtonPressed() {
        if (mListener != null) {
            mListener.onFragmentInteraction();
        }
    }

    private void joinGame(View view) {
        viewModel.connectionListenerCleanup();

        Intent intent = new Intent(getActivity(), MultiGameActivity.class);
        intent.putExtra("isHost", true);
        view.getContext().startActivity(intent);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction();
    }
}
