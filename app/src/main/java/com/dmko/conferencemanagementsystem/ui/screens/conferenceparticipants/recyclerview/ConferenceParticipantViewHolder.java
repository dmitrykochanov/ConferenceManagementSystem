package com.dmko.conferencemanagementsystem.ui.screens.conferenceparticipants.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dmko.conferencemanagementsystem.R;
import com.dmko.conferencemanagementsystem.data.user.entities.BriefUserRoles;
import com.dmko.conferencemanagementsystem.ui.screens.conferenceparticipants.ConferenceParticipantsContract;
import com.robertlevonyan.views.chip.Chip;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.dmko.conferencemanagementsystem.utils.StatusHelper.roleAsString;

public class ConferenceParticipantViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.text_user_name) TextView textUserName;
    @BindView(R.id.text_user_email) TextView textUserEmail;
    @BindView(R.id.layout_roles) LinearLayout layoutRoles;

    private ConferenceParticipantsContract.Presenter presenter;

    public ConferenceParticipantViewHolder(View itemView, ConferenceParticipantsContract.Presenter presenter) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.presenter = presenter;
    }

    public void bindParticipant(BriefUserRoles participant) {
        textUserName.setText(String.format("%s %s", participant.getFirstName(), participant.getLastName()));
        textUserEmail.setText(participant.getEmail());

        for (Long role : participant.getRoles()) {
            Chip roleChip = new Chip(textUserName.getContext());
            roleChip.setChipText(roleAsString(role, textUserName.getContext()));
            roleChip.setClosable(presenter.isCurrentUserConferenceAdmin());
            layoutRoles.addView(roleChip);

            roleChip.setOnCloseClickListener(v -> {
                List<Long> roles = participant.getRoles();
                roles.remove(role);
                presenter.updateRoles(participant.getId(), roles);
            });
        }

        if (presenter.isCurrentUserConferenceAdmin()) {
            ImageButton pickRolesButton = new ImageButton(textUserName.getContext());
            pickRolesButton.setImageDrawable(textUserName.getContext().getResources().getDrawable(R.drawable.round_add_black_24));
            pickRolesButton.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
            layoutRoles.addView(pickRolesButton);

            pickRolesButton.setOnClickListener(v -> {
                presenter.onPickRolesSelected(participant.getId());
            });
        }
    }
}
