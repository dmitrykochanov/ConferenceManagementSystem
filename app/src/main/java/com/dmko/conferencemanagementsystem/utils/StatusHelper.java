package com.dmko.conferencemanagementsystem.utils;

import android.content.Context;

import com.dmko.conferencemanagementsystem.R;
import com.dmko.conferencemanagementsystem.data.requests.entities.BriefConferenceRequest;
import com.dmko.conferencemanagementsystem.data.reviews.entities.BriefReview;
import com.dmko.conferencemanagementsystem.data.submissions.entities.Document;

import static com.dmko.conferencemanagementsystem.data.submissions.entities.BriefSubmission.Statuses.ACCEPT;
import static com.dmko.conferencemanagementsystem.data.submissions.entities.BriefSubmission.Statuses.PENDING;
import static com.dmko.conferencemanagementsystem.data.submissions.entities.BriefSubmission.Statuses.REJECT;
import static com.dmko.conferencemanagementsystem.data.user.entities.BriefUserRoles.Roles.ADMIN;
import static com.dmko.conferencemanagementsystem.data.user.entities.BriefUserRoles.Roles.CREATOR;
import static com.dmko.conferencemanagementsystem.data.user.entities.BriefUserRoles.Roles.REVIEWER;
import static com.dmko.conferencemanagementsystem.data.user.entities.BriefUserRoles.Roles.SUBMITTER;

public final class StatusHelper {
    private StatusHelper() {
    }

    public static String roleAsString(Long role, Context context) {

        if (role == SUBMITTER) {
            return context.getString(R.string.role_submitter);
        } else if (role == ADMIN) {
            return context.getString(R.string.role_admin);
        } else if (role == CREATOR) {
            return context.getString(R.string.role_creator);
        } else if (role == REVIEWER) {
            return context.getString(R.string.role_reviewer);
        } else {
            throw new IllegalArgumentException("Role doesn't exist " + role);
        }
    }

    public static String submissionStatusAsString(int status, Context context) {
        switch (status) {
            case PENDING:
                return context.getString(R.string.status_submission_pending);
            case ACCEPT:
                return context.getString(R.string.status_submission_accept);
            case REJECT:
                return context.getString(R.string.status_submission_reject);
            default:
                throw new IllegalArgumentException("Submission status doesn't exist " + status);
        }
    }

    public static int submissionStatusAsColor(int status) {
        switch (status) {
            case ACCEPT:
                return R.color.colorStatusAccept;
            case REJECT:
                return R.color.colorStatusReject;
            case PENDING:
                return R.color.colorStatusPending;
            default:
                throw new IllegalArgumentException("Submission status doesn't exist " + status);
        }
    }

    public static int conferenceRequestStatusAsColor(int status) {
        switch (status) {
            case BriefConferenceRequest.Statuses.PENDING:
                return R.color.colorStatusPending;
            case BriefConferenceRequest.Statuses.ACCEPTED:
                return R.color.colorStatusAccept;
            case BriefConferenceRequest.Statuses.DECLINED:
                return R.color.colorStatusReject;
            default:
                throw new IllegalArgumentException("Conference request status doesn't exist " + status);
        }
    }

    public static int documentStatusAsColor(int status) {
        switch (status) {
            case Document.Statuses.PENDING:
                return R.color.colorStatusPending;
            case Document.Statuses.ACCEPT:
                return R.color.colorStatusAccept;
            case Document.Statuses.REJECT:
                return R.color.colorStatusReject;
            default:
                throw new IllegalArgumentException("Document status doesn't exist " + status);
        }
    }

    public static int reviewStatusAsColor(int status) {
        switch (status) {
            case BriefReview.Statuses.ACCEPT:
                return R.color.colorStatusAccept;
            case BriefReview.Statuses.REJECT:
                return R.color.colorStatusReject;
            case BriefReview.Statuses.NO_DECISION:
                return R.color.colorStatusPending;
            case BriefReview.Statuses.PROBABLY_ACCEPT:
                return R.color.colorStatusPending;
            case BriefReview.Statuses.PROBABLY_REJECT:
                return R.color.colorStatusPending;
            default:
                throw new IllegalArgumentException("Review status doesn't exist " + status);
        }
    }

}
