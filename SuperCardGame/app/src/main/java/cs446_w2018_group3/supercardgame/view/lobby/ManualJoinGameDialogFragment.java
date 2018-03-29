package cs446_w2018_group3.supercardgame.view.lobby;

import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import android.widget.Toast;

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
 * {@link ManualJoinGameDialogFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ManualJoinGameDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ManualJoinGameDialogFragment extends AppCompatDialogFragment {
    private static final String TAG = ManualJoinGameDialogFragment.class.getName();

    LobbyViewModel viewModel;

    private OnFragmentInteractionListener mListener;

    public ManualJoinGameDialogFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ManualJoinGameDialogFragment.
     */
    public static ManualJoinGameDialogFragment newInstance() {
        ManualJoinGameDialogFragment fragment = new ManualJoinGameDialogFragment();
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

        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_manual_join_game, container, false);

        getDialog().setTitle("Join game");

        TextView hostHolder = v.findViewById(R.id.host);
        TextView portHolder = v.findViewById(R.id.port);
        Button button = v.findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    InetAddress host = InetAddress.getByName(hostHolder.getText().toString());
                    int port = Integer.parseInt(portHolder.getText().toString());
                    if (mListener != null) {
                        mListener.onFragmentInteraction(host, port);
                        getFragmentManager().popBackStack();
                    }
                }
                catch (UnknownHostException err) {
                    Toast.makeText(getContext(), err.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        return v;
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
        void onFragmentInteraction(InetAddress host, int port);
    }
}
