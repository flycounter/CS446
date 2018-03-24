package cs446_w2018_group3.supercardgame.view.lobby;

import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
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
import android.widget.TextView;

import com.google.gson.Gson;

import cs446_w2018_group3.supercardgame.R;
import cs446_w2018_group3.supercardgame.model.network.ConnInfo;
import cs446_w2018_group3.supercardgame.viewmodel.LobbyViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link JoinGameDialogFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link JoinGameDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class JoinGameDialogFragment extends AppCompatDialogFragment {
    private static final String TAG = JoinGameDialogFragment.class.getName();
    // TODO: Rename parameter arguments, choose names that match

    private LobbyViewModel viewModel;

    private OnFragmentInteractionListener mListener;

    public JoinGameDialogFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment JoinGameDialogFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static JoinGameDialogFragment newInstance() {
        JoinGameDialogFragment fragment = new JoinGameDialogFragment();
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

        ConnInfo connInfo = viewModel.getConnInfoContainer().getValue();
        String connState = viewModel.getConnStateContainer().getValue();

        roomName.setText(connInfo != null ? connInfo.getPlayerName() : "");
        roomInfo.setText(connInfo != null ? String.format("%s:%s", connInfo.getHost().getHostAddress(), connInfo.getPort()) : "");
        connStatus.setText(connState);

        // set up observers
        viewModel.getConnInfoContainer().observe(this, new Observer<ConnInfo>() {
            @Override
            public void onChanged(@Nullable ConnInfo connInfo) {
                Log.i(TAG, "onChanged: connInfo: " + new Gson().toJson(connInfo));
                roomName.setText(connInfo != null ? connInfo.getPlayerName() : "");
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

        return v;

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
